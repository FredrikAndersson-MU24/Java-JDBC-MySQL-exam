import java.util.List;

public class Menu {

    static BookDAO bookDAO = new BookDAO();
    static LoanDAO loanDAO = new LoanDAO();


    public static void mainMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                     --- Welcome to the library ---
                     Please choose login method
                    
                    1. User
                    2. Admin
                    """);
            switch (choice) {
                case 1:
                    userMenu();
                    break;
                case 2:
                    adminMenu();
                    break;
                case 0:
                    System.out.println("quit");
                    return;
                default:
                    System.out.println("error");
                    break;
            }
        }
    }

    public static void userMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                     --- User menu ---
                    1. Lend a book
                    2. Return a book
                    3. Check for active loans
                    4. List all available books
                    0. Go back
                    """);
            switch (choice) {
                case 1:
                    lendBook();
                    break;
                case 2:
                    System.out.println("return");
                    break;
                case 3:
                    System.out.println("active loans");
                    break;
                case 4:
                    System.out.println("list");
                    break;
                case 0:
                    System.out.println("quit");
                    return;
                default:
                    System.out.println("error");
                    break;
            }
        }
    }

    public static void adminMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                     --- Administration ---
                    
                    1. Add a book
                    2. Delete a book
                    3. List all books
                    0. Go back
                    """);
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    getAllBooks();
                    break;
                case 0:
                    System.out.println("quit");
                    return;
                default:
                    System.out.println("error");
                    break;
            }
        }
    }

    public static void addBook() {
        bookDAO.addBook(InputHandler.getString("Title"), InputHandler.getPositiveInt("Author ID"));
    }

    private static void getAllBooks() {
        List<Book> listOfBooks = bookDAO.getAllBooks();
        listOfBooks.forEach(b -> System.out.println(b));
    }

    private static void deleteBook() {
        bookDAO.deleteBook(InputHandler.getPositiveInt("Please enter ID of the book you want to delete: "));
    }

    private static void lendBook() {
        loanDAO.addLoan(
                InputHandler.getPositiveInt("Please enter user ID: "),
                InputHandler.getPositiveInt("Please enter book ID: "),
                InputHandler.getPositiveInt("Please enter lend period in days: ")
        );

    }
}
