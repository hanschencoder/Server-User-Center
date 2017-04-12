package site.hanschen.api.user.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author HansChen
 */
public class DatabaseManager {

    private static final String DRIVER_CLASS_NAME_5 = "com.mysql.jdbc.Driver";
    private static final String DRIVER_CLASS_NAME_6 = "com.mysql.cj.jdbc.Driver";

    private Connection connection;

    public DatabaseManager() {
        try {
            Class.forName(DRIVER_CLASS_NAME_6);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Where is MySQL JDBC Driver ?");
        }
    }

    /**
     * Attempts to establish a connection to the given database URL
     *
     * @param url      a database url of the form, example:jdbc:mysql://localhost:3306/test
     * @param user     the database user on whose behalf the connection is being made
     * @param password the user's password
     */
    public void connect(final String url, final String user, final String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {

        DatabaseManager manager = new DatabaseManager();
        try {
            manager.connect(args[0], args[1], args[2]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (manager.getConnection() != null) {
            System.out.println("Connect succeed !");
            try {
                manager.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Oops, connect failed !");
        }
    }

}
