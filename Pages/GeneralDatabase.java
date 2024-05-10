package Pages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import Models.Book;
import Models.CSVMananger;
import Models.GeneralBook;
import Models.Review;
import Models.User;
import Pages.reviewSystem.ReviewPage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GeneralDatabase{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JScrollPane scrollPane;
    JTable table;
    DefaultTableModel model;
    TableRowSorter rowSorter;
    String column[];
    String data[][];
    JButton addButton;
    ActionListener listener;
    String username;
    JLabel goBack = new JLabel("Go Back");

    public <M> String arrayListToString(ArrayList<M> list) { //Reads arraylist, as in case review list, then converts it to string in a formatted way
        return list.stream()
                   .map(Object::toString)
                   .collect(Collectors.joining(", "));  // Customize delimiter here
    }

    ResourceBundle messages;
    public GeneralDatabase(String username, boolean isAdmin, ResourceBundle bundle) {
        messages = bundle;
        goBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if (isAdmin)
                    new AdminView(messages);
                else
                    new UserView(new User(username, " "), messages);
                frame.dispose();
            }
        });
        frame.add(goBack);
        goBack.setBounds(100, 10, 100, 100);
        goBack.setFont(new Font("Arial", 0, 20));
        goBack.setForeground(Color.blue);


        this.username = username;
 
        column = new String[]{messages.getString("title"),messages.getString("author"),messages.getString("Rating"), messages.getString("Reviews")};

        model = new DefaultTableModel(data, column); 
        table = new JTable(model){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                return false;               
            }
        };
        table.removeEditor(); //User cannot edit text on table

        CSVMananger manager = new CSVMananger();
        List<GeneralBook> books = manager.readFromCsv();

        for (GeneralBook generalBook : books) {
            model.addRow(new Object[]{generalBook.getTitle(), generalBook.getAuthor(), (generalBook.getRating() == 0) ? messages.getString("No_Rating") : generalBook.getRating(), !generalBook.getReviews().isEmpty() ? generalBook.getReviews() : messages.getString("No_Review")});
        }

        table.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                if (row == -1) table.clearSelection();

                else if (row != -1 && column == 3) {
                    table.clearSelection();
                    if (table.getValueAt(row, column) != messages.getString("No Review")){
                        Rectangle cellRect = table.getCellRect(row, column, true);
                        String text = table.getValueAt(row, column).toString();
                        FontMetrics fm = table.getFontMetrics(table.getFont());
                        FontRenderContext frc = fm.getFontRenderContext();

                        // Prepare to measure the text
                        AttributedString attrStr = new AttributedString(text);
                        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
                        int xStart = cellRect.x + 2; // Start X position
                        int yStart = cellRect.y + 5; // Start Y position

                        // Measure lines
                        while (measurer.getPosition() < text.length()) {
                            TextLayout layout = measurer.nextLayout(cellRect.width - 4);

                            // Check each word in this line
                            String lineText = text.substring(measurer.getPosition() - layout.getCharacterCount(), measurer.getPosition());
                            String[] words = lineText.split(", ");
                            int xPos = xStart;

                            for (String word : words) {
                                int wordWidth = fm.stringWidth(word.trim());
                                Rectangle wordRect = new Rectangle(xPos, yStart, wordWidth, (int) layout.getAscent() + (int) layout.getDescent());

                                String title = (String) table.getValueAt(row, 0);
                                String author = (String) table.getValueAt(row, 1);
                                Review mainReview = new Review();

                                for (GeneralBook generalBook : books) {
                                    if (generalBook.getTitle().equals(title)) {
                                        List<Review> reviews = generalBook.getReviews();
                                        for (Review review : reviews) {
                                            if (username.equals(review.getUser())) {
                                                mainReview = review;
                                            }
                                        }
                                    }
                                }

                                if (wordRect.contains(e.getPoint())) {
                                    JOptionPane.showMessageDialog(table, "You clicked on word: " + word.trim());
                                    ReviewPage rp = new ReviewPage(new Book(title, author), mainReview, false, table, column, row); 
                                    
                                    return; // Stop after the first match
                                }

                                xPos += wordWidth + fm.stringWidth(", "); // Adjust position for next word
                            }

                            yStart += layout.getAscent() + layout.getDescent() + layout.getLeading(); // Adjust Y position for the next line
                        }
                    } 
            }
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                table.clearSelection();
            }
        });

        addButton = new JButton("Add");
        if (isAdmin != true){
            listener = e -> {
                int row = table.getSelectedRow();
                table.clearSelection();
                try {
                    List<Review> reviewsList = new ArrayList<>();
                    float rating = 0;

                    LinkedList list = new LinkedList<>(Arrays.asList(IntStream.range(0, 2)
                    .mapToObj(col -> table.getValueAt(row, col))
                    .toArray()));

                    for (GeneralBook generalBook : books) {
                        if(generalBook.getTitle().equals((String)list.get(0))) {
                            reviewsList = generalBook.getReviews();
                            rating = generalBook.getRating();
                        }
                    }

                    GeneralBook book = new GeneralBook((String)list.get(0), (String)list.get(1), rating, reviewsList);
                    new BookProfileForm(book, username);
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "You Should Select a Row! ", "Warning", JOptionPane.ERROR_MESSAGE);
                }            
            };
        }
        else{
            listener = e -> {
                table.clearSelection();
                try {
                    new adminCUD(false, "", table, 0, model);
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "You Should Select a Book! ", "Warning", JOptionPane.ERROR_MESSAGE);
                }            
            };

            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener((e) -> {
                int row = table.getSelectedRow();
                LinkedList list = new LinkedList<>(Arrays.asList(IntStream.range(0, 2)
                    .mapToObj(col -> table.getValueAt(row, col))
                    .toArray()));

                CSVMananger.removeFromCsv((String)list.get(0)); 
                model.removeRow(row);
            });  
            frame.add(removeButton);
            removeButton.setBounds(1020, 105, 100, 35);

            JButton editButton = new JButton("Edit");
            editButton.addActionListener((e) -> {
                try {
                    int row = table.getSelectedRow();
                    System.out.println(row);
                    LinkedList list = new LinkedList<>(Arrays.asList(IntStream.range(0, 2)
                        .mapToObj(col -> table.getValueAt(row, col))
                        .toArray()));
                    
                    new adminCUD(true, (String)list.get(0), table, row, model);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "You Should Select a Book! ", "Warning", JOptionPane.ERROR_MESSAGE);
                }
            });

            frame.add(editButton);
            editButton.setBounds(780, 105, 100, 35);
            
        }
        

        addButton.addActionListener(listener);
        addButton.setBounds(900, 105, 100, 35);
        frame.add(addButton);



        JLabel searchLabel = new JLabel("Search");
        searchLabel.setBounds(340, 100, 100, 40);
        searchLabel.setFont(new Font("Arial", 0, 25));
        searchLabel.setForeground(Color.white);
        frame.add(searchLabel);

        JTextField searchBookField = new JTextField();
        searchBookField.setBounds(450, 100, 300, 40);
        frame.add(searchBookField);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        
        searchBookField.getDocument().addDocumentListener(new DocumentListener(){ //for searching

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchBookField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text , 0, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchBookField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}

        });

        TableCellRenderer tableCellRenderer = new TextAreaRenderer();
        table.setDefaultRenderer(Object.class, tableCellRenderer);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 850);
        frame.getContentPane().setBackground(new Color(196, 164, 132));
        frame.setLayout(null);

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 1100, 650);

        scrollPane.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 

        table.setFillsViewportHeight(true); // This makes sure empty space below rows does not behave as a clickable row.


        TableColumnModel columnModel = table.getColumnModel();
        columnModel.setColumnSelectionAllowed(false);

        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(Color.black);

        table.setCellSelectionEnabled(true);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);



        table.getTableHeader().setPreferredSize(
            new Dimension(table.getWidth(), 40)
        );
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setHeaderRenderer(renderer);
        }

        //Without the following code snippet, when program starts, java automatically activates userIDField which makes the login page unprofessional
        //So this code snippet makes the page more viewable
        frame.addWindowListener((WindowListener) new WindowAdapter() { 
            @Override
            public void windowOpened(WindowEvent e) {
                frame.requestFocusInWindow();
            }
        });
        
        frame.add(scrollPane);
        frame.setVisible(true);

        

        
    }

    class TextAreaRenderer extends JTextArea implements TableCellRenderer {
        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true); 
        }
    
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (isSelected) {
                this.setForeground(table.getSelectionForeground());
                this.setBackground(table.getSelectionBackground());
            } else {
                this.setForeground(table.getForeground());
                this.setBackground(table.getBackground());
            }
            this.setText((value == null) ? "" : value.toString());
            adjustRowHeight(table, row, column);

            
            // if (column == table.getColumnModel().getColumnIndex("Action")){
            //     this.setFont(new Font("Arial", Font.BOLD, 15));
            //     this.setForeground(new Color(2, 48, 32));
                
            // }
            // else {
                this.setFont(new Font("Arial", 0, 15));
                this.setForeground(Color.black);
            // }
                

            if (column == table.getColumnModel().getColumnIndex(messages.getString("Reviews"))){
                this.setForeground(Color.blue);
                this.setBorder(new EmptyBorder(0, 0, 0, 0));
            }
            else if (column == 9){
                this.setBorder(new EmptyBorder(0, 0, 0, 0));
            }
            else{
                int padding = (this.getWidth() - this.getFontMetrics(this.getFont()).stringWidth(this.getText())) / 2;
                this.setBorder(new EmptyBorder(0, padding, 0, padding/3));
            }
                
                
    
            return this;
        }
    
        private void adjustRowHeight(JTable table, int row, int column) {   //If the text is longer than the width of the cell, the
            setBounds(table.getCellRect(row, column, false));//text occupies the next line as well
            int preferredHeight = getPreferredSize().height;
            if (table.getRowHeight(row) < preferredHeight) {
                table.setRowHeight(row, preferredHeight/2);
            }
        }
    }
}

    
