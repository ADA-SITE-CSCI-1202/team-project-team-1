package src.PojoClass;

public class generalDB {
   private String title;
   private String author;

    public generalDB(String title, String author) {
	this.title = title;
	this.author = author;
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
   

    

    
}