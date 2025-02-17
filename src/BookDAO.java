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

    public List<Book> getAllBooks(){
        String query = "SELECT * FROM books;";
        List<Book> listOfBooks = new ArrayList<>();

        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                listOfBooks.add(new Book(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4)));
            }

        } catch (SQLException e) {
            System.out.println("Failed to get all books!");
            e.printStackTrace();
        }

        return listOfBooks;
    }


}
