package main;

import java.util.ArrayList;

public class ProgramCoordinator extends Staff {
	ArrayList<Course> courses;
	
	public ProgramCoordinator (String id, String password, String fName, String lName, 
			ArrayList<CourseOffering> offerings, ArrayList<Course> courses) {
		super(id, password, fName, lName, offerings);
		this.courses = courses;
	}
}
