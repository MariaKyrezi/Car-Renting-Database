package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.StringTokenizer;

import classes.Car;

public class EditCar {
	
	public static void createCarTable() throws SQLException, ClassNotFoundException 
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();

        String query = "CREATE TABLE IF NOT EXISTS cars "
                + "(vehicle_id INTEGER not NULL AUTO_INCREMENT,"
                + "		rent_cost FLOAT not null,"
                + "		vehicle_range FLOAT not null,"
                + "		insurance_cost FLOAT not null unique,"
                + "		color VARCHAR(16) not null,"
                + "		rent_counter INTEGER not null,"
                + "		passanger_number INTEGER not null,"
                + "		brand VARCHAR(16) not null,"
                + "		model VARCHAR(16) not null,"
                + " PRIMARY KEY (vehicle_id))";
        stmt.execute(query);
        stmt.close();
    }
	
	public static void dropCarTable() throws ClassNotFoundException, SQLException
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		
		String query = "DROP TABLE cars";
		
		stmt.execute(query);
        stmt.close();
	}
	
	/* Inserts a new entry in the cars table*/
	public static void addNewCar (Car car) throws ClassNotFoundException
	{	
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
            		+ " cars (rent_cost,vehicle_range,insurance_cost,color,rent_counter,passanger_number,brand,model)"
            		+ " VALUES ("
            		+ "'" + car.getRent_cost() + "',"
            		+ "'" + car.getVehicle_range() + "',"
            		+ "'" + car.getInsurance_cost() + "',"
            		+ "'" + car.getColor() + "',"
            		+ "'" + car.getRent_counter() + "',"
            		+ "'" + car.getPassanger_number() + "',"
            		+ "'" + car.getBrand() + "',"
            		+ "'" + car.getModel() + "'"
            		+ ") ON DUPLICATE KEY UPDATE vehicle_id = vehicle_id;";


            
            stmt.executeUpdate(insertQuery);
            System.out.println("# Car " + car.getVehicle_id() + " was successfully added in the database.");
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
	
	
	public static void updateCar(Car car) throws SQLException, ClassNotFoundException
	{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        
        String update = "UPDATE cars SET rent_cost='" + car.getRent_cost() + "'"
        		+ ", vehicle_range='" + car.getVehicle_range() + "'"
        		+ ", insurance_cost='" + car.getInsurance_cost() + "'"
        		+ ", color='" + car.getColor() + "'"
        		+ ", rent_counter='" + car.getRent_counter() + "'"
        		+ ", passanger_number='" + car.getPassanger_number() + "'"
        		+ ", brand='" + car.getBrand() + "'"
        		+ ", model='" + car.getModel() + "'"
        		+ " WHERE vehicle_id = '" + car.getVehicle_id() + "'";
        
        stmt.executeUpdate(update);
    }
	
	
	public static Car getCar(int vehicle_id) throws SQLException, ClassNotFoundException
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
	
		ResultSet rs;
		Car car = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM cars WHERE vehicle_id = '" + vehicle_id + "'");
			rs.next();

			float rent_cost = rs.getFloat("rent_cost");
			float vehicle_range = rs.getFloat("vehicle_range");
			float insurance_cost = rs.getFloat("insurance_cost");
			String color = rs.getString("color");
			int rent_counter = rs.getInt("rent_counter");
			// Car specific fields
			int passanger_number = rs.getInt("passanger_number");;
			String brand = rs.getString("brand");
			String model = rs.getString("model");
			
			car = new Car("Car", vehicle_id, rent_cost, vehicle_range, insurance_cost, color, rent_counter, passanger_number,
					brand, model);
			
		} catch (Exception e) {
			System.err.println("Got an exception while trying to get car " + vehicle_id + "! ");
			System.err.println(e.getMessage());
		}

   		return car;
	}
	
	public static ArrayList<Car> getAllCars() throws SQLException, ClassNotFoundException
	{
		ArrayList<Car> carList = new ArrayList<>();
		
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
	
		ResultSet rs;
		rs = stmt.executeQuery("SELECT * FROM cars");
		while (rs.next()) 
        {
			int vehicle_id = rs.getInt("vehicle_id");
			float rent_cost = rs.getFloat("rent_cost");
			float vehicle_range = rs.getFloat("vehicle_range");
			float insurance_cost = rs.getFloat("insurance_cost");
			String color = rs.getString("color");
			int rent_counter = rs.getInt("rent_counter");
			// Car specific fields
			int passanger_number = rs.getInt("passanger_number");;
			String brand = rs.getString("brand");
			String model = rs.getString("model");
			
			Car car = new Car("Car", vehicle_id, rent_cost, vehicle_range, insurance_cost, color, rent_counter, passanger_number,
					brand, model);
			
            carList.add(car);
        }


        return carList;
	}

	
	public static void deleteCar(int vehicle_id) throws ClassNotFoundException, SQLException
	{
		Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String delete = "DELETE FROM cars WHERE `cars`.`vehicle_id` = "+ vehicle_id;
        stmt.executeUpdate(delete);
	}
	
	public static void load_example_Cars(String path)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(path));
			ArrayList<String> array = new ArrayList<String>();
            String line;
            int arg_count = 0;
            
            while ((line = br.readLine()) != null) 
            {
            	// Check for comments
            	if(line.charAt(0) == '#')
            	{
            		continue;
            	}
            	
                // Tokenize the line using StringTokenizer with space as delimiter
                StringTokenizer tokenizer = new StringTokenizer(line);

                // Process each word
                arg_count = 0;
                while (tokenizer.hasMoreTokens()) 
                {
                    array.add(tokenizer.nextToken());
                    arg_count++;
                }
                
                // Checking if there is a mistake in the examples
                if(arg_count == 10)
                {
                	 Car car = new Car(array.get(0), Integer.parseInt(array.get(1)), Float.parseFloat(array.get(2)), 
                			 Float.parseFloat(array.get(3)), Float.parseFloat(array.get(4)), array.get(5), Integer.parseInt(array.get(6)),
                			 Integer.parseInt(array.get(7)), array.get(8), array.get(9));
                     
                     EditCar.addNewCar(car);;
                }
                
                array.clear();
            }
            br.close();
        } 
		catch (IOException | ClassNotFoundException e) 
		{
            e.printStackTrace();
        }
	}
	
}
