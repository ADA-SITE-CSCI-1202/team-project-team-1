
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package src.main;

import PojoClass.generalDB;
import PojoClass.personalDB;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Leyla
 */
public class Main extends javax.swing.JFrame {

    String[] ratings = {"4.3 (3)","3.8 (6)","3.2 (5)","4.5 (8)","No rating","3.3 (5)","2.4 (3)","2.9 (8)","3.0 (7)"};
    String[] users = {"user23","rahim","tuncay","user83","No review","sagol","rashid","cavid","hesen"};
    TreeMap<String, String> generalDB = new TreeMap<>();
    public static ArrayList<String> dataForWindow = new ArrayList<>();
    public static String ratingForWindow;
    public static String reviewForWindow;

    public static int row;
    public static int column;

    ArrayList<generalDB> generalList = new ArrayList<>();
    DefaultTableModel modelGeneral;
    static DefaultTableModel modelPersonal;
    static TableCellRenderer centerAlight = new Texttablecenter();

    public Main() {
	initComponents();
	create_GeneralDB();
	modelGeneral = (DefaultTableModel) general_table.getModel();
	modelPersonal = (DefaultTableModel) personal_table.getModel();
	addTableGeneral();
	setGeneralTable();
	setPersonalTable();
	addTablePersonal();

	Font font = new Font("Helvetica", Font.BOLD, 15);

        UIManager.put("OptionPane.messageFont", font);
    }

    private void setGeneralTable(){
	general_table.setRowHeight(general_table.getRowHeight() + 12);
	JTableHeader header = general_table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(general_table));
	header.setForeground(Color.BLUE);
	header.setFont(new Font("Tahome", Font.BOLD, 14));
	general_table.getColumnModel().getColumn(0).setPreferredWidth(160);
	general_table.getColumnModel().getColumn(1).setPreferredWidth(160);
    }

    private void setPersonalTable(){
	personal_table.setRowHeight(general_table.getRowHeight() + 12);
	JTableHeader header = personal_table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(personal_table));
	header.setForeground(Color.BLUE);
	header.setFont(new Font("Tahome", Font.BOLD, 14));
	personal_table.getColumnModel().getColumn(2).setPreferredWidth(40);
	personal_table.getColumnModel().getColumn(5).setPreferredWidth(40);
	personal_table.getColumnModel().getColumn(0).setPreferredWidth(170);
	personal_table.getColumnModel().getColumn(1).setPreferredWidth(130);
    }

    public static class HeaderRenderer implements TableCellRenderer {

    DefaultTableCellRenderer renderer;

    public HeaderRenderer(JTable table) {
        renderer = (DefaultTableCellRenderer)
            table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int col) {
        return renderer.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, col);
    }
}

    public static class Texttablecenter extends DefaultTableCellRenderer {
     
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected,boolean hasFocus,int row,int column){
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }
    
    
    
}

    private void create_GeneralDB(){
	String initialDBPath = "C:\\Task\\brodsky.csv";
	ArrayList<String> list = new ArrayList<>();
	try {
            String[][] data = readCSV(initialDBPath, " ");
	   // System.out.println(data[73][1]);
	    int i = 0;
            for (String[] row : data) {
		i++;
		if(i > 72 && i < 78){
		String rowValue = "";
                for (String value : row) {
		    rowValue+= value + " ";
		    //System.out.print(value + " ");
		    //System.out.println();
			}
		list.add(rowValue);	
                }
                //System.out.println(); // New line for each row
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	

	
	for(String str : list){
	    String[] words = str.split(",");
	    String author = words[words.length-1];
	    //System.out.println(author);
	    for(int i = 0; i < words.length - 1 ; i++){
		String output = words[i].replace("\"", "");
		  generalList.add(new generalDB(output,author));
		    }
	    }
	
    }

    private void addTableGeneral(){
	modelGeneral.setRowCount(0);
	for(int i = 0 ; i < generalList.size(); i++){
	generalDB g = generalList.get(i);
	    //System.out.println(g.getTitle() + "  " + g.getAuthor());
	Object[] addrow = {g.getTitle().trim(),g.getAuthor(),ratings[i],users[i]};
	modelGeneral.addRow(addrow);
	    }
	general_table.getColumnModel().getColumn(0).setCellRenderer(centerAlight);
        general_table.getColumnModel().getColumn(1).setCellRenderer(centerAlight);
        general_table.getColumnModel().getColumn(2).setCellRenderer(centerAlight);
        general_table.getColumnModel().getColumn(3).setCellRenderer(centerAlight);
	}

    public static void addTablePersonal(){
	modelPersonal.setRowCount(0);
	ArrayList<personalDB> data = getPersoanlData();
	for(personalDB person : data){
	    Object[] addrow = {person.getTitle(),person.getAuthor(),person.getRating(),person.getReviews(),person.getStautus(),person.getTime_spent(),
				person.getStart_date(),person.getEnd_date(),person.getUser_rating(),person.getUser_review()};
		modelPersonal.addRow(addrow);
	    }
	personal_table.getColumnModel().getColumn(0).setCellRenderer(centerAlight);
        personal_table.getColumnModel().getColumn(1).setCellRenderer(centerAlight);
        personal_table.getColumnModel().getColumn(2).setCellRenderer(centerAlight);
        personal_table.getColumnModel().getColumn(3).setCellRenderer(centerAlight);
        personal_table.getColumnModel().getColumn(4).setCellRenderer(centerAlight);
        personal_table.getColumnModel().getColumn(5).setCellRenderer(centerAlight);
        personal_table.getColumnModel().getColumn(6).setCellRenderer(centerAlight);
        personal_table.getColumnModel().getColumn(7).setCellRenderer(centerAlight);
        personal_table.getColumnModel().getColumn(8).setCellRenderer(centerAlight);
        personal_table.getColumnModel().getColumn(9).setCellRenderer(centerAlight);
    }

    public static double generateRandomDouble(double min, double max) {
        // Create a new Random object
        Random random = new Random();

        // Generate a random double value between 0.0 (inclusive) and 1.0 (exclusive)
        double randomDouble = random.nextDouble();

        // Scale the random double to fit within the specified range
        double scaledRandomDouble = min + (randomDouble * (max - min));

        return scaledRandomDouble;
    }

    private static String[][] readCSV(String filePath, String delimiter) throws IOException {
        ArrayList<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(delimiter);
                rows.add(data);
            }
        }

        return rows.toArray(new String[0][]);
    }

    public static ArrayList<personalDB> getPersoanlData(){
	String path = "C:\\Task\\personalDB.xlsx";
	ArrayList<personalDB> result = new ArrayList<>();
	File file = new File(path);
	try {
	    FileInputStream excelInputStream = new FileInputStream(file);
	    BufferedInputStream excelBuf = new BufferedInputStream(excelInputStream);
	    XSSFWorkbook book = new XSSFWorkbook(excelBuf);
	    XSSFSheet sheet = book.getSheetAt(0);

	    for(int i = 1; i <= sheet.getLastRowNum(); i++){
		    XSSFRow row = sheet.getRow(i);

		    String title = checkNull(row.getCell(0));
		    String author = checkNull(row.getCell(1));
		    String rating = checkNull(row.getCell(2));
		    String reviews = checkNull(row.getCell(3));
		    String status = checkNull(row.getCell(4));
		    String time_spent = checkNull(row.getCell(5));
		    String start_date = checkNull(row.getCell(6));
		    String end_date = checkNull(row.getCell(7));
		    String user_rating = checkNull(row.getCell(8));
		    String user_review = checkNull(row.getCell(9));

		    if(user_rating.trim().isEmpty()){
			user_rating = "Add rating";
			}
		    if(user_review.trim().isEmpty()){
			user_review = "Add review";
			}	

		    result.add(new personalDB(title, author, rating, reviews, status, time_spent, start_date, end_date, user_rating, user_review));
		}

		return result;
	    
	} catch (FileNotFoundException ex) {
		return null;
	    
	} catch (IOException ex) {
	    return null;
	}
    }

    private static String checkNull(XSSFCell str){
	String value = "";
	if(str == null){
	    return value;
	    }
	    return str.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        general_table = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        personal_table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/database.png"))); // NOI18N
        jButton1.setText("General Database");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/group.png"))); // NOI18N
        jButton2.setText("Personal Database");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(28, 28, 28)
                .addComponent(jButton2)
                .addContainerGap(498, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 957, -1));

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        general_table.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        general_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Author", "Ratings", "Reviews"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        general_table.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        general_table.setShowGrid(true);
        general_table.getTableHeader().setReorderingAllowed(false);
        general_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                general_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(general_table);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1028, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel2);

        personal_table.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        personal_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Author", "Rating", "Reviews", "Status", "Time Spent", "Start Date", "End Date", "User Rating", "User Review"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        personal_table.setShowGrid(true);
        personal_table.getTableHeader().setReorderingAllowed(false);
        personal_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                personal_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(personal_table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1351, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel3);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 71, 1380, 570));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void general_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_general_tableMouseClicked
	dataForWindow.clear();
	int rowAtPoint = general_table.rowAtPoint(evt.getPoint());
	int columnAtPoint = general_table.columnAtPoint(evt.getPoint());
	if(columnAtPoint == 3){
	    if(!general_table.getValueAt(rowAtPoint, columnAtPoint).toString().equals("No review")){
		dataForWindow.add(general_table.getValueAt(rowAtPoint, 0).toString());
		dataForWindow.add(general_table.getValueAt(rowAtPoint, 1).toString());
		dataForWindow.add(general_table.getValueAt(rowAtPoint, 2).toString().split("\\(")[0]);
		dataForWindow.add(general_table.getValueAt(rowAtPoint, columnAtPoint).toString());
		Reviews rev = new Reviews(this, true);
		rev.setVisible(true);
		}
		else{
		    JOptionPane.showMessageDialog(null, "There is no review about this book !", "WARNING", JOptionPane.WARNING_MESSAGE);
		}
	    //System.out.println(general_table.getValueAt(rowAtPoint, 2).toString().split("\\(")[0]);
	    }
    }//GEN-LAST:event_general_tableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void personal_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_personal_tableMouseClicked
        ratingForWindow = "";
	reviewForWindow = "";
	int rowAtPoint = personal_table.rowAtPoint(evt.getPoint());
	int columnAtPoint = personal_table.columnAtPoint(evt.getPoint());
	if(columnAtPoint == 8){
		row = rowAtPoint;
		column = columnAtPoint;
		ratingForWindow = personal_table.getValueAt(rowAtPoint, columnAtPoint).toString();
		addRating rat = new addRating(this, true);
		rat.setVisible(true);
		
	    }

	if(columnAtPoint == 9){
	    row = rowAtPoint;
	    column = columnAtPoint;
	    if(!personal_table.getValueAt(rowAtPoint, columnAtPoint).toString().equals("Add review")){
	    reviewForWindow = personal_table.getValueAt(rowAtPoint, columnAtPoint).toString();
	    }
	    addReview rev = new addReview(this, true);
	    rev.setVisible(true);
	    }    
    }//GEN-LAST:event_personal_tableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
	/* Set the Nimbus look and feel */
	//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
	/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
	 */
	try {
	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException ex) {
	    java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new Main().setVisible(true);
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable general_table;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTable personal_table;
    // End of variables declaration//GEN-END:variables
}