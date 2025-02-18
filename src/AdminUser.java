public class AdminUser extends User{

    public AdminUser(int id, String name, String username, String password) {
        super(id, name, username, password, true);
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}
