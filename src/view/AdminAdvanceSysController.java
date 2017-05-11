package view;

import java.sql.SQLException;

import application.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class AdminAdvanceSysController {


	@FXML
    private Label sysTimeLabel;
	
//	@FXML
//    private Label realTimeLabel;
	
	@FXML
	private ComboBox<String> weekComboBox;
	
	private String userID;

	public void setUserID(String userID) {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
	}
	
    @FXML
    private void initialize() throws SQLException {
		ObservableList<String> weeks = FXCollections.observableArrayList();

    	//sysTimeLabel.setText(main.Reader.SystemDate());
    	displayTime();
    	
    	//realTimeLabel.setText(main.DateTime.getCurrentTime());
    	
    	int i = 0;
 		while (i <= 12) {
 			weeks.add(Integer.toString(i));
 			i++;
 		}
    	
//    	for (int i = 1; i <= 12; i++) {
//    		int iString = i;
//    		weeks.add(Integer.toString(iString));
//    	}
    	
    	weekComboBox.getItems().addAll(weeks);
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
