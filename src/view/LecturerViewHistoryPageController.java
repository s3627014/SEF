package view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import application.MainApp;
import errors.InstanceNotFound;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import main.Database;
import main.DateTime;
import main.InternalMark;
import main.Mark;
import main.Reader;
import main.Student;
import main.User;

public class LecturerViewHistoryPageController {
	@FXML
	private TableView<InternalMark> table;
	@FXML
	private TableColumn<InternalMark, String> courseNameColumn;
	@FXML
	private TableColumn<InternalMark, String> courseIDColumn;
	@FXML
	private TableColumn<InternalMark, String> markColumn;
	@FXML
	private TableColumn<InternalMark, String> offerIDColumn;
	@FXML
	private TableColumn<InternalMark, String> RNFColumn;
	@FXML
	private ToggleGroup toggles;
	@FXML
	private TextField studentIDField;
	@FXML
	private Button exemptionButton;

	private String userID;

	public LecturerViewHistoryPageController() {
	}

	@FXML
	private void initialize() {
		courseNameColumn
				.setCellValueFactory(cellData -> cellData.getValue().getOffer().getCourse().getCourseNameProperty());
		courseIDColumn
				.setCellValueFactory(cellData -> cellData.getValue().getOffer().getCourse().getCourseIDProperty());
		offerIDColumn.setCellValueFactory(cellData -> cellData.getValue().getOffer().getOfferIDProperty());
		markColumn.setCellValueFactory(cellData -> cellData.getValue().getResultProperty());
		RNFColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFinalised())));

	}

	public void ListStudentHistory() throws InstanceNotFound {
		ObservableList<InternalMark> markList = FXCollections.observableArrayList();
		Reader reader = new Reader();

		ArrayList<Mark> marks = reader.LoadMarks("STUDENT", studentIDField.getText());
		ArrayList<InternalMark> internalMarks = new ArrayList<InternalMark>();
		int i = 0;
		while (i < marks.size()) {
			if (marks.get(i) instanceof InternalMark) {
				internalMarks.add((InternalMark) marks.get(i));
			}
			i++;
		}
		markList.addAll(internalMarks);

		table.setItems(markList);

	}

	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showLecturerHomePage();
	}

	public void warningDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cannot upload result!");


		alert.showAndWait();
	}
	public void noSelectionDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cannot grant exemption!");
		alert.setHeaderText("You must select a mark first.");
		alert.setContentText("Please contact nobody");

		alert.showAndWait();
	}
	public void RNFDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Can only edit RNF marks!");
		alert.setHeaderText("You can not edit this mark.");
		alert.setContentText("Please contact nobody");

		alert.showAndWait();
	}
	public void editButtonClicked() {
		if(table.getSelectionModel().selectedItemProperty() ==null) {
			noSelectionDialog();
			return;
		}
		if(table.getSelectionModel().selectedItemProperty().getValue().getFinalised() ==true) {
			RNFDialog();
			return;
		}
		TextInputDialog dialog = new TextInputDialog("mark");
		dialog.setTitle("Change mark for " + userID);
		dialog.setHeaderText("Please enter updated mark.");
		dialog.setContentText("Mark: ");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
	

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(mark -> {
		
				try {
					uploadResult(table.getSelectionModel().selectedItemProperty().getValue().getOffer().getOfferID(),mark);
					ListStudentHistory();
				} catch (InstanceNotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		});
	}
	public void uploadResult(String offerID, String result) throws InstanceNotFound, SQLException {
		Reader reader = new Reader();
		CourseOffering offer = reader.LoadOffering(offerID);
		ArrayList<CourseOffering> offerings = reader.LoadOfferings("TEACHER", userID);
		int i = 0;
		while (i < offerings.size()) {

			if (offerings.get(i).getOfferID().equals(offer.getOfferID())) {

				Database db = new Database();
				DateTime dt = db.dt;
				if ((Integer.parseInt(dt.getCurrentSem()) == Integer.parseInt(offer.getSemester().getCurrentSem()))
						&& (Integer.parseInt(dt.getCurrentYear()) == Integer
								.parseInt(offer.getSemester().getCurrentYear()))) {
					Student student = (Student) reader.LoadUser(studentIDField.getText());
					InternalMark mark = new InternalMark(student, offer, result, false);
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
