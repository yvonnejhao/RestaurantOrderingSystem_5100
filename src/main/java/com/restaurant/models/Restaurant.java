package com.restaurant.models;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private List<MenuItem> menu;
    private List<Order> orders; // 新增訂單清單

    public Restaurant() {
        menu = new ArrayList<>();
        orders = new ArrayList<>(); // 初始化訂單清單
    }

    // 菜單管理方法
    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void removeMenuItem(MenuItem item) {
        menu.remove(item);
    }

    // 訂單管理方法
    public void addOrder(Order order) {
        orders.add(order); // 添加訂單到訂單清單
    }

    public List<Order> getOrders() {
        return orders; // 返回所有訂單
    }

    public void updateOrderStatus(String orderNo, String newStatus) {
        for (Order order : orders) {
            if (order.getOrderNo().equals(orderNo)) {
                order.setStatus(newStatus);
                break;
            }
        }
    }
}
