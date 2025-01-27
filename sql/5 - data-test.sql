-- Trano 1

UPDATE house SET nbr_floor = 2 ,  last_changement = TO_DATE('2025-02-01', 'YYYY-MM-DD')  WHERE id = 1;


UPDATE taxe_per_commune SET amount_per_m2 = 200, date_taxe = TO_DATE('2025-01-27', 'YYYY-MM-DD') WHERE id = 1;