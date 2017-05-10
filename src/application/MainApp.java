package application;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.User;
import view.ProgramCoordinatorViewHistoryPageController;
import view.StudentEnrolPageController;
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
    public void showStudentEnrolPage(){
    	try {
 
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/studentEnrolPage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            scene.getStylesheets().add
            (MainApp.class.getResource("/styles/login.css").toExternalForm());
            StudentEnrolPageController controller = loader.getController();
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
    
    public void showCreateOfferingPage(){
    	try {
    		 
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/createOfferingPage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            scene.getStylesheets().add
            (MainApp.class.getResource("/styles/login.css").toExternalForm());
            StudentEnrolPageController controller = loader.getController();
            System.out.println("Sending id: " + userID);
            controller.setUserID(userID);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showAdvanceSysPage(){
    	try {
    		 
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/advanceSysPage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            scene.getStylesheets().add
            (MainApp.class.getResource("/styles/login.css").toExternalForm());
            StudentEnrolPageController controller = loader.getController();
            System.out.println("Sending id: " + userID);
            controller.setUserID(userID);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void showProgramCoordinatorHomePage(){
    	try {
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/programCoordinatorPage.fxml"));
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
    public void showProgramCoordinatorCreateCoursePage(){
    	try {
    		System.out.println("sadasd");
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/programCoordinatorCreateCoursePage.fxml"));
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
    public void showStudentHistoryPage(){
    	try {
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/programCoordinatorViewHistoryPage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            scene.getStylesheets().add
            (MainApp.class.getResource("/styles/studentHistory.css").toExternalForm());
            ProgramCoordinatorViewHistoryPageController controller = loader.getController();
           
            controller.ListStudentHistory();
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