package mg.itu.model;

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