package mg.itu.model;

import bean.ClassMAPTable;

public class ArrondissementPosition extends ClassMAPTable {

    String id;
    String idArrondissement;
    double longitude;
    double latitude;
    String table = "arrondissement_position";

    public ArrondissementPosition() {
        super.setNomTable(table);
    }

    public ArrondissementPosition(String id, String idArrondissement, double longitude, double latitude) {
        this.id = id;
        this.idArrondissement = idArrondissement;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = { "id", "id_arrondissement", "longitude", "latitude" };
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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}