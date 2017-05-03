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
import main.CourseOffering;
import main.Reader;
import main.Student;
import main.User;

public class StudentEnrolPageController {
	@FXML
	private TableView<CourseOffering> table;
	@FXML
	private TableColumn<CourseOffering, String> courseNameColumn;
	@FXML
	private TableColumn<CourseOffering, String> courseIDColumn;
	@FXML
	private Button enrolButton;
	@FXML
	private Button backButton;
	private String userID;

	@FXML
	private void initialize() {
		courseNameColumn.setCellValueFactory(cellData -> cellData.getValue().getCourse().getCourseNameProperty());
		courseIDColumn.setCellValueFactory(cellData -> cellData.getValue().getCourse().getCourseIDProperty());
	}

	public void setUserID(String userID) {
		this.userID = userID;
		listOfferings();
	}

	public void enrol() throws SQLException, InstanceNotFound {
		Student student = new Student();
		student.enrol(userID,table.getSelectionModel().selectedItemProperty().getValue().getOfferID());
	}

	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showStudentHomePage();
	}

	public void listOfferings() {
		ObservableList<CourseOffering> offerings = FXCollections.observableArrayList();
		Student student = new Student();
		
			try {
				ArrayList<CourseOffering> courses = student.listOfferings();
				System.out.println(courses.get(0).getOfferID());
				
					offerings.addAll(courses);
				
			} catch (InstanceNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			table.setItems(offerings);
		

	}

}
