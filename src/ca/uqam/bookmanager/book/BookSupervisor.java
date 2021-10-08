package ca.uqam.bookmanager.book;


import ca.uqam.bookmanager.Supervisor;
import ca.uqam.bookmanager.authentication.UserRole;

import java.util.Objects;

import ca.uqam.bookmanager.database.IDataSource;

public class BookSupervisor extends Supervisor
{
    
    IBookProvider bookProvider;
    
    public BookSupervisor(IDataSource datasource)
    {
        this.bookProvider = new BookProvider(datasource);
    }
    
    public void BookMenu(UserRole userRole)
    {
        BookHomeAction action = null;
        while (action != BookHomeAction.QUIT)
        {
            DisplayBookMenuOption(userRole);
            action = HandleBookMenuOption(userRole);
            if (action == BookHomeAction.DISPLAY_ALL)
                DisplayAllBook();
            if (action == BookHomeAction.SEARCH)
                SearchMenu();
            if (action == BookHomeAction.CREATE)
                CreateMenu();
            if (action == BookHomeAction.UPDATE)
                UpdateMenu();
            if (action == BookHomeAction.DELETE)
                DeleteMenu();
        }
    }
    
    private void DisplayBookMenuOption(UserRole role)
    {
        System.out.println("\033[1;34mPlease, select one of the following options:\033[0m");
        System.out.println("\033[0;34m(1)\033[0m Display all books");
        System.out.println("\033[0;34m(2)\033[0m Search for books");
        if (role == UserRole.LIBRARIAN || role == UserRole.ADMINISTRATOR)
        {
            System.out.println("\033[0;34m(3)\033[0m Add a new book ");
            System.out.println("\033[0;34m(4)\033[0m Update a book");
            System.out.println("\033[0;34m(5)\033[0m Delete a book");
        }
        System.out.println("\033[0;34m(0)\033[0m Leave");
    }
    
    private BookHomeAction HandleBookMenuOption(UserRole role)
    {
        BookHomeAction action;
        try
        {
            action = BookHomeAction.values()[Integer.parseInt(this.scanner.nextLine())];
            if (!(role == UserRole.LIBRARIAN || role == UserRole.ADMINISTRATOR) && (action == BookHomeAction.CREATE || action == BookHomeAction.UPDATE || action == BookHomeAction.DELETE))
            {
                return null;
            }
            return action;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    private void DisplayAllBook()
    {
        System.out.println("\033[1;34mA list of all the book in the database :\033[0m");
        Book[] books = bookProvider.ReadAllBook();
        for (Book book : books)
        {
            System.out.println(book.ToString());
        }
        System.out.println();
    }
    
    private void SearchMenu()
    {
        BookSearchAction action = null;
        while (action != BookSearchAction.QUIT)
        {
            DisplaySearchOption();
            action = HandleSearchOption();
            if (action == BookSearchAction.BYID)
                SearchById();
            if (action == BookSearchAction.BYTITLE)
                SearchByTitle();
            if (action == BookSearchAction.BYAUTHOR)
                SearchByAuthor();
            if (action == BookSearchAction.BYDESCRIPTION)
                SearchByDescription();
            if (action == BookSearchAction.BYISBN)
                SearchByIsbn();
        }
    }
    
    private void DisplaySearchOption()
    {
        System.out.println("\033[1;34mPlease, select one of the following options :\033[0m");
        System.out.println("\033[0;34m(1)\033[0m Search by Id");
        System.out.println("\033[0;34m(2)\033[0m Search by title");
        System.out.println("\033[0;34m(3)\033[0m Search by author");
        System.out.println("\033[0;34m(4)\033[0m Search by description");
        System.out.println("\033[0;34m(5)\033[0m Search by ISBN");
        System.out.println("\033[0;34m(0)\033[0m Leave ");
    }
    
    private BookSearchAction HandleSearchOption()
    {
        BookSearchAction action;
        try
        {
            return BookSearchAction.values()[Integer.parseInt(this.scanner.nextLine())];
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    private void SearchById()
    {
        try
        {
            System.out.println("\033[1;34mEnter an ID :\033[0m");
            int  input  = Integer.parseInt(scanner.nextLine());
            Book result = bookProvider.ReadBook(input);
            if (result != null)
            {
                System.out.println(result.ToString());
            }
            else
            {
                System.out.println("\033[1;31mNo book found with the entered ID\033[0m");
            }
            
        }
        catch (NumberFormatException e)
        {
            System.out.println("\033[1;31mYou entered an invalid number\033[0m");
        }
    }
    private void SearchByTitle()
    {
        System.out.println("\033[1;34mEnter an title or a fragment :\033[0m");
        String input   = scanner.nextLine();
        Book[] results = bookProvider.SearchBookByTitle(input);
        if (results == null)
        {
            System.out.println("\033[1;31mNo book found with the entered Title\033[0m");
        }
        else
        {
            System.out.println("\033[1;34mResult :\033[0m");
            for (Book book : results)
            {
                System.out.println(book.ToString());
            }
            System.out.println();
        }
    }
    private void SearchByAuthor()
    {
        System.out.println("\033[1;34mEnter an author or a fragment :\033[0m");
        String input   = scanner.nextLine();
        Book[] results = bookProvider.SearchBookByAuthor(input);
        System.out.println("\033[1;34mResult :\033[0m");
        if (results == null)
        {
            System.out.println("\033[1;31mNo book found with the entered Author\033[0m");
        }
        else
        {
            System.out.println("\033[1;34mResult :\033[0m");
            for (Book book : results)
            {
                System.out.println(book.ToString());
            }
            System.out.println();
        }
    }
    private void SearchByDescription()
    {
        System.out.println("\033[1;34mEnter a description or a fragment :\033[0m");
        String input   = scanner.nextLine();
        Book[] results = bookProvider.SearchBookByDescription(input);
        if (results == null)
        {
            System.out.println("\033[1;31mNo book found with the entered Description\033[0m");
        }
        else
        {
            System.out.println("\033[1;34mResults :\033[0m");
            for (Book book : results)
            {
                System.out.println(book.ToString());
            }
        }
    }
    private void SearchByIsbn()
    {
        try
        {
            System.out.println("\033[1;34mEnter an ISBN or a fragment :\033[0m");
            int    input   = Integer.parseInt(scanner.nextLine());
            Book[] results = bookProvider.SearchBookByIsbn(input);
            if (results == null)
            {
                System.out.println("\033[1;31mNo book found with the entered ISBN\033[0m");
            }
            else
            {
                System.out.println("\033[1;34mResult :\033[0m");
                for (Book book : results)
                {
                    System.out.println(book.ToString());
                }
                System.out.println();
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("\033[1;31mYou entered an invalid number\033[0m");
        }
    }
    
    private void CreateMenu()
    {
        try
        {
            System.out.println("\033[1;34mTitle :\033[0m");
            String title = scanner.nextLine();
            System.out.println("\033[1;34mAuthor :\033[0m");
            String author = scanner.nextLine();
            System.out.println("\033[1;34mDescription :\033[0m");
            String description = scanner.nextLine();
            System.out.println("\033[1;34mISBN :\033[0m");
            int isbn = Integer.parseInt(scanner.nextLine());
            System.out.println("\033[1;34mQuantity :\033[0m");
            int quantity = Integer.parseInt(scanner.nextLine());
            bookProvider.CreateBook(title, author, description, isbn, quantity);
        }
        catch (Exception e)
        {
            System.out.println("\033[1;31mThere was an error during the creation of the book, please refer to the manual or the application developer.\033[0m");
        }
        
    }
    
    private void UpdateMenu()
    {
        try
        {
            System.out.println("\033[1;34mEnter the id of the book you want to update :");
            Book book = bookProvider.ReadBook(Integer.parseInt(scanner.nextLine()));
            if (book != null)
            {
                System.out.printf("\033[1;34mTitle :\033[0m \033[0;33m(leave blank for no change)\033[0m \033[0;32m(current : %s)\033[0m \033[1;34m:\\033[0m", book.getTitle());
                String title = scanner.nextLine();
                System.out.printf("\033[1;34mmAuthor :\033[0m \033[0;33m(leave blank for no change)\033[0m \033[0;32m(current : %s)\033[0m \033[1;34m:\\033[0m", book.getAuthor());
                String author = scanner.nextLine();
                System.out.printf("\033[1;34mDescription :\033[0m \033[0;33m(leave blank for no change)\033[0m \033[0;32m(current : %s)\033[0m \033[1;34m:\\033[0m", book.getDescription());
                String description = scanner.nextLine();
                System.out.printf("\033[1;34mISBN :\033[0m \033[0;33m(leave blank for no change)\033[0m \033[0;32m(current : %d)\033[0m \033[1;34m:\\033[0m", book.getIsbn());
                int isbn;
                try
                {
                    isbn = Integer.parseInt(scanner.nextLine());
                }
                catch (NumberFormatException e)
                {
                    isbn = book.getIsbn();
                }
                System.out.printf("\033[1;34mQuantity :\033[0m \033[1;31m(leave blank for no change)\033[0m \033[0;32m(current : %s)\033[0m \033[1;34m:\033[0m", book.getQuantity());
                int quantity;
                try
                {
                    quantity = Integer.parseInt(scanner.nextLine());
                }
                catch (NumberFormatException e)
                {
                    quantity = book.getQuantity();
                }
                Book temp = new Book(title, author, description, isbn, quantity);
                System.out.println("\033[0;32mThis is the updated book :\033[0m");
                System.out.println(temp.ToString());
                String answer = "";
                while (!(Objects.equals(answer, "Y") || Objects.equals(answer, "N")))
                {
                    System.out.println("\033[1;33mAre you sure you want to update this book ? (Y/N)\033[0m");
                    answer = scanner.nextLine();
                    if (Objects.equals(answer, "Y"))
                        bookProvider.DeleteBook(book.getId());
                }
                if (!Objects.equals(title, ""))
                    book.setTitle(title);
                if (!Objects.equals(author, ""))
                    book.setAuthor(author);
                if (!Objects.equals(description, ""))
                    book.setDescription(description);
                bookProvider.UpdateBook(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getIsbn(), book.getQuantity());
            }
            else
            {
                System.out.println("\033[1;31mThe book you are trying to update doesn't exist\033[0m");
            }
        }
        catch (Exception e)
        {
            System.out.println("\033[1;31mThere was an error during the deletion of the book, please refer to the manual or the application developer\033[0m");
        }
    }
    
    private void DeleteMenu()
    {
        try
        {
            System.out.println("\033[1;33mEnter the id of the book you want to delete :\033[0m");
            Book book = bookProvider.ReadBook(Integer.parseInt(scanner.nextLine()));
            if (book != null)
            {
                System.out.println("\033[1;31mThis is the book you asked for deletion :\033[0m");
                System.out.println(book.ToString());
                String answer = "";
                while (!(Objects.equals(answer, "Y") || Objects.equals(answer, "N")))
                {
                    System.out.println("\\033[1;33mAre you sure you want to delete this book ? (Y/N)\033[0m");
                    answer = scanner.nextLine();
                    if (Objects.equals(answer, "Y"))
                        bookProvider.DeleteBook(book.getId());
                }
            }
            else
            {
                System.out.println("\033[1;31mThe book you are trying to delete doesn't exist\033[0m");
            }
        }
        catch (Exception e)
        {
            System.out.println("\033[1;31mThere was an error during the deletion of the book, please refer to the manual or the application developer\033[0m");
        }
    }
    
}
