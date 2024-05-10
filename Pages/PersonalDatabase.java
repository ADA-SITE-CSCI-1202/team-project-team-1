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

        PersonalManager pm = new PersonalManager();
        List<PersonalBook> books = pm.readFromCsv(username);
        
        for (PersonalBook personalBook : books) {
            model.addRow(new Object[]{personalBook.getTitle(), personalBook.getAuthor(), personalBook.getRating(), personalBook.getReviews(), personalBook.getStatus(), personalBook.getTimeSpent(), personalBook.getStartDate(), personalBook.getEndDate(), !(personalBook.getUserRating() == 0) ? personalBook.getUserRating() : "No Rating", (personalBook.getUserReview().equals("Add Review")) ? personalBook.getUserReview() : ((personalBook.getUserReview().length() >= 10) ? personalBook.getUserReview().substring(0,10) : personalBook.getUserReview()) + "Click To Read More"});
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

                    List<Review> reviewList = new ArrayList<>();
                    Review rev = new Review();

                    for (PersonalBook personalBook : books) {
                        if(personalBook.getTitle().equals(title)) {
                            reviewList = personalBook.getReviews();
                        }
                    }

                    for (Review review : reviewList) {
                        if (review.getUser().equals(username)) {
                            rev = review;
                        }
                    }

                    if (value.contains("To Read More") || "12345".contains(value)) {
                        ReviewPage rp = new ReviewPage(new Book(title, author), rev, true, table, column, row);
                    }
                    else{
                        ReviewPage rp = new ReviewPage(new Book(title, author), rev, true, table, column, row);
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
                pm.removeBookFromCsv(book.getTitle(), username);

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
