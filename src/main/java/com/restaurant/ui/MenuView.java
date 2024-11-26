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
    private Map<String, Double> menuItems;
    private Map<String, Integer> cartItems;
    private DefaultTableModel cartTableModel;
    private JLabel totalLabel;
    private String tableNumber;
    private Restaurant restaurant;

    public MenuView(String tableNumber, Restaurant restaurant) {
        this.tableNumber = tableNumber;
        this.restaurant = restaurant;

        setTitle("Menu - Table " + tableNumber);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Initialize menu and cart
        menuItems = new HashMap<>();
        loadMenuItems();
        cartItems = new HashMap<>();

        // Menu panel on the left
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints menuGbc = new GridBagConstraints();
        menuGbc.insets = new Insets(5, 5, 5, 5);
        menuGbc.gridx = 0;
        menuGbc.gridy = GridBagConstraints.RELATIVE;
        menuGbc.anchor = GridBagConstraints.WEST;
        menuGbc.fill = GridBagConstraints.HORIZONTAL;
        menuGbc.weightx = 1.0;

        for (Map.Entry<String, Double> entry : menuItems.entrySet()) {
            String itemName = entry.getKey();
            double itemPrice = entry.getValue();
            menuPanel.add(createMenuItemPanel(itemName, itemPrice), menuGbc);
        }

        // Cart panel on the right
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBackground(new Color(173, 216, 230));

        cartTableModel = new DefaultTableModel(new Object[]{"Item", "Qty", "Price"}, 0);
        JTable cartTable = new JTable(cartTableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalLabel = new JLabel();
        updateTotal(); // Set the initial values
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
        cartPanel.setPreferredSize(new Dimension(300, cartPanel.getPreferredSize().height)); // Set the width to 300 pixels

        // "Back to Login" button
        JButton backToLoginButton = new JButton("Back to Login");
        backToLoginButton.addActionListener(e -> {
            this.dispose(); // Close the current frame
            new LoginView(restaurant).setVisible(true); // Open the login page
        });

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backToLoginButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add back panel
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
        gbc.weightx = 0.6; // 60% of the width
        gbc.weighty = 1.0;
        add(menuPanel, gbc);

        // Add cart panel
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0; // No resizing
        add(cartPanel, gbc);

        // Register as a listener to the restaurant
        restaurant.addMenuChangeListener(this::refreshMenu);
    }

    private void loadMenuItems() {
        menuItems.clear();
        for (MenuItem item : restaurant.getMenu()) {
            menuItems.put(item.getName(), item.getPrice());
        }
    }

    private JPanel createMenuItemPanel(String itemName, double itemPrice) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 5); // Add some padding with a consistent gap from the left side

        Font font = new Font("Arial", Font.PLAIN, 18); // Define a common font

        // Item name with fixed width
        JLabel nameLabel = new JLabel(itemName);
        nameLabel.setFont(font);
        nameLabel.setPreferredSize(new Dimension(150, nameLabel.getPreferredSize().height)); // Set fixed width
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Align to the left
        panel.add(nameLabel, gbc);

        // Amount label
        JLabel amountLabel = new JLabel("Amount: ");
        amountLabel.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Align to the left
        panel.add(amountLabel, gbc);

        // Subtract button
        JButton subtractButton = new JButton("-");
        subtractButton.setFont(font);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Align to the center
        panel.add(subtractButton, gbc);

        // Amount field
        JTextField amountField = new JTextField("0", 2);
        amountField.setFont(font);
        amountField.setEditable(false);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Align to the center
        panel.add(amountField, gbc);

        // Add button
        JButton addButton = new JButton("+");
        addButton.setFont(font);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Align to the center
        panel.add(addButton, gbc);

        // Price label
        JLabel priceLabel = new JLabel(String.format("$ %.2f", itemPrice));
        priceLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Span two columns
        gbc.anchor = GridBagConstraints.WEST; // Align to the left
        panel.add(priceLabel, gbc);

        // Add to Cart button
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(font);
        addToCartButton.setBackground(new Color(30, 144, 255));
        addToCartButton.setForeground(Color.WHITE);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Span three columns
        gbc.anchor = GridBagConstraints.CENTER; // Align to the center
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
        double grandTotal = total + tax;

        totalLabel.setText(String.format(
            "<html>"
            + "<table>"
            + "<tr><td>Total:</td><td style='padding-left: 20px;'>$%.2f</td></tr>"
            + "<tr><td>Tax (10%%):</td><td style='padding-left: 20px;'>$%.2f</td></tr>"
            + "<tr><td>Grand Total:</td><td style='padding-left: 20px;'>$%.2f</td></tr>"
            + "</table>"
            + "</html>", 
            total, tax, grandTotal
        ));
    }

    private void placeOrder() {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty. Please add items before placing an order.");
            return;
        }

        // Generate order number
        String orderNo = String.format("%03d", restaurant.getOrders().size() + 1);

        // Create a new order and add items
        Order newOrder = new Order(orderNo, tableNumber, "Confirmed");
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            for (int i = 0; i < quantity; i++) {
                newOrder.addItem(new MenuItem(itemName, menuItems.get(itemName)));
            }
        }

        // Add order to the restaurant
        restaurant.addOrder(newOrder);

        JOptionPane.showMessageDialog(this, "Order placed successfully! Order No: " + orderNo);
        cartItems.clear();
        updateCartTable();
        updateTotal();
    }

    private void refreshMenu() {
        SwingUtilities.invokeLater(() -> {
            loadMenuItems();
            getContentPane().removeAll();
            revalidate();
            repaint();
        });
    }
}
