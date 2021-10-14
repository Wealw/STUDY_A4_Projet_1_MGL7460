package ca.uqam.bookmanager.authentication;

/**
 * Define what a user provider should be able to do.
 */
public interface IUserProvider {
    /**
     * Create a user store it in the db and return it.
     *
     * @param username     User username
     * @param passwordHash User password hash
     * @param role         User role
     * @return User
     */
    User createUser(final String username, final String passwordHash, final UserRole role);
    /**
     * Search for all user.
     *
     * @return Returns a list of all users.
     */
    User[] readAllUser();
    /**
     * Search for a user with a specific ID.
     *
     * @param id User id
     * @return Returns the specified user
     */
    User readUser(final int id);
    /**
     * Search for a user with a specific username.
     *
     * @param username User id
     * @return Returns the specified user
     */
    User readUser(final String username);
    /**
     * Modify a specific user.
     *
     * @param id           User id
     * @param username     User username
     * @param passwordHash User password hash
     * @param role         User role
     */
    void updateUser(final int id, final String username, final String passwordHash, final UserRole role);
    /**
     * Delete the specified use.
     *
     * @param id User ID
     */
    void deleteUser(final int id);
    /**
     * Search for users by username.
     *
     * @param username User username
     * @return Return user list
     */
    User[] searchUserByUsername(final String username);
    /**
     * Search for users by role.
     *
     * @param role User role
     * @return Return user list
     */
    User[] searchUserByRole(final UserRole role);
}
