package ca.uqam.bookmanager.book;

/**
 * Define all the user possible action in home menu
 */
public enum BookHomeAction {
    /**
     * User wants to leave.
     */
    QUIT,
    /**
     * User wants to display all books.
     */
    DISPLAY_ALL,
    /**
     * User wants to search some books.
     */
    SEARCH,
    /**
     * User wants to create books.
     */
    CREATE,
    /**
     * User wants to modify books.
     */
    UPDATE,
    /**
     * User wants to delete books.
     */
    DELETE
}
