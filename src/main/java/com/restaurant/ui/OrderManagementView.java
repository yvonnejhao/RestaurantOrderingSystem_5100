package com.restaurant.ui;

import com.restaurant.models.Order;
import com.restaurant.models.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrderManagementView extends JPanel {
    private Restaurant restaurant;
    private DefaultTableModel tableModel;
    private JTable orderTable;

    public OrderManagementView(Restaurant restaurant) {
        this.restaurant = restaurant;
        setLayout(new BorderLayout());

        // Initialize table model with columns
        String[] columns = {"Order No.", "Table No.", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only the "Status" column editable
                return column == 2;
            }
        };

        orderTable = new JTable(tableModel);
        orderTable.setRowHeight(30);

        // Create a JComboBox for the "Status" column
        String[] statusOptions = {"Done", "Processing", "Confirmed", "Deleted"};
        JComboBox<String> comboBox = new JComboBox<>(statusOptions);

        // Set JComboBox as both editor and renderer
        orderTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox));
        orderTable.getColumnModel().getColumn(2).setCellRenderer(new ComboBoxRenderer(statusOptions));

        // Load orders into the table
        loadOrders();

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(orderTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for saving changes, closing, and going back to the login page
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveChanges());
        buttonPanel.add(saveButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.dispose();
            }
        });
        buttonPanel.add(closeButton);

        JButton backToLoginButton = new JButton("Back to Login");
        backToLoginButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.dispose(); // Close the current window
            }
            new LoginView(restaurant).setVisible(true); // Open the login page
        });
        buttonPanel.add(backToLoginButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadOrders() {
        List<Order> orders = restaurant.getOrders();
        for (Order order : orders) {
            tableModel.addRow(new Object[]{order.getOrderNo(), order.getTableNo(), order.getStatus()});
        }
    }

    private void saveChanges() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String orderNo = (String) tableModel.getValueAt(i, 0);
            String newStatus = (String) tableModel.getValueAt(i, 2);

            // Update the order status in the restaurant model
            restaurant.updateOrderStatus(orderNo, newStatus);
        }
        restaurant.saveOrdersToCSV(); // Save to CSV file
        JOptionPane.showMessageDialog(this, "Order statuses updated successfully!");
    }

    // Custom renderer to show dropdown arrow always
    private static class ComboBoxRenderer extends DefaultTableCellRenderer {
        private final JComboBox<String> comboBox;

        public ComboBoxRenderer(String[] items) {
            comboBox = new JComboBox<>(items);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            comboBox.setSelectedItem(value);
            if (isSelected) {
                comboBox.setBackground(table.getSelectionBackground());
                comboBox.setForeground(table.getSelectionForeground());
            } else {
                comboBox.setBackground(table.getBackground());
                comboBox.setForeground(table.getForeground());
            }
            return comboBox;
        }
    }
}
