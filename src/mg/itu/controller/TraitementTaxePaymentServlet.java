package mg.itu.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.database.Database;
import mg.itu.model.TaxePayment;

@WebServlet("controller/TraitementTaxePaymentServlet")
public class TraitementTaxePaymentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        try {
            // Utilisation de la classe Database pour obtenir la connexion
            connection = Database.getConnection();
            String action = request.getParameter("action");

            if (action != null) {
                switch (action) {
                    case "form":
                        try {
                            loadFormulaire(request, response, connection);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new ServletException(e);
                        }
                        break;
                    default:
                        try {
                            defaultView(request, response, connection);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new ServletException(e);
                        }
                        break;
                }
            } else {
                try {
                    defaultView(request, response, connection);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServletException(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void defaultView(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws Exception {
        // Récupération de tous les paiements de taxe
        List<TaxePayment> taxePayments = null;
        request.setAttribute("taxePayments", taxePayments);

        // Affichage de la page principale
        forwardRequest(request, response, "pages/taxe/taxePayments.jsp");
    }

    private void loadFormulaire(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws Exception {
        // Affichage du formulaire pour ajouter ou modifier un paiement
        forwardRequest(request, response, "/WEB-INF/views/taxe/formulaireTaxePayment.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
}
