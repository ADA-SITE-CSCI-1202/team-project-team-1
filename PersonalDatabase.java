import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;

import javax.swing.JOptionPane;

import Models.Book;
import Models.Review;
import Models.User;
import reviewSystem.ReviewPage;

public class PersonalDatabase extends GeneralDatabase  {
    User user;
    public PersonalDatabase(User user)  {
        this.user = user;
        frame.setSize(1300, 950);
        scrollPane.setBounds(50, 150, 1200, 750);
        
        String[] newcolumns = new String[]{"Status", "Time Spent",
            "Start Date", "End Date", "User Rating", "User Review"};

        model.setRowCount(0);
        for (String str : newcolumns) {
            model.addColumn(str);
        }
        table.setDefaultRenderer(Object.class, new TextAreaRenderer());
        
        
        ArrayList<Review> reviewList= new ArrayList<>(Arrays.asList( //this is actually for writing personal db
            new Review(new User("Farhad", "sss"), "Cox gozedwnjhwbx njhbcxwhbhjxbwjk,jbxwhbjwebjcbwxjbcewb el he ewnd", 5),
            new Review(new User("Murad", "sss"), "Yaxsi kimi", -1),
            new Review(new User("Ibrahim", "sss"), "Orta", 5)
        )); //Murad can pass like that(note that this is just as a framework to what it should look like)
        Review rew = reviewList.get(0);
        Review rew2 = reviewList.get(1);
        int shownContentLength = (rew.getContent().length() >= 10) ? 10 : rew.getContent().length();
        int shownContentLength2 = (rew2.getContent().length() >= 10) ? 10 : rew2.getContent().length();
        String shownContent = rew.getContent().substring(0, shownContentLength) + "..." + "\nClick To Read More";
        String shownContent2 = rew2.getContent().substring(0, shownContentLength2) + "..." + "\nClick To Read More";
        String ratingContent = (rew.getRating() != -1) ? String.valueOf(rew.getRating()) : "Add Rating";
        String ratingContent2 = (rew2.getRating() != -1) ? String.valueOf(rew2.getRating()) : "Add Rating";
        model.addRow(new Object[]{"The Little Prince", "IDK", 5, arrayListToString(reviewList), "5", "5", "10", "5", ratingContent, shownContent});
        model.addRow(new Object[]{"The Little Prince", "IDK", 5, arrayListToString(reviewList), "5", "5", "10", "5", ratingContent2, shownContent2 });


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                if (row != -1 && (column == table.getColumnModel().getColumnIndex("User Review") || 
                    column == table.getColumnModel().getColumnIndex("User Rating"))){
                    table.clearSelection();
                    e.consume();
                    String value = (String) table.getModel().getValueAt(row, column);
                    String title = (String) table.getModel().getValueAt(row, table.getColumnModel().getColumnIndex("Title"));
                    String author = (String) table.getModel().getValueAt(row, table.getColumnModel().getColumnIndex("Author"));
                    if (value.contains("To Read More") || "12345".contains(value)) {
                        ReviewPage rp = new ReviewPage(new Book(title, author), reviewList.get(0), true, table, column, row, user);
                    }
                    else{
                        new ReviewPage(new Book(title, author), null, true, table, column, row, user);
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
                Models.Book book = new Models.Book((String)list.get(0), (String)list.get(1));
                //deletebook from personal db

                model.removeRow(row);
                JOptionPane.showMessageDialog(null, "The Book Successfully Removed from the Personal DataBase! ", "Success", JOptionPane.INFORMATION_MESSAGE);

                System.out.println(list); //Murad should add this to personal database, instead of printing
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "You Should Select a Book! ", "Warning", JOptionPane.ERROR_MESSAGE);
            }

            table.clearSelection();

            
        });

   


    }


    public static void main(String[] args) {
        new PersonalDatabase(new User("Farhad", "ewdshj"));
    }
}
