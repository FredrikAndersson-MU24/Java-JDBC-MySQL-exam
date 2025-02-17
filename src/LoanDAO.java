import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoanDAO {
    Connection conn = Database.getConnection();


    public void addLoan(int userId, int bookId, int lendPeriod) {
        String newLoanQuery = "INSERT INTO loans (user_id, book_id, loan_date, return_date) VALUES (?, ?, ?, ?);";
        String setBookUnavailableQuery = "UPDATE books SET available = false WHERE id = ?;";
        LocalDate loanDate = LocalDate.now();
        LocalDate returnDate = loanDate.plusDays(lendPeriod);
        try {
            conn.setAutoCommit(false);
            //Prepare statement to add a new loan to the loans table
            PreparedStatement newLoan = conn.prepareStatement(newLoanQuery);
            newLoan.setInt(1, userId);
            newLoan.setInt(2, bookId);
            newLoan.setDate(3, Date.valueOf(loanDate));
            newLoan.setDate(4, Date.valueOf(returnDate));
            newLoan.executeUpdate();
            //Prepare statement to set the lent book unavailable
            PreparedStatement setBookUnavailable = conn.prepareStatement(setBookUnavailableQuery);
            setBookUnavailable.setInt(1, bookId);
            setBookUnavailable.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Failed to register loan!");
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException f) {
                System.out.println("Rollback failed!");
                f.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
