package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import classes.RentalForm;

public class EditRentalForm {
	public static void createRentalFormTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE IF NOT EXISTS rental_forms "
                + "(form_id INTEGER not NULL AUTO_INCREMENT,"
                + "		vehicle_id INTEGER not null,"
                + "		vehicle_type VARCHAR(15) not null,"
                + "		rent_duration DATE not null,"
                + "		total_cost FLOAT not null,"
                + "		payment_amount FLOAT not null,"
                + "		rental_date DATE not null,"
                + "		client_license_id INTEGER not null,"
                + "		client_fname VARCHAR(30) not null,"
                + "		client_lname VARCHAR(30) not null,"
                + " PRIMARY KEY (form_id))";
        stmt.execute(query);
        stmt.close();
    }
	
	public static void dropRentalFormTable() throws ClassNotFoundException, SQLException
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		
		String query = "DROP TABLE rental_forms";
		
		stmt.execute(query);
        stmt.close();
	}
	
	public static void addNewRentalForm (RentalForm rentalForm) throws ClassNotFoundException {
		
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
            		+ " rental_forms (form_id,vehicle_id,vehicle_type,rent_duration,total_cost,payment_amount,rental_date,"
            		+ "client_license_id,client_fname,client_lname)"
            		+ " VALUES ("
            		+ "'" + rentalForm.getForm_id() + "',"
            		+ "'" + rentalForm.getVehicle_id() + "',"
            		+ "'" + rentalForm.getVehicle_type() + "',"
            		+ "'" + rentalForm.getRent_duration() + "',"
            		+ "'" + rentalForm.getTotal_cost() + "',"
            		+ "'" + rentalForm.getPayment_amount() + "',"
            		+ "'" + rentalForm.getRental_date() + "',"
            		+ "'" + rentalForm.getClient_license_id() + "',"
            		+ "'" + rentalForm.getClient_fname() + "',"
            		+ "'" + rentalForm.getClient_lname() + "'"
            		+ ") ON DUPLICATE KEY UPDATE form_id = form_id;";

            
            stmt.executeUpdate(insertQuery);
            System.out.println("# Rental form was successfully added in the database.");
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
	
	public static RentalForm getRentalForm(int form_id) throws SQLException, ClassNotFoundException
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
	
		ResultSet rs;
		RentalForm rentalForm = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM rental_forms WHERE form_id = '" + form_id + "'");
			rs.next();

			int vehicle_id = rs.getInt("vehicle_id");
			String vehicle_type = rs.getString("vehicle_type");
			Date rent_duration = Date.valueOf(rs.getString("rent_duration"));
			float total_cost = rs.getInt("total_cost");
			float payment_amount = rs.getInt("payment_amount");
			Date rental_date = rs.getDate("rental_date");
			int client_license_id = rs.getInt("client_license_id");
			String client_fname = rs.getString("client_fname");
			String client_lname = rs.getString("client_lname");
			
			rentalForm = new RentalForm(form_id, vehicle_id, vehicle_type, rent_duration, total_cost, payment_amount, rental_date,
					client_license_id, client_fname, client_lname);
			
		} catch (Exception e) {
			System.err.println("Got an exception while trying to get rental form id : " + form_id + "! ");
			System.err.println(e.getMessage());
		}

   		return rentalForm;
	}
	
	public static boolean isVehicleRented(int vehicle_id, String vehicle_type) throws SQLException, ClassNotFoundException
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
	
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT * FROM rental_forms WHERE vehicle_id = '" + vehicle_id + "'");
			if(rs.next() == true)	// If there is a valid row
			{
				String type = rs.getString("vehicle_type");
				
				if(type.equals(vehicle_type))
					return true;
			}			
			
		} catch (Exception e) {
			System.err.println("Got an exception while trying to get rental form for vehicle id : " + vehicle_id + "!");
			System.err.println(e.getMessage());
		}

   		return false;
	}
	
	public static void load_example_rentalForms(String path)
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
                	 RentalForm rentalForm = new RentalForm(Integer.parseInt(array.get(0)), Integer.parseInt(array.get(1)), array.get(2),
                			 Date.valueOf(array.get(3)), (float) Double.parseDouble(array.get(4)), (float) Double.parseDouble(array.get(5)), 
                     		Date.valueOf(array.get(6)), Integer.parseInt(array.get(7)), array.get(8), array.get(9));
                     
                     EditRentalForm.addNewRentalForm(rentalForm);
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
