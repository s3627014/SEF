package main;

public class InternalMark extends Mark {
	Student student;
	CourseOffering offer;
	
	public InternalMark (Student student, CourseOffering offer, String result) {
		super(student, result);
		
		this.offer = offer;
	}
	
	public void setAll (Student student, CourseOffering offer, String result) {		
		this.student = student;
		this.offer = offer;
		this.result = result;
	}
	
	public CourseOffering getOffer () {
		return offer;
	}
}
