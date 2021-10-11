package ca.uqam.bookmanager.authentication;


import ca.uqam.bookmanager.AbstractSupervisor;
import ca.uqam.bookmanager.database.IDataSource;

import java.util.Objects;

public class UserSupervisor extends AbstractSupervisor {
    
    private final IUserProvider userProvider;
    
    public UserSupervisor(IDataSource datasource) {
        super();
        this.userProvider = new UserProvider(datasource);
    }
    
    public User AuthenticationMenu() {
        UserHomeAction userAction = null;
        User           user       = null;
        while (userAction == null || (userAction != UserHomeAction.QUIT && user == null)) {
            displayAuthenticationMenuOption();
            userAction = handleAuthenticationMenuOption();
            if (userAction == UserHomeAction.LOGIN)
                user = logIn();
            if (userAction == UserHomeAction.SIGNUP)
                user = signUp();
        }
        return user;
    }
    
    private void displayAuthenticationMenuOption() {
        System.out.println("\033[1;34mPlease, select one of the following options :\033[0m");
        System.out.println("\033[0;34m(1)\033[0m Log In");
        System.out.println("\033[0;34m(2)\033[0m Sign Up");
        System.out.println("\033[0;34m(0)\033[0m Leave BookManager");
    }
    
    private UserHomeAction handleAuthenticationMenuOption() {
        try {
            return UserHomeAction.values()[Integer.parseInt(this.scanner.nextLine())];
        } catch (Exception e) {
            return null;
        }
    }
    
    private User logIn() {
        System.out.println("\033[1;34mEnter your username :\033[0m");
        String username = this.scanner.nextLine();
        System.out.println("\033[1;34mEnter your password :\033[0m");
        String password   = this.scanner.nextLine();
        User   userObject = userProvider.readUser(username);
        if (userObject != null && userObject.verifyPassword(password)) {
            System.out.println("\033[0;32mSuccessfully logged in as " + username + ".\033[0m");
            return userObject;
        }
        System.out.println("\033[1;31mYour password or username is incorrect. Please try again.\033[0m");
        return null;
    }
    
    private User signUp() {
        System.out.println("\033[1;34mEnter your new username :\033[0m");
        String username = this.scanner.nextLine();
        System.out.println("\033[1;34mEnter your new password :\033[0m");
        String password = this.scanner.nextLine();
        System.out.println("\033[1;34mRe enter your password for confirmation:\033[0m");
        String passwordConfirmation = this.scanner.nextLine();
        if (!Objects.equals(password, passwordConfirmation)) {
            System.out.println("\033[1;31mYour password and its confirmation didn't match.\033[0m");
            return null;
        }
        User userObject = userProvider.createUser(username, password, UserRole.NORMAL);
        if (userObject == null) {
            System.out.println("\033[1;31mThere was an error during the creation of your user profile, your username is already in use. Please use another one. \033[0m");
        }
        else {
            System.out.println("\033[0;32m Succesfully signed and loged in as " + userObject.getUsername() + "\033[0m");
        }
        System.out.println();
        return userObject;
    }
    
    public void userMenu(User user) {
        UserMenuAction action = null;
        while (action != UserMenuAction.QUIT) {
            displayUserMenuOption(user.getRole());
            action = handleUserMenuOption(user.getRole());
            if (action == UserMenuAction.EDIT_CURRENT) {
                editUser(user);
            }
            if (action == UserMenuAction.DISPLAY_ALL) {
                displayAllUsers();
            }
            if (action == UserMenuAction.SEARCH) {
                searchUser();
            }
            if (action == UserMenuAction.CREATE) {
                createUser();
            }
            if (action == UserMenuAction.EDIT) {
                editUser();
            }
            if (action == UserMenuAction.DELETE) {
                deleteUsers();
            }
        }
    }
    
    private void displayUserMenuOption(UserRole role) {
        System.out.println("\033[1;34mPlease select one of the following option :\033[0m");
        System.out.println("\033[0;34m(1)\033[0m Edit current user");
        if (role == UserRole.ADMINISTRATOR) {
            System.out.println("\033[0;34m(2)\033[0m List all users");
            System.out.println("\033[0;34m(3)\033[0m Search users");
            System.out.println("\033[0;34m(4)\033[0m Create user");
            System.out.println("\033[0;34m(5)\033[0m Edit user (other than yours, just don't edit your own role obviously)");
            System.out.println("\033[0;34m(6)\033[0m Delete user (other than yours, i mean you can but its not recommended)");
        }
        System.out.println("\033[0;34m(0)\033[0m Leave");
    }
    
    private UserMenuAction handleUserMenuOption(UserRole role) {
        try {
            UserMenuAction action = UserMenuAction.values()[Integer.parseInt(scanner.nextLine())];
            if (role != UserRole.ADMINISTRATOR && !(action == UserMenuAction.EDIT_CURRENT || action == UserMenuAction.QUIT)) {
                return null;
            }
            return action;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private void editUser(User user) {
        System.out.printf("\033[1;34mEnter a new username\033[0m \033[0;33m(leave blank for no change)\033[0m \033[0;32m(current : %s)\033[1;34m :\033[0m \n", user.getUsername());
        String username = scanner.nextLine();
        System.out.println("\033[1;34mEnter a new password\033[0m \033[0;33m(leave blank for no change)\033[0m \033[1;34m:\033[0m");
        String password = scanner.nextLine();
        System.out.println("\033[1;34mConfirm password\033[0m \033[0;33m(leave blank for no change)\033[0m \033[1;34m:\033[0m");
        String passwordConfirmation = scanner.nextLine();
        if (!Objects.equals(username, "")) {
            user.setUsername(username);
        }
        if (!Objects.equals(password, "")) {
            if (!Objects.equals(password, passwordConfirmation)) {
                System.out.println("\033[1;31mThe password didn't match its confirmation\033[0m");
            }
            else {
                user.setPassword(password);
            }
        }
        userProvider.updateUser(user.getId(), user.getUsername(), user.getPasswordHash(), user.getRole());
    }
    
    private void displayAllUsers() {
        System.out.println("\033[1;34mAll users :\033[0m");
        User[] users = userProvider.readAllUser();
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.println();
    }
    
    private void searchUser() {
        UserSearchAction action = null;
        while (action != UserSearchAction.QUIT) {
            displayUserSearchOption();
            action = handleUserSearchOption();
            if (action == UserSearchAction.BY_USERNAME)
                searchUserByUsername();
            if (action == UserSearchAction.BY_ROLE)
                searchUserByRole();
        }
    }
    private void createUser() {
        try {
            System.out.println("\033[1;34mEnter a new username :\033[0m");
            String username = scanner.nextLine();
            System.out.println("\033[1;34mEnter a new password :\033[0m");
            String password = scanner.nextLine();
            System.out.println("\033[1;34mConfirm password :\033[0m");
            String passwordConfirmation = scanner.nextLine();
            if (Objects.equals(password, passwordConfirmation)) {
                int choice = -1;
                while (choice < 1 || choice > 3) {
                    System.out.println("\033[1;34mSelect one of the following option :\033[0m");
                    System.out.println("\033[0;34m(1)\033[0m Normal user");
                    System.out.println("\033[0;34m(2)\033[0m Librarian");
                    System.out.println("\033[0;34m(3)\033[0m Administrator");
                    choice = Integer.parseInt(scanner.nextLine());
                }
                UserRole role       = UserRole.values()[choice];
                User     userObject = userProvider.createUser(username, password, role);
                if (userObject == null) {
                    System.out.println("\u001B[31mThe username provided is already in use.\u001B[0m");
                }
            }
            else {
                System.out.println("\u001B[31mThe passwords you entered didn't match\u001B[0m");
            }
        } catch (NumberFormatException e) {
            System.out.println("\u001B[31mYou entered an invalid number\u001B[0m");
        }
    }
    private void editUser() {
        try {
            System.out.println("\033[1;34mEnter a user id :\033[0m");
            User user = userProvider.readUser(Integer.parseInt(scanner.nextLine()));
            if (user != null) {
                System.out.printf("\033[1;34mEnter a new username\033[0m \033[0;33m(leave blank for no change)\033[0m \033[0;32m(current : %s)\033[1;34m :\033[0m \n", user.getUsername());
                String username = scanner.nextLine();
                System.out.println("\033[1;34mEnter a new password\033[0m \033[0;33m(leave blank for no change)\033[0m \033[1;34m:\033[0m");
                String password = scanner.nextLine();
                System.out.println("\033[1;34mConfirm password\033[0m \033[0;33m(leave blank for no change)\033[0m \033[1;34m:\033[0m");
                String passwordConfirmation = scanner.nextLine();
                if (!Objects.equals(username, "")) {
                    user.setUsername(username);
                }
                if (!Objects.equals(password, "")) {
                    if (!Objects.equals(password, passwordConfirmation)) {
                        System.out.println("\033[1;31mThe password didn't match its confirmation\033[0m");
                    }
                    else {
                        user.setPassword(password);
                    }
                }
                int choice = -1;
                while (choice < 1 || choice > 3) {
                    System.out.println("\033[1;34mSelect one of the following option\033[0m");
                    System.out.println("\033[0;34m(1)\033[0m Normal user");
                    System.out.println("\033[0;34m(2)\033[0m Librarian");
                    System.out.println("\033[0;34m(3)\033[0m Administrator");
                    choice = Integer.parseInt(scanner.nextLine());
                }
                user.setRole(UserRole.values()[choice]);
                userProvider.updateUser(user.getId(), user.getUsername(), user.getPasswordHash(), user.getRole());
            }
            else {
                System.out.println("\u001B[31mThe user you are trying to delete doesn't exist\u001B[0m");
            }
        } catch (NumberFormatException e) {
            System.out.println("\u001B[31mYou entered an invalid number\u001B[0m");
        }
    }
    private void deleteUsers() {
        try {
            System.out.println("\033[1;34mEnter a user id :\033[0m");
            User user = userProvider.readUser(Integer.parseInt(scanner.nextLine()));
            if (user == null) {
                System.out.println("\u001B[31mThe user you are trying to delete doesn't exist\u001B[0m");
            }
            else {
                System.out.println("\033[1;33mThis is the the user you are trying to delete :\033[0m");
                System.out.println(user);
                String answer = "";
                while (!(Objects.equals(answer, "Y") || Objects.equals(answer, "N"))) {
                    System.out.println("\033[1;31mAre you sure you want to delete this user ? \033[1;34m(Y/N)\033[0m");
                    answer = scanner.nextLine();
                    if (Objects.equals(answer, "Y"))
                        userProvider.deleteUser(user.getId());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("\033[1;31mYou entered an invalid number\033[0m");
        }
        
    }
    private void displayUserSearchOption() {
        System.out.println("\033[1;34mPlease, select one of the following options :\033[0m");
        System.out.println("\033[0;34m(1)\033[0m Search by username");
        System.out.println("\033[0;34m(2)\033[0m Search by role");
        System.out.println("\033[0;34m(0)\033[0m Leave");
    }
    private UserSearchAction handleUserSearchOption() {
        try {
            return UserSearchAction.values()[Integer.parseInt(this.scanner.nextLine())];
        } catch (Exception e) {
            return null;
        }
    }
    public void searchUserByUsername() {
        System.out.println("\033[1;34mEnter a username :\033[0m");
        User[] users = userProvider.searchUserByUsername(scanner.nextLine());
        System.out.println("\033[1;34mResults :\033[0m");
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.println();
    }
    public void searchUserByRole() {
        try {
            int choice = -1;
            while (choice < 1 || choice > 3) {
                System.out.println("\033[1;34mSelect one of the following option\033[0m");
                System.out.println("\033[0;34m(1)\033[0m Normal user");
                System.out.println("\033[0;34m(2)\033[0m Librarian");
                System.out.println("\033[0;34m(3)\033[0m Administrator");
                choice = Integer.parseInt(scanner.nextLine());
            }
            User[] users = userProvider.searchUserByRole(UserRole.values()[choice]);
            System.out.println("\033[1;34mResult :\033[0m");
            for (User user : users) {
                System.out.println(user.toString());
            }
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("\033[1;31m[31mYou entered an invalid number\033[0m");
        }
    }
    
}
