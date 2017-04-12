package main;
import java.util.ArrayList;

public class OverloadPerms {
	private ArrayList<DateTime> semesters = new ArrayList<DateTime>();
	
	public void addSemester (DateTime semester) {
		this.semesters.add(semester);
	}
}
