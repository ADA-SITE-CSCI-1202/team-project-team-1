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
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import Pages.loginSystem.LoginMain;

public class AdminView {
    JFrame frame = new JFrame();
    
    JPanel containerPanel = new JPanel(new BorderLayout());
    JPanel panel = new JPanel(new BorderLayout());
    JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    
    ImageIcon libraryIcon = new ImageIcon("Pages/assets/online-library.png");
    ImageIcon logOutIcon = new ImageIcon("Pages/assets/logout.png");
    ImageIcon dbIcon = new ImageIcon("Pages/assets/database-storage.png");

    JLabel projectName = new JLabel("Library Management");
    
    JLabel title = new JLabel("Admin");
    
    JButton generalDB = new JButton("General DB Management");
    JButton logOut = new JButton("Log Out");

    JButton addButton = new JButton("Add");
    JButton removeButton = new JButton("Remove");
    JButton editButton = new JButton("Edit");

    Border compoundBorder = BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(new Color(0xdb6d13), 4, true),
                                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                                        );

    public AdminView() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel.setBackground(new Color(0xdb6d13));
        panel.setPreferredSize(new Dimension(550, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        
        libraryIcon = new ImageIcon(libraryIcon.getImage().getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH));
        logOutIcon = new ImageIcon(logOutIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
        dbIcon = new ImageIcon(dbIcon.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));

        projectName.setIcon(libraryIcon);
        projectName.setFont(new Font(null, Font.BOLD, 38));
        projectName.setForeground(Color.WHITE);
        projectName.setIconTextGap(20);

        title.setFont(new Font(null, Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        panel.add(projectName, BorderLayout.NORTH);
        panel.add(title, BorderLayout.CENTER);
        
        logOut.setIcon(logOutIcon);
        generalDB.setIcon(dbIcon);

        logOut.setHorizontalTextPosition(SwingConstants.LEADING);
        projectName.setHorizontalTextPosition(SwingConstants.LEFT);
        
        buttonsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xdb6d13), 1),
            BorderFactory.createEmptyBorder(50, 5, 5, 5)
            ));

        generalDB.setPreferredSize(new Dimension(400, 150));
        generalDB.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 6, true));
        generalDB.setFocusPainted(false);
        generalDB.setMargin(new Insets(0, 0, 0, 0));
        generalDB.setBackground(new Color(0xe6f0f4));
        generalDB.setFont(new Font(null, Font.BOLD, 24));
        generalDB.setForeground(new Color(0xdb6d13));
        generalDB.setIconTextGap(20);

        generalDB.addActionListener(e -> {
            try {
                frame.dispose();
                new GeneralDatabase("admin");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        logOut.setPreferredSize(new Dimension(250, 150));
        logOut.setBorder(compoundBorder);
        logOut.setFocusPainted(false);
        logOut.setMargin(new Insets(0, 50, 0, 50));
        logOut.setBackground(Color.WHITE);
        logOut.setFont(new Font(null, Font.BOLD, 24));
        logOut.setIconTextGap(20);

        logOut.addActionListener(e -> {
            try {
                frame.dispose();
                new LoginMain();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
            
        topPanel.add(generalDB);
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
