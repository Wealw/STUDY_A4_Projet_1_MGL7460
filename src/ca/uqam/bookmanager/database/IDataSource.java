package ca.uqam.bookmanager.database;

import java.sql.Connection;

/**
 * Define what a database furnisher sould furnish
 */
public interface IDataSource {
    
    /**
     * Return a database instance
     *
     * @return Database instance
     */
    Connection getConnection();
}
