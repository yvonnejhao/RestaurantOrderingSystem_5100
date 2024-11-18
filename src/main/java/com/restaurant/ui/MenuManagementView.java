package com.restaurant.ui;

import com.restaurant.models.MenuItem;
import com.restaurant.models.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

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

        // Load menu items into the table
        loadMenuItems();

        // Set cell renderers and editors for Modify and Delete buttons
        menuTable.getColumn("Modify").setCellRenderer(new ButtonRenderer());
        menuTable.getColumn("Modify").setCellEditor(new ButtonEditor(new JCheckBox(), "Modify", () -> handleModify(menuTable)));

        menuTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        menuTable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete", () -> handleDelete(menuTable)));

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
        List<MenuItem> menu = restaurant.getMenu();
        for (MenuItem item : menu) {
            tableModel.addRow(new Object[]{item.getName(), String.format("$ %.2f", item.getPrice()), "Modify", "Delete"});
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

    private void handleModify(JTable table) {
        int row = table.getSelectedRow();
        String currentName = (String) tableModel.getValueAt(row, 0);
        String currentPrice = (String) tableModel.getValueAt(row, 1);

        String newName = JOptionPane.showInputDialog(this, "Enter new name:", currentName);
        String newPriceStr = JOptionPane.showInputDialog(this, "Enter new price:", currentPrice.replace("$ ", ""));

        try {
            double newPrice = Double.parseDouble(newPriceStr);
            restaurant.getMenu().get(row).setName(newName);
            restaurant.getMenu().get(row).setPrice(newPrice);

            tableModel.setValueAt(newName, row, 0);
            tableModel.setValueAt(String.format("$ %.2f", newPrice), row, 1);

            JOptionPane.showMessageDialog(this, "Item modified successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete(JTable table) {
        int row = table.getSelectedRow();
        restaurant.getMenu().remove(row);
        tableModel.removeRow(row);
        JOptionPane.showMessageDialog(this, "Item deleted successfully!");
    }

    // Renderer for buttons
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor for buttons
    private static class ButtonEditor extends DefaultCellEditor {
        private JButton button;

        public ButtonEditor(JCheckBox checkBox, String label, Runnable action) {
            super(checkBox);
            button = new JButton(label);
            button.setOpaque(true);
            button.addActionListener(e -> {
                action.run();
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText((value == null) ? "" : value.toString());
            return button;
        }
    }
}
