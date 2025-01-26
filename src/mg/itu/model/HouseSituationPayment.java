package mg.itu.model;

public class HouseSituationPayment {
    
    private House house;
    private int year;
    private double totalToPay;
    private double totalPayed;
    private double totalUnpayed;

    public HouseSituationPayment()
    { }

    public House getHouse() 
    { return house; }

    public void setHouse(House house) 
    { this.house = house; }

    public int getYear() 
    { return year; }

    public void setYear(int year) 
    { this.year = year; }

    public double getTotalToPay() 
    { return totalToPay; }

    public void setTotalToPay(double totalToPay) 
    { this.totalToPay = totalToPay; }

    public double getTotalPayed() 
    { return totalPayed; }

    public void setTotalPayed(double totalPayed) 
    { this.totalPayed = totalPayed; }

    public double getTotalUnpayed() 
    { return totalUnpayed; }

    public void setTotalUnpayed(double totalUnpayed) 
    { this.totalUnpayed = totalUnpayed; }
}