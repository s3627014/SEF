package view;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminPageHomeController {
	@FXML
	private Button createOfferingButton;
	@FXML
	private Button advanceSysButton;
	
	private String userID;
	 MainApp main = new MainApp();
	 @FXML
	    private void initialize() {
	    }
	 public void createOfferingClicked() throws Exception{
	    	main.showCreateOfferingPage();
	    	
	    }
	 public void advanceSysClicked() throws Exception{
	    	main.showAdvanceSysPage();
	    	
	    }
	 public void setUserID(String userID) {
			this.userID = userID;
			System.out.println("Setting the id as " + userID);
		}
}
