package main;

import java.util.ArrayList;

import errors.InstanceNotFound;

/**
 * Created by luke on 31/03/2017.
 */

public class Admin extends User {
	
   public Admin(String id, String password, String fName, String lName){
        super(id, password, fName, lName);
    }
   
   public ArrayList<User> getAllUsers () {
	   
	   Reader reader = new Reader();
	   ArrayList<User> users = null;
	   try {
			users = reader.LoadAllUsers();
	   } catch (InstanceNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }
	   
	   return users;
   }
   
   public ArrayList<Course> getAllCourses () {
	   
	   Reader reader = new Reader();
	   ArrayList<Course> courses = null;
	   try {
			courses = reader.LoadAllCourses();
	   } catch (InstanceNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }
	   
	   return courses;
   }
   
   public ArrayList<CourseOffering> getAllOffers () {
	   
	   Reader reader = new Reader();
	   ArrayList<CourseOffering> offers = null;
	   try {
		    offers = reader.LoadAllOfferings();
	   } catch (InstanceNotFound e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }
	   
	   return offers;
   }
   
   public boolean addCourseOffering(String offerID, DateTime semester, Course course, 
		   		Staff lecturer){
	   
	   // Create offer based on passed in variables
	   CourseOffering offer = new CourseOffering(offerID, semester, course, lecturer);
	   
	   boolean result = true;

		// Instantiate reader and save
	   Reader reader = new Reader();
	   try {
		   reader.SaveOffering(offer);
	   }
	   catch (Exception err) {
		   result = false;
	   }
	   
	   return result;
   }
}