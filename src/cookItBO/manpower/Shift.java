//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Shift.java
//  @ Date : 29/06/2012
//  @ Author : 
//
//



package  cookItBO.manpower;
import cookItBO.common.Day;
import cookItBO.common.Time;


public class Shift {
	private final Day day;
	private final Time startTime;
	private final Time endTime;
	
	public Shift(Day d, Time st,Time et){
		day=d;
		startTime=st;
		endTime=et;
	}

	public Day getDay() {
		return day;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}
	
}
