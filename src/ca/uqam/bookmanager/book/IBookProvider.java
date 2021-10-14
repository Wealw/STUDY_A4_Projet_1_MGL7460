package ca.uqam.bookmanager.book;

/**
 * Define what a user provider should do
 */
public interface IBookProvider {
    /**
     * Create a book in the database and return its instance.
     *
     * @param title       Book title
     * @param author      Book author
     * @param description Book description
     * @param isbn        Book ISBN
     * @param quantity    Quantity of book
     * @return Book instance
     */
    Book createBook(final String title, final String author, final String description, final int isbn, final int quantity);
    /**
     * Look for all book
     *
     * @return Returns a list of all book in the database
     */
    Book[] readAllBook();
    /**
     * "Look for a book with a specific id"
     *
     * @param id ID of a specific book
     * @return Returns a specific book from its ID
     */
    Book readBook(final int id);
    /**
     * Modify a specific book in the database.
     *
     * @param id          Book id
     * @param title       Book title
     * @param author      Book author
     * @param description Book description
     * @param isbn        Book ISBN
     * @param quantity    Quantity of book
     */
    void updateBook(final int id, final String title, final String author, final String description, final int isbn, final int quantity);
    /**
     * Delete a specific book from the database.
     *
     * @param id Book id
     */
    void deleteBook(final int id);
    /**
     * Search for a book by its title.
     *
     * @param title Book title
     * @return Return a list of book with matching title
     */
    Book[] searchBookByTitle(final String title);
    /**
     * "Return a list of book with matching author"
     *
     * @param author Book author
     * @return Return a list of book with matching author
     */
    Book[] searchBookByAuthor(final String author);
    /**
     * "Return a list of book with matching description"
     *
     * @param description Book description
     * @return Return a list of book with matching description
     */
    Book[] searchBookByDescription(final String description);
    /**
     * "Return a list of book with matching ISBN"
     *
     * @param isbn Book ISBN
     * @return Return a list of book with matching ISBN
     */
    Book[] searchBookByIsbn(final int isbn);
}
