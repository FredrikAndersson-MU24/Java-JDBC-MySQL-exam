import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public void returnLoan(int bookID) {
        String setBookAvailableQuery = "UPDATE books SET available = true WHERE id = ?;";
        String setLoanReturnedQuery = "UPDATE loans SET returned = true WHERE book_id = ?;";

        try {
            conn.setAutoCommit(false);

            //Set book to available
            PreparedStatement setBookAvailable = conn.prepareStatement(setBookAvailableQuery);
            setBookAvailable.setInt(1, bookID);
            setBookAvailable.executeUpdate();
            //Set loan as returned
            PreparedStatement setLoanReturned = conn.prepareStatement(setLoanReturnedQuery);
            setLoanReturned.setInt(1, bookID);
            setLoanReturned.executeUpdate();

            conn.commit();
            System.out.println("Book returned successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to execute return");
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Loan> getAllLoans() {
        String query = "SELECT * FROM loans;";
        List<Loan> listOfLoans = new ArrayList<>();
        try {
            Statement getLoans = conn.createStatement();
            ResultSet rs = getLoans.executeQuery(query);
            while (rs.next()) {
                listOfLoans.add(new Loan(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate(),
                        rs.getBoolean(6)));
            }
        } catch (SQLException e) {
            System.out.println("Failed when trying to get all loans!");
            e.printStackTrace();
        }
        return listOfLoans;
    }

    public List<Loan> getUsersActiveLoans(User user) {
        String query = "SELECT loans.id, loans.user_id, loans.book_id, books.title, loans.loan_date, loans.return_date, loans.returned from loans " +
                "JOIN books ON loans.book_id = books.id " +
                "WHERE user_id = ? AND returned = false;";
        List<Loan> listOfLoans = new ArrayList<>();
        try {
            PreparedStatement getLoans = conn.prepareStatement(query);
            getLoans.setInt(1, user.getId());
            ResultSet rs = getLoans.executeQuery();
            while (rs.next()) {
                listOfLoans.add(createLoanFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed when trying to get active loans!");
            e.printStackTrace();
        }
        return listOfLoans;
    }

    public List<Loan> getAllActiveLoans() {
        String query = "SELECT loans.id, loans.user_id, loans.book_id, books.title, loans.loan_date, loans.return_date, loans.returned from loans " +
                "JOIN books ON loans.book_id = books.id " +
                "WHERE returned = false;";
        List<Loan> listOfLoans = new ArrayList<>();
        try {
            Statement getLoans = conn.createStatement();
            ResultSet rs = getLoans.executeQuery(query);
            while (rs.next()) {
                listOfLoans.add(createLoanFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed when trying to get active loans!");
            e.printStackTrace();
        }
        return listOfLoans;
    }

    private static Loan createLoanFromResultSet(ResultSet rs) throws SQLException {
        return new Loan(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getDate(5).toLocalDate(),
                rs.getDate(6).toLocalDate(),
                rs.getBoolean(7));
    }

}
