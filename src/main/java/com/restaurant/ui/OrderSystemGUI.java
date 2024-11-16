package main.java.com.restaurant.ui;

import main.java.com.restaurant.models.MenuItem;
import main.java.com.restaurant.models.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderSystemGUI extends JFrame {
    private Restaurant restaurant;
    private JTextArea menuArea;
    private JTextArea orderArea;
    private double total = 0.0;

    public OrderSystemGUI() {
        restaurant = new Restaurant();
        initializeMenu();

        setTitle("Restaurant Ordering System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Menu Display Area
        menuArea = new JTextArea();
        menuArea.setEditable(false);
        updateMenuDisplay();
        JScrollPane menuScroll = new JScrollPane(menuArea);
        menuScroll.setBorder(BorderFactory.createTitledBorder("Menu"));

        // Order Display Area
        orderArea = new JTextArea();
        orderArea.setEditable(false);
        JScrollPane orderScroll = new JScrollPane(orderArea);
        orderScroll.setBorder(BorderFactory.createTitledBorder("Your Order"));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton checkoutButton = new JButton("Checkout");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToOrder();
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(checkoutButton);

        // Add components to frame
        add(menuScroll, BorderLayout.WEST);
        add(orderScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initializeMenu() {
        restaurant.addMenuItem(new MenuItem("Burger", 5.99));
        restaurant.addMenuItem(new MenuItem("Pasta", 7.49));
        restaurant.addMenuItem(new MenuItem("Salad", 4.99));
        restaurant.addMenuItem(new MenuItem("Coffee", 2.99));
    }

    private void updateMenuDisplay() {
        StringBuilder menuText = new StringBuilder();
        for (MenuItem item : restaurant.getMenu()) {
            menuText.append(item.getName()).append(" - $").append(item.getPrice()).append("\n");
        }
        menuArea.setText(menuText.toString());
    }

    private void addItemToOrder() {
        String itemName = JOptionPane.showInputDialog(this, "Enter item name:");
        for (MenuItem item : restaurant.getMenu()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                orderArea.append(item.getName() + " - $" + item.getPrice() + "\n");
                total += item.getPrice();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Item not found in menu.");
    }

    private void checkout() {
        orderArea.append("\nTotal: $" + total + "\n");
        JOptionPane.showMessageDialog(this, "Thank you for your order! Total: $" + total);
        total = 0;
        orderArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderSystemGUI gui = new OrderSystemGUI();
            gui.setVisible(true);
        });
    }
}


// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class OrderSystemGUI extends JFrame {
//     private Restaurant restaurant;
//     private JTextArea menuArea;
//     private JTextArea orderArea;
//     private double total = 0.0;

//     public OrderSystemGUI() {
//         restaurant = new Restaurant();
//         initializeMenu();

//         setTitle("Restaurant Ordering System");
//         setSize(500, 400);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new BorderLayout());

//         // Menu Display Area
//         menuArea = new JTextArea();
//         menuArea.setEditable(false);
//         updateMenuDisplay();
//         JScrollPane menuScroll = new JScrollPane(menuArea);
//         menuScroll.setBorder(BorderFactory.createTitledBorder("Menu"));

//         // Order Display Area
//         orderArea = new JTextArea();
//         orderArea.setEditable(false);
//         JScrollPane orderScroll = new JScrollPane(orderArea);
//         orderScroll.setBorder(BorderFactory.createTitledBorder("Your Order"));

//         // Button Panel
//         JPanel buttonPanel = new JPanel();
//         JButton addButton = new JButton("Add Item");
//         JButton checkoutButton = new JButton("Checkout");

//         addButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 addItemToOrder();
//             }
//         });

//         checkoutButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 checkout();
//             }
//         });

//         buttonPanel.add(addButton);
//         buttonPanel.add(checkoutButton);

//         // Add components to frame
//         add(menuScroll, BorderLayout.WEST);
//         add(orderScroll, BorderLayout.CENTER);
//         add(buttonPanel, BorderLayout.SOUTH);
//     }

//     private void initializeMenu() {
//         restaurant.addMenuItem(new MenuItem("Burger", 5.99));
//         restaurant.addMenuItem(new MenuItem("Pasta", 7.49));
//         restaurant.addMenuItem(new MenuItem("Salad", 4.99));
//         restaurant.addMenuItem(new MenuItem("Coffee", 2.99));
//     }

//     private void updateMenuDisplay() {
//         StringBuilder menuText = new StringBuilder();
//         for (MenuItem item : restaurant.getMenu()) {
//             menuText.append(item.getName()).append(" - $").append(item.getPrice()).append("\n");
//         }
//         menuArea.setText(menuText.toString());
//     }

//     private void addItemToOrder() {
//         String itemName = JOptionPane.showInputDialog(this, "Enter item name:");
//         for (MenuItem item : restaurant.getMenu()) {
//             if (item.getName().equalsIgnoreCase(itemName)) {
//                 orderArea.append(item.getName() + " - $" + item.getPrice() + "\n");
//                 total += item.getPrice();
//                 return;
//             }
//         }
//         JOptionPane.showMessageDialog(this, "Item not found in menu.");
//     }

//     private void checkout() {
//         orderArea.append("\nTotal: $" + total + "\n");
//         JOptionPane.showMessageDialog(this, "Thank you for your order! Total: $" + total);
//         total = 0;
//         orderArea.setText("");
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             OrderSystemGUI gui = new OrderSystemGUI();
//             gui.setVisible(true);
//         });
//     }
// }
