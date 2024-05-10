package Pages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Models.Book;
import Models.CSVMananger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminCUD extends JFrame {
    // Fields for Title and Author
    private JTextField titleField, authorField;
    // Button to trigger action
    private JButton addButton;

    public adminCUD(boolean isEdit, String oldTitle, JTable table, int row, DefaultTableModel model) {
        // Frame setup
        setTitle("Add Book Details");
        setSize(300, 150);
        setLayout(new GridLayout(3, 2, 10, 10)); // Grid layout for organized form

        // Label and text field for Title
        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        // Label and text field for Author
        add(new JLabel("Author:"));
        authorField = new JTextField();
        add(authorField);

        // Adding space to align the button to the right
        add(new JLabel(""));
        
        // Button for adding the book
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action performed on button click
                String title = titleField.getText();
                String author = authorField.getText();
                titleField.setText("");
                authorField.setText("");
                if (!isEdit){
                    try {
                        if (CSVMananger.bookExists(new Book(title, author))) {
                            JOptionPane.showMessageDialog(null, "The book already exists in the CSV file.", "Warning", JOptionPane.ERROR_MESSAGE);
                            throw new Exception();
                        }
                        
                        CSVMananger.addToCsv(new Book(title, author));
                        JOptionPane.showMessageDialog(null, "New Book Succefully added! ", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                        model.addRow(new Object[]{title, author, "No Rating", "No Review"});
                        
                    } catch (Exception neme) {
                        neme.printStackTrace();
                    }
                }
                else{
                    CSVMananger.editInCsv(new Book(title, author), oldTitle);
                    table.setValueAt(title, row, 0);
                    table.setValueAt(author, row, 1);
                }
                
            }
        });
        add(addButton);

        // Set the window to be visible
        setVisible(true);
    }
}