package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.StringTokenizer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import classes.User;

public class EditUser {
	
	public static void createUserTable() throws SQLException, ClassNotFoundException 
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();

        String query = "CREATE TABLE IF NOT EXISTS users "
                + "(user_id INTEGER not NULL AUTO_INCREMENT,"
                + "		firstname VARCHAR(20) not null,"
                + "		lastname VARCHAR(30) not null,"
                + "		license_id INTEGER not NULL unique,"
                + "		password VARCHAR(30) not null,"
                + "		birthdate DATE not null,"
                + "		address VARCHAR(30) not null,"
                + "		card_num VARCHAR(16) not null,"
                + "		card_CSV VARCHAR(3) not null,"
                + " PRIMARY KEY (user_id))";
        stmt.execute(query);
        stmt.close();
    }
	
	public static void dropUserTable() throws ClassNotFoundException, SQLException
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		
		String query = "DROP TABLE users";
		
		stmt.execute(query);
        stmt.close();
	}
	
	/* Inserts a new entry in the users table*/
	public static boolean addNewUser (User user) throws ClassNotFoundException
	{	
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
            		+ " users (firstname,lastname,license_id,password,birthdate,address,card_num,card_CSV)"
            		+ " VALUES ("
            		+ "'" + user.getFname() + "',"
            		+ "'" + user.getLname() + "',"
            		+ "'" + user.getLicence_id() + "',"
            		+ "'" + user.getPassword() + "',"
            		+ "'" + user.getBirthdate() + "',"
            		+ "'" + user.getAddress() + "',"
            		+ "'" + user.getCard_num() + "',"
            		+ "'" + user.getCard_csv() + "'"
            		+ ")";


            
            stmt.executeUpdate(insertQuery);
            System.out.println("# User " + user.getFname() + " was successfully added in the database.");
            stmt.close();

        } catch (SQLException ex) {
        	System.out.println("SQL Error when adding user with user license id " + user.getLicence_id());
            // ex.printStackTrace();
            return false;
        }
        
        return true;
    }
	
	
	public static void updateUser(User user) throws SQLException, ClassNotFoundException
	{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        
        String update = "UPDATE users SET firstname='" + user.getFname() + "'"
        		+", birthdate='" + user.getBirthdate() + "'"
        		+", address='" + user.getAddress() + "'"
        		+", card_num='" + user.getCard_num() + "'"
        		+", card_CSV='" + user.getCard_csv() + "'"
        		+" WHERE license_id = '" + user.getLicence_id() + "'";
        stmt.executeUpdate(update);
    }
	
	
	public static User getUser(int license_id, String password) throws SQLException, ClassNotFoundException
	{
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
	
		ResultSet rs;
		User user = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM users WHERE license_id = '" + license_id + "' AND password='"+password+"'");
			rs.next();
			
			String fname = rs.getString("firstname");
			String lname = rs.getString("lastname");
			int licence_id = rs.getInt("license_id");
			Date birthdate = rs.getDate("birthdate");
			String address = rs.getString("address");
			String card_num = rs.getString("card_num");
			String card_csv = rs.getString("card_CSV");
			
			user = new User(fname, lname, licence_id, password, birthdate, address, card_num, card_csv);
			
		} catch (Exception e) {
			System.err.println("Got an exception while trying to get the user with license id " + license_id + "! ");
			System.err.println(e.getMessage());
		}

   		return user;
	}

	
	public static void deleteUser(int licence_id) throws ClassNotFoundException, SQLException
	{
		Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String delete = "DELETE FROM users WHERE `users`.`license_id` = "+ licence_id;
        stmt.executeUpdate(delete);
	}
	
	public static void load_example_users(String path)
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
                if(arg_count == 8)
                {
                	 User user = new User(array.get(0), array.get(1), Integer.parseInt(array.get(2)), 
                     		array.get(3), Date.valueOf(array.get(4)), array.get(5), array.get(6), array.get(7));
                     
                     EditUser.addNewUser(user);
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
