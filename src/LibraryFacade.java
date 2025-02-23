import java.util.List;

public abstract class LibraryFacade {

    // Authors
    public static void addAuthor() {
        AuthorService.addAuthor();
    }

    public static void getAuthors() {
        AuthorService.getAuthors();
    }

    public static void getAuthorsByFreeTextSearch() {
        AuthorService.getAuthorsByFreeTextSearch();
    }


    // Users
    public static void getAllUsers() {
        UserService.getAllUsers();
    }

    public static boolean usernameExists(String username) {
        return UserService.usernameExists(username);
    }

    public static void addUserAsAdmin() {
        UserService.addUserAsAdmin();
    }

    public static void addUser() {
        UserService.addUser();
    }


    // Login
    public static int authenticateLogin() {
        return LoginService.authenticateLogin();
    }

    public static User getCurrentUser() {
        return LoginService.currentUser;
    }

    public static void setCurrentUserNull() {
        LoginService.currentUser = null;
    }


    // Loans
    public static void lendBook() {
        LoanService.lendBook();
    }

    public static void returnLoan() {
        LoanService.returnLoan();
    }

    public static void getAllLoans() {
        LoanService.getAllLoans();
    }

    public static void getUsersActiveLoans() {
        LoanService.getUsersActiveLoans();
    }

    public static void getActiveLoans() {
        LoanService.getActiveLoans();
    }


    // Books
    public static void addBook() {
        BookService.addBook();
    }

    public static void getBooks(String type) {
        BookService.getBooks(type);
    }

    public static void getBooksByFreeTextSearch() {
        BookService.getBooksByFreeTextSearch();
    }

    public static void deleteBook() {
        BookService.deleteBook();
    }


}
