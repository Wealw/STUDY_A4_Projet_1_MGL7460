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
        return new User("test","ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff",UserRole.ADMINISTRATOR);
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
