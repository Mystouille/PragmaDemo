package optim.localsearch;

import cookItBO.schedule.DeliverySchedule;
import optim.solution.DeliveryNode;
import optim.solution.DeliveryRouting;
import optim.solution.DeliverySolution;

public class LocalDeliverySwap extends LocalMove{

	public LocalDeliverySwap(double proba) {
		super(proba);
	}

	public void computeLocalMove(DeliverySolution s, double temperature) {
			int i1 = (int)(Math.random()*s.getNbRoutes());
			int i2 = (int)(Math.random()*s.getNbRoutes());
			int j1 = (int)(Math.random()*s.getNbRoutesInRoute(i1));
			int j2 = (int)(Math.random()*s.getNbRoutesInRoute(i2));
			if (s.getDeliveryRouting(i1, j1).getNodes().size()==2){
				j1--;
			}
			if (s.getDeliveryRouting(i2, j2).getNodes().size()==2){
				j2--;
			}
			if (i1==i2 && j1==j2){
				this.computeLocalMove(s, temperature);
			}
			else{
				DeliveryRouting r1 = s.getDeliveryRouting(i1, j1);
				DeliveryRouting r2 = s.getDeliveryRouting(i2, j2);
				int k1 = 1+(int)(Math.random()*(r1.getNodes().size()-3));
				int k2 = 1+(int)(Math.random()*(r2.getNodes().size()-3));
				double cost = this.computeSwapCost(s, r1, k1, r2, k2);
				if (this.acceptLocalMove(cost, temperature) && this.checkSwapFeasibility(s, r1, k1, r2, k2, DeliverySchedule.CLIENT_DELIVERY_DURATION.toInt())){
					DeliveryNode n1 = r1.getNodes().get(k1);
					DeliveryNode n2 = r2.getNodes().get(k2);
					r1.getNodes().remove(k1);
					r1.getNodes().add(k1, n2);
					r2.getNodes().remove(k2);
					r2.getNodes().add(k2, n1);
					r1.computeDynamicTimeWindows();
					r2.computeDynamicTimeWindows();
					s.computeSolutionCost();
					this.changes++;
//					System.out.println("Solution cost after swap : "+s.getSolutionCost());
				}
			}
		
	}
	
	private boolean checkSwapFeasibility(DeliverySolution s, DeliveryRouting r1, int k1, DeliveryRouting r2, int k2, int delivery){
		boolean ok = true;
		int bestarr1 = r2.getNodes().get(k2-1).getBestArrival().toInt()+delivery+
			s.getCookit().getDistance(r2.getNodes().get(k2-1).getLocation(), r1.getNodes().get(k1).getLocation()).getTravelTimeby(r2.getUnit().getVehicle().getType()).toInt();
		int bestarr2 = r1.getNodes().get(k1-1).getBestArrival().toInt()+delivery+
			s.getCookit().getDistance(r1.getNodes().get(k1-1).getLocation(), r2.getNodes().get(k2).getLocation()).getTravelTimeby(r1.getUnit().getVehicle().getType()).toInt();
		int bestdep1 = r2.getNodes().get(k2+1).getBestDeparture().toInt()-delivery-
			s.getCookit().getDistance(r2.getNodes().get(k2+1).getLocation(), r1.getNodes().get(k1).getLocation()).getTravelTimeby(r2.getUnit().getVehicle().getType()).toInt();
		int bestdep2 = r1.getNodes().get(k1+1).getBestArrival().toInt()-delivery-
			s.getCookit().getDistance(r1.getNodes().get(k1+1).getLocation(), r2.getNodes().get(k2).getLocation()).getTravelTimeby(r1.getUnit().getVehicle().getType()).toInt();
		ok &= bestarr1<=r1.getNodes().get(k1).getCommand().getTimeWindow().getPreferedTime().toInt()+r1.getNodes().get(k1).getCommand().getTimeWindow().getWindowWidth().toInt();
		ok &= bestarr2<=r2.getNodes().get(k2).getCommand().getTimeWindow().getPreferedTime().toInt()+r2.getNodes().get(k2).getCommand().getTimeWindow().getWindowWidth().toInt();
		ok &= bestdep1-delivery >= r1.getNodes().get(k1).getCommand().getTimeWindow().getPreferedTime().toInt();
		ok &= bestdep2-delivery >= r2.getNodes().get(k2).getCommand().getTimeWindow().getPreferedTime().toInt();
		ok &= bestdep1-bestarr1 >= delivery;
		ok &= bestdep2-bestarr2 >= delivery;
		return ok;
	}
	
	private double computeSwapCost(DeliverySolution s, DeliveryRouting r1, int k1, DeliveryRouting r2, int k2){
		double cost = -s.getCookit().getDistance(r1.getNodes().get(k1-1).getLocation(), 
				r1.getNodes().get(k1).getLocation()).getTravelTimeby(r1.getUnit().getVehicle().getType()).toInt();
		cost -= s.getCookit().getDistance(r1.getNodes().get(k1).getLocation(), 
				r1.getNodes().get(k1+1).getLocation()).getTravelTimeby(r1.getUnit().getVehicle().getType()).toInt();
		cost -= s.getCookit().getDistance(r2.getNodes().get(k2-1).getLocation(), 
				r2.getNodes().get(k2).getLocation()).getTravelTimeby(r2.getUnit().getVehicle().getType()).toInt();
		cost -= s.getCookit().getDistance(r2.getNodes().get(k2).getLocation(), 
				r2.getNodes().get(k2+1).getLocation()).getTravelTimeby(r2.getUnit().getVehicle().getType()).toInt();
		cost += s.getCookit().getDistance(r1.getNodes().get(k1-1).getLocation(), 
				r2.getNodes().get(k2).getLocation()).getTravelTimeby(r1.getUnit().getVehicle().getType()).toInt();
		cost += s.getCookit().getDistance(r1.getNodes().get(k1+1).getLocation(), 
				r2.getNodes().get(k2).getLocation()).getTravelTimeby(r1.getUnit().getVehicle().getType()).toInt();
		cost += s.getCookit().getDistance(r2.getNodes().get(k2-1).getLocation(), 
				r1.getNodes().get(k1).getLocation()).getTravelTimeby(r2.getUnit().getVehicle().getType()).toInt();
		cost += s.getCookit().getDistance(r2.getNodes().get(k2+1).getLocation(), 
				r1.getNodes().get(k1).getLocation()).getTravelTimeby(r2.getUnit().getVehicle().getType()).toInt();
		return cost;
	}
	
	
	public static void main(String[] args) {


	}

	@Override
	public String getStatistics() {
		return "Local Delivery Swaps made : "+this.changes;
	}


	

}
