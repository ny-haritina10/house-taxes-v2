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
import bean.ClassMAPTable;
import mg.itu.database.Database;
import mg.itu.model.Arrondissement;
import mg.itu.model.ArrondissementPosition;
import mg.itu.model.Commune;
import mg.itu.model.Facture;
import mg.itu.model.House;
import mg.itu.model.HouseComposantMaterial;
import mg.itu.model.InsertBatch;

@WebServlet("controller/MainController")
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException 
    {   
        try (Connection connection = Database.getConnection()) {
            
            Commune commune1 = new Commune("3", "Commune A");
            Commune commune2 = new Commune("4", "Commune B");

            Arrondissement arrondissement1 = new Arrondissement("5", "3", "Arrondissement X");
            Arrondissement arrondissement2 = new Arrondissement("6", "4", "Arrondissement Y");

            ArrondissementPosition position = new ArrondissementPosition("77", "1", 77.0, 77.0);

            ClassMAPTable[] objects = { commune1, commune2, arrondissement1, arrondissement2, position };


            ClassMAPTable.insertBatch(objects, connection);
            req.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(req, resp); 
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error while inserting data", e);
        }  
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {

    }
}