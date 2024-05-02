package reviewSystem;
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
        frame.setSize(800, 650);
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
        Image bookImg = bookIcon.getImage().getScaledInstance(400, 700, 0);
        JLabel bookLabel = new JLabel(new ImageIcon(bookImg));
        bookLabel.setBounds(400, -150, 400, 700);
        bookLabel.setLayout(null);
        frame.add(bookLabel);

        // JLabel bookAuthor = new JLabel(book.getAuthor()); // this is the real code, but as I'm too lazy to create a book, it is commented out
        JLabel bookAuthor = new JLabel("J.K.Rowling");

        bookAuthor.setFont(new Font("Times New Roman", 0, 20)); //To Do: add a system that sets the font size accordign to the length of the author
        bookAuthor.setBounds(140, 400, 300, 100);
        
        bookAuthor.setForeground(Color.white);
        bookLabel.add(bookAuthor);



        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ReviewPage(null, 
        new Review(new User("Farhad", "567156"), null));
    }
}
