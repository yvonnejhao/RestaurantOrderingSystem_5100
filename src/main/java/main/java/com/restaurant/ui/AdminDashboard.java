package main.java.com.restaurant.ui;

import com.restaurant.models.Restaurant;
import com.restaurant.ui.AdminView;
import com.restaurant.ui.MenuManagementView;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard(Restaurant restaurant) {
        setTitle("Admin Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // 創建並添加 "Order View" 按鈕
        JButton orderViewButton = new JButton("Order View");
        orderViewButton.addActionListener(e -> new AdminView(restaurant).setVisible(true));
        add(orderViewButton);

        // 創建並添加 "Menu Management" 按鈕
        JButton menuManagementButton = new JButton("Menu Management");
        menuManagementButton.addActionListener(e -> new MenuManagementView(restaurant).setVisible(true));
        add(menuManagementButton);
    }
}

