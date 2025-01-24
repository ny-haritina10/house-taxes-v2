package mg.itu.model;

import bean.ClassMAPTable;

public class Commune extends ClassMAPTable {

    String id;
    String label;

    public Commune() {
        super.setNomTable("commune");
    }

    public Commune(String id, String label) {
        this.id = id;
        this.label = label;
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = { "id","label" };
        return motCles;
    } 

    @Override
    public String getAttributIDName() 
    { return "id"; }

    @Override
    public String getTuppleID() 
    { return id; }
    
    public String getId() 
    { return id; }

    public void setId(String id) 
    { this.id = id; }

    public String getLabel() 
    { return label; }

    public void setLabel(String label) 
    { this.label = label; }
}