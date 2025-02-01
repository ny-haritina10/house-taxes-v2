CREATE TABLE paiement(
    id NUMBER PRIMARY KEY ,
    date_payment date not null,
    amount_pay  NUMBER(15,2) ,
    id_facture  NUMBER not null,
    FOREIGN KEY(id_facture) REFERENCES facture(id)
);

CREATE SEQUENCE paiement_seq START WITH 1 INCREMENT BY 1;


CREATE OR REPLACE TRIGGER trg_paiement_id
BEFORE INSERT ON paiement
FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        :NEW.id := paiement_seq.NEXTVAL;
    END IF;
END;
/

INSERT INTO paiement (id, date_payment, amount_pay, id_facture) VALUES (1, TO_DATE('2024-02-15', 'YYYY-MM-DD'), 10000000, 262);

INSERT INTO paiement (id, date_payment, amount_pay, id_facture) VALUES (2, TO_DATE('2024-03-15', 'YYYY-MM-DD'), 8000000, 262);


CREATE OR REPLACE VIEW v_facture_payement AS
SELECT 
    p.id_facture,
    p.date_payment,
    (f.totalSurface * unit_price * coefficient) as total_prix,
    p.amount_pay
from 
    facture f 
join 
    paiement p 
on 
    f.id = p.id_facture ;


CREATE OR REPLACE TYPE payment_summary_row AS OBJECT (
    id_facture NUMBER,
    total_prix NUMBER,
    total_paye NUMBER,
    reste_a_paye NUMBER
);
/

CREATE OR REPLACE TYPE payment_summary_table AS TABLE OF payment_summary_row;
/

CREATE OR REPLACE FUNCTION get_payment_summary(
    p_start_date IN DATE,
    p_end_date IN DATE
) RETURN payment_summary_table PIPELINED AS
BEGIN
    FOR r IN (
        SELECT 
            id_facture,
            total_prix,
            SUM(amount_pay) as total_paye,
            total_prix - SUM(amount_pay) as reste_a_paye
        FROM 
            v_facture_payement
        WHERE 
            date_payment >= p_start_date
            AND date_payment <= p_end_date
        GROUP BY 
            id_facture,
            total_prix
    ) LOOP
        PIPE ROW(payment_summary_row(
            r.id_facture,
            r.total_prix,
            r.total_paye,
            r.reste_a_paye
        ));
    END LOOP;
    
    RETURN;
END get_payment_summary;
/


SELECT * FROM TABLE(
    get_payment_summary(
        TO_DATE('2024-01-27', 'YYYY-MM-DD'),
        TO_DATE('2024-03-27', 'YYYY-MM-DD')
    )
);