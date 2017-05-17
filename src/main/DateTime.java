package main;

public class DateTime {

	int currentWeekNum = 0;
	int currentSemesterNum = 1;
	int currentYearNum = 2017;
	String weekString = null;
	String semString = null;
	String yearString = null;


	public DateTime(int week, int semester, int year)
	{
		this.currentWeekNum=week;
		this.currentSemesterNum=semester;
		this.currentYearNum=year;

	}

	public String incrementWeek (){

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

	public String decrementWeek (){


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

	public String getCurrentWeek(){

		weekString = Integer.toString(currentWeekNum);
		return weekString;

	}

	public String getCurrentSem(){

		semString = Integer.toString(currentSemesterNum);
		return semString;

	}

	public String getCurrentYear(){

		yearString = Integer.toString(currentYearNum);
		return yearString;

	}
}
