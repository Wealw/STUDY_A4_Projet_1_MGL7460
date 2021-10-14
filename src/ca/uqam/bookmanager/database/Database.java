package ca.uqam.bookmanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database class
 */
@SuppressWarnings ("PMD.SystemPrintln")
public class Database implements IDataSource {
    
    /**
     * Database driver instance.
     */
    private Connection connection;
    
    /**
     * Initialize basic attribute of database class
     */
    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./database.sqlite");
        } catch (SQLException e) {
            System.out.println("There was an error when connecting to the database please refer to the developer");
            throw new RuntimeException("There was an error when connecting to the database please refer to the developer");
        }
    }
    
    /**
     * Return a database instance
     *
     * @return "Return database"
     */
    public Connection getConnection() {
        return connection;
    }
}
