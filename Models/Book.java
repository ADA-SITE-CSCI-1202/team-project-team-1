package Models;

public class Book {
    private String Title;
    private String Author;

    public Book(String title, String author) { Title = title; Author = author; }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    @Override
    public String toString() {
        return "Title: " + this.getTitle() + "\n" + "Author: " + this.getAuthor() + "\n";
    }
}
