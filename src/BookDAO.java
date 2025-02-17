import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public void addBook(String title, int authorId){
        String query = "INSERT INTO books (title, author, available) VALUES (?, ?, ?);";
        boolean available = true;
        try {
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,title);
            stmt.setInt(2,authorId);
            stmt.setBoolean(3, available);
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("Failed to add book!");
            e.printStackTrace();
        }
    }


}
