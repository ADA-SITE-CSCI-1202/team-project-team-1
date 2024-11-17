package Models;

import java.util.List;

public class GeneralBook extends Book{
    public List<Review> Reviews;
    public float Rating;
    Integer id;

    public GeneralBook(Integer id){
        super("Title", "Author");
        List<GeneralBook> books = CSVMananger.readFromCsv();
        for (GeneralBook generalBook : books) {
            if (generalBook.getId().equals(id)){
                this.setTitle(generalBook.getTitle());
                this.setAuthor(generalBook.getAuthor());
                this.Reviews = generalBook.getReviews();
                this.Rating = generalBook.getRating();
                this.id = id;
            }

        }
    }

    public GeneralBook(String title, String author) {
        super(title, author);
    }

    public GeneralBook(Integer id, String title, String author, List<Review> reviews) {
        super(title, author);
        Reviews = reviews;
        this.id = id;
    }

    public GeneralBook(Integer id, String title, String author, float rating, List<Review> reviews) {
        super(title, author);
        Reviews = reviews;
        Rating = rating;
        this.id = id;
    }

    public GeneralBook(GeneralBook book) {
        super(book.getTitle(), book.getAuthor());
        this.Reviews = book.getReviews();
        this.Rating = book.getRating();
        this.id = book.id;
    }

     

    public void setReviews(List<Review> reviews) {
        Reviews = reviews;
    }

    public List<Review> getReviews() {
        return Reviews;
    }

    public int getRatingCount(){ //content length 0dirsa hec gostermesin onu uida
        int count = 0;

        for (Review review : Reviews) {
            if (review.getRating() > 0) {
                count++;     
            }
        }

        return count;
    }

    public float getRating() {
        int totalRating = 0;
        int count = 0;

        for (Review review : Reviews) {
            if (review.getRating() > 0) {
                totalRating += review.getRating();
                count++;     
            }
        }

        return (float) ((count != 0) ? (totalRating * 1.0) / count : 0);
    }

    public Integer getId() {
        return id;
    }

    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(this.getId() + "," + this.getTitle() + "," + this.getAuthor() + "\n");
        
        for (Review review : this.Reviews) {
            sb.append(review.toString() + "\n");
        }

        return sb.toString();
    }

    
}
