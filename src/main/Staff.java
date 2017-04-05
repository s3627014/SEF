package main;

import java.util.ArrayList;

public class Staff extends User {
	ArrayList<CourseOffering> offerings;
	
	public Staff (String id, String password, String fName, String lName, 
			ArrayList<CourseOffering> offerings) {
		super(id, password, fName, lName);
		this.offerings = offerings;
	}
}
