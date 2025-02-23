package model;

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
                "\nmodel.Author : " + author +
                "\nAvailable: " + (available ? "Yes" : "No") +
                "\nmodel.Book ID: " + id +
                "\nmodel.Author ID: " + authorId;
    }

    public String printAsTable() {
        return String.format("| %-10s | %-50s | %-10s | %-30s | %-10s |", id, title, authorId, author, available ? "Yes" : "No");
    }

    public String printIdAndTitleAsTable() {
        return String.format("| %-10s | %-50s |", id, title);
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }
}
