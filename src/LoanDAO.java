import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoanDAO {

    public void addLoan(int userId, int bookId, int lendPeriod){
        String query = "INSERT INTO loans (user_id, book_id, loan_date, return_date) VALUES (?, ?, ?, ?);";
        LocalDate loanDate = LocalDate.now();
        LocalDate returnDate = loanDate.plusDays(lendPeriod);

        try {
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,userId);
            stmt.setInt(2,bookId);
            stmt.setDate(3, Date.valueOf(loanDate));
            stmt.setDate(4, Date.valueOf(returnDate));
            stmt.executeUpdate();
        } catch ( SQLException e){
            System.out.println("Failed to register loan!");
            e.printStackTrace();
        }
    }

}
