package ca.uqam.bookmanager.authentication;

public interface IUserProvider
{
    // TODO : Update prototype parameter according to user property
    User CreateUser(String username, String passwordHash, UserRole role);
    User[] ReadAllUser();
    User ReadUser(int id);
    User ReadUser(String username);
    // TODO : Update prototype parameter according to user property
    void UpdateUser(int id, String username, String passwordHash, UserRole role);
    void DeleteUser(int id);
    User[] SearchUserByUsername(String username);
    User[] SearchUserByRole(UserRole role);
}
