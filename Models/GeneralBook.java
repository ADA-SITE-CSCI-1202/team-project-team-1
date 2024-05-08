package Models;

import java.util.List;

public class GeneralBook extends Book{
    private List<Review> Reviews;

    public GeneralBook(String title, String author) {
        super(title, author);
    }

    public GeneralBook(String title, String author, List<Review> reviews) {
        super(title, author);
        Reviews = reviews;
    }

    public void setReviews(List<Review> reviews) {
        Reviews = reviews;
    }

    public List<Review> getReviews() {
        return Reviews;
    }
    
}
