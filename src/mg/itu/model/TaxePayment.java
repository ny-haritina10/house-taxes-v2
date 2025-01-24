package mg.itu.model;

import bean.ClassMAPTable;
import java.sql.Date;

public class TaxePayment extends ClassMAPTable {

    String id;
    String idHouse;
    double amount;
    Date datePayment;

    public TaxePayment() {
        super.setNomTable("taxe_payment");
    }

    public TaxePayment(String id, String idHouse, double amount, Date datePayment) {
        this.id = id;
        this.idHouse = idHouse;
        this.amount = amount;
        this.datePayment = datePayment;
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = { "id", "id_house", "amount", "date_payment" };
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }
}