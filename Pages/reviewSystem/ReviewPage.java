package Pages.reviewSystem;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import Models.Book;
import Models.Review;
import Models.User;

public class ReviewPage {
    public JFrame frame;
    public JTextArea reviewTextArea;

    public ReviewPage (Book book, Review review, Boolean isEditable, JTable table, int column, int row, User user){
        String content = (review != null) ? review.getContent() : "";
        String userNameString = user.getUsername(); 
        frame = new JFrame();
        frame.setSize(800, 670);
        frame.setLayout(null);
        frame.setResizable(false); 
        
        ImageIcon userIcon = new ImageIcon("reviewSystem/User_Icon.png"); //Gets and sets user icon 
        Image iconImg = userIcon.getImage().getScaledInstance(125, 120, 500); 
        JLabel iconLabel = new JLabel(new ImageIcon(iconImg));
        iconLabel.setBounds(20, 50, 130, 120);
        frame.add(iconLabel);

        JLabel userName = new JLabel(userNameString); //Takes the username from review object and puts it 
        userName.setFont(new Font("Arial", Font.PLAIN, 45));
        userName.setBounds(190, 50, 200, 70);
        frame.add(userName);

        JLabel labelRating = new JLabel("User Rating:");
        labelRating.setBounds(190, 130, 130, 40);
        labelRating.setFont(new Font("Arial", 0, 20));
        frame.add(labelRating);

        ImageIcon starIcon = new ImageIcon("reviewSystem/Star_Icon.png"); //Gets and sets the star image
        Image starImg = starIcon.getImage().getScaledInstance(35, 35, 500);
        JLabel starLabel = new JLabel(new ImageIcon(starImg));
        starLabel.setBounds(302, 130, 35, 35);
        frame.add(starLabel);

        if (!isEditable){
        JLabel ratingLabel = new JLabel(String.valueOf(5)); //Takes the rating from review object and puts it // eslinde olmali: i: review.getRating()
        ratingLabel.setBounds(340, 135, 50, 30);
        ratingLabel.setFont(new Font("Arial", 0, 20));
        frame.add(ratingLabel);
        }
        else{
            JComboBox<Integer> starBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
            String ratingValue = (String)table.getValueAt(row, table.getColumnModel().getColumnIndex("User Rating"));
            if ("12345".contains(ratingValue))
                starBox.setSelectedIndex(Integer.parseInt(ratingValue) - 1);;            
            starBox.setBounds(340, 137, 65, 30);
            starBox.setFont(new Font("Arial", 0, 17));
            
            starBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Integer selectedItem = (Integer) e.getItem();
                        table.setValueAt(selectedItem, row, table.getColumnModel().getColumnIndex("User Rating"));
                    }
                }
            });
            frame.add(starBox);

        }

        ImageIcon bookIcon = new ImageIcon("reviewSystem/Book.png"); //Gets and sets the image(icon) of the book
        Image bookImg = bookIcon.getImage().getScaledInstance(450, 700, 500);
        JLabel bookLabel = new JLabel(new ImageIcon(bookImg)); //we will use this label as a container 
        bookLabel.setBounds(400, -150, 450, 700);
        bookLabel.setLayout(null);

        String authorName = "J.K. Rowling"; // = book.getAuthor(); // this is the real code, but as I'm too lazy to create a book, it is commented out
        StringBuffer authorNameBuffer = new StringBuffer();
        for (String i: authorName.split(" ")){ //There is a problem: if the length of a word(a series of letters that are not seperated by space) 
            while (i.length() > 16){                 //is longer than 16, the program just cuts it, and writes the next word from the next line, so
                authorNameBuffer.append(i.substring(0, 16) + " ");//I split the word if it is longer than indicated word length 
                i = i.substring(16, i.length());
            }
            authorNameBuffer.append(i + " ");
        }
        authorName = authorNameBuffer.toString();

        JLabel bookAuthor = new JLabel("<html>" + authorName + "</html>" ); // the functionality of html tags is that: if the line is longer than width, makes it go the next line
        bookAuthor.setFont(new Font("Times New Roman", Font.PLAIN, 20)); 
        bookAuthor.setBounds(125, 330, 190, 100);
        bookAuthor.setVerticalAlignment(JLabel.CENTER);
        bookAuthor.setHorizontalAlignment(JLabel.CENTER);
        bookAuthor.setForeground(Color.white);

        JLabel bookName = new JLabel("<html>" + "Harry Potter" + "</div></html>"); // the functionality of html tags is that: if the line is longer than width, makes it go the next line
        bookName.setFont(new Font("Times New Roman", Font.BOLD, 30));
        bookName.setBounds(120, 220, 200, 120);
        bookName.setVerticalAlignment(JLabel.TOP);
        bookName.setHorizontalAlignment(JLabel.CENTER);
        bookName.setForeground(Color.white);

        String bookRatingString = String.valueOf(4.5); // = String.valueOf(book.getRating());   //this should be the real code but I do not have a book object yet

        JLabel bookRating = new JLabel(bookRatingString, new ImageIcon(starImg), JLabel.LEFT);
        bookRating.setFont(new Font("Arial", Font.BOLD, 30));
        bookRating.setForeground(Color.white);
        bookRating.setBounds(120, 200, 190, 285);
        bookRating.setVerticalAlignment(JLabel.BOTTOM);
        bookRating.setHorizontalAlignment(JLabel.CENTER);

        bookLabel.add(bookAuthor);
        bookLabel.add(bookName);
        bookLabel.add(bookRating);

        Image board = new ImageIcon( //Gets and sets the board image
            "reviewSystem/Empty_Sheet.png"
            ).getImage().getScaledInstance(680, 450, 0);
        ImageIcon boardIcon = new ImageIcon(board);
        JLabel boardLabel = new JLabel(boardIcon); //This will act as a container for the review text
        boardLabel.setBounds(-82, 190, boardIcon.getIconWidth(), boardIcon.getIconHeight()); 

        JLabel reviewTag = new JLabel("User Review");
        reviewTag.setFont(new Font("Arial", 0, 40));
        reviewTag.setBounds(120, 30, 432, 100);
        reviewTag.setHorizontalAlignment(JLabel.CENTER);

        if (!isEditable){
            JLabel reviewTextLabel = new JLabel("<html>" +    // Gets and sets the review text, 
                content                          // the functionality of html tags is that: if the line is longer than width, makes it go the next line
                + "</html>"); //no more than 300 characters
            reviewTextLabel.setBounds(130, 110, 415, 300);
            reviewTextLabel.setFont(new Font("Times New Roman", 0, 25));
            reviewTextLabel.setVerticalAlignment(JLabel.TOP);
            reviewTextLabel.setHorizontalAlignment(JLabel.CENTER);

            boardLabel.add(reviewTextLabel);
        }

        else{
            reviewTextArea = new JTextArea(content); //no more than 300 characters
                
            reviewTextArea.setFont(new Font("Times New Roman", 0, 25));
            reviewTextArea.setLineWrap(true);
            reviewTextArea.setWrapStyleWord(true);

            reviewTextArea.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    textChanged();
                }
    
                public void removeUpdate(DocumentEvent e) {
                    textChanged();
                }
    
                public void changedUpdate(DocumentEvent e) {
                    textChanged();
                }
    
                private void textChanged() {
                    String x = reviewTextArea.getText();
                    String str = (x.length() >= 10) ? x.substring(0, 10) : x;
                    table.setValueAt(str + "...\nClick To Read More", row, column);

                }
            });


            JScrollPane reviewPane = new JScrollPane(reviewTextArea);

            reviewPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 

            reviewPane.setBounds(130, 110, 415, 300);

            

            

            boardLabel.add(reviewPane);
        }

        boardLabel.add(reviewTag);
        

        frame.add(bookLabel);
        frame.add(boardLabel);

        frame.setVisible(true);
    }

}
