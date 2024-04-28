package Pages;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Models.User;

public class UserView {
    private User user;

    JFrame frame = new JFrame();
    
    JPanel panel = new JPanel();
    
    JLabel projectName = new JLabel("Library Management");
    JLabel title = new JLabel("Hi, " + user.getUsername());
    
    JButton generalDB = new JButton("General");
    JButton personalDB = new JButton("Personal");
    JButton logOut = new JButton("Log Out");


    public UserView(User user) {
        this.user = user;


        
        frame.setVisible(true);
    }
}
