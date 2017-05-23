package main;
import java.util.ArrayList;

public class OverloadPerms {
	private ArrayList<DateTime> semesters = new ArrayList<DateTime>();
	
	public void addSemester (DateTime semester) {
		this.semesters.add(semester);
	}
	
	public ArrayList<DateTime> getSemesters () {
		return semesters;
	}
	
	public boolean checkSemester (DateTime semester) {
		boolean result = false;
		
		for (DateTime s : semesters) {
			if (semester.getCurrentYear().equals(s.getCurrentYear()) && semester.getCurrentSem().equals(s.getCurrentSem()))
				result = true;
		}
		System.out.println(result);
		return result;
	}
}
