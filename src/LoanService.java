import java.util.List;

public abstract class LoanService {

    static LoanDAO loanDAO = new LoanDAO();
    static BookDAO bookDAO = new BookDAO();

    public static void lendBook() {
        LibraryFacade.getBooks("available");
        int id = InputHandler.getPositiveInt("Please enter book ID (or 0(zero) to abort): ");
        if (id == 0) {
            return;
        }
        Book book = bookDAO.getBookById(id);
        if (book != null && book.isAvailable()) {
            loanDAO.addLoan(
                    LibraryFacade.getCurrentUser().getId(),
                    book.getId(),
                    LibraryFacade.getCurrentUser().getLoanPeriod());
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

    public static void returnLoan() {
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

    public static void getAllLoans() {
        List<Loan> listOfLoans = loanDAO.getAllLoans();
        if (listOfLoans.isEmpty()) {
            System.out.println("There are no recorded loans.");
        } else {
            printLoansAsTableAsAdmin(listOfLoans);
        }
    }

    public static void getUsersActiveLoans() {
        List<Loan> listOfLoans = loanDAO.getUsersActiveLoans(LibraryFacade.getCurrentUser());
        if (listOfLoans.isEmpty()) {
            System.out.println("You have no active loans.");
        } else {
            printLoansAsTableAsUser(listOfLoans);
        }
    }

    public static void getActiveLoans() {
        List<Loan> listOfLoans = loanDAO.getAllActiveLoans();
        if (listOfLoans.isEmpty()) {
            System.out.println("There are no active loans.");
        } else {
            printLoansAsTableAsAdmin(listOfLoans);
        }
    }

    public static void printLoansAsTableAsUser(List<Loan> listOfLoans) {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s |  %-50s | %-15s | %-15s | %-10s |", "Loan ID", "Book ID", "Title", "Loan date", "Return date", "Late");
        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------");
        listOfLoans.forEach(l -> System.out.println(l.printAsTableAsUser()));
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void printLoansAsTableAsAdmin(List<Loan> listOfLoans) {
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-10s | %-15s | %-15s | %-10s | %-10s |", "Loan ID", "User Id", "Book ID", "Loan date", "Return date", "Returned", "Late");
        System.out.println("\n------------------------------------------------------------------------------------------------------");
        listOfLoans.forEach(l -> System.out.println(l.printAsTableAsAdmin()));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

}
