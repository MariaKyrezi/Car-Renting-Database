package frontEnd;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;

import classes.User;
import database.EditUser;

public class LoginWindow {

    private static JTextField license_idTextField;
    private static JPasswordField passwordTextField;

    /*public static void main(String[] args) {
        createLogin();
    }*/

    public static void createLogin() 
    {
        JFrame loginFrame = new JFrame("Login Window");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel loginPanel = new JPanel(new BorderLayout());

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

        license_idTextField = CarRentalApp.createLabeledTextField(fieldsPanel, "License id:");
        passwordTextField = CarRentalApp.addPasswordField(fieldsPanel, "Password:");

        JButton loginButton = new JButton("Login");

        // Add ActionListener to the login button
        loginButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                User user = null;
                try
                {
                    user = EditUser.getUser(Integer.parseInt(license_idTextField.getText().trim()),
                            new String(passwordTextField.getPassword()));
                } 
                catch (NumberFormatException | ClassNotFoundException | SQLException e1) 
                {
                    e1.printStackTrace();
                    user = null;
                }

                if (user == null) 
                {
                	System.out.println("Incorrect credentials!");
                    JOptionPane.showMessageDialog(loginFrame, "Incorrect credentials", "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    mainBack.user = user;
                    System.out.println("User " + user.getLname() + " with id " + user.getLicence_id() + " has logged in!");
                    JOptionPane.showMessageDialog(loginFrame, "Login successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loginFrame.dispose(); // Close the login window after successful login
                }
            }
        });

        fieldsPanel.add(loginButton);

        JPanel verticalCenterPanel = new JPanel();
        verticalCenterPanel.setLayout(new BoxLayout(verticalCenterPanel, BoxLayout.Y_AXIS));
        verticalCenterPanel.add(Box.createVerticalGlue());
        verticalCenterPanel.add(fieldsPanel);
        verticalCenterPanel.add(Box.createVerticalGlue());

        loginPanel.add(verticalCenterPanel, BorderLayout.CENTER);

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }
    
}
