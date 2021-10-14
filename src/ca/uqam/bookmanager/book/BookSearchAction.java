package ca.uqam.bookmanager.book;

/**
 * Define all the user possible action
 */
public enum BookSearchAction {
    /**
     * User want to leave the menu.
     */
    QUIT,
    /**
     * User wants to search by ID.
     */
    BY_ID,
    /**
     * User wants to search by Title.
     */
    BY_TITLE,
    /**
     * User wants to search by author.
     */
    BY_AUTHOR,
    /**
     * User wants to search by description.
     */
    BY_DESCRIPTION,
    /**
     * User wants to search by ISBN.
     */
    BY_ISBN
}
