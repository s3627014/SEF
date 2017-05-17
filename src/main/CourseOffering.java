package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CourseOffering {
	StringProperty offerID;
	DateTime semester;
	Course course;
	Staff lecturer;

	public CourseOffering (String offerID) {
		this.offerID = new SimpleStringProperty(offerID);
	}

	public CourseOffering (String offerID, DateTime semester, Course course, Staff lecturer) {
		this.offerID = new SimpleStringProperty(offerID);;
		this.semester = semester;
		this.course = course;
		this.lecturer = lecturer;
	}

	public void setAll (String offerID, DateTime semester, Course course, Staff lecturer) {
		this.offerID = new SimpleStringProperty(offerID);;
		this.semester = semester;
		this.course = course;
		this.lecturer = lecturer;
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
