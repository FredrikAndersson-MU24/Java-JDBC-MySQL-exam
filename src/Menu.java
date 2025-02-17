import java.util.List;

public class Menu {

    static BookDAO bookDAO = new BookDAO();
    static LoanDAO loanDAO = new LoanDAO();
    static AuthorDAO authorDAO = new AuthorDAO();


    public static void mainMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                     --- Welcome to the library ---
                     Please choose login method
                    
                    1. User
                    2. Admin
                    0. Quit
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
                    returnLoan();
                    break;
                case 3:
                    getActiveLoans();
                    break;
                case 4:
                    getAllBooks();
                    break;
                case 0:
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
                    4. Add author
                    5. List all authors
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
                case 4:
                    addAuthor();
                    break;
                case 5:
                    getAuthors();
                    break;
                case 0:
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
        Book book = bookDAO.getBookById(InputHandler.getPositiveInt("Please enter book ID: "));
        if (book != null && book.isAvailable()) {
            loanDAO.addLoan(
                    InputHandler.getPositiveInt("Please enter user ID: "),
                    book.getId(),
                    InputHandler.getPositiveInt("Please enter lend period in days: "));
        } else {
            if (book == null) {
                System.out.println("Unknown book ID!");
                return;
            }
            if (!book.isAvailable()) {
                System.out.println("Sorry, this book is not available right now.");
            }
        }
    }

    private static void returnLoan() {
        Book book = bookDAO.getBookById(InputHandler.getPositiveInt("Please enter book ID: "));
        if (book != null && !book.isAvailable()) {
            loanDAO.returnLoan(book.getId());
        } else {
            if (book == null) {
                System.out.println("Unknown book ID!");
                return;
            }
            if (book.isAvailable()) {
                System.out.println("Sorry, the specified book is not on loan. Please check book ID!");
            }
        }
    }

    private static void getAllLoans() {
        loanDAO.getAllLoans().forEach(l -> System.out.println(l));
    }

    private static void getActiveLoans() {
        loanDAO.getActiveLoans().forEach(l -> System.out.println(l));
    }

    private static void addAuthor(){
        authorDAO.addAuthor(InputHandler.getString("Please enter the name of the author: "));
    }

    private static void getAuthors(){
        authorDAO.getAuthors().forEach(a -> System.out.println(a));
    }

}
