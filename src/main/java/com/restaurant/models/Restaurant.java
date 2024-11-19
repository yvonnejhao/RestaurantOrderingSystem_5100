package com.restaurant.models;

import com.restaurant.utils.CSVUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private List<MenuItem> menu;
    private List<Order> orders;

    private static final String MENU_CSV = "menu.csv";
    private static final String ORDERS_CSV = "orders.csv";

    public Restaurant() {
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();
        loadMenuFromCSV();
        loadOrdersFromCSV();
    }

    // 加載菜單項目
    private void loadMenuFromCSV() {
        List<String[]> data = CSVUtils.readCSV(MENU_CSV);
        boolean isFirstLine = true; // 用來標記是否為第一行
        for (String[] row : data) {
            if (isFirstLine) {
                isFirstLine = false; // 跳過第一行
                continue;
            }
            if (row.length == 2) {
                try {
                    menu.add(new MenuItem(row[0], Double.parseDouble(row[1])));
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing price for menu item: " + row[0]);
                }
            }
        }
    }

    // 加載訂單項目（無需跳過第一行）
    private void loadOrdersFromCSV() {
        List<String[]> data = CSVUtils.readCSV(ORDERS_CSV);
        for (String[] row : data) {
            if (row.length == 3) {
                orders.add(new Order(row[0], row[1], row[2]));
            }
        }
    }

    // 保存菜單項目
    public boolean saveMenuToCSV() {
        try (FileWriter writer = new FileWriter(MENU_CSV)) {
            writer.append("Item,Price\n");
            for (MenuItem item : getMenu()) {
                writer.append(item.getName())
                      .append(",")
                      .append(String.valueOf(item.getPrice()))
                      .append("\n");
            }
            return true; // Save successful
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Save failed
        }
    }

    // 保存訂單項目
    public void saveOrdersToCSV() {
        try (FileWriter writer = new FileWriter(ORDERS_CSV)) {
            for (Order order : getOrders()) {
                writer.append(order.getOrderNo())
                      .append(",")
                      .append(order.getTableNo())
                      .append(",")
                      .append(order.getStatus())
                      .append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter
    public List<MenuItem> getMenu() {
        return menu;
    }

    public List<Order> getOrders() {
        return orders;
    }

    // 添加菜單和訂單的方法
    public void addMenuItem(MenuItem item) {
        menu.add(item);
        saveMenuToCSV(); // 每次更新後保存到 CSV
    }

    public void addOrder(Order order) {
        orders.add(order);
        saveOrdersToCSV(); // 每次更新後保存到 CSV
    }

    public void updateOrderStatus(String orderNo, String newStatus) {
        for (Order order : orders) {
            if (order.getOrderNo().equals(orderNo)) {
                order.setStatus(newStatus);
                saveOrdersToCSV(); // 保存變更
                break;
            }
        }
    }
}
