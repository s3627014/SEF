package main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends User {
	OverloadPerms overloadPerms;
	ArrayList<Mark> marks;

	public Student () {}
	
	public Student (String id) {
		this.id = id;
	}
	
	public Student (String id, String password, String fName, String lName){
		super(id, password, fName, lName);
	}

	public Student (String id, String password, String fName, String lName, OverloadPerms overloadPerms,
				   ArrayList<Mark> marks) {
		super(id, password, fName, lName);
		this.overloadPerms = overloadPerms;
		this.marks = marks;
	}
	
	public static ResultSet listCourses() throws SQLException {
		Reader reader = new Reader();
		ResultSet courseList = reader.GetTable("ASS1_COURSES");
		return courseList;

	}
}