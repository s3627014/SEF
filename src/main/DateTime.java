package main;

public class DateTime {	
	
	static int currentWeekNum = 0;
	static int currentSemesterNum = 1;
	static int currentYearNum = 2017;
	static String weekString = null;
	static String semString = null;
	static String yearString = null;


	public DateTime(int week, int semester, int year)
	{
		this.currentWeekNum=week;
		this.currentSemesterNum=semester;
		this.currentYearNum=year;

	}
	
	public static String incrementWeek (){
		
		currentWeekNum += 1;
		
		if(currentWeekNum >12){
			currentWeekNum =0;
			if(currentSemesterNum == 1){
				currentSemesterNum =2;
			}
			else{
				currentSemesterNum =1;
				currentYearNum +=1;
			}
		}
		weekString = Integer.toString(currentWeekNum);
			
		return weekString;
	}
	
	public static String decrementWeek (){
		
		
		currentWeekNum -= 1;
		
		if(currentWeekNum <0){
			currentWeekNum =12;
			if(currentSemesterNum == 2){
				currentSemesterNum =1;
			}
			else{
				currentSemesterNum =2;
				currentYearNum -=1;
			}
		}
		weekString = Integer.toString(currentWeekNum);
			
		return weekString;
	}
	
	public static String getCurrentWeek(){
		
		weekString = Integer.toString(currentWeekNum);
		return weekString;
		
	}
	
	public static String getCurrentSem(){
		
		semString = Integer.toString(currentSemesterNum);
		return semString;
		
	}
	
	public static String getCurrentYear(){
		
		yearString = Integer.toString(currentYearNum);
		return yearString;
		
	}
}
