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
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import Models.Book;
import Models.CSVMananger;
import Models.GeneralBook;
import Models.PersonalManager;
import Models.Review;

public class ReviewPage {
    public JFrame frame;
    public JTextArea reviewTextArea;

    public ReviewPage (GeneralBook book, Review review, Boolean isEditable, JTable table, int column, int row){
        String content = (review != null) ? review.getContent() : "";
        String userNameString = review.getUser(); 
        frame = new JFrame();
        frame.setSize(800, 670);
        frame.setLayout(null);
        frame.setResizable(false); 
        
        ImageIcon userIcon = new ImageIcon("Pages/assets/User_Icon.png"); //Gets and sets user icon 
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

        ImageIcon starIcon = new ImageIcon("Pages/assets/Star_Icon.png"); //Gets and sets the star image
        Image starImg = starIcon.getImage().getScaledInstance(35, 35, 500);
        JLabel starLabel = new JLabel(new ImageIcon(starImg));
        starLabel.setBounds(302, 130, 35, 35);
        frame.add(starLabel);

        if (!isEditable){
            int rating = review.getRating();
            JLabel ratingLabel = new JLabel(String.valueOf((rating > 0) ? rating : "No Rating")); //Takes the rating from review object and puts it // eslinde olmali: i: review.getRating()
            ratingLabel.setBounds(340, 135, 100, 30);
            ratingLabel.setFont(new Font("Arial", 0, 20));
            frame.add(ratingLabel);
        }
        else{
            JComboBox<String> starBox = new JComboBox<>(new String[]{"No Rating", "1", "2", "3", "4", "5"});
            String ratingValue = String.valueOf(review.getRating());
            if ("012345".contains(ratingValue))
                starBox.setSelectedIndex(Integer.parseInt(ratingValue)); 

            starBox.setBounds(340, 137, 65, 30);
            starBox.setFont(new Font("Arial", 0, 17));
            
            starBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String item = (String) e.getItem();
                        Integer selectedItem = ("12345".contains(item)) ? Integer.valueOf(item) : 0;
                        table.setValueAt(item, row, table.getColumnModel().getColumnIndex("User Rating"));
                        PersonalManager.changeRatingReview(book.getId(), userNameString, selectedItem, "", "rating");
                    }
                }
            });
            frame.add(starBox);

        }

        ImageIcon bookIcon = new ImageIcon("Pages/assets/Book.png"); //Gets and sets the image(icon) of the book
        Image bookImg = bookIcon.getImage().getScaledInstance(450, 700, 500);
        JLabel bookLabel = new JLabel(new ImageIcon(bookImg)); //we will use this label as a container 
        bookLabel.setBounds(400, -150, 450, 700);
        bookLabel.setLayout(null);

        JLabel bookAuthor = new JLabel("<html>" + book.getAuthor() + "</html>" ); // the functionality of html tags is that: if the line is longer than width, makes it go the next line
        bookAuthor.setFont(new Font("Times New Roman", Font.PLAIN, 20)); 
        bookAuthor.setBounds(125, 330, 190, 100);
        bookAuthor.setVerticalAlignment(JLabel.CENTER);
        bookAuthor.setHorizontalAlignment(JLabel.CENTER);
        bookAuthor.setForeground(Color.white);

        JLabel bookName = new JLabel("<html>" + book.getTitle() + "</div></html>"); // the functionality of html tags is that: if the line is longer than width, makes it go the next line
        bookName.setFont(new Font("Times New Roman", Font.BOLD, 30));
        bookName.setBounds(120, 220, 200, 120);
        bookName.setVerticalAlignment(JLabel.TOP);
        bookName.setHorizontalAlignment(JLabel.CENTER);
        bookName.setForeground(Color.white);

        float brating = book.getRating();
        JLabel bookRating = new JLabel(String.valueOf((brating > 0) ? brating : "No Rating"), new ImageIcon(starImg), JLabel.LEFT);
        bookRating.setFont(new Font("Arial", Font.BOLD, 30));
        bookRating.setForeground(Color.white);
        bookRating.setBounds(120, 200, 190, 285);
        bookRating.setVerticalAlignment(JLabel.BOTTOM);
        bookRating.setHorizontalAlignment(JLabel.CENTER);

        bookLabel.add(bookAuthor);
        bookLabel.add(bookName);
        bookLabel.add(bookRating);

        Image board = new ImageIcon( //Gets and sets the board image
            "Pages/assets/Empty_Sheet.png"
            ).getImage().getScaledInstance(680, 450, 0);
        ImageIcon boardIcon = new ImageIcon(board);
        JLabel boardLabel = new JLabel(boardIcon); //This will act as a container for the review text
        boardLabel.setBounds(-82, 190, boardIcon.getIconWidth(), boardIcon.getIconHeight()); 

        JLabel reviewTag = new JLabel("User Review");
        reviewTag.setFont(new Font("Arial", 0, 40));
        reviewTag.setBounds(120, 30, 432, 100);
        reviewTag.setHorizontalAlignment(JLabel.CENTER);

        if (!isEditable){
            JLabel reviewTextLabel = new JLabel();//no more than 300 characters
            reviewTextLabel.setBounds(130, 110, 415, 300);
            reviewTextLabel.setFont(new Font("Times New Roman", 0, 25));
            reviewTextLabel.setVerticalAlignment(JLabel.TOP);
            reviewTextLabel.setHorizontalAlignment(JLabel.CENTER);

            String text = review.getContent().replace("\\n", "<br>");
            text = wrapText(text, reviewTextLabel, 415);
            reviewTextLabel.setText(text);

            boardLabel.add(reviewTextLabel);
        }

        else{
            reviewTextArea = new JTextArea(content.replace("\\n", "\n")); //no more than 300 characters
                
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
                    String x = reviewTextArea.getText().replace("\n", "\\n");
                    String str = (x.length() >= 10) ? x.substring(0, 10) : x;
                    table.setValueAt(str + "...\nClick To Read More", row, column);
                    PersonalManager.changeRatingReview(book.getId(), userNameString, -1, x, "content");

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

    
    // Helper function to wrap text and break long words
    public static String wrapText(String text, JLabel label, int maxWidth) {
        FontMetrics fm = label.getFontMetrics(label.getFont());
        StringBuilder wrappedText = new StringBuilder("<html>");
        String[] words = text.split(" ");
        
        for (String word : words) {
            // Measure word width
            int wordWidth = fm.stringWidth(word);
            
            // If the word is longer than maxWidth, break it
            if (wordWidth > maxWidth) {
                wrappedText.append(insertBreaksInWord(word, fm, maxWidth)).append(" ");
            } else {
                wrappedText.append(word).append(" ");
            }
        }

        wrappedText.append("</html>");
        return wrappedText.toString();
    }

    // Helper function to insert breaks in long words
    public static String insertBreaksInWord(String word, FontMetrics fm, int maxWidth) {
        StringBuilder result = new StringBuilder();
        int currentWidth = 0;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            currentWidth += fm.charWidth(c);

            // Insert <br> when width exceeds maxWidth
            if (currentWidth > maxWidth) {
                result.append("<br>");
                currentWidth = fm.charWidth(c);  // Reset current width after break
            }

            result.append(c);
        }

        return result.toString();
    }
    
}


