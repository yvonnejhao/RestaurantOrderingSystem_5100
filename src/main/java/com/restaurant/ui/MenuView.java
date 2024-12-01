package com.restaurant.ui;

import com.restaurant.models.MenuItem;
import com.restaurant.models.Order;
import com.restaurant.models.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MenuView extends JFrame {
    private Map<String, Double> menuItems; // Stores menu items and their prices
    private Map<String, Integer> cartItems; // Stores cart items and their quantities
    private DefaultTableModel cartTableModel; // Table model for displaying cart
    private JLabel totalLabel; // Label to display total, tax, and grand total
    private String tableNumber; // Table number for the current order
    private Restaurant restaurant; // Reference to the restaurant instance

    public MenuView(String tableNumber, Restaurant restaurant) {
        this.tableNumber = tableNumber;
        this.restaurant = restaurant;

        // Set up the main window
        setTitle("Menu - Table " + tableNumber);
        setSize(1000, 600); // Adjust window size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Initialize menu and cart
        menuItems = new HashMap<>();
        loadMenuItems(); // Load menu items from the restaurant instance
        cartItems = new HashMap<>();

        // Create menu panel
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints menuGbc = new GridBagConstraints();
        menuGbc.insets = new Insets(5, 5, 5, 5);
        menuGbc.gridx = 0;
        menuGbc.gridy = GridBagConstraints.RELATIVE;
        menuGbc.anchor = GridBagConstraints.WEST;
        menuGbc.fill = GridBagConstraints.HORIZONTAL;

        for (Map.Entry<String, Double> entry : menuItems.entrySet()) {
            String itemName = entry.getKey();
            double itemPrice = entry.getValue();
            menuPanel.add(createMenuItemPanel(itemName, itemPrice), menuGbc);
        }

        // Create cart panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBackground(new Color(173, 216, 230));

        cartTableModel = new DefaultTableModel(new Object[]{"Item", "Qty", "Price"}, 0);
        JTable cartTable = new JTable(cartTableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalLabel = new JLabel();
        updateTotal(); // Set the initial total values
        totalPanel.add(totalLabel, BorderLayout.CENTER);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.setBackground(new Color(255, 165, 0));
        placeOrderButton.setForeground(Color.WHITE);
        placeOrderButton.addActionListener(e -> placeOrder());
        totalPanel.add(placeOrderButton, BorderLayout.SOUTH);

        cartPanel.add(new JLabel("My Cart", SwingConstants.CENTER), BorderLayout.NORTH);
        cartPanel.add(scrollPane, BorderLayout.CENTER);
        cartPanel.add(totalPanel, BorderLayout.SOUTH);

        // Set a fixed width for the cart panel
        cartPanel.setPreferredSize(new Dimension(400, cartPanel.getPreferredSize().height));

        // Create "Back to Login" button
        JButton backToLoginButton = new JButton("Back to Login");
        backToLoginButton.addActionListener(e -> {
            this.dispose(); // Close the current window
            new LoginView(restaurant).setVisible(true); // Open the login page
        });

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backToLoginButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add "Back to Login" button panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        add(backPanel, gbc);

        // Add menu panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.6; // Menu takes 60% of the width
        gbc.weighty = 1.0;
        add(menuPanel, gbc);

        // Add cart panel
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.4; // Cart takes 40% of the width
        add(cartPanel, gbc);

        // Register as a listener to the restaurant
        restaurant.addMenuChangeListener(this::refreshMenu);
    }

    // Load menu items from the restaurant instance
    private void loadMenuItems() {
        menuItems.clear();
        for (MenuItem item : restaurant.getMenu()) {
            menuItems.put(item.getName(), item.getPrice());
        }
    }

    // Create a panel for a menu item
    private JPanel createMenuItemPanel(String itemName, double itemPrice) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 5);

        Font font = new Font("Arial", Font.PLAIN, 18);

        // Item name label
        JLabel nameLabel = new JLabel(itemName);
        nameLabel.setFont(font);
        nameLabel.setPreferredSize(new Dimension(150, nameLabel.getPreferredSize().height));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        // Amount label and buttons
        JLabel amountLabel = new JLabel("Amount: ");
        amountLabel.setFont(font);
        gbc.gridx = 1;
        panel.add(amountLabel, gbc);

        JButton subtractButton = new JButton("-");
        subtractButton.setFont(font);
        gbc.gridx = 2;
        panel.add(subtractButton, gbc);

        JTextField amountField = new JTextField("0", 2);
        amountField.setFont(font);
        amountField.setEditable(false);
        gbc.gridx = 3;
        panel.add(amountField, gbc);

        JButton addButton = new JButton("+");
        addButton.setFont(font);
        gbc.gridx = 4;
        panel.add(addButton, gbc);

        // Price label
        JLabel priceLabel = new JLabel(String.format("$ %.2f", itemPrice));
        priceLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(priceLabel, gbc);

        // Add to Cart button
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(font);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(addToCartButton, gbc);

        // Action listeners for buttons
        subtractButton.addActionListener(e -> {
            int amount = Integer.parseInt(amountField.getText());
            if (amount > 0) {
                amountField.setText(String.valueOf(amount - 1));
            }
        });

        addButton.addActionListener(e -> {
            int amount = Integer.parseInt(amountField.getText());
            amountField.setText(String.valueOf(amount + 1));
        });

        addToCartButton.addActionListener(e -> {
            int amount = Integer.parseInt(amountField.getText());
            if (amount > 0) {
                addToCart(itemName, amount, itemPrice);
                amountField.setText("0");
            }
        });

        return panel;
    }

    // Add items to the cart
    private void addToCart(String itemName, int quantity, double price) {
        int currentQuantity = cartItems.getOrDefault(itemName, 0);
        cartItems.put(itemName, currentQuantity + quantity);

        updateCartTable();
        updateTotal();
    }

    // Update the cart table
    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            double price = menuItems.get(itemName) * quantity;
            cartTableModel.addRow(new Object[]{itemName, quantity, String.format("$ %.2f", price)});
        }
    }

    // Update the total amount
    private void updateTotal() {
        double total = 0.0;
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            total += menuItems.get(entry.getKey()) * entry.getValue();
        }
        double tax = total * 0.10;
        double grandTotal = total + tax;

        totalLabel.setText(String.format(
            "<html>"
            + "<table>"
            + "<tr><td>Total:</td><td style='padding-left: 20px;'>$%.2f</td></tr>"
            + "<tr><td>Tax (10%%):</td><td style='padding-left: 20px;'>$%.2f</td></tr>"
            + "<tr><td>Grand Total:</td><td style='padding-left: 20px;'>$%.2f</td></tr>"
            + "</table>"
            + "</html>", total, tax, grandTotal));
    }

    // Place the order
    private void placeOrder() {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty. Please add items before placing an order.");
            return;
        }

        String orderNo = String.format("%03d", restaurant.getOrders().size() + 1);
        Order newOrder = new Order(orderNo, tableNumber, "Confirmed");
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                newOrder.addItem(new MenuItem(entry.getKey(), menuItems.get(entry.getKey())));
            }
        }

        restaurant.addOrder(newOrder);
        JOptionPane.showMessageDialog(this, "Order placed successfully! Order No: " + orderNo);
        cartItems.clear();
        updateCartTable();
        updateTotal();
    }

    // Refresh menu when items change
    private void refreshMenu() {
        SwingUtilities.invokeLater(() -> {
            loadMenuItems();
            getContentPane().removeAll();
            revalidate();
            repaint();
        });
    }
}
