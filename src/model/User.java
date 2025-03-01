package model;

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
        return "model.User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                ", loanPeriod=" + loanPeriod +
                '}';
    }

    public String printAsTable() {
        return String.format("| %-10s | %-50s | %-20s | %-10s |", id, name, username, admin ? "Yes" : "No");
    }
}