package main;

import java.util.ArrayList;

import errors.InstanceNotFound;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CourseOffering {
	StringProperty offerID;
	DateTime semester;
	Course course;
	Staff lecturer;
	int maxStudents;
	ArrayList<Student> students = new ArrayList<Student>();

	public CourseOffering (String offerID) {
		this.offerID = new SimpleStringProperty(offerID);
	}

	public CourseOffering (String offerID, DateTime semester, Course course, Staff lecturer, int capacity,
			ArrayList<Student> students) {
		this.offerID = new SimpleStringProperty(offerID);
		this.semester = semester;
		this.course = course;
		this.lecturer = lecturer;
		this.maxStudents = capacity;
		this.students = students;
	}

	public void setAll (String offerID, DateTime semester, Course course, Staff lecturer, int capacity,
			ArrayList<Student> students) {
		this.offerID = new SimpleStringProperty(offerID);
		this.semester = semester;
		this.course = course;
		this.lecturer = lecturer;
		this.maxStudents = capacity;
		this.students = students;
	}
	
	public int getMaxStudents () {
		return maxStudents;
	}
	
	public ArrayList<Student> getStudents () {
		return this.students;
	}

	public boolean checkIfFull() {
		
		// Fill students list
		Reader reader = new Reader();
		try {
			this.students = reader.LoadEnrolledStudents(this.offerID.get());
		} catch (InstanceNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// If there are less students than the max
		if (this.students.size() < maxStudents)
			return false;
		else
			return true;
	}
	
	public String getOfferID () {
		return offerID.get();
	}
	public StringProperty getOfferIDProperty () {
		return offerID;
	}
	public DateTime getSemester () {
		return semester;
	}

	public Course getCourse () {
		return course;
	}

	public Staff getLecturer () {
		return lecturer;
	}

	public void print () {
		System.out.println("Course Offering: " + offerID);
		System.out.println("Semester: " + semester.getCurrentSem() + ", " + semester.getCurrentYear());
		System.out.println("Course: " + course.getCourseName());
		System.out.println("Lecturer: " + lecturer.getFirstName() + " " + lecturer.getLastName());
	}
}
