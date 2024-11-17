package Pages;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javax.swing.JOptionPane;

import Models.Book;
import Models.GeneralBook;
import Models.PersonalBook;
import Models.PersonalManager;
import Models.Review;
import Pages.reviewSystem.ReviewPage;

public class PersonalDatabase extends GeneralDatabase  {
    ResourceBundle messages;
    
    public PersonalDatabase(String username, ResourceBundle bundle) {
        super(username, false, bundle);
        messages = bundle;
        frame.setSize(1300, 950);
        scrollPane.setBounds(50, 150, 1200, 750);

        String[] newcolumns = new String[]{messages.getString("Status"), messages.getString("Time_Spent"),
            messages.getString("Start_Date"), messages.getString("End_Date"), messages.getString("User_Rating"), messages.getString("User_Review")};

        model.setRowCount(0);
        for (String str : newcolumns) {
            model.addColumn(str);
        }
        table.setDefaultRenderer(Object.class, new TextAreaRenderer()); 

        List<PersonalBook> books = PersonalManager.readFromCsv(username);
        
        List <Review> reviewsToShow = null;
        for (PersonalBook personalBook : books) {
            if (personalBook.getAuthor() == "Author" &&  personalBook.getTitle() == "Title") {
                continue;
            }
            reviewsToShow = new ArrayList<>();
            for (Review review : personalBook.getReviews()) {
                if (review.getContent().length() > 0)
                    reviewsToShow.add(review);
            }
            model.addRow(new Object[]{personalBook.getTitle(), personalBook.getAuthor(), (personalBook.getRating() == 0) ? messages.getString("No_Rating") : personalBook.getRating() + "(" + personalBook.getRatingCount() + ")", (reviewsToShow.size()>0) ? reviewsToShow : messages.getString("No_Review"), personalBook.getStatus(), personalBook.getTimeSpent(), personalBook.getStartDate(), personalBook.getEndDate(), !(personalBook.getUserRating() == 0) ? personalBook.getUserRating() : "No Rating", (personalBook.getUserReview().length() == 0) ? "Add Review" : ((personalBook.getUserReview().length() >= 10) ? personalBook.getUserReview().substring(0,10) + "\nClick To Read More" : personalBook.getUserReview() + "\nClick To Change")});
        }

        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                if (row != -1 && (column == table.getColumnModel().getColumnIndex(messages.getString("User_Review")) || 
                    column == table.getColumnModel().getColumnIndex(messages.getString("User_Rating")))){
                    table.clearSelection();
                    e.consume();
                    
                    String value = (String) table.getModel().getValueAt(row, column);
                    String title = (String) table.getModel().getValueAt(row, table.getColumnModel().getColumnIndex(messages.getString("title")));
                    String author = (String) table.getModel().getValueAt(row, table.getColumnModel().getColumnIndex(messages.getString("author")));

                    Review rev = null;
                    PersonalBook book = null;
                    for (PersonalBook personalBook : books) {
                        if (personalBook.getTitle().equals(title) && personalBook.getAuthor().equals(author))
                            book = personalBook;
                    }

                    rev = new Review(username, book.getUserReview(), book.getUserRating());

                    

                    if (value.contains("To Read More") || "12345".contains(value)) {
                        ReviewPage rp = new ReviewPage(book, rev, true, table, column, row);
                    }
                    else{
                        ReviewPage rp = new ReviewPage(book, rev, true, table, column, row);
                    }
                }

            }
        });;

        addButton.setText("Delete");
        addButton.removeActionListener(listener);
        addButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            try {
                LinkedList list = new LinkedList<>(Arrays.asList(IntStream.range(0, 2)
                .mapToObj(col -> table.getValueAt(row, col))
                .toArray()));
                Book book = new Book((String)list.get(0), (String)list.get(1));
                PersonalBook pb = null;
                for (PersonalBook personalBook : books) {
                    if (personalBook.getTitle().equals(book.getTitle()) && personalBook.getAuthor().equals(book.getAuthor()))
                        pb = personalBook;

                }
                PersonalManager.removeBookFromCsv(pb.getId(), username);

                model.removeRow(row);
                JOptionPane.showMessageDialog(null, "The Book Successfully Removed from the Personal DataBase! ", "Success", JOptionPane.INFORMATION_MESSAGE);

                System.out.println(list); //Murad should add this to personal database, instead of printing
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "You Should Select a Book! ", "Warning", JOptionPane.ERROR_MESSAGE);
            }

            table.clearSelection();

            
        });
    }

}
