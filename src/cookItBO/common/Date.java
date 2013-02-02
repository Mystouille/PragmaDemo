package cookItBO.common;

import java.util.GregorianCalendar;

public class Date extends GregorianCalendar{
	
	
	public Date(int year, int month, int day){
		super(year,month,day,0,0,0);
	}
	
	/**return the day number*/
	public int toInt(){
		return (int)(this.time/(1000*3600*24));
	}
	
	public Day toDay(){
		return Day.getDay(toInt()%7);
	}
	
}
