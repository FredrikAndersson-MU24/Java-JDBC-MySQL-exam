import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    private Connection conn = Database.getConnection();

    public void addAuthor(String name) {
        String query = "INSERT INTO authors (name) VALUES (?);";
        try {
            PreparedStatement addAuthor = conn.prepareStatement(query);
            addAuthor.setString(1, name);
            addAuthor.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to add author!");
            e.printStackTrace();
        }
    }

    public List<Author> getAuthors() {
        String query = "SELECT * FROM authors;";
        List<Author> listOfAuthors = new ArrayList<>();
        try {
            Statement getAuthors = conn.createStatement();
            ResultSet rs = getAuthors.executeQuery(query);
            while (rs.next()) {
                listOfAuthors.add(new Author(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get authors!");
            e.printStackTrace();
        }
        return listOfAuthors;
    }

}
