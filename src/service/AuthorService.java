package service;

import dao.AuthorDAO;
import model.Author;
import model.Book;
import util.*;


import java.util.List;

public abstract class AuthorService {

    static AuthorDAO authorDAO = new AuthorDAO();

    public static void addAuthor() {
        String author;
        while (true) {
            author = InputHandler.getString("Please enter the name of the author (or 0(zero) to abort):");
            if (author.equals("0")) {
                return;
            }
            if (!authorDAO.authorExists(author)) {
                break;
            }
            System.out.println("There is already an author with that name in the database!");
        }
        authorDAO.addAuthor(author);
    }

    public static void getAuthors() {
        List<Author> listOfAuthors = authorDAO.getAuthors();
        if (listOfAuthors.isEmpty()) {
            System.out.println("There are no authors.");
        } else {
            printAuthorsAsTable(listOfAuthors);
        }
    }

    public static void getAuthorsByFreeTextSearch() {
        List<Author> listOfAuthors = authorDAO.getAuthorsByFreeTextSearch(InputHandler.getString("Please enter search term: "));
        if (listOfAuthors.isEmpty()) {
            System.out.println("No match.");
        } else {
            printAuthorsAsTable(listOfAuthors);
        }
    }

    public static void deleteAuthor() {
        getAuthors();
        int id = InputHandler.getPositiveInt("Please enter ID of the author you want to delete (or 0(zero) to abort): ");
        if (id == 0) {
            return;
        }
        Author author = authorDAO.getAuthorById(id);
        if (author != null) {
            if (authorDAO.deleteAuthor(id)){
                System.out.println("Author deleted successfully!");
            } else {
                System.out.println("Author on active loan. Cannot be deleted att the moment.");
            }
        } else {
                System.out.println("Unknown author ID!");
        }
    }

    public static void printAuthorsAsTable(List<Author> listOfAuthors) {
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("| %-10s | %-50s |", "Author ID", "Name");
        System.out.println("\n-------------------------------------------------------------------");
        listOfAuthors.forEach(l -> System.out.println(l.printAsTable()));
        System.out.println("-------------------------------------------------------------------");
    }

}
