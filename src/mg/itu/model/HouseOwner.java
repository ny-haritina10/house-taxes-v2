package mg.itu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassMAPTable;

public class HouseOwner extends ClassMAPTable {
    
    String id;
    String name;
    String phone;

    public HouseOwner() {
        super.setNomTable("house_owner");
    }
    
    public HouseOwner(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

   public List<House> getHouses(Connection connection) {
        List<House> houses = new ArrayList<House>();
        String query = "SELECT * FROM house WHERE id_house_owner = ?";

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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving houses for owner.", e);
        }

        return houses;
    }

    public static HouseOwner findById(String id, String tableName, Connection connection) 
        throws SQLException 
    {
        HouseOwner houseOwner = null;
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    houseOwner = new HouseOwner(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving house owner by ID.", e);
        }

        return houseOwner;
    }
    
    @Override
    public String[] getMotCles() {
        String[] motCles = {"id", "name", "phone"};
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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}