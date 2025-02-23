package model;

public class AdminUser extends User{

    public AdminUser(int id, String name, String username) {
        super(id, name, username, true);
    }

    public AdminUser(int id, String name, String username, String password) {
        super(id, name, username, password, true);
    }

}
