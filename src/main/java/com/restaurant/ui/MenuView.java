package com.restaurant.ui;

import com.restaurant.models.MenuItem;
import com.restaurant.models.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MenuView extends JFrame {
    private Map<String, Double> menuItems;
    private Map<String, Integer> cartItems;
    private DefaultTableModel cartTableModel;
    private JLabel totalLabel;
    private String tableNumber; // Store table number
    private Restaurant restaurant; // Reference to Restaurant

    public MenuView(String tableNumber, Restaurant restaurant) { // Accept Restaurant instance
        this.tableNumber = tableNumber;
        this.restaurant = restaurant;

        setTitle("Menu - Table " + tableNumber); // Show table number
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize menu and cart
        menuItems = new HashMap<>();
        loadMenuItems(); // Load menu from Restaurant
        cartItems = new HashMap<>();

        // Menu panel on the left
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1, 10, 10));
        menuPanel.setBackground(new Color(173, 216, 230));
        for (Map.Entry<String, Double> entry : menuItems.entrySet()) {
            String itemName = entry.getKey();
            double itemPrice = entry.getValue();
            menuPanel.add(createMenuItemPanel(itemName, itemPrice));
        }

        // Cart panel on the right
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBackground(new Color(173, 216, 230));

        cartTableModel = new DefaultTableModel(new Object[]{"Item", "Qty", "Price"}, 0);
        JTable cartTable = new JTable(cartTableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalLabel = new JLabel("Total: $0.00", SwingConstants.RIGHT);
        totalPanel.add(totalLabel, BorderLayout.CENTER);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.setBackground(new Color(255, 165, 0));
        placeOrderButton.setForeground(Color.WHITE);
        placeOrderButton.addActionListener(e -> placeOrder());

        totalPanel.add(placeOrderButton, BorderLayout.SOUTH);

        cartPanel.add(new JLabel("My Cart", SwingConstants.CENTER), BorderLayout.NORTH);
        cartPanel.add(scrollPane, BorderLayout.CENTER);
        cartPanel.add(totalPanel, BorderLayout.SOUTH);

        add(menuPanel, BorderLayout.WEST);
        add(cartPanel, BorderLayout.CENTER);

        // Register as a listener to Restaurant
        restaurant.addMenuChangeListener(this::refreshMenu);
    }

    private void loadMenuItems() {
        menuItems.clear();
        for (MenuItem item : restaurant.getMenu()) {
            menuItems.put(item.getName(), item.getPrice());
        }
    }

    private JPanel createMenuItemPanel(String itemName, double itemPrice) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(173, 216, 230));

        JLabel nameLabel = new JLabel(itemName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel priceLabel = new JLabel(String.format("$ %.2f", itemPrice));

        JLabel amountLabel = new JLabel("amount - ");
        JTextField amountField = new JTextField("0", 2);
        amountField.setEditable(false);

        JButton addButton = new JButton("+");
        addButton.addActionListener(e -> {
            int amount = Integer.parseInt(amountField.getText());
            amountField.setText(String.valueOf(amount + 1));
        });

        JButton subtractButton = new JButton("-");
        subtractButton.addActionListener(e -> {
            int amount = Integer.parseInt(amountField.getText());
            if (amount > 0) {
                amountField.setText(String.valueOf(amount - 1));
            }
        });

        JButton addToCartButton = new JButton("Add to cart");
        addToCartButton.setBackground(new Color(30, 144, 255));
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.addActionListener(e -> {
            int amount = Integer.parseInt(amountField.getText());
            if (amount > 0) {
                addToCart(itemName, amount, itemPrice);
                amountField.setText("0");
            }
        });

        panel.add(nameLabel);
        panel.add(priceLabel);
        panel.add(amountLabel);
        panel.add(subtractButton);
        panel.add(amountField);
        panel.add(addButton);
        panel.add(addToCartButton);

        return panel;
    }

    private void addToCart(String itemName, int quantity, double price) {
        int currentQuantity = cartItems.getOrDefault(itemName, 0);
        cartItems.put(itemName, currentQuantity + quantity);

        updateCartTable();
        updateTotal();
    }

    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            double price = menuItems.get(itemName) * quantity;
            cartTableModel.addRow(new Object[]{itemName, quantity, String.format("$ %.2f", price)});
        }
    }

    private void updateTotal() {
        double total = 0.0;
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            total += menuItems.get(itemName) * quantity;
        }
        double tax = total * 0.10;
        totalLabel.setText(String.format("Total: $%.2f   Tax (10%%): $%.2f   Grand Total: $%.2f", total, tax, total + tax));
    }

    private void placeOrder() {
        JOptionPane.showMessageDialog(this, "Order placed! Total: " + totalLabel.getText() + " Table: " + tableNumber);
        cartItems.clear();
        updateCartTable();
        updateTotal();
    }

    private void refreshMenu() {
        SwingUtilities.invokeLater(() -> {
            loadMenuItems();
            getContentPane().removeAll();
            repaint();
            revalidate();
        });
    }
}
