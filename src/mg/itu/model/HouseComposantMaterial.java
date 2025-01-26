package mg.itu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassMAPTable;

public class HouseComposantMaterial extends ClassMAPTable {

    String id;
    String idHouseComposant;
    String idMaterial;
    double coefficient;

    public HouseComposantMaterial() {
        super.setNomTable("house_composant_material");
    }

    public HouseComposantMaterial(String id, String idHouseComposant, String idMaterial, double coefficient) {
        this.id = id;
        this.idHouseComposant = idHouseComposant;
        this.idMaterial = idMaterial;
        this.coefficient = coefficient;
    }

    public static List<HouseComposantMaterial> getAll(Connection connection) throws SQLException {
        List<HouseComposantMaterial> materials = new ArrayList<>();
        String query = "SELECT * FROM house_composant_material";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                HouseComposantMaterial material = new HouseComposantMaterial(
                    resultSet.getString("id"),
                    resultSet.getString("id_house_composant"),
                    resultSet.getString("id_material"),
                    resultSet.getDouble("coefficient")
                );
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving all house_composant_material records.", e);
        }
    
        return materials;
    }

    public static HouseComposantMaterial getById(Connection connection, String id) throws SQLException {
        String query = "SELECT * FROM house_composant_material WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new HouseComposantMaterial(
                        resultSet.getString("id"),
                        resultSet.getString("id_house_composant"),
                        resultSet.getString("id_material"),
                        resultSet.getDouble("coefficient")
                    );
                }
            }
        }
        return null;
    }

    public List<HistoHouseCompMat> getHistory(Connection connection) 
        throws SQLException 
    {
        List<HistoHouseCompMat> historyList = new ArrayList<>();
        String query = "SELECT * FROM histo_house_comp_mat WHERE id_house_composant_material = ? ORDER BY changed_at DESC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    historyList.add(new HistoHouseCompMat(
                        resultSet.getString("id"),
                        resultSet.getString("id_house_composant_material"),
                        resultSet.getDouble("coefficient"),
                        resultSet.getTimestamp("changed_at")
                    ));
                }
            }
        }
        return historyList;
    }

    public double getCoefficientByPeriod(Connection connection, int month, int year) 
        throws SQLException 
    {
        List<HistoHouseCompMat> history = getHistory(connection);

        for (HistoHouseCompMat update : history) {
            if (update.getChangedAt().toLocalDateTime().getYear() == year &&
                update.getChangedAt().toLocalDateTime().getMonthValue() <= month) {
                return update.getCoefficient();
            }
        }

        return this.getCoefficient();
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = { "id", "id_house_composant", "id_material", "coefficient" };
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

    public String getIdHouseComposant() {
        return idHouseComposant;
    }

    public void setIdHouseComposant(String idHouseComposant) {
        this.idHouseComposant = idHouseComposant;
    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}