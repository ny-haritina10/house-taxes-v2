package mg.itu.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.database.Database;
import mg.itu.model.House;
import mg.itu.model.HouseSituationPayment;

@WebServlet("controller/SituationPaymentController")
public class SituationPaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {
        String action = req.getParameter("action");

        if ("view".equals(action)) {
            try (Connection connection = Database.getConnection()) {
                int year = Integer.parseInt(req.getParameter("year"));

                List<House> houses = House.getAll(connection);
                List<HouseSituationPayment> situations = new ArrayList<>();
                
                for (House house : houses) {
                    HouseSituationPayment situation = house.getHouseSituation(connection, year);
                    situations.add(situation);
                }

                req.setAttribute("situations", situations);
                req.setAttribute("year", year);

                req.getRequestDispatcher("/WEB-INF/views/situation/situation-house.jsp").forward(req, resp);
            } 
            
            catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving payment situations.");
            }
        } 
        
        else {
            req.getRequestDispatcher("/WEB-INF/views/situation/situation-house.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {
        doGet(req, resp);
    }
}