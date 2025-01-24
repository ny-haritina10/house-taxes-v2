package mg.itu.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.User;
import mg.itu.database.Database;

@WebServlet("/controller/TraitementUser")
public class TraitementUser extends HttpServlet {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        try (Connection con = Database.getConnection()) {
            
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            User user = new User(con);
            User loggedInUser = user.login(username, password);

            if (loggedInUser != null) {
                
                req.getSession().setAttribute("user", loggedInUser);
                req.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(req,resp);
            } else {
                req.setAttribute("message", "Nom d'utilisateur ou mot de passe incorrect.");
                req.getRequestDispatcher("/WEB-INF/views/login/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        req.getRequestDispatcher("/WEB-INF/views/login/login.jsp").forward(req, resp);
    }
}
