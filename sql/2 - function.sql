CREATE OR REPLACE FUNCTION f_get_arrondissement_by_point(
    p_latitude NUMBER,
    p_longitude NUMBER
) RETURN NUMBER IS
    v_intersections INTEGER := 0;
    v_x1 NUMBER;
    v_y1 NUMBER;
    v_x2 NUMBER;
    v_y2 NUMBER;
    v_result NUMBER := NULL;
BEGIN
    -- Loop through each arrondissement
    FOR arr_record IN (
        SELECT DISTINCT id_arrondissement 
        FROM arrondissement_position
    ) LOOP
        v_intersections := 0;

        -- Get ordered coordinates for the current arrondissement
        FOR coord_record IN (
            WITH ordered_coords AS (
                SELECT 
                    id_arrondissement,
                    longitude,
                    latitude,
                    ROW_NUMBER() OVER (PARTITION BY id_arrondissement ORDER BY id) AS rn,
                    COUNT(*) OVER (PARTITION BY id_arrondissement) AS total_points
                FROM arrondissement_position
                WHERE id_arrondissement = arr_record.id_arrondissement
            )
            SELECT 
                c1.longitude AS x1,
                c1.latitude AS y1,
                NVL(c2.longitude, first_point.longitude) AS x2,
                NVL(c2.latitude, first_point.latitude) AS y2
            FROM ordered_coords c1
            LEFT JOIN ordered_coords c2 ON c1.rn = c2.rn - 1 
                AND c1.id_arrondissement = c2.id_arrondissement
            CROSS JOIN (
                SELECT longitude, latitude 
                FROM ordered_coords 
                WHERE rn = 1
            ) first_point
            WHERE c1.id_arrondissement = arr_record.id_arrondissement
            ORDER BY c1.rn
        ) LOOP
            -- Ray-casting algorithm
            IF ((coord_record.y1 > p_latitude) != (coord_record.y2 > p_latitude)) AND 
               (p_longitude < (coord_record.x2 - coord_record.x1) * 
               (p_latitude - coord_record.y1) / (coord_record.y2 - coord_record.y1) + 
               coord_record.x1) THEN
                v_intersections := v_intersections + 1;
            END IF;
        END LOOP;

        -- If odd number of intersections, point is inside polygon
        IF MOD(v_intersections, 2) = 1 THEN
            RETURN arr_record.id_arrondissement;
        END IF;
    END LOOP;

    RETURN v_result;
END;
/