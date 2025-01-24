/* Commune ############################## */
INSERT INTO commune (id, label) VALUES (1, 'Commune Urbaine Antananarivo');
INSERT INTO commune (id, label) VALUES (2, 'Commune Urbaine Antsirabe');

/* Arrondissement ############################## */
INSERT INTO arrondissement (id, id_commune, label) VALUES (1, 1, '1er arrondissement');
INSERT INTO arrondissement (id, id_commune, label) VALUES (2, 1, '2eme arrondissement');
INSERT INTO arrondissement (id, id_commune, label) VALUES (3, 1, '3eme arrondissement');
INSERT INTO arrondissement (id, id_commune, label) VALUES (4, 1, '4eme arrondissement');

-- Arrondissement Position
/* ARR - 1 ############################## */
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (1, 1, -18.58910, 47.08053);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (2, 1, -18.84027, 46.79626);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (3, 1, -18.99235, 46.91848);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (4, 1, -19.02872, 47.17666);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (5, 1, -18.84807, 47.55844);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (6, 1, -18.64379, 47.51312);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (7, 1, -18.56305, 47.19451);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (8, 1, -18.58910, 47.08053);

/* ARR - 2 ############################## */
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (9, 2, -18.71408, 47.79464);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (10, 2, -18.77653, 47.79464);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (11, 2, -18.84287, 47.84957);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (12, 2, -18.89228, 47.90039);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (13, 2, -18.95856, 48.01025);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (14, 2, -18.77653, 48.13247);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (15, 2, -18.65551, 48.25332);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (16, 2, -18.53960, 48.21212);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (17, 2, -18.48227, 48.06381);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (18, 2, -18.46663, 47.89764);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (19, 2, -18.49661, 47.92236);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (20, 2, -18.71408, 47.79464);

/* ARR - 3 ############################## */
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (21, 3, -19.07807, 47.32772);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (22, 3, -19.05547, 47.61749);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (23, 3, -19.27273, 47.76580);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (24, 3, -19.35702, 47.43621);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (25, 3, -19.26625, 47.26455);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (26, 3, -19.07807, 47.32772);

/* ARR - 4 ############################## */
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (27, 4, -18.37798, 47.46368);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (28, 4, -18.69195, 47.59002);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (29, 4, -18.55262, 47.82486);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (30, 4, -18.32973, 47.62710);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (31, 4, -18.37798, 47.46368);

/* MATERIAL ########################################## */
INSERT INTO material (id, label) VALUES (1, 'Hazo');
INSERT INTO material (id, label) VALUES (2, 'Brique');
INSERT INTO material (id, label) VALUES (3, 'Beton');
INSERT INTO material (id, label) VALUES (4, 'Bozaka');
INSERT INTO material (id, label) VALUES (5, 'Tuile');
INSERT INTO material (id, label) VALUES (6, 'Tole');

/* COMPOSANT ########################################## */
INSERT INTO house_composant (id, label) VALUES (1, 'Rindrina');
INSERT INTO house_composant (id, label) VALUES (2, 'Tafo');

/* TAXE PER ARRONDISSEMENT ############################## */
INSERT INTO taxe_per_commune (id, id_commune, date_taxe, amount_per_m2) VALUES (1, 1, TO_DATE('2025-01-01', 'YYYY-MM-DD'), 3000);


/* TRANO ########################################## */
INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (1, f_get_arrondissement_by_point(46.98440, -18.86859), 'Trano 1', 400, 200, 2, -18.86859, 46.98440);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (2, f_get_arrondissement_by_point(47.30850, -18.80775), 'Trano 2', 150, 90, 1, -18.80775, 47.30850);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (3, f_get_arrondissement_by_point(47.22335, -18.75954), 'Trano 3', 600, 700, 3, -18.75954, 47.22335);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (4, f_get_arrondissement_by_point(47.98553, -18.6346), 'Trano 4', 300, 150, 1, -18.6346, 47.98553);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (5, f_get_arrondissement_by_point(48.00201, -18.75572), 'Trano 5', 540, 260, 2, -18.75572, 48.00201);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (6, f_get_arrondissement_by_point(47.96081, -18.80231), 'Trano 6', 470, 360, 3, -18.80231, 47.96081);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (7, f_get_arrondissement_by_point(47.56256, -19.17673), 'Trano 7', 220, 100, 1, -19.17673, 47.56256);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (8, f_get_arrondissement_by_point(47.64358, -19.23512), 'Trano 8', 600, 210, 2, -19.23512, 47.64358);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (9, f_get_arrondissement_by_point(47.39227, -19.18062), 'Trano 9', 500, 400, 3, -19.18062, 47.39227);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (10, f_get_arrondissement_by_point(47.60376, -18.49139), 'Trano 10', 250, 300, 4, -18.49139, 47.60376);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (11, f_get_arrondissement_by_point(47.58453, -18.53569), 'Trano 11', 260, 100, 3, -18.53569, 47.58453);

INSERT INTO house (id, id_arrondissement, label, width, height, nbr_floor, latitude, longitude) 
VALUES (12, f_get_arrondissement_by_point(47.72735, -18.521361), 'Trano 12', 255.5, 200, 2, -18.521361, 47.72735);


/* House Composant material ########################################## */
INSERT INTO house_composant_material (id, id_house_composant, id_material, coefficient)
VALUES (1, 1, 1, 0.8);

INSERT INTO house_composant_material (id, id_house_composant, id_material, coefficient)
VALUES (2, 1, 2, 1.1);

INSERT INTO house_composant_material (id, id_house_composant, id_material, coefficient)
VALUES (3, 1, 3, 1.2);

INSERT INTO house_composant_material (id, id_house_composant, id_material, coefficient)
VALUES (4, 2, 4, 0.6);

INSERT INTO house_composant_material (id, id_house_composant, id_material, coefficient)
VALUES (5, 2, 5, 0.8);

INSERT INTO house_composant_material (id, id_house_composant, id_material, coefficient)
VALUES (6, 2, 6, 1.1);

INSERT INTO house_composant_material (id, id_house_composant, id_material, coefficient)
VALUES (7, 2, 3, 1.4);

/* House Caracteristique ########################################## */
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (1, 1, 3);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (2, 1, 6);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (3, 2, 2);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (4, 2, 5);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (5, 3, 1);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (6, 3, 5);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (7, 4, 3);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (8, 4, 4);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (9, 5, 2);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (10, 5, 5);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (11, 6, 1);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (12, 6, 7);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (13, 7, 2);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (14, 7, 6);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (15, 8, 1);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (16, 8, 7);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (17, 9, 3);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (18, 9, 4);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (19, 10, 3);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (20, 10, 5);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (21, 11, 1);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (22, 11, 5);

INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (23, 12, 2);
INSERT INTO house_caracteristique (id, id_house, id_house_composant_material)
VALUES (24, 12, 6);