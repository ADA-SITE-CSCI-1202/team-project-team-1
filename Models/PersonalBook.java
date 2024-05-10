package Models;

import java.util.List;

public class PersonalBook extends GeneralBook{
    private String Status;
    private int TimeSpent;
    private String StartDate;
    private String EndDate;
    private int UserRating;
    private String UserReview;
    
    public PersonalBook(String title, String author, List<Review> reviews, String status, int timeSpent, String startDate, String endDate, int userRating, String userReview) {
        super(title, author, reviews);
        
        Status = status;
        TimeSpent = timeSpent;
        StartDate = startDate;
        EndDate = endDate;
        UserRating = userRating;
        UserReview = userReview;
    }
    
    public void setStatus(String status) {
        Status = status;
    }
    
    public void setTimeSpent(int timeSpent) {
        TimeSpent = timeSpent;
    }
    
    public void setStartDate(String startDate) {
        StartDate = startDate;
    }
    
    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
    
    public void setUserRating(int userRating) {
        UserRating = userRating;
    }
    
    public void setUserReview(String userReview) {
        UserReview = userReview;
    }
    
    public String getStatus() {
        return Status;
    }
    
    public int getTimeSpent() {
        return TimeSpent;
    }
    
    public String getStartDate() {
        return StartDate;
    }
    
    public String getEndDate() {
        return EndDate;
    }
    
    public int getUserRating() {
        return UserRating;
    }
    
    public String getUserReview() {
        return UserReview;
    }
}
