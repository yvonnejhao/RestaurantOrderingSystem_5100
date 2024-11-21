package com.restaurant;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.restaurant.models.Restaurant;
import com.restaurant.ui.LoginView;

public class Main {
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
