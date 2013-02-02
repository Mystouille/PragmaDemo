package instanceGenerator;

import java.util.Random;

import cookItBO.CookItBO;
import cookItBO.common.Day;
import cookItBO.common.Time;
import cookItBO.common.Vehicle;
import cookItBO.common.VehicleType;
import cookItBO.manpower.Employee;
import cookItBO.manpower.Shift;

public class EmployeeFactory {

	public static void runOn(CookItBO instance, int nCourier,int nbScooter,int nbBike,int nbCar,Random r){
		
		Employee employee;
		
		Time st=new Time(18,00,00);
		Time et=new Time(20,30,00);
		Day d1=Day.MONDAY;
		Day d2=Day.TUESDAY;
		Day d3=Day.WEDNESDAY;
		Day d4=Day.THURSDAY;
		Day d5=Day.FRIDAY;
		Day d6=Day.SATURDAY;
		Day d7=Day.SUNDAY;
		Shift s1=new Shift(d1,st,et);
		Shift s2=new Shift(d2,st,et);
		Shift s3=new Shift(d3,st,et);
		Shift s4=new Shift(d4,st,et);
		Shift s5=new Shift(d5,st,et);
		Shift s6=new Shift(d6,st,et);
		Shift s7=new Shift(d7,st,et);
		
		for(int i=0;i<nbCar;i++){
			instance.getHq().addVehicleInList(new Vehicle(VehicleType.CAR,"car"+i,10));
		}
		for(int i=0;i<nbScooter;i++){
			instance.getHq().addVehicleInList(new Vehicle(VehicleType.SCOOTER,"scooter"+i,6));
		}
		for(int i=0;i<nbBike;i++){
			instance.getHq().addVehicleInList(new Vehicle(VehicleType.BIKE,"bike"+i,6));
		}
		for(int i=0;i<nCourier;i++){
			instance.getHq().addVehicleInList(new Vehicle(VehicleType.FOOT, "feet"+i,6));
			employee=new Employee("Prénom Emp"+i,"Nom Emp"+i);
			employee.setShift(d1, s1);
			employee.setShift(d2, s2);
			employee.setShift(d3, s3);
			employee.setShift(d4, s4);
			employee.setShift(d5, s5);
			employee.setShift(d6, s6);
			employee.setShift(d7, s7);
			employee.addlicenceInList(instance.getHq().getVehicleList().get(i).getType());
			employee.addlicenceInList(instance.getHq().getVehicleList().get(instance.getHq().getVehicleList().size()-1).getType());
			instance.addEmployeeInList(employee);
		}

		
		
	}

}
