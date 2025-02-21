public abstract class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private boolean admin;
    private int loanPeriod; // loan period in days


    public User(int id, String name, String username, String password, boolean admin) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }
    public User(int id, String name, String username, String password, boolean admin, int loanPeriod) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.loanPeriod = loanPeriod;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public int getLoanPeriod() {
        return this.loanPeriod;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                ", loanPeriod=" + loanPeriod +
                '}';
    }
}
