package main;

public class CourseOffering {
	private String offerID;
	private DateTime semester;
	private Course course;
	private Staff lecturer;
	
	public CourseOffering (String offerID) {
		this.offerID = offerID;
	}
	
	public CourseOffering (String offerID, DateTime semester, Course course, Staff lecturer) {
		this.offerID = offerID;
		this.semester = semester;
		this.course = course;
		this.lecturer = lecturer;
	}
	
	public void setAll (String offerID, DateTime semester, Course course, Staff lecturer) {
		this.offerID = offerID;
		this.semester = semester;
		this.course = course;
		this.lecturer = lecturer;
	}
	
	public String getOfferID () {
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
		System.out.println("Semester: " + semester.getFormattedDate());
		System.out.println("Course: " + course.getCourseName());
		System.out.println("Lecturer: " + lecturer.getFirstName() + " " + lecturer.getLastName());
	}
}
