import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection conn = Database.getConnection();

    public void addUser(String name, String username, String password, int loanPeriod, boolean admin){
        String query = "INSERT INTO users (name, username, password, loan_period, admin_rights) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement addUser = conn.prepareStatement(query);
            addUser.setString(1,name);
            addUser.setString(2,username);
            addUser.setString(3,password);
            addUser.setInt(4,loanPeriod);
            addUser.setBoolean(5,admin);
            addUser.executeUpdate();
            System.out.println("User added!");
        } catch (SQLException e) {
            System.out.println("Failed to add user!");
            e.printStackTrace();
        }
    }

    public List<String> getUsernames(){
        String query = "SELECT username FROM users";
        List<String> usernames = new ArrayList<>();
        try{
            Statement getUsernames = conn.createStatement();
            ResultSet rs = getUsernames.executeQuery(query);
            while(rs.next()){
                usernames.add(rs.getString(1).toLowerCase());
            }
            System.out.println("Successfully retrieved usernames from DB");
        } catch (SQLException e){
            System.out.println("Failed to get usernames!");
            e.printStackTrace();
        }
        return usernames;
    }



}
