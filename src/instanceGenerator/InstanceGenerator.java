package instanceGenerator;

import java.util.Random;

import cookItBO.CookItBO;

public class InstanceGenerator {
	public final int nClient;
	public final int nCourier;
	public final int nDepot;
	public final int nRecipe;
	public final int nScooter;
	public final int nBike;
	public final int nCar;
	public static final int minXCoord=0;
	public static final int minYCoord=0;
	public static final int maxXCoord=1000;
	public static final int maxYCoord=1000;
	public final boolean tw=false;
	
	
	public InstanceGenerator(int nbClient, int nbCourier,int nbDepot, int nbRecipe,int nbScooter,int nbBike,int nbCar){
		nClient=nbClient;
		nCourier=nbCourier;
		nDepot=nbDepot;
		nRecipe=nbRecipe;
		nScooter=nbScooter;
		nBike=nbBike;
		nCar=nbCar;
	}
	
	public CookItBO run(){
		Random r=new Random(1234567890);
		CookItBO instance=new CookItBO();
		MenuFactory.runOn(instance, nRecipe, r);
		LocationFactory.runOn(instance,nDepot,nClient,minXCoord,maxXCoord,minYCoord,maxYCoord,tw,r);
		EmployeeFactory.runOn(instance, nCourier, nScooter, nBike,nCar,r);
		DistanceFactory.runOn(instance);
		//333,253;
		return instance;
	}

}
