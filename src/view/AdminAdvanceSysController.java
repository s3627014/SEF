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
    	//sysTimeLabel.setText(main.Reader.SystemDate());
    	displayTime();
    	
    	//realTimeLabel.setText(main.DateTime.getCurrentTime());
    }
    
    public void displayTime(){
    	sysTimeLabel.setText("Week " + main.DateTime.getCurrentWeek()+ " Semester " + main.DateTime.getCurrentSem()+ ", " + main.DateTime.getCurrentYear());
    }
    
    
    public void plusWeekButtonClicked(){
    	main.DateTime.incrementWeek();
    	displayTime();
    }
    
    public void minusWeekButtonClicked(){
    	main.DateTime.decrementWeek();
    	displayTime();
    }
	
	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showAdminHomePage();
	}
}
