package Models;

public class Review {
    private User user;
    private String content;
    private int rating;

    public Review(User user, String content, int rating) {
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

    public void setRating(int rating) {
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

    public String toString(){
        return user.getUsername();
    }
}
