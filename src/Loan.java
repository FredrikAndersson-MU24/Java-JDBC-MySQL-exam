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
                "\tReturned: " + (returned ? "Yes": "No");
    }

    public String toStringAsUser() {
        return "Loan ID: " + id +
                "\tBook ID:" + bookId +
                "\tTitle: " + bookTitle +
                "\tLoan date: " + loanDate +
                "\tReturn date: " + returnDate +
                "\tLate: " + (returnDate.isBefore(LocalDate.now()) ? "Yes" : "No");
    }
}
