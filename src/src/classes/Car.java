package classes;

public class Car extends Vehicle {
	
	private int passanger_number;
	private String brand;
	private String model;
	
	public Car(String _type, int _vehicle_id, float _rent_cost, float _vehicle_range, float _insurance_cost, String _color,
			int _rent_counter, int _passanger_number, String _brand, String _model)
	{
		super(_type, _vehicle_id, _rent_cost, _vehicle_range, _insurance_cost, _color, _rent_counter);
		this.passanger_number = _passanger_number;
		this.brand = _brand;
		this.model = _model;
	}
	
	public String toString()
	{
		return super.toString() + ", {"
				+ passanger_number + ", "
				+ brand + ", "
				+ model
				+ "}";
	}
	
	public int getPassanger_number(){return passanger_number;}
	public void setPassanger_number(int passanger_number){this.passanger_number = passanger_number;}
	
	public String getBrand(){return brand;}
	public void setBrand(String brand){this.brand = brand;}
	
	public String getModel(){return model;}
	public void setModel(String model){this.model = model;}
}
