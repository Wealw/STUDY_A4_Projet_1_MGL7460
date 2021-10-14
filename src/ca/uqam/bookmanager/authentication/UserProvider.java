package ca.uqam.bookmanager.authentication;

import ca.uqam.bookmanager.database.IDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Provide user and user list to other class
 */
@SuppressWarnings ("PMD.SystemPrintln")
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
        //if (null != requestUserWithOneOutcome("SELECT * FROM User WHERE UserName ='" + username + "', Password_Hash ='" + passwordHash + "';")) {
        //    return null;
        //}
        //requestWithNoOutcome("INSERT INTO User (UserName, Password_Hash, Role) VALUES ('" + username + "','" + passwordHash + "','" + role.name() + "');");
        //return requestUserWithOneOutcome("SELECT * FROM User WHERE UserName ='" + username + "', Password_Hash ='" + passwordHash + "';");
        PreparedStatement statement1;
        PreparedStatement statement2;
        PreparedStatement statement3;
        try {
            statement1 = dataSource.getConnection()
                                   .prepareStatement("SELECT * FROM User WHERE UserName = ?;");
            statement2 = dataSource.getConnection()
                                   .prepareStatement("INSERT INTO User (UserName, Password_Hash, Role) VALUES (?,?,?);");
            statement3 = dataSource.getConnection()
                                   .prepareStatement("SELECT * FROM User WHERE UserName = ? and Password_Hash = ? ;");
            statement1.setString(1, username);
            statement2.setString(1, username);
            statement2.setString(2, passwordHash);
            statement2.setString(3, role.name());
            statement3.setString(1, username);
            statement3.setString(2, passwordHash);
            if (requestUserWithOneOutcome(statement1) != null) {
                return null;
            }
            requestWithNoOutcome(statement2);
            User userObject = requestUserWithOneOutcome(statement3);
            statement1.close();
            statement2.close();
            statement3.close();
            return userObject;
        } catch (SQLException e) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAH");
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return null;
        }
    }
    
    /**
     * @return Returns user list
     */
    @Override
    public User[] readAllUser() {
        //return requestUserWithMultipleOutcome("SELECT * FROM USER;");
        try {
            PreparedStatement statement = dataSource.getConnection()
                                                    .prepareStatement("SELECT * FROM USER;");
            User[] userList = requestUserWithMultipleOutcome(statement);
            statement.close();
            return userList;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return new User[0];
        }
    }
    
    /**
     * Search for user by id.
     *
     * @param id User id
     * @return Returns user
     */
    @Override
    public User readUser(int id) {
        //return requestUserWithOneOutcome("SELECT * FROM User WHERE Id = " + id + ";");
        try {
            PreparedStatement statement = dataSource.getConnection()
                                                    .prepareStatement("SELECT * FROM User WHERE Id = ?;");
            statement.setInt(1, id);
            User userObject = requestUserWithOneOutcome(statement);
            statement.close();
            return userObject;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return null;
        }
    }
    
    /**
     * Search for user by username.
     *
     * @param username User id
     * @return Returns user
     */
    @Override
    public User readUser(String username) {
        //return requestUserWithOneOutcome("SELECT * FROM User WHERE UserName = '" + username + "';");
        try {
            PreparedStatement statement = dataSource.getConnection()
                                                    .prepareStatement("SELECT * FROM User WHERE UserName = ?;");
            statement.setString(1, username);
            User userObject = requestUserWithOneOutcome(statement);
            statement.close();
            return userObject;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return null;
        }
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
        //requestWithNoOutcome("UPDATE User SET UserName = '" + username + "', Password_Hash = '" + passwordHash + "', Role ='" + role.name() + "' WHERE Id =" + id + ";");
        try {
            PreparedStatement statement = dataSource.getConnection()
                                                    .prepareStatement("UPDATE User SET UserName = ?, Password_Hash = ?, Role = ? WHERE Id = ?;");
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, role.name());
            statement.setInt(4, id);
            requestWithNoOutcome(statement);
            statement.close();
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
        }
    }
    
    /**
     * Search for user by ID.
     *
     * @param id User ID
     */
    @Override
    public void deleteUser(int id) {
        //requestWithNoOutcome("DELETE FROM User WHERE Id = " + id + ";");
        try {
            PreparedStatement statement = dataSource.getConnection()
                                                    .prepareStatement("DELETE FROM User WHERE Id = ? ;");
            statement.setInt(1, id);
            requestWithNoOutcome(statement);
            statement.close();
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
        }
    }
    
    /**
     * Search for user by username.
     *
     * @param username User username
     * @return Returns user list
     */
    @Override
    public User[] searchUserByUsername(String username) {
        //return requestUserWithMultipleOutcome("SELECT * FROM User WHERE UserName LIKE '" + username + "';");
        try {
            PreparedStatement statement = dataSource.getConnection()
                                                    .prepareStatement("SELECT * FROM User WHERE UserName LIKE ? ;");
            statement.setString(1, username);
            User[] userList = requestUserWithMultipleOutcome(statement);
            statement.close();
            return userList;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return new User[0];
        }
    }
    
    /**
     * Search user by role.
     *
     * @param role User role
     * @return Returns user list
     */
    @Override
    public User[] searchUserByRole(UserRole role) {
        //return requestUserWithMultipleOutcome("SELECT * FROM User WHERE Role = '" + role.name() + "';");
        try {
            PreparedStatement statement = dataSource.getConnection()
                                                    .prepareStatement("SELECT * FROM User WHERE Role LIKE ? ;");
            statement.setString(1, role.name());
            User[] userList = requestUserWithMultipleOutcome(statement);
            statement.close();
            return userList;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return new User[0];
        }
    }
    
    /**
     * Execute sql query with multiple outcome.
     *
     * @param sql SQL query
     * @return Return user list
     */
    private User[] requestUserWithMultipleOutcome(PreparedStatement sql) {
        try {
            ResultSet       rs    = sql.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs.getInt("Id"), rs.getString("UserName"), rs.getString("Password_Hash"), UserRole.valueOf(rs.getString("Role"))));
            }
            rs.close();
            return users.toArray(new User[0]);
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return new User[0];
        }
    }
    
    /**
     * Execute sql query with one outcome.
     *
     * @param sql SQL query
     * @return Return user
     */
    private User requestUserWithOneOutcome(PreparedStatement sql) {
        try {
            ResultSet rs         = sql.executeQuery();
            User      userObject = null;
            if (!rs.isClosed()) {
                userObject = new User(rs.getInt("Id"), rs.getString("UserName"), rs.getString("Password_Hash"), UserRole.valueOf(rs.getString("Role")));
            }
            rs.close();
            return userObject;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return null;
        }
    }
    
    /**
     * Execute sql query with no outcome.
     *
     * @param sql SQL query
     */
    private void requestWithNoOutcome(PreparedStatement sql) {
        try {
            sql.execute();
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
        }
    }
}
