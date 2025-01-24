package mg.itu.model;

import bean.ClassMAPTable;

public class Arrondissement extends ClassMAPTable {

    String id;
    String idCommune;
    String label;

    public Arrondissement() {
        super.setNomTable("arrondissement");
    }

    public Arrondissement(String id, String idCommune, String label) {
        this.id = id;
        this.idCommune = idCommune;
        this.label = label;
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = { "id", "id_commune", "label" };
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

    public String getIdCommune() {
        return idCommune;
    }

    public void setIdCommune(String idCommune) {
        this.idCommune = idCommune;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}