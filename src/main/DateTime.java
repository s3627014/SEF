package main;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;

public class DateTime {
	
 	private long advance;
	private static long time;
	
	public DateTime()
	{
		time = System.currentTimeMillis();
	}
	
	public DateTime(int setClockForwardInDays)
	{
		advance = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
		time = System.currentTimeMillis() + advance;
	}
	
	public DateTime(int day, int month, int year)
	{
		setDate(day, month, year);
	}
	
	public static long getTime()
	{
		return time;
	}
	
	public String toString()
	{
		long currentTime = getTime();
		Date gct = new Date(currentTime);
		return gct.toString();
	}
	
	public static String getCurrentTime()
	{
		Date date = new Date(System.currentTimeMillis());  // returns current Date/Time
		return date.toString();
	}
	
	public Date getDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		long currentTime = getTime();
		Date gct = new Date(currentTime);
		
		return gct;
	}
	
	public static String getFormattedDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		long currentTime = getTime();
		Date gct = new Date(currentTime);
		
		return sdf.format(gct);
	}
	
	// returns difference in days
	public static int diffDays(DateTime endDate, DateTime startDate)
	{
		final long HOURS_IN_DAY = 24L;
		final int MINUTES_IN_HOUR = 60;
		final int SECONDS_IN_MINUTES = 60;
		final int MILLISECONDS_IN_SECOND = 1000;
		long convertToDays = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTES * MILLISECONDS_IN_SECOND;
		long hirePeriod = endDate.getTime() - startDate.getTime();
		return (int) (1 + (hirePeriod) / (convertToDays)) -1;
	}
	
	private void setDate(int day, int month, int year)
	{
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0);   

		java.util.Date date = calendar.getTime();

		time = date.getTime();
	}
	
	// Advances date/time by specified days, hours and mins for testing purposes
	public void setAdvance(int days, int hours, int mins)
	{
		advance = ((days * 24L + hours) * 60L) * 60000L;
	}
	
	

	static int currentWeekNum = 0;
	static int currentSemesterNum = 1;
	static int currentYearNum = 2017;
	static String weekString = null;
	static String semString = null;
	static String yearString = null;
	
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
