import javax.swing.*;
import java.awt.*;

public class SwingDemo extends JFrame {

    public SwingDemo() {
        setTitle("Swing Components Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null); 
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

    
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);
        add(nameLabel);
        add(nameField);

        JButton greetButton = new JButton("Greet");
        add(greetButton);
       
        JToggleButton toggleButton = new JToggleButton("Notifications: OFF");
        toggleButton.addActionListener(e -> {
            if (toggleButton.isSelected()) {
                toggleButton.setText("Notifications: ON");
            } else {
                toggleButton.setText("Notifications: OFF");
            }
        });
        add(toggleButton);

        JCheckBox newsletterBox = new JCheckBox("Subscribe to weekly updates");
        add(newsletterBox);

        JLabel planLabel = new JLabel("Select Plan:");
        JRadioButton basicPlan = new JRadioButton("Basic", true);
        JRadioButton proPlan = new JRadioButton("Pro");

        ButtonGroup planGroup = new ButtonGroup();
        planGroup.add(basicPlan);
        planGroup.add(proPlan);

        add(planLabel);
        add(basicPlan);
        add(proPlan);

        greetButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String plan = basicPlan.isSelected() ? "Basic" : "Pro";
            String sub = newsletterBox.isSelected() ? "Subscribed" : "Not Subscribed";
            
            JOptionPane.showMessageDialog(this,
                    "User: " + (name.isEmpty() ? "Anonymous" : name) +
                    "\nPlan: " + plan +
                    "\nUpdates: " + sub);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SwingDemo().setVisible(true);
        });
    }
}