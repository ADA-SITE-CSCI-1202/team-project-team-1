package Models;

public class User {
    private String Username;
    private String Password;
    
    public User() {}

    public User(String username, String password) {
        Username = username;
        Password = password;
    }

    public void setUsername(String username) {
        Username = username;
    }
    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setRating(double rating) {
        this.Rating = rating;
    }

    public double getRating() {
        return Rating;
    }

    
}
