package main;

import javafx.beans.property.StringProperty;

public class InternalMark extends Mark {
	Student student;
	CourseOffering offer;
	boolean finalised;

	public InternalMark (Student student, CourseOffering offer, String result, boolean finalised) {
		super(student, result);

		this.offer = offer;
		this.finalised = finalised;
	}

	public void setAll (Student student, CourseOffering offer, StringProperty result,
			boolean finalised) {
		this.student = student;
		this.offer = offer;
		this.result = result;
		this.finalised = finalised;
	}

	public CourseOffering getOffer () {
		return offer;
	}

	public boolean getFinalised () {
		return finalised;
	}
}
