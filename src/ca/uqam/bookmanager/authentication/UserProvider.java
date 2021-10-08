package ca.uqam.bookmanager.authentication;

import ca.uqam.bookmanager.database.IDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserProvider implements IUserProvider
{
    
    IDataSource dataSource;
    
    public UserProvider(IDataSource dataSource)
    {
        this.dataSource = dataSource;
    }
    
    @Override
    public User CreateUser(String username, String passwordHash, UserRole role)
    {
        RequestWithNoOutcome("INSERT INTO User (UserName, Password_Hash, Role) VALUES ('" + username + "','" + passwordHash + "','" + role.name() + "');");
        return null;
        
    }
    @Override
    public User[] ReadAllUser()
    {
        return RequestBookWithMultipleOutcome("SELECT * FROM USER;");
    }
    @Override
    public User ReadUser(int id)
    {
        return RequestBookWithOneOutcome("SELECT * FROM User WHERE Id = " + id + ";");
    }
    @Override
    
    public User ReadUser(String username)
    {
        return RequestBookWithOneOutcome("SELECT * FROM User WHERE UserName = '" + username + "';");
    }
    @Override
    public void UpdateUser(int id, String username, String passwordHash, UserRole role)
    {
        RequestWithNoOutcome("UPDATE User SET UserName = '" + username + "', Password_Hash = '" + passwordHash + "', Role ='" + role.name() + "' WHERE Id =" + id + ";");
    }
    @Override
    public void DeleteUser(int id)
    {
        RequestWithNoOutcome("DELETE FROM User WHERE Id = " + id + ";");
    }
    @Override
    public User[] SearchUserByUsername(String username)
    {
        return RequestBookWithMultipleOutcome("SELECT * FROM User WHERE UserName LIKE '" + username + "';");
    }
    @Override
    public User[] SearchUserByRole(UserRole role)
    {
        return RequestBookWithMultipleOutcome("SELECT * FROM User WHERE Role = '" + role.name()+ "';");
    }
    
    private User[] RequestBookWithMultipleOutcome(String sql)
    {
        try
        {
            Statement       statement = dataSource.getDatabase()
                                                  .createStatement();
            ResultSet       rs        = statement.executeQuery(sql);
            ArrayList<User> users     = new ArrayList<>();
            while (rs.next())
            {
                users.add(new User(rs.getInt("Id"), rs.getString("UserName"), rs.getString("Password_Hash"), UserRole.valueOf(rs.getString("Role"))));
            }
            return users.toArray(new User[0]);
        }
        catch (SQLException e)
        {
            return new User[0];
        }
    }
    
    private User RequestBookWithOneOutcome(String sql)
    {
        try
        {
            Statement statement = dataSource.getDatabase()
                                            .createStatement();
            ResultSet rs        = statement.executeQuery(sql);
            return new User(rs.getInt("Id"), rs.getString("UserName"), rs.getString("Password_Hash"), UserRole.valueOf(rs.getString("Role")));
            
        }
        catch (SQLException e)
        {
            return null;
        }
    }
    
    private void RequestWithNoOutcome(String sql)
    {
        try
        {
            Statement statement = dataSource.getDatabase()
                                            .createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            System.out.println("There was an error when executing the query");
        }
    }
    
}
