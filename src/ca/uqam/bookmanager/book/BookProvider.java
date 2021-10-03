package ca.uqam.bookmanager.book;

import ca.uqam.bookmanager.database.IDataSource;

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
        return null;
    }
    @Override
    public Book[] ReadAllBook()
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    @Override
    public Book ReadBook(int id)
    {
        // TODO : Write SLQ request
        return null;
    }
    @Override
    public void UpdateBook(int id,String title, String author, String description, int isbn, int quantity)
    {
        // TODO : Write SLQ request
    }
    @Override
    public void DeleteBook(int id)
    {
        // TODO : Write SLQ request
    }
    @Override
    public Book[] SearchBookByTitle(String title)
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    @Override
    public Book[] SearchBookByAuthor(String author)
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    @Override
    public Book[] SearchBookByDescription(String description)
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    @Override
    public Book[] SearchBookByIsbn(int isbn){
        // TODO : Write SLQ request
        return new Book[0];
    }
    
}
