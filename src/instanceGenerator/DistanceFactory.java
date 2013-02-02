package instanceGenerator;

import java.util.ArrayList;
import java.util.Collections;

import cookItBO.CookItBO;
import cookItBO.common.Time;
import cookItBO.common.VehicleType;
import cookItBO.places.Distance;
import cookItBO.places.GPSCoordinates;
import cookItBO.places.Location;

public class DistanceFactory {

	public static void runOn(CookItBO instance) {
		
		ArrayList<Location> list=new ArrayList<Location>();
		list.add(instance.getHq());
		list.addAll(instance.getClientList());
		list.addAll(instance.getDepotList());
		Distance d;
		double gpsDist;
		Time travelTime;
		for(int i=0;i<list.size();i++){
			for(int j=i;j<list.size();j++){
				if(i!=j){
					d=new Distance(list.get(i), list.get(j));
					gpsDist=GPSCoordinates.getDistance(list.get(i).getCoordinates(), list.get(j).getCoordinates());
					for(VehicleType v:VehicleType.values()){
						travelTime=new Time((int)(gpsDist/v.averageSpeed));
						d.addTravelTimeInList(v,travelTime);
					}
					instance.addDistanceInList(d);
				}
			}
		}
	}
}
