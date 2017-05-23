package view;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import main.Database;
import main.DateTime;
import main.User;
public class StudentPageHomeController {
	@FXML
	private Button courseButton;

	private String userID;
	MainApp main = new MainApp();

	@FXML
	private void initialize() {
	}

	public void CourseClicked() throws Exception {
		main.showStudentCoursePage();

	}

	public void EnrolClicked() throws Exception {
		Database db = new Database();
		DateTime dt = db.dt;
		if(Integer.parseInt(dt.getCurrentWeek())>2) {
			warningDialog();
		}
		else {
			main.showStudentEnrolPage();
		}

	}

	public void setUserID(String userID) {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
	}

	public void logoutClicked() {
		main.showLoginPage();
	}
	public void warningDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cannot enrol!");
		alert.setHeaderText("You can not enrol past week 2.");
		alert.setContentText("Please contact staff for more information.");

		alert.showAndWait();
	}
}

	