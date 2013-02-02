package cookItBO.common;

public enum VehicleType {
	CAR (50),
	SCOOTER(45),
	BIKE(20),
	FOOT(8);
	
	public final double averageSpeed;
	
	private VehicleType(double speed){
		averageSpeed=speed;
	}
}
