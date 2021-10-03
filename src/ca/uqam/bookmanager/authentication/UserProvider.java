package ca.uqam.bookmanager.authentication;

import ca.uqam.bookmanager.database.IDataSource;

public class UserProvider implements IUserProvider
{
    IDataSource dataSource;
    
    public UserProvider(IDataSource dataSource)
    {
        this.dataSource = dataSource;
    }
    
    @Override
    public User CreateUser(String username, String passwordHash)
    {
        // TODO : Write SLQ request
        return null;
    }
    @Override
    public User[] ReadAllUser()
    {
        // TODO : Write SLQ request
        return new User[0];
    }
    @Override
    public User ReadUser(int id)
    {
        // TODO : Write SLQ request
        return null;
    }
    @Override
    public User ReadUser(String username){
        return null;
    }
    @Override
    public void UpdateUser()
    {
        // TODO : Write SLQ request
    }
    @Override
    public void DeleteUser()
    {
        // TODO : Write SLQ request
    }
    @Override
    public User[] SearchUserByUsername(String username)
    {
        // TODO : Write SLQ request
        return new User[0];
    }
    
}
