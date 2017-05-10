package main;

import java.util.Date;

import javafx.beans.property.StringProperty;

public class ExternalMark extends Mark {
	private String course;
	private String institution;
	private String description;
	private Date startDate;

	public ExternalMark(Student student, String course, String institution, String description,
			Date startDate, String result) {
		super(student, result);
		
		this.course = (course);
		this.institution = (institution);
		this.description = (description);
		this.startDate = (startDate);
	}

	public void setAll (Student student, String course, String institution, String description,
			Date startDate, StringProperty result) {

		this.student = student;
		this.course = course;
		this.institution = institution;
		this.description = description;
		this.startDate = startDate;
		this.result = result;
	}

	public String getCourse() {
		return course;
	}

	public String getInstitution() {
		return institution;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartDate() {
		return startDate;
	}
}
