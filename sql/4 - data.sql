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
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (1, 1, -18.589103, 47.080536);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (2, 1, -18.840271, 46.796265);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (3, 1, -18.99235, 46.918488);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (4, 1, -19.028725, 47.176666);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (5, 1, -18.848073, 47.558441);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (6, 1, -18.643793, 47.513123);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (7, 1, -18.563054, 47.194519);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (8, 1, -18.589103, 47.080536);

/* ARR - 2 ############################## */
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (9, 2, -18.714083, 47.794647);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (10, 2, -18.776539, 47.794647);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (11, 2, -18.842872, 47.849579);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (12, 2, -18.89228, 47.900391);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (13, 2, -18.958568, 48.010254);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (14, 2, -18.776539, 48.132477);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (15, 2, -18.655511, 48.253326);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (16, 2, -18.539607, 48.212128);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (17, 2, -18.482278, 48.063812);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (18, 2, -18.466639, 47.897644);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (19, 2, -18.496612, 47.922363);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (20, 2, -18.714083, 47.794647);

/* ARR - 3 ############################## */
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (21, 3, -19.07807, 47.327728);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (22, 3, -19.075472, 47.617493);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (23, 3, -19.272739, 47.765808);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (24, 3, -19.357025, 47.436218);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (25, 3, -19.266254, 47.264557);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (26, 3, -19.07807, 47.327728);

/* ARR - 4 ############################## */
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (27, 4, -18.377986, 47.463684);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (28, 4, -18.691951, 47.590027);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (29, 4, -18.552627, 47.82486);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (30, 4, -18.329732, 47.627106);
INSERT INTO arrondissement_position (id, id_arrondissement, longitude, latitude) VALUES (31, 4, -18.377986, 47.463684);

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
INSERT INTO taxe_per_commune (id, id_commune, date_taxe, amount_per_m2) VALUES (1, 1, TO_DATE('2025-01-01', 'YYYY-MM-DD'), 100);


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

/* ######################################## */

INSERT INTO house_owner (id, name, phone) VALUES (1, 'Jean Dupont', '0601020304');
INSERT INTO house_owner (id, name, phone) VALUES (2, 'Marie Laurent', '0612345678');
INSERT INTO house_owner (id, name, phone) VALUES (3, 'Pierre Martin', '0623456789');
INSERT INTO house_owner (id, name, phone) VALUES (4, 'Sophie Dubois', '0634567890');
INSERT INTO house_owner (id, name, phone) VALUES (5, 'Christophe Leroy', '0645678901');
INSERT INTO house_owner (id, name, phone) VALUES (6, 'Isabelle Thomas', '0656789012');
INSERT INTO house_owner (id, name, phone) VALUES (7, 'Olivier Petit', '0667890123');
INSERT INTO house_owner (id, name, phone) VALUES (8, 'Claire Moreau', '0678901234');
INSERT INTO house_owner (id, name, phone) VALUES (9, 'Nicolas Rousseau', '0689012345');
INSERT INTO house_owner (id, name, phone) VALUES (10, 'Emilie Bernard', '0690123456');
INSERT INTO house_owner (id, name, phone) VALUES (11, 'Alexandre Girard', '0601122334');
INSERT INTO house_owner (id, name, phone) VALUES (12, 'Camille Lemoine', '0611223344');

UPDATE house SET id_house_owner = 1 WHERE id = 1;
UPDATE house SET id_house_owner = 2 WHERE id = 2;
UPDATE house SET id_house_owner = 3 WHERE id = 3;
UPDATE house SET id_house_owner = 4 WHERE id = 4;
UPDATE house SET id_house_owner = 5 WHERE id = 5;
UPDATE house SET id_house_owner = 6 WHERE id = 6;
UPDATE house SET id_house_owner = 7 WHERE id = 7;
UPDATE house SET id_house_owner = 8 WHERE id = 8;
UPDATE house SET id_house_owner = 9 WHERE id = 9;
UPDATE house SET id_house_owner = 10 WHERE id = 10;
UPDATE house SET id_house_owner = 11 WHERE id = 11;
UPDATE house SET id_house_owner = 12 WHERE id = 12;

/*================================================================== */
/*================================================================== */
/*================================================================== */
INSERT INTO users (id, id_commune, user_name, user_password) VALUES (1, 1, 'user', 'user');