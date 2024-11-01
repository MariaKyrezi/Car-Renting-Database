package frontEnd;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.concurrent.CountDownLatch;


public class ConfirmationWindow {
	
	private static String userChoice;
	private static CountDownLatch latch;

    public static String getAnswer(String question) 
    {
    	// Initialize static vars
    	userChoice = null;
    	latch = new CountDownLatch(1);
    	
        // Create a separate thread for GUI creation
        Thread guiThread = new Thread(() -> createWindow(question));
        guiThread.start();

        try 
        {
            // Wait for user input
            latch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return userChoice;
    }

    private static void createWindow(String question)
    {
        // Create the main frame
        JFrame frame = new JFrame("Load examples confirmation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);

        // Center the frame on the screen
        centerFrameOnScreen(frame);

        // Create a panel to hold the components
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);
        placeComponents(frame, panel, question);

        // Set the frame to be visible
        frame.setVisible(true);
    }

    private static void centerFrameOnScreen(JFrame frame)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    private static void placeComponents(JFrame frame, JPanel panel, String question)
    {
        panel.setLayout(new GridBagLayout());

        // Create constraints for centering components
        GridBagConstraints centerConstraints = new GridBagConstraints();
        centerConstraints.gridx = 0;
        centerConstraints.gridy = 0;
        centerConstraints.anchor = GridBagConstraints.CENTER;

        // Create the text label
        JLabel label = new JLabel(question);
        panel.add(label, centerConstraints);

        // Create constraints for centering buttons
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.anchor = GridBagConstraints.CENTER;
        buttonConstraints.insets.top = 10;  // Add some top margin

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        panel.add(buttonPanel, buttonConstraints);

        // Create constraints for spacing between buttons
        GridBagConstraints buttonSpacingConstraints = new GridBagConstraints();
        buttonSpacingConstraints.gridx = 1;
        buttonSpacingConstraints.gridy = 0;
        buttonSpacingConstraints.insets.left = 10;  // Add some left margin

        // Create the "YES" button
        JButton yesButton = new JButton("YES");
        buttonPanel.add(yesButton, buttonSpacingConstraints);

        // Create constraints for spacing between buttons
        GridBagConstraints buttonSpacingConstraints2 = new GridBagConstraints();
        buttonSpacingConstraints2.gridx = 2;
        buttonSpacingConstraints2.gridy = 0;
        buttonSpacingConstraints2.insets.left = 10;  // Add some left margin

        // Create the "NO" button
        JButton noButton = new JButton("NO");
        buttonPanel.add(noButton, buttonSpacingConstraints2);

        // Add action listeners to the buttons
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                userChoice = "yes";
                handleUserChoice(frame);
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                userChoice = "no";
                handleUserChoice(frame);
            }
        });
    }

    private static void handleUserChoice(JFrame frame)
    {
        frame.dispose(); 	// Close the window
        
        latch.countDown();	// Signal to release the waiting thread
    }
}
