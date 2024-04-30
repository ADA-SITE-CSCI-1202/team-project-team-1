package Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Models.User;

public class UserView {
    private User user;

    JFrame frame = new JFrame();
    
    JPanel containerPanel = new JPanel(new BorderLayout());
    JPanel panel = new JPanel(new BorderLayout());
    JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    
    ImageIcon libraryIcon = new ImageIcon("Pages/assets/online-library.png");
    ImageIcon logOutIcon = new ImageIcon("Pages/assets/logout.png");

    JLabel projectName = new JLabel("Library Management");
    
    JLabel title = new JLabel();
    
    JButton generalDB = new JButton("General \nDB");
    JButton personalDB = new JButton("Personal \nDB");
    JButton logOut = new JButton("Log Out");

    Border compoundBorder = BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(new Color(0xdb6d13), 4, true),
                                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                                        );

    public UserView(User user) {
        this.user = user;
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel.setBackground(new Color(0xdb6d13));
        panel.setPreferredSize(new Dimension(550, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        
        libraryIcon = new ImageIcon(libraryIcon.getImage().getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH));
        logOutIcon = new ImageIcon(logOutIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        projectName.setIcon(libraryIcon);
        projectName.setFont(new Font(null, Font.BOLD, 38));
        projectName.setForeground(Color.WHITE);
        
        title.setText("Hi, " + user.getUsername());
        title.setFont(new Font(null, Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        panel.add(projectName, BorderLayout.NORTH);
        panel.add(title, BorderLayout.CENTER);
        
        logOut.setIcon(logOutIcon);
        
        buttonsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xdb6d13), 1),
            BorderFactory.createEmptyBorder(50, 5, 5, 5)
            ));

        generalDB.setPreferredSize(new Dimension(200, 150));
        generalDB.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 6, true));
        generalDB.setFocusPainted(false);
        generalDB.setMargin(new Insets(0, 0, 0, 0));
        generalDB.setBackground(new Color(0xe6f0f4));
        generalDB.setFont(new Font(null, Font.BOLD, 24));
        generalDB.setForeground(new Color(0xdb6d13));
        
        personalDB.setPreferredSize(new Dimension(200, 150));
        personalDB.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 6, true));
        personalDB.setFocusPainted(false);
        personalDB.setMargin(new Insets(0, 0, 0, 0));
        personalDB.setBackground(new Color(0xe6f0f4));
        personalDB.setFont(new Font(null, Font.BOLD, 24));
        personalDB.setForeground(new Color(0xdb6d13));

        logOut.setPreferredSize(new Dimension(250, 150));
        logOut.setBorder(compoundBorder);
        logOut.setFocusPainted(false);
        logOut.setMargin(new Insets(0, 50, 0, 50));
        logOut.setBackground(Color.WHITE);
        logOut.setFont(new Font(null, Font.BOLD, 24));
            
        topPanel.add(generalDB);
        topPanel.add(personalDB);
        bottomPanel.add(logOut);
        
        buttonsPanel.add(topPanel);
        buttonsPanel.add(bottomPanel);
        
        containerPanel.add(panel, BorderLayout.NORTH);
        containerPanel.add(buttonsPanel, BorderLayout.CENTER);

        frame.setSize(550, 750);
        frame.setLayout(new BorderLayout());
        frame.add(containerPanel);
        
        frame.setVisible(true);
    }
}
