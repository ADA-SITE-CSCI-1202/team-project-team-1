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

    public void giveRating() {
        
    }

    @Override
    public String toString() {
        return "\nUsername: " + getUsername() + ". Password: " + getPassword();
    }
}
