package ca.uqam.bookmanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database class
 */
public class Database implements IDataSource {
    
    /**
     * Database driver instance.
     */
    private Connection database;
    
    /**
     * Initialize basic attribute of database class
     */
    public Database() {
        try {
            database = DriverManager.getConnection("jdbc:sqlite:database_test/database.sqlite");
        } catch (SQLException e) {
            System.out.println("There was an error when connecting to the database please refer to the developer");
            System.exit(1);
        }
    }
    
    /**
     * Return a database instance
     *
     * @return "Return database"
     */
    public Connection getDatabase() {
        return database;
    }
}
