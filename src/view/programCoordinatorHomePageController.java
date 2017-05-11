package view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextInputDialog;
import main.Course;
import main.Database;
import main.Reader;
import main.Staff;
import main.Student;
import main.User;

public class programCoordinatorHomePageController {
	@FXML
	private Button courseButton;

	MainApp main = new MainApp();

	@FXML
	private void initialize() {
	}

	public void CourseClicked() throws Exception {
		main.showProgramCoordinatorCreateCoursePage();
		/*
		 * Reader reader = new Reader(); ArrayList<String> topics = new
		 * ArrayList<String>(); topics.add("topic 1");
		 * 
		 * Staff prgCoordinator = (Staff) reader.LoadUser("e12345"); Course
		 * course = new Course("Test","c12346","123",prgCoordinator,topics);
		 * reader.SaveCourse(course);
		 */

	}

	public void studentActionsClicked() throws Exception {
		main.showStudentHistoryPage();
		/*
		 * Reader reader = new Reader(); ArrayList<String> topics = new
		 * ArrayList<String>(); topics.add("topic 1");
		 * 
		 * Staff prgCoordinator = (Staff) reader.LoadUser("e12345"); Course
		 * course = new Course("Test","c12346","123",prgCoordinator,topics);
		 * reader.SaveCourse(course);
		 */

	}

	public void logoutClicked() {
		main.showLoginPage();
	}

	public void showOverloadDialog() {
		TextInputDialog dialog = new TextInputDialog("s1234");
		dialog.setTitle("Grant overload exemption for");
		dialog.setHeaderText("Please enter student ID.");
		dialog.setContentText("Student ID: ");
		Optional<String> result = dialog.showAndWait();
		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(studentID -> {
			showOverloadSemesterDialog();
		});
	}

	public void showOverloadSemesterDialog(){
		 List<String> choices = new ArrayList<>();
		 LocalDateTime todaysDate = LocalDateTime.now();
		 LocalDateTime s1 = LocalDateTime.of(todaysDate.getYear(), Month.JANUARY, 1, 12, 30);
		 LocalDateTime s2 = LocalDateTime.of(todaysDate.getYear(), Month.JULY, 1, 12, 30);
		 LocalDateTime s3 = LocalDateTime.of(todaysDate.getYear(), Month.JANUARY, 1, 12, 30);
		 if(todaysDate.isBefore(s1)){
			 
			 choices.add("Semester 1 "+todaysDate.getYear());
			 choices.add("Semester 2 "+todaysDate.getYear());
			 choices.add("Semester 1 "+todaysDate.plusYears(1).getYear());
		 }
		 else if(todaysDate.isAfter(s1) && todaysDate.isBefore(s2)){
			 choices.add("Semester 2 "+todaysDate.getYear());
			 choices.add("Semester 1 "+todaysDate.getYear());
			 choices.add("Semester 1 "+todaysDate.plusYears(1).getYear());
		 }
		 else if(todaysDate.isAfter(s2)){
			 choices.add("Semester 1 "+todaysDate.plusYears(1).getYear());
			 choices.add("Semester 2 "+todaysDate.plusYears(1).getYear());
			 choices.add("Semester 1 "+todaysDate.plusYears(2).getYear());
		 }

		 ChoiceDialog<String> dialog = new ChoiceDialog<>("Semester", choices);
		 dialog.setTitle("Semester Choice");
		 dialog.setHeaderText("Available Semester to overload");
		 dialog.setContentText("Select a semester:");

		 // Traditional way to get the response value.
		 Optional<String> result = dialog.showAndWait();
		 // The Java 8 way to get the response value (with lambda expression).
		 if (result.isPresent()){
			    String date = result.get();
			    LocalDateTime selectedDate;
			    int year = Integer.parseInt(date.substring(11,15));
			 
			    if(date.substring(9,10).equals("1")){
			    	selectedDate = LocalDateTime.of(year, Month.JANUARY, 1, 12, 30);
			    	System.out.println(selectedDate);
			    }
			    else{
			    	selectedDate = LocalDateTime.of(year, Month.JULY, 1, 12, 30);
			    	System.out.println(selectedDate);
			    }
			   
			}
	 }
}
