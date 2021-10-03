package ca.uqam.bookmanager.authentication;

import ca.uqam.bookmanager.database.IDataSource;

import java.util.Objects;
import java.util.Scanner;

public class UserSupervisor
{
    
    private Scanner scanner = new Scanner(System.in);
    IUserProvider userProvider;
    
    public UserSupervisor(IDataSource datasource)
    {
        this.userProvider = new UserProvider(datasource);
    }
    
    
    public User AuthenticationMenu()
    {
        UserHomeAction userAction = null;
        User           user       = null;
        while (userAction == null || (userAction != UserHomeAction.QUIT && user == null))
        {
            DisplayAuthenticationMenuOption();
            userAction = HandleAuthenticationMenu();
            if (userAction == UserHomeAction.LOGIN)
                user = LogIn();
            if (userAction == UserHomeAction.SIGNUP)
                user = SignUp();
        }
        return user;
    }
    
    private void DisplayAuthenticationMenuOption()
    {
        System.out.println("Please select one of the following option :");
        System.out.println("(1) Log In");
        System.out.println("(2) Sign Up");
        System.out.println("(0) Leave BookManager");
    }
    
    private UserHomeAction HandleAuthenticationMenu()
    {
        
        try
        {
            return UserHomeAction.values()[Integer.parseInt(this.scanner.nextLine())];
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    private User LogIn()
    {
        System.out.println("Enter your username :");
        String username = this.scanner.nextLine();
        System.out.println("Enter your password :");
        String password   = this.scanner.nextLine();
        User   userObject = userProvider.ReadUser(username);
        if (userObject != null && userObject.verifyPassword(password))
        {
            System.out.println("Successfully logged in as" + username);
            return userObject;
        }
        return null;
    }
    
    private User SignUp()
    {
        System.out.println("Enter your new username :");
        String username = this.scanner.nextLine();
        System.out.println("Enter your new password :");
        String password = this.scanner.nextLine();
        System.out.println("Re enter your password for confirmation:");
        String passwordConfirmation = this.scanner.nextLine();
        System.out.println(password + "/" + passwordConfirmation);
        if (!Objects.equals(password, passwordConfirmation))
        {
            System.out.println("Your password and its confirmation didn't match.");
            return null;
        }
        User userObject = userProvider.CreateUser(username, password);
        if (userObject == null)
            System.out.println("There was an error during the creation of your user profile, your username is already in use. Please use another one");
        return userObject;
    }
   
}
