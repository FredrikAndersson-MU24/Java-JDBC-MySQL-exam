public class RegisteredUser extends User{


    public RegisteredUser(int id, String name, String username, String password, int loanPeriod) {
        super(id, name, username, password, false, loanPeriod);
    }

    @Override
    public String toString() {
        return "RegisteredUser{}";
    }
}
