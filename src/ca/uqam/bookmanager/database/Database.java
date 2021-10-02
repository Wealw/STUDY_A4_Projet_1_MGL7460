package ca.uqam.bookmanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database
{
    
    private Connection database;
    
    public Database()
    {
        try
        {
            database = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        }
        catch (SQLException e)
        {
            System.out.println("There was an arror when connecting to the database please refer to the developer");
            System.exit(1);
        }
    }
    
    public Connection getDatabase()
    {
        return database;
    }
    
}
