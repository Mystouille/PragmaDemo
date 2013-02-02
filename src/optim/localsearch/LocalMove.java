package optim.localsearch;

import optim.solution.DeliverySolution;

public abstract class LocalMove {
	
	private double callprobability;
	protected int changes;
	
	public LocalMove(double proba){
		this.callprobability = proba;
		this.changes=0;
	}
	
	public abstract String getStatistics();
	
	public abstract void computeLocalMove(DeliverySolution s, double temperature);
	
	protected boolean acceptLocalMove(double increase, double temperature){
		return (Math.random()<= Math.exp(-increase/temperature));
	}

	public double getCallprobability() {
		return callprobability;
	}


	public void setCallprobability(double callprobability) {
		this.callprobability = callprobability;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
