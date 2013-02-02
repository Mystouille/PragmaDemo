package cookItBO.places;

import java.util.HashMap;
import cookItBO.common.*;
public class Distance {

	private final Location location1,location2;
	private HashMap<VehicleType,Time> travelTimeList;
	
	public Distance(Location l1,Location l2){
		location1=l1;
		location2=l2;
		travelTimeList=new HashMap<VehicleType, Time>();
	}
	
	public Location getLocation1() {
		return location1;
	}

	public Location getLocation2() {
		return location2;
	}

	public void addTravelTimeInList(VehicleType v, Time t){
		travelTimeList.put(v,t);
	}
	
	public Time getTravelTimeby(VehicleType v){
		return travelTimeList.get(v);
	}
	
}
