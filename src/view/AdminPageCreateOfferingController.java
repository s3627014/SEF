package view;

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
import main.Student;


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
	Reader reader = new Reader();

	public AdminPageCreateOfferingController() {}
    
	   
    @FXML
    private void initialize() {
    	courseNameColumn.setCellValueFactory(cellData ->cellData.getValue().getCourseNameProperty());
    	courseIDColumn.setCellValueFactory(cellData ->cellData.getValue().getCourseIDProperty());
    	
    	//Issue here with getDescription() and getCoordinator()
    	//courseDescColumn.setCellValueFactory(cellData ->cellData.getValue().getCourse().getDescription());
    	//courseCoordColumn.setCellValueFactory(cellData ->cellData.getValue().getCourse().getCoordinator());
    	
    }
    
    public void listOfferings() {
		ObservableList<Course> offerings = FXCollections.observableArrayList();
		//Student student = new Student();

		try {
				ArrayList<Course> courses = reader.LoadAllCourses();
		    	ArrayList<Course> tempCourses = new ArrayList<Course>();
		    	ArrayList<CourseOffering> offering = reader.LoadAllOfferings();
		    	int i=0;
		    	Boolean MatchFound = false;
		    	while(i<courses.size()){
		    		int k=0;
		    		while(k<offering.size()){
		    			if(courses.get(i).getCourseID().equals(offering.get(k).getCourse().getCourseID())){
		    				MatchFound = true;
		    			}
		    			k++;
		    		}
		    		if(!MatchFound){
		    			tempCourses.add(courses.get(i));
		    		}
		    		MatchFound=false;
		    		i++;
		    	}
		    	 courses = tempCourses;
				
			} catch (InstanceNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			table.setItems(offerings);
		

	}
    
    
    
	
	public void createButtonClicked() {
		//MainApp main = new MainApp();
		//main.showStudentHomePage();
	}

	public void setUserID(String userID) {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
		listOfferings();
	}

	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showAdminHomePage();
	}

	
}


