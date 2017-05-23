package view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import application.MainApp;
import errors.InstanceNotFound;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import main.Course;
import main.CourseOffering;
import main.DateTime;
import main.ExternalMark;
import main.InternalMark;
import main.Mark;
import main.Reader;
import main.Student;
import main.User;

public class ProgramCoordinatorViewExternalHistoryPageController {
	@FXML
	private TableView<ExternalMark> table;
	@FXML
	private TableColumn<ExternalMark, String> courseNameColumn;
	@FXML
	private TableColumn<ExternalMark, String> courseDescriptionColumn;
	@FXML
	private TableColumn<ExternalMark, String> markColumn;
	@FXML
	private TableColumn<ExternalMark, String> institutionColumn;
	@FXML
	private TextField studentIDField;
	private String userID;

	public ProgramCoordinatorViewExternalHistoryPageController() {
	}

	@FXML
	private void initialize() {
		courseNameColumn.setCellValueFactory(cellData -> cellData.getValue().getCourseProperty());
		courseDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
		institutionColumn.setCellValueFactory(cellData -> cellData.getValue().getInstitutionProperty());
		markColumn.setCellValueFactory(cellData -> cellData.getValue().getResultProperty());

	}

	public void ListStudentHistory() throws InstanceNotFound {
		setUserID(studentIDField.getText());
		ObservableList<ExternalMark> markList = FXCollections.observableArrayList();
		Reader reader = new Reader();

		User user = null;
		ArrayList<Mark> marks = reader.LoadMarks("STUDENT", userID);
		ArrayList<ExternalMark> externalMark = new ArrayList<ExternalMark>();
		int i = 0;
		while (i < marks.size()) {
			if (marks.get(i) instanceof ExternalMark) {
				externalMark.add((ExternalMark) marks.get(i));
			}
			i++;
		}
		markList.addAll(externalMark);

		table.setItems(markList);

	}

	public void showGrantExemption() {
		showEnrolDialog();
		// MainApp main = new MainApp();
		// main.showGrantExemptionPage();

	}

	public void setUserID(String userID) {
		this.userID = userID;

	}

	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showProgramCoordinatorHomePage();
	}

	public void showEnrolDialog() {
		DateTime dt = main.Database.dt;
		if (Integer.parseInt(dt.getCurrentWeek()) > 3) {
			warningDialog();
		} 
		else {

			TextInputDialog dialog = new TextInputDialog("o1234");
			dialog.setTitle("Grant Exemption for " + userID);
			dialog.setHeaderText("Please enter offering to enrol into.");
			dialog.setContentText("Offer ID: ");

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();

			// The Java 8 way to get the response value (with lambda
			// expression).
			Student student = new Student();
			result.ifPresent(offerID -> {
				try {
					student.enrol(userID, offerID);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	}

	public void showInternalMarks() {
		MainApp main = new MainApp();
		main.showStudentHistoryPage();

	}

	public void warningDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cannot grant exemption!");
		alert.setHeaderText("You can not grant exemption past week 1.");
		alert.setContentText("Please contact staff for more information.");

		alert.showAndWait();
	}
}
