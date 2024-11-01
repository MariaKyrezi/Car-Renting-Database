package frontEnd;

import javax.swing.*;

import classes.Bicycle;
import classes.Car;
import classes.Motorcycle;
import classes.RentalForm;
import classes.Scooter;

import database.EditBicycle;
import database.EditCar;
import database.EditMotorcycle;
import database.EditRentalForm;
import database.EditScooter;

import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class NewReservationWindow {
	
	private static float total_cost;

    /*public static void main(String[] args) {
        openNewReservationWindow();
    }*/

    public static void openNewReservationWindow() 
    {
    	JFrame newReservationFrame = new JFrame("New Reservation");
        newReservationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newReservationFrame.setSize(550, 450);
        newReservationFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel reservationPanel = new JPanel();
        reservationPanel.setLayout(new BoxLayout(reservationPanel, BoxLayout.Y_AXIS));

        // Autofill these text fields with the logged user's credentials
        JTextField customerName = CarRentalApp.createLabeledTextField(reservationPanel, "Customer Name:");
        customerName.setEditable(false);
        customerName.setText(mainBack.user.getFname());

        JTextField customerSurname = CarRentalApp.createLabeledTextField(reservationPanel, "Customer Surname:");
        customerSurname.setEditable(false);
        customerSurname.setText(mainBack.user.getLname());

        JTextField customerLicenseId = CarRentalApp.createLabeledTextField(reservationPanel, "Customer license id:");
        customerLicenseId.setEditable(false);
        customerLicenseId.setText(String.valueOf(mainBack.user.getLicence_id()));

        // Checkbox for "Someone else is driving"
        JCheckBox someoneElseCheckBox = new JCheckBox("Someone else is driving");
        JLabel driverLicenseIdLabel = new JLabel("Other driver's license id:");
        JTextField driverLicenseIdTextField = new JTextField();
        
        // Initially hide the text field
        driverLicenseIdTextField.setVisible(false);
        driverLicenseIdLabel.setVisible(false);
        
        // Add an action listener to the checkbox
        someoneElseCheckBox.addActionListener(e -> 
        {
            driverLicenseIdLabel.setVisible(someoneElseCheckBox.isSelected());
            driverLicenseIdTextField.setVisible(someoneElseCheckBox.isSelected());
        });

        // Use GridLayout to arrange components in rows and columns
        JPanel checkBoxPanel = new JPanel(new GridLayout(2, 2));
        checkBoxPanel.add(someoneElseCheckBox);
        checkBoxPanel.add(new JLabel()); // Placeholder for alignment
        checkBoxPanel.add(driverLicenseIdLabel);
        checkBoxPanel.add(driverLicenseIdTextField);

        reservationPanel.add(checkBoxPanel);

        
        // Suggestion: Vehicle id should be autofilled from a previous action of the user (User first selected a vehicle)
        JComboBox<String> vehicle_type_box = CarRentalApp.addVehicleTypeComboBox(reservationPanel, "Vehicle type:");
        JTextField vehicle_id_field = CarRentalApp.createLabeledTextField(reservationPanel, "Vehicle id:");
        JComboBox<String>[] dateComboBoxes = CarRentalApp.addDatePicker(reservationPanel, "Rental Date: ");
        JComboBox<String>[] durationComboBoxes = CarRentalApp.addDatePicker(reservationPanel, "Rental Duration: ");
        
        
        
        // Create a JTextField for displaying total cost
        JTextField totalCostTextField = CarRentalApp.createLabeledTextField(reservationPanel, "Total cost:");
        totalCostTextField.setEditable(false);

        // Add ActionListener to update total cost whenever combo boxes change
        vehicle_type_box.addActionListener(e -> updateTotalCost(reservationPanel, totalCostTextField, dateComboBoxes, durationComboBoxes, vehicle_type_box));
        vehicle_id_field.addFocusListener(new FocusListener() {
        	@Override
        	public void focusGained(FocusEvent e)
        	{
        		
        	}
        	
        	@Override
        	public void focusLost(FocusEvent e)
        	{
        		updateTotalCost(reservationPanel, totalCostTextField, dateComboBoxes, durationComboBoxes, vehicle_type_box);
        	}
        });
        for (JComboBox<String> comboBox : dateComboBoxes) 
        {
            comboBox.addActionListener(e -> updateTotalCost(reservationPanel, totalCostTextField, dateComboBoxes, durationComboBoxes, vehicle_type_box));
        }
        for (JComboBox<String> comboBox : durationComboBoxes)
        {
            comboBox.addActionListener(e -> updateTotalCost(reservationPanel, totalCostTextField, dateComboBoxes, durationComboBoxes, vehicle_type_box));
        }
        vehicle_type_box.addActionListener(e -> updateTotalCost(reservationPanel, totalCostTextField, dateComboBoxes, durationComboBoxes, vehicle_type_box));
            
        
        CarRentalApp.createLabeledTextField(reservationPanel, "Payment Amount:");
        CarRentalApp.createLabeledTextField(reservationPanel, "Other Details:");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Submit Reservation");
        buttonPanel.add(submitButton);
        submitButton.addActionListener(e -> handleSubmission(reservationPanel, newReservationFrame, dateComboBoxes, 
        		durationComboBoxes, vehicle_type_box, someoneElseCheckBox, driverLicenseIdTextField));

        reservationPanel.add(buttonPanel);

        mainPanel.add(reservationPanel, BorderLayout.CENTER);
        newReservationFrame.add(mainPanel);
        newReservationFrame.setVisible(true);
    }

    // Handle submission logic here
    private static void handleSubmission(JPanel reservationPanel, JFrame newReservationWindow, JComboBox<String>[] rentalDateBoxes, 
    		JComboBox<String>[] rentalDurationBoxes, JComboBox<String> vehicle_type_box, JCheckBox someoneElseCheckBox, JTextField driverLicenseIdTextField) 
    {
        String client_fname = CarRentalApp.getTextFromLabeledTextField(reservationPanel, "Customer Name:");
        String client_lname = CarRentalApp.getTextFromLabeledTextField(reservationPanel, "Customer Surname:");
        int client_license_id = CarRentalApp.parseIntegerField(reservationPanel, "Customer license id:");
        
        String vehicle_type = (String) vehicle_type_box.getSelectedItem();
        int vehicle_id = CarRentalApp.parseIntegerField(reservationPanel, "Vehicle id:");
        Date rental_date = CarRentalApp.getDateFromComboBoxes(rentalDateBoxes[0], rentalDateBoxes[1], rentalDateBoxes[2]);
        Date rental_duration = CarRentalApp.getDateFromComboBoxes(rentalDurationBoxes[0], rentalDurationBoxes[1], rentalDurationBoxes[2]);            
        float payment_amount = Float.parseFloat(CarRentalApp.getTextFromLabeledTextField(reservationPanel, "Payment Amount:"));
        String otherDetails = CarRentalApp.getTextFromLabeledTextField(reservationPanel, "Other Details:");
        
        // Perform actions with the reservation data (save to database, etc.)
        if (mainBack.user != null) {
            try {            	
            	
            	// Check if the vehicle is already rented
            	if(EditRentalForm.isVehicleRented(vehicle_id, vehicle_type) == true)
            	{
            		JOptionPane.showMessageDialog(newReservationWindow, "Vehicle is already rented!", "Reservation Error",
                            JOptionPane.ERROR_MESSAGE);
        			return;
            	}
            	
            	
            	// Check if vehicle exists and update rent counter, returns false if vehicle not found
            	if(updateRentCounter(newReservationWindow, vehicle_type, vehicle_id) == false)
            	{
            		return;
            	}
            	
            	// Add new Registration form to the database
            	if (someoneElseCheckBox.isSelected())
            	{
            		int otherDriverLicenseId = Integer.parseInt(driverLicenseIdTextField.getText());
                    System.out.println("Real Driver's License id: " + otherDriverLicenseId);
                    EditRentalForm.addNewRentalForm(new RentalForm(0, vehicle_id, vehicle_type, rental_duration, total_cost, payment_amount, rental_date,
                    		otherDriverLicenseId, client_fname, client_lname));
                }
            	else
            	{
            		EditRentalForm.addNewRentalForm(new RentalForm(0, vehicle_id, vehicle_type, rental_duration, total_cost, payment_amount, rental_date,
                    		client_license_id, client_fname, client_lname));
            	}
            	
                
                System.out.println("New Reservation:");
                System.out.println("Customer Name: " + client_fname);
                System.out.println("Customer Surname: " + client_lname);
                System.out.println("Customer license id: " + client_license_id);
                System.out.println("Vehicle type: " + vehicle_type);
                System.out.println("Vehicle id: " + vehicle_id);
                System.out.println("Rental Date: " + rental_date);
                System.out.println("Rental Duration: " + rental_duration);
                System.out.println("Total Cost: " + total_cost);
                System.out.println("Payment Amount: " + payment_amount);
                System.out.println("Other Details: " + otherDetails);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("User not logged in!");
            JOptionPane.showMessageDialog(newReservationWindow, "User not logged in!", "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Close the window after submission
        newReservationWindow.dispose();
    }
    
    /* Updates the rent counter of the given vehicle, else if no vehicle was found shows an error message*/
    private static boolean updateRentCounter(JFrame newReservationWindow, String vehicle_type, int vehicle_id) throws ClassNotFoundException, SQLException
    {
    	// Update the rent counter of the vehicle, also check if vehicle id is valid
    	switch(vehicle_type)
    	{
    	case "Car":
    		Car car = EditCar.getCar(vehicle_id);
    		if(car == null)
    		{
    			JOptionPane.showMessageDialog(newReservationWindow, "Invalid vehicle id!", "Reservation Error",
                        JOptionPane.ERROR_MESSAGE);
    			return false;
    		}
    		car.incRent_counter();
    		EditCar.updateCar(car);
    		break;
    	case "Motorcycle":
    		Motorcycle motorcycle = EditMotorcycle.getMotorcycle(vehicle_id);
    		if(motorcycle == null)
    		{
    			JOptionPane.showMessageDialog(newReservationWindow, "Invalid vehicle id!", "Reservation Error",
                        JOptionPane.ERROR_MESSAGE);
    			return false;
    		}
    		motorcycle.incRent_counter();
    		EditMotorcycle.updateMotorcycle(motorcycle);
    		break;
    	case "Scooter":
    		Scooter scooter = EditScooter.getScooter(vehicle_id);
    		if(scooter == null)
    		{
    			JOptionPane.showMessageDialog(newReservationWindow, "Invalid vehicle id!", "Reservation Error",
                        JOptionPane.ERROR_MESSAGE);
    			return false;
    		}
    		scooter.incRent_counter();
    		EditScooter.updateScooter(scooter);
    		break;
    	case "Bicycle":
    		Bicycle bicycle = EditBicycle.getBicycle(vehicle_id);
    		if(bicycle == null)
    		{
    			JOptionPane.showMessageDialog(newReservationWindow, "Invalid vehicle id!", "Reservation Error",
                        JOptionPane.ERROR_MESSAGE);
    			return false;
    		}
    		bicycle.incRent_counter();
    		EditBicycle.updateBicycle(bicycle);
    		break;
    	}
    	
    	return true;
    }
    
    private static void updateTotalCost(JPanel reservationPanel, JTextField totalCostTextField, JComboBox<String>[] rentalDateBoxes,
            JComboBox<String>[] rentalDurationBoxes, JComboBox<String> vehicle_type_box) 
    {
		// Calculate total cost based on the selected values in combo boxes
		try 
		{
			Date rental_date = CarRentalApp.getDateFromComboBoxes(rentalDateBoxes[0], rentalDateBoxes[1], rentalDateBoxes[2]);
			Date rental_duration = CarRentalApp.getDateFromComboBoxes(rentalDurationBoxes[0], rentalDurationBoxes[1], rentalDurationBoxes[2]);
			
			long timeDifference = rental_duration.getTime() - rental_date.getTime();
			int rent_days = (int) (timeDifference / (24 * 60 * 60 * 1000));
			
			String vehicle_type = (String) vehicle_type_box.getSelectedItem();
			int vehicle_id = CarRentalApp.parseIntegerField(reservationPanel, "Vehicle id:");
			float vehicle_rent = 1;
			
			try
			{
				// Retrieve rent cost based on the vehicle type and id
				switch (vehicle_type) 
				{
				case "Car":
					Car car = EditCar.getCar(vehicle_id);
					if(car == null)
					{
						JOptionPane.showMessageDialog(reservationPanel, "Invalid vehicle id!", "Reservation Error",
		                        JOptionPane.ERROR_MESSAGE);
					}
						vehicle_rent = car.getRent_cost();
					break;
				case "Motorcycle":
					Motorcycle motorcycle = EditMotorcycle.getMotorcycle(vehicle_id);
					if(motorcycle == null)
					{
						JOptionPane.showMessageDialog(reservationPanel, "Invalid vehicle id!", "Reservation Error",
		                        JOptionPane.ERROR_MESSAGE);
					}
					vehicle_rent = motorcycle.getRent_cost();
					break;
				case "Scooter":
					Scooter scooter = EditScooter.getScooter(vehicle_id);
					if(scooter == null)
					{
						JOptionPane.showMessageDialog(reservationPanel, "Invalid vehicle id!", "Reservation Error",
		                        JOptionPane.ERROR_MESSAGE);
					}
					vehicle_rent = scooter.getRent_cost();
					break;
				case "Bicycle":
					Bicycle bicycle = EditBicycle.getBicycle(vehicle_id);
					if(bicycle == null)
					{
						JOptionPane.showMessageDialog(reservationPanel, "Invalid vehicle id!", "Reservation Error",
		                        JOptionPane.ERROR_MESSAGE);
					}
					vehicle_rent = bicycle.getRent_cost();
					break;
				}
			}
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (SQLException e)
			{
				System.out.println("Could not find vehicle with id " + vehicle_id);
				e.printStackTrace();
			}
			
			
			total_cost = rent_days * vehicle_rent;
			totalCostTextField.setText(String.format("%.2f", total_cost));
		}
		catch (NumberFormatException | NullPointerException e) 
		{
			// Handle exceptions if necessary
			totalCostTextField.setText("");
		}
    }
    
}
