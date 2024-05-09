import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Rectangle;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Models.Review;
import Models.User;

import javax.swing.BorderFactory;
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

    public <M> String arrayListToString(ArrayList<M> list) { //Reads arraylist, as in case review list, then converts it to string in a formatted way
        return list.stream()
                   .map(Object::toString)
                   .collect(Collectors.joining(", "));  // Customize delimiter here
    }




    @SuppressWarnings("unchecked")
    GeneralDatabase() {
 
        column = new String[]{"Title","Author","Rating", "Reviews", "Action"}; 
        data = new String[][]{ {"101","Amit","670000", arrayListToString(new ArrayList<>(Arrays.asList("we", "s"))), "Add"},    
        }; //Murad will read and write from csv to here

        model = new DefaultTableModel(data, column); 
        table = new JTable(model){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                return false;               
            }
        };
        table.removeEditor(); //User cannot edit text on table


        ArrayList<Review> reviewList= new ArrayList<>(Arrays.asList( //this is actually for writing personal db
            new Review(new User("Farhad", "sss"), "Cox gozel", 5),
            new Review(new User("Murad", "sss"), "Yaxsi kimi", 5),
            new Review(new User("Ibrahim", "sss"), "Orta", 5),
            new Review(new User("Ibrahim", "sss"), "Orta", 5),
            new Review(new User("Ibrahim", "sss"), "Orta", 5),
            new Review(new User("Ibrahim", "sss"), "Orta", 5),
            new Review(new User("irvam", "sss"), "Orta", 5),
            new Review(new User("orvam", "sss"), "Orta", 5),
            new Review(new User("oro", "sss"), "Orta", 5),
            new Review(new User("ara", "sss"), "Orta", 5),
            new Review(new User("para", "sss"), "Orta", 5)
        )); //Murad can pass like that(note that this is just as a framework to what it should look like)
       

        //The following line is to add a new row and can be used in adminview or personal db
        model.addRow(new Object[]{"The Little Prince", "IDK", 5, arrayListToString(reviewList), "Add" });
        model.addRow(new Object[]{"The Little Prince", "IDK", 5, arrayListToString(reviewList), "Add" });

        table.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                if (row != -1 && column == 3) {
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

                            if (wordRect.contains(e.getPoint())) {
                                JOptionPane.showMessageDialog(table, "You clicked on word: " + word.trim());
                                return; // Stop after the first match
                            }

                            xPos += wordWidth + fm.stringWidth(", "); // Adjust position for next word
                        }

                        yStart += layout.getAscent() + layout.getDescent() + layout.getLeading(); // Adjust Y position for the next line
                    }
                }

                else if (row != -1 && column == table.getColumnModel().getColumnIndex("Action") && column == 4){ //When add is pushed, user should be able to add the book to the personal db
                    LinkedList list = new LinkedList<>(Arrays.asList(IntStream.range(0, 2)
                                                        .mapToObj(col -> table.getValueAt(row, col))
                                                        .toArray()));
                    JOptionPane.showMessageDialog(null, "The Book Successfully Added to the Personal DataBase! ", "Success", JOptionPane.INFORMATION_MESSAGE);

                                            
                    System.out.println(list); //Murad should add this to personal database, instead of printing
                    

                }
            }
        });

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

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.setColumnSelectionAllowed(false);

        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(Color.black);

        table.setCellSelectionEnabled(true);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);



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

            
            if (column == table.getColumnModel().getColumnIndex("Action")){
                this.setFont(new Font("Arial", Font.BOLD, 15));
                this.setForeground(new Color(2, 48, 32));
                
            }
            else {
                this.setFont(new Font("Arial", 0, 15));
                this.setForeground(Color.black);
            }
                

            if (column == table.getColumnModel().getColumnIndex("Reviews")){
                this.setForeground(Color.blue);
                this.setBorder(new EmptyBorder(0, 0, 0, 0));
            }
            else if (column == 9){
                this.setBorder(new EmptyBorder(0, 0, 0, 0));
            }
            else{
                int padding = (this.getWidth() - this.getFontMetrics(this.getFont()).stringWidth(this.getText())) / 2;
                this.setBorder(new EmptyBorder(0, padding, 0, padding/2));
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


    public static void main(String[] args) {
        new GeneralDatabase();
    }
}

    
