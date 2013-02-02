//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Employee.java
//  @ Date : 29/06/2012
//  @ Author : 
//
//



package  cookItBO.manpower;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.org.apache.bcel.internal.generic.LASTORE;//leu derni� magazziiiiiin

import cookItBO.common.Day;
import cookItBO.common.Vehicle;
import cookItBO.common.VehicleType;


public class Employee {
	private final String firstName;
	private final String lastName;
	private ArrayList<VehicleType> licenceList;
	private HashMap<Day, Shift> shiftList;
	
	
	
	
	public Employee(String fn,String ln){
		firstName=fn;
		lastName=ln;
		licenceList=new ArrayList<VehicleType>();
		shiftList=new HashMap<Day, Shift>();
		
	}

	public void addlicenceInList(VehicleType v){
		licenceList.add(v);
	}
	
	public void setShift(Day d, Shift s){
		shiftList.put(d,s);
	}
	public Shift gethift(Day d){
		return shiftList.get(d);
	}
	
	public ArrayList<VehicleType> getLicenceList() {
		return licenceList;
	}
	

	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}


	
	public boolean hasVehicleLicense(VehicleType v){
		boolean hasIt = false;
		int i=0;
		while (!hasIt && i<this.licenceList.size()){
			hasIt = v.equals(this.licenceList.get(i));
			i++;
		}
		return hasIt;
	}
	
}