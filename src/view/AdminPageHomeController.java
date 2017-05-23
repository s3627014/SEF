package view;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import main.Database;
import main.DateTime;

public class AdminPageHomeController {
	@FXML
	private Button createOfferingButton;
	@FXML
	private Button advanceSysButton;
	@FXML
	private Button logoutButton;

	private String userID;
	MainApp main = new MainApp();

	@FXML
	private void initialize() {
	}

	public void createOfferingClicked() throws Exception {
		Database db = new Database();
		DateTime dt = db.dt;
		if (Integer.parseInt(dt.getCurrentWeek()) < 10) {
			main.showCreateOfferingPage();
		}
		else{
			warningDialog();
		}

	}

	public void advanceSysClicked() throws Exception {
		main.showAdvanceSysPage();

	}

	public void logoutClicked() {
		main.showLoginPage();
	}

	public void setUserID(String userID) {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
	}

	public void warningDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cannot create offering");
		alert.setHeaderText("Offerings must be created 2 weeks before next semester.");
		alert.setContentText("Good job.");

		alert.showAndWait();
	}
}
