package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import application.MainApp;
import errors.InstanceNotFound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Course;
import main.CourseOffering;
import main.InternalMark;
import main.Mark;
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
	MainApp main = new MainApp();
	Reader reader = new Reader();
	
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
		ArrayList<Course> prereqs = reader.LoadPrereqs("COURSE",
				table.getSelectionModel().selectedItemProperty().getValue().getCourse().getCourseID());
		ArrayList<Mark> marks = reader.LoadMarks("STUDENT", userID);
		ArrayList<InternalMark> InternalMarks = new ArrayList<InternalMark>();
		int i = 0;
		while (i < marks.size()) {
			if (marks.get(i) instanceof InternalMark) {
				InternalMarks.add((InternalMark) marks.get(i));
			}
			i++;
		}
		i =0;
		if(prereqs.size()==0) {
			Student student = new Student();
			student.enrol(userID,table.getSelectionModel().selectedItemProperty().getValue().getOfferID());
			listOfferings();
			return;
		}
		while(i<prereqs.size()) {
			int k=0;
			while(k<InternalMarks.size()) {
				if(prereqs.get(i).getCourseID().equals(InternalMarks.get(k).getOffer().getCourse().getCourseID())) {
					Student student = new Student();

					student.enrol(userID,table.getSelectionModel().selectedItemProperty().getValue().getOfferID());
					listOfferings();
					return;
					
				}
				k++;
			}
			i++;
		}
	
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("PREREQS NOT MET");
		String s ="Prerequistes not met. Would you like to \napply for exemption?";
		alert.setContentText(s);

		Optional<ButtonType> result = alert.showAndWait();

		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			//Do something
		}
		
	}

	public void backButtonClicked() {
		main.showStudentHomePage();
	}

	public void listOfferings() {
		ObservableList<CourseOffering> offerings = FXCollections.observableArrayList();
		Student student = new Student();
		
			try {
				ArrayList<CourseOffering> courses = student.listOfferings();
				ArrayList<CourseOffering> enrolments = reader.LoadClasses(userID);
				offerings.addAll(courses);
				int i=0;
				while(i<courses.size()) {
					int k=0;
					while(k<enrolments.size()) {
						if(courses.get(i).getCourse().getCourseID().equals(enrolments.get(k).getCourse().getCourseID())) {
							offerings.remove(courses.get(i));
						}
						k++;
					}
					i++;
				}
				
			} catch (InstanceNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			table.setItems(offerings);
		

	}

}
