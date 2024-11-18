package com.restaurant.ui;


import com.restaurant.models.Order;
import com.restaurant.models.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrderManagementView extends JPanel {
    private Restaurant restaurant;
    private DefaultTableModel tableModel;

    public OrderManagementView(Restaurant restaurant) {
        this.restaurant = restaurant;
        setLayout(new BorderLayout());

        // 表格列名
        String[] columns = {"Order No.", "Table No.", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable orderTable = new JTable(tableModel);
        orderTable.setRowHeight(30);

        // 載入訂單到表格
        loadOrders();

        // 添加滾動面板
        JScrollPane scrollPane = new JScrollPane(orderTable);
        add(scrollPane, BorderLayout.CENTER);

        // 添加關閉按鈕
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadOrders() {
        List<Order> orders = restaurant.getOrders(); // 假設 Restaurant 類中有 getOrders() 方法
        for (Order order : orders) {
            tableModel.addRow(new Object[]{order.getOrderNo(), order.getTableNo(), order.getStatus()});
        }
    }
}
