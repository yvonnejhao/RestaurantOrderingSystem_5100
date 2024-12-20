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

    // Observer mechanism to notify changes
    private List<Runnable> menuChangeListeners;

    public Restaurant() {
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.menuChangeListeners = new ArrayList<>();
        loadMenuFromCSV();
        loadOrdersFromCSV();
    }

    // Load menu items
    private void loadMenuFromCSV() {
        List<String[]> data = CSVUtils.readCSV(MENU_CSV);
        boolean isFirstLine = true; // Used to mark if it is the first line
        for (String[] row : data) {
            if (isFirstLine) {
                isFirstLine = false; // Skip the first line
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

    // Load order items (no need to skip the first line)
    private void loadOrdersFromCSV() {
        List<String[]> data = CSVUtils.readCSV(ORDERS_CSV);
        for (String[] row : data) {
            if (row.length == 3) {
                orders.add(new Order(row[0], row[1], row[2]));
            }
        }
    }

    // Save menu items
    public boolean saveMenuToCSV() {
        try (FileWriter writer = new FileWriter(MENU_CSV)) {
            writer.append("Item,Price\n");
            for (MenuItem item : getMenu()) {
                writer.append(item.getName())
                      .append(",")
                      .append(String.valueOf(item.getPrice()))
                      .append("\n");
            }
            notifyMenuChangeListeners(); // Notify observers that the menu has been updated
            return true; // Save successful
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Save failed
        }
    }

    // Save order items
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

    // Methods to add menu and order items
    public void addMenuItem(MenuItem item) {
        menu.add(item);
        saveMenuToCSV(); // Save to CSV after each update
    }

    public void addOrder(Order order) {
        orders.add(order);
        saveOrdersToCSV(); // Save to CSV after each update
    }

    public void updateMenuItem(int index, String newName, double newPrice) {
        MenuItem item = menu.get(index);
        item.setName(newName);
        item.setPrice(newPrice);
        saveMenuToCSV(); // Save and notify observers after update
    }

    public void removeMenuItem(int index) {
        menu.remove(index);
        saveMenuToCSV(); // Save and notify observers after deletion
    }

    public void updateOrderStatus(String orderNo, String newStatus) {
        for (Order order : orders) {
            if (order.getOrderNo().equals(orderNo)) {
                order.setStatus(newStatus);
                saveOrdersToCSV(); // Save changes
                break;
            }
        }
    }

    // Observer-related methods
    public void addMenuChangeListener(Runnable listener) {
        menuChangeListeners.add(listener);
    }

    private void notifyMenuChangeListeners() {
        for (Runnable listener : menuChangeListeners) {
            listener.run();
        }
    }

    // Getters
    public List<MenuItem> getMenu() {
        return menu;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
