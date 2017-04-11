/**
 * Created by sajith on 4/11/17.
 */
import java.sql.*;

public class DBCon {
    //JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/ebank";

    // Database credentals
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // STEP 2 : Register JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3 : Open a connection
            System.out.println("Connecting to Database...");
            conn = DriverManager.getConnection(DB_URL, "root", "");

            //STEP 4 : Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM employees";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5 : Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                String name = rs.getString("name");
                String position = rs.getString("position");

                // Display values
                System.out.print("name : " + name);
                System.out.println(" Position : " + position);
            }
            // STEP 6 : Clean-up environment
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            // Handle errors for Class.forName
        }
    }
}
