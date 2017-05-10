package view;

import java.sql.SQLException;
import java.util.ArrayList;

import application.MainApp;
import errors.InstanceNotFound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Course;
import main.CourseOffering;
import main.Database;
import main.ProgramCoordinator;
import main.Reader;
import main.Staff;
import main.Student;
import main.User;

public class programCoordinatorCreateCoursePageController {
	@FXML
	private Button createCourseButton;
	@FXML
	private TextField courseIDField;
	@FXML
	private TextField courseNameField;
	@FXML
	private TextArea courseDescArea;
	@FXML
	private ComboBox<String> comboBox;

	MainApp main = new MainApp();
	Reader reader = new Reader();
	@FXML
	private void initialize() throws InstanceNotFound {
		ObservableList<String> programCoordinators = FXCollections.observableArrayList();
		
		ArrayList<User> users = reader.LoadAllUsers();
		System.out.println(users.size());
		int i = 0;
		while (i < users.size()) {
			if (users.get(i).getUserID().startsWith("e")) {
				programCoordinators.add(users.get(i).getUserID());
			}
			i++;
		}

		comboBox.getItems().addAll(programCoordinators);
	}

	public void createCourse() throws InstanceNotFound, SQLException {
		User coordinator = reader.LoadUser(comboBox.getValue());
		String courseID = courseIDField.getText();
		String courseName = courseNameField.getText();
		String courseDesc = courseDescArea.getText();

		Reader reader = new Reader();
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("topic 1");

		Course course = new Course(courseName, courseID, courseDesc, (Staff) coordinator, topics);
		reader.SaveCourse(course);
	}
}
