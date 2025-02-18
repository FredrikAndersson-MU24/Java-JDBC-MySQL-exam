public abstract class User {
    int id;
    String name;
    String username;
    String password;
    boolean admin;

    public User(int id, String name, String username, String password, boolean admin) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }
}
