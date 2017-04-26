package main;

public class Mark {
	protected Student student;
	protected String result;
	
	public Mark (Student student, String result) {
		this.student = student;
		this.result = result;
	}
	
	public Student getStudent () {
		return student;
	}
	
	public String getResult () {
		return result;
	}
}
