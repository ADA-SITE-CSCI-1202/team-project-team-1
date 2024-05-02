package reviewSystem;
import javax.sound.midi.Track;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import Models.Book;
import Models.Review;
import Models.User;

public class ReviewPage {
    public ReviewPage (Book book, Review review){
        JFrame frame = new JFrame();
        frame.setSize(800, 670);
        frame.setLayout(null);
        frame.setResizable(false);
        


        ImageIcon userIcon = new ImageIcon("reviewSystem/png-transparent-computer-icons-user-profile-male-avatar-avatar-heroes-logo-black-thumbnail-removebg-preview.png");
        Image iconImg = userIcon.getImage().getScaledInstance(125, 120, 0);
        JLabel iconLabel = new JLabel(new ImageIcon(iconImg));
        iconLabel.setBounds(20, 50, 130, 120);
        frame.add(iconLabel);

        JLabel userName = new JLabel(review.getUser().getUsername());
        userName.setFont(new Font("Arial", Font.PLAIN, 45));
        userName.setBounds(190, 50, 200, 70);
        frame.add(userName);

        JLabel labelRating = new JLabel("User Rating:");
        labelRating.setBounds(190, 130, 130, 40);
        labelRating.setFont(new Font("Arial", 0, 20));
        frame.add(labelRating);

        ImageIcon starIcon = new ImageIcon("reviewSystem/png-transparent-star-golden-stars-angle-3d-computer-graphics-symmetry-thumbnail-removebg-preview.png");
        Image starImg = starIcon.getImage().getScaledInstance(35, 35, 0);
        JLabel starLabel = new JLabel(new ImageIcon(starImg));
        starLabel.setBounds(302, 130, 35, 35);
        frame.add(starLabel);

        JLabel ratingLabel = new JLabel(String.valueOf(review.getUser().getRating()));
        ratingLabel.setBounds(340, 135, 50, 30);
        ratingLabel.setFont(new Font("Arial", 0, 20));
        frame.add(ratingLabel);

        ImageIcon bookIcon = new ImageIcon(
            "reviewSystem/DALL_E_2024-04-27_15.58.43_-_A_sleek_and_clean_blank_book_cover__displayed_with_a_horizontal_orientation_along_the_Y-axis__creating_a_vertical_standing_position._The_cover_should_-removebg-preview.png"
            );
        Image bookImg = bookIcon.getImage().getScaledInstance(450, 700, 0);
       
        JLabel bookLabel = new JLabel(new ImageIcon(bookImg));
        bookLabel.setBounds(400, -150, 450, 700);
        bookLabel.setLayout(null);
        frame.add(bookLabel);

        // JLabel bookAuthor = new JLabel(book.getAuthor()); // this is the real code, but as I'm too lazy to create a book, it is commented out
        JLabel bookAuthor = new JLabel("<html>" + "J.K. Rowling" + "<html>" );
        bookAuthor.setFont(new Font("Times New Roman", Font.PLAIN, 25)); //To Do: add a system that sets the font size accordign to the length of the author
        bookAuthor.setBounds(125, 330, 190, 100);
        bookAuthor.setVerticalAlignment(JLabel.CENTER);
        bookAuthor.setHorizontalAlignment(JLabel.CENTER);
        // bookAuthor.setBackground(Color.yellow);
        // bookAuthor.setOpaque(true);

        JLabel bookName = new JLabel("<html>" + "Harry Potter" + "</html>");
        bookName.setFont(new Font("Times New Roman", Font.BOLD, 30));
        bookName.setBounds(125, 220, 190, 120);
        bookName.setVerticalAlignment(JLabel.TOP);
        bookName.setHorizontalAlignment(JLabel.CENTER);
        // bookName.setBackground(Color.CYAN);
        // bookName.setOpaque(true);
        
        bookAuthor.setForeground(Color.white);
        bookLabel.add(bookAuthor);
        
        bookName.setForeground(Color.white);
        bookLabel.add(bookName);

        JLabel bookRating = new JLabel("4.5", new ImageIcon(starImg), JLabel.LEFT);
        bookRating.setFont(new Font("Arial", Font.BOLD, 30));
        bookRating.setForeground(Color.white);
        bookRating.setBounds(120, 200, 190, 285);
        bookRating.setVerticalAlignment(JLabel.BOTTOM);
        bookRating.setHorizontalAlignment(JLabel.CENTER);
        // bookRating.setBackground(Color.red);
        // bookRating.setOpaque(true);
        bookLabel.add(bookRating);

        Image board = new ImageIcon(
            "reviewSystem/empty-white-paper-sheet-wood-background_48248-1428.jpg-removebg-preview.png"
            ).getImage().getScaledInstance(680, 450, 0);
        ImageIcon boardIcon = new ImageIcon(board);
        JLabel boardLabel = new JLabel(boardIcon);
        boardLabel.setBounds(-82, 190, boardIcon.getIconWidth(), boardIcon.getIconHeight());
        // boardLabel.setBackground(Color.cyan);
        // boardLabel.setOpaque(true);

        JLabel reviewTag = new JLabel("User Review");
        reviewTag.setFont(new Font("Arial", 0, 40));
        
        boardLabel.add(reviewTag);
        reviewTag.setBounds(120, 30, 432, 100);
        reviewTag.setHorizontalAlignment(JLabel.CENTER);
        // reviewTag.setBackground(Color.DARK_GRAY);
        // reviewTag.setOpaque(true);

        JLabel reviewTextLabel = new JLabel("<html>" + 
            "in cursus turpis massa tincidunt dui ut ornare lectus sit amet est placerat in egestas erat imperdiet sed euismod nisi porta lorem mollis aliquam ut porttitor leo a diam sollicitudin tempor id eu nisl nunc mi ipsum faucibus vitae aliquet nec ullamcorper sit amet risus nullam eget felis eget nunc"
            + "</html>");
        reviewTextLabel.setBounds(130, 110, 415, 300);
        reviewTextLabel.setFont(new Font("Times New Roman", 0, 25));
        reviewTextLabel.setVerticalAlignment(JLabel.TOP);
        reviewTextLabel.setHorizontalAlignment(JLabel.LEFT);

        // reviewTextLabel.setBackground(Color.blue);
        // reviewTextLabel.setOpaque(true);

        
        boardLabel.add(reviewTextLabel);
        



        frame.add(boardLabel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ReviewPage(null, 
        new Review(new User("Farhad", "567156"), null));
    }
}
