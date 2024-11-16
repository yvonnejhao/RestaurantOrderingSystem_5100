package main.java.com.restaurant.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderNo;
    private String tableNo;
    private String status;
    private List<MenuItem> items;

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

    // Getters and Setters
    public String getOrderNo() {
        return orderNo;
    }

    public String getTableNo() {
        return tableNo;
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
}
