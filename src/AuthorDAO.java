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
                listOfAuthors.add(createAuthorFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get authors!");
            e.printStackTrace();
        }
        return listOfAuthors;
    }

    public boolean authorExists(String author) {
        String query = "SELECT * FROM authors;";
        boolean result = false;
        try {
            Statement authorExists = conn.createStatement();
            ResultSet rs = authorExists.executeQuery(query);
            while (rs.next()) {
                if (rs.getString(2).equalsIgnoreCase(author)) {
                    result = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get authors!");
            e.printStackTrace();
        }
        return result;
    }

    public List<Author> getAuthorsByFreeTextSearch(String searchString){
        String query = "SELECT * FROM authors WHERE name LIKE ?;";
        List<Author> listOfAuthors = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,"%" + searchString + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                listOfAuthors.add(createAuthorFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get search results!");
            e.printStackTrace();
        }
        return listOfAuthors;
    }

    private Author createAuthorFromResultSet(ResultSet rs) throws SQLException {
        return new Author(
                rs.getInt(1),
                rs.getString(2));
    }

}
