package main;

import java.util.ArrayList;

import errors.InstanceNotFound;

public class ProgramCoordinator extends Staff {
	ArrayList<Course> courses;
	
	public ProgramCoordinator (String id, String password, String fName, String lName) {
		super(id, password, fName, lName);
	}
	
	public ProgramCoordinator (String id, String password, String fName, String lName, 
			ArrayList<CourseOffering> offerings, ArrayList<Course> courses) {
		super(id, password, fName, lName, offerings);
		this.courses = courses;
	}
	
	public ArrayList<Course> listCourses () {
		return courses;
	}
	
	public ArrayList<CourseOffering> listClasses () throws Exception {
		
		// Initialise reader and result
		Reader reader = new Reader();
		ArrayList<CourseOffering> classList = null;
		
		// Run reader function
		try {
			classList = reader.LoadClasses(this.id);
		} catch (InstanceNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return classList;
	}
	
	public boolean addCourse (String courseName, String courseID, String desc, 
			Staff coordinator, ArrayList<String> topics) {
		
		// Make course out of passed variables
		Course course = new Course(courseName, courseID, desc, coordinator, topics);
		
		boolean result = true;
		   
		// Instantiate reader and save
		Reader reader = new Reader();
		try {
			reader.SaveCourse(course);
		}
		catch (Exception err) {
			result = false;
		}
		
		return result;
	}
}
