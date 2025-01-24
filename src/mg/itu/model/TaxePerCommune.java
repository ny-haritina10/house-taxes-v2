package mg.itu.model;

import bean.ClassMAPTable;
import java.sql.Date;

public class TaxePerCommune extends ClassMAPTable {

    String id;
    String idCommune;
    Date dateTaxe;
    double amountPerM2;

    public TaxePerCommune() {
        super.setNomTable("taxe_per_commune");
    }

    public TaxePerCommune(String id, String idCommune, Date dateTaxe, double amountPerM2) {
        this.id = id;
        this.idCommune = idCommune;
        this.dateTaxe = dateTaxe;
        this.amountPerM2 = amountPerM2;
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = { "id", "id_commune", "date_taxe", "amount_per_m2" };
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

    public Date getDateTaxe() {
        return dateTaxe;
    }

    public void setDateTaxe(Date dateTaxe) {
        this.dateTaxe = dateTaxe;
    }

    public double getAmountPerM2() {
        return amountPerM2;
    }

    public void setAmountPerM2(double amountPerM2) {
        this.amountPerM2 = amountPerM2;
    }
}