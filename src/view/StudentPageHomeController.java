package view;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import main.Database;
import main.User;

public class StudentPageHomeController {
	@FXML
	private Button courseButton;
	@FXML
	private Button enrolButton;
	
	private String userID;
	 MainApp main = new MainApp();
	 @FXML
	    private void initialize() {
	    }
	 public void CourseClicked() throws Exception{
	    	main.showStudentCoursePage();
	    	
	    }
	 public void EnrolClicked() throws Exception{
	    	main.showStudentEnrolPage();
	    	
	    }
	 public void setUserID(String userID) {
			this.userID = userID;
			System.out.println("Setting the id as " + userID);
		}
}
