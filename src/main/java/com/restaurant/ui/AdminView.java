package com.restaurant.ui;

import com.restaurant.models.Restaurant;

import javax.swing.*;
import java.awt.*;

public class AdminView extends JFrame {
    private Restaurant restaurant;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public AdminView(Restaurant restaurant) {
        this.restaurant = restaurant;

        setTitle("Admin View");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize CardLayout and content panel
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Add views to CardLayout
        contentPanel.add(new OrderManagementView(restaurant), "OrderManagement");
        contentPanel.add(new MenuManagementView(restaurant), "MenuManagement");

        // Create Navigation Buttons
        JPanel navigationPanel = new JPanel();

        JButton orderManagementButton = new JButton("Order Management");
        orderManagementButton.addActionListener(e -> cardLayout.show(contentPanel, "OrderManagement"));

        JButton menuManagementButton = new JButton("Menu Management");
        menuManagementButton.addActionListener(e -> cardLayout.show(contentPanel, "MenuManagement"));

        navigationPanel.add(orderManagementButton);
        navigationPanel.add(menuManagementButton);

        // Add navigation and content panels
        add(navigationPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // Show initial view
        cardLayout.show(contentPanel, "OrderManagement");
    }
}
