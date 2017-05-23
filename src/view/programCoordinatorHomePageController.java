package view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.MainApp;
import errors.InstanceNotFound;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import main.Admin;
import main.Course;
import main.Database;
import main.DateTime;
import main.Reader;
import main.Staff;
import main.Student;
import main.User;

public class programCoordinatorHomePageController {
	@FXML
	private Button courseButton;

	MainApp main = new MainApp();

	@FXML
	private void initialize() {
	}

	public void CourseClicked() throws Exception {
		Database db = new Database();
		DateTime dt = db.dt;
		if(Integer.parseInt(dt.getCurrentWeek())>8) {
			warningDialog();
		}
		else {
			main.showProgramCoordinatorCreateCoursePage();
		}

	}

	public void studentActionsClicked() throws Exception {
		main.showStudentHistoryPage();
	}

	public void logoutClicked() {
		main.showLoginPage();
	}

	public void showOverloadDialog() {
		TextInputDialog dialog = new TextInputDialog("s12345");
		dialog.setTitle("Grant overload exemption for");
		dialog.setHeaderText("Please enter student ID.");
		dialog.setContentText("Student ID: ");
		Optional<String> result = dialog.showAndWait();
		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(studentID -> {
			showOverloadSemesterDialog(studentID);
		});
	}

	public void showOverloadSemesterDialog(String studentID){
		// Create the custom dialog.
	    Dialog<Pair<String, String>> dialog = new Dialog<>();
	    dialog.setTitle("Offering Date");
	    dialog.setHeaderText("Please enter a semester and year for overload permision");
	    // Set the button types.
	    ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

	            GridPane gridPane = new GridPane();
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setPadding(new Insets(20, 150, 10, 10));
	    TextField from = new TextField();
	    from.setPromptText("Semester");
	    TextField to = new TextField();
	    to.setPromptText("year");
	    gridPane.add(from, 0, 0);
	    gridPane.add(to, 2, 0);
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
	    	DateTime time = new DateTime(0, semester, year);
	    	Reader reader = new Reader();
			try {
				reader.SaveOverloadPerm(studentID, time);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	    	
	    });
	}
	public void warningDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cannot create course");
		alert.setHeaderText("courses must be created 4 weeks before next semester.");
		alert.setContentText("Good job.");

		alert.showAndWait();
	}
}
