package mg.itu.model;

import bean.ClassMAPTable;

public class HouseCaracteristique extends ClassMAPTable {

    String id;
    String idHouse;
    String idHouseComposantMaterial;

    public HouseCaracteristique() {
        super.setNomTable("house_caracteristique");
    }

    public HouseCaracteristique(String id, String idHouse, String idHouseComposantMaterial) {
        this.id = id;
        this.idHouse = idHouse;
        this.idHouseComposantMaterial = idHouseComposantMaterial;
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = { "id", "id_house", "id_house_composant_material" };
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

    public String getIdHouse() {
        return idHouse;
    }

    public void setIdHouse(String idHouse) {
        this.idHouse = idHouse;
    }

    public String getIdHouseComposantMaterial() {
        return idHouseComposantMaterial;
    }

    public void setIdHouseComposantMaterial(String idHouseComposantMaterial) {
        this.idHouseComposantMaterial = idHouseComposantMaterial;
    }
}