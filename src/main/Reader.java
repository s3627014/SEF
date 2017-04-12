package main;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import errors.*;


public class Reader {
	
	public static User LoadUser (String userID) throws InstanceNotFound {
		
		// Make user null to begin with
		User user = null;
	    String fName = null;
	    String lName = null;
	    String pass = null;
		
		// Search through database for user 
		ResultSet rs = SearchDB("ASS1_USERS", "USERID", userID);
		
		// Go through result set and build user
		try {
			while (rs.next()) {				
			    fName = rs.getString("FNAME");
			    lName = rs.getString("LNAME");
			    pass = rs.getString("PASS");
			    
			    user = new User(userID, pass, fName, lName);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}
		
		// Throw error if user was not found
		if (user == null)
			throw new InstanceNotFound();
		
		// Check if student, staff or admin (will start with 's', 'e' or 'a' respectively)
		if (userID.startsWith("s")) {
			
			// Load overloads
			rs = SearchDB("ASS1_OVERLOADS", "STUDENT", userID);
			
			// Go through result set and build overloads
			OverloadPerms overloadPerms = new OverloadPerms();
			try {
				while (rs.next()) {
				    Date dateSem = rs.getDate("SEMESTER");
				// Waiting on Tim to create convertDate function
				//    DateTime semester = DateTime.convertDate(dateSem);
				//    overloadPerms.addSemester(semester);
				}
			} catch (SQLException err) {
				System.out.println(err);
			}
			
			// Load internal marks
			rs = SearchDB("ASS1_INTLMARKS", "STUDENT", userID);
			
			// Go through result set and build overloads
			ArrayList<Mark> marks = new ArrayList<Mark>();
			try {
				while (rs.next()) {
				    String offerID = rs.getString("OFFERING");
				    String result = rs.getString("MARK");
				    Mark mark = new Mark(userID, offerID, result);
				}
			} catch (SQLException err) {
				System.out.println(err);
			}
			
			// Set up student
			Student student = new Student(userID, pass, fName, lName, overloadPerms, marks);
			return student;
		}
		else if (userID.startsWith("e")) {
			
			// Load offerings (classes taught)
			rs = SearchDB("ASS1_OFFERINGS", "TEACHER", userID);
			
			// Go through result set and build offerings
			ArrayList<String> offerIDs = new ArrayList<String>();
			ArrayList<CourseOffering> offerings = new ArrayList<CourseOffering>();
			try {
				while (rs.next()) {
				    String offerID = rs.getString("OFFERID");
				    offerIDs.add(offerID);
				}
			} catch (SQLException err) {
				System.out.println(err);
			}
			
			// Load offerings as CourseOfferings
			for (String offerID : offerIDs){
				CourseOffering offering = LoadOffering(offerID);
				offerings.add(offering);
			}
			
			// Load courses (courses coordinated)
			rs = SearchDB("ASS1_COURSES", "COORDINATOR", userID);
			
			// Go through result set and build offerings
			ArrayList<String> courseIDs = new ArrayList<String>();
			ArrayList<Course> courses = new ArrayList<Course>();
			try {
				while (rs.next()) {
				    String courseID = rs.getString("COURSEID");
				    courseIDs.add(courseID);
				}
			} catch (SQLException err) {
				System.out.println(err);
			}
			
			// Load courses as Courses
			for (String courseID : courseIDs){
				Course course = LoadCourse(courseID);
				courses.add(course);
			}
			
			// Set up staff
			if (courses.size() > 0){
				ProgramCoordinator staff = new ProgramCoordinator(userID, pass, fName, lName, 
						offerings, courses);
				return staff;
			}
			else {
				Staff staff = new Staff(userID, pass, fName, lName, offerings);
				return staff;
			}
			
		}
		else if (userID.startsWith("a")) {
			
			// Make admin specific loads
		}
		
		return user;
	}
	
	public static Course LoadCourse (String courseID) throws InstanceNotFound {
		
		// Make course null to begin with
		Course course = null;
	    String courseName = null;
	    String description = null;
	    String coordID = null;
	    Staff coordinator = null;
	    ArrayList<String> prereqIDs = new ArrayList<String>();
	    ArrayList<Course> prereqs = new ArrayList<Course>();
	    ArrayList<String> topics = new ArrayList<String>();
		
		// Search through database for course 
		ResultSet rs = SearchDB("ASS1_COURSES", "COURSEID", courseID);
		
		// Go through result set and build course
		try {
			while (rs.next()) {
			    courseName = rs.getString("COURSENAME");
			    description = rs.getString("DESCRIPTION");
			    coordID = rs.getString("COORDINATOR");
			}
		} catch (SQLException err) {
			System.out.println(err);
		}
		
		// Throw error if course was not found
		if (courseName == null)
			throw new InstanceNotFound();
		
		// Search through database for topics 
		rs = SearchDB("ASS1_TOPICS", "COURSEID", courseID);
		
		// Go through result set and build course
		try {
			while (rs.next()) {
			    String topicDesc = rs.getString("DESCRIPTION");
			    topics.add(topicDesc);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}
		
		// Search through database for prereq IDs
		rs = SearchDB("ASS1_PREREQS", "COURSE", courseID);
		
		// Go through result set and build course
		try {
			while (rs.next()) {
			    String prereqID = rs.getString("PREREQ");
			    prereqIDs.add(prereqID);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}
		
		// Load coordinator as Staff
		coordinator = (Staff) LoadUser(coordID);
		
		// Load prereqs as Courses
		for (String prereqID : prereqIDs) {
			
			Course prereq = LoadCourse(prereqID);
			prereqs.add(prereq);
		}
		
		return course;
	}
	
	public static CourseOffering LoadOffering (String offerID) {
		
		return new CourseOffering();
	}
	
	// This function will eventually hold the SQL calls, for now it just has a local array
	public static ResultSet SearchDB (String table, String key, String value) {
		
		// Connect to database
		Connection con = null;
		try {
			 con = Database.connect();
		} 
		catch (ClassNotFoundException err) {
			System.out.println("Could not find Oracle package.");
		} 
		catch (SQLException err) {
			System.out.println("Could not connect to database.");
		}
		
		// Build sql query to select the key value from the table
		String query = "SELECT * FROM " + table;
		query += " WHERE " + key + "='" + value + "'";
		
		System.out.println(query);
		
		// Attempt to run statement on oracle server
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);
		}
		catch (SQLException err) {
			System.out.println(err);
		}		
		
		// Return user if found in database
		return(rs);
	}
}
