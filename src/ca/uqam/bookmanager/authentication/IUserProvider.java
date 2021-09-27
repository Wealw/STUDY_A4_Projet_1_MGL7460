package ca.uqam.bookmanager.authentication;

public interface IUserProvider
{
    // TODO : Update prototype parameter according to user property
    public void CreateUser();
    public User[] ReadAllUser();
    public User ReadUser(int id);
    // TODO : Update prototype parameter according to user property
    public void UpdateUser();
    public void DeleteUser();
    public User[] SearchUserByUsername(String username);
}
