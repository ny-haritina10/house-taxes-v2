package mg.itu.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.CGenUtil;
import bean.ClassMAPTable;

public class House extends ClassMAPTable {

    String id;
    String idHouseOwner;
    String idArrondissement;
    String label;
    double width;
    double height;
    int nbrFloor;
    double longitude;
    double latitude;

    public House() {
        super.setNomTable("house");
    }

    public House(String id,  String idArrondissement, String idHouseOwner,String label, double width, double height, int nbrFloor, double longitude, double latitude) {
        this.id = id;
        this.idArrondissement = idArrondissement;
        this.idHouseOwner = idHouseOwner;
        this.label = label;
        this.width = width;
        this.height = height;
        this.nbrFloor = nbrFloor;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void edition(Connection connection, int year, int month) 
        throws Exception
    {
        PricePerM2 price = calculatePricePerM2(connection, year, month);
        double surface = calculateTotalSurface(connection, month, year);
        double coeff = calculateTotalCoeff(connection, year, month);

        Facture facture = new Facture(
            surface,
            year, 
            month, 
            price.getAmount(), 
            coeff,
            this.getId() 
        );

        facture.insert(connection);
    }

    public static List<House> getAll(Connection connection) throws SQLException {
        List<House> houses = new ArrayList<>();
        String query = "SELECT * FROM house ORDER BY id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                House house = new House(
                    resultSet.getString("id"),
                    resultSet.getString("id_arrondissement"),
                    resultSet.getString("id_house_owner"),
                    resultSet.getString("label"),
                    resultSet.getDouble("width"),
                    resultSet.getDouble("height"),
                    resultSet.getInt("nbr_floor"),
                    resultSet.getDouble("longitude"),
                    resultSet.getDouble("latitude")
                );
                houses.add(house);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving houses from the database.", e);
        }

        return houses;
    }

    public double calculateTotalCoeff(Connection connection, int year, int month) 
        throws Exception 
    {
        double totalCoefficient = 1.0; 
    
        String query = """
            WITH house_total_coefficients AS (
                SELECT 
                    hc.id_house,
                    EXP(SUM(LN(hcm.coefficient))) AS total_coefficient
                FROM 
                    house_caracteristique hc
                JOIN house_composant_material hcm ON hc.id_house_composant_material = hcm.id
                WHERE hc.id_house = ?
                GROUP BY hc.id_house
            )
            SELECT COALESCE(htc.total_coefficient, 1.0) AS total_coefficient
            FROM house_total_coefficients htc
            RIGHT JOIN house h ON h.id = htc.id_house
            WHERE h.id = ?
        """;
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId()); 
            preparedStatement.setString(2, this.getId()); 
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalCoefficient = resultSet.getDouble("total_coefficient");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving total coefficient.", e);
        }
    
        return totalCoefficient;
    }

    public PricePerM2 calculatePricePerM2(Connection connection, int year, int month) throws Exception {
        double amount = 0.0;
        Date datePrice = null;
    
        String query = """
            WITH relevant_history AS (
                SELECT amount_per_m2, changed_at,
                       ROW_NUMBER() OVER (PARTITION BY id_commune ORDER BY changed_at DESC) AS rn
                FROM histo_taxe_per_commune htc
                WHERE id_commune = ?
                AND changed_at <= TO_DATE(?, 'YYYY-MM-DD')
                AND EXISTS (
                    SELECT 1 
                    FROM house h 
                    WHERE h.id = ?
                    AND h.id_arrondissement = (
                        SELECT MIN(a.id) -- Ensure only one row is returned
                        FROM arrondissement a 
                        WHERE a.id_commune = htc.id_commune
                    )
                )
            ),
            fallback_to_taxe AS (
                SELECT amount_per_m2, date_taxe,
                       ROW_NUMBER() OVER (PARTITION BY id_commune ORDER BY amount_per_m2 DESC) AS rn
                FROM taxe_per_commune tpc
                WHERE tpc.id_commune = ?
                AND EXISTS (
                    SELECT 1 
                    FROM house h 
                    WHERE h.id = ?
                    AND h.id_arrondissement = (
                        SELECT MIN(a.id) -- Ensure only one row is returned
                        FROM arrondissement a 
                        WHERE a.id_commune = tpc.id_commune
                    )
                )
                AND NOT EXISTS (
                    SELECT 1 
                    FROM histo_taxe_per_commune htc 
                    WHERE htc.id_commune = tpc.id_commune
                )
            )
            SELECT COALESCE(
                (SELECT amount_per_m2 
                 FROM relevant_history 
                 WHERE rn = 1),
                (SELECT amount_per_m2 
                 FROM fallback_to_taxe 
                 WHERE rn = 1),
                0
            ) AS amount,
            COALESCE(
                (SELECT changed_at 
                 FROM relevant_history 
                 WHERE rn = 1),
                (SELECT date_taxe 
                 FROM fallback_to_taxe 
                 WHERE rn = 1),
                TO_DATE(?, 'YYYY-MM-DD') 
            ) AS date_price
            FROM dual                                                          
        """;
    
        Arrondissement arrondissement = (Arrondissement) new Arrondissement().getById(this.getIdArrondissement(), "arrondissement", connection);
        Commune commune = (Commune) new Commune().getById(arrondissement.getIdCommune(), "commune", connection);
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String targetDate = String.format("%04d-%02d-27", year, month); 

            preparedStatement.setString(1, commune.getId());
            preparedStatement.setString(2, targetDate); 
            preparedStatement.setString(3, this.getId()); 
            preparedStatement.setString(4, commune.getId()); 
            preparedStatement.setString(5, this.getId()); 
            preparedStatement.setString(6, targetDate); 
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    amount = resultSet.getDouble("amount");
                    datePrice = resultSet.getDate("date_price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving price per m2.", e);
        }
    
        if (amount == 0) {
            String sql = "SELECT amount_per_m2, date_taxe FROM taxe_per_commune WHERE id_commune = ?";
            try (PreparedStatement fallbackStatement = connection.prepareStatement(sql)) {
                fallbackStatement.setString(1, commune.getId());
                try (ResultSet fallbackResultSet = fallbackStatement.executeQuery()) {
                    if (fallbackResultSet.next()) {
                        amount = fallbackResultSet.getDouble("amount_per_m2");
                        datePrice = fallbackResultSet.getDate("date_taxe");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error retrieving fallback price per m2.", e);
            }
        }
    
        return new PricePerM2(amount, datePrice);
    }

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
    }

    public Commune getCommune(Connection connection) {
        Commune commune = null;
        String query = "SELECT commune_id, commune_label FROM house_commune_view WHERE house_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    commune = new Commune(
                        resultSet.getString("commune_id"),
                        resultSet.getString("commune_label")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving commune for house.", e);
        }

        return commune;
    }

    public List<Facture> getFactureByMonthYear(Connection connection, int year, int month) {
        List<Facture> factures = new ArrayList<>();
        String query = "SELECT * FROM house_invoice_simple WHERE house_id = ? AND year = ? AND month = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId()); 
            preparedStatement.setInt(2, year); 
            preparedStatement.setInt(3, month); 

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Facture facture = new Facture(
                        resultSet.getString("facture_id"),
                        resultSet.getDouble("total_surface"),
                        resultSet.getInt("year"),
                        resultSet.getInt("month"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getDouble("coefficient"),
                        resultSet.getString("house_id"),
                        resultSet.getDouble("monthly_amount_to_pay"),
                        resultSet.getString("isPayed"),
                        resultSet.getDate("date_payment_facture")
                    );

                    factures.add(facture);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des factures.", e);
        }

        return factures;
    }

    public Arrondissement getArrondissement(Connection connection) {
        Arrondissement arrondissement = null;
        String query = """
            SELECT 
                arrondissement_id, 
                arrondissement_label, 
                commune_id 
            FROM 
                house_arrondissement_view 
            WHERE 
                house_id = ?
        """;
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId()); // ID de la maison
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    arrondissement = new Arrondissement(
                        resultSet.getString("arrondissement_id"),
                        resultSet.getString("commune_id"),
                        resultSet.getString("arrondissement_label")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de l'arrondissement.", e);
        }
    
        return arrondissement;
    }

    public static House findById(String id, String tableName, Connection connection) 
        throws SQLException 
    {
        House house = null;
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    house = new House(
                        resultSet.getString("id"),
                        resultSet.getString("id_arrondissement"),
                        resultSet.getString("id_house_owner"),
                        resultSet.getString("label"),
                        resultSet.getDouble("width"),
                        resultSet.getDouble("height"),
                        resultSet.getInt("nbr_floor"),
                        resultSet.getDouble("longitude"),
                        resultSet.getDouble("latitude")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving house by ID.", e);
        }

        return house;
    }
    
    public HouseOwner getProprietaire(Connection connection) 
        throws Exception
    {
        return HouseOwner.findById(this.getIdHouseOwner(), "house_owner", connection);
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = { "id", "id_arrondissement", "label", "width", "height", "nbr_floor", "longitude", "latitude", "id_house_owner" };
        return motCles;
    }
    
    @Override
    public String getAttributIDName() 
    { return "id"; }

    @Override
    public String getTuppleID() 
    { return id; }

    @Override
    public String toString() {
        return "House{" +
                "id='" + id + '\'' +
                ", idHouseOwner='" + idHouseOwner + '\'' +
                ", idArrondissement='" + idArrondissement + '\'' +
                ", label='" + label + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", nbrFloor=" + nbrFloor +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public String getId() 
    { return id; }

    public void setId(String id) 
    { this.id = id; }

    public String getIdArrondissement() 
    { return idArrondissement; }

    public void setIdArrondissement(String idArrondissement) 
    { this.idArrondissement = idArrondissement; }

    public String getLabel() 
    { return label; }

    public void setLabel(String label) 
    { this.label = label; }

    public double getWidth() 
    { return width; }

    public void setWidth(double width) 
    { this.width = width; }

    public double getHeight() 
    { return height; }

    public void setHeight(double height) 
    { this.height = height; }

    public int getNbrFloor() 
    { return nbrFloor; }

    public void setNbrFloor(int nbrFloor) 
    { this.nbrFloor = nbrFloor; }

    public double getLongitude() 
    { return longitude; }

    public void setLongitude(double longitude) 
    { this.longitude = longitude; }

    public double getLatitude() 
    { return latitude; }

    public void setLatitude(double latitude) 
    { this.latitude = latitude; }

    public String getIdHouseOwner() {
        return idHouseOwner;
    }

    public void setIdHouseOwner(String idHouseOwner) {
        this.idHouseOwner = idHouseOwner;
    }
}