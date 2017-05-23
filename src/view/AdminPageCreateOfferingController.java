package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import application.MainApp;
import errors.InstanceNotFound;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import main.Admin;
import main.Course;
import main.CourseOffering;
import main.DateTime;
import main.Reader;
import main.Staff;
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
    
    public void listOfferings() throws InstanceNotFound {
		ObservableList<Course> offerings = FXCollections.observableArrayList();
		//Student student = new Student();
		ArrayList<Course> courses = reader.LoadAllCourses();

		try {
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
			offerings.addAll(courses);
			table.setItems(offerings);
		

	}
    
    
    
	
	public void createButtonClicked() {
		// Create the custom dialog.
	    Dialog<Pair<String, String>> dialog = new Dialog<>();
	    dialog.setTitle("Offering Date");
	    dialog.setHeaderText("Please enter a semester and year for offering");
	    // Set the button types.
	    ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

	            GridPane gridPane = new GridPane();
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setPadding(new Insets(20, 150, 10, 10));
	    TextField offerID = new TextField();
	    offerID.setPromptText("offerID");
	    TextField from = new TextField();
	    from.setPromptText("Semester");
	    TextField to = new TextField();
	    to.setPromptText("year");
	    TextField lecturer = new TextField();
	    lecturer.setPromptText("Lecturer");
	    gridPane.add(from, 0, 0);
	    gridPane.add(to, 2, 0);
	    gridPane.add(offerID, 0, 2);
	    gridPane.add(lecturer, 2, 2);
	    dialog.getDialogPane().setContent(gridPane);

	    // Request focus on the username field by default.
	    Platform.runLater(() -> from.requestFocus());

	    // Convert the result to a username-password-pair when the login button is clicked.
	    dialog.setResultConverter(dialogButton -> {
	        if (dialogButton == loginButtonType) {
	            return new Pair<>(from.getText(), to.getText());
	        }
	        return null;
	    });

	    Optional<Pair<String, String>> result = dialog.showAndWait();

	    result.ifPresent(pair -> {
	    	int semester = Integer.parseInt(pair.getKey());
	    	int year = Integer.parseInt(pair.getValue());
	    	Staff lecturerID = null;
			try {
				lecturerID = (Staff) reader.LoadUser(lecturer.getText());
			} catch (InstanceNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	DateTime time = new DateTime(0, semester, year);
	    	Admin admin = new Admin();
	    	Course course = table.getSelectionModel().selectedItemProperty().getValue();
	    	admin.addCourseOffering(offerID.getText(), time, course, lecturerID);
	    	
	        System.out.println("From=" + pair.getKey() + ", To=" + pair.getValue());
	    });
	}

	public void setUserID(String userID) throws InstanceNotFound {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
		listOfferings();
		
	}

	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showAdminHomePage();
	}
	public void showOfferingDialog() {
		TextInputDialog dialog = new TextInputDialog("o1234");
		dialog.setTitle("Grant Exemption for " + userID);
		dialog.setHeaderText("Please enter offering to enrol into.");
		dialog.setContentText("Offer ID: ");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
	

		// The Java 8 way to get the response value (with lambda expression).
		Student student = new Student();
		result.ifPresent(offerID -> {
			try {
				student.enrol(userID,offerID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
}


