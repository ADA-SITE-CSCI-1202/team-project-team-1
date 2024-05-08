package Models;

import java.util.Date;
import java.util.List;

public class PersonalBook extends GeneralBook{
    private boolean Status;
    private int TimeSpent;
    private Date StartDate;
    private Date EndDate;

    protected PersonalBook(String title, String author, List<Review> reviews, boolean status, int timeSpent, Date startDate, Date endDate) {
        super(title, author, reviews);
        
        Status = status;
        TimeSpent = timeSpent;
        StartDate = startDate;
        EndDate = endDate;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public void setTimeSpent(int timeSpent) {
        TimeSpent = timeSpent;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public boolean getStatus() {
        return Status;
    }

    public int getTimeSpent() {
        return TimeSpent;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }
    
}
