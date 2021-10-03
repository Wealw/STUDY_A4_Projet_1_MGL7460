package ca.uqam.bookmanager.authentication;


import ca.uqam.bookmanager.Supervisor;
import ca.uqam.bookmanager.database.IDataSource;


import java.util.Objects;

public class UserSupervisor extends Supervisor
{
    
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
        User userObject = userProvider.CreateUser(username, password, UserRole.NORMAL);
        if (userObject == null)
            System.out.println("There was an error during the creation of your user profile, your username is already in use. Please use another one");
        return userObject;
    }
    
    public void UserMenu(User user)
    {
        UserMenuAction action = null;
        while (action != UserMenuAction.QUIT)
        {
            DisplayUserMenuOption(user.getRole());
            action = HandleUserMenuOption(user.getRole());
            if (action == UserMenuAction.EDIT_CURRENT)
                EditUser(user);
            if (action == UserMenuAction.DISPLAY_ALL)
                DisplayAllUsers();
            if (action == UserMenuAction.SEARCH)
                SearchUser();
            if (action == UserMenuAction.CREATE)
                CreateUser();
            if (action == UserMenuAction.EDIT)
                EditUser();
            if (action == UserMenuAction.DELETE)
                DeleteUsers();
        }
    }
    
    private void DisplayUserMenuOption(UserRole role)
    {
        System.out.println("Please select one of the following option :");
        System.out.println("(1) Edit current user");
        if (role == UserRole.ADMINISTRATOR)
        {
            System.out.println("(2) List all users");
            System.out.println("(3) Search users");
            System.out.println("(4) Create user");
            System.out.println("(5) Edit user (other than yours, just don't edit your own role obviously)");
            System.out.println("(6) Delete user (other than yours, i mean you can but its not recommended)");
        }
        System.out.println("(0) ");
    }
    
    private UserMenuAction HandleUserMenuOption(UserRole role)
    {
        try
        {
            UserMenuAction action = UserMenuAction.values()[Integer.parseInt(scanner.nextLine())];
            if (role != UserRole.ADMINISTRATOR && action != UserMenuAction.EDIT_CURRENT)
            {
                return null;
            }
            return action;
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }
    
    private void EditUser(User user)
    {
        System.out.printf("Enter a new username (leave blank for no change) (current : %s)", user.getUsername());
        String username = scanner.nextLine();
        System.out.printf("Enter a new password (leave blank for no change)");
        String password = scanner.nextLine();
        System.out.printf("Confirm password (leave blank for no change)");
        String passwordConfirmation = scanner.nextLine();
        if (username != "")
        {
            user.setUsername(username);
        }
        if (!Objects.equals(password, ""))
        {
            if (Objects.equals(password, passwordConfirmation))
            {
                System.out.println("The password didn't match its confirmation");
            }
            else
            {
                user.setPassword(password);
            }
        }
        userProvider.UpdateUser(user.getId(), user.getUsername(), user.getPasswordHash(), user.getRole());
    }
    
    private void DisplayAllUsers()
    {
        User[] users = userProvider.ReadAllUser();
        for (User user : users)
        {
            System.out.println(user.ToString());
        }
    }
    
    private void SearchUser()
    {
    
    }
    
    private void CreateUser()
    {
        try
        {
            System.out.println("Enter a new username");
            String username = scanner.nextLine();
            System.out.println("Enter a new password");
            String password = scanner.nextLine();
            System.out.println("Confirm password");
            String passwordConfirmation = scanner.nextLine();
            int    choice               = -1;
            while (choice < 1 || choice > 3)
            {
                System.out.println("Select one of the following option");
                System.out.println("(1) Normal user");
                System.out.println("(2) Librarian");
                System.out.println("(3) Administrator");
                choice = Integer.parseInt(scanner.nextLine());
            }
            UserRole role = UserRole.values()[choice];
            userProvider.CreateUser(username, password, role);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\u001B[31mYou entered an invalid number\u001B[0m");
        }
    }
    
    private void EditUser()
    {
        try
        {
            System.out.println("Enter a user id:");
            User user = userProvider.ReadUser(Integer.parseInt(scanner.nextLine()));
            if (user != null)
            {
                EditUser(user);
            }
            else
            {
                System.out.println("\u001B[31mThe user you are trying to delete doesn't exist\u001B[0m");
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("\u001B[31mYou entered an invalid number\u001B[0m");
        }
    }
    
    private void DeleteUsers()
    {
        try
        {
            System.out.println("Enter a user id:");
            User user = userProvider.ReadUser(Integer.parseInt(scanner.nextLine()));
            if (user == null)
            {
                System.out.println("\u001B[31mThe user you are trying to delete doesn't exist\u001B[0m");
            }
            else
            {
                System.out.println("This is the the user you are trying to delete :");
                System.out.println(user.ToString());
                String answer = "";
                while (!(Objects.equals(answer, "Y") || Objects.equals(answer, "N")))
                {
                    System.out.println("\u001B[33mAre you sure you want to delete this user ? (Y/N)\u001B[0m");
                    answer = scanner.nextLine();
                    if (Objects.equals(answer, "Y"))
                        userProvider.DeleteUser(user.getId());
                }
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("\u001B[31mYou entered an invalid number\u001B[0m");
        }
        
    }
    
}
