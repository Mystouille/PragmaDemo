import instanceGenerator.InstanceGenerator;
import optim.heuristic.DeliveryBuilder;
import optim.metaheuristic.SimulatedAnnealing;
import cookItBO.CookItBO;
import cookItBO.common.Date;
import gui.CookItFrame;
import optim.localsearch.*;

public final class Launcher {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		InstanceGenerator testinstance = new InstanceGenerator(100, 7, 1, 3, 1,50,50);
//		CookItBO test = testinstance.run();
//		DeliveryBuilder heur = new DeliveryBuilder(test, new Date(0,0,0));
//		SimulatedAnnealing meta = new SimulatedAnnealing(heur.getSolution());
//		meta.addLocalMove(new Local2Opt(1.0));
//		meta.addLocalMove(new LocalDeliverySwap(1.0));
//		meta.addLocalMove(new LocalDeliverySwap(1.0));
//		meta.startAnnealingProcess(10);
		CookItFrame frame=new CookItFrame("data/");
		frame.init();
	}

}
