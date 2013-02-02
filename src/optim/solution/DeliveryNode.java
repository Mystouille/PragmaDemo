package optim.solution;

import java.util.Date;

import cookItBO.common.Time;
import cookItBO.demand.Command;
import cookItBO.places.Location;

public class DeliveryNode {
	
	private Time bestarrival;
	private Time bestdeparture;
	private Location location;
	private Command command;
	private Time waitingtime;
	private boolean hq;
	
	public DeliveryNode(Command command){
		this.command = command;
		this.location = command.getLocation();
	}

	public DeliveryNode(Location location){
		this.location = location;
	}
	
	public Location getLocation(){
		return this.location;
	}
	
	
	public Time getWaitingtime() {
		return waitingtime;
	}

	public void setWaitingtime(Time waitingtime) {
		this.waitingtime = waitingtime;
	}

	public Time getBestArrival() {
		return this.bestarrival;
	}

	public void setBestArrival(Time arrival) {
		this.bestarrival = arrival;
	}

	public Time getBestDeparture() {
		return this.bestdeparture;
	}

	public void setBestDeparture(Time departure) {
		this.bestdeparture = departure;
	}

	public Command getCommand() {
		return command;
	}
	
	public boolean getHq(){
		return this.hq;
	}
	
	public void setHq(boolean h){
		this.hq = h;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
