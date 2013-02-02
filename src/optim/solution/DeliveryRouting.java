package optim.solution;

import java.util.ArrayList;

import cookItBO.CookItBO;
import cookItBO.common.Day;
import cookItBO.common.Time;
import cookItBO.schedule.DeliverySchedule;

public class DeliveryRouting {	
	
	private CookItBO cookit;
	private boolean firstRoute;
	private boolean endRoute;
	private DeliveryRouting lastRoute;
	private DeliveryRouting nextRoute;
	private ArrayList<DeliveryNode> nodes;
	private DeliveryUnit unit;
	private int remainingCapacity;
	private Time deliveryDuration;
	private Time soonestStart;
	
	public DeliveryRouting(DeliveryUnit unit, Time soonestStart, CookItBO cookit){
		this.cookit = cookit;
		this.unit = unit;
		this.soonestStart = soonestStart;
		this.nodes = new ArrayList<DeliveryNode>();
		DeliveryNode start = new DeliveryNode(this.cookit.getHq());
		start.setBestArrival(soonestStart);
		start.setBestDeparture(this.unit.getEnd());
		start.setHq(true);
		this.nodes.add(start);
		DeliveryNode end = new DeliveryNode(this.cookit.getHq());
		end.setBestArrival(soonestStart);
		end.setBestDeparture(this.unit.getEnd());
		end.setHq(true);
		this.nodes.add(end);
		this.remainingCapacity = this.unit.getVehicle().getCapacity();
	}
	
	public DeliveryRouting getCurrentRoute(){
		if (this.endRoute){
			return this;
		}
		else{
			return this.nextRoute.getCurrentRoute();
		}
	}
	
	public void initializeTimeWindows(){
		this.nodes.get(0).setBestArrival(this.soonestStart);
		this.nodes.get(0).setBestDeparture(this.unit.getEnd());
		this.nodes.get(1).setBestArrival(this.soonestStart);
		this.nodes.get(1).setBestDeparture(this.unit.getEnd());
	}
	
	public void computeDynamicBestArrivals(){
		int temp;
		for (int i=1; i<this.nodes.size(); i++){
			temp = this.nodes.get(i-1).getBestArrival().toInt()+
				this.deliveryDuration.toInt()+this.cookit.getDistance(this.nodes.get(i).getLocation(), 
					this.nodes.get(i-1).getLocation()).getTravelTimeby(this.unit.getVehicle().getType()).toInt();
			if (i == this.nodes.size()-1){
				if (this.nodes.get(i).getBestArrival().toInt() < temp){
					this.nodes.get(i).setBestArrival(new Time(0,0,temp));
				}
			}
			else{
				if (this.nodes.get(i).getCommand().getTimeWindow().getPreferedTime().toInt() >= temp){
					this.nodes.get(i).setBestArrival(this.nodes.get(i).getCommand().getTimeWindow().getPreferedTime());
				}
				else{
					this.nodes.get(i).setBestArrival(new Time(0,0,temp));
				}
			}
		}
		if (!this.endRoute){
			if (this.nextRoute.getNodes().size()>2){
				this.nextRoute.computeDynamicBestArrivals();
			}
		}
	}
	
	public void computeDynamicBestDepartures(){
		int temp;
		for (int i=this.nodes.size()-2; i>=0; i--){
			temp = this.nodes.get(i+1).getBestDeparture().toInt()-this.deliveryDuration.toInt()-this.cookit.getDistance(this.nodes.get(i+1).getLocation(), 
					this.nodes.get(i).getLocation()).getTravelTimeby(this.unit.getVehicle().getType()).toInt();
			if (i == 0){
				if (this.nodes.get(i).getBestDeparture().toInt()>temp){
					this.nodes.get(i).setBestDeparture(new Time(0,0,temp));
				}
			}
			else{
				if (this.nodes.get(i).getCommand().getTimeWindow().getPreferedTime().toInt()+
						this.nodes.get(i).getCommand().getTimeWindow().getWindowWidth().toInt()<= temp){
					this.nodes.get(i).setBestDeparture(new Time(0,0,this.nodes.get(i).getCommand().getTimeWindow().getPreferedTime().toInt()+
						this.nodes.get(i).getCommand().getTimeWindow().getWindowWidth().toInt()));
				}
				else{
					this.nodes.get(i).setBestDeparture(new Time(0,0,temp));
				}
			}
		}
		if (!this.firstRoute){this.lastRoute.computeDynamicBestDepartures();}
	}
	
	public void computeDynamicTimeWindows(){
		this.computeDynamicBestArrivals();
		this.computeDynamicBestDepartures();
	}
	
	public boolean checkInsertionFeasibility(DeliveryNode n1, DeliveryNode n2, DeliveryNode toinsert){
		boolean check = true;
		int bestThisarrival = n1.getBestArrival().toInt()+this.deliveryDuration.toInt()+this.cookit.getDistance(n1.getLocation(), toinsert.getLocation()).getTravelTimeby(this.unit.getVehicle().getType()).toInt();
//		System.out.println(bestThisarrival);
		int bestThisdeparture = n2.getBestDeparture().toInt()-this.deliveryDuration.toInt()-this.cookit.getDistance(n2.getLocation(), toinsert.getLocation()).getTravelTimeby(this.unit.getVehicle().getType()).toInt();
//		System.out.println(bestThisdeparture);
		check &= bestThisarrival+this.deliveryDuration.toInt()<=toinsert.getCommand().getTimeWindow().getPreferedTime().toInt()+toinsert.getCommand().getTimeWindow().getWindowWidth().toInt();
//		System.out.println(check);
		check &= bestThisdeparture-this.deliveryDuration.toInt()>=toinsert.getCommand().getTimeWindow().getPreferedTime().toInt();
//		System.out.println(check);
		check &= (bestThisdeparture>=bestThisarrival+this.deliveryDuration.toInt());
//		System.out.println(check);
		return check;
	}
	
	public boolean checkSwapFeasibility(DeliveryNode n, DeliveryNode toinsert){
		return this.checkInsertionFeasibility(this.nodes.get(this.nodes.indexOf(n)-1), this.nodes.get(this.nodes.indexOf(n)+1), toinsert);
	}
	
	public boolean insertDeliveryNodeToRoute(DeliveryNode toinsert, int index){
		this.nodes.add(index+1, toinsert);
		this.remainingCapacity -= toinsert.getCommand().getProductList().size();
		this.computeDynamicTimeWindows();
		return (this.remainingCapacity == 0);
	}
	
	public void closeRouteAndCreateNext(){
		this.endRoute = false;
		Time next = new Time(0,0,this.nodes.get(this.nodes.size()-1).getBestArrival().toInt()+DeliverySchedule.DEPOT_TRANSFER_DURATION.toInt());
		this.nextRoute = new DeliveryRouting(this.unit, next, this.cookit);
		this.nextRoute.setEndRoute(true);
		this.nextRoute.setLastRoute(this);
		this.nextRoute.setDeliveryDuration(this.deliveryDuration);
		this.nextRoute.initializeTimeWindows();
	}
	
	public double computeRouteCost(){
		double cost = 0;
		for (int i=0; i<this.nodes.size()-1; i++){
			cost += this.cookit.getDistance(this.nodes.get(i).getLocation(), this.nodes.get(i+1).getLocation()).getTravelTimeby(this.unit.getVehicle().getType()).toInt();
		}
		if (!this.endRoute){
			if (this.nextRoute.getNodes().size() > 2){
				cost += this.nextRoute.computeRouteCost();
			}
		}
		return cost;
	}
	
	public void setDeliveryDuration(Time t){
		this.deliveryDuration = t;
	}
	
	public Time getDeliveryDuration(){
		return this.deliveryDuration;
	}
	
	public boolean isFirstRoute() {
		return firstRoute;
	}

	public void setFirstRoute(boolean firstRoute) {
		this.firstRoute = firstRoute;
	}

	public boolean isEndRoute() {
		return endRoute;
	}

	public void setEndRoute(boolean endRoute) {
		this.endRoute = endRoute;
	}

	public DeliveryRouting getLastRoute() {
		return lastRoute;
	}

	public void setLastRoute(DeliveryRouting lastRoute) {
		this.lastRoute = lastRoute;
	}

	public DeliveryRouting getNextRoute() {
		return nextRoute;
	}

	public void setNextRoute(DeliveryRouting nextRoute) {
		this.nextRoute = nextRoute;
	}

	public ArrayList<DeliveryNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<DeliveryNode> nodes) {
		this.nodes = nodes;
	}

	public DeliveryUnit getUnit() {
		return unit;
	}

	public void setUnit(DeliveryUnit unit) {
		this.unit = unit;
	}

	public String toString() {
		String s = "Delivery Route Description"+"\n";
		for (int i=0; i<this.nodes.size(); i++){
			s += this.nodes.get(i).getLocation().getStreet()+"\n";
			s += "Best Arrival Time : "+this.nodes.get(i).getBestArrival().toInt()+"\n";
			s += "Best Departure Time : "+this.nodes.get(i).getBestDeparture().toInt()+"\n";
		}
		return s;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	

}
