package loginSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.plaf.FontUIResource;

public class LoginPage implements ActionListener {
    JFrame frame = new JFrame();

    JPanel panel = new JPanel();

    JButton loginButton = new JButton("Login");
    JButton signUpButton = new JButton("Sign Up");

    JTextField userIDField = new JTextField();

    JPasswordField userPasswordField = new JPasswordField();

    JLabel userIDLabel = new JLabel("UserID");
    JLabel userPasswordLabel = new JLabel("Password");
    JLabel messageLabel = new JLabel();

    HashMap <String, String> loginInfoNew;


    public LoginPage(HashMap <String, String> loginInfo) {
        loginInfoNew = loginInfo;

        ImageIcon bgImg = new ImageIcon("team-project-team-1/loginSystem/sa.png");

        panel.setPreferredSize(new Dimension(500, 500));
        panel.setLayout(null);

        userIDLabel.setBounds(50, 100, 75, 25);
        userPasswordLabel.setBounds(50, 150, 75, 25);

        messageLabel.setBounds(125, 250, 250, 35);
        messageLabel.setFont(new FontUIResource("Times New Roman", FontUIResource.BOLD, 25));

        userIDField.setBounds(125, 100, 200, 25);
        userPasswordField.setBounds(125, 150, 200, 25);

        loginButton.setBounds(125, 225, 100, 25);
        signUpButton.setBounds(250, 225, 100, 25);

        loginButton.addActionListener(this);
        signUpButton.addActionListener(this);

        loginButton.setMnemonic(KeyEvent.VK_ENTER);

        panel.add(userIDLabel);
        panel.add(userPasswordLabel);
        panel.add(messageLabel);
        panel.add(userIDField);
        panel.add(userPasswordField);
        panel.add(loginButton);
        panel.add(signUpButton);

        
        

        JLabel background = new JLabel(bgImg);
        background.setBounds(0, 0, 1000, 1000);
        background.setLayout(null);
        // frame.add(background);
        frame.setIconImage(bgImg.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        frame.add(panel, gbc);

        frame.setLocationRelativeTo(null);
        



        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String ID = userIDField.getText();
            String Password = String.valueOf(userPasswordField.getPassword());

            System.out.println(ID + " " + Password);
            System.out.println(loginInfoNew.get(ID));

            if (loginInfoNew.containsKey(ID)) {
                if (loginInfoNew.get(ID).equals(Password)) {
                    frame.dispose();
                    new WelcomePage(ID, Password);
                }
                else{
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("Wrong Password!");
                }
            }
            else{
                messageLabel.setForeground(Color.red);
                messageLabel.setText("User Not Found!");
            }
        }

        else if (e.getSource() == signUpButton) {
            String ID = userIDField.getText();
            String Password = String.valueOf(userPasswordField.getPassword());

            userIDField.setText("");
            userPasswordField.setText("");

            loginInfoNew.put(ID, Password);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("team-project-team-1/loginSystem/Book1.csv", true))) {
                bw.newLine();
                bw.append(ID + "," + Password);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
}