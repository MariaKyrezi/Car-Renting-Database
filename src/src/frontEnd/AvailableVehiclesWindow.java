package frontEnd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AvailableVehiclesWindow {

    public static void main(String[] args) {
        createVehiclesWindow();
    }

    public static void createVehiclesWindow() 
    {
        JFrame availablevehiclesFrame = new JFrame("Available Vehicles");
        availablevehiclesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        availablevehiclesFrame.setSize(500, 400);
        availablevehiclesFrame.setLayout(new GridLayout(2, 2));

        // Example image paths, replace with your actual image paths
        String[] imagePaths = {
                "src/media/car.jpg",
                "src/media/motorcycle.jpg",
                "src/media/scooter.jpg",
                "src/media/bicycle.jpg"
        };

        for (int i = 0; i < 4; i++) 
        {
            JButton button = new JButton(new ImageIcon(imagePaths[i]));
            scaleImageIcon(button, imagePaths[i], 200, 150); // Set the desired size (e.g., 100x100)
            button.addActionListener(new ButtonClickListener(i + 1)); // Each button corresponds to a number
            availablevehiclesFrame.add(button);
        }
        
        availablevehiclesFrame.setLocationRelativeTo(null);
        availablevehiclesFrame.setVisible(true);
    }

    private static void createDynamicButtonWindow(int buttonNumber) 
    {
        JFrame dynamicButtonWindow = new JFrame("Dynamic Buttons");
        dynamicButtonWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dynamicButtonWindow.setSize(400, 300);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));

        // Example: Generate a random number of buttons (between 1 and 10) for demonstration
        int numButtons = new Random().nextInt(10) + 1;

        for (int i = 0; i < numButtons; i++) {
            JButton dynamicButton = new JButton("Button " + (i + 1));
            buttonPanel.add(dynamicButton);
        }

        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        dynamicButtonWindow.add(scrollPane);

        dynamicButtonWindow.setLocationRelativeTo(null);
        dynamicButtonWindow.setVisible(true);
    }

    private static class ButtonClickListener implements ActionListener 
    {
        private final int buttonNumber;

        public ButtonClickListener(int buttonNumber) 
        {
            this.buttonNumber = buttonNumber;
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            // Handle button click
            createDynamicButtonWindow(buttonNumber);
        }
    }
    
    private static void scaleImageIcon(JButton button, String imagePath, int width, int height) 
    {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(image));
    }
}
