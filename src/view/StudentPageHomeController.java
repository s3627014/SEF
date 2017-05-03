package view;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.Database;
import main.User;

public class StudentPageHomeController {
	@FXML
	private Button courseButton;
	private String userID;
	 @FXML
	    private void initialize() {
	    	
	    }
	 public void CourseClicked() throws Exception{
	    	MainApp main = new MainApp();
	    	main.showStudentCoursePage();
	    	
	    }
	 public void setUserID(String userID) {
			this.userID = userID;
			System.out.println("Setting the id as " + userID);
		}
}
