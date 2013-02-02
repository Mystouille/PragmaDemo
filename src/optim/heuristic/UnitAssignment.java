package optim.heuristic;

import java.util.ArrayList;

import optim.solution.DeliveryUnit;
import cookItBO.CookItBO;
import cookItBO.actives.HQ;
import cookItBO.common.*;
import cookItBO.manpower.*;

public class UnitAssignment {
	
	private DeliveryUnit[] units;
	private Date deliveryDate;
	
	
	
	public UnitAssignment(CookItBO cookit, Date deliveryDate){
		this.deliveryDate = deliveryDate;
		this.units = new DeliveryUnit[cookit.getEmployeeList().size()];
		this.computeAssignment(cookit.getEmployeeList(), cookit.getHq().getVehicleList());
	}
	
	private void computeAssignment(ArrayList<Employee> e, ArrayList<Vehicle> v){
		int i=0;
		int n=0;
		Vehicle vtemp;
		//on ordonne les vehicules pour que le premier ait la meilleure capacité
		while (i<e.size()-1-n && n<e.size()-2){
			if (v.get(i).getCapacity() < v.get(i+1).getCapacity()){
				vtemp = v.get(i+1);
				v.remove(i+1);
				v.add(i, vtemp);
			}
			i++;
			if (i==e.size()-1-n){i=0;n++;}
		}
		//on crée une copie temporaire de la liste des employés
		ArrayList<Employee> tlist = new ArrayList<Employee>();
		for (i=0; i<e.size(); i++){
			tlist.add(e.get(i));
		}
		int j=0;
		//pour chaque véhicule, par ordre croissant dans la liste triée
		int k=0;
		for (i=0; i<v.size(); i++){
			Employee etemp=new Employee("fogel","maclovin");
			//on assigne l'employé disponible qui a la plage horaire la plus large
			while (!tlist.isEmpty() && j<tlist.size()){
				if (tlist.get(j).hasVehicleLicense(v.get(i).getType())){
					if (etemp.getLastName().equals("maclovin")){
						etemp = tlist.get(j);
					}
					else{
						etemp = this.hasBestSchedule(etemp, tlist.get(j), this.deliveryDate);
					}
				}
				j++;
			}
			if (!etemp.getLastName().equals("maclovin")){
				this.units[k] = new DeliveryUnit(etemp, v.get(i));
				this.units[k].setShiftTimes(this.deliveryDate);
				tlist.remove(etemp);
				k++;
			}
			j=0;
		}
	}
	
	//méthode qui renvoie l'employé qui a la plus large plage horaire
	private Employee hasBestSchedule(Employee e1, Employee e2, Date today){
		Employee etemp=null;
		System.out.println(e1.gethift(today.toDay()).getStartTime().toInt());
		if (e1.gethift(today.toDay()).getEndTime().toInt()-
				e1.gethift(today.toDay()).getStartTime().toInt()>=
					e2.gethift(today.toDay()).getEndTime().toInt()-
						e2.gethift(today.toDay()).getStartTime().toInt()){
			etemp = e1;
		}
		else{
			etemp = e2;
		}
		return etemp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

	public DeliveryUnit[] getUnits() {
		return units;
	}

}
