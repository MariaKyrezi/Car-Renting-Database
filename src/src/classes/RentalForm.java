package classes;

import java.sql.Date;

public class RentalForm {
	
	private int form_id;
	private int vehicle_id;
	private String vehicle_type;
	private Date rent_duration;
	private float total_cost;
	private float payment_amount;
	private Date rental_date;
	private int client_license_id;
	private String client_fname;
	private String client_lname;
	
	
	public RentalForm(int form_id, int vehicle_id, String vehicle_type, Date rent_duration, float total_cost, float payment_amount, Date rental_date, 
			int client_license_id, String client_fname, String client_lname)
	{
		this.form_id = form_id;
		this.vehicle_id = vehicle_id;
		this.setVehicle_type(vehicle_type);
		this.rent_duration = rent_duration;
		this.total_cost = total_cost;
		this.payment_amount = payment_amount;
		this.rental_date = rental_date;
		this.client_license_id = client_license_id;
		this.client_fname = client_fname;
		this.client_lname = client_lname;
	}

	public String toString()
	{
		return "RentalForm{" +
				"form_id = " + form_id +
				", rent_duration = " + rent_duration +
				", total_cost = " + total_cost +
				", payment_amount = " + payment_amount +
				", rental_date = " + rental_date +
				", client_fname = " + client_fname +
				", client_lname = " + client_lname +
				"}";
	}
	
	public int getForm_id(){return form_id;}
	public void setForm_id(int form_id){this.form_id = form_id;}
	
	public int getVehicle_id(){return vehicle_id;}
	public void setVehicle_id(int vehicle_id){this.vehicle_id = vehicle_id;}
	
	public String getVehicle_type(){return vehicle_type;}
	public void setVehicle_type(String vehicle_type){this.vehicle_type = vehicle_type;}
	
	public Date getRent_duration(){return rent_duration;}
	public void setRent_duration(Date rent_duration){this.rent_duration = rent_duration;}
	
	public float getTotal_cost(){return total_cost;}
	public void setTotal_cost(float total_cost){this.total_cost = total_cost;}
	
	public float getPayment_amount(){return payment_amount;}
	public void setPayment_amount(float payment_amount){this.payment_amount = payment_amount;}
	
	public Date getRental_date(){return rental_date;}
	public void setRental_date(Date rental_date){this.rental_date = rental_date;}
	
	public int getClient_license_id(){return client_license_id;}
	public void setClient_license_id(int client_license_id){this.client_license_id = client_license_id;}
	
	public String getClient_fname(){return client_fname;}
	public void setClient_fname(String client_fname){this.client_fname = client_fname;}
	
	public String getClient_lname(){return client_lname;}
	public void setClient_lname(String client_lname){this.client_lname = client_lname;}
	
}
