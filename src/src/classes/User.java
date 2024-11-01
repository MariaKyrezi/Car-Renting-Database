package classes;

import java.sql.Date;

public class User {
	
	private String fname;
	private String lname;
	private int licence_id;
	private String password;
	private Date birthdate;
	private String address;
	private String card_num;
	private String card_csv;
	
	
	public User(String fname, String lname, int licence_id, String password, Date birthdate, String address, String card_num, String card_csv)
	{
		this.fname = fname;
		this.lname = lname;
		this.licence_id = licence_id;
		this.password = password;
		this.birthdate = birthdate;
		this.address = address;
		this.card_num = card_num;
		this.card_csv = card_csv;
	}
	
	public String toString()
	{
		return "User{" +
				"firstname = " + fname +
				", lastname = " + lname +
				", license_id = " + licence_id +
				", birthdate = " + birthdate +
				", address = " + address +
				", card_num = " + card_num +
				", card_CSV = " + card_csv +
				"}";
	}
	
	public String getFname(){return fname;}
	public void setFname(String fname){this.fname = fname;}
	
	public String getLname(){return lname;}
	public void setLname(String lname){this.lname = lname;}
	
	public int getLicence_id(){return licence_id;}
	public void setLicence_id(int licence_id){this.licence_id = licence_id;}
	
	public String getPassword(){return password;}
	public void setPassword(String password){this.password = password;}
	
	public Date getBirthdate(){return birthdate;}
	public void setBirthdate(Date birthdate){this.birthdate = birthdate;}
	
	public String getAddress(){return address;}
	public void setAddress(String address){this.address = address;}
	
	public String getCard_num(){return card_num;}
	public void setCard_num(String card_num){this.card_num = card_num;}
	
	public String getCard_csv(){return card_csv;}
	public void setCard_csv(String card_csv){this.card_csv = card_csv;}

}
