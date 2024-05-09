package Pages;
import javax.naming.TimeLimitExceededException;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import Models.Book;
import Models.GeneralBook;
import Models.PersonalBook;
import Models.PersonalManager;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class BookProfileForm extends JFrame {

    public BookProfileForm(GeneralBook book) {
        setTitle("Profile Book Form");
        setSize(800, 600);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 50, 20, 50);

        // Title and Author (as labels now)
        addLabelAndFixedLabel("Title:", book.getTitle(), 0);
        addLabelAndFixedLabel("Author:", book.getAuthor(), 1);

        // Time Spent
        JTextField timefield = new JTextField();
        addLabelAndTextField("Time Spent:", timefield, 2);

        // Start and End Dates with all possible dates and months
        JComboBox<String> startDayCombo = new JComboBox<>();
        JComboBox<String> startMonthCombo = new JComboBox<>();
        JTextField startYear = new JTextField();
        addLabelAndFullDateCombo("Start Date:", startDayCombo, startMonthCombo, startYear, 3);

        JComboBox<String> endDayCombo = new JComboBox<>();
        JComboBox<String> endMonthCombo = new JComboBox<>();
        JTextField endYear = new JTextField();
        addLabelAndFullDateCombo("End Date:", endDayCombo, endMonthCombo, endYear, 4);

        // Rating and Extended Review
        GridBagConstraints constraintz = new GridBagConstraints();
        constraintz.fill = GridBagConstraints.HORIZONTAL;
        constraintz.insets = new Insets(10, 50, 10, 50);
        

        JLabel label = new JLabel("Rating (out of 5):");
        constraintz.gridx = 0;
        constraintz.gridy = 5;
        add(label, constraintz);

        JComboBox<String> ratingComboBox = new JComboBox<>(new String[]{"No Rating", "1", "2", "3", "4", "5"});
        constraintz.gridx = 1;
        add(ratingComboBox, constraintz);
        
        JTextArea reviewTextArea = new JTextArea(4, 20);
        addLabelAndTextArea("Review (Character Limit: 300):", reviewTextArea, 6, 300);

        // Status with ComboBox
        JLabel statusLabel = new JLabel("Status:");
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(statusLabel, constraints);

        JComboBox<String> statusCombo = new JComboBox<>(new String[] {"NOT_STARTED", "IN_PROGRESS", "COMPLETED"});
        constraints.gridx = 1;
        add(statusCombo, constraints);

        // Save Button
        JButton saveButton = new JButton("Add");
        

        saveButton.addActionListener((e) -> {
            try {
                String timeSpent = timefield.getText();
                String ratingText = String.valueOf(ratingComboBox.getSelectedItem());
                String review = reviewTextArea.getText();

                timeSpent = (timeSpent.length() > 0) ? timeSpent : "Add Time Spent";
                int rating = (ratingText != "No Rating") ? Integer.parseInt(ratingText) : -1;
                review = (review.length() > 0) ? review : "Add Review";

                String startDay = String.valueOf(startDayCombo.getSelectedItem());
                String startMonth = String.valueOf(startMonthCombo.getSelectedItem());
                String startedYear = String.valueOf(startYear.getText());

                String endDay = String.valueOf(endDayCombo.getSelectedItem());
                String endMonth = String.valueOf(endMonthCombo.getSelectedItem());
                String endedYear = String.valueOf(endYear.getText());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                String formattedStartDate = "Add Start Date";
                String formattedEndDate = "Add End Date";

                LocalDate startDate = LocalDate.MIN;
                LocalDate endDate = LocalDate.MAX;

                if (startDay.length() > 0 &&  startMonth.length() > 0 && startedYear.length() > 0 ){
                    Month startedMonth = Month.valueOf(startMonth.toUpperCase());
                    

                    startDate = LocalDate.of(Integer.parseInt(startedYear), startedMonth.getValue(), Integer.parseInt(startDay));
                    formattedStartDate = startDate.format(formatter);
                }

                if (endDay.length() > 0 && endMonth.length() >0 && endedYear.length() > 0){
                    Month endedMonth = Month.valueOf(endMonth.toUpperCase());

                    endDate = LocalDate.of(Integer.parseInt(endedYear), endedMonth.getValue(), Integer.parseInt(endDay));
                    formattedEndDate = endDate.format(formatter);        
                }

                // if (GeneralCSV.contains()) {
                //     throw new IllegalAccessError();
                // }
                    
                if (startDate.compareTo(endDate) > 0)
                    throw new TimeLimitExceededException();

                System.out.println(timeSpent + " " + formattedStartDate + " " + formattedEndDate + " " + rating + " " +  review);
                PersonalManager pm = new PersonalManager();
                pm.addPersonalBookToCsv(new PersonalBook(book.getTitle(), book.getAuthor(), ))

                timefield.setText("");
                reviewTextArea.setText("");
                startYear.setText("");
                endYear.setText("");

                ratingComboBox.setSelectedItem(0);
                startDayCombo.setSelectedItem(0);
                endDayCombo.setSelectedItem(0);
                startMonthCombo.setSelectedItem(0);
                endMonthCombo.setSelectedItem(0);
                
                

            } catch (TimeLimitExceededException ex) {
                JOptionPane.showMessageDialog(null, "The End Date Cannot Be After the Start Date!", "Warning", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalAccessError iae){
                JOptionPane.showMessageDialog(null, "The Book Already Exists in General Database", "Warning", JOptionPane.ERROR_MESSAGE);

            }
            



        });

        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        add(saveButton, constraints);

        this.setVisible(true);
        
    }

    private void addLabelAndFixedLabel(String labelText, String fixedText, int yPos) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 50, 10, 50);

        JLabel label = new JLabel(labelText);
        constraints.gridx = 0;
        constraints.gridy = yPos;
        add(label, constraints);

        JLabel fixedLabel = new JLabel(fixedText);
        constraints.gridx = 1;
        add(fixedLabel, constraints);
    }

    private void addLabelAndTextField(String labelText, JTextField textField, int yPos) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 50, 10, 50);


        JLabel label = new JLabel(labelText);
        constraints.gridx = 0;
        constraints.gridy = yPos;
        add(label, constraints);

        constraints.gridx = 1;
        add(textField, constraints);
    }

    private void addLabelAndTextArea(String labelText, JTextArea textArea, int yPos, int charLimit) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 50, 10, 50);
        constraints.gridwidth = 2;

        JLabel label = new JLabel(labelText);
        constraints.gridx = 0;
        constraints.gridy = yPos;
        add(label, constraints);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        constraints.gridy++;
        add(scrollPane, constraints);

        ((AbstractDocument)textArea.getDocument()).setDocumentFilter(new DocumentFilter() {
            private final int maxChars = charLimit;

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if ((fb.getDocument().getLength() + string.length()) <= maxChars) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if ((fb.getDocument().getLength() + text.length() - length) <= maxChars) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    private void addLabelAndFullDateCombo(String labelText, JComboBox<String> dayCombo, JComboBox<String> monthCombo, JTextField yearField, int yPos) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 50, 10, 50);

        JLabel label = new JLabel(labelText);
        constraints.gridx = 0;
        constraints.gridy = yPos;
        add(label, constraints);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        for (int day = 1; day <= 31; day++) {
            dayCombo.addItem(String.valueOf(day));
        }
        dayCombo.setSelectedIndex(0);

        String[] months = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
        };
        for (String month : months) {
        monthCombo.addItem(month);
        }

        monthCombo.setSelectedIndex(0);
        datePanel.add(dayCombo);
        datePanel.add(monthCombo);
        yearField.setPreferredSize(new Dimension(50, 20));
        datePanel.add(yearField);

        constraints.gridx = 1;
        add(datePanel, constraints);
    }


}
