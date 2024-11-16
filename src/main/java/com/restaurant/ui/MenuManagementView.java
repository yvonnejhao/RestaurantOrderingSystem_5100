package main.java.com.restaurant.ui;

import main.java.com.restaurant.models.MenuItem;
import main.java.com.restaurant.models.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuManagementView extends JPanel {
    private Restaurant restaurant;
    private DefaultTableModel tableModel;

    public MenuManagementView(Restaurant restaurant) {
        this.restaurant = restaurant;
        setLayout(new BorderLayout());

        // Initialize table model with columns
        String[] columns = {"Item", "Price", "Modify", "Delete"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable menuTable = new JTable(tableModel);
        menuTable.setRowHeight(30);

        // Add "Modify" and "Delete" functionality
        menuTable.getColumn("Modify").setCellEditor(new ButtonEditor(new JCheckBox(), "Modify", e -> {
            int row = menuTable.getSelectedRow();
            String itemName = (String) tableModel.getValueAt(row, 0);
            MenuItem item = restaurant.getMenu().stream()
                    .filter(menuItem -> menuItem.getName().equals(itemName))
                    .findFirst().orElse(null);
            if (item != null) {
                String newName = JOptionPane.showInputDialog(this, "Enter new name:", item.getName());
                String newPriceStr = JOptionPane.showInputDialog(this, "Enter new price:", item.getPrice());
                try {
                    double newPrice = Double.parseDouble(newPriceStr);
                    item.setName(newName);
                    item.setPrice(newPrice);
                    refreshTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));

        menuTable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete", e -> {
            int row = menuTable.getSelectedRow();
            String itemName = (String) tableModel.getValueAt(row, 0);
            MenuItem item = restaurant.getMenu().stream()
                    .filter(menuItem -> menuItem.getName().equals(itemName))
                    .findFirst().orElse(null);
            if (item != null) {
                restaurant.removeMenuItem(item);
                refreshTable();
            }
        }));

        // Load menu items into the table
        loadMenuItems();

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(menuTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel for adding new items
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add new menu item");
        addButton.addActionListener(e -> addNewItem());
        buttonPanel.add(addButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadMenuItems() {
        tableModel.setRowCount(0); // Clear table before loading
        for (MenuItem item : restaurant.getMenu()) {
            tableModel.addRow(new Object[]{item.getName(), "$ " + item.getPrice(), "Modify", "Delete"});
        }
    }

    private void addNewItem() {
        String itemName = JOptionPane.showInputDialog(this, "Enter Item Name:");
        if (itemName == null || itemName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Item name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String priceStr = JOptionPane.showInputDialog(this, "Enter Item Price:");
        if (priceStr == null || priceStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Price cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            MenuItem newItem = new MenuItem(itemName, price);
            restaurant.addMenuItem(newItem);
            tableModel.addRow(new Object[]{itemName, String.format("$ %.2f", price), "Modify", "Delete"});
            JOptionPane.showMessageDialog(this, "New item added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        loadMenuItems();
    }
}
