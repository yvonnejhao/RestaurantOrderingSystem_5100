package main.java.com.restaurant.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;


    public ButtonEditor(JCheckBox checkBox, String label, ActionListener actionListener) {
        super(checkBox);
        this.label = label;
        button = new JButton(label);
        button.setOpaque(true);

        // Define what happens when the button is clicked
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // Stop editing when clicked
            }
        });
        button.addActionListener(actionListener); // Add custom action
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        button.setText(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
