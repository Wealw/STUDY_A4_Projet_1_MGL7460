package ca.uqam.bookmanager.database;

import java.sql.Connection;

public interface IDataSource {
    
    Connection getDatabase();
    
}
