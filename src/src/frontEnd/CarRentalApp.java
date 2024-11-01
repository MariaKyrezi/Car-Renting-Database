package frontEnd;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.Calendar;

public class CarRentalApp {
	
	private static ActionListener listener;
	
    private static JButton customerLoginButton;
    private static JButton customerRegistrationButton;
    private static JButton newReservationButton;
    private static JButton upcomingModelsButton;
    private static JButton availableVehiclesButton;
    private static JButton returnVehicleButton;
    private static JButton reportAccidentButton;
    private static JButton customerSupportButton;

    
    /*public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> new CarRentalApp());
	}*/
    
    
    public static void StartCarRentalApp() 
    {
        JFrame appFrame = new JFrame("Car Rental Management System");
        appFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        appFrame.setLayout(new BorderLayout());

        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(8, 1));

        listener = new ButtonClickListener();
        customerLoginButton = createButton("Customer Login", listener);
        customerRegistrationButton = createButton("Customer Registration", listener);
        newReservationButton = createButton("New Reservation", listener);
        upcomingModelsButton = createButton("Upcoming Models", listener);
        availableVehiclesButton = createButton("Available Vehicles", listener);
        returnVehicleButton = createButton("Return Vehicle", listener);
        reportAccidentButton = createButton("Report Accident", listener);
        customerSupportButton = createButton("Customer Support", listener);

        buttonsPanel.add(customerLoginButton);
        buttonsPanel.add(customerRegistrationButton);
        buttonsPanel.add(newReservationButton);
        buttonsPanel.add(upcomingModelsButton);
        buttonsPanel.add(availableVehiclesButton);
        buttonsPanel.add(returnVehicleButton);
        buttonsPanel.add(reportAccidentButton);
        buttonsPanel.add(customerSupportButton);

        appFrame.add(buttonsPanel, BorderLayout.WEST);

        // Add an image to the free space
        ImageIcon carImage = new ImageIcon("src/media/car.jpg");  // Replace "car_image.jpg" with your image file path
        JLabel imageLabel = new JLabel(carImage);
        appFrame.add(imageLabel, BorderLayout.CENTER);

        appFrame.setSize(730, 500);
        appFrame.setLocationRelativeTo(null);
        appFrame.setVisible(true);
        
        appFrame.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                // This method will be called just before the window is closed
                // Perform any actions you want here
            	
            	mainBack.latch.countDown();	//Signal back end code that the app is closing
            	
                // Dispose the window
            	appFrame.dispose();
            }
        });
        
    }

    private static JButton createButton(String buttonText, ActionListener listener) 
    {
        JButton button = new JButton(buttonText);
        button.addActionListener(listener);
        return button;
    }


    private static class ButtonClickListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            JButton sourceButton = (JButton) e.getSource();
            String buttonText = sourceButton.getText();
            

            switch (buttonText) 
            {
            	case "Customer Login":
            		LoginWindow.createLogin();
            		break;
                case "Customer Registration":
                	RegistrationWindow.CustomerRegistrationWindow();
                    break;
                case "New Reservation":
                	if (mainBack.user != null) 
                	{                		
                		NewReservationWindow.openNewReservationWindow();
                	}
                	else
                	{
                		System.out.println("User not logged in!");
                        JOptionPane.showMessageDialog(sourceButton.getRootPane().getParent(), "User not logged in!", "Registration Error",
                                JOptionPane.ERROR_MESSAGE);
                	}
                    break;
                case "Upcoming Models":
                	// Fill later?
                    break;
                case "Available Vehicles":
                	AvailableVehiclesWindow.createVehiclesWindow();
                    break;
                case "Return Vehicle":
                    openReturnCarWindow();
                    break;
                case "Report Accident":
                    openReportAccidentWindow();
                    break;
                case "Customer Support":
                    openCustomerSupportWindow();
                    break;
            }
        }
        
        

        

        private void openReturnCarWindow() {
            JFrame newReservationWindow = new JFrame("Return Car Details");
            newReservationWindow.setSize(400, 300);
            newReservationWindow.setLocationRelativeTo(null);

            JPanel reservationPanel = new JPanel();
            reservationPanel.setLayout(new BoxLayout(reservationPanel, BoxLayout.Y_AXIS));

            reservationPanel.add(createLabeledTextField("Customer Name:"));
            reservationPanel.add(createLabeledTextField("Date of Return:"));
            reservationPanel.add(createLabeledTextField("Time of return:"));
            reservationPanel.add(createLabeledTextField("Payment Amount:"));
            reservationPanel.add(createLabeledTextField("Reservation ID:"));

            JButton submitButton = new JButton("Submit Return");
            submitButton.addActionListener(e -> {
                // Handle submission logic here
                String customerName = getTextFromLabeledTextField(reservationPanel, "Customer Name:");
                String date = getTextFromLabeledTextField(reservationPanel, "Date of Return:");
                String time = getTextFromLabeledTextField(reservationPanel, "Time of return:");
                //εδώ θα πρέπει να υπολογίσω την νέα τιμή ανάλογα με το αν άργησε να επιστρέψει το αυτοκινητο
                //String paymentAmount = getTextFromLabeledTextField(reservationPanel, "Payment Amount:");
                //String otherDetails = getTextFromLabeledTextField(reservationPanel, "Other Details:");

                // Perform actions with the reservation data (save to database, etc.)
                // For now, print the data to the console
                System.out.println("Customer Name: " + customerName);
                System.out.println("Date of Return: " + date);
                System.out.println("Time of Return: " + time);
                //System.out.println("Payment Amount: " + paymentAmount);
                //System.out.println("Other Details: " + otherDetails);

                // Close the window after submission
                newReservationWindow.dispose();
            });

            reservationPanel.add(submitButton);

            newReservationWindow.add(reservationPanel);
            newReservationWindow.setVisible(true);
        }

        private void openReportAccidentWindow() {
            JFrame reportAccidentWindow = new JFrame("Report Accident");
            reportAccidentWindow.setSize(400, 200);
            reportAccidentWindow.setLocationRelativeTo(null);

            JPanel accidentPanel = new JPanel();
            accidentPanel.setLayout(new BoxLayout(accidentPanel, BoxLayout.Y_AXIS));

            accidentPanel.add(new JLabel("Have you paid the security money?"));

            ButtonGroup buttonGroup = new ButtonGroup();

            JRadioButton yesRadioButton = new JRadioButton("Yes");
            yesRadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
            JRadioButton noRadioButton = new JRadioButton("No");
            noRadioButton.setFont(new Font("Arial", Font.PLAIN, 16));

            buttonGroup.add(yesRadioButton);
            buttonGroup.add(noRadioButton);

            accidentPanel.add(yesRadioButton);
            accidentPanel.add(noRadioButton);

            JLabel reservationIdLabel = new JLabel("Enter Reservation ID:");
            reservationIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            JTextField reservationIdTextField = new JTextField(10); // Adjust the width as needed

            accidentPanel.add(reservationIdLabel);
            accidentPanel.add(reservationIdTextField);

            JButton submitButton = new JButton("Submit Report");
            submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
            submitButton.addActionListener(e -> {
                if (yesRadioButton.isSelected()) {
                    String reservationId = reservationIdTextField.getText();
                    if (!reservationId.isEmpty()) {
                        displayMessage("The car will be changed without extra cost.\nReservation ID: " + reservationId);
                    } else {
                        displayMessage("Please enter a valid Reservation ID.");
                    }
                } else if (noRadioButton.isSelected()) {
                    displayMessage("You will be charged 3 times the cost.");
                }
                // Close the window after submission
                reportAccidentWindow.dispose();
            });

            accidentPanel.add(submitButton);

            reportAccidentWindow.add(accidentPanel);
            reportAccidentWindow.setVisible(true);
        }

        private void displayMessage(String message) {
            JOptionPane.showMessageDialog(null, message);
        }
        private JPanel createLabeledTextField(String labelText) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel label = new JLabel(labelText);
            JTextField textField = new JTextField(20); // Adjust the width of the text field as needed

            panel.add(label);
            panel.add(textField);

            return panel;
        }

        private String getTextFromLabeledTextField(JPanel parentPanel, String labelText) {
            for (Component component : parentPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel panel = (JPanel) component;
                    for (Component innerComponent : panel.getComponents()) {
                        if (innerComponent instanceof JTextField) {
                            JTextField textField = (JTextField) innerComponent;
                            JLabel label = (JLabel) panel.getComponent(0); // Assuming label is the first component
                            if (label.getText().equals(labelText)) {
                                return textField.getText();
                            }
                        }
                    }
                }
            }
            return "";
        }

        private void openCustomerSupportWindow() {
            JFrame customerSupportWindow = new JFrame("Customer Support");
            customerSupportWindow.setSize(600, 300);
            customerSupportWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the support window

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JTextArea resultArea = new JTextArea();
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea);

            JPanel queryPanel = new JPanel();
            queryPanel.setLayout(new GridLayout(0, 1));

            // Add buttons for predefined questions
            String[] predefinedQuestions = {
                    "Status of available or rented vehicles by category",
                    "Rental status by time period",
                    "Maximum, minimum, and average rental duration by vehicle category",
                    "Revenue from rentals by time period and vehicle category",
                    "Total maintenance and repair expenses by time period",
                    "Most popular vehicle by category"
            };

            for (String question : predefinedQuestions) {
                JButton questionButton = new JButton(question);
                questionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String queryResult = performPredefinedQuery(question);
                        resultArea.append("\n\n" + queryResult);
                    }
                });
                queryPanel.add(questionButton);
            }
            // Add space for additional customer questions
            JTextArea customerQuestionArea = new JTextArea();
            customerQuestionArea.setRows(5);
            customerQuestionArea.setEditable(true);

            JScrollPane customerQuestionScrollPane = new JScrollPane(customerQuestionArea);

            JLabel customerQuestionLabel = new JLabel("Questions from Customer:");

            JButton submitQuestionButton = new JButton("Submit Question");
            submitQuestionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String customerQuestion = customerQuestionArea.getText();
                    // Perform actions with the customer question, e.g., database storage, processing, etc.
                    resultArea.append("\n\nCustomer Question: " + customerQuestion);
                    // Provide a sample answer for illustration purposes
                    String sampleAnswer = "Sample answer to customer question.";
                    // Clear the text area after submission
                    customerQuestionArea.setText("");
                }
            });

            mainPanel.add(queryPanel, BorderLayout.WEST);
            JPanel customerQuestionPanel = new JPanel(new BorderLayout());
            customerQuestionPanel.add(customerQuestionLabel, BorderLayout.NORTH);
            customerQuestionPanel.add(customerQuestionScrollPane, BorderLayout.CENTER);
            customerQuestionPanel.add(submitQuestionButton, BorderLayout.SOUTH);
            mainPanel.add(customerQuestionPanel, BorderLayout.CENTER);
            mainPanel.add(scrollPane, BorderLayout.SOUTH);

            customerSupportWindow.setContentPane(mainPanel);
            customerSupportWindow.setVisible(true);
        }
        private String performPredefinedQuery(String question) {
            // Placeholder for predefined query logic
            // Implement the logic to execute the predefined query on the database
            // Return the result as a string for simplicity in this example
            switch (question) {
                case "Status of available or rented vehicles by category":
                    return "Response to question: " + question + " - Query Result 1";
                case "Rental status by time period":
                    return "Response to question: " + question + " - Query Result 2";
                // Add cases for other questions
                default:
                    return "Unknown question: " + question;
            }
        }

        private void openNewWindow(String windowTitle) {
            JFrame newWindow = new JFrame(windowTitle);
            newWindow.setSize(400, 300);
            newWindow.setLocationRelativeTo(null);
            newWindow.setVisible(true);
        }
    }
    
    
    
    
    public static JTextField createLabeledTextField(JPanel panel, String labelText) 
    {
    	
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(20); // Adjust the size as needed

        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldPanel.add(label);
        fieldPanel.add(textField);

        panel.add(fieldPanel);

        return textField;
    }

    public static JPasswordField addPasswordField(JPanel panel, String labelText) 
    {
        JLabel label = new JLabel(labelText);
        JPasswordField passwordField = new JPasswordField(30); // Adjust the size as needed

        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldPanel.add(label);
        fieldPanel.add(passwordField);

        panel.add(fieldPanel);

        return passwordField;
    }
    
    public static String getTextFromLabeledTextField(JPanel parentPanel, String labelText) {
        for (Component component : parentPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component innerComponent : panel.getComponents()) {
                    if (innerComponent instanceof JTextField) {
                        JTextField textField = (JTextField) innerComponent;
                        Component labelOrCheckBox = panel.getComponent(0); // Assuming label or checkbox is the first component
                        if (labelOrCheckBox instanceof JLabel) {
                            JLabel label = (JLabel) labelOrCheckBox;
                            if (label.getText().equals(labelText)) {
                                return textField.getText();
                            }
                        } else if (labelOrCheckBox instanceof JCheckBox) {
                            JCheckBox checkBox = (JCheckBox) labelOrCheckBox;
                            if (checkBox.getText().equals(labelText)) {
                                return textField.getText();
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    
    public static String getPasswordFromLabeledPasswordField(JPanel parentPanel, String labelText) {
        for (Component component : parentPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component innerComponent : panel.getComponents()) {
                    if (innerComponent instanceof JPasswordField) {
                        JPasswordField passwordField = (JPasswordField) innerComponent;
                        JLabel label = (JLabel) panel.getComponent(0); // Assuming label is the first component
                        if (label.getText().equals(labelText)) {
                            // Password fields have a different method for retrieving text
                            char[] passwordChars = passwordField.getPassword();
                            return new String(passwordChars);
                        }
                    }
                }
            }
        }
        return "";
    }

    /*Returns a negative number if the call fails*/
    public static int parseIntegerField(JPanel panel, String label) 
    {
        try 
        {
            String input = CarRentalApp.getTextFromLabeledTextField(panel, label).trim();
            return Integer.parseInt(input);
        } 
        catch (NumberFormatException e)
        {
            // Handle the case where the input is not a valid integer
            JOptionPane.showMessageDialog(null, label + " must be a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return 0; // or return a default value, depending on your requirements
        }
    }


    public static Date getDateFromComboBoxes(JComboBox<String> dayComboBox, JComboBox<String> monthComboBox, JComboBox<String> yearComboBox) 
    {
        // Extract selected day, month, and year
        int day = Integer.parseInt((String) dayComboBox.getSelectedItem());
        String month = (String) monthComboBox.getSelectedItem();
        int year = Integer.parseInt((String) yearComboBox.getSelectedItem());

        // Convert month string to a numerical value (assuming January is 1)
        int monthValue;
        switch (month) {
            case "January": monthValue = 1; break;
            case "February": monthValue = 2; break;
            case "March": monthValue = 3; break;
            case "April": monthValue = 4; break;
            case "May": monthValue = 5; break;
            case "June": monthValue = 6; break;
            case "July": monthValue = 7; break;
            case "August": monthValue = 8; break;
            case "September": monthValue = 9; break;
            case "October": monthValue = 10; break;
            case "November": monthValue = 11; break;
            case "December": monthValue = 12; break;
            default: monthValue = 1; // Default to January if month is not recognized
        }

        // Construct a java.sql.Date object
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthValue - 1, day); // Note: month is 0-indexed in Calendar
        java.sql.Date sqlDate = new java.sql.Date(calendar.getTimeInMillis());

        return sqlDate;
    }

    public static JComboBox<String>[] addDatePicker(JPanel panel, String label_text) 
    {
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout());

        // Day ComboBox
        String[] daysArray = new String[31];
        for (int i = 1; i <= 31; i++) 
        {
            daysArray[i - 1] = String.valueOf(i);
        }
        JComboBox<String> dayComboBox = new JComboBox<>(daysArray);

        // Month ComboBox
        String[] monthsArray = new String[]{"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthComboBox = new JComboBox<>(monthsArray);

        // Year ComboBox
        String[] yearsArray = new String[100];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 100; i++) 
        {
            yearsArray[i] = String.valueOf(currentYear - i);
        }
        JComboBox<String> yearComboBox = new JComboBox<>(yearsArray);

        datePanel.add(new JLabel(label_text));
        datePanel.add(dayComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);

        panel.add(datePanel);

        // Return the ComboBoxes for later use
        return new JComboBox[]{dayComboBox, monthComboBox, yearComboBox};
    }

    public static JComboBox<String> addVehicleTypeComboBox(JPanel panel, String label_text)
    {
        JPanel typePanel = new JPanel();

        // Vehicle Type ComboBox
        String[] vehicleTypes = {"Car", "Motorcycle", "Scooter", "Bicycle"};
        JComboBox<String> typeComboBox = new JComboBox<>(vehicleTypes);
        typeComboBox.setName("VehicleTypeComboBox"); // Set a name for easy retrieval

        typePanel.add(new JLabel(label_text));
        typePanel.add(typeComboBox);

        panel.add(typePanel);
        
		return typeComboBox;
    }

}

