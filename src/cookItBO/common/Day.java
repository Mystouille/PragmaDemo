package cookItBO.common;

public enum Day {
	THURSDAY,FRIDAY,SATURDAY,SUNDAY,MONDAY,TUESDAY,WEDNESDAY;
	
	public static Day getDay(int a){
		switch(a){
		case 0:return THURSDAY ;
		case 1:return FRIDAY;
		case 2:return SATURDAY;
		case 3:return SUNDAY;
		case 4:return MONDAY;
		case 5:return TUESDAY;
		case 6:return WEDNESDAY;
		default: return null;
		}
	}
	
	public static int toInt(Day a){
		switch(a){
		case THURSDAY:return 0 ;
		case FRIDAY:return 1;
		case SATURDAY:return 2;
		case SUNDAY:return 3;
		case MONDAY:return 4;
		case TUESDAY:return 5;
		case WEDNESDAY:return 6;
		default: return -1;
		}
	}
}
