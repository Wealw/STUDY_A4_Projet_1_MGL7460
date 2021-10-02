package ca.uqam.bookmanager.authentication;

public interface IUserProvider
{
    // TODO : Update prototype parameter according to user property
    User CreateUser(String username, String passwordHash);
    User[] ReadAllUser();
    User ReadUser(int id);
    User ReadUser(String username);
    // TODO : Update prototype parameter according to user property
    void UpdateUser();
    void DeleteUser();
    User[] SearchUserByUsername(String username);
}
