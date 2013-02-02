package optim.solution;

import java.util.ArrayList;

import cookItBO.CookItBO;
import cookItBO.common.Date;
import cookItBO.schedule.Delivery;
import cookItBO.schedule.DeliveryOperation;
import cookItBO.schedule.DeliverySchedule;
import cookItBO.schedule.DeliveryTarget;


public class DeliverySolution {
	
	private DeliveryRouting[] deliveryRoutes;
	private CookItBO cookit;
	private double solutioncost;
	private ArrayList<DeliveryNode> unserved;
	private Date today;
	
	public DeliverySolution(CookItBO cookit, Date today){
		this.cookit = cookit;
		this.today = today;
		this.deliveryRoutes = new DeliveryRouting[cookit.getEmployeeList().size()];
	}
	
	public void initializeDeliveryRoutes(DeliveryUnit[] units, DeliveryNode hq){
		this.deliveryRoutes = new DeliveryRouting[units.length];
		for (int i=0; i<this.deliveryRoutes.length; i++){
			this.deliveryRoutes[i] = new DeliveryRouting(units[i], units[i].getStart(), this.cookit);
			this.deliveryRoutes[i].setFirstRoute(true);
			this.deliveryRoutes[i].setEndRoute(true);
			this.deliveryRoutes[i].initializeTimeWindows();
			this.deliveryRoutes[i].setDeliveryDuration(DeliverySchedule.CLIENT_DELIVERY_DURATION);
		}
	}
	
	public void computeSolutionCost(){
		this.solutioncost = 0;
		for (int r = 0; r < this.deliveryRoutes.length; r++){
			this.solutioncost += this.deliveryRoutes[r].computeRouteCost();
		}
	}
	
	public void instantiateCookItBo(){
		DeliverySchedule schedule = new DeliverySchedule(this.today);
		Delivery deliv;
		DeliveryTarget target;
		DeliveryOperation op;
		for (int i=0; i<this.deliveryRoutes.length; i++){
			for (int j=0; j<this.getNbRoutesInRoute(i); j++){
				if (this.getDeliveryRouting(i, j).getNodes().size()>=3){
					deliv = new Delivery();
					deliv.setEmployee(this.getDeliveryRouting(i, j).getUnit().getEmployee());
					deliv.setVehicle(this.getDeliveryRouting(i, j).getUnit().getVehicle());
					for (int k=0; k<this.getDeliveryRouting(i, j).getNodes().size(); k++){
						target = new DeliveryTarget(this.getDeliveryRouting(i, j).getNodes().get(k).getBestArrival());
						target.setLocation(this.getDeliveryRouting(i, j).getNodes().get(k).getLocation());
						if (this.getDeliveryRouting(i, j).getNodes().get(k).getHq()){
							op = new DeliveryOperation(null, schedule.DEPOT_TRANSFER_DURATION.toInt());
							target.adddeliveryOperationInList(op);
						}
						else{
							for (int p=0; p<this.getDeliveryRouting(i, j).getNodes().get(k).getCommand().getProductList().size(); p++){
								op = new DeliveryOperation(this.getDeliveryRouting(i, j).getNodes().get(k).getCommand().getProductList().get(p),
										schedule.CLIENT_DELIVERY_DURATION.toInt());
								target.adddeliveryOperationInList(op);
							}
						}
						deliv.addDeliveryTargetInList(target);
					}
					schedule.addDeliveryInList(deliv);
				}
			}
		}
		this.cookit.addDeliveryScheduleInList(schedule);
	}
	
	public int getNbRoutes(){
		return this.deliveryRoutes.length;
	}
	
	public CookItBO getCookit(){
		return this.cookit;
	}
	
	public DeliveryRouting getDeliveryRouting(int unitId){
		return this.deliveryRoutes[unitId];
	}
	
	public DeliveryRouting getDeliveryRouting(int unitId, int nbRoute){
		int temp = 0;
		DeliveryRouting toreturn = this.getDeliveryRouting(unitId);
		while (temp < nbRoute){
			temp++;
			toreturn = toreturn.getNextRoute();
		}
		return toreturn;
	}
	
	public int getNbRoutesInRoute(int id){
		int incr = 1;
		DeliveryRouting r = this.deliveryRoutes[id];
		while (!r.isEndRoute()){
			incr++;
			r = r.getNextRoute();
		}
		return incr;
	}
	
	public double getSolutionCost(){
		return this.solutioncost;
	}
	

	public ArrayList<DeliveryNode> getUnserved() {
		return unserved;
	}

	public void setUnserved(ArrayList<DeliveryNode> unserved) {
		this.unserved = unserved;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
