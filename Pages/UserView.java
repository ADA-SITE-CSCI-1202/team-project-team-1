package Pages;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Models.User;

public class UserView {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel title = new JLabel();
    JButton generalDB = new JButton();
    JButton personalDB = new JButton();
    JButton logOut = new JButton();

    public UserView(User user) {
        
        frame.setVisible(true);
    }
}
