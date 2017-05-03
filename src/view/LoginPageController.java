package view;


import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import application.MainApp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import main.Database;
import main.User;


public class LoginPageController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    

    @FXML
    private Button loginButton;
  
    MainApp main = new MainApp();

    
   
   
    public LoginPageController(){}
    
    @FXML
    private void initialize() {
    	
    }
    public void loginClicked() throws Exception{
    	Database db = new Database();
    	System.out.println(usernameField.getText());
    	User.login(usernameField.getText(), passwordField.getText());
    	main.setUserID(usernameField.getText());
    	main.showStudentHomePage();
    	//main.showStudentCoursePage(usernameField.getText());
    }

    
 

}