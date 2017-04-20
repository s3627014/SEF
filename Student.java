package main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends User {
	OverloadPerms overloadPerms;
	ArrayList<Mark> marks;

	public Student () {}
	
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
	
	public static ResultSet listCourses() throws SQLException {
		User user = new User();
		user = user.getUser();
		System.out.println("User id is: " + user.getUserID());
		Reader reader = new Reader();
		ResultSet offeringList = reader.SearchDB("ASS1_ENROLMENTS", "STUDENT", user.getUserID());
		return offeringList;
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
}