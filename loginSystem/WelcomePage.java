package loginSystem;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class WelcomePage {
    JFrame frame = new JFrame();
    JLabel welcomLabel = new JLabel("Congrats!");

    public WelcomePage(String ID, String Password) {
        welcomLabel.setBounds(125, 200, 250, 100);
        welcomLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);

        frame.add(welcomLabel);



        frame.setVisible(true);
    }

}
