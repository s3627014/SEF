package view;

import java.sql.SQLException;
import java.util.ArrayList;

import application.MainApp;
import errors.InstanceNotFound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Admin;
import main.Course;
import main.CourseOffering;
import main.Reader;
import main.User;

public class AdminPageCreateOfferingController {
	@FXML
	private TableView<CourseOffering> table;
	@FXML
	private TableColumn<CourseOffering, String> courseNameColumn;
	@FXML
	private TableColumn<CourseOffering, String> courseIDColumn;
	@FXML
	private TableColumn<CourseOffering, String> courseDescColumn;
	@FXML
	private TableColumn<CourseOffering, String> courseCoordColumn;
	@FXML
	private Button createButton;
	@FXML
	private Button backButton;
	private String userID;

	public AdminPageCreateOfferingController() {}
    
	   
	
    @FXML
    private void initialize() {
    	courseNameColumn.setCellValueFactory(cellData ->cellData.getValue().getCourse().getCourseNameProperty());
    	courseIDColumn.setCellValueFactory(cellData ->cellData.getValue().getCourse().getCourseIDProperty());
    	
    	//Issue here with getDescription() and getCoordinator()
    	//courseDescColumn.setCellValueFactory(cellData ->cellData.getValue().getCourse().getDescription());
    	//courseCoordColumn.setCellValueFactory(cellData ->cellData.getValue().getCourse().getCoordinator());
			
    }

	public void ListStudentCourses() {
		ObservableList<Course> courseNeedingCreating = FXCollections.observableArrayList();
		Reader reader = new Reader();
		User user = null;
		try {
			System.out.println("Loading user: " + userID);
			user = reader.LoadUser(userID);
		} catch (InstanceNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user instanceof Admin) {
			Admin admin = (Admin) user;
			try {
				ArrayList<Course> courses = admin.getAllCourses();
				for (Course course : courses) {
					courseNeedingCreating.add(course);
				}
				/*} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();*/	
			}
			//remove finally when fixed
			finally{
				
			}
			//table.setId(courseNeedingCreating);
	}

	}
	
	public void createButtonClicked() {
		//MainApp main = new MainApp();
		//main.showStudentHomePage();
	}

	public void setUserID(String userID) {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
		ListStudentCourses();
	}

	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showAdminHomePage();
	}
}
