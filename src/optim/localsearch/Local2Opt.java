package optim.localsearch;

import java.util.ArrayList;

import cookItBO.common.Time;

import optim.solution.DeliveryNode;
import optim.solution.DeliveryRouting;
import optim.solution.DeliverySolution;
import optim.solution.DeliveryUnit;

public class Local2Opt extends LocalMove{

	public Local2Opt(double proba) {
		super(proba);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void computeLocalMove(DeliverySolution s, double temperature) {
		int r = (int)(Math.random()*s.getNbRoutes());
		int nbr = (int)(Math.random()*s.getNbRoutesInRoute(r));
		DeliveryRouting route = s.getDeliveryRouting(r, nbr);
		if (route.getNodes().size() >= 4){
			int i = 1+(int)Math.round((route.getNodes().size()-4)*Math.random());
			int j = i+1+(int)Math.round((route.getNodes().size()-3-i)*Math.random());
			double cost = -s.getCookit().getDistance(route.getNodes().get(i).getLocation(), 
					route.getNodes().get(i-1).getLocation()).getTravelTimeby(route.getUnit().getVehicle().getType()).toInt();
			cost -= s.getCookit().getDistance(route.getNodes().get(j).getLocation(), 
					route.getNodes().get(j+1).getLocation()).getTravelTimeby(route.getUnit().getVehicle().getType()).toInt();
			cost += s.getCookit().getDistance(route.getNodes().get(i-1).getLocation(), 
					route.getNodes().get(j).getLocation()).getTravelTimeby(route.getUnit().getVehicle().getType()).toInt();
			cost += s.getCookit().getDistance(route.getNodes().get(i).getLocation(), 
					route.getNodes().get(j+1).getLocation()).getTravelTimeby(route.getUnit().getVehicle().getType()).toInt();
			if (this.acceptLocalMove(cost, temperature)){
				ArrayList<DeliveryNode> temp = new ArrayList<DeliveryNode>();
				temp.add(route.getNodes().get(i-1));
				for (int k = j; k >= i; k--){
					temp.add(route.getNodes().get(k));
				}
				temp.add(route.getNodes().get(j+1));
				if (this.checkSwapFeasibility(temp, route.getDeliveryDuration(), s, route.getUnit())){
					int id=1;
					for (int k=i; k<=j; k++){
						route.getNodes().remove(k);
						route.getNodes().add(k, temp.get(id));
						id++;
					}
					route.computeDynamicTimeWindows();
					s.computeSolutionCost();
					if (cost!=0){this.changes++;}
//					System.out.println("Solution cost after 2Opt : "+s.getSolutionCost());
				}
			}
		}
	}
	
	private boolean checkSwapFeasibility(ArrayList<DeliveryNode> tempnodes, Time delivery, DeliverySolution s, DeliveryUnit u){
		boolean ok = true;
		int[] arr = new int[tempnodes.size()];
		int[] dep = new int[tempnodes.size()];
		arr[0] = tempnodes.get(0).getBestArrival().toInt();
		dep[dep.length-1] = tempnodes.get(tempnodes.size()-1).getBestDeparture().toInt();
		int b;
		for (int i=1; i<arr.length; i++){
			if (tempnodes.get(i).getHq()){
				b = tempnodes.get(i).getBestArrival().toInt();
			}
			else{
				b = tempnodes.get(i).getCommand().getTimeWindow().getPreferedTime().toInt();
			}
			arr[i] = Math.max(arr[i-1]+delivery.toInt()+s.getCookit().getDistance(tempnodes.get(i-1).getLocation(),
					tempnodes.get(i).getLocation()).getTravelTimeby(u.getVehicle().getType()).toInt(), b);
		}
		for (int i=dep.length-2; i>=0; i--){
			if (tempnodes.get(i).getHq()){
				b = tempnodes.get(i).getBestDeparture().toInt();
			}
			else{
				b = tempnodes.get(i).getCommand().getTimeWindow().getPreferedTime().toInt()+tempnodes.get(i).getCommand().getTimeWindow().getWindowWidth().toInt();
			}
			dep[i] = Math.min(dep[i+1]-delivery.toInt()-s.getCookit().getDistance(tempnodes.get(i+1).getLocation(),
					tempnodes.get(i).getLocation()).getTravelTimeby(u.getVehicle().getType()).toInt(), b);
		}
		int i=0;
		while (ok && i<tempnodes.size()){
			if (!tempnodes.get(i).getHq()){
				ok &= (arr[i]+delivery.toInt() <= tempnodes.get(i).getCommand().getTimeWindow().getPreferedTime().toInt()
						+tempnodes.get(i).getCommand().getTimeWindow().getWindowWidth().toInt());
				ok &= (dep[i]-delivery.toInt() >= tempnodes.get(i).getCommand().getTimeWindow().getPreferedTime().toInt());
				ok &= (dep[i]-arr[i] >= delivery.toInt());
			}
			i++;
		}
		return ok;
	}

	@Override
	public String getStatistics() {
		return "Local 2Opt swaps made : "+this.changes;
	}
	

}
