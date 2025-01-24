package mg.itu.model;

import bean.ClassMAPTable;

public class User extends ClassMAPTable {

    String id;
    String idCommune;
    String userName;
    String userPassword;

    public User() {
        super.setNomTable("users");
    }

    public User(String id, String idCommune, String userName, String userPassword) {
        this.id = id;
        this.idCommune = idCommune;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = { "id", "id_commune", "user_name", "user_password" };
        return motCles;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String getTuppleID() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCommune() {
        return idCommune;
    }

    public void setIdCommune(String idCommune) {
        this.idCommune = idCommune;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}