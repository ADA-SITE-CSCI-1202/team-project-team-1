package Models;

import java.util.List;

public class GeneralBook extends Book{
    private List<Review> Reviews;
    private float Rating;

    public GeneralBook(String title, String author) {
        super(title, author);
    }

    public GeneralBook(String title, String author, List<Review> reviews) {
        super(title, author);
        Reviews = reviews;
    }

    public GeneralBook(String title, String author, float rating, List<Review> reviews) {
        super(title, author);
        Reviews = reviews;
        Rating = rating;
    }

    public void setReviews(List<Review> reviews) {
        Reviews = reviews;
    }

    public List<Review> getReviews() {
        return Reviews;
    }

    public float getRating() {
        int totalRating = 0;
        int count = 0;

        for (Review review : Reviews) {
            totalRating += review.getRating();
            count++;
        }

        return totalRating / count;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(this.getTitle() + "," + this.getAuthor() + "\n");
        
        for (Review review : this.Reviews) {
            sb.append(review.toString() + "\n");
        }

        return sb.toString();
    }
}
