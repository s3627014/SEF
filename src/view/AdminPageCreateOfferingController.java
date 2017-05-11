package view;

import application.MainApp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Course;


public class AdminPageCreateOfferingController {
	@FXML
	private TableView<Course> table;
	@FXML
	private TableColumn<Course, String> courseNameColumn;
	@FXML
	private TableColumn<Course, String> courseIDColumn;
	@FXML
	private TableColumn<Course, String> courseDescColumn;
	@FXML
	private TableColumn<Course, String> courseCoordColumn;
	@FXML
	private Button createButton;
	@FXML
	private Button backButton;
	private String userID;

	public AdminPageCreateOfferingController() {}
    
	   
    @FXML
    private void initialize() {
    	courseNameColumn.setCellValueFactory(cellData ->cellData.getValue().getCourseNameProperty());
    	courseIDColumn.setCellValueFactory(cellData ->cellData.getValue().getCourseIDProperty());
    	
    	//Issue here with getDescription() and getCoordinator()
    	//courseDescColumn.setCellValueFactory(cellData ->cellData.getValue().getCourse().getDescription());
    	//courseCoordColumn.setCellValueFactory(cellData ->cellData.getValue().getCourse().getCoordinator());
			
    }

	
	public void createButtonClicked() {
		//MainApp main = new MainApp();
		//main.showStudentHomePage();
	}

	public void setUserID(String userID) {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
	}

	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showAdminHomePage();
	}
}
