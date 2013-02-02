package optim.metaheuristic;

import java.util.ArrayList;

import optim.localsearch.LocalMove;
import optim.solution.DeliverySolution;

public class SimulatedAnnealing {
	
	//paramètres par défaut
	private static final double T = 10;
	private static final double LAMBDA = 0.99;
	
	private ArrayList<LocalMove> moves;
	private DeliverySolution solution;
	private double temperature;
	private double decreasingrate;
	private double initialcost;
	private double endcost;
	
	public SimulatedAnnealing(DeliverySolution initial){
		this.moves = new ArrayList<LocalMove>();
		this.solution = initial;
		this.initialcost = this.solution.getSolutionCost();
		this.temperature = T;
		this.decreasingrate = LAMBDA;
	}
	
	public void startAnnealingProcess(int timelimit){
		int iteration = 0;
		long t = System.currentTimeMillis();
		while (System.currentTimeMillis()-t<timelimit*1000){
			for (int i=0; i<this.moves.size(); i++){
				if (this.moves.get(i).getCallprobability() >= Math.random()){
					this.moves.get(i).computeLocalMove(this.solution, this.temperature);
				}
			}
			iteration++;
			this.temperature *= this.decreasingrate;
		}
		this.endcost = this.solution.getSolutionCost();
		System.out.println("##################################");
		System.out.println("## SOLUTION COST : "+this.endcost+" (initial : "+this.initialcost+")");
		System.out.println("##################################");
		System.out.println("Changements réalisés pendant le recuit");
		for (int i=0; i<this.moves.size(); i++){
			System.out.println(this.moves.get(i).getStatistics());
		}
		System.out.println("Noeuds non servis : "+this.solution.getUnserved().size());
	}
	
	public void addLocalMove(LocalMove move){
		this.moves.add(move);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
