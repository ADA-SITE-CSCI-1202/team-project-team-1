package Models;

public class Review {
    private User user;
    private String Content;

    public Review(User user, String content) {
        this.user = user;
        Content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setContent(String content) {
        Content = content;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return Content;
    }
}
