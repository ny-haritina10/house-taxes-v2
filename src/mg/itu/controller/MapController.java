package mg.itu.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import mg.itu.database.Database;
import mg.itu.model.House;
import mg.itu.model.ArrondissementPosition;
import mg.itu.model.Arrondissement;

@WebServlet("controller/MapController")
public class MapController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException 
    {
        String action = req.getParameter("action");

        if ("map".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/open-street-map/map.jsp").forward(req, resp);
        } 

        else {
            try (Connection connection = Database.getConnection()) {
                List<House> houses = House.getAll(connection);

                List<ArrondissementPosition> arrondissementPositions = Arrondissement.getAllArrondissementPosition(connection);

                Map<String, List<ArrondissementPosition>> groupedPositions = new HashMap<>();
                for (ArrondissementPosition position : arrondissementPositions) {
                    String idArrondissement = position.getIdArrondissement();
                    groupedPositions.computeIfAbsent(idArrondissement, k -> new ArrayList<>()).add(position);
                }

                MapResponse response = new MapResponse(houses, groupedPositions);

                Gson gson = new Gson();
                String jsonResponse = gson.toJson(response);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write(jsonResponse);
            } 
            catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving data from the database.");
            }
        }
    }

    private static class MapResponse {
        private List<House> houses;
        private Map<String, List<ArrondissementPosition>> arrondissementPositions;

        public MapResponse(List<House> houses, Map<String, List<ArrondissementPosition>> arrondissementPositions) {
            this.houses = houses;
            this.arrondissementPositions = arrondissementPositions;
        }

        public List<House> getHouses() {
            return houses;
        }

        public Map<String, List<ArrondissementPosition>> getArrondissementPositions() {
            return arrondissementPositions;
        }
    }
}