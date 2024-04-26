
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/landforlife";
    private static final String USER = "root";
    private static final String Password = "";
    private static DBConnection instance;
    private Connection cnx;

    public DBConnection() {
        try {
            this.cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/landforlife", "root", "");
            System.out.println("Connected ");
        } catch (SQLException var2) {
            System.err.println("erreu:" + var2.getMessage());
        }

    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }

        return instance;
    }

    public Connection getCnx() {
        return this.cnx;
    }
}
