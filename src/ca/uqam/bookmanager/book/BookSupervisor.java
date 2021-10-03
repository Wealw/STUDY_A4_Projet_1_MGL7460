package ca.uqam.bookmanager.book;

import ca.uqam.bookmanager.Supervisor;
import ca.uqam.bookmanager.authentication.UserRole;

import java.util.Objects;

public class BookSupervisor extends Supervisor
{
    
    IBookProvider bookProvider = new BookProvider();
    
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
        System.out.println("Please select one of the following :");
        System.out.println("(1) Display all books");
        System.out.println("(2) Search for books");
        if (role == UserRole.LIBRARIAN || role == UserRole.ADMINISTRATOR)
        {
            System.out.println("(3) Add a new book ");
            System.out.println("(4) Update a book");
            System.out.println("(5) Delete a book");
        }
        System.out.println("(0) Leave");
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
        System.out.println("A list of all the book in the database :");
        Book[] books = bookProvider.ReadAllBook();
        for (Book book : books)
        {
            System.out.println(book.ToString());
        }
    }
    
    private void SearchMenu()
    {
        //TODO : Add a search menu
    }
    
    private void DisplaySearchOption(){
        //TODO : Add display
    }
    
    private BookSearchAction HandleSearchOption(){
        //TODO : Add handler
        return null;
    }
    
    private void CreateMenu()
    {
        try
        {
            System.out.println("\u001B[34mTitle :\u001B[0m");
            String title = scanner.nextLine();
            System.out.println("\u001B[34mAuthor :\u001B[0m");
            String author = scanner.nextLine();
            System.out.println("\u001B[34mDescription :\u001B[0m");
            String description = scanner.nextLine();
            System.out.println("\u001B[34mISBN :\u001B[0m");
            int isbn = Integer.parseInt(scanner.nextLine());
            System.out.println("\u001B[34mQuantity :\u001B[0m");
            int quantity = Integer.parseInt(scanner.nextLine());
            bookProvider.CreateBook(title, author, description, isbn, quantity);
        }
        catch (Exception e)
        {
            System.out.println("\u001B[31mThere was an error during the creation of the book, please refer to the manual or the application developer\u001B[0m");
        }
        
    }
    
    private void UpdateMenu()
    {
        try
        {
            System.out.println("Enter the id of the book you want to update :");
            Book book = bookProvider.ReadBook(Integer.parseInt(scanner.nextLine()));
            if (book != null)
            {
                System.out.printf("\u001B[34mTitle :\u001B[0m (leave blank for no change) (current : %s)", book.getTitle());
                String title = scanner.nextLine();
                System.out.printf("\u001B[34mAuthor :\u001B[0m (leave blank for no change) (current : %s)", book.getAuthor());
                String author = scanner.nextLine();
                System.out.printf("\u001B[34mDescription :\u001B[0m (leave blank for no change) (current : %s)", book.getDescription());
                String description = scanner.nextLine();
                System.out.printf("\u001B[34mISBN :\u001B[0m (leave blank for no change) (current : %d)", book.getIsbn());
                int isbn = Integer.parseInt(scanner.nextLine());
                System.out.printf("\u001B[34mQuantity :\u001B[0m (leave blank for no change) (current : %s)", book.getQuantity());
                int  quantity = Integer.parseInt(scanner.nextLine());
                Book temp = new Book(title, author, description, isbn, quantity);
                System.out.println("This is the updated book :");
                System.out.println(temp.ToString());
                String answer = "";
                while (!(Objects.equals(answer, "Y") || Objects.equals(answer, "N")))
                {
                    System.out.println("\u001B[33mAre you sure you want to update this book ? (Y/N)\u001B[0m");
                    answer = scanner.nextLine();
                    if (Objects.equals(answer, "Y"))
                        bookProvider.DeleteBook(book.getId());
                }
                bookProvider.UpdateBook(book.getId(), title, author, description, isbn, quantity);
            }
            else
            {
                System.out.println("\u001B[31mThe book you are trying to update doesn't exist\u001B[0m");
            }
        }
        catch (Exception e)
        {
            System.out.println("\u001B[31mThere was an error during the deletion of the book, please refer to the manual or the application developer\u001B[0m");
        }
    }
    
    private void DeleteMenu()
    {
        try
        {
            System.out.println("Enter the id of the book you want to delete :");
            Book book = bookProvider.ReadBook(Integer.parseInt(scanner.nextLine()));
            if (book != null)
            {
                System.out.println("This is the book you asked for deletion :");
                System.out.println(book.ToString());
                String answer = "";
                while (!(Objects.equals(answer, "Y") || Objects.equals(answer, "N")))
                {
                    System.out.println("\u001B[33mAre you sure you want to delete this book ? (Y/N)\u001B[0m");
                    answer = scanner.nextLine();
                    if (Objects.equals(answer, "Y"))
                        bookProvider.DeleteBook(book.getId());
                }
            }
            else
            {
                System.out.println("\u001B[31mThe book you are trying to delete doesn't exist\u001B[0m");
            }
        }
        catch (Exception e)
        {
            System.out.println("\u001B[31mThere was an error during the deletion of the book, please refer to the manual or the application developer\u001B[0m");
        }
    }
    
}
