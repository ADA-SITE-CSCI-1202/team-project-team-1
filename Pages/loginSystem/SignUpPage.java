package Pages.loginSystem;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.MouseEvent;
import Models.PersonalManager;

class BlankInputException extends IllegalArgumentException {
    public BlankInputException(String message) {
        super("Empty Blanks");
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.ERROR_MESSAGE);
    }
}

class InvalidUsernameLengthException extends IllegalArgumentException {
    public InvalidUsernameLengthException(String message) {
        super("Username out of Criteria");
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.ERROR_MESSAGE);
    }
}

class InvalidPasswordFormatException extends IllegalArgumentException {
    public InvalidPasswordFormatException(String message) {
        super("Password out of Criteria");
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.ERROR_MESSAGE);
    }
}

class PasswordMismatchException extends IllegalStateException {
    public PasswordMismatchException(String message) {
        super("Passwords Do Not Match");
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.ERROR_MESSAGE);
    }
}

class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String message) {
        super("Duplicate Username");
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.ERROR_MESSAGE);
    }
}

public class SignUpPage extends LoginPage{
    JPasswordField AgainPasswordField = new JPasswordField();

    JLabel AgainPasswordLabel = new JLabel("Password");
    JLabel newClickableLabel = new JLabel("Go Back");

    JButton signUpButton = new JButton("Sign Up");

    ResourceBundle messages;

    public SignUpPage(HashMap<String, String> loginInfo, JFrame loginFrame, ResourceBundle bundle) {
        super(loginInfo, bundle);

        messages = bundle;

        frame.setSize(500, 600);

        imageLabel.setBounds(0, 0, 0, 0);
        clickableLabel.setBounds(0, 0, 0, 0);
        loginButton.setBounds(0, 0, 0, 0);

        AgainPasswordLabel.setText(messages.getString("againPasswordLabel"));
        AgainPasswordLabel.setBounds(10, 2, 150, 25);
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

        signUpButton.setText(messages.getString("signUpButton"));
        signUpButton.setBounds(50, 275, 275, 40);
        panel.add(signUpButton);

        signUpButton.addActionListener(e -> {
            String ID = userIDField.getText();
            String Password = String.valueOf(userPasswordField.getPassword());
            String AgainPassword = String.valueOf(AgainPasswordField.getPassword());

            try {
                if (ID.length() < 1 || Password.length() < 1 || AgainPassword.length() < 1) {
                    throw new BlankInputException(messages.getString("blankInputError"));
                }

                if (ID.length() < 5 || ID.length() > 15) {
                    throw new InvalidUsernameLengthException(messages.getString("usernameLengthError"));
                }

                if (Password.length() < 8 || !Password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")) {
                    throw new InvalidPasswordFormatException(messages.getString("passwordFormatError"));
                }

                if (!Password.equals(AgainPassword)) {
                    throw new PasswordMismatchException(messages.getString("passwordMismatch"));
                }

                if (loginInfo.containsKey(ID)) {
                    throw new DuplicateUsernameException(messages.getString("duplicateUsernameError"));
                }

                userIDField.setText("");
                userPasswordField.setText("");
                AgainPasswordField.setText("");

                loginInfo.put(ID, Password);
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("Data/accounts.csv", true))) {
                    bw.newLine();
                    bw.append(ID + "," + Password);
                    JOptionPane.showMessageDialog(null, messages.getString("signupSuccess"), "Successful", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Path userFolder = Paths.get("Data/UserData");
                if (!Files.exists(userFolder)) {
                    try {
                        Files.createDirectory(userFolder);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                PersonalManager pm = new PersonalManager();
                pm.createUserCsv(ID);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        newClickableLabel.setText(messages.getString("newClickableLabel"));
        newClickableLabel.setForeground(Color.BLUE);
        newClickableLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        newClickableLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                loginFrame.setVisible(true);
            }
        });

        newClickableLabel.setBounds(160, 350, 150, 30);
        panel.add(newClickableLabel);
    }
}
