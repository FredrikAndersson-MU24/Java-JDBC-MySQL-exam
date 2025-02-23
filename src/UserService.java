import java.util.List;

public class UserService {

    static UserDAO userDAO = new UserDAO();

    public static void addUser() {
        String name = InputHandler.getString("Please enter your name (or 0(zero) to abort): ");
        if (name.equals("0")) {
            return;
        }
        String username;
        String password;
        while (true) {
            username = InputHandler.getString("Please enter your username (or 0(zero) to abort): ");
            if (username.equals("0")) {
                return;
            }
            if (!usernameExists(username)) {
                break;
            }
            System.out.println("Username already exists!");
        }
        while (true) {
            password = InputHandler.getString("Please enter your password (or 0(zero) to abort): ");
            if (password.equals("0")) {
                return;
            }
            if (!password.equalsIgnoreCase(username)) {
                break;
            }
            System.out.println("Password can not be the same as your username!");
        }
        userDAO.addUser(name, username, password, 28, false);
    }

    public static void addUserAsAdmin() {
        String name = InputHandler.getString("Please enter name (or 0(zero) to abort): ");
        if (name.equals("0")) {
            return;
        }
        String username;
        String password;
        while (true) {
            username = InputHandler.getString("Please enter username (or 0(zero) to abort): ");
            if (username.equals("0")) {
                return;
            }
            if (!usernameExists(username)) {
                break;
            }
            System.out.println("Username already exists!");
        }
        while (true) {
            password = InputHandler.getString("Please enter password (or 0(zero) to abort): ");
            if (password.equals("0")) {
                return;
            }
            if (!password.equalsIgnoreCase(username)) {
                break;
            }
            System.out.println("Password can not be the same as username!");
        }
        int loanPeriod = InputHandler.getPositiveInt("Please enter loan period (default 28):");
        boolean admin = InputHandler.getBoolean("Should this be an admin user? Y/N: ");
        userDAO.addUser(name, username, password, loanPeriod, admin);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean usernameExists(String username) {
        return userDAO.usernameExists(username.toLowerCase());
    }

    public static void getAllUsers() {
        List<User> listOfUsers = userDAO.getAllUsers();
        if (listOfUsers.isEmpty()) {
            System.out.println("No registered users found!");
        } else {
            printUsersAsTable(listOfUsers);
        }
    }

    public static void printUsersAsTable(List<User> listOfUsers) {
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-50s | %-20s | %-10s |", "User ID", "Name", "Username", "Admin");
        System.out.println("\n-------------------------------------------------------------------------------------------------------");
        listOfUsers.forEach(u -> System.out.println(u.printAsTable()));
        System.out.println("-------------------------------------------------------------------------------------------------------");
    }

}
