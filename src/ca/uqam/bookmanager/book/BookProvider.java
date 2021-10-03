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
    public void CreateBook()
    {
        // TODO : Write SLQ request
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
    public void UpdateBook()
    {
        // TODO : Write SLQ request
    }
    @Override
    public void DeleteBook()
    {
        // TODO : Write SLQ request
    }
    @Override
    public Book[] SearchBookByTitle()
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    @Override
    public Book[] SearchBookByAuthor()
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    @Override
    public Book[] SearchBookByDescription()
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    
}
