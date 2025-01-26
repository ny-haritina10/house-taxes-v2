package mg.itu.model;

import java.sql.Timestamp;

public class HistoHouseCompMat {
    
    private String id;
    private String idHouseComposantMaterial;
    private double coefficient;
    private Timestamp changedAt;

    public HistoHouseCompMat() {}

    public HistoHouseCompMat(String id, String idHouseComposantMaterial, double coefficient, Timestamp changedAt) {
        this.id = id;
        this.idHouseComposantMaterial = idHouseComposantMaterial;
        this.coefficient = coefficient;
        this.changedAt = changedAt;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdHouseComposantMaterial() { return idHouseComposantMaterial; }
    public void setIdHouseComposantMaterial(String idHouseComposantMaterial) { this.idHouseComposantMaterial = idHouseComposantMaterial; }
    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }
    public Timestamp getChangedAt() { return changedAt; }
    public void setChangedAt(Timestamp changedAt) { this.changedAt = changedAt; }
}