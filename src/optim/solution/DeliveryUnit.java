package optim.solution;

import cookItBO.common.Date;
import cookItBO.common.Time;
import cookItBO.common.Vehicle;
import cookItBO.manpower.Employee;

public class DeliveryUnit {
	
	private Employee employee;
	private Vehicle vehicle;
	private Time start;
	private Time end;
	
	public DeliveryUnit(Employee e, Vehicle v){
		this.employee = e;
		this.vehicle = v;
	}
	
	public void setShiftTimes(Date today){
		this.start = this.employee.gethift(today.toDay()).getStartTime();
		this.end = this.employee.gethift(today.toDay()).getEndTime();
	}

	public Employee getEmployee() {
		return employee;
	}

	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end = end;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setEmployee(Employee e) {
		this.employee = e;
	}

	public void setVehicle(Vehicle v) {
		this.vehicle = v;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
