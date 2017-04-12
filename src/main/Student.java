package main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends User {
	OverloadPerms overloadPerms;
	ArrayList<Mark> marks;

	public Student(String id, String password, String fName, String lName, OverloadPerms overloadPerms,
				   ArrayList<Mark> marks) {
		super(id, password, fName, lName);
		this.overloadPerms = overloadPerms;
		this.marks = marks;
	}

	public Student() {

	}
	public static void listCourses() throws SQLException {
		Reader reader = new Reader();
		ResultSet courseList = reader.GetTable("ASS1_COURSES");
		System.out.println(courseList.getString("COURSENAME"));
		courseList.next();
		System.out.println(courseList.getString("COURSENAME"));
	}
}