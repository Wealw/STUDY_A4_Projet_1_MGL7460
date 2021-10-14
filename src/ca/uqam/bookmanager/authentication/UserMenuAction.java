package ca.uqam.bookmanager.authentication;


/**
 * Define all the possible action in the user main menu.
 */
public enum UserMenuAction {
    /**
     * User wants to leave.
     */
    QUIT,
    /**
     * User want to edit his profile.
     */
    EDIT_CURRENT,
    /**
     * User wants to display all user.
     */
    DISPLAY_ALL,
    /**
     * User wants to search some users.
     */
    SEARCH,
    /**
     * User wants to create some users.
     */
    CREATE,
    /**
     * User wants to edit some users.
     */
    EDIT,
    /**
     * User wants to delete some users.
     */
    DELETE,
}
