package main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import errors.InstanceNotFound;

public class Student extends User {
	OverloadPerms overloadPerms;
	ArrayList<Mark> marks;
	private static ArrayList<CourseOffering> classList;
	public Student () {}
	Reader reader = new Reader();
	public Student (String id, String password, String fName, String lName, OverloadPerms overloadPerms){
		super(id, password, fName, lName);
		this.overloadPerms = overloadPerms;
	}

	public Student (String id, String password, String fName, String lName, OverloadPerms overloadPerms,
				   ArrayList<Mark> marks) {
		super(id, password, fName, lName);
		this.overloadPerms = overloadPerms;
		this.marks = marks;
	}
	
	public ArrayList<CourseOffering> listCourses () throws SQLException {
		
		// Initialise reader and result
		
		// Run reader function
		try {
			classList = reader.LoadClasses(this.id);
		} catch (InstanceNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return classList;
	}
	
	public ArrayList<CourseOffering> listCoursesForSem (int sem, int year) throws SQLException {
		
		// Start list 
		ArrayList<CourseOffering> list = new ArrayList<CourseOffering>();
		ArrayList<CourseOffering> templist= listCourses ();
		// Go through all enrolled classes
		for (CourseOffering c : templist) {
			
			// Check data against year
			if (Integer.parseInt(c.getSemester().getCurrentYear()) == year
					&& Integer.parseInt(c.getSemester().getCurrentSem()) == sem)
				list.add(c);
		}
		
		return list;
	}
	
	public OverloadPerms getOverloadPerms () {
		return overloadPerms;
	}
	
	public ArrayList<Mark> getMarks () {
		return marks;
	}
	
	public void setMarks (ArrayList<Mark> marks) {
		this.marks = marks;
	}
	public void withdraw(String userID, String offerID) throws SQLException {
		Reader reader = new Reader();
		reader.DeleteEnrolment(userID,offerID);
	}
	public ArrayList<CourseOffering> listOfferings() throws InstanceNotFound {
		Reader reader = new Reader();
		return reader.LoadAllOfferings();
	}
	public void enrol(String userID, String offerID) throws SQLException {
		Reader reader = new Reader();
		reader.SaveEnrolment(userID, offerID);
	}
}