public class RegisteredUser extends User{

    private int loanPeriod = 28; // loan period in days

    public RegisteredUser(int id, String name, String username, String password, int loanPeriod) {
        super(id, name, username, password, false);
        this.loanPeriod = loanPeriod;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "loanPeriod=" + loanPeriod +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}
