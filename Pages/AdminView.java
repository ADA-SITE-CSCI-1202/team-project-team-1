package Pages;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Models.Admin;

public class AdminView {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel title = new JLabel();
    JButton generalDB = new JButton();
    JButton edit = new JButton();
    JButton delete = new JButton();
    JButton exit = new JButton();

    public AdminView(Admin admin) {

        frame.setVisible(true);
    }
}
