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

@WebServlet("controller/MainController")
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {   
        try {
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

    }
}