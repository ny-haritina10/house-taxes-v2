/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE TABLE commune (
    id NUMBER PRIMARY KEY,
    label VARCHAR2(255) NOT NULL
);

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE TABLE users (
    id NUMBER PRIMARY KEY,
    id_commune NUMBER NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    CONSTRAINT fk_commune_user FOREIGN KEY (id_commune) REFERENCES commune(id)
);

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE TABLE arrondissement (
    id NUMBER PRIMARY KEY,
    id_commune NUMBER NOT NULL,
    label VARCHAR2(255) NOT NULL,
    CONSTRAINT fk_commune FOREIGN KEY (id_commune) REFERENCES commune(id)
);

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE TABLE arrondissement_position (
    id NUMBER PRIMARY KEY,
    id_arrondissement NUMBER NOT NULL,
    longitude NUMBER(10, 5) NOT NULL,
    latitude NUMBER(10, 5) NOT NULL,
    CONSTRAINT fk_arrondissement_position FOREIGN KEY (id_arrondissement) REFERENCES arrondissement(id)
);

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE TABLE taxe_per_commune (
    id NUMBER PRIMARY KEY,
    id_commune NUMBER NOT NULL,
    date_taxe DATE NOT NULL,
    amount_per_m2 NUMBER(10, 5) NOT NULL,
    CONSTRAINT fk_taxe_per_commune FOREIGN KEY (id_commune) REFERENCES commune(id)
);

/*================================================================== */
/*================================================================== */
/*================================================================== */
CREATE TABLE house (
    id NUMBER PRIMARY KEY,
    id_arrondissement NUMBER,
    label VARCHAR2(255) NOT NULL,
    width NUMBER(10, 5) NOT NULL,
    height NUMBER(10, 5) NOT NULL,
    nbr_floor NUMBER NOT NULL,
    longitude NUMBER(10, 5) NOT NULL,
    latitude NUMBER(10, 5) NOT NULL,
    CONSTRAINT fk_house_arrondissement FOREIGN KEY (id_arrondissement) REFERENCES arrondissement(id)
);

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE TABLE house_composant (
    id NUMBER PRIMARY KEY,
    label VARCHAR2(255) NOT NULL
);

/*================================================================== */
/*================================================================== */
/*================================================================== */
CREATE TABLE material (
    id NUMBER PRIMARY KEY,
    label VARCHAR2(255) NOT NULL
);

/*================================================================== */
/*================================================================== */
/*================================================================== */
CREATE TABLE house_composant_material (
    id NUMBER PRIMARY KEY,
    id_house_composant NUMBER NOT NULL,
    id_material NUMBER NOT NULL,
    coefficient NUMBER(10, 5) NOT NULL,
    CONSTRAINT fk_house_composant_mc FOREIGN KEY (id_house_composant) REFERENCES house_composant(id),
    CONSTRAINT fk_house_composant_mm FOREIGN KEY (id_material) REFERENCES material(id)
);

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE TABLE house_caracteristique (
    id NUMBER PRIMARY KEY,
    id_house NUMBER NOT NULL,
    id_house_composant_material NUMBER NOT NULL,
    CONSTRAINT fk_house_caracteristique_h FOREIGN KEY (id_house) REFERENCES house(id),
    CONSTRAINT fk_house_caracteristique_cm FOREIGN KEY (id_house_composant_material) REFERENCES house_composant_material(id)
);

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE SEQUENCE taxe_payment_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE taxe_payment (
    id NUMBER PRIMARY KEY,
    id_house NUMBER NOT NULL,
    amount NUMBER(10, 5) NOT NULL,
    date_payment DATE NOT NULL,
    CONSTRAINT fk_taxe_payment_house FOREIGN KEY (id_house) REFERENCES house(id)
);

CREATE OR REPLACE TRIGGER taxe_payment_trigger
BEFORE INSERT ON taxe_payment
FOR EACH ROW
BEGIN
    :new.id := taxe_payment_seq.NEXTVAL;
END;
/

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE SEQUENCE h_surface_house_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE histo_house (
    id NUMBER PRIMARY KEY,
    id_house NUMBER, 
    id_arrondissement NUMBER,
    label VARCHAR2(255),
    width NUMBER(10, 5),
    height NUMBER(10, 5),
    nbr_floor NUMBER,
    longitude NUMBER(10, 5),
    latitude NUMBER(10, 5),
    change_type VARCHAR2(10) NOT NULL, -- 'INSERT', 'UPDATE', or 'DELETE'
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, 
    changed_by VARCHAR2(100), 
    CONSTRAINT fk_house_history FOREIGN KEY (id_house) REFERENCES house(id) ON DELETE CASCADE,
    CONSTRAINT fk_histo_arr FOREIGN KEY (id_arrondissement) REFERENCES arrondissement(id) ON DELETE CASCADE
);

CREATE OR REPLACE TRIGGER pk_h_house_trg
BEFORE INSERT ON histo_house
FOR EACH ROW
BEGIN
    :new.id := h_surface_house_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER trg_house_history
AFTER INSERT OR UPDATE OR DELETE ON house
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO histo_house (id_house, id_arrondissement, label, width, height, nbr_floor, longitude, latitude, change_type, changed_by)
        VALUES (:NEW.id, :NEW.id_arrondissement, :NEW.label, :NEW.width, :NEW.height, :NEW.nbr_floor, :NEW.longitude, :NEW.latitude, 'INSERT', USER);
    ELSIF UPDATING THEN
        INSERT INTO histo_house (id_house, id_arrondissement, label, width, height, nbr_floor, longitude, latitude, change_type, changed_by)
        VALUES (:OLD.id, :OLD.id_arrondissement, :OLD.label, :OLD.width, :OLD.height, :OLD.nbr_floor, :OLD.longitude, :OLD.latitude, 'UPDATE', USER);
    ELSIF DELETING THEN
        INSERT INTO histo_house (id_house, id_arrondissement, label, width, height, nbr_floor, longitude, latitude, change_type, changed_by)
        VALUES (:OLD.id, :OLD.id_arrondissement, :OLD.label, :OLD.width, :OLD.height, :OLD.nbr_floor, :OLD.longitude, :OLD.latitude, 'DELETE', USER);
    END IF;
END;
/

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE SEQUENCE h_taxe_commune_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE histo_taxe_per_commune (
    id NUMBER PRIMARY KEY, 
    id_taxe NUMBER NOT NULL, 
    id_commune NUMBER NOT NULL, 
    date_taxe DATE NOT NULL, 
    amount_per_m2 NUMBER(10, 5) NOT NULL, 
    change_type VARCHAR2(10) NOT NULL, -- 'INSERT', 'UPDATE', or 'DELETE'
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, 
    changed_by VARCHAR2(100), 
    CONSTRAINT fk_histo_taxe FOREIGN KEY (id_taxe) REFERENCES taxe_per_commune(id) ON DELETE CASCADE,
    CONSTRAINT fk_histo_commune FOREIGN KEY (id_commune) REFERENCES commune(id) ON DELETE CASCADE
);

CREATE OR REPLACE TRIGGER pk_h_taxe_trg
BEFORE INSERT ON histo_taxe_per_commune
FOR EACH ROW
BEGIN
    :new.id := h_taxe_commune_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER trg_taxe_history
AFTER INSERT OR UPDATE OR DELETE ON taxe_per_commune
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO histo_taxe_per_commune (id_taxe, id_commune, date_taxe, amount_per_m2, change_type, changed_by)
        VALUES (:NEW.id, :NEW.id_commune, :NEW.date_taxe, :NEW.amount_per_m2, 'INSERT', USER);
    ELSIF UPDATING THEN
        INSERT INTO histo_taxe_per_commune (id_taxe, id_commune, date_taxe, amount_per_m2, change_type, changed_by)
        VALUES (:OLD.id, :OLD.id_commune, :OLD.date_taxe, :OLD.amount_per_m2, 'UPDATE', USER);
    ELSIF DELETING THEN
        INSERT INTO histo_taxe_per_commune (id_taxe, id_commune, date_taxe, amount_per_m2, change_type, changed_by)
        VALUES (:OLD.id, :OLD.id_commune, :OLD.date_taxe, :OLD.amount_per_m2, 'DELETE', USER);
    END IF;
END;
/

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE SEQUENCE h_house_cm_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE histo_house_comp_mat (
    id NUMBER PRIMARY KEY, 
    id_house_composant_material NUMBER NOT NULL, 
    id_house_composant NUMBER NOT NULL, 
    id_material NUMBER NOT NULL,
    coefficient NUMBER(10, 5) NOT NULL, 
    change_type VARCHAR2(10) NOT NULL, 
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, 
    changed_by VARCHAR2(100), 
    CONSTRAINT fk_histo_hcm FOREIGN KEY (id_house_composant_material) REFERENCES house_composant_material(id) ON DELETE CASCADE,
    CONSTRAINT fk_histo_hc FOREIGN KEY (id_house_composant) REFERENCES house_composant(id) ON DELETE CASCADE,
    CONSTRAINT fk_histo_m FOREIGN KEY (id_material) REFERENCES material(id) ON DELETE CASCADE
);

CREATE OR REPLACE TRIGGER pk_h_house_cm_trg
BEFORE INSERT ON histo_house_comp_mat
FOR EACH ROW
BEGIN
    :new.id := h_house_cm_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER trg_house_cm_history
AFTER INSERT OR UPDATE OR DELETE ON house_composant_material
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO histo_house_comp_mat (id_house_composant_material, id_house_composant, id_material, coefficient, change_type, changed_by)
        VALUES (:NEW.id, :NEW.id_house_composant, :NEW.id_material, :NEW.coefficient, 'INSERT', USER);
    ELSIF UPDATING THEN
        INSERT INTO histo_house_comp_mat (id_house_composant_material, id_house_composant, id_material, coefficient, change_type, changed_by)
        VALUES (:OLD.id, :OLD.id_house_composant, :OLD.id_material, :OLD.coefficient, 'UPDATE', USER);
    ELSIF DELETING THEN
        INSERT INTO histo_house_comp_mat (id_house_composant_material, id_house_composant, id_material, coefficient, change_type, changed_by)
        VALUES (:OLD.id, :OLD.id_house_composant, :OLD.id_material, :OLD.coefficient, 'DELETE', USER);
    END IF;
END;
/

/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE TABLE house_owner(
   id NUMBER PRIMARY KEY ,
   name VARCHAR2(50)  NOT NULL,
   phone VARCHAR2(50)
);


/*================================================================== */
/*================================================================== */
/*================================================================== */\

ALTER TABLE house ADD id_house_owner NUMBER;

ALTER TABLE house 
ADD CONSTRAINT fk_house_owner 
FOREIGN KEY (id_house_owner) 
REFERENCES house_owner(id);


/*================================================================== */
/*================================================================== */
/*================================================================== */

CREATE TABLE facture(
   id NUMBER PRIMARY KEY ,
   totalSurface NUMBER(15,2)  ,
   year NUMBER(10),
   month NUMBER(10),
   unit_price NUMBER(15,2)  ,
   coefficient NUMBER(15,2)  ,
   id_house NUMBER  NOT NULL,
   FOREIGN KEY(id_house) REFERENCES house(id)
);

CREATE SEQUENCE facture_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_facture_id
BEFORE INSERT ON facture
FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        :NEW.id := facture_seq.NEXTVAL;
    END IF;
END;
/


/*================================================================== */
/*================================================================== */
/*================================================================== */

ALTER TABLE facture
MODIFY monthly_amount_to_pay NUMBER(15, 2);

ALTER TABLE facture
ADD is_payed CHAR(1) DEFAULT 'N';

ALTER TABLE facture 
ADD date_payment_facture DATE DEFAULT NULL;