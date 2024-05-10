package Pages;

import javax.swing.*;

import Pages.loginSystem.IDandPassword;
import Pages.loginSystem.LoginPage;

import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageDetecter {
    String languageText;
    Locale locale;
    ResourceBundle messages;
    JButton button = new JButton("Start");
    @SuppressWarnings("deprecation")
    public LanguageDetecter(){
        // Create and set up the window
        JFrame frame = new JFrame("Simple ComboBox Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(null);

        // Create the combo box with items
        JComboBox<String> language = new JComboBox<>(new String[]{"English", "Azerbaijani"});
        language.setBounds(50, 50, 200, 30); // Set the position and size of the combo box

        // Add ActionListener to the combo box
        frame.add(button);
        button.setBounds(50, 100, 100, 30);
        button.addActionListener((e) -> {
            languageText = (String) language.getSelectedItem();

            if (languageText == "English") {
                locale = new Locale("en", "En");
                messages = ResourceBundle.getBundle("languages.messages", locale);
                IDandPassword iDandPassword = new IDandPassword();
                new LoginPage(iDandPassword.getLoginInfo(), messages);
                frame.dispose();
            }
            else{
                locale = new Locale("az", "Az");
                messages = ResourceBundle.getBundle("languages.messages", locale);
                IDandPassword iDandPassword = new IDandPassword();
                new LoginPage(iDandPassword.getLoginInfo(), messages);
                frame.dispose();
            }
        });

        // Add the combo box to the frame
        frame.add(language);

        // Display the window
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
        frame.setVisible(true);
    }
}
