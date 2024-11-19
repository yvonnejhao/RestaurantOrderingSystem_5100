package com.restaurant;

import javax.swing.SwingUtilities;

import com.restaurant.models.Restaurant;
import com.restaurant.ui.LoginView;

public class Main {
    public static void main(String[] args) {
        // Initialize the restaurant instance
        Restaurant restaurant = new Restaurant();

        // Use SwingUtilities to launch LoginView
        SwingUtilities.invokeLater(() -> {
            new LoginView(restaurant).setVisible(true);
        });
    }
}
