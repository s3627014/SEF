package main;

import java.util.ArrayList;

public class Course {
	private String courseName;
	private String courseID;
	private String desc;
	private Staff coordinator;
	private ArrayList<Course> prereqs;
	private ArrayList<String> topics;
	private ArrayList<Student> exemptStudents;
	
	// CONSTRUCTORS
	public Course (String courseName, String courseID, String desc, Staff coordinator, 
			ArrayList<String> topics) {
		this.courseName = courseName;
		this.courseID = courseID;
		this.desc = desc;
		this.coordinator = coordinator;
		this.topics = topics;
	}
	
	public Course (String courseName, String courseID, String desc, Staff coordinator, 
			ArrayList<Course>prereqs, ArrayList<String> topics) {
		this.courseName = courseName;
		this.courseID = courseID;
		this.desc = desc;
		this.coordinator = coordinator;
		this.prereqs = prereqs;
		this.topics = topics;
	}
	
	public Course (){}
	
	public void setPrereqs (ArrayList<Course> prereqs) {
		this.prereqs = prereqs;
	}

	// GETTERS
	public String getCourseName () {
		return this.courseName;
	}
	
	public String getCourseID () {
		return this.courseID;
	}
	
	public String getDescription () {
		return this.desc;
	}
	
	public Staff getCoordinator () {
		return this.coordinator;
	}
	
	public ArrayList<String> getTopics () {
		return this.topics;
	}
	
	public ArrayList<Course> getPrereqs () {
		return this.prereqs;
	}
	
	// Check functions	
	public boolean checkPrerequisites (ArrayList<Course> courses) {
		// Create result variable
		boolean result = true;
		
		// Go through prereqs and make sure is met
		for (Course prereq : this.prereqs) {
			
			// Match starts as false
			boolean match = false;
			for (Course course : courses) {
				
				// If a match is found set match to true
				if (course.equals(prereq))
					match = true;
			}
			
			// If any prereqs have no match, the prerequisites have not been met
			if (!match) {
				result = false;
			}
		}
		
		return result;
	}
	
	public boolean checkExemption (Student student) {
		// Create result variable
		boolean result = false;
		
		// Go through students
		for (Student exemptStudent : exemptStudents) {
			
			// If the student exists in exempt student they are exempt
			if (exemptStudent.equals(student))
				result = true;
		}
		
		return false;
	}
}
