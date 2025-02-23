import java.time.LocalDate;

public class Loan {

    int id;
    int userId;
    int bookId;
    String bookTitle;
    LocalDate loanDate;
    LocalDate returnDate;
    boolean returned;

    public Loan(int id, int userId, int bookId, LocalDate loanDate, LocalDate returnDate, boolean returned) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    public Loan(int id, int userId, int bookId, String bookTitle, LocalDate loanDate, LocalDate returnDate, boolean returned) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\tUser Id: " + userId +
                "\tBook ID:" + bookId +
                "\tTitle: " + bookTitle +
                "\tLoan date: " + loanDate +
                "\tReturn date: " + returnDate +
                "\tReturned: " + (returned ? "Yes" : "No");
    }

    public String toStringAsUser() {
        return "Loan ID: " + id +
                "\tBook ID:" + bookId +
                "\tTitle: " + bookTitle +
                "\tLoan date: " + loanDate +
                "\tReturn date: " + returnDate +
                "\tLate: " + (returnDate.isBefore(LocalDate.now()) ? "Yes" : "No");
    }

    public String toStringAsAdmin() {
        return "Loan ID: " + id +
                "\tUser Id: " + userId +
                "\tBook ID:" + bookId +
                "\tLoan date: " + loanDate +
                "\tReturn date: " + returnDate +
                "\tReturned: " + (returned ? "Yes" : "No") +
                "\tLate: " + (returnDate.isBefore(LocalDate.now()) && !returned ? "Yes" : "No");
    }

    public String printAsTableAsAdmin() {
        return String.format("| %-10s | %-10s | %-10s | %-15s | %-15s | %-10s | %-10s |", id, userId, bookId, loanDate, returnDate, returned ? "Yes" : "No", returnDate.isBefore(LocalDate.now()) && !returned ? "Yes" : "No");
    }

    public String printAsTableAsUser() {
        return String.format("| %-10s | %-10s |  %-50s | %-15s | %-15s | %-10s |", id, bookId, bookTitle, loanDate, returnDate, returnDate.isBefore(LocalDate.now()) && !returned ? "Yes" : "No");
    }

    // TODO Add actual return date to track late returns
}
