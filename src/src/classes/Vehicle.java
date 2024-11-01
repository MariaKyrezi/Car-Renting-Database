package classes;

public abstract class Vehicle {
	
	private String type;
	private int vehicle_id;
	private float rent_cost;
	private float vehicle_range;
	private float insurance_cost;
	private String color;
	private int rent_counter;
	
	public Vehicle(String _type, int _vehicle_id, float _rent_cost, float _vehicle_range, float _insurance_cost, String _color,
			int _rent_counter)
	{
		this.type = _type;
		this.vehicle_id = _vehicle_id;
		this.rent_cost = _rent_cost;
		this.vehicle_range = _vehicle_range;
		this.insurance_cost = _insurance_cost;
		this.color = _color;
		this.rent_counter = _rent_counter;
	}
	
	public String toString()
	{
		return type + "{"
				+ vehicle_id + ", "
				+ rent_cost + ", "
				+ vehicle_range + ", "
				+ insurance_cost + ", "
				+ color + ", "
				+ rent_counter
				+ "}";
	}
	
	
	public String getType(){return type;}
	public void setType(String type){this.type = type;}
	
	public int getVehicle_id(){return vehicle_id;}
	public void setVehicle_id(int vehicle_id){this.vehicle_id = vehicle_id;}
	
	public float getRent_cost(){return rent_cost;}
	public void setRent_cost(float rent_cost){this.rent_cost = rent_cost;}
	
	public float getVehicle_range(){return vehicle_range;}
	public void setVehicle_range(float vehicle_range){this.vehicle_range = vehicle_range;}
	
	public float getInsurance_cost(){return insurance_cost;}
	public void setInsurance_cost(float insurance_cost){this.insurance_cost = insurance_cost;}
	
	public String getColor(){return color;}
	public void setColor(String color){this.color = color;}

	public int getRent_counter(){return rent_counter;}
	public void setRent_counter(int rent_counter){this.rent_counter = rent_counter;}
	public void incRent_counter(){this.rent_counter++;}
	
}
