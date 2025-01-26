package mg.itu.model;

public class ArrondissementSituationPayment {

    private Arrondissement arrondissement;
    private int year;
    private double totalToPay;
    private double totalPayed;
    private double totalUnpayed;

    public ArrondissementSituationPayment() { }

    public ArrondissementSituationPayment(Arrondissement arrondissement, int year, double totalToPay, double totalPayed, double totalUnpayed) {
        this.arrondissement = arrondissement;
        this.year = year;
        this.totalToPay = totalToPay;
        this.totalPayed = totalPayed;
        this.totalUnpayed = totalUnpayed;
    }

    public Arrondissement getArrondissement() { return arrondissement; }
    public void setArrondissement(Arrondissement arrondissement) { this.arrondissement = arrondissement; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getTotalToPay() { return totalToPay; }
    public void setTotalToPay(double totalToPay) { this.totalToPay = totalToPay; }

    public double getTotalPayed() { return totalPayed; }
    public void setTotalPayed(double totalPayed) { this.totalPayed = totalPayed; }

    public double getTotalUnpayed() { return totalUnpayed; }
    public void setTotalUnpayed(double totalUnpayed) { this.totalUnpayed = totalUnpayed; }
}