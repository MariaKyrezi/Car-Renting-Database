package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DB_Connection {
	private static final String url = "jdbc:mysql://localhost";
    private static final int port = 3306;
    private static final String username = "root";
    private static final String password = "";
    private static final String databaseName = "EVOL_Services";
    
    
    /**
     * Attempts to establish a specific database connection
     *
     * @return a connection to the database
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url + ":" + port + "/" + databaseName, username, password);
    }
    
    /*Attempts to establish a connection to the hub of databases, to create a database*/
    public static Connection getInitialConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); //initialize Driver class
        return DriverManager.getConnection(url + ":" + port, username, password);
    }
    
    public static void initDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE IF NOT EXISTS " + databaseName);
        stmt.close();
        conn.close();
        
        initTables();
    }
    
    /* In case i want to clear the database stored in XAMPP*/
    public static void removeDatabase() throws ClassNotFoundException, SQLException {
    	Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("DROP DATABASE IF EXISTS " + databaseName);
        stmt.close();
        conn.close();
    }
    
    public static void initTables() throws SQLException, ClassNotFoundException {
        EditUser.createUserTable();
        EditRentalForm.createRentalFormTable();
        
        // Vehicles
        EditCar.createCarTable();
        EditMotorcycle.createMotorcycleTable();
        EditBicycle.createBicycleTable();
        EditScooter.createScooterTable();
    }
    
    public static void dropTables() throws ClassNotFoundException, SQLException
    {
    	EditUser.dropUserTable();
    	EditRentalForm.dropRentalFormTable();
    	
    	// Vehicles
    	EditCar.dropCarTable();
    	EditMotorcycle.dropMotorcycleTable();
    	EditBicycle.dropBicycleTable();
    	EditScooter.dropScooterTable();
    }
    
    public static void load_examples()
    {
    	EditUser.load_example_users("src/examples/users.txt");
    	EditRentalForm.load_example_rentalForms("src/examples/rentals.txt");
    	
    	// Vehicles
    	EditCar.load_example_Cars("src/examples/cars.txt");
    	EditMotorcycle.load_example_Motorcycle("src/examples/motorcycles.txt");
    	EditBicycle.load_example_Bicycles("src/examples/bicycles.txt");
    	EditScooter.load_example_Scooters("src/examples/scooters.txt");
    }
    
}
