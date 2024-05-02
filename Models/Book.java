package Models;

import java.util.List;

public class Book {
    private String Title;
    private String Author;
    private float Rating;
    private List<Review> Reviews;

    public Book(String title, String author) { Title = title; Author = author; }
    
    protected Book(String title, String author, float rating, List<Review> reviews) {
        Title = title;
        Author = author;
        Rating = rating;
        Reviews = reviews;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public void setReviews(List<Review> reviews) {
        Reviews = reviews;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public float getRating() {
        return Rating;
    }

    public List<Review> getReviews() {
        return Reviews;
    }
}
