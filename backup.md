
/*    
    - user 
        - Commune
        - au moment Login
            - put in session

    - 12 trano 
        - facturéMaison() edition
        - getCommune
        - getArrondissement
        - getProprietaire
        - getCaractéristique()
        - getCoeff()

    - 12 proprietaires
        - getMaisons()

    - 12 factures : 
        - historique: 
            - surface total
            - pu (temporelle, lieu)
                - date
                - amount
                - commune
                - mois, annee
            - coeff

    - edition
        - generer factures pour proprio

    - structure code 
        - OOP
*/






```
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


CREATE TABLE house_composant (
    id NUMBER PRIMARY KEY,
    label VARCHAR2(255) NOT NULL
);


CREATE TABLE material (
    id NUMBER PRIMARY KEY,
    label VARCHAR2(255) NOT NULL
);


CREATE TABLE house_composant_material (
    id NUMBER PRIMARY KEY,
    id_house_composant NUMBER NOT NULL,
    id_material NUMBER NOT NULL,
    coefficient NUMBER(10, 5) NOT NULL,
    CONSTRAINT fk_house_composant_mc FOREIGN KEY (id_house_composant) REFERENCES house_composant(id),
    CONSTRAINT fk_house_composant_mm FOREIGN KEY (id_material) REFERENCES material(id)
);



CREATE TABLE house_caracteristique (
    id NUMBER PRIMARY KEY,
    id_house NUMBER NOT NULL,
    id_house_composant_material NUMBER NOT NULL,
    CONSTRAINT fk_house_caracteristique_h FOREIGN KEY (id_house) REFERENCES house(id),
    CONSTRAINT fk_house_caracteristique_cm FOREIGN KEY (id_house_composant_material) REFERENCES house_composant_material(id)
);

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
```


The house_composant_material table stores the initial coefficients of the house components with materials.
The histo_house_comp_mat table keeps track of coefficient updates along with their timestamps.

If a coefficient has been updated (based on a given month and year), the view should return the most recent coefficient for that house_composant_material as of the given time (latest update before or during the given month/year).
If there is no update for the given time, it should return the coefficient from the house_composant_material table.

The goal is to create a method that returns the appropriate coefficient for a given house_composant_material and time (month/year), considering updates in the histo_house_comp_mat table.


<!-- 
// public PricePerM2 calculatePricePerM2(Connection connection, int year, int month) throws Exception {
    //     double amount = 0.0;
    //     Date datePrice = null;
    
    //     String query = """
    //         WITH relevant_history AS (
    //             SELECT amount_per_m2, changed_at,
    //                    ROW_NUMBER() OVER (PARTITION BY id_commune ORDER BY changed_at DESC) AS rn
    //             FROM histo_taxe_per_commune htc
    //             WHERE id_commune = ?
    //             AND changed_at <= TO_DATE(?, 'YYYY-MM-DD')
    //             AND EXISTS (
    //                 SELECT 1 
    //                 FROM house h 
    //                 WHERE h.id = ?
    //                 AND h.id_arrondissement = (
    //                     SELECT MIN(a.id) -- Ensure only one row is returned
    //                     FROM arrondissement a 
    //                     WHERE a.id_commune = htc.id_commune
    //                 )
    //             )
    //         ),
    //         fallback_to_taxe AS (
    //             SELECT amount_per_m2, date_taxe,
    //                    ROW_NUMBER() OVER (PARTITION BY id_commune ORDER BY amount_per_m2 DESC) AS rn
    //             FROM taxe_per_commune tpc
    //             WHERE tpc.id_commune = ?
    //             AND EXISTS (
    //                 SELECT 1 
    //                 FROM house h 
    //                 WHERE h.id = ?
    //                 AND h.id_arrondissement = (
    //                     SELECT MIN(a.id) 
    //                     FROM arrondissement a 
    //                     WHERE a.id_commune = tpc.id_commune
    //                 )
    //             )
    //             AND NOT EXISTS (
    //                 SELECT 1 
    //                 FROM histo_taxe_per_commune htc 
    //                 WHERE htc.id_commune = tpc.id_commune
    //             )
    //         )
    //         SELECT COALESCE(
    //             (SELECT amount_per_m2 
    //              FROM relevant_history 
    //              WHERE rn = 1),
    //             (SELECT amount_per_m2 
    //              FROM fallback_to_taxe 
    //              WHERE rn = 1),
    //             0
    //         ) AS amount,
    //         COALESCE(
    //             (SELECT changed_at 
    //              FROM relevant_history 
    //              WHERE rn = 1),
    //             (SELECT date_taxe 
    //              FROM fallback_to_taxe 
    //              WHERE rn = 1),
    //             TO_DATE(?, 'YYYY-MM-DD') 
    //         ) AS date_price
    //         FROM dual                                                          
    //     """;
    
    //     Arrondissement arrondissement = (Arrondissement) new Arrondissement().getById(this.getIdArrondissement(), "arrondissement", connection);
    //     Commune commune = (Commune) new Commune().getById(arrondissement.getIdCommune(), "commune", connection);
    
    //     try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    //         String targetDate = String.format("%04d-%02d-27", year, month); 

    //         preparedStatement.setString(1, commune.getId());
    //         preparedStatement.setString(2, targetDate); 
    //         preparedStatement.setString(3, this.getId()); 
    //         preparedStatement.setString(4, commune.getId()); 
    //         preparedStatement.setString(5, this.getId()); 
    //         preparedStatement.setString(6, targetDate); 
    
    //         try (ResultSet resultSet = preparedStatement.executeQuery()) {
    //             if (resultSet.next()) {
    //                 amount = resultSet.getDouble("amount");
    //                 datePrice = resultSet.getDate("date_price");
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         throw new RuntimeException("Error retrieving price per m2.", e);
    //     }
    
    //     if (amount == 0) {
    //         String sql = "SELECT amount_per_m2, date_taxe FROM taxe_per_commune WHERE id_commune = ?";
    //         try (PreparedStatement fallbackStatement = connection.prepareStatement(sql)) {
    //             fallbackStatement.setString(1, commune.getId());
    //             try (ResultSet fallbackResultSet = fallbackStatement.executeQuery()) {
    //                 if (fallbackResultSet.next()) {
    //                     amount = fallbackResultSet.getDouble("amount_per_m2");
    //                     datePrice = fallbackResultSet.getDate("date_taxe");
    //                 }
    //             }
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //             throw new RuntimeException("Error retrieving fallback price per m2.", e);
    //         }
    //     }
    
    //     return new PricePerM2(amount, datePrice);
    // } -->

<!-- 

    public double calculateTotalSurface(Connection connection, int month, int year) {
        double totalSurface = 0.0;

        String query = """
            WITH relevant_history AS (
                SELECT width, height, changed_at
                FROM histo_house hh
                WHERE id_house = ?
                AND changed_at <= TO_DATE(?, 'YYYY-MM-DD') 
                ORDER BY changed_at DESC
            ),
            limited_history AS (
                SELECT width, height
                FROM (
                    SELECT width, height, ROW_NUMBER() OVER (ORDER BY changed_at DESC) AS rn
                    FROM relevant_history
                )
                WHERE rn = 1  
            ),
            fallback_to_house AS (
                SELECT width, height
                FROM house h
                WHERE h.id = ?
                AND NOT EXISTS (
                    SELECT 1 FROM histo_house hh WHERE hh.id_house = h.id
                )
            )
            SELECT SUM(width * height) AS total_surface
            FROM (
                SELECT width, height FROM limited_history
                UNION ALL
                SELECT width, height FROM fallback_to_house
            )
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId()); 
            String targetDate = String.format("%04d-%02d-27", year, month); 
            preparedStatement.setString(2, targetDate); 
            preparedStatement.setString(3, this.getId()); 

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalSurface = resultSet.getDouble("total_surface");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error calculating total surface area.", e);
        }

        return totalSurface;
    } -->



