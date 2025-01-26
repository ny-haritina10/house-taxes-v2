package mg.itu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassMAPTable;

public class Arrondissement extends ClassMAPTable {

    String id;
    String idCommune;
    String label;

    public Arrondissement() {
        super.setNomTable("arrondissement");
    }

    public Arrondissement(String id, String idCommune, String label) {
        this.id = id;
        this.idCommune = idCommune;
        this.label = label;
    }

    public List<House> getHouses(Connection connection) throws SQLException {
        List<House> houses = new ArrayList<>();
        String query = "SELECT * FROM house WHERE id_arrondissement = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        }

        return houses;
    }

    public static List<Arrondissement> getAll(Connection connection) throws SQLException {
        List<Arrondissement> arrondissements = new ArrayList<>();
        String query = "SELECT * FROM arrondissement ORDER BY id";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                Arrondissement arrondissement = new Arrondissement(
                    resultSet.getString("id"),
                    resultSet.getString("id_commune"),
                    resultSet.getString("label")
                );
                arrondissements.add(arrondissement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving arrondissements from the database.", e);
        }
    
        return arrondissements;
    }

    public List<ArrondissementSituationPayment> getArrondissementSituation(Connection connection, int year) 
        throws Exception 
    {
        List<ArrondissementSituationPayment> situations = new ArrayList<>();
        List<House> houses = getHouses(connection);

        for (House house : houses) {
            HouseSituationPayment houseSituation = house.getHouseSituation(connection, year);
            ArrondissementSituationPayment situation = new ArrondissementSituationPayment(
                this, 
                year,
                houseSituation.getTotalToPay(),
                houseSituation.getTotalPayed(),
                houseSituation.getTotalUnpayed()
            );
            situations.add(situation);
        }

        return situations;
    }

    public ArrondissementSituationPayment getTotalArrondissementSituation(Connection connection, int year) throws Exception {
        List<ArrondissementSituationPayment> situations = getArrondissementSituation(connection, year);

        double totalToPay = 0.0;
        double totalPayed = 0.0;
        double totalUnpayed = 0.0;

        // Aggregate the results
        for (ArrondissementSituationPayment situation : situations) {
            totalToPay += situation.getTotalToPay();
            totalPayed += situation.getTotalPayed();
            totalUnpayed += situation.getTotalUnpayed();
        }

        // Create a new ArrondissementSituationPayment object for the arrondissement
        ArrondissementSituationPayment totalSituation = new ArrondissementSituationPayment(
            this, 
            year,
            totalToPay,
            totalPayed,
            totalUnpayed
        );

        return totalSituation;
    }
    @Override
    public String[] getMotCles() {
        String[] motCles = { "id", "id_commune", "label" };
        return motCles;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String getTuppleID() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCommune() {
        return idCommune;
    }

    public void setIdCommune(String idCommune) {
        this.idCommune = idCommune;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}