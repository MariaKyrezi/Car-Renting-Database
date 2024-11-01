package frontEnd;


import java.sql.SQLException;

import java.util.concurrent.CountDownLatch;

import classes.User;
import classes.Vehicle;
import database.DB_Connection;


public class mainBack {
	
	public static User user = null;
	public static Vehicle vehicle_choice = null;
	public static CountDownLatch latch = new CountDownLatch(1);
	
	
	public static void main(String[] args)
	{
		try
		{
			String load_examples = "no";
			String clear_database = "no";
			
			
			DB_Connection.initDatabase();
			System.out.println("Database initialized!");
			
			load_examples = ConfirmationWindow.getAnswer("Load examples to database?");
			if(load_examples.equals("yes"))
			{
				DB_Connection.load_examples();	//read .txt and setup tables in database
			}
			
			System.out.println("Starting front end...");
            Thread guiThread = new Thread(() -> CarRentalApp.StartCarRentalApp());
            guiThread.start();

            latch.await(); // Wait for the app to close
            
	        
	        // Cleaning up...
			clear_database = ConfirmationWindow.getAnswer("Clear database?");
			if(clear_database.equals("yes"))
			{
				System.out.println("Cleaning up...");
				DB_Connection.dropTables();
				System.out.println("Tables cleared!");
			}
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (InterruptedException e1) 
		{
			e1.printStackTrace();
		}
    }
}
