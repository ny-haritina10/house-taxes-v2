package mg.itu.model;

import bean.ClassMAPTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Facture extends ClassMAPTable {

    String id;
    double totalSurface;
    int year;
    int month;
    double unitPrice;
    double coefficient;
    String idHouse;

    public Facture() {
        super.setNomTable("facture");
    }

    public Facture(String id, double totalSurface, int year, int month, 
                   double unitPrice, double coefficient, String idHouse) {
        this.id = id;
        this.totalSurface = totalSurface;
        this.year = year;
        this.month = month;
        this.unitPrice = unitPrice;
        this.coefficient = coefficient;
        this.idHouse = idHouse;
    }

    public Facture(double totalSurface, int year, int month, double unitPrice, double coefficient, String idHouse) {
        this.totalSurface = totalSurface;
        this.year = year;
        this.month = month;
        this.unitPrice = unitPrice;
        this.coefficient = coefficient;
        this.idHouse = idHouse;
    }

    public void insert(Connection connection) 
        throws SQLException 
    {
        String query = """
            INSERT INTO facture (totalSurface, year, month, unit_price, coefficient, id_house)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, this.totalSurface);
            preparedStatement.setInt(2, this.year);
            preparedStatement.setInt(3, this.month);
            preparedStatement.setDouble(4, this.unitPrice);
            preparedStatement.setDouble(5, this.coefficient);
            preparedStatement.setString(6, this.idHouse);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting facture into the database.", e);
        }
    }

    /**
     * Retrieves all Facture records from the database.
     *
     * @param con The database connection.
     * @return A list of Facture objects.
     * @throws SQLException If a database access error occurs.
     */
    public static List<Facture> getAll(Connection con) throws SQLException {
        List<Facture> factures = new ArrayList<>();
        String query = "SELECT * FROM facture";

        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Facture facture = new Facture(
                    resultSet.getString("id"),
                    resultSet.getDouble("totalSurface"),
                    resultSet.getInt("year"),
                    resultSet.getInt("month"),
                    resultSet.getDouble("unit_price"),
                    resultSet.getDouble("coefficient"),
                    resultSet.getString("id_house")
                );
                factures.add(facture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving factures from the database.", e);
        }

        return factures;
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = {"id", "totalSurface", "year", "month", 
                            "unit_price", "coefficient", "id_house"};
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

    @Override
    public String toString() {
        return "Facture{" +
                "id='" + id + '\'' +
                ", totalSurface=" + totalSurface +
                ", year=" + year +
                ", month=" + month +
                ", unitPrice=" + unitPrice +
                ", coefficient=" + coefficient +
                ", idHouse='" + idHouse + '\'' +
                '}';
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getTotalSurface() { return totalSurface; }
    public void setTotalSurface(double totalSurface) { this.totalSurface = totalSurface; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }

    public String getIdHouse() { return idHouse; }
    public void setIdHouse(String idHouse) { this.idHouse = idHouse; }
}