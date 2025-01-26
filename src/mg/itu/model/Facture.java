package mg.itu.model;

import bean.ClassMAPTable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
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
    double monthlyAmountToPay;
    String isPayed;
    Date datePaymentFacture;

    public Facture() {
        super.setNomTable("facture");
    }

    public Facture(String id, double totalSurface, int year, int month, double unitPrice, double coefficient, String idHouse, double monthlyAmountToPay, String isPayed, Date datePaymentFacture) {
        this.id = id;
        this.totalSurface = totalSurface;
        this.year = year;
        this.month = month;
        this.unitPrice = unitPrice;
        this.coefficient = coefficient;
        this.idHouse = idHouse;
        this.monthlyAmountToPay = monthlyAmountToPay;
        this.isPayed = isPayed;
        this.datePaymentFacture = datePaymentFacture;
    }

    public Facture(double totalSurface, int year, int month, double unitPrice, double coefficient, String idHouse) {
        this.totalSurface = totalSurface;
        this.year = year;
        this.month = month;
        this.unitPrice = unitPrice;
        this.coefficient = coefficient;
        this.idHouse = idHouse;
    }

    public static double getMonthlyAmountToPayByPeriod(Connection connection, int year, int month, House house) 
        throws Exception
    {
        double amount = 0.00;

        PricePerM2 price = house.calculatePricePerM2(connection, year, month);
        double surface = house.calculateTotalSurface(connection, month, year);
        double coeff = house.calculateTotalCoefficientByPeriod(connection, year, month);

        amount += (price.getAmount() * surface * coeff);
        return amount;
    }

    public void setMonthlyAmountToPay(Connection connection) 
        throws Exception
    {
        double amount = 0.00;
        House house = House.findById(this.getIdHouse(), "house", connection);

        PricePerM2 price = house.calculatePricePerM2(connection, this.getYear(), this.getMonth());
        double surface = house.calculateTotalSurface(connection, this.getMonth(), this.getYear());
        double coeff = house.calculateTotalCoefficientByPeriod(connection, this.getYear(), this.getMonth());

        amount += (price.getAmount() * surface * coeff);

        this.monthlyAmountToPay = amount;
    }

    public double getMonthlyMonthlyAmountToPay()
    { return this.monthlyAmountToPay; }

    public void insert(Connection connection) 
        throws Exception 
    {
        String query = """
            INSERT INTO facture (totalSurface, year, month, unit_price, coefficient, id_house, monthly_amount_to_pay, is_payed, date_payment_facture)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        this.setMonthlyAmountToPay(connection);
        BigDecimal amountBD = new BigDecimal(this.getMonthlyMonthlyAmountToPay()).setScale(2, RoundingMode.HALF_UP);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, this.totalSurface);
            preparedStatement.setInt(2, this.year);
            preparedStatement.setInt(3, this.month);
            preparedStatement.setDouble(4, this.unitPrice);
            preparedStatement.setDouble(5, this.coefficient);
            preparedStatement.setString(6, this.idHouse);
            preparedStatement.setDouble(7, amountBD.doubleValue());

            preparedStatement.setString(8, "N");    // not payed by default
            preparedStatement.setDate(9, null);     // null by default

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting facture into the database.", e);
        }
    }

    public static Facture findById(String id, Connection connection) 
        throws SQLException 
    {
        Facture facture = null;
        String query = "SELECT * FROM facture WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    facture = new Facture(
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving facture by ID.", e);
        }

        return facture;
    }

    public static List<Facture> getAll(Connection con) throws SQLException {
        List<Facture> factures = new ArrayList<>();
        String query = "SELECT * FROM facture ORDER BY id";

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
                    resultSet.getString("id_house"),
                    resultSet.getDouble("monthly_amount_to_pay"),
                    resultSet.getString("is_payed"),
                    resultSet.getDate("date_payment_facture")
                );
                factures.add(facture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving factures from the database.", e);
        }

        return factures;
    }

    public void payFacture(Connection connection) throws SQLException {
        String sql = "UPDATE facture SET is_payed = 'Y', date_payment_facture = CURRENT_DATE WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, this.getId()); // Set the ID parameter
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) 
            { System.out.println("Facture with ID " + this.getId() + " has been marked as paid."); } 
            
            else 
            { System.out.println("No facture found with ID " + this.getId() + "."); }
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating facture payment status.", e);
        }
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = {"id", "totalSurface", "year", "month", 
                            "unit_price", "coefficient", "id_house", "monthly_amount_to_pay", "is_payed"};
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
            ", monthlyAmountToPay=" + monthlyAmountToPay +
            ", isPayed=" + isPayed +
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

    public String getIdHouse() { return idHouse; }
    public void setIdHouse(String idHouse) { this.idHouse = idHouse; }

    public double getCoefficient() 
    { return coefficient; }

    public void setCoefficient(double coeff)
    { this.coefficient = coeff; }

    public String getIsPayed() 
    { return isPayed; }

    public void setIsPayed(String isPayed) 
    { this.isPayed = isPayed; }

    public Date getDatePaymentFacture() 
    { return datePaymentFacture; }

    public void setDatePaymentFacture(Date datePaymentFacture)
    { this.datePaymentFacture = datePaymentFacture; }
}