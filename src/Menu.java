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
                    getBooks("available");
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
                    getBooks("all");
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
        String title = InputHandler.getString("Please enter book title (or 0(zero) to abort):");
        if (title.equals("0")) {
            return;
        }
        getAuthors();
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
    private static void getBooks(String type) {
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

    private static void printAvailableBooksAsTable(List<Book> listOfBooks) {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-50s | %-10s | %-30s | %-10s |", "Book ID", "Title", "Author ID", "Author", "Available");
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------");
        listOfBooks.forEach(b -> {
            if (b.isAvailable()) {
                System.out.println(b.printAsTable());
            }});
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void printAllBooksAsTable(List<Book> listOfBooks) {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-50s | %-10s | %-30s | %-10s |", "Book ID", "Title", "Author ID", "Author", "Available");
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------");
        listOfBooks.forEach(b -> System.out.println(b.printAsTable()));
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }


    private static void printBookIdAndTitleAsTable(List<Book> listOfBooks) {
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("| %-10s | %-50s |", "Book ID", "Title");
        System.out.println("\n-------------------------------------------------------------------");
        listOfBooks.forEach(b -> System.out.println(b.printIdAndTitleAsTable()));
        System.out.println("-------------------------------------------------------------------");
    }

    private static void getBooksByFreeTextSearch() {
        List<Book> listOfBooks = bookDAO.getBooksByFreeTextSearch(InputHandler.getString("Please enter search term: "));
        if (listOfBooks.isEmpty()) {
            System.out.println("No match for search term.");
        } else {
            printAllBooksAsTable(listOfBooks);
        }
    }

    private static void deleteBook() {
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

    private static void lendBook() {
        getBooks("available");
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
                // TODO Put on waiting list and get notified when available
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
        List<Loan> listOfLoans = loanDAO.getAllLoans();
        if (listOfLoans.isEmpty()) {
            System.out.println("There are no recorded loans.");
        } else {
            printLoansAsTableAsAdmin(listOfLoans);
        }
    }

    private static void getUsersActiveLoans() {
        List<Loan> listOfLoans = loanDAO.getUsersActiveLoans(currentUser);
        if (listOfLoans.isEmpty()) {
            System.out.println("You have no active loans.");
        } else {
            printLoansAsTableAsUser(listOfLoans);
        }
    }

    private static void getActiveLoans() {
        List<Loan> listOfLoans = loanDAO.getAllActiveLoans();
        if (listOfLoans.isEmpty()) {
            System.out.println("There are no active loans.");
        } else {
            printLoansAsTableAsAdmin(listOfLoans);
        }
    }


    private static void printLoansAsTableAsUser(List<Loan> listOfLoans) {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s |  %-50s | %-15s | %-15s | %-10s |", "Loan ID", "Book ID", "Title", "Loan date", "Return date", "Late");
        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------");
        listOfLoans.forEach(l -> System.out.println(l.printAsTableAsUser()));
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void printLoansAsTableAsAdmin(List<Loan> listOfLoans) {
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-10s | %-15s | %-15s | %-10s | %-10s |", "Loan ID", "User Id", "Book ID", "Loan date", "Return date", "Returned" , "Late");
        System.out.println("\n------------------------------------------------------------------------------------------------------");
        listOfLoans.forEach(l -> System.out.println(l.printAsTableAsAdmin()));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }



    private static void addAuthor() {
        String author = InputHandler.getString("Please enter the name of the author (or 0(zero) to abort):");
        if (author.equals("0")) {
            return;
        }
        authorDAO.addAuthor(author);
    }

    private static void getAuthors() {
        List<Author> listOfAuthors = authorDAO.getAuthors();
        if (listOfAuthors.isEmpty()) {
            System.out.println("There are no authors.");
        } else {
            printAuthorsAsTable(listOfAuthors);
        }
    }

    private static void getAuthorsByFreeTextSearch() {
        List<Author> listOfAuthors = authorDAO.getAuthorsByFreeTextSearch(InputHandler.getString("Please enter search term: "));
        if (listOfAuthors.isEmpty()) {
            System.out.println("No match.");
        } else {
            printAuthorsAsTable(listOfAuthors);
        }
    }

    private static void printAuthorsAsTable(List<Author> listOfAuthors) {
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("| %-10s | %-50s |", "Author ID", "Name");
        System.out.println("\n-------------------------------------------------------------------");
        listOfAuthors.forEach(l -> System.out.println(l.printAsTable()));
        System.out.println("-------------------------------------------------------------------");
    }

    private static void addUser() {
        String name = InputHandler.getString("Please enter your name (or 0(zero) to abort): ");
        if (name.equals("0")) {
            return;
        }
        String username;
        String password;
        while (true) {
            username = InputHandler.getString("Please enter your username (or 0(zero) to abort): ");
            if (username.equals("0")) {
                return;
            }
            if (!usernameExists(username)) {
                break;
            }
            System.out.println("Username already exists!");
        }
        while (true) {
            password = InputHandler.getString("Please enter your password (or 0(zero) to abort): ");
            if (password.equals("0")) {
                return;
            }
            if (!password.equalsIgnoreCase(username)) {
                break;
            }
            System.out.println("Password can not be the same as your username!");
        }
        userDAO.addUser(name, username, password, 28, false);
    }

    private static void addUserAsAdmin() {
        String name = InputHandler.getString("Please enter name (or 0(zero) to abort): ");
        if (name.equals("0")) {
            return;
        }
        String username;
        String password;
        while (true) {
            username = InputHandler.getString("Please enter username (or 0(zero) to abort): ");
            if (username.equals("0")) {
                return;
            }
            if (!usernameExists(username)) {
                break;
            }
            System.out.println("Username already exists!");
        }
        while (true) {
            password = InputHandler.getString("Please enter password (or 0(zero) to abort): ");
            if (password.equals("0")) {
                return;
            }
            if (!password.equalsIgnoreCase(username)) {
                break;
            }
            System.out.println("Password can not be the same as username!");
        }
        int loanPeriod = InputHandler.getPositiveInt("Please enter loan period (default 28):");
        boolean admin = InputHandler.getBoolean("Should this be an admin user? Y/N: ");
        userDAO.addUser(name, username, password, loanPeriod, admin);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean usernameExists(String newUsername) {
        return userDAO.getUsernames().contains(newUsername.toLowerCase());
    }

    private static void getAllUsers() {
        List<User> listOfUsers = userDAO.getAllUsers();
        if (listOfUsers.isEmpty()) {
            System.out.println("No registered users found!");
        } else {
            printUsersAsTable(listOfUsers);
        }
    }


    private static void printUsersAsTable(List<User> listOfUsers) {
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-50s | %-20s | %-10s |", "User ID", "Name", "Username", "Admin");
        System.out.println("\n-------------------------------------------------------------------------------------------------------");
        listOfUsers.forEach(u -> System.out.println(u.printAsTable()));
        System.out.println("-------------------------------------------------------------------------------------------------------");
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

    // TODO Prohibit entering an author that already exists
    // TODO Error handling when trying to delete a book on loan
}