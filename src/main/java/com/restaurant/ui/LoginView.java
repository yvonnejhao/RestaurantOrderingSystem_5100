package com.restaurant.ui;

import com.restaurant.models.Restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class LoginView extends JFrame {
    private JTextField tableNumberField;
    private JTextField adminIdField;
    private JButton loginButton;
    private boolean isCustomerSelected;
    private Restaurant restaurant;

    public LoginView(Restaurant restaurant) {
        this.restaurant = restaurant;

        setTitle("Welcome to Restaurant!");
        setSize(800, 600); // Increased window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Restaurant!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28)); // Larger font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(welcomeLabel, gbc);

        // Customer area
        JPanel customerPanel = new JPanel(new GridBagLayout());
        TitledBorder customerBorder = BorderFactory.createTitledBorder("Customer");
        customerBorder.setTitleFont(new Font("Arial", Font.PLAIN, 18)); // Set the desired font and size
        customerPanel.setBorder(BorderFactory.createCompoundBorder(customerBorder, new EmptyBorder(10, 10, 10, 10))); // Set the border size
        customerPanel.setPreferredSize(new Dimension(400, 150)); // Set a fixed preferred size
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(customerPanel, gbc);

        JLabel tableNumberLabel = new JLabel("Please enter your table number:");
        tableNumberLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Larger font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        customerPanel.add(tableNumberLabel, gbc);

        tableNumberField = new JTextField(10);
        tableNumberField.setFont(new Font("Arial", Font.PLAIN, 18)); // Larger font size
        tableNumberField.setEditable(true); // Ensure the JTextField is always editable
        tableNumberField.setToolTipText("Format: A06 (A letter followed by two digits)");
        gbc.gridx = 0;
        gbc.gridy = 1;
        customerPanel.add(tableNumberField, gbc);

        // Admin area
        JPanel adminPanel = new JPanel(new GridBagLayout());
        TitledBorder adminBorder = BorderFactory.createTitledBorder("Admin");
        adminBorder.setTitleFont(new Font("Arial", Font.PLAIN, 18)); // Set the desired font and size
        adminPanel.setBorder(BorderFactory.createCompoundBorder(adminBorder, new EmptyBorder(10, 10, 10, 10))); // Set the border size
        adminPanel.setPreferredSize(new Dimension(400, 150)); // Set a fixed preferred size
        gbc.gridx = 0; // Ensure the same column as customerPanel
        gbc.gridy = 2; // Place it below the customerPanel
        gbc.gridwidth = 1;
        mainPanel.add(adminPanel, gbc);

        JLabel adminIdLabel = new JLabel("Please enter your admin ID:");
        adminIdLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Larger font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        adminPanel.add(adminIdLabel, gbc);

        adminIdField = new JTextField(10);
        adminIdField.setFont(new Font("Arial", Font.PLAIN, 18)); // Larger font size
        adminIdField.setEditable(true); // Ensure the JTextField is always editable
        gbc.gridx = 0;
        gbc.gridy = 1;
        adminPanel.add(adminIdField, gbc);

        // Login button
        loginButton = new JButton("Login");
        customizeButton(loginButton);
        loginButton.addActionListener(new LoginAction());
        gbc.gridx = 0;
        gbc.gridy = 3; // Place it below the adminPanel
        gbc.gridwidth = 2;
        mainPanel.add(loginButton, gbc);

        // Add ActionListener to text fields to handle Enter key press
        tableNumberField.addActionListener(new LoginAction());
        adminIdField.addActionListener(new LoginAction());

        add(mainPanel, BorderLayout.CENTER);
    }

    private void customizeButton(JButton button) {
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20)); // Larger font size
        button.setMargin(new Insets(10, 20, 10, 20)); // Add padding
    }

    private class LoginAction implements ActionListener {
        private final Pattern tablePattern = Pattern.compile("^[A-Z]\\d{2}$");
        private final Pattern adminIdPattern = Pattern.compile("^\\d{6}$");

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == tableNumberField) {
                isCustomerSelected = true;
            } else if (e.getSource() == adminIdField) {
                isCustomerSelected = false;
            }

            if (isCustomerSelected) {
                String tableNumber = tableNumberField.getText();
                if (!tablePattern.matcher(tableNumber).matches()) {
                    JOptionPane.showMessageDialog(LoginView.this, "Invalid table number. Format should be a letter followed by two digits (e.g., A06).", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println("Customer login successful. Table: " + tableNumber);
                new MenuView(tableNumber, restaurant).setVisible(true);
                dispose();
            } else {
                String adminId = adminIdField.getText();
                if (!adminIdPattern.matcher(adminId).matches()) {
                    JOptionPane.showMessageDialog(LoginView.this, "Invalid Admin ID. Format should be six digits (e.g., 123456).", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println("Admin login successful. ID: " + adminId);
                new AdminView(restaurant).setVisible(true);
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        // Set the cross-platform look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Restaurant restaurant = new Restaurant(); // Initialize restaurant
            new LoginView(restaurant).setVisible(true);
        });
    }
}
