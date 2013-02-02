package optim.heuristic;

import java.util.ArrayList;
import java.util.Iterator;

import optim.solution.*;
import cookItBO.CookItBO;
import cookItBO.actives.Depot;
import cookItBO.common.Date;
import cookItBO.common.Vehicle;
import cookItBO.common.VehicleType;
import cookItBO.demand.Command;
import cookItBO.places.Distance;
import cookItBO.places.Location;

public class DeliveryBuilder {
	
	private CookItBO cookit;
	private Date deliveryDate;
	private DeliveryUnit[] teams;
	private DeliverySolution solution;
	private ArrayList<DeliveryNode> unservedNodes;
	
	public DeliveryBuilder(CookItBO cookit, Date today){
		this.cookit = cookit;
		this.unservedNodes = new ArrayList<DeliveryNode>();
		this.deliveryDate = today;
		this.solution = new DeliverySolution(cookit, today);
		this.buildHeuristicSolution();
//		for (int i=0; i<teams.length; i++){
//			System.out.println(" ");
//			System.out.println("Route no "+i);
//			System.out.print(" "+this.solution.getDeliveryRouting(i).toString());
//		}
		System.out.println("Unserved nodes : "+this.unservedNodes.size());
		this.solution.computeSolutionCost();
		System.out.println("Cout de la solution : "+this.solution.getSolutionCost());
	}
	
	public DeliverySolution getSolution(){
		return this.solution;
	}
	
	private void buildHeuristicSolution(){
		UnitAssignment assign = new UnitAssignment(this.cookit, this.deliveryDate);
		this.teams = assign.getUnits();
//		for (int i=0; i<this.teams.length; i++){
//			System.out.println("Team "+i);
//			VehicleType type=teams[i].getVehicle().getType();
//			System.out.println("Employee : "+teams[i].getEmployee().hasVehicleLicense(teams[i].getVehicle().getType()));
//			System.out.println("Vehicle : "+teams[i].getVehicle().getName()+" "+teams[i].getVehicle().getType().averageSpeed);
//			System.out.println("Début : "+teams[i].getStart().toInt());
//			System.out.println("Fin : "+teams[i].getEnd().toInt());
//		}
		ArrayList<DeliveryNode> nodes = this.createNodeLists();
		int idx = 0;
		DeliveryNode hq = new DeliveryNode(this.cookit.getHq());
		hq.setHq(true);
		this.solution.initializeDeliveryRoutes(this.teams, hq);
		boolean check = false;
		boolean problem;
		int checkcount = 0;
		while (!nodes.isEmpty() && !check){
//			System.out.println("Taille de la liste de nodes : "+nodes.size());
			problem = this.insertCheapestNode(nodes, this.solution.getDeliveryRouting(idx).getCurrentRoute());
			if (problem){checkcount++;}
			idx++;
			if (idx == this.teams.length){
				idx=0;
				check = (checkcount == this.teams.length);
				checkcount = 0;
			}
		}
		// A PRIORI INUTILE SI DEJA BLOQUE 
//		check = false;
//		while (!nodes.isEmpty() && !check){
//			System.out.println("Taille de la liste de nodes : "+nodes.size()+" lol");
//			problem = this.insertAndSwapCheapestNode(nodes, this.solution.getDeliveryRouting(idx));
//			if (problem){checkcount++;}
//			idx++;
//			if (idx == this.teams.length){
//				idx=0;
//				check = (checkcount == this.teams.length);
//				checkcount = 0;
//			}
//		}
		this.unservedNodes = nodes;
		this.solution.setUnserved(this.unservedNodes);
	}
	
	private boolean insertCheapestNode(ArrayList<DeliveryNode> nodes, DeliveryRouting route){
		DeliveryNode n1;
		DeliveryNode n2;
		DeliveryNode bestinsert = null;
		DeliveryNode tempnode = null;
		int bestid = -1;
		double insertcost = Integer.MAX_VALUE;
		double tempcost;
		Iterator<DeliveryNode> iterlist;
		boolean endroute = false;
		for (int i=0; i<route.getCurrentRoute().getNodes().size()-1; i++){
			n1 = route.getCurrentRoute().getNodes().get(i);
			n2 = route.getCurrentRoute().getNodes().get(i+1);
			iterlist = nodes.iterator();
			while (iterlist.hasNext()){
				tempnode = iterlist.next();
				tempcost = this.computeInsertCost(n1, n2, tempnode, route.getUnit().getVehicle());
				if (tempcost < insertcost && route.checkInsertionFeasibility(n1, n2, tempnode)){
					bestinsert = tempnode;
					bestid = route.getNodes().indexOf(n1);
					insertcost = tempcost;
				}
			}
		}
		if (bestid != -1){
			endroute = route.insertDeliveryNodeToRoute(bestinsert, bestid);
			nodes.remove(bestinsert);
		}
		if (endroute){
			route.closeRouteAndCreateNext();//A CORRIGER
		}
		return (bestid == -1);
	}
	
	private boolean insertAndSwapCheapestNode(ArrayList<DeliveryNode> nodes, DeliveryRouting route){
		DeliveryNode n1;
		DeliveryNode n2;
		DeliveryNode bestswap = null;
		int bestlastid = -1;
		int templast = -1;
		boolean swappable = false;
		DeliveryNode tempnode = null;
		int bestid = -1;
		double insertcost = Integer.MAX_VALUE;
		double tempcost;
		double subcost;
		Iterator<DeliveryNode> iterlist;
		if (!route.isEndRoute()){
			for (int i=0; i<route.getNodes().size()-2; i++){
				System.out.println("on essaie d'insérer en "+i);
				n1 = route.getNodes().get(i);
				n2 = route.getNodes().get(i+2);
				iterlist = nodes.iterator();
				while (iterlist.hasNext()){
					tempnode = iterlist.next();
					System.out.println("avec le swap du noeud "+nodes.indexOf(tempnode));
					if (route.checkInsertionFeasibility(n1, n2, tempnode)){
						System.out.println("insérable");
						tempcost = this.computeSwapCost(route, route.getNodes().get(i+1), tempnode, route.getUnit().getVehicle());
						System.out.println("tempcost : "+tempcost);
						subcost = Integer.MAX_VALUE;
						templast = -1;
						for (int j=0; j<route.getCurrentRoute().getNodes().size()-1; j++){
							swappable = route.getCurrentRoute().checkInsertionFeasibility(route.getCurrentRoute().getNodes().get(j), 
									route.getCurrentRoute().getNodes().get(j+1), route.getNodes().get(i+1));
							System.out.println("swappable : "+swappable);
							if (swappable && this.computeInsertCost(route.getCurrentRoute().getNodes().get(j), 
									route.getCurrentRoute().getNodes().get(j+1), route.getNodes().get(i), route.getUnit().getVehicle())<subcost){
								templast = i;
								subcost = this.computeInsertCost(route.getCurrentRoute().getNodes().get(j), 
										route.getCurrentRoute().getNodes().get(j+1), route.getNodes().get(i), route.getUnit().getVehicle());
							}
						}
						if (templast != -1 && tempcost+subcost<insertcost){
							bestswap = tempnode;
							bestid = i;
							insertcost = tempcost+subcost;
							bestlastid = templast;
						}
					}
				}
			}
		}
		else{
			return true;
		}
		if (bestid != -1){
			n1 = route.getNodes().get(bestid);
			route.getNodes().remove(n1);
			route.insertDeliveryNodeToRoute(bestswap, bestid);
			nodes.remove(bestswap);
			if (route.getCurrentRoute().insertDeliveryNodeToRoute(n1, bestlastid)){
				route.getCurrentRoute().closeRouteAndCreateNext();
			}
			return false;
		}
		else{
			return this.insertAndSwapCheapestNode(nodes, route.getNextRoute());
		}
		
	}
	
	private double computeInsertCost(DeliveryNode n1, DeliveryNode n2, DeliveryNode inserted, Vehicle v){
		double cost = 0;
		if (!n1.getLocation().equals(n2.getLocation())){
			cost = -this.cookit.getDistance(n1.getLocation(), n2.getLocation()).getTravelTimeby(v.getType()).toInt();
		}
		cost += this.cookit.getDistance(n1.getLocation(), inserted.getLocation()).getTravelTimeby(v.getType()).toInt();
		cost += this.cookit.getDistance(inserted.getLocation(), n2.getLocation()).getTravelTimeby(v.getType()).toInt();
		return cost;
	}
	
	private double computeSwapCost(DeliveryRouting route, DeliveryNode n, DeliveryNode swapped, Vehicle v){
		double cost = 0;
		if (!n.getHq()){
			cost += this.cookit.getDistance(route.getNodes().get(route.getNodes().indexOf(n)-1).getLocation(), swapped.getLocation()).getTravelTimeby(v.getType()).toInt();
			cost += this.cookit.getDistance(route.getNodes().get(route.getNodes().indexOf(n)+1).getLocation(), swapped.getLocation()).getTravelTimeby(v.getType()).toInt();
			cost -= this.cookit.getDistance(route.getNodes().get(route.getNodes().indexOf(n)-1).getLocation(), n.getLocation()).getTravelTimeby(v.getType()).toInt();
			cost -= this.cookit.getDistance(route.getNodes().get(route.getNodes().indexOf(n)+1).getLocation(), n.getLocation()).getTravelTimeby(v.getType()).toInt();
		}
		return cost;
	}
	
	private ArrayList<DeliveryNode> createNodeLists(){
		ArrayList<DeliveryNode> nodes = new ArrayList<DeliveryNode>();
		Iterator<Command> itercom = this.cookit.getCommandList().iterator();
		Command ctemp;
		while (itercom.hasNext()){
			ctemp = itercom.next();
			if (ctemp.getDate().toInt() == this.deliveryDate.toInt()){
				nodes.add(new DeliveryNode(ctemp));
			}
		}
		return nodes;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
