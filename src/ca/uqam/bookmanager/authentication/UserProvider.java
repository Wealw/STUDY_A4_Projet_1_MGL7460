package ca.uqam.bookmanager.authentication;

import ca.uqam.bookmanager.database.IDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Provide user and user list to other class
 */
class UserProvider implements IUserProvider {
    
    /**
     * Datasource.
     */
    private final IDataSource dataSource;
    
    /**
     * Define basic attribute for the user provider class.
     *
     * @param dataSource Datasources
     */
    public UserProvider(IDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * Create a user.
     *
     * @param username     User username
     * @param passwordHash User password hash
     * @param role         User role
     * @return Returns user
     */
    @Override
    public User createUser(String username, String passwordHash, UserRole role) {
        if (null != requestBookWithOneOutcome("SELECT * FROM User WHERE UserName ='" + username + "', Password_Hash ='" + passwordHash + "';")) {
            return null;
        }
        requestWithNoOutcome("INSERT INTO User (UserName, Password_Hash, Role) VALUES ('" + username + "','" + passwordHash + "','" + role.name() + "');");
        return requestBookWithOneOutcome("SELECT * FROM User WHERE UserName ='" + username + "', Password_Hash ='" + passwordHash + "';");
    }
    
    /**
     * @return Returns user list
     */
    @Override
    public User[] readAllUser() {
        return RequestBookWithMultipleOutcome("SELECT * FROM USER;");
    }
    
    /**
     * Search for user by id.
     *
     * @param id User id
     * @return Returns user
     */
    @Override
    public User readUser(int id) {
        return requestBookWithOneOutcome("SELECT * FROM User WHERE Id = " + id + ";");
    }
    
    /**
     * Search for user by username.
     *
     * @param username User id
     * @return Returns user
     */
    @Override
    public User readUser(String username) {
        return requestBookWithOneOutcome("SELECT * FROM User WHERE UserName = '" + username + "';");
    }
    
    /**
     * Update a specified user.
     *
     * @param id           User id
     * @param username     User username
     * @param passwordHash User password hash
     * @param role         User role
     */
    @Override
    public void updateUser(int id, String username, String passwordHash, UserRole role) {
        requestWithNoOutcome("UPDATE User SET UserName = '" + username + "', Password_Hash = '" + passwordHash + "', Role ='" + role.name() + "' WHERE Id =" + id + ";");
    }
    
    /**
     * Search for user by ID.
     *
     * @param id User ID
     */
    @Override
    public void deleteUser(int id) {
        requestWithNoOutcome("DELETE FROM User WHERE Id = " + id + ";");
    }
    
    /**
     * Search for user by username.
     *
     * @param username User username
     * @return Returns user list
     */
    @Override
    public User[] searchUserByUsername(String username) {
        return RequestBookWithMultipleOutcome("SELECT * FROM User WHERE UserName LIKE '" + username + "';");
    }
    
    /**
     * Search user by role.
     *
     * @param role User role
     * @return Returns user list
     */
    @Override
    public User[] searchUserByRole(UserRole role) {
        return RequestBookWithMultipleOutcome("SELECT * FROM User WHERE Role = '" + role.name() + "';");
    }
    
    /**
     * Execute sql query with multiple outcome.
     *
     * @param sql SQL query
     * @return Return user list
     */
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
    
    /**
     * Execute sql query with one outcome.
     *
     * @param sql SQL query
     * @return Return user
     */
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
    
    /**
     * Execute sql query with no outcome.
     *
     * @param sql SQL query
     */
    private void requestWithNoOutcome(String sql) {
        try {
            Statement statement = dataSource.getDatabase()
                                            .createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println();
        }
    }
}
