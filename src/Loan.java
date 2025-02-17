import java.time.LocalDate;

public class Loan {

    int id;
    int userId;
    int bookId;
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

    @Override
    public String toString() {
        return "ID: " + id +
                "\tUser Id: " + userId +
                "\tBook ID:" + bookId +
                "\tLoan date: " + loanDate +
                "\tReturn date: " + returnDate +
                "\tReturned: " + returned;
    }
}
