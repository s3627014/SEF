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

	
	 MainApp main = new MainApp();
	 @FXML
	    private void initialize() {
	    }
	 public void uploadClicked() throws Exception{
		 main.showUploadMarksPage();
	    	
	    }
	 public void studentActionsClicked() throws Exception{
		 main.showStudentHistoryPageLecturer();
	    }
	 public void logoutClicked() {
		 main.showLoginPage();
	 }

}
