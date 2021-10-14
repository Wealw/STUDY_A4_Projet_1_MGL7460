package ca.uqam.bookmanager.book;

import ca.uqam.bookmanager.database.IDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    @SuppressWarnings ("PMD.LawOfDemeter")
    @Override
    public Book createBook(final String title, final String author, final String description, final int isbn, final int quantity) {
        final PreparedStatement statement1;
        final PreparedStatement statement2;
        try {
            statement1 = dataSource.getConnection()
                                  .prepareStatement("INSERT INTO Book (Title, Author, Description, ISBN, Quantity) VALUES ( ?, ? , ? ,? ,? );");
            statement2 = dataSource.getConnection().prepareStatement("SELECT * FROM Book WHERE Title = ? and Author = ? and Description = ? and ISBN = ? and Quantity = ?;");
            statement1.setString(1, title);
            statement1.setString(2, author);
            statement1.setString(3, description);
            statement1.setInt(4, isbn);
            statement1.setInt(5, quantity);
            statement2.setString(1, title);
            statement2.setString(2, author);
            statement2.setString(3, description);
            statement2.setInt(4, isbn);
            statement2.setInt(5, quantity);
            requestWithNoOutcome(statement1);
            statement1.close();
            Book bookObject = requestBookWithOneOutcome(statement2);
            statement2.close();
            return bookObject;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return null;
        }
        //requestWithNoOutcome("INSERT INTO Book (Title, Author, Description, ISBN, Quantity) VALUES ('" + title + "','" + author + "','" + description + "'," + isbn + "," + quantity + ");");
        //return null;
    }
    
    /**
     * @return Returns a list of all book in the database
     */
    @Override
    public Book[] readAllBook() {
        try {
            final PreparedStatement statement = dataSource.getConnection()
                                                          .prepareStatement("SELECT * FROM Book;");
            Book[] bookList = requestBookWithMultipleOutcome(statement);
            statement.close();
            return bookList;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return new Book[0];
        }
    }
    
    /**
     * @param id ID of a specific book
     * @return Book instance
     */
    @Override
    public Book readBook(final int id) {
        try {
            final PreparedStatement statement = dataSource.getConnection()
                                                          .prepareStatement("SELECT * FROM Book WHERE Id = ? ;");
            statement.setInt(1, id);
            Book bookObject = requestBookWithOneOutcome(statement);
            statement.close();
            return bookObject;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return null;
        }
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
        //requestWithNoOutcome("UPDATE Book SET Title = '" + title + "', Author = '" + author + "', Description = '" + description + "',ISBN = " + isbn + ", Quantity =" + quantity + " WHERE Id = " + id + ";");
        try {
            final PreparedStatement statement = dataSource.getConnection()
                                                          .prepareStatement("UPDATE Book SET Title = ?, Author = ?, Description = ?,ISBN = ? , Quantity =? WHERE Id = ?;");
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, description);
            statement.setInt(4, isbn);
            statement.setInt(5, quantity);
            statement.setInt(6, id);
            requestWithNoOutcome(statement);
            statement.close();
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
        }
    }
    
    /**
     * @param id Book id
     */
    @Override
    public void deleteBook(final int id) {
        //requestWithNoOutcome("DELETE FROM Book WHERE Id =" + id + ";");
        try {
            final PreparedStatement statement = dataSource.getConnection()
                                                          .prepareStatement("DELETE FROM Book WHERE Id = ? ;");
            statement.setInt(1, id);
            requestWithNoOutcome(statement);
            statement.close();
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
        }
    }
    
    /**
     * @param title Book title
     * @return Book list
     */
    @Override
    public Book[] searchBookByTitle(final String title) {
        //return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE Title LIKE '" + title + "';");
        try {
            final PreparedStatement statement = dataSource.getConnection()
                                                          .prepareStatement("SELECT * FROM Book WHERE Title LIKE ?;");
            statement.setString(1, title);
            Book[] bookList = requestBookWithMultipleOutcome(statement);
            statement.close();
            return bookList;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return new Book[0];
        }
    }
    
    /**
     * @param author Book author
     * @return Book list
     */
    @Override
    public Book[] searchBookByAuthor(final String author) {
        //return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE Author LIKE '" + author + "';");
        try {
            final PreparedStatement statement = dataSource.getConnection()
                                                          .prepareStatement("SELECT * FROM Book WHERE Author LIKE ?;");
            statement.setString(1, author);
            Book[] bookList = requestBookWithMultipleOutcome(statement);
            statement.close();
            return bookList;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return new Book[0];
        }
    }
    
    /**
     * @param description Book description
     * @return Book list
     */
    @Override
    public Book[] searchBookByDescription(final String description) {
        //return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE Description LIKE '" + description + "';");
        try {
            final PreparedStatement statement = dataSource.getConnection()
                                                          .prepareStatement("SELECT * FROM Book WHERE Description LIKE ?;");
            statement.setString(1, description);
            Book[] bookList = requestBookWithMultipleOutcome(statement);
            statement.close();
            return bookList;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return new Book[0];
        }
    }
    
    /**
     * @param isbn Book ISBN
     * @return Book list
     */
    @Override
    public Book[] searchBookByIsbn(final int isbn) {
        //return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE ISBN LIKE " + isbn + ";");
        try {
            final PreparedStatement statement = dataSource.getConnection()
                                                          .prepareStatement("SELECT * FROM Book WHERE ISBN LIKE ?;");
            statement.setInt(1, isbn);
            Book[] bookList = requestBookWithMultipleOutcome(statement);
            statement.close();
            return bookList;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return new Book[0];
        }
    }
    
    /**
     * Execute sql query with multiple outcome.
     *
     * @param sql Sql query
     * @return Book list
     */
    private Book requestBookWithOneOutcome(final PreparedStatement sql) {
        try {
            final ResultSet rs         = sql.executeQuery();
            Book            bookObject = new Book(rs.getInt("Id"), rs.getString("Title"), rs.getString("Author"), rs.getString("Description"), rs.getInt("ISBN"), rs.getInt("Quantity"));
            rs.close();
            return bookObject;
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return null;
        }
    }
    
    /**
     * Execute a sql query with one outcome.
     *
     * @param sql Sql query
     * @return Book
     */
    private Book[] requestBookWithMultipleOutcome(final PreparedStatement sql) {
        try {
            ResultSet       rs    = sql.executeQuery();
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(new Book(rs.getInt("Id"), rs.getString("Title"), rs.getString("Author"), rs.getString("Description"), rs.getInt("ISBN"), rs.getInt("Quantity")));
            }
            rs.close();
            return books.toArray(new Book[0]);
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
            return new Book[0];
        }
    }
    
    /**
     * Execute a sql query with no outcome.
     *
     * @param sql Sql query
     */
    private void requestWithNoOutcome(final PreparedStatement sql) {
        try {
            sql.execute();
        } catch (SQLException e) {
            System.out.println("\033[1;31mThere was an error when executing the query\033[0m");
        }
    }
}
