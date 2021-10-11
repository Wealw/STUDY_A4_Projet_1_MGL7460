package ca.uqam.bookmanager.book;

import ca.uqam.bookmanager.database.IDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Provide book and book list to other class
 */
class BookProvider implements IBookProvider {
    
    /**
     * Datasource.
     */
    private final IDataSource dataSource;
    
    /**
     * Define the basic attribute of a book provider
     *
     * @param dataSource datasource
     */
    public BookProvider(IDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @param title       Book title
     * @param author      Book author
     * @param description Book description
     * @param isbn        Book ISBN
     * @param quantity    Quantity of book
     * @return Book
     */
    @Override
    public Book createBook(final String title, final String author, final String description, final int isbn, final int quantity) {
        requestWithNoOutcome("INSERT INTO Book (Title, Author, Description, ISBN, Quantity) VALUES ('" + title + "','" + author + "','" + description + "'," + isbn + "," + quantity + ");");
        return null;
    }
    
    /**
     * @return Returns a list of all book in the database
     */
    @Override
    public Book[] readAllBook() {
        return requestBookWithMultipleOutcome("SELECT * FROM Book;");
    }
    
    /**
     * @param id ID of a specific book
     * @return Book instance
     */
    @Override
    public Book readBook(final int id) {
        return requestBookWithOneOutcome("SELECT * FROM Book WHERE Id =" + id + ";");
    }
    
    /**
     * @param id          Book id
     * @param title       Book title
     * @param author      Book author
     * @param description 'Book description"
     * @param isbn        Book ISBN
     * @param quantity    Quantity of book
     */
    @Override
    public void updateBook(final int id, final String title, final String author, final String description, final int isbn, final int quantity) {
        requestWithNoOutcome("UPDATE Book SET Title = '" + title + "', Author = '" + author + "', Description = '" + description + "',ISBN = " + isbn + ", Quantity =" + quantity + " WHERE Id = " + id + ";");
    }
    
    /**
     * @param id Book id
     */
    @Override
    public void deleteBook(final int id) {
        requestWithNoOutcome("DELETE FROM Book WHERE Id =" + id + ";");
    }
    
    /**
     * @param title Book title
     * @return Book list
     */
    @Override
    public Book[] searchBookByTitle(final String title) {
        return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE Title LIKE '" + title + "';");
    }
    
    /**
     * @param author Book author
     * @return Book list
     */
    @Override
    public Book[] searchBookByAuthor(final String author) {
        return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE Author LIKE '" + author + "';");
    }
    
    /**
     * @param description Book description
     * @return Book list
     */
    @Override
    public Book[] searchBookByDescription(final String description) {
        return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE Description LIKE '" + description + "';");
    }
    
    /**
     * @param isbn Book ISBN
     * @return Book list
     */
    @Override
    public Book[] searchBookByIsbn(final int isbn) {
        return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE ISBN LIKE " + isbn + ";");
    }
    
    /**
     * Execute sql query with multiple outcome.
     *
     * @param sql Sql query
     * @return Book list
     */
    private Book requestBookWithOneOutcome(final String sql) {
        try {
            final Statement statement = dataSource.getDatabase()
                                                  .createStatement();
            final ResultSet rs = statement.executeQuery(sql);
            return new Book(rs.getInt("Id"), rs.getString("Title"), rs.getString("Author"), rs.getString("Description"), rs.getInt("ISBN"), rs.getInt("Quantity"));
        } catch (SQLException e) {
            return null;
        }
    }
    
    /**
     * Execute a sql query with one outcome.
     *
     * @param sql Sql query
     * @return Book
     */
    private Book[] requestBookWithMultipleOutcome(final String sql) {
        try {
            Statement statement = dataSource.getDatabase()
                                            .createStatement();
            ResultSet       rs    = statement.executeQuery(sql);
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(new Book(rs.getInt("Id"), rs.getString("Title"), rs.getString("Author"), rs.getString("Description"), rs.getInt("ISBN"), rs.getInt("Quantity")));
            }
            return books.toArray(new Book[0]);
        } catch (SQLException e) {
            return new Book[0];
        }
    }
    
    /**
     * Execute a sql query with no outcome.
     *
     * @param sql Sql query
     */
    private void requestWithNoOutcome(final String sql) {
        try {
            Statement statement = dataSource.getDatabase()
                                            .createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("There was an error when executing the query");
        }
    }
}
