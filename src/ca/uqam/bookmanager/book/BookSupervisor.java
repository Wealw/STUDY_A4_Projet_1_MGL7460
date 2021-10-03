package ca.uqam.bookmanager.book;

import ca.uqam.bookmanager.database.IDataSource;

public class BookSupervisor
{
    
    IBookProvider bookProvider;
    
    public BookSupervisor(IDataSource datasource){
        this.bookProvider = new BookProvider(datasource);
    }
    
    public void DisplayBookMenu(){
    
    }
    
    //TODO : Define the displaying function
    
}
