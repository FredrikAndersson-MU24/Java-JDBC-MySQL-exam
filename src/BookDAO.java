import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDAO {

    public void addBook(String title, int authorId){
        String query = "INSERT INTO books (title, author) VALUES ?, ?;";

        try {
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,title);
            stmt.setInt(2,authorId);
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("Failed to add book!");
            e.printStackTrace();
        }

    }


}
