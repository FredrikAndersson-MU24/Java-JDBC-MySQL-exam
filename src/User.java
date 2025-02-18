public abstract class User {
    int id;
    String name;
    String username;
    String password;
    boolean adminRights;

    public User(int id, String name, String username, String password, boolean adminRights) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.adminRights = adminRights;
    }
}
