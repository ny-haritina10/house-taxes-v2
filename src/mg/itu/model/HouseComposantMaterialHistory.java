package mg.itu.model;

public class HouseComposantMaterialHistory {

    private String idHouseComposantMaterial;
    private double coefficient;
    private int changeMonth;
    private int changeYear;

    public HouseComposantMaterialHistory() {
    }

    public HouseComposantMaterialHistory(String idHouseComposantMaterial, double coefficient, int changeMonth, int changeYear) {
        this.idHouseComposantMaterial = idHouseComposantMaterial;
        this.coefficient = coefficient;
        this.changeMonth = changeMonth;
        this.changeYear = changeYear;
    }

    // Getters and Setters
    public String getIdHouseComposantMaterial() {
        return idHouseComposantMaterial;
    }

    public void setIdHouseComposantMaterial(String idHouseComposantMaterial) {
        this.idHouseComposantMaterial = idHouseComposantMaterial;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getChangeMonth() {
        return changeMonth;
    }

    public void setChangeMonth(int changeMonth) {
        this.changeMonth = changeMonth;
    }

    public int getChangeYear() {
        return changeYear;
    }

    public void setChangeYear(int changeYear) {
        this.changeYear = changeYear;
    }

    @Override
    public String toString() {
        return "HouseComposantMaterialHistory{" +
                "idHouseComposantMaterial='" + idHouseComposantMaterial + '\'' +
                ", coefficient=" + coefficient +
                ", changeMonth=" + changeMonth +
                ", changeYear=" + changeYear +
                '}';
    }
}