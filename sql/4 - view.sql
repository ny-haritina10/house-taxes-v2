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


CREATE OR REPLACE VIEW houses_by_owner_view AS
SELECT 
    ho.id AS owner_id,
    ho.name AS owner_name,
    ho.phone AS owner_phone,
    h.id AS house_id,
    h.label AS house_label,
    h.width,
    h.height,
    h.nbr_floor,
    h.longitude,
    h.latitude
FROM 
    house_owner ho
LEFT JOIN 
    house h ON ho.id = h.id_house_owner;


CREATE OR REPLACE VIEW house_invoice_simple AS
SELECT 
    f.id AS facture_id,
    h.id AS house_id,
    h.label AS house_label,
    f.totalSurface AS total_surface,
    f.unit_price AS unit_price,
    f.coefficient AS coefficient,
    f.year AS year,
    f.month AS month
FROM 
    facture f
JOIN 
    house h ON f.id_house = h.id;