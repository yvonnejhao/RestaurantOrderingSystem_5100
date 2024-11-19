package com.restaurant;

import javax.swing.SwingUtilities;

import com.restaurant.models.MenuItem;
import com.restaurant.models.Order;
import com.restaurant.models.Restaurant;
import com.restaurant.ui.LoginView;

public class Main {
    public static void main(String[] args) {
        // 初始化餐厅实例
        Restaurant restaurant = new Restaurant();

        // // 添加菜单项
        // restaurant.addMenuItem(new MenuItem("Burger", 6.30));
        // restaurant.addMenuItem(new MenuItem("Pizza", 5.50));

        // // 添加测试订单
        // restaurant.addOrder(new Order("001", "01", "Done"));
        // restaurant.addOrder(new Order("002", "02", "Processing"));
        // restaurant.addOrder(new Order("003", "03", "Confirmed"));
        // restaurant.addOrder(new Order("004", "04", "Deleted"));

        // 使用 SwingUtilities 启动 LoginView
        SwingUtilities.invokeLater(() -> {
            new LoginView(restaurant).setVisible(true);
        });
    }
}
