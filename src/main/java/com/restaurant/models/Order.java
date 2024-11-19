package com.restaurant.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderNo;  // Three-digit order number
    private String tableNo;  // Table number associated with the order
    private String status;   // Status of the order (e.g., "Confirmed", "Processing")
    private List<MenuItem> items;  // List of menu items in the order

    // Constructor
    public Order(String orderNo, String tableNo, String status) {
        this.orderNo = orderNo;
        this.tableNo = tableNo;
        this.status = status;
        this.items = new ArrayList<>();
    }

    // Add a menu item to the order
    public void addItem(MenuItem item) {
        items.add(item);
    }

    // Calculate the total price of the order
    public double calculateTotal() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    // Generate a summary of the order
    public String generateSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Order No: ").append(orderNo).append("\n");
        summary.append("Table No: ").append(tableNo).append("\n");
        summary.append("Status: ").append(status).append("\n");
        summary.append("Items:\n");
        for (MenuItem item : items) {
            summary.append("- ").append(item.getName()).append(": $").append(String.format("%.2f", item.getPrice())).append("\n");
        }
        summary.append("Total: $").append(String.format("%.2f", calculateTotal())).append("\n");
        return summary.toString();
    }

    // Getters and Setters
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
}
