public class Book {
    int id;
    String title;
    int authorId;
    boolean available;


    public Book(int id, String title, int authorId, boolean available) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.available = available;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nTitle: " + title +
                "\nAuthor ID: " + authorId +
                "\nAvailable:" + available;
    }
}
