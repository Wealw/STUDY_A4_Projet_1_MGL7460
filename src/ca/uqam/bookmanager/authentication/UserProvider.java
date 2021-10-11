package ca.uqam.bookmanager.authentication;

import ca.uqam.bookmanager.database.IDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserProvider implements IUserProvider {
    
    private final IDataSource dataSource;
    
    public UserProvider(IDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public User createUser(String username, String passwordHash, UserRole role) {
        if (null != requestBookWithOneOutcome("SELECT * FROM User WHERE UserName ='" + username + "', Password_Hash ='" + passwordHash + "';")) {
            return null;
        }
        requestWithNoOutcome("INSERT INTO User (UserName, Password_Hash, Role) VALUES ('" + username + "','" + passwordHash + "','" + role.name() + "');");
        return requestBookWithOneOutcome("SELECT * FROM User WHERE UserName ='" + username + "', Password_Hash ='" + passwordHash + "';");
    }
    @Override
    public User[] readAllUser() {
        return RequestBookWithMultipleOutcome("SELECT * FROM USER;");
    }
    @Override
    public User readUser(int id) {
        return requestBookWithOneOutcome("SELECT * FROM User WHERE Id = " + id + ";");
    }
    @Override
    
    public User readUser(String username) {
        return requestBookWithOneOutcome("SELECT * FROM User WHERE UserName = '" + username + "';");
    }
    @Override
    public void updateUser(int id, String username, String passwordHash, UserRole role) {
        requestWithNoOutcome("UPDATE User SET UserName = '" + username + "', Password_Hash = '" + passwordHash + "', Role ='" + role.name() + "' WHERE Id =" + id + ";");
    }
    @Override
    public void deleteUser(int id) {
        requestWithNoOutcome("DELETE FROM User WHERE Id = " + id + ";");
    }
    @Override
    public User[] searchUserByUsername(String username) {
        return RequestBookWithMultipleOutcome("SELECT * FROM User WHERE UserName LIKE '" + username + "';");
    }
    @Override
    public User[] searchUserByRole(UserRole role) {
        return RequestBookWithMultipleOutcome("SELECT * FROM User WHERE Role = '" + role.name() + "';");
    }
    
    private User[] RequestBookWithMultipleOutcome(String sql) {
        try {
            Statement statement = dataSource.getDatabase()
                                            .createStatement();
            ResultSet       rs    = statement.executeQuery(sql);
            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs.getInt("Id"), rs.getString("UserName"), rs.getString("Password_Hash"), UserRole.valueOf(rs.getString("Role"))));
            }
            return users.toArray(new User[0]);
        } catch (SQLException e) {
            return new User[0];
        }
    }
    
    private User requestBookWithOneOutcome(String sql) {
        try {
            Statement statement = dataSource.getDatabase()
                                            .createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return new User(rs.getInt("Id"), rs.getString("UserName"), rs.getString("Password_Hash"), UserRole.valueOf(rs.getString("Role")));
            
        } catch (SQLException e) {
            return null;
        }
    }
    
    private void requestWithNoOutcome(String sql) {
        try {
            Statement statement = dataSource.getDatabase()
                                            .createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
        }
    }
    
}
