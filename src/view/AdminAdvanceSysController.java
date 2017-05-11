package view;

import java.sql.SQLException;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminAdvanceSysController {


	@FXML
    private Label sysTimeLabel;
	
	@FXML
    private Label realTimeLabel;
	
	private String userID;

	public void setUserID(String userID) {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
	}
	
    @FXML
    private void initialize() throws SQLException {
    	sysTimeLabel.setText(main.Reader.SystemDate());
    	realTimeLabel.setText(main.DateTime.getCurrentTime());
    }

	
	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showAdminHomePage();
	}
}
