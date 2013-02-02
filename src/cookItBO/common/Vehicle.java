package cookItBO.common;

public class Vehicle {
	private final String name;
	private final int capacity;
	private final VehicleType type;
	
	public Vehicle(VehicleType t, String n,int a){
		capacity=a;
		name=n;
		type=t;
	}


	public String getName() {
		return name;
	}


	public VehicleType getType() {
		return type;
	}


	public int getCapacity() {
		return capacity;
	}
}
