package frontEnd;

import javax.swing.*;

import classes.User;
import database.EditUser;

import java.awt.*;
import java.sql.Date;

public class RegistrationWindow {

    /*public static void main(String[] args) {
        CustomerRegistrationWindow();
    }*/

    public static void CustomerRegistrationWindow() 
    {
        JFrame newCustomerRegistrationFrame = new JFrame("Customer Registration Window");
        newCustomerRegistrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newCustomerRegistrationFrame.setSize(450, 350);
        newCustomerRegistrationFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new BoxLayout(registrationPanel, BoxLayout.Y_AXIS));
        
        CarRentalApp.createLabeledTextField(registrationPanel, "Customer Name:");
        CarRentalApp.createLabeledTextField(registrationPanel, "Customer Surname:");
        CarRentalApp.createLabeledTextField(registrationPanel, "Driving Licence Number:");
        CarRentalApp.addPasswordField(registrationPanel, "Password:");
        CarRentalApp.createLabeledTextField(registrationPanel, "Address:");
        JComboBox<String>[] dateComboBoxes = CarRentalApp.addDatePicker(registrationPanel, "Birthday: ");
        CarRentalApp.createLabeledTextField(registrationPanel, "Credit Card Number:");
        CarRentalApp.createLabeledTextField(registrationPanel, "Credit Card Pin (CSV):");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Submit Registration");
        buttonPanel.add(submitButton);
        submitButton.addActionListener(e -> handleSubmission(registrationPanel, newCustomerRegistrationFrame, dateComboBoxes));

        registrationPanel.add(buttonPanel);

        mainPanel.add(registrationPanel, BorderLayout.CENTER);
        newCustomerRegistrationFrame.add(mainPanel);
        newCustomerRegistrationFrame.setVisible(true);
    }
    
    public static void handleSubmission(JPanel registrationPanel, JFrame newCustomerRegistrationFrame, JComboBox<String>[] dateComboBoxes) 
    {
        String customerName = CarRentalApp.getTextFromLabeledTextField(registrationPanel, "Customer Name:");
        String customerSurname = CarRentalApp.getTextFromLabeledTextField(registrationPanel, "Customer Surname:");
        int licenseNumber = CarRentalApp.parseIntegerField(registrationPanel, "Driving Licence Number:");
        String password = CarRentalApp.getPasswordFromLabeledPasswordField(registrationPanel, "Password:");
        String address = CarRentalApp.getTextFromLabeledTextField(registrationPanel, "Address:");
        Date birthday = CarRentalApp.getDateFromComboBoxes(dateComboBoxes[0], dateComboBoxes[1], dateComboBoxes[2]);
        String creditCardNumber = CarRentalApp.getTextFromLabeledTextField(registrationPanel, "Credit Card Number:");
        String creditCardPin = CarRentalApp.getTextFromLabeledTextField(registrationPanel, "Credit Card Pin (CSV):");

        // Perform actions with the registration data (save to database, etc.)
        
        // Integrity check
        if(customerName.equals("") || customerSurname.equals("") || password.equals("") 
        		|| address.equals("") || creditCardNumber.equals("") || creditCardPin.equals(""))
        {
        	JOptionPane.showMessageDialog(null, "Please fill all the fields correctly!", "Input Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        else if(creditCardPin.length() != 3)
        {
        	JOptionPane.showMessageDialog(null, "Credit card CSV must be 3 digits!", "Input Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        else if(licenseNumber < 0)
        {
        	JOptionPane.showMessageDialog(null, "License id must be a positive number!", "Input Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        
        
        
        try 
        {
			boolean addedUser = EditUser.addNewUser(new User(customerName, customerSurname, licenseNumber, password, birthday, address,
					creditCardNumber, creditCardPin));
			
			if(!addedUser)
			{
				JOptionPane.showMessageDialog(null, "There already exists a user with that license id!", "Input Error", JOptionPane.ERROR_MESSAGE);
	        	return;
			}
		} 
        catch (ClassNotFoundException e1) 
        {
        	System.out.println("Error when trying to register user : " + customerName + " with license id : " + licenseNumber);
			e1.printStackTrace();
		}
        
        System.out.println("Customer Name: " + customerName);
        System.out.println("Customer Surname: " + customerSurname);
        System.out.println("Driving Licence Number: " + licenseNumber);
        System.out.println("Password: " + password);
        System.out.println("Birthday: " + birthday);
        System.out.println("Address: " + address);
        System.out.println("Credit Card Number: " + creditCardNumber);
        System.out.println("Credit Card Pin (CSV): " + creditCardPin);

        JOptionPane.showMessageDialog(null, "Registration complete!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // Close the window after submission
        newCustomerRegistrationFrame.dispose();
    }
    
}
