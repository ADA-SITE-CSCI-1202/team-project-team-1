package Models;

import java.util.List;

public class GeneralBook extends Book{
    private float Rating;
    private List<Review> Reviews;

    public GeneralBook(String title, String author) {
        super(title, author);
    }

    public GeneralBook(String title, String author, float rating, List<Review> reviews) {
        super(title, author);
        
        Rating = rating;
        Reviews = reviews;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public void setReviews(List<Review> reviews) {
        Reviews = reviews;
    }

    public float getRating() {
        return Rating;
    }

    public List<Review> getReviews() {
        return Reviews;
    }
    
}
