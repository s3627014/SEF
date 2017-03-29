package main;
import java.util.ArrayList;

public class Student extends User {
	OverloadPerms overloadPerms;
	ArrayList<Mark> marks;

	Student(String id, String password, String fName, String lName, OverloadPerms overloadPerms, 
			ArrayList<Mark> marks) {
		super(id, password, fName, lName);
		this.overloadPerms = overloadPerms;
		this.marks = marks;
	}

}
