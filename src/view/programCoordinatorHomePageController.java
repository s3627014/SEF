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

public class programCoordinatorHomePageController {
	@FXML
	private Button courseButton;

	
	 MainApp main = new MainApp();
	 @FXML
	    private void initialize() {
	    }
	 public void CourseClicked() throws Exception{
		 main.showProgramCoordinatorCreateCoursePage();
		/* Reader reader = new Reader();
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("topic 1");
		 
		 Staff prgCoordinator = (Staff) reader.LoadUser("e12345");
	    	Course course = new Course("Test","c12346","123",prgCoordinator,topics);
	    	reader.SaveCourse(course);
	    	*/
	    	
	    }
	 public void studentActionsClicked() throws Exception{
		 main.showStudentHistoryPage();
		/* Reader reader = new Reader();
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("topic 1");
		 
		 Staff prgCoordinator = (Staff) reader.LoadUser("e12345");
	    	Course course = new Course("Test","c12346","123",prgCoordinator,topics);
	    	reader.SaveCourse(course);
	    	*/
	    	
	    }
	 public void logoutClicked() {
		 main.showLoginPage();
	 }

}
