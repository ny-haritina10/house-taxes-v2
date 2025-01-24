package mg.itu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private int idCommune;
    private String username;
    private String password;
    private Connection connection;

    // Constructeur par défaut
    public User(Connection connection) {
        this.connection = connection;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCommune() {
        return idCommune;
    }

    public void setIdCommune(int idCommune) {
        this.idCommune = idCommune;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Méthode pour authentifier un utilisateur
    public User login(String username, String password) throws SQLException {
        String query = "SELECT id, id_commune, user_name, user_password " +
                       "FROM users " +
                       "WHERE user_name = ? AND user_password = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password); // Assurez-vous que le mot de passe est bien haché avant la vérification

            rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(connection);
                user.setId(rs.getInt("id"));
                user.setIdCommune(rs.getInt("id_commune"));
                user.setUsername(rs.getString("user_name"));
                user.setPassword(rs.getString("user_password"));

                return user;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return null; // Retourne null si l'utilisateur n'est pas trouvé
    }

    

    

}
