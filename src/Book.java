public class Book {
    int id;
    String title;
    int authorId;
    String author;
    boolean available;


    public Book(int id, String title, int authorId, boolean available) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.available = available;
    }

    public Book(int id, String title, int authorId, String author, boolean available) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.author = author;
        this.available = available;
    }

    @Override
    public String toString() {
        return "Title: " + title +
                "\nAuthor : " + author +
                "\nAvailable: " + (available ? "Yes" : "No") +
                "\nBook ID: " + id +
                "\nAuthor ID: " + authorId;
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }
}
