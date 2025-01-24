package mg.itu.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ClassMAPTable;

public class House extends ClassMAPTable {

    String id;
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

    public House(String id, String idArrondissement, String label, double width, double height, int nbrFloor, double longitude, double latitude) {
        this.id = id;
        this.idArrondissement = idArrondissement;
        this.label = label;
        this.width = width;
        this.height = height;
        this.nbrFloor = nbrFloor;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double calculateSurface(Connection connection, Date dateMin, Date dateMax) {
        double totalSurface = 0.0;
    
        String query = """
            SELECT 
                SUM(width * height) AS total_surface
            FROM (
                SELECT width, height
                FROM histo_house
                WHERE changed_at BETWEEN 
                    COALESCE(?, (SELECT MIN(changed_at) FROM histo_house)) 
                    AND 
                    COALESCE(?, (SELECT MAX(changed_at) FROM histo_house))
                    AND id_house = ?
                UNION ALL
                SELECT h.width, h.height
                FROM house h
                WHERE h.id = ? 
                AND NOT EXISTS (
                    SELECT 1 
                    FROM histo_house hh 
                    WHERE hh.id_house = h.id
                )
            ) combined_surfaces
        """;
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (dateMin != null) {
                preparedStatement.setTimestamp(1, new java.sql.Timestamp(dateMin.getTime()));
            } else {
                preparedStatement.setNull(1, java.sql.Types.TIMESTAMP);
            }
    
            if (dateMax != null) {
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(dateMax.getTime()));
            } else {
                preparedStatement.setNull(2, java.sql.Types.TIMESTAMP);
            }
    
            // Set the house ID for filtering
            String houseId = this.getId();
            preparedStatement.setString(3, houseId); // For histo_house
            preparedStatement.setString(4, houseId); // For house
    
            // Execute the query
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

    @Override
    public String[] getMotCles() {
        String[] motCles = { "id", "id_arrondissement", "label", "width", "height", "nbr_floor", "longitude", "latitude" };
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

    public String getIdArrondissement() {
        return idArrondissement;
    }

    public void setIdArrondissement(String idArrondissement) {
        this.idArrondissement = idArrondissement;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getNbrFloor() {
        return nbrFloor;
    }

    public void setNbrFloor(int nbrFloor) {
        this.nbrFloor = nbrFloor;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}