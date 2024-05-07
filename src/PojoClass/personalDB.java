package src.PojoClass;

public class personalDB {
    private String title;
    private String author;
    private String rating;
    private String reviews;
    private String stautus;
    private String time_spent;
    private String start_date;
    private String end_date;
    private String user_rating;
    private String user_review;

    public personalDB(String title, String author, String rating, String reviews, String stautus, String time_spent, String start_date, String end_date, String user_rating, String user_review) {
	this.title = title;
	this.author = author;
	this.rating = rating;
	this.reviews = reviews;
	this.stautus = stautus;
	this.time_spent = time_spent;
	this.start_date = start_date;
	this.end_date = end_date;
	this.user_rating = user_rating;
	this.user_review = user_review;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
	return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
	this.author = author;
    }

    /**
     * @return the rating
     */
    public String getRating() {
	return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(String rating) {
	this.rating = rating;
    }

    /**
     * @return the reviews
     */
    public String getReviews() {
	return reviews;
    }

    /**
     * @param reviews the reviews to set
     */
    public void setReviews(String reviews) {
	this.reviews = reviews;
    }

    /**
     * @return the stautus
     */
    public String getStautus() {
	return stautus;
    }

    /**
     * @param stautus the stautus to set
     */
    public void setStautus(String stautus) {
	this.stautus = stautus;
    }

    /**
     * @return the time_spent
     */
    public String getTime_spent() {
	return time_spent;
    }

    /**
     * @param time_spent the time_spent to set
     */
    public void setTime_spent(String time_spent) {
	this.time_spent = time_spent;
    }

    /**
     * @return the start_date
     */
    public String getStart_date() {
	return start_date;
    }

    /**
     * @param start_date the start_date to set
     */
    public void setStart_date(String start_date) {
	this.start_date = start_date;
    }

    /**
     * @return the end_date
     */
    public String getEnd_date() {
	return end_date;
    }

    /**
     * @param end_date the end_date to set
     */
    public void setEnd_date(String end_date) {
	this.end_date = end_date;
    }

    /**
     * @return the user_rating
     */
    public String getUser_rating() {
	return user_rating;
    }

    /**
     * @param user_rating the user_rating to set
     */
    public void setUser_rating(String user_rating) {
	this.user_rating = user_rating;
    }

    /**
     * @return the user_review
     */
    public String getUser_review() {
	return user_review;
    }

    /**
     * @param user_review the user_review to set
     */
    public void setUser_review(String user_review) {
	this.user_review = user_review;
    }
    
    
}