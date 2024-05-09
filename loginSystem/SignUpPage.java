package loginSystem;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.MouseEvent;

    class BlankInputException extends IllegalArgumentException {
        public BlankInputException() {
            super("Empty Blanks");
            JOptionPane.showMessageDialog(null, "Please, fill out all the blanks! ", 
            "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    class InvalidUsernameLengthException extends IllegalArgumentException {
        public InvalidUsernameLengthException() {
            super("Username out of Criteria");
            JOptionPane.showMessageDialog(null, "Username should be between 5 and 15 characters! ", 
            "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    class InvalidPasswordFormatException extends IllegalArgumentException {
        public InvalidPasswordFormatException() {
            super("Password out of Criteria");
            JOptionPane.showMessageDialog(null, "Password should contain at least 8 characters, including at least 1 Uppercase, 1 Lowercase letter, and 1 number ", "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    class PasswordMismatchException extends IllegalStateException {
        public PasswordMismatchException() {
            super("Passwords Do Not Match");   
            JOptionPane.showMessageDialog(null, "Passwords should be the same", "Warning", JOptionPane.ERROR_MESSAGE);     
        }
    }
    
    class DuplicateUsernameException extends RuntimeException {
        public DuplicateUsernameException() {
            super("Duplicate Username");
            JOptionPane.showMessageDialog(null, "Username Already Exists", "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }

public class SignUpPage extends LoginPage{
    JPasswordField AgainPasswordField = new JPasswordField();

    JLabel AgainPasswordLabel = new JLabel("Password");
    JLabel newClickableLabel = new JLabel("Go Back");

    JButton signUpButton = new JButton("Sign Up");


    public SignUpPage(HashMap<String, String> loginInfo, JFrame loginFrame) {
        super(loginInfo);

        frame.setSize(500, 600);

        imageLabel.setBounds(0, 0, 0, 0);
        clickableLabel.setBounds(0, 0, 0, 0);
        loginButton.setBounds(0, 0, 0, 0);

        AgainPasswordLabel.setBounds(10, 2, 75, 25);
        AgainPasswordLabel.setForeground(Color.gray);
        AgainPasswordField.add(AgainPasswordLabel);

        panel.setPreferredSize(new Dimension(400, 450));

        AgainPasswordField.setBounds(50, 200, 275, 30);
        AgainPasswordField.setBorder(compoundBorder);
        AgainPasswordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                AgainPasswordLabel.setText(null);
            }
        });
        AgainPasswordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                AgainPasswordLabel.setText(null);
            }
            public void removeUpdate(DocumentEvent e) {}
            public void changedUpdate(DocumentEvent e) {}
        });
        panel.add(AgainPasswordField);

        

        signUpButton.setBounds(50, 275, 275, 40);
        panel.add(signUpButton);

        signUpButton.addActionListener(e -> {
            String ID = userIDField.getText();
            String Password = String.valueOf(userPasswordField.getPassword());
            String AgainPassword = String.valueOf(AgainPasswordField.getPassword());
            System.out.println(ID);

            try { 
                if (ID.length() < 1 || Password.length() < 1 || AgainPassword.length() < 1) { 
                    throw new BlankInputException(); // if user pushs signup button without entering anything, it should give an error
                }

                if (ID.length() < 5 || ID.length() > 15 ) {
                    throw new InvalidUsernameLengthException(); //if username length is not between 5 and 15 characters (5 and 15 is okay), give an error
                }

                if (Password.length() < 8 || !Password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")){
                    throw new InvalidPasswordFormatException(); // if password is shorter than 8 characters or does not contain at least 
                }                                               // 1 uppercase, lowercase letter and number, give an error

                if (!Password.equals(AgainPassword)){
                    throw new PasswordMismatchException(); //if second password and first password are not the same, give an error
                }
        
                if (loginInfo.containsKey(ID)) {
                    throw new DuplicateUsernameException(); //if username already exists, give an error
                }
    
    
                userIDField.setText("");
                userPasswordField.setText("");
                AgainPasswordField.setText("");
    
                    loginInfo.put(ID, Password);
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("loginSystem/Users.csv", true))) {
                        bw.newLine();
                        bw.append(ID + "," + Password);
                        JOptionPane.showMessageDialog(null, "Signed Up, successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();   
                    }
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });






        newClickableLabel.setForeground(Color.BLUE);
        newClickableLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        newClickableLabel.addMouseListener(new MouseAdapter()  {  
            @Override
            public void mouseClicked(MouseEvent e)  {  
                frame.dispose();
                loginFrame.setVisible(true);
                Path userFolder = Paths.get("Data/User");
                if (!Files.exists(userFolder)){
                    try {
                        Files.createDirectory(userFolder);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                File user = new File("Data/User" + userIDField.getText());
                

            }  
        }); 


        

        newClickableLabel.setBounds(160, 350, 150, 30);
        panel.add(newClickableLabel);

        

        
        
    }
    
}
