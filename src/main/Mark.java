package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Mark {
	protected Student student;
	protected StringProperty result;
	
	public Mark (Student student, String result) {
		this.student = student;
		this.result = new SimpleStringProperty(result);;
	}
	
	public Student getStudent () {
		return student;
	}
	
	public String getResult () {
		return result.get();
	}
	public StringProperty getResultProperty () {
		return result;
	}
}
