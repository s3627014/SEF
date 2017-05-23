package view;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import application.MainApp;
import errors.InstanceNotFound;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import main.Admin;
import main.CourseOffering;
import main.Database;
import main.DateTime;
import main.InternalMark;
import main.ProgramCoordinator;
import main.Reader;
import main.Staff;
import main.Student;
import main.User;

public class UploadResultsController {

	@FXML
	private TextField studentIDField;
	@FXML
	private TextField offerIDField;
	@FXML
	private TextField markField;
	private String userID;

	MainApp main = new MainApp();
	Reader reader = new Reader();

	public UploadResultsController() {
	}

	@FXML
	private void initialize() {

	}

	public void uploadResult() throws InstanceNotFound, SQLException {
		CourseOffering offer = reader.LoadOffering(offerIDField.getText());
		ArrayList<CourseOffering> offerings = reader.LoadOfferings("TEACHER", userID);
		System.out.println("SIZEEE:   " + offer.getOfferID());
		int i = 0;
		while (i < offerings.size()) {

			if (offerings.get(i).getOfferID().equals(offer.getOfferID())) {

				Database db = new Database();
				DateTime dt = db.dt;
				if ((Integer.parseInt(dt.getCurrentSem()) == Integer.parseInt(offer.getSemester().getCurrentSem()))
						&& (Integer.parseInt(dt.getCurrentYear()) == Integer
								.parseInt(offer.getSemester().getCurrentYear()))) {
					Student student = (Student) reader.LoadUser(studentIDField.getText());
					InternalMark mark = new InternalMark(student, offer, markField.getText());
					reader.SaveMark(mark);
				} else {
					System.out.println();
					warningDialog();
				}
				return;
			} 
			i++;
		}
		notLecturerDialog();
	}

	public void backClicked() {

		main.showLecturerHomePage();
	}

	public void warningDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cannot upload results");
		alert.setHeaderText("You can only upload results for current semester.");
		alert.setContentText("Please have fun.");

		alert.showAndWait();
	}

	public void notLecturerDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cannot upload results");
		alert.setHeaderText("You can only upload results for your offerings.");
		alert.setContentText("Please have fun.");

		alert.showAndWait();
	}

	public void setUserID(String userID) {
		this.userID = userID;
		System.out.println("Setting the id as " + userID);
	}

}
