package com.restaurant.ui;

import com.restaurant.models.Restaurant;

import javax.swing.*;
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
        setSize(600, 500); // Increased window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("Welcome to Restaurant!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Larger font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(welcomeLabel, gbc);

        // Customer area
        JPanel customerPanel = new JPanel(new GridBagLayout());
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(customerPanel, gbc);

        JButton customerButton = new JButton("Customer");
        customerButton.setBackground(new Color(30, 144, 255));
        customerButton.setForeground(Color.WHITE);
        customerButton.setFont(new Font("Arial", Font.BOLD, 16));
        customerButton.setMargin(new Insets(10, 20, 10, 20)); // Add padding
        customerButton.addActionListener(e -> {
            isCustomerSelected = true;
            tableNumberField.setEditable(true);
            adminIdField.setEditable(false);
            adminIdField.setText("");
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        customerPanel.add(customerButton, gbc);

        JLabel tableNumberLabel = new JLabel("Please enter your table number:");
        tableNumberLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        customerPanel.add(tableNumberLabel, gbc);

        tableNumberField = new JTextField(10);
        tableNumberField.setEditable(false);
        tableNumberField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        customerPanel.add(tableNumberField, gbc);

        // Admin area
        JPanel adminPanel = new JPanel(new GridBagLayout());
        adminPanel.setBorder(BorderFactory.createTitledBorder("Admin"));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(adminPanel, gbc);

        JButton adminButton = new JButton("Admin");
        adminButton.setBackground(new Color(30, 144, 255));
        adminButton.setForeground(Color.WHITE);
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.setMargin(new Insets(10, 20, 10, 20)); // Add padding
        adminButton.addActionListener(e -> {
            isCustomerSelected = false;
            adminIdField.setEditable(true);
            tableNumberField.setEditable(false);
            tableNumberField.setText("");
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        adminPanel.add(adminButton, gbc);

        JLabel adminIdLabel = new JLabel("Please enter your ID number:");
        adminIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        adminPanel.add(adminIdLabel, gbc);

        adminIdField = new JTextField(10);
        adminIdField.setEditable(false);
        adminIdField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        adminPanel.add(adminIdField, gbc);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size and style
        loginButton.setMargin(new Insets(10, 20, 10, 20)); // Add padding
        loginButton.addActionListener(new LoginAction());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(loginButton, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private class LoginAction implements ActionListener {
        private final Pattern tablePattern = Pattern.compile("^[A-Z]\\d{2}$");
        private final Pattern adminIdPattern = Pattern.compile("^\\d{6}$");

        @Override
        public void actionPerformed(ActionEvent e) {
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
        SwingUtilities.invokeLater(() -> {
            Restaurant restaurant = new Restaurant(); // Initialize restaurant
            new LoginView(restaurant).setVisible(true);
        });
    }
}
