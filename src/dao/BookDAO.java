package dao;

import model.Book;
import util.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection conn = Database.getConnection();


    public void addBook(String title, int authorId) {
        String query = "INSERT INTO books (title, author_id, available) VALUES (?, ?, ?);";
        boolean available = true;
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setInt(2, authorId);
            stmt.setBoolean(3, available);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to add book!");
            e.printStackTrace();
        }
    }

    public Book getBookById(int bookId) {
        String query = "SELECT * FROM books WHERE id = ?;";
        Book book = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                book = new Book(rs.getInt(1), rs.getString(2), rs.getInt(3),rs.getBoolean(4));
            }
        } catch (SQLException e){
            System.out.println("Failed to get book by ID!");
            e.printStackTrace();
        }
        return book;
    }

    public List<Book> getBooksByFreeTextSearch(String searchString){
        String query = "SELECT books.id AS 'model.Book ID', books.title AS Title, authors.id AS 'model.Author ID', authors.name AS 'model.Author', books.available AS Available FROM books\n" +
                "\tJOIN authors ON authors.id = books.author_id WHERE title LIKE ?;";
        List<Book> listOfBooks = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,"%" + searchString + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                listOfBooks.add(createBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get search results!");
            e.printStackTrace();
        }
        return listOfBooks;
    }

    public List<Book> getAllBooks() {
        String query = "SELECT books.id AS 'model.Book ID', books.title AS Title, authors.id AS 'model.Author ID', authors.name AS 'model.Author', books.available AS Available FROM books\n" +
                "\tJOIN authors ON authors.id = books.author_id;";
        List<Book> listOfBooks = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                listOfBooks.add(createBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get all books!");
            e.printStackTrace();
        }
        return listOfBooks;
    }

    public void deleteBook(int id) {
        String query = "DELETE FROM books WHERE id = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to delete book");
            e.printStackTrace();
        }
    }

    private Book createBookFromResultSet(ResultSet rs) throws SQLException {
        return new Book(
                rs.getInt(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getBoolean(5));
    }

}
