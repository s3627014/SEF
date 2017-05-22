package view;

import java.sql.SQLException;

import javax.swing.JComboBox;

import application.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import main.DateTime;

public class AdminAdvanceSysController {

	@FXML
    private Label sysTimeLabel;
	
	@FXML
	private ComboBox<String> weekComboBox;
	@FXML
	private ComboBox<String> semComboBox;
	@FXML
	private ComboBox<String> yearComboBox;
	
	@FXML
	private Button backButton;
	@FXML
	private Button advanceSysButton;
	@FXML
	private Button minusWeekButton;
	@FXML
	private Button plusWeekButton;
	
	private String userID;
    DateTime dt = main.Database.dt;

	public void setUserID(String userID) {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
	}
	
    @FXML
    private void initialize() throws SQLException {
		ObservableList<String> weeks = FXCollections.observableArrayList();
		ObservableList<String> sem = FXCollections.observableArrayList();
		ObservableList<String> year = FXCollections.observableArrayList();
		

    	displayTime();
    	
    	int i = 0;
 		while (i <= 12) {
 			weeks.add(Integer.toString(i));
 			i++;
 		}
 		
 		int j = 1;
 		while (j <= 2){
 			sem.add(Integer.toString(j));
 			j++;
 		}
 		
 		int k = 2015;
 		while (k <= 2020){
 			year.add(Integer.toString(k));
 			k++;
 		}
 		
    	weekComboBox.getItems().addAll(weeks);
    	semComboBox.getItems().addAll(sem);
    	yearComboBox.getItems().addAll(year);

    	
    }
    
    
    public void displayTime(){
    	sysTimeLabel.setText("Week " + dt.getCurrentWeek()+ " Semester " + dt.getCurrentSem()+ ", " + dt.getCurrentYear());
    }
    
    
    
    public void advanceSysButtonClicked(){
    	
    	//String weeks = weekComboBox.getItems();
    	
        //dt = new DateTime();

    }

    
    public void plusWeekButtonClicked(){
    	dt.incrementWeek();
    	displayTime();
    }
    
    public void minusWeekButtonClicked(){
    	dt.decrementWeek();
    	displayTime();
    }
	
	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showAdminHomePage();
	}
}
