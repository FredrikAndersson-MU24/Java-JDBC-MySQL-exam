public abstract class User {
    private final int id;
    private final String name;
    private final String username;
    private String password;
    private final boolean admin;
    private int loanPeriod; // loan period in days

    public User(int id, String name, String username, boolean admin) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.admin = admin;
    }

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

    /**
     * Alternative toString
     * @return ID, Name, Username, Admin Yes/No as String
     */
    public String toStringUsers() {
        return "User ID: " + id +
                "\tName: " + name +
                "\tUsername: " + username +
                "\tAdmin: " + ( isAdmin() ? "Yes" : "No" );
    }
}
