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
import javafx.scene.control.ListView;
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
	private TextField topicTextField;
	@FXML
	private ComboBox<String> progCoordComboBox;
	@FXML
	private ComboBox<String> courseComboBox;
	@FXML
	private ComboBox<String> topicComboBox;
	@FXML
	private ListView<String> prereqList;
	@FXML
	private ListView<String> topicList;
	ArrayList<Course> prereqArrayList = new ArrayList<Course>();
	ArrayList<String> topicArrayList = new ArrayList<String>();
	MainApp main = new MainApp();
	Reader reader = new Reader();
	@FXML
	private void initialize() throws InstanceNotFound {
		ObservableList<String> programCoordinators = FXCollections.observableArrayList();
		ObservableList<String> prereqs = FXCollections.observableArrayList();
		ArrayList<User> users = reader.LoadAllUsers();
		int i = 0;
		while (i < users.size()) {
			if (users.get(i).getUserID().startsWith("e")) {
				programCoordinators.add(users.get(i).getUserID());
			}
			i++;
		}
		ArrayList<Course> courses = reader.LoadAllCourses();
		 i = 0;
		while (i < courses.size()) {
			prereqs.add(courses.get(i).getCourseID());
			i++;
		}
		progCoordComboBox.getItems().addAll(programCoordinators);
		courseComboBox.getItems().addAll(prereqs);
	}

	public void createCourse() throws InstanceNotFound, SQLException {
		User coordinator = reader.LoadUser(progCoordComboBox.getValue());
		String courseID = courseIDField.getText();
		String courseName = courseNameField.getText();
		String courseDesc = courseDescArea.getText();


		Course course = new Course(courseName, courseID, courseDesc, (Staff) coordinator, prereqArrayList, topicArrayList);
		reader.SaveCourse(course);
	}
	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showProgramCoordinatorHomePage();
	}
	public void addPrereq() throws InstanceNotFound {
		Course course = reader.LoadCourse(courseComboBox.getValue());
		prereqArrayList.add(course);
		prereqList.getItems().add(courseComboBox.getValue());
	}
	public void removePrereq() throws InstanceNotFound {
		Course course = reader.LoadCourse(courseComboBox.getValue());
		prereqArrayList.remove(course);
		prereqList.getItems().remove(courseComboBox.getValue());
	}
	public void addTopic() throws InstanceNotFound {
		String topic = topicTextField.getText();
		topicArrayList.add(topic);
		topicList.getItems().add(topic);
	}
	public void removeTopic() throws InstanceNotFound {
		String topic = topicTextField.getText();
		topicArrayList.remove(topic);
		topicList.getItems().remove(topic);
	}
}
