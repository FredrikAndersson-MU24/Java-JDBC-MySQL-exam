package service;

import dao.UserDAO;
import model.AdminUser;
import model.User;
import util.*;

public abstract  class LoginService {

    static UserDAO userDAO = new UserDAO();
    static User currentUser;


    /**
     * Lets the user input username and password. Checks if the user exists in the database and if the password is correct.
     * If the user inputs the number zero in either prompts the login attempt is aborted.
     *
     * @return An integer to use in a switch statement.
     * 0 if login is aborted.
     * 1 if model.RegisteredUser.
     * 2 if model.AdminUser.
     */
    public static int authenticateLogin() {
        int choice = 0; // Return value if login is aborted at any point
        String username = authenticateUsername();
        if (!username.equals("0")) {
            if (authenticatePassword(username)) {
                currentUser = userDAO.getUser(username);
                choice = (currentUser instanceof AdminUser ? 2 : 1);//instanceof just to practice using it. Could also have used .isAdmin()
            }
        }
        return choice;
    }

    private static String authenticateUsername() {
        String username;
        while (true) {
            username = InputHandler.getString("Please enter your username (or 0(zero) to abort login attempt): ").toLowerCase();
            if (username.equals("0")) {
                break;
            } else if (LibraryFacade.usernameExists(username)) {
                break;
            }
            System.out.println("User not found!");
        }
        return username;
    }

    /**
     * Validate users password at login. Queries the database with the username and password and checks if they match.
     * @param username Provide username to check password for.
     * @return True if password is correct. Else false.
     */
    private static boolean authenticatePassword(String username) {
        boolean result;
        while (true) {
            String password = InputHandler.getString("Please enter your password  (or 0(zero) to abort login attempt): ");
            if (password.equals("0")) {
                result = false;
                break;
            } else if (userDAO.passwordCorrect(username, password)) {
                result = true;
                break;
            }
            System.out.println("Wrong password! Note that the password is case sensitive.");
        }
        return result;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        LoginService.currentUser = currentUser;
    }
}
