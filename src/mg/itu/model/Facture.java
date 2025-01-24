package mg.itu.model;

import bean.ClassMAPTable;

public class Facture extends ClassMAPTable {
   String id;
   double totalSurface;
   int year;
   int month;
   double unitPrice;
   double coefficient;
   String idHouse;

   public Facture() {
       super.setNomTable("facture");
   }

   public Facture(String id, double totalSurface, int year, int month, 
                  double unitPrice, double coefficient, String idHouse) {
       this.id = id;
       this.totalSurface = totalSurface;
       this.year = year;
       this.month = month;
       this.unitPrice = unitPrice;
       this.coefficient = coefficient;
       this.idHouse = idHouse;
   }

   @Override
   public String[] getMotCles() {
       String[] motCles = {"id", "totalSurface", "year", "month", 
                            "unit_price", "coefficient", "id_house"};
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

   // Getters and Setters
   public String getId() { return id; }
   public void setId(String id) { this.id = id; }

   public double getTotalSurface() { return totalSurface; }
   public void setTotalSurface(double totalSurface) { this.totalSurface = totalSurface; }

   public int getYear() { return year; }
   public void setYear(int year) { this.year = year; }

   public int getMonth() { return month; }
   public void setMonth(int month) { this.month = month; }

   public double getUnitPrice() { return unitPrice; }
   public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

   public double getCoefficient() { return coefficient; }
   public void setCoefficient(double coefficient) { this.coefficient = coefficient; }

   public String getIdHouse() { return idHouse; }
   public void setIdHouse(String idHouse) { this.idHouse = idHouse; }
}