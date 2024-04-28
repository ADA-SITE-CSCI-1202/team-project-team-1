package Models;

public class Review {
    private User user;
    private String content;
    private double rating;

    protected Review(User user, String content, double rating) {
        this.user = user;
        this.content = content;
        this.rating = rating;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public double getRating() {
        return rating;
    }
}
