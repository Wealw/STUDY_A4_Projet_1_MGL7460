package ca.uqam.bookmanager.authentication;

public interface IUserProvider {
    
    // TODO : Update prototype parameter according to user property
    User createUser(String username, String passwordHash, UserRole role);
    User[] readAllUser();
    User readUser(int id);
    User readUser(String username);
    // TODO : Update prototype parameter according to user property
    void updateUser(int id, String username, String passwordHash, UserRole role);
    void deleteUser(int id);
    User[] searchUserByUsername(String username);
    User[] searchUserByRole(UserRole role);
    
}
