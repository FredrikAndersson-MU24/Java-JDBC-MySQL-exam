import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final Connection conn = Database.getConnection();

    /**
     * Add a new row to the user table in the database
     *
     * @param name
     * @param username
     * @param password
     * @param loanPeriod
     * @param admin
     */
    public void addUser(String name, String username, String password, int loanPeriod, boolean admin) {
        String query = "INSERT INTO users (name, username, password, loan_period, admin_rights) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement addUser = conn.prepareStatement(query);
            addUser.setString(1, name);
            addUser.setString(2, username);
            addUser.setString(3, password);
            addUser.setInt(4, loanPeriod);
            addUser.setBoolean(5, admin);
            addUser.executeUpdate();
            System.out.println("User added!");
        } catch (SQLException e) {
            System.out.println("Failed to add user!");
            e.printStackTrace();
        }
    }

    /**
     * Check if provided username already exists in the database
     *
     * @param username
     * @return true - username exists in the db
     * false - username does not exist in the db
     */
    public boolean usernameExists(String username) {
        String query = "SELECT * FROM users WHERE username = ?;";
        boolean result = false;
        try {
            PreparedStatement usernameExist = conn.prepareStatement(query);
            usernameExist.setString(1, username);
            ResultSet rs = usernameExist.executeQuery();
            while (rs.next()) {
                if (rs.getString(3).equals(username)) {
                    result = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get usernames!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get a list of all registered users.
     *
     * @return List of AdminUser or RegisteredUser objects
     */
    public List<User> getAllUsers() {
        String query = "SELECT id, name, username, admin_rights FROM users;";
        List<User> listOfUsers = new ArrayList<>();
        try {
            Statement getUsernames = conn.createStatement();
            ResultSet rs = getUsernames.executeQuery(query);
            while (rs.next()) {
                if (rs.getBoolean(4)) {
                    listOfUsers.add(new AdminUser(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
                if (!rs.getBoolean(4)) {
                    listOfUsers.add(new RegisteredUser(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get all users!");
            e.printStackTrace();
        }
        return listOfUsers;
    }

    /**
     * Check if provided username and password matches
     *
     * @param username
     * @return true - password correct
     * false - password incorrect
     */
    public boolean passwordCorrect(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?;";
        boolean result = false;
        try {
            PreparedStatement getPwd = conn.prepareStatement(query);
            getPwd.setString(1, username);
            ResultSet rs = getPwd.executeQuery();
            if (rs.next()) {
                if (rs.getString(1).equals(password)) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get password!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Use to get a user object from the database. Intended use case is setting the current user when logging in.
     *
     * @param username Provide username to find in database.
     * @return AdminUser or RegisteredUser object
     */
    public User getUser(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        User user = null;
        boolean isAdmin;
        try {
            PreparedStatement getUser = conn.prepareStatement(query);
            getUser.setString(1, username);
            ResultSet rs = getUser.executeQuery();
            if (rs.next()) {
                isAdmin = rs.getBoolean(6);
                if (isAdmin) {
                    user = new AdminUser(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4));
                } else {
                    user = new RegisteredUser(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get user!");
            e.printStackTrace();
        }
        return user;
    }

}
