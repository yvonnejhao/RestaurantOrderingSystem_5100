package main.java.com.restaurant.ui;

import main.java.com.restaurant.models.Restaurant;

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
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(173, 216, 230));

        JLabel welcomeLabel = new JLabel("Welcome to Restaurant!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 顧客區域
        JPanel customerPanel = new JPanel(new FlowLayout());
        JButton customerButton = new JButton("Customer");
        customerButton.setBackground(new Color(30, 144, 255));
        customerButton.setForeground(Color.WHITE);
        customerButton.addActionListener(e -> {
            isCustomerSelected = true;
            tableNumberField.setEditable(true);
            adminIdField.setEditable(false);
            adminIdField.setText("");
        });
        tableNumberField = new JTextField(10);
        tableNumberField.setEditable(false);

        customerPanel.add(customerButton);
        customerPanel.add(new JLabel("Please enter your table number:"));
        customerPanel.add(tableNumberField);

        // Admin 區域
        JPanel adminPanel = new JPanel(new FlowLayout());
        JButton adminButton = new JButton("Admin");
        adminButton.setBackground(new Color(30, 144, 255));
        adminButton.setForeground(Color.WHITE);
        adminButton.addActionListener(e -> {
            isCustomerSelected = false;
            adminIdField.setEditable(true);
            tableNumberField.setEditable(false);
            tableNumberField.setText("");
        });
        adminIdField = new JTextField(10);
        adminIdField.setEditable(false);

        adminPanel.add(adminButton);
        adminPanel.add(new JLabel("Please enter your ID number:"));
        adminPanel.add(adminIdField);

        // Login 按鈕
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(new LoginAction());

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(customerPanel);
        mainPanel.add(adminPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(loginButton);

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
                new MenuView(tableNumber).setVisible(true);
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
            Restaurant restaurant = new Restaurant(); // 初始化餐廳
            new LoginView(restaurant).setVisible(true);
        });
    }
}

