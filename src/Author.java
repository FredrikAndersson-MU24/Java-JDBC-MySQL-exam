public class Author {
    int id;
    String name;

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\tName: " + name;
    }

    public String printAsTable() {
        return String.format("| %-10s | %-50s |", id, name);
    }
}
