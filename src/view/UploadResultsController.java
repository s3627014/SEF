package view;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import application.MainApp;
import errors.InstanceNotFound;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import main.Admin;
import main.CourseOffering;
import main.Database;
import main.InternalMark;
import main.ProgramCoordinator;
import main.Reader;
import main.Student;
import main.User;


public class UploadResultsController {

@FXML
private TextField studentIDField;
@FXML
private TextField offerIDField;
@FXML
private TextField markField;

  
    MainApp main = new MainApp();
    Reader reader = new Reader();
    
   
   
    public UploadResultsController(){}
    
    @FXML
    private void initialize() {
    	
    }

    public void uploadResult() throws InstanceNotFound, SQLException {
    	Student student =(Student) reader.LoadUser(studentIDField.getText());
    	CourseOffering offer = reader.LoadOffering(offerIDField.getText());
    	InternalMark mark = new InternalMark(student,offer,markField.getText());
    	reader.SaveMark(mark);
    }
 

}
