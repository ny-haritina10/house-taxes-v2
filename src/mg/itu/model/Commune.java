package mg.itu.model;

import bean.ClassMAPTable;

public class Commune extends ClassMAPTable {

    String id;
    String label;
    String table = "commune";

    public Commune() {
        super.setNomTable(table);
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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }   
}