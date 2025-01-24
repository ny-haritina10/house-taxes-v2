package mg.itu.model;

import java.sql.Date;

public class PricePerM2 {
    
    double amount;
    Date datePrice;

    public PricePerM2()
    { }

    public PricePerM2(double amount, Date datePrice) {
        this.amount = amount;
        this.datePrice = datePrice;
    }

    public double getAmount() 
    { return amount; }

    public void setAmount(double amount) 
    { this.amount = amount; }

    public Date getDatePrice() 
    { return datePrice; }
    
    public void setDatePrice(Date datePrice) 
    { this.datePrice = datePrice; }
}