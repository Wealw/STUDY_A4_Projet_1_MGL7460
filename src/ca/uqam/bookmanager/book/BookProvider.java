package ca.uqam.bookmanager.book;

import ca.uqam.bookmanager.database.IDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookProvider implements IBookProvider {
    
    private final IDataSource dataSource;
    
    public BookProvider(IDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public Book createBook(String title, String author, String description, int isbn, int quantity) {
        requestWithNoOutcome("INSERT INTO Book (Title, Author, Description, ISBN, Quantity) VALUES ('" + title + "','" + author + "','" + description + "'," + isbn + "," + quantity + ");");
        return null;
    }
    @Override
    public Book[] readAllBook() {
        return requestBookWithMultipleOutcome("SELECT * FROM Book;");
    }
    @Override
    public Book readBook(final int id) {
        return requestBookWithOneOutcome("SELECT * FROM Book WHERE Id =" + id + ";");
    }
    @Override
    public void updateBook(final int id, final String title, final String author, final String description, final int isbn, final int quantity) {
        requestWithNoOutcome("UPDATE Book SET Title = '" + title + "', Author = '" + author + "', Description = '" + description + "',ISBN = " + isbn + ", Quantity =" + quantity + " WHERE Id = " + id + ";");
    }
    @Override
    public void deleteBook(final int id) {
        requestWithNoOutcome("DELETE FROM Book WHERE Id =" + id + ";");
    }
    @Override
    public Book[] searchBookByTitle(final String title) {
        return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE Title LIKE '" + title + "';");
    }
    @Override
    public Book[] searchBookByAuthor(final String author) {
        return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE Author LIKE '" + author + "';");
    }
    @Override
    public Book[] searchBookByDescription(final String description) {
        return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE Description LIKE '" + description + "';");
    }
    @Override
    public Book[] searchBookByIsbn(final int isbn) {
        return requestBookWithMultipleOutcome("SELECT * FROM Book WHERE ISBN LIKE " + isbn + ";");
    }
    private Book requestBookWithOneOutcome(final String sql) {
        try {
            Statement statement = dataSource.getDatabase()
                                            .createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return new Book(rs.getInt("Id"), rs.getString("Title"), rs.getString("Author"), rs.getString("Description"), rs.getInt("ISBN"), rs.getInt("Quantity"));
        } catch (SQLException e) {
            return null;
        }
    }
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
