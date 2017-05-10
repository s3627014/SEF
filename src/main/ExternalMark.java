package main;

import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExternalMark extends Mark {
	private StringProperty course;
	private StringProperty institution;
	private StringProperty description;
	private Date startDate;

	public ExternalMark(Student student, String course, String institution, String description,
			Date startDate, String result) {
		super(student, result);
		
		this.course = new SimpleStringProperty(course);
		this.institution = new SimpleStringProperty(institution);
		this.description = new SimpleStringProperty(description);
		this.startDate = (startDate);
	}

	public void setAll (Student student, String course, String institution, String description,
			Date startDate, StringProperty result) {

		this.student = student;
		this.course = new SimpleStringProperty(course);;
		this.institution = new SimpleStringProperty(institution);;
		this.description = new SimpleStringProperty(description);;
		this.startDate = startDate;
		this.result = result;
	}

	public String getCourse() {
		return course.get();
	}

	public String getInstitution() {
		return institution.get();
	}

	public String getDescription() {
		return description.get();
	}
	public StringProperty getCourseProperty() {
		return course;
	}

	public StringProperty getInstitutionProperty() {
		return institution;
	}

	public StringProperty getDescriptionProperty() {
		return description;
	}

	public Date getStartDate() {
		return startDate;
	}
}
