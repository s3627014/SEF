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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import main.Course;
import main.CourseOffering;
import main.InternalMark;
import main.Mark;
import main.Reader;
import main.Student;
import main.User;

public class ProgramCoordinatorViewHistoryPageController {
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
	private ToggleGroup toggles;
	@FXML
	private TextField studentIDField;
@FXML
private Button exemptionButton;
	private String userID;
	public ProgramCoordinatorViewHistoryPageController() {}
    
	   
	
    @FXML
    private void initialize() {
    	courseNameColumn.setCellValueFactory(cellData ->cellData.getValue().getOffer().getCourse().getCourseNameProperty());
    	courseIDColumn.setCellValueFactory(cellData ->cellData.getValue().getOffer().getCourse().getCourseIDProperty());
    	offerIDColumn.setCellValueFactory(cellData ->cellData.getValue().getOffer().getOfferIDProperty());
    	markColumn.setCellValueFactory(cellData ->cellData.getValue().getResultProperty());
    
			
    }

	public void ListStudentHistory() throws InstanceNotFound {
		setUserID(studentIDField.getText());
		ObservableList<InternalMark> markList = FXCollections.observableArrayList();
		Reader reader = new Reader();
		
		User user = null;
		ArrayList<Mark> marks = reader.LoadMarks("STUDENT", userID);
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
	public void showGrantExemption() {
		showEnrolDialog();
		//MainApp main = new MainApp();
		//main.showGrantExemptionPage();
		
	}
	public void setUserID(String userID) {
		this.userID = userID;
		
	}

	public void backButtonClicked() {
		MainApp main = new MainApp();
		main.showProgramCoordinatorHomePage();
	}
	public void showEnrolDialog() {
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
	public void externalClicked() {
		MainApp main = new MainApp();
		main.showStudentExternalHistoryPage();
	}
	public void removeExemptionButton() {
		exemptionButton.setVisible(false);
	}
}
