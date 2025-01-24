package mg.itu.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.CGenUtil;
import mg.itu.database.Database;
import mg.itu.model.Facture;
import mg.itu.model.House;
import mg.itu.model.PricePerM2;

@WebServlet("controller/MainController")
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {   
        try (Connection con = Database.getConnection()) {
            House[] houses = (House[]) CGenUtil.rechercher(
                new House(),
                null,
                null,
                con,
                ""
            );

            for (House house : houses) {
                System.out.println("h-" + house.getId() + " = " + house.calculateSurface(con, 1, 2025));
                PricePerM2 pricePerM2 = house.getPricePerM2(con, 2025, 1);

                System.out.println("Amount: " + pricePerM2.getAmount());
                System.out.println("Date: " + pricePerM2.getDatePrice());

                // Facture facture = new Facture(
                //     house.calculateSurface(con, 1, 2025), 
                //     2025, 
                //     1, 
                //     pricePerM2.getAmount(), 
                //     1.2, // hard coded
                //     house.getId() 
                // );

                // facture.insert(con);
                // System.out.println("Facture inserted for house: " + house.getId());

                double totalCoefficient = house.getCoeff(con, 2025, 1);
                System.out.println("Total coefficient: " + totalCoefficient);
            }

            req.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(req, resp);  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }  
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {
        // Handle POST requests if needed
    }
}