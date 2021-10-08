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
        System.out.println("\033[1;34mPlease, select one of the following options :\033[0m");
        System.out.println("\033[0;34m(1)\033[0m Log In");
        System.out.println("\033[0;34m(2)\033[0m Sign Up");
        System.out.println("\033[0;34m(0)\033[0m Leave BookManager");
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
        System.out.println("\033[1;34mEnter your username :\033[0m");
        String username = this.scanner.nextLine();
        System.out.println("\033[1;34mEnter your password :\033[0m");
        String  password = this.scanner.nextLine();
        User   userObject = userProvider.ReadUser(username);
        if (userObject != null && userObject.verifyPassword(password))
        {
            System.out.println("\033[0;32mSuccessfully logged in as " + username + ".\033[0m");
            return userObject;
        }
        System.out.println("\033[1;31mYour password or username is incorrect. Please try again.\033[0m");
        return null;
    }
    
    private User SignUp()
    {
        System.out.println("\033[1;34mEnter your new username :\033[0m");
        String username = this.scanner.nextLine();
        System.out.println("\033[1;34mEnter your new password :\033[0m");
        String password = this.scanner.nextLine();
        System.out.println("\033[1;34mRe enter your password for confirmation:\033[0m");
        String passwordConfirmation = this.scanner.nextLine();
        if (!Objects.equals(password, passwordConfirmation))
        {
            System.out.println("\033[1;31mYour password and its confirmation didn't match.\033[0m");
            return null;
        }
        User userObject = userProvider.CreateUser(username, password, UserRole.NORMAL);
        if (userObject == null)
            System.out.println("\033[1;31mThere was an error during the creation of your user profile, your username is already in use. Please use another one. \033[0m");
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
        System.out.println("\033[1;34mPlease select one of the following option :\033[0m");
        System.out.println("\033[0;34m(1)\033[0m Edit current user");
        if (role == UserRole.ADMINISTRATOR)
        {
            System.out.println("\033[0;34m(2)\033[0m List all users");
            System.out.println("\033[0;34m(3)\033[0m Search users");
            System.out.println("\033[0;34m(4)\033[0m Create user");
            System.out.println("\033[0;34m(5)\033[0m Edit user (other than yours, just don't edit your own role obviously)");
            System.out.println("\033[0;34m(6)\033[0m Delete user (other than yours, i mean you can but its not recommended)");
        }
        System.out.println("\033[0;34m(0)\033[0m Leave");
    }
    
    private UserMenuAction HandleUserMenuOption(UserRole role)
    {
        try
        {
            UserMenuAction action = UserMenuAction.values()[Integer.parseInt(scanner.nextLine())];
            if (role != UserRole.ADMINISTRATOR && !(action == UserMenuAction.EDIT_CURRENT || action == UserMenuAction.QUIT))
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
        System.out.printf("\033[1;34mEnter a new username\033[0m \033[0;33m(leave blank for no change)\033[0m \033[0;32m(current : %s)\033[1;34m :\033[0m \n", user.getUsername());
        String username = scanner.nextLine();
        System.out.println("\033[1;34mEnter a new password\033[0m \033[0;33m(leave blank for no change)\033[0m \033[1;34m:\033[0m");
        String password = scanner.nextLine();
        System.out.println("\033[1;34mConfirm password\033[0m \033[0;33m(leave blank for no change)\033[0m \033[1;34m:\033[0m");
        String passwordConfirmation = scanner.nextLine();
        if (!Objects.equals(username, ""))
        {
            user.setUsername(username);
        }
        if (!Objects.equals(password, ""))
        {
            if (!Objects.equals(password, passwordConfirmation))
            {
                System.out.println("\033[1;31mThe password didn't match its confirmation\033[0m");
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
        System.out.println("\033[1;34mAll users :\033[0m");
        User[] users = userProvider.ReadAllUser();
        for (User user : users)
        {
            System.out.println(user.ToString());
        }
        System.out.println();
    }
    
    private void SearchUser()
    {
        UserSearchAction action = null;
        while (action != UserSearchAction.QUIT)
        {
            DisplayUserSearchOption();
            action = HandleUserSearchOption();
            if (action == UserSearchAction.BY_USERNAME)
                SearchUserByUsername();
            if (action == UserSearchAction.BY_ROLE)
                SearchUserByRole();
        }
    }
    private void CreateUser()
    {
        try
        {
            System.out.println("\033[1;34mEnter a new username :\033[0m");
            String username = scanner.nextLine();
            System.out.println("\033[1;34mEnter a new password :\033[0m");
            String password = scanner.nextLine();
            System.out.println("\033[1;34mConfirm password :\033[0m");
            String passwordConfirmation = scanner.nextLine();
            if (Objects.equals(password, passwordConfirmation))
            {
                int choice = -1;
                while (choice < 1 || choice > 3)
                {
                    System.out.println("\033[1;34mSelect one of the following option :\033[0m");
                    System.out.println("\033[0;34m(1)\033[0m Normal user");
                    System.out.println("\033[0;34m(2)\033[0m Librarian");
                    System.out.println("\033[0;34m(3)\033[0m Administrator");
                    choice = Integer.parseInt(scanner.nextLine());
                }
                UserRole role = UserRole.values()[choice];
                userProvider.CreateUser(username, password, role);
            }
            else
            {
                System.out.println("\u001B[31mThe passwords you entered didn't match\u001B[0m");
            }
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
            System.out.println("\033[1;34mEnter a user id :\033[0m");
            User user = userProvider.ReadUser(Integer.parseInt(scanner.nextLine()));
            if (user != null)
            {
                System.out.printf("\033[1;34mEnter a new username\033[0m \033[0;33m(leave blank for no change)\033[0m \033[0;32m(current : %s)\033[1;34m :\033[0m \n", user.getUsername());
                String username = scanner.nextLine();
                System.out.println("\033[1;34mEnter a new password\033[0m \033[0;33m(leave blank for no change)\033[0m \033[1;34m:\033[0m");
                String password = scanner.nextLine();
                System.out.println("\033[1;34mConfirm password\033[0m \033[0;33m(leave blank for no change)\033[0m \033[1;34m:\033[0m");
                String passwordConfirmation = scanner.nextLine();
                if (!Objects.equals(username, ""))
                {
                    user.setUsername(username);
                }
                if (!Objects.equals(password, ""))
                {
                    if (!Objects.equals(password, passwordConfirmation))
                    {
                        System.out.println("\033[1;31mThe password didn't match its confirmation\033[0m");
                    }
                    else
                    {
                        user.setPassword(password);
                    }
                }
                int choice = -1;
                while (choice < 1 || choice > 3)
                {
                    System.out.println("\033[1;34mSelect one of the following option\033[0m");
                    System.out.println("\033[0;34m(1)\033[0m Normal user");
                    System.out.println("\033[0;34m(2)\033[0m Librarian");
                    System.out.println("\033[0;34m(3)\033[0m Administrator");
                    choice = Integer.parseInt(scanner.nextLine());
                }
                user.setRole(UserRole.values()[choice]);
                userProvider.UpdateUser(user.getId(), user.getUsername(), user.getPasswordHash(), user.getRole());
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
            System.out.println("\033[1;34mEnter a user id :\033[0m");
            User user = userProvider.ReadUser(Integer.parseInt(scanner.nextLine()));
            if (user == null)
            {
                System.out.println("\u001B[31mThe user you are trying to delete doesn't exist\u001B[0m");
            }
            else
            {
                System.out.println("\033[1;33mThis is the the user you are trying to delete :\033[0m");
                System.out.println(user.ToString());
                String answer = "";
                while (!(Objects.equals(answer, "Y") || Objects.equals(answer, "N")))
                {
                    System.out.println("\033[1;31mAre you sure you want to delete this user ? \033[1;34m(Y/N)\033[0m");
                    answer = scanner.nextLine();
                    if (Objects.equals(answer, "Y"))
                        userProvider.DeleteUser(user.getId());
                }
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("\033[1;31mYou entered an invalid number\033[0m");
        }
        
    }
    private void DisplayUserSearchOption()
    {
        System.out.println("\033[1;34mPlease, select one of the following options :\033[0m");
        System.out.println("\033[0;34m(1)\033[0m Search by username");
        System.out.println("\033[0;34m(2)\033[0m Search by role");
        System.out.println("\033[0;34m(0)\033[0m Leave");
    }
    private UserSearchAction HandleUserSearchOption()
    {
        try
        {
            return UserSearchAction.values()[Integer.parseInt(this.scanner.nextLine())];
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public void SearchUserByUsername()
    {
        System.out.println("\033[1;34mEnter a username :\033[0m");
        User[] users = userProvider.SearchUserByUsername(scanner.nextLine());
        System.out.println("\033[1;34mResults :\033[0m");
        for (User user : users)
        {
            System.out.println(user.ToString());
        }
        System.out.println();
    }
    public void SearchUserByRole()
    {
        try
        {
            int choice = -1;
            while (choice < 1 || choice > 3)
            {
                System.out.println("\033[1;34mSelect one of the following option\033[0m");
                System.out.println("\033[0;34m(1)\033[0m Normal user");
                System.out.println("\033[0;34m(2)\033[0m Librarian");
                System.out.println("\033[0;34m(3)\033[0m Administrator");
                choice = Integer.parseInt(scanner.nextLine());
            }
            User[] users = userProvider.SearchUserByRole(UserRole.values()[choice]);
            System.out.println("\033[1;34mResult :\033[0m");
            for (User user : users)
            {
                System.out.println(user.ToString());
            }
            System.out.println();
        }
        catch (NumberFormatException e)
        {
            System.out.println("\033[1;31m[31mYou entered an invalid number\033[0m");
        }
    }
    
}
