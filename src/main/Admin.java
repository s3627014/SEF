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
   
   public boolean createCourseOffering(Course course) {
	   boolean result = true;
	   
	   Reader reader = new Reader();
	   try {
		   reader.SaveCourse(course);
	   }
	   catch (Exception err) {
		   result = false;
	   }
	   
	   return result;
   }
}
