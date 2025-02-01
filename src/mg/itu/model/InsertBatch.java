package mg.itu.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import bean.ClassMAPTable;


/*
* VAKIO TSARA FA TONGA DIA TSY MANDEHA VO TSY ATAO ITO ZAVATRA ITO . AZA ADINO MAMAFA ANITO REFA HITSARA
* Ny InsertBatch.java atao anaty model
* Ny MainController.java atao anaty controller 

* Ny model rehetra efa mi-extends ClassMAPTable daoly,  raha miteny izy oe mamorina classe vaovao 
* dia atao mi extends ClassMAPTable ilay izy dia fenoina daoly leh methods misy @Overidde reto : 
    @Override
    public String[] getMotCles() {
        // colonne an leh table
        String[] motCles = { "id", "id_arrondissement", "label", "width", "height", "nbr_floor", "longitude", "latitude", "id_house_owner" };
        return motCles;
    }
    
    @Override
    public String getAttributIDName() 
    { return "id"; }

    @Override
    public String getTuppleID() 
    { return id; }
* 
* Ao anaty classe rehetra ho testéna tsy maintsy mi-créé anito
* => String table = "soloina an leh nom table";
* Apetraka ao am attribut any classe rehetra ho testena io NB: TSY MAINTSY ATAO 
exemple : 
package mg.itu.model;

import bean.ClassMAPTable;

public class Commune extends ClassMAPTable {

    String id;
    String label;
    String table = "commune";

    public String getTable() {
        return table;
    }
... reste inchangé 

* Anontanio chat anazava an leh code raha misy tsy azo fa tsy afaka manazava za (mombany leh BATCH)
*/

public class InsertBatch {
    private static final int BATCH_SIZE = 1000; 
    
    public static void insertBatch(ClassMAPTable[] objects, Connection connection) throws Exception {
        if (objects == null || objects.length == 0) {
            throw new IllegalArgumentException("The objects array cannot be null or empty.");
        }
        
        PreparedStatement preparedStatement = null;
        String currentTable = null;
        String sql = null;
        int batchCount = 0;
        
        try {
            connection.setAutoCommit(false);
            
            for (int objIndex = 0; objIndex < objects.length; objIndex++) {
                ClassMAPTable obj = objects[objIndex];
                String tableName = getSimpleTableName(obj.getNomTable());
                String[] columns = obj.getMotCles();
                
                if (!tableName.equals(currentTable) || preparedStatement == null) {
                    if (preparedStatement != null && batchCount > 0) {
                        preparedStatement.executeBatch();
                        batchCount = 0;
                    }
                    
                    StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
                    StringBuilder values = new StringBuilder("VALUES (");
                    
                    for (int i = 0; i < columns.length; i++) {
                        if (i > 0) {
                            sqlBuilder.append(", ");
                            values.append(", ");
                        }
                        sqlBuilder.append(columns[i]);
                        values.append("?");
                    }
                    
                    sql = sqlBuilder.append(") ").append(values).append(")").toString();
                    
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    
                    preparedStatement = connection.prepareStatement(sql);
                    currentTable = tableName;
                }
                
                for (int i = 0; i < columns.length; i++) {
                    Object value = obj.getValeur(i);
                    
                    if (value == null) {
                        preparedStatement.setNull(i + 1, java.sql.Types.NULL);
                    } else if (value instanceof Integer || value.toString().matches("-?\\d+")) {
                        try {
                            preparedStatement.setInt(i + 1, Integer.parseInt(value.toString()));
                        } catch (NumberFormatException e) {
                            throw new SQLException("Invalid number format for column " + columns[i] + ": " + value);
                        }
                    } else if (value instanceof Double || value.toString().matches("-?\\d+(\\.\\d+)?")) {
                        try {
                            preparedStatement.setDouble(i + 1, Double.parseDouble(value.toString()));
                        } catch (NumberFormatException e) {
                            throw new SQLException("Invalid decimal format for column " + columns[i] + ": " + value);
                        }
                    } else {
                        preparedStatement.setString(i + 1, value.toString());
                    }
                }
                
                preparedStatement.addBatch();
                batchCount++;
                
                if (batchCount >= BATCH_SIZE || objIndex == objects.length - 1) {
                    preparedStatement.executeBatch();
                    batchCount = 0;
                }
            }
            
            connection.commit();
            
        } catch (SQLException e) {
            connection.rollback();
            throw new Exception("Error during batch insertion: " + e.getMessage(), e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            connection.setAutoCommit(true);
        }
    }
    
    public static String getSimpleTableName(String fullClassName) {
        String simpleName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);        
        return toSnakeCase(simpleName);
    }
    
    private static String toSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}