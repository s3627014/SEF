package view;

import java.util.ArrayList;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import main.Course;
import main.Database;
import main.Reader;
import main.Staff;
import main.User;

public class LecturerHomePageController {
	@FXML
	private Button courseButton;
	private String userID;
	
	 MainApp main = new MainApp();
	 @FXML
	    private void initialize() {
	    }
	 public void uploadClicked() throws Exception{
		 main.showUploadMarksPage(userID);
	    	
	    }
	 public void studentActionsClicked() throws Exception{
		 main.showStudentHistoryPageLecturer();
	    }
	 public void logoutClicked() {
		 main.showLoginPage();
	 }
	 public void setUserID(String userID) {
			this.userID = userID;
			System.out.println("Setting the id as " + userID);
		}

}
