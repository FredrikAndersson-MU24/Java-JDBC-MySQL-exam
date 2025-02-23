public class Menu {

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
                    switch (LibraryFacade.authenticateLogin()) {
                        case 1 -> userMenu();
                        case 2 -> adminMenu();
                        case 0 -> {
                        }
                    }
                    break;
                case 2:
                    LibraryFacade.addUser();
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
                    """.formatted(LibraryFacade.getCurrentUser().getName()));
            switch (choice) {
                case 1:
                    LibraryFacade.lendBook();
                    break;
                case 2:
                    LibraryFacade.returnLoan();
                    break;
                case 3:
                    LibraryFacade.getUsersActiveLoans();
                    break;
                case 4:
                    LibraryFacade.getBooks("available");
                    break;
                case 5:
                    LibraryFacade.getBooksByFreeTextSearch();
                    break;
                case 6:
                    LibraryFacade.getAuthorsByFreeTextSearch();
                    break;
                case 0:
                    LibraryFacade.setCurrentUserNull();
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
                    1. Books
                    2. Authors
                    3. Users
                    4. Loans
                    
                    0. Log out
                    """.formatted(LibraryFacade.getCurrentUser().getName()));
            switch (choice) {
                case 1:
                    adminBookMenu();
                    break;
                case 2:
                    adminAuthorMenu();
                    break;
                case 3:
                    adminUserMenu();
                    break;
                case 4:
                    adminLoanMenu();
                    break;
                case 0:
                    LibraryFacade.setCurrentUserNull();
                    return;
                default:
                    System.out.println("Please enter valid menu option.");
                    break;
            }
        }
    }

    public static void adminBookMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                     --- Book administration ---
                    1. Add a book
                    2. Delete a book
                    3. List all books
                    4. Search books by title
                    
                    0. Go back
                    """);
            switch (choice) {
                case 1:
                    LibraryFacade.addBook();
                    break;
                case 2:
                    LibraryFacade.deleteBook();
                    break;
                case 3:
                    LibraryFacade.getBooks("all");
                    break;
                case 4:
                    LibraryFacade.getBooksByFreeTextSearch();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Please enter valid menu option.");
                    break;
            }
        }
    }

    public static void adminAuthorMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                     --- Author administration ---
                    1. Add author
                    2. List all authors
                    3. Search authors
                    
                    0. Go back
                    """);
            switch (choice) {
                case 1:
                    LibraryFacade.addAuthor();
                    break;
                case 2:
                    LibraryFacade.getAuthors();
                    break;
                case 3:
                    LibraryFacade.getAuthorsByFreeTextSearch();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Please enter valid menu option.");
                    break;
            }
        }
    }

    public static void adminUserMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                     --- User administration ---
                    1. Add user
                    2. View all registered users
                    
                    0. Go back
                    """);
            switch (choice) {
                case 1:
                    LibraryFacade.addUserAsAdmin();
                    break;
                case 9:
                    LibraryFacade.getAllUsers();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Please enter valid menu option.");
                    break;
            }
        }
    }

    public static void adminLoanMenu() {
        while (true) {
            int choice = InputHandler.getPositiveInt("""
                     --- Loan administration ---
                    1. View all active loans
                    2. View all loans
                    
                    0. Go back
                    """);
            switch (choice) {
                case 1:
                    LibraryFacade.getActiveLoans();
                    break;
                case 2:
                    LibraryFacade.getAllLoans();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Please enter valid menu option.");
                    break;
            }
        }
    }

}