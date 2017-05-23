package view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import application.MainApp;
import errors.InstanceNotFound;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import main.Course;
import main.CourseOffering;
import main.Database;
import main.DateTime;
import main.Reader;
import main.Student;
import main.User;

public class StudentPageCourseController {
	@FXML
	private TableView<CourseOffering> table;
	@FXML
	private TableColumn<CourseOffering, String> courseNameColumn;
	@FXML
	private TableColumn<CourseOffering, String> courseIDColumn;
	@FXML
	private Button withdrawButton;
	private String userID;

	public StudentPageCourseController() {}
    
	   
	
    @FXML
    private void initialize() {
    	courseNameColumn.setCellValueFactory(cellData ->cellData.getValue().getCourse().getCourseNameProperty());
    	courseIDColumn.setCellValueFactory(cellData ->cellData.getValue().getCourse().getCourseIDProperty());
    	
			
    }

	public void ListStudentCourses() {
		ObservableList<CourseOffering> studentEnrolledCourses = FXCollections.observableArrayList();
		Reader reader = new Reader();
		User user = null;
		try {
			System.out.println("Loading user: " + userID);
			user = reader.LoadUser(userID);
		} catch (InstanceNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user instanceof Student) {
			Student student = (Student) user;
			try {
				ArrayList<CourseOffering> courses = student.listCourses();
				for (CourseOffering course : courses) {
					studentEnrolledCourses.add(course);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			table.setItems(studentEnrolledCourses);
		}

	}

	public void setUserID(String userID) {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
		ListStudentCourses();
	}
	public void withdraw() throws SQLException {
		Database db = new Database();
		DateTime dt = db.dt;
		if(Integer.parseInt(dt.getCurrentWeek())>3) {
			warningDialog();
		}
		else {
			
		Student student = new Student();
		student.withdraw(userID,table.getSelectionModel().selectedItemProperty().getValue().getOfferID());
		ListStudentCourses();
		}
	}
	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showStudentHomePage();
	}
	public void warningDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cannot withdraw!");
		alert.setHeaderText("You can not withdraw past week 3.");
		alert.setContentText("Please contact staff for more information.");

		alert.showAndWait();
	}
}
