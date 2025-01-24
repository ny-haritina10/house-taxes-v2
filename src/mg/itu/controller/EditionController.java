package mg.itu.controller;

import java.io.IOException;
import java.sql.Connection;
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

@WebServlet("controller/EditionController")
public class EditionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {   
        String action = req.getParameter("action");

        if ("details".equals(action)) {
            String id = req.getParameter("id");

            try (Connection connection = Database.getConnection()) {
                Facture facture = (Facture) new Facture().getById(id, "facture", connection);

                req.setAttribute("facture", facture);
                req.getRequestDispatcher("/WEB-INF/views/edition/details.jsp").forward(req, resp);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try (Connection con = Database.getConnection()) {
                req.getRequestDispatcher("/WEB-INF/views/edition/form.jsp").forward(req, resp);  
            } 
            catch (Exception e) {
                e.printStackTrace();
            }  
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {
        try (Connection connection = Database.getConnection()) {
            int year = Integer.valueOf(req.getParameter("year"));
            int month = Integer.valueOf(req.getParameter("month"));

            House[] houses = (House[]) CGenUtil.rechercher(
                new House(),
                null,
                null,
                connection,
                " "
            );

            for (House house : houses) {
                house.edition(connection, year, month);
            }

            List<Facture> factures = Facture.getAll(connection);

            req.setAttribute("factures", factures);
            req.getRequestDispatcher("/WEB-INF/views/edition/list.jsp").forward(req, resp);  
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
    }
}