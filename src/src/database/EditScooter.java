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


import classes.Scooter;

public class EditScooter {
	
	public static void createScooterTable() throws ClassNotFoundException, SQLException
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();

        String query = "CREATE TABLE IF NOT EXISTS scooters "
                + "(vehicle_id INTEGER not NULL AUTO_INCREMENT,"
                + "		rent_cost FLOAT not null,"
                + "		vehicle_range FLOAT not null,"
                + "		insurance_cost FLOAT not null,"
                + "		color VARCHAR(30) not null,"
                + "		rent_counter INTEGER not null,"
                + " PRIMARY KEY (vehicle_id))";
        stmt.execute(query);
        stmt.close();
	}
	
	public static void dropScooterTable() throws ClassNotFoundException, SQLException
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		
		String query = "DROP TABLE scooters";
		
		stmt.execute(query);
        stmt.close();
	}
	
	public static void addNewScooter(Scooter scooter) throws ClassNotFoundException
	{
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
            		+ " scooters (rent_cost,vehicle_range,insurance_cost,color,rent_counter)"
            		+ " VALUES ("
            		+ "'" + scooter.getRent_cost() + "',"
            		+ "'" + scooter.getVehicle_range() + "',"
            		+ "'" + scooter.getInsurance_cost() + "',"
            		+ "'" + scooter.getColor() + "',"
            		+ "'" + scooter.getRent_counter() + "'"
            		+ ") ON DUPLICATE KEY UPDATE vehicle_id = vehicle_id;";

            
            stmt.executeUpdate(insertQuery);
            System.out.println("# Scooter " + scooter.getVehicle_id() + " was successfully added in the database.");
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
	
	
	public static void updateScooter(Scooter scooter) throws SQLException, ClassNotFoundException
	{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        
        String update = "UPDATE scooters SET rent_cost='" + scooter.getRent_cost() + "'"
        		+ ", vehicle_range='" + scooter.getVehicle_range() + "'"
        		+ ", insurance_cost='" + scooter.getInsurance_cost() + "'"
        		+ ", color='" + scooter.getColor() + "'"
        		+ ", rent_counter='" + scooter.getRent_counter() + "'"
        		+ " WHERE vehicle_id = '" + scooter.getVehicle_id() + "'";
        
        stmt.executeUpdate(update);
    }
	
	
	public static Scooter getScooter(int vehicle_id) throws SQLException, ClassNotFoundException
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
	
		ResultSet rs;
		Scooter scooter = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM scooters WHERE vehicle_id = '" + vehicle_id + "'");
			rs.next();

			float rent_cost = rs.getFloat("rent_cost");
			float vehicle_range = rs.getFloat("vehicle_range");
			float insurance_cost = rs.getFloat("insurance_cost");
			String color = rs.getString("color");
			int rent_counter = rs.getInt("rent_counter");
			
			scooter = new Scooter("Scooter", vehicle_id, rent_cost, vehicle_range, insurance_cost, color, rent_counter);
			
		} catch (Exception e) {
			System.err.println("Got an exception while trying to get scooter " + vehicle_id + "! ");
			System.err.println(e.getMessage());
		}

   		return scooter;
	}

	
	public static void deleteScooter(int vehicle_id) throws ClassNotFoundException, SQLException
	{
		Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String delete = "DELETE FROM scooters WHERE `scooters`.`vehicle_id` = " + vehicle_id;
        stmt.executeUpdate(delete);
	}
	
	public static void load_example_Scooters(String path)
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
                if(arg_count == 7)
                {
                	Scooter scooter = new Scooter(array.get(0), Integer.parseInt(array.get(1)), Float.parseFloat(array.get(2)), 
                			 Float.parseFloat(array.get(3)), Float.parseFloat(array.get(4)), array.get(5), Integer.parseInt(array.get(6)));
                     
                     EditScooter.addNewScooter(scooter);
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
