package loginSystem;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// import Pages.AdminView;

public class LoginPage {
    JFrame frame = new JFrame();

    JPanel panel = new JPanel();

    JLabel imageLabel = new JLabel(new ImageIcon("loginSystem/resized.png"));
    JLabel clickableLabel = new JLabel("New Here? Sign Up");

    JLabel userIDLabel = new JLabel("Username");
    JLabel userPasswordLabel = new JLabel("Password");

    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();

    JButton loginButton = new JButton("Login");
    JButton signInsteadButton = new JButton("SignUp Instead");

    Border compoundBorder = BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(Color.darkGray), 
                                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                                    );

    public LoginPage(HashMap <String, String> loginInfo) {
        //Without the following code snippet, when program starts, java automatically activates userIDField which makes the login page unprofessional
        //So this code snippet makes the page more viewable
        frame.addWindowListener((WindowListener) new WindowAdapter() { 
            @Override
            public void windowOpened(WindowEvent e) {
                loginButton.requestFocusInWindow();
            }
        });

        panel.setPreferredSize(new Dimension(800, 500));
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));

        imageLabel.setBounds(400, 50, 400, 350);

        clickableLabel.setBounds(125, 350, 150, 30);
        clickableLabel.setForeground(Color.BLUE);
        clickableLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clickableLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                new SignUpPage(loginInfo, frame);
                frame.setVisible(false);
            }
        });

        userIDLabel.setBounds(10, 2, 75, 25);
        userIDLabel.setForeground(Color.gray);
        userIDField.add(userIDLabel);
        userIDField.setBorder(compoundBorder);

        userPasswordLabel.setBounds(10, 2, 75, 25);
        userPasswordLabel.setForeground(Color.gray);
        userPasswordField.add(userPasswordLabel);
        userPasswordField.setBorder(compoundBorder);

        userIDField.setBounds(50, 100, 275, 30);
        userPasswordField.setBounds(50, 150, 275, 30);

        //.addMouseListener(...) methods of userIDField and userPasswordField adds them the functionality that when they are clicked, 
        //they sets texts of Labels on them to the null
        userIDField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                userIDLabel.setText(null);
            }
        });

        userPasswordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                userPasswordLabel.setText(null);
            }
        });

        //.getDocument().addDocumentListener(...) methods of userIDField and userPasswordField adds them the functionality that 
        //when anything written on them, they sets texts of Labels on them to the null
        //the reason of these code snippets are the same with .addMouseListener(...) methods but they are implemented to ensure that
        //if anything written on them, they sets Label texts to null which can be useful in cases when user uses TAB instead of clicking field itself
        userIDField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                userIDLabel.setText(null);
            }
            public void removeUpdate(DocumentEvent e) {}
            public void changedUpdate(DocumentEvent e) {}
        });
        
        userPasswordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                userPasswordLabel.setText(null);
            }
            public void removeUpdate(DocumentEvent e) {}
            public void changedUpdate(DocumentEvent e) {}
        });      


        loginButton.setBounds(50, 225, 275, 40);
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 20));

        //.addActionListener(Lambda Expression) this is basically all the logic behind Login Page 
        //such that when loginButton is pressed, this logic starts working
        //Instead of implementing ActionListener interfaceand overriding its .actionPerformed(ActionEvent) method 
        //I added all of them to a lambda expression 
        loginButton.addActionListener(e -> { //lambda expression that is in role of ActionListener interface
            String ID = userIDField.getText();
            String Password = String.valueOf(userPasswordField.getPassword());

            try {
                if(ID.length() < 1 || Password.length() < 1){
                    throw new BlankInputException();
                }

                if (ID.equals("admin") && Password.equals("admin")) {
                    frame.dispose();
                    // new AdminView();
                }
    
                if (loginInfo.containsKey(ID)) {
                    if (loginInfo.get(ID).equals(Password)) {
                        frame.dispose();
                        new WelcomePage(ID, Password); //should be: new UserView(ID, Password);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Wrong Password! ", "Warning", JOptionPane.ERROR_MESSAGE);
                        throw new Exception("Wrong Password");
                    }
                }
                else{
                    signInsteadButton.addActionListener(ee -> { //lambda expression 2 starts whose functionality is mainly to instantiate a SignUp Page class
                        new SignUpPage(loginInfo, frame);
                        frame.setVisible(false);
                    }); //lambda expression 2 ends
                    Object[] options = {"Try Again", signInsteadButton};
                    JOptionPane.showOptionDialog(null, "User Not Found, Sign Up Instead", "Not Found", 0, 0, null, options, options[1]);

                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });//lambda expression ends

        loginButton.setMnemonic(KeyEvent.VK_ENTER);

        //I added everything to the panel so that I can easily pin the panel to the center instead of trying adjust everything(JLabels, JButtons and etc.)
        panel.add(userIDField); 
        panel.add(userPasswordField);
        panel.add(loginButton);
        panel.add(imageLabel);
        panel.add(clickableLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(0xDABDAB));
        panel.setBackground(Color.white);

        //The next code snippet is to pin the panel to the center of the frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        frame.add(panel, gbc);

        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);
    }
    
    
}