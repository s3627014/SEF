package application;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.User;
import view.StudentPageCourseController;
import view.StudentPageHomeController;

public class MainApp extends Application {
	private static Stage primaryStage;
	private static String userID;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(MainApp.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        	
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/LoginPage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
        	
        	
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            this.primaryStage = primaryStage;
            primaryStage.setTitle("Course Manager");
            scene.getStylesheets().add
            (MainApp.class.getResource("/styles/login.css").toExternalForm());
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void showStudentCoursePage(){
    	try {
 
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/studentCoursePage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            scene.getStylesheets().add
            (MainApp.class.getResource("/styles/login.css").toExternalForm());
            StudentPageCourseController controller = loader.getController();
            System.out.println("Sending id: " + userID);
            controller.setUserID(userID);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void showStudentHomePage(){
    	try {
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/studentPage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            scene.getStylesheets().add
            (MainApp.class.getResource("/styles/login.css").toExternalForm());
            
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setUserID(String userID) {
    	this.userID = userID;
    }
    public String getUserID() {
    	return userID;
    }
   
}