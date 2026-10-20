package com.netdatahub.swing;

import javax.swing.*;
import java.awt.*;

public class SwingDemo extends JFrame {

    public SwingDemo() {
        setTitle("Swing Components & Look and Feel Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        // 1. Text Field
        JLabel nameLabel = new JLabel("Product/User Name:");
        JTextField nameField = new JTextField(15);
        add(nameLabel);
        add(nameField);

        // 2. Button
        JButton greetButton = new JButton("Submit Action");
        add(greetButton);

        // 3. Toggle Button
        JToggleButton toggleButton = new JToggleButton("Notifications: OFF");
        toggleButton.addActionListener(e -> {
            if (toggleButton.isSelected()) {
                toggleButton.setText("Notifications: ON");
            } else {
                toggleButton.setText("Notifications: OFF");
            }
        });
        add(toggleButton);

        // 4. Checkbox
        JCheckBox newsletterBox = new JCheckBox("Subscribe to DB Event Stream");
        add(newsletterBox);

        // 5. Radio Buttons
        JLabel planLabel = new JLabel("Select Tier:");
        JRadioButton basicPlan = new JRadioButton("Basic", true);
        JRadioButton proPlan = new JRadioButton("Pro");

        ButtonGroup planGroup = new ButtonGroup();
        planGroup.add(basicPlan);
        planGroup.add(proPlan);

        add(planLabel);
        add(basicPlan);
        add(proPlan);

        // Button action handling
        greetButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String plan = basicPlan.isSelected() ? "Basic" : "Pro";
            String sub = newsletterBox.isSelected() ? "Subscribed" : "Not Subscribed";

            JOptionPane.showMessageDialog(this,
                    "Name: " + (name.isEmpty() ? "Anonymous" : name) +
                    "\nPlan: " + plan +
                    "\nStatus: " + sub,
                    "Swing Form Submitted",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void main(String[] args) {
        // Set System Pluggable Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Run GUI construction on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new SwingDemo().setVisible(true);
        });
    }
}
