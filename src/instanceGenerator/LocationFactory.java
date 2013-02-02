package instanceGenerator;

import java.util.ArrayList;
import java.util.Random;

import cookItBO.CookItBO;
import cookItBO.actives.Depot;
import cookItBO.actives.HQ;
import cookItBO.common.Date;
import cookItBO.common.Time;
import cookItBO.demand.Client;
import cookItBO.demand.Command;
import cookItBO.demand.TimeWindow;
import cookItBO.places.GPSCoordinates;

public class LocationFactory {
	
	
	public static void runOn(CookItBO instance,int nDepot, int nClient,int minX,int maxX,int minY,int maxY,boolean tw,Random r){
		int xRange=maxX-minX;
		int yRange=maxY-minY;
		
		
		//HQ
		HQ hq=new HQ();
		hq.setCoordinates(new GPSCoordinates((int)((xRange)*(0.666)), (int)((yRange)*(0.506)),0));	
		hq.setStreet("HQ Street");
		instance.setHq(hq);
		
		int x,y;
		//Depots
		Depot depot;
		for(int i=0;i<nDepot;i++){
			x=r.nextInt(xRange);
			y=r.nextInt(xRange);

			
			//if it has been generated near the middle
			if((x>(xRange/4)&&x<(xRange*(3/4)))
				&&(y>(yRange/4)&&y<(yRange*(3/4)))){
				//south east triangular half of the center zone
				if(x>y*xRange/yRange){
					//east triangular quarter of the center zone
					if(y>(yRange-yRange*x)/xRange){
						//push them eastward
						x=x+(xRange/4);
					}
					//south triangular quarter of the center zone
					else{
						y=y+(yRange/4);
						//push them southward
					}
				}
				//north west triangular half of the center zone
				else{
					//north triangular quarter of the center zone
					if(y>(yRange-yRange*x)/xRange){
						//push them northward
						y=y-(yRange/4);
					}
					else{
						//push them westhward
						x=x+(xRange/4);
					}
				}
			}
			x=x+minX;
			y=y+minY;
			depot=new Depot(20);
			depot.setCoordinates(new GPSCoordinates(x, y, 0));
			instance.addDepotInList(depot);
		}
		
		//Clients
		Client client;
		Command command;
		TimeWindow timeWindow;
		double time;
		int hour,min;
		Date d=new Date(0,0,0);
		for(int i=0;i<nClient;i++){
			x=minX;
			x=x+r.nextInt(maxX-minX);
			y=minY;
			y=y+r.nextInt(maxY-minY);
			
			client=new Client("Prenom"+i, "Nom"+i);
			client.setCoordinates(new GPSCoordinates(x, y, 0));
			client.setStreet("Rue"+i);
			instance.addClientInList(client);
			command=new Command("Command"+client.getLastName(), client, d);
			command.addProductInList(instance.getMenu(d).getProductList().get(r.nextInt(instance.getMenu(d).getProductList().size())));
			time=r.nextDouble()*2;
			hour=(int)time;
			min=((int)(time*10))-(hour*10);
			
			timeWindow=new TimeWindow(new Time(18+hour,min,00), tw?new Time(0,30,0):new Time(20-18-hour,30-min,0));
			command.setTimeWindow(timeWindow);
			instance.addCommandInList(command);
			
			
		}
		
		
	}
	
	
}
