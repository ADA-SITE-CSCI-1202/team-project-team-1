package Models;

import java.util.Date;
import java.util.List;

public class Book {
    private String Title;
    private String Author;
    private float Rating;
    private List<Review> Reviews;
    private boolean Status;
    private int TimeSpent;
    private Date StartDate;
    private Date EndDate;
    
    protected Book(String title, String author, float rating, List<Review> reviews, boolean status, int timeSpent, Date startDate, Date endDate) {
        Title = title;
        Author = author;
        Rating = rating;
        Reviews = reviews;
        Status = status;
        TimeSpent = timeSpent;
        StartDate = startDate;
        EndDate = endDate;
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

    public void setStatus(boolean status) {
        Status = status;
    }

    public void setTimeSpent(int timeSpent) {
        TimeSpent = timeSpent;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
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

    public boolean getStatus() {
        return Status;
    }

    public int getTimeSpent() {
        return TimeSpent;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }
}
