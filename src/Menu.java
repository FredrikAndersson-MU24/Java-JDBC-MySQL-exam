import java.util.List;

public class Menu {

    static BookDAO bookDAO = new BookDAO();
    static LoanDAO loanDAO = new LoanDAO();
    static AuthorDAO authorDAO = new AuthorDAO();
    static UserDAO userDAO = new UserDAO();

    static User currentUser;

    public static void userLoginMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                    --- User Login ---
                    1. Login as registered user or admin
                    2. Register as user
                    0. Quit
                    """);
            switch (choice) {
                case 1:
                    switch (authenticateLogin()) {
                        case 1 -> userMenu();
                        case 2 -> adminMenu();
                        case 0 -> {
                        }
                    }
                    break;
                case 2:
                    addUser();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Please enter valid menu option.");
                    break;
            }
        }
    }


    public static void userMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                     --- Welcome back %s! ---
                     --- What would you like to do today? ---
                    1. Lend a book
                    2. Return a book
                    3. Check for active loans
                    4. List all available books
                    5. Search books by title
                    6. Search authors
                    0. Log out
                    """.formatted(currentUser.getName()));
            switch (choice) {
                case 1:
                    lendBook();
                    break;
                case 2:
                    returnLoan();
                    break;
                case 3:
                    getUsersActiveLoans();
                    break;
                case 4:
                    getAllBooks();
                    break;
                case 5:
                    getBooksByFreeTextSearch();
                    break;
                case 6:
                    getAuthorsByFreeTextSearch();
                    break;
                case 0:
                    currentUser = null;
                    return;
                default:
                    System.out.println("Please enter valid menu option.");
                    break;
            }
        }
    }

    public static void adminMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                     --- Administration ---
                    --- Hi, %s! ---
                    1. Add a book
                    2. Delete a book
                    3. List all books
                    4. Add author
                    5. List all authors
                    6. Add user
                    7. View all active loans
                    8. View all loans
                    9. View all registered users
                    0. Log out
                    """.formatted(currentUser.getName()));
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
                case 6:
                    addUserAsAdmin();
                    break;
                case 7:
                    getActiveLoans();
                    break;
                case 8:
                    getAllLoans();
                    break;
                case 9:
                    getAllUsers();
                    break;
                case 0:
                    currentUser = null;
                    return;
                default:
                    System.out.println("Please enter valid menu option.");
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

    private static void getBooksByFreeTextSearch() {
        List<Book> listOfBooks = bookDAO.getBooksByFreeTextSearch(InputHandler.getString("Please enter search term: "));
        if (listOfBooks.isEmpty()){
            System.out.println("No match for search term.");
        } else {
            listOfBooks.forEach(b -> System.out.println(b));
        }
    }

    private static void deleteBook() {
        bookDAO.deleteBook(InputHandler.getPositiveInt("Please enter ID of the book you want to delete: "));
    }

    private static void lendBook() {
        getAllBooks();
        int id = InputHandler.getPositiveInt("Please enter book ID (or 0(zero) to abort): ");
        if (id == 0) {
            return;
        }
        Book book = bookDAO.getBookById(id);
        if (book != null && book.isAvailable()) {
            loanDAO.addLoan(
                    currentUser.getId(),
                    book.getId(),
                    currentUser.getLoanPeriod());
            System.out.println("Book added to your list of loans");
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
        getUsersActiveLoans();
        int id = InputHandler.getPositiveInt("Please enter book ID (or 0(zero) to abort): ");
        if (id == 0) {
            return;
        }
        Book book = bookDAO.getBookById(id);
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

    private static void getUsersActiveLoans() {
        List<Loan> loans = loanDAO.getUsersActiveLoans(currentUser);
        if (loans.isEmpty()) {
            System.out.println("You have no active loans.");
        } else {
            loans.forEach(l -> System.out.println(l.toStringAsUser()));
        }
    }

    private static void getActiveLoans() {
        List<Loan> loans = loanDAO.getAllActiveLoans();
        if (loans.isEmpty()) {
            System.out.println("There are no active loans.");
        } else {
            loans.forEach(l -> System.out.println(l));
        }
    }

    private static void addAuthor() {
        authorDAO.addAuthor(InputHandler.getString("Please enter the name of the author: "));
    }

    private static void getAuthors() {
        authorDAO.getAuthors().forEach(a -> System.out.println(a));
    }

    private static void getAuthorsByFreeTextSearch() {
        List<Author> listOfAuthors = authorDAO.getAuthorsByFreeTextSearch(InputHandler.getString("Please enter search term: "));
        if (listOfAuthors.isEmpty()){
            System.out.println("No match.");
        } else {
            listOfAuthors.forEach(a -> System.out.println(a));
        }
    }

    private static void addUser() {
        String name = InputHandler.getString("Please enter your name: ");
        String username;
        String password;
        while (true) {
            username = InputHandler.getString("Please enter your username: ");
            if (!usernameExists(username)) {
                break;
            }
            System.out.println("Username already exists!");
        }
        while (true) {
            password = InputHandler.getString("Please enter your password: ");
            if (!password.equalsIgnoreCase(username)){
                break;
            }
            System.out.println("Password can not be the same as your username!");
        }
        userDAO.addUser(name, username, password, 28, false);
    }

    private static void addUserAsAdmin() {
        String name = InputHandler.getString("Please enter name: ");
        String username;
        String password;
        while (true) {
            username = InputHandler.getString("Please enter username: ");
            if (!usernameExists(username)) {
                break;
            }
            System.out.println("Username already exists!");
        }
        while (true) {
            password = InputHandler.getString("Please enter password: ");
            if (!password.equalsIgnoreCase(username)){
                break;
            }
            System.out.println("Password can not be the same as username!");
        }
        int loanPeriod = InputHandler.getPositiveInt("Please enter loan period (default 28):");
        boolean admin = InputHandler.getBoolean("Should this be an admin user? Y/N: ");
        userDAO.addUser(name, username, password, loanPeriod, admin);
    }

    private static boolean usernameExists(String newUsername) {
        return userDAO.getUsernames().contains(newUsername.toLowerCase());
    }

    private static void getAllUsers(){
        List<User> listOfUsers = userDAO.getAllUsers();
        if (listOfUsers.isEmpty()) {
            System.out.println("No registered users found!");
        } else {
            listOfUsers.forEach(u -> System.out.println(u.toStringUsers()));
        }
    }

    /**
     * Lets the user input username and password. Checks if the user exists in the database and if the password is correct.
     * If the user inputs the number zero in either prompts the login attempt is aborted.
     *
     * @return An integer to use in a switch statement.
     * 0 if login is aborted.
     * 1 if RegisteredUser.
     * 2 if AdminUser.
     */
    private static int authenticateLogin() {
        int choice = 0; // Value to return if login is aborted at any point
        String username = authenticateUsername();
        if (!username.equals("0")) {
            if (authenticatePassword(username)) {
                currentUser = userDAO.getUser(username);
                choice = (currentUser instanceof AdminUser ? 2 : 1);//instanceof just to practice using it. Could also have used .isAdmin()
            }
        }
        return choice;
    }

    private static String authenticateUsername() {
        String username;
        while (true) {
            username = InputHandler.getString("Please enter your username (or 0(zero) to abort login attempt): ");
            if (username.equals("0")) {
                break;
            } else if (userDAO.getUsernames().contains(username.toLowerCase())) {
                break;
            }
            System.out.println("User not found!");
        }
        return username;
    }

    /**
     * Use to validate users password at login. Queries the database with the username and checks if the password matches.
     * @param username Provide username to check password for.
     * @return True if password is correct. Else false.
     */
    private static boolean authenticatePassword(String username) {
        boolean result;
        while (true) {
            String password = InputHandler.getString("Please enter your password  (or 0(zero) to abort login attempt): ");
            if (password.equals("0")) {
                result = false;
                break;
            } else if (userDAO.getPassword(username).equals(password)) {
                result = true;
                break;
            }
            System.out.println("Wrong password! Note that the password is case sensitive.");
        }
        return result;
    }
}