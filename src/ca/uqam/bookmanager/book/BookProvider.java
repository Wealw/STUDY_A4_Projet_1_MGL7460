package ca.uqam.bookmanager.book;

import ca.uqam.bookmanager.database.IDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookProvider implements IBookProvider
{
    
    IDataSource dataSource;
    
    public BookProvider(IDataSource dataSource)
    {
        this.dataSource = dataSource;
    }
    
    @Override
    public Book CreateBook(String title, String author, String description, int isbn, int quantity)
    {
        RequestWithNoOutcome("INSERT INTO Book (Title, Author, Description, ISBN, Quantity) VALUES ('" + title + "','"+ author + "','" + description + "'," + isbn + "," + quantity + ");");
        return null;
    }
    @Override
    public Book[] ReadAllBook()
    {
        return RequestBookWithMultipleOutcome("SELECT * FROM Book;");
    }
    @Override
    public Book ReadBook(int id)
    {
        return RequestBookWithOneOutcome("SELECT * FROM Book WHERE Id =" + id +";");
    }
    @Override
    public void UpdateBook(int id,String title, String author, String description, int isbn, int quantity)
    {
        RequestWithNoOutcome("UPDATE Book SET Title = '" + title + "', Author = '" + author + "', Description = '" + description + "',ISBN = " + isbn + ", Quantity =" + quantity + " WHERE Id = " + id + ";");
    }
    @Override
    public void DeleteBook(int id)
    {
        RequestWithNoOutcome("DELETE FROM Book WHERE Id =" + id + ";");
    }
    @Override
    public Book[] SearchBookByTitle(String title)
    {
        return RequestBookWithMultipleOutcome("SELECT * FROM Book WHERE Title LIKE '" + title + "';");
    }
    @Override
    public Book[] SearchBookByAuthor(String author)
    {
        return RequestBookWithMultipleOutcome("SELECT * FROM Book WHERE Author LIKE '" + author + "';");
    }
    @Override
    public Book[] SearchBookByDescription(String description)
    {
        return RequestBookWithMultipleOutcome("SELECT * FROM Book WHERE Description LIKE '" + description + "';");
    }
    @Override
    public Book[] SearchBookByIsbn(int isbn){
        return RequestBookWithMultipleOutcome("SELECT * FROM Book WHERE ISBN LIKE " + isbn + ";");
    }
    private Book[] RequestBookWithMultipleOutcome(String sql){
        try{
            Statement statement = dataSource.getDatabase().createStatement();
            ResultSet rs        = statement.executeQuery(sql);
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()){
                books.add(new Book(rs.getInt("Id"),rs.getString("Title"), rs.getString("Author"), rs.getString("Description"),rs.getInt("ISBN"),rs.getInt("Quantity")));
            }
            return books.toArray(new Book[0]);
        } catch (SQLException e){
            return new Book[0];
        }
    }
    
    private Book RequestBookWithOneOutcome(String sql){
        try{
            Statement statement = dataSource.getDatabase().createStatement();
            ResultSet rs        = statement.executeQuery(sql);
            return new Book(rs.getInt("Id"),rs.getString("Title"), rs.getString("Author"), rs.getString("Description"),rs.getInt("ISBN"),rs.getInt("Quantity"));
        } catch (SQLException e){
            return null;
        }
    }
    
    private void RequestWithNoOutcome(String sql){
        try{
            Statement statement = dataSource.getDatabase().createStatement();
            statement.execute(sql);
        } catch (SQLException e){
            System.out.println("There was an error when executing the query");
        }
    }
}
