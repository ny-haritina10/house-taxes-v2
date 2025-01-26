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
    
    public HouseSituationPayment getHouseSituation(Connection connection, int year)
        throws Exception 
    {
        List<Facture> yearlyFactures = getFactureByYear(connection, year);

        double payedAmount = 0.00;  
        double totalYearly = 0;

        for (int i = 1; i < 13; i++) {
            totalYearly += (Facture.getMonthlyAmountToPayByPeriod(connection, year, i, this));
        }

        for (Facture facture : yearlyFactures) {
            if (facture.getIsPayed().equals("Y")) {
                payedAmount += facture.getMonthlyMonthlyAmountToPay();
            }
        }

        HouseSituationPayment situation = new HouseSituationPayment();
        
        situation.setHouse(this);
        situation.setTotalPayed(payedAmount);
        situation.setTotalUnpayed(totalYearly - payedAmount);
        situation.setTotalToPay(totalYearly);

        return situation;
    }

    public void edition(Connection connection, int year, int month) 
        throws Exception
    {
        PricePerM2 price = calculatePricePerM2(connection, year, month);
        double surface = calculateTotalSurface(connection, month, year);
        double coeff = calculateTotalCoefficientByPeriod(connection, year, month);

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

    public double calculateTotalCoefficientByPeriod(Connection connection, int year, int month) 
        throws SQLException 
    {
        List<HouseComposantMaterial> materials = getHouseComposantMaterials(connection);

        double totalCoefficient = 1.0;

        for (HouseComposantMaterial material : materials) {
            double coefficient = material.getCoefficientByPeriod(connection, month, year);
            totalCoefficient *= coefficient;
        }

        return totalCoefficient;
    }

    public PricePerM2 calculatePricePerM2(Connection connection, int year, int month) 
        throws Exception 
    {
        Arrondissement arrondissement = (Arrondissement) new Arrondissement().getById(this.getIdArrondissement(), "arrondissement", connection);
        Commune commune = (Commune) new Commune().getById(arrondissement.getIdCommune(), "commune", connection);
    
        Double amount = getMostRecentPricePerM2FromHistory(connection, commune.getId(), year, month);
    
        if (amount == null || amount == 0) {
            amount = getFallbackPricePerM2(connection, commune.getId());
        }
    
        Date datePrice = getPriceDate(connection, commune.getId(), year, month);
    
        if (datePrice == null) {
            String targetDate = String.format("%04d-%02d-27", year, month);
            datePrice = Date.valueOf(targetDate);
        }
    
        return new PricePerM2(amount, datePrice);
    }
    
    public double calculateTotalSurface(Connection connection, int month, int year) 
        throws SQLException 
    {
        double[] dimensions = getMostRecentDimensionsFromHistory(connection, year, month);
    
        if (dimensions == null) {
            dimensions = getFallbackDimensions(connection);
        }
    
        double width = dimensions[0];
        double height = dimensions[1];
        double nbrFloor = dimensions[2];

        return width * height * nbrFloor;
    }
    
    private double[] getMostRecentDimensionsFromHistory(Connection connection, int year, int month) throws SQLException {
        String query = """
            SELECT width, height, nbr_floor
            FROM (
                SELECT width, height, nbr_floor, changed_at
                FROM histo_house
                WHERE id_house = ?
                AND changed_at <= TO_DATE(?, 'YYYY-MM-DD')
                ORDER BY changed_at DESC
            )
            WHERE ROWNUM = 1
        """;
    
        String targetDate = String.format("%04d-%02d-27", year, month);
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId());
            preparedStatement.setString(2, targetDate);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double width = resultSet.getDouble("width");
                    double height = resultSet.getDouble("height");
                    double nbrFloor = resultSet.getDouble("nbr_floor");

                    return new double[] { width, height, nbrFloor};
                }
            }
        }
    
        return null;
    }
    
    private double[] getFallbackDimensions(Connection connection) throws SQLException {
        String query = "SELECT width, height, nbr_floor FROM house WHERE id = ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId());
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double width = resultSet.getDouble("width");
                    double height = resultSet.getDouble("height");
                    double nbrFloor = resultSet.getDouble("nbr_floor");

                    return new double[] { width, height, nbrFloor};
                }
            }
        }
    
        throw new SQLException("No dimensions found for house with ID: " + this.getId());
    }

    private Double getMostRecentPricePerM2FromHistory(Connection connection, String communeId, int year, int month) 
        throws SQLException 
    {
        String query = """
            SELECT amount_per_m2, changed_at
            FROM (
                SELECT amount_per_m2, changed_at
                FROM histo_taxe_per_commune
                WHERE id_commune = ?
                AND changed_at <= TO_DATE(?, 'YYYY-MM-DD')
                ORDER BY changed_at DESC
            )
            WHERE ROWNUM = 1
        """;
    
        String targetDate = String.format("%04d-%02d-27", year, month);
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, communeId);
            preparedStatement.setString(2, targetDate);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("amount_per_m2");
                }
            }
        }
    
        return null;
    }
    
    private Double getFallbackPricePerM2(Connection connection, String communeId) throws SQLException {
        String query = "SELECT amount_per_m2 FROM taxe_per_commune WHERE id_commune = ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, communeId);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("amount_per_m2");
                }
            }
        }
    
        return 0.0;
    }
    
    private Date getPriceDate(Connection connection, String communeId, int year, int month) throws SQLException {
        String query = """
            SELECT changed_at
            FROM (
                SELECT changed_at
                FROM histo_taxe_per_commune
                WHERE id_commune = ?
                AND changed_at <= TO_DATE(?, 'YYYY-MM-DD')
                ORDER BY changed_at DESC
            )
            WHERE ROWNUM = 1
        """;
    
        String targetDate = String.format("%04d-%02d-27", year, month);
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, communeId);
            preparedStatement.setString(2, targetDate);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDate("changed_at");
                }
            }
        }
    
        String fallbackQuery = "SELECT date_taxe FROM taxe_per_commune WHERE id_commune = ?";
        try (PreparedStatement fallbackStatement = connection.prepareStatement(fallbackQuery)) {
            fallbackStatement.setString(1, communeId);
    
            try (ResultSet fallbackResultSet = fallbackStatement.executeQuery()) {
                if (fallbackResultSet.next()) {
                    return fallbackResultSet.getDate("date_taxe");
                }
            }
        }
    
        return null;
    }

    private List<HouseComposantMaterial> getHouseComposantMaterials(Connection connection) throws SQLException {
        List<HouseComposantMaterial> materials = new ArrayList<>();
        String query = """
            SELECT hcm.* 
            FROM house_caracteristique hc
            JOIN house_composant_material hcm ON hc.id_house_composant_material = hcm.id
            WHERE hc.id_house = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    HouseComposantMaterial material = new HouseComposantMaterial(
                        resultSet.getString("id"),
                        resultSet.getString("id_house_composant"),
                        resultSet.getString("id_material"),
                        resultSet.getDouble("coefficient")
                    );
                    materials.add(material);
                }
            }
        }

        return materials;
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

    public List<Facture> getFactureByYear(Connection connection, int year) {
        List<Facture> factures = new ArrayList<>();
        String query = "SELECT * FROM facture WHERE ID_HOUSE = ? AND YEAR = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId()); 
            preparedStatement.setInt(2, year); 

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Facture facture = new Facture(
                        resultSet.getString("id"),
                        resultSet.getDouble("totalSurface"),
                        resultSet.getInt("year"),
                        resultSet.getInt("month"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getDouble("coefficient"),
                        resultSet.getString("id_house"),
                        resultSet.getDouble("monthly_amount_to_pay"),
                        resultSet.getString("is_payed"),
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