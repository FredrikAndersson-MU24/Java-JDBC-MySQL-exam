import java.util.List;

public class BookService {

    static BookDAO bookDAO = new BookDAO();

    public static void addBook() {
        String title = InputHandler.getString("Please enter book title (or 0(zero) to abort):");
        if (title.equals("0")) {
            return;
        }
        LibraryFacade.getAuthors();
        int authorId = InputHandler.getPositiveInt("Please enter Author ID  (or 0(zero) to abort): ");
        if (authorId == 0) {
            return;
        }
        bookDAO.addBook(title, authorId);
    }

    /**
     *  Gets all books from database. Argument states what to print. Prints as a table.
     * @param type
     * available - only books that are available
     * all - all books regardless of status
     * idAndTitle - print all books, but only ID and title.
     */
    public static void getBooks(String type) {
        List<Book> listOfBooks = bookDAO.getAllBooks();
        if (listOfBooks.isEmpty()) {
            System.out.println("There are no books.");
            return;
        }
        switch (type) {
            case "all" -> printAllBooksAsTable(listOfBooks);
            case "available" -> printAvailableBooksAsTable(listOfBooks);
            case "idAndTitle" -> printBookIdAndTitleAsTable(listOfBooks);
        }
    }


    public static void getBooksByFreeTextSearch() {
        List<Book> listOfBooks = bookDAO.getBooksByFreeTextSearch(InputHandler.getString("Please enter search term: "));
        if (listOfBooks.isEmpty()) {
            System.out.println("No match for search term.");
        } else {
            printAllBooksAsTable(listOfBooks);
        }
    }

    public static void deleteBook() {
        getBooks("idAndTitle");
        int id = InputHandler.getPositiveInt("Please enter ID of the book you want to delete (or 0(zero) to abort): ");
        if (id == 0) {
            return;
        }
        Book book = bookDAO.getBookById(id);
        if (book != null && book.isAvailable()) {
            System.out.println(book.isAvailable());
            bookDAO.deleteBook(id);
            System.out.println("Book deleted.");
        } else {
            if (book == null) {
                System.out.println("Unknown book ID!");
                return;
            }
            if (!book.isAvailable()) {
                System.out.println("Sorry, this book is on active loan and cannot be deleted at the moment.");
                // TODO Choice to get reminder when book is returned
            }
        }
    }

    public static void printAvailableBooksAsTable(List<Book> listOfBooks) {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-50s | %-10s | %-30s | %-10s |", "Book ID", "Title", "Author ID", "Author", "Available");
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------");
        listOfBooks.forEach(b -> {
            if (b.isAvailable()) {
                System.out.println(b.printAsTable());
            }});
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void printAllBooksAsTable(List<Book> listOfBooks) {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-50s | %-10s | %-30s | %-10s |", "Book ID", "Title", "Author ID", "Author", "Available");
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------");
        listOfBooks.forEach(b -> System.out.println(b.printAsTable()));
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }


    public static void printBookIdAndTitleAsTable(List<Book> listOfBooks) {
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("| %-10s | %-50s |", "Book ID", "Title");
        System.out.println("\n-------------------------------------------------------------------");
        listOfBooks.forEach(b -> System.out.println(b.printIdAndTitleAsTable()));
        System.out.println("-------------------------------------------------------------------");
    }

}
