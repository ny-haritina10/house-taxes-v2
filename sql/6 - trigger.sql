CREATE OR REPLACE TRIGGER trg_taxe_history
AFTER INSERT OR UPDATE OR DELETE ON taxe_per_commune
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO histo_taxe_per_commune (id_taxe, id_commune, date_taxe, amount_per_m2, change_type, changed_by,changed_at)
        VALUES (:NEW.id, :NEW.id_commune, :NEW.date_taxe, :NEW.amount_per_m2, 'INSERT', USER,:new.date_taxe);
    ELSIF UPDATING THEN
        INSERT INTO histo_taxe_per_commune (id_taxe, id_commune, date_taxe, amount_per_m2, change_type, changed_by,changed_at)
        VALUES (:new.id, :new.id_commune, :new.date_taxe, :new.amount_per_m2, 'UPDATE', USER,:new.date_taxe);
    ELSIF DELETING THEN
        INSERT INTO histo_taxe_per_commune (id_taxe, id_commune, date_taxe, amount_per_m2, change_type, changed_by)
        VALUES (:OLD.id, :OLD.id_commune, :OLD.date_taxe, :OLD.amount_per_m2, 'DELETE', USER);
    END IF;
END;
/


ALTER TABLE house_composant_material 
ADD last_changement DATE;


CREATE OR REPLACE TRIGGER trg_house_cm_history
AFTER INSERT OR UPDATE OR DELETE ON house_composant_material
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO histo_house_comp_mat (id_house_composant_material, id_house_composant, id_material, coefficient, change_type, changed_by,changed_at)
        VALUES (:NEW.id, :NEW.id_house_composant, :NEW.id_material, :NEW.coefficient, 'INSERT', USER,COALESCE(:NEW.last_changement, CURRENT_TIMESTAMP));
    ELSIF UPDATING THEN
        INSERT INTO histo_house_comp_mat (id_house_composant_material, id_house_composant, id_material, coefficient, change_type, changed_by,changed_at)
        VALUES (:NEW.id, :NEW.id_house_composant, :NEW.id_material, :NEW.coefficient, 'UPDATE', USER,COALESCE(:NEW.last_changement, CURRENT_TIMESTAMP));
    ELSIF DELETING THEN
        INSERT INTO histo_house_comp_mat (id_house_composant_material, id_house_composant, id_material, coefficient, change_type, changed_by)
        VALUES (:OLD.id, :OLD.id_house_composant, :OLD.id_material, :OLD.coefficient, 'DELETE', USER);
    END IF;
END;
/