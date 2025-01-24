--
--
--
CREATE OR REPLACE VIEW v_house_composant_material AS
SELECT 
    hcm.id AS house_composant_material_id,
    hc.label AS house_composant_label,
    m.label AS material_label,
    hcm.coefficient
FROM 
    house_composant_material hcm
JOIN 
    house_composant hc ON hcm.id_house_composant = hc.id
JOIN 
    material m ON hcm.id_material = m.id;


CREATE OR REPLACE VIEW house_commune_view AS
SELECT 
    h.id AS house_id,
    h.label AS house_label,
    c.id AS commune_id,
    c.label AS commune_label
FROM 
    house h
    JOIN arrondissement a ON h.id_arrondissement = a.id
    JOIN commune c ON a.id_commune = c.id;