package cookItBO.common;

import java.util.Date;
import java.util.GregorianCalendar;

public class Time{
	private final int time;
	
	public Time(int hour, int min, int sec){
		time=hour*3600+min*60+sec;
	}
	
	public Time(int sec){
		this.time=sec;
	}
	
	/**return the second number*/
	public int toInt(){
		return (int)(this.time);
	}
	
	public static String pretty(int time){
		int h=time/3600;
		int min=(int)(time/60)-(h*60);
		int sec=time-h*3600-min*60;
		return h+"h"+min+"min"+sec+"sec";
	}

}
