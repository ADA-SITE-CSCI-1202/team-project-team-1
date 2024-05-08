package Models;

public class Review {
    private String user;
    private String content;
    private int rating;

    protected Review(String user, String content, int rating) {
        this.user = user;
        this.content = content;
        this.rating = rating;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public double getRating() {
        return rating;
    }
}
