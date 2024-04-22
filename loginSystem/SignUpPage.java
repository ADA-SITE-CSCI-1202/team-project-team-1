package loginSystem;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.MouseEvent;

public class SignUpPage extends LoginPage{
    JPasswordField AgainPasswordField = new JPasswordField();

    JLabel AgainPasswordLabel = new JLabel("Password");

    JButton signUpButton = new JButton("Sign Up");

    JLabel newClickableLabel = new JLabel("Go Back");

    

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
                    JOptionPane.showMessageDialog(null, "Please, fill out all the blanks! ", "Warning", JOptionPane.ERROR_MESSAGE);
                    throw new Exception("bla bla");
                }
        
                if (loginInfo.containsKey(ID)) {
                    JOptionPane.showMessageDialog(null, "Username Already Exists", "Warning", JOptionPane.ERROR_MESSAGE);
                    throw new Exception();
                }
    
    
                userIDField.setText("");
                userPasswordField.setText("");
                AgainPasswordField.setText("");
    
                if (Password.equals(AgainPassword)) {
                    loginInfo.put(ID, Password);
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("loginSystem/Book1.csv", true))) {
                        bw.newLine();
                        bw.append(ID + "," + Password);
                        JOptionPane.showMessageDialog(null, "Signed Up, successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();   
                    }
                }  
                else{
                    JOptionPane.showMessageDialog(null, "Passwords do not match!", "Warning", JOptionPane.ERROR_MESSAGE);
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
                

            }  
        }); 


        

        newClickableLabel.setBounds(160, 350, 150, 30);
        panel.add(newClickableLabel);

        

        
        
    }
    
}
