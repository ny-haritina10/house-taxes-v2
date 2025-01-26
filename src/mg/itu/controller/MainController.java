package mg.itu.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.CGenUtil;
import mg.itu.database.Database;
import mg.itu.model.Facture;
import mg.itu.model.House;
import mg.itu.model.HouseComposantMaterial;

@WebServlet("controller/MainController")
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {   
        try (Connection connection = Database.getConnection()) {
            // int year = 2025; 
            // int month = 1; 

            // List<House> houses = House.getAll(connection);

            // for (House house : houses) {
            //     double totalCoefficient = house.calculateTotalCoefficientByPeriod(connection, year, month);
            //     System.out.println("Total Coefficient for House " + house.getId() + " in " + month + "/" + year + ": " + totalCoefficient);
            // }

            req.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(req, resp); 
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
        }  
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {

    }
}