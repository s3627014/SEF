package main;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import errors.*;


public class Reader {
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<CourseOffering> offerings = new ArrayList<CourseOffering>();
	private ArrayList<Mark> allMarks = new ArrayList<Mark>();	
	
	// CHECKS
	private User CheckForUser (String userID) {
		User result = null;
		
		// Go through users and check for match
		for (User user : users) {
			if (user.getUserID().equals(userID)){
				result = user;
			}
		}
		
		return result;
	}
	
	private Course CheckForCourse (String courseID) {
		Course result = null;
		
		// Go through users and check for match
		for (Course course : courses) {
			if (course.getCourseID().equals(courseID)){
				result = course;
			}
		}
		
		return result;
	}
	
	private CourseOffering CheckForOffer (String offerID) {
		CourseOffering result = null;
		
		// Go through users and check for match
		for (CourseOffering offer : offerings) {
			if (offer.getOfferID().equals(offerID)){
				result = offer;
			}
		}
		
		return result;
	}
	
	private Mark CheckForMark (String studentID, String offering, String institution) {
		Mark result = null;
		
		// Go through marks and check for match
		for (Mark mark : allMarks) {
			if (institution == null && mark.getClass() == InternalMark.class){
				InternalMark m = (InternalMark) mark;
				if (studentID.equals(m.getStudent().getUserID()) && 
						offering.equals(m.getOffer().getOfferID()))
					result = m;
			}
			else if (institution != null && mark.getClass() == ExternalMark.class){
				ExternalMark m = (ExternalMark) mark;
				if (studentID.equals(m.getStudent().getUserID()) && 
						offering.equals(m.getCourse()) && institution.equals(m.getInstitution()))
					result = m;
			}
		}
		
		return result;
	}
	
	
	// LOADS
	public User LoadUser (String userID) throws InstanceNotFound {
		
		// Check user hasn't been read in already
		User user = CheckForUser(userID);
		if (user != null)
			return user;
		
		// If it doesn't exist start everything at null
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
			ArrayList<Mark> marks = LoadMarks("STUDENT", userID);
			
			// Set up student
			Student student = new Student(userID, pass, fName, lName, overloadPerms, marks);
			
			// Add and return
			users.add(student);
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
			
			// Build temporary object and add to list before other loads
			Staff staff = new Staff(userID, pass, fName, lName, offerings);	
			users.add(staff);
			
			// Load courses as Courses
			for (String courseID : courseIDs){
				Course course = LoadCourse(courseID);
				courses.add(course);
			}
			
			// Set up staff
			if (courses.size() > 0){
				staff = (ProgramCoordinator) new ProgramCoordinator(userID, pass, fName, lName, 
						offerings, courses);
				return staff;
			}
			else {	
				return staff;
			}
			
		}
		else if (userID.startsWith("a")) {
			
			// Make admin specific loads
			Admin admin = new Admin(userID, pass, fName, lName);
			return admin;
		}
		
		return user;
	}
	
	public Course LoadCourse (String courseID) throws InstanceNotFound {
		
		// Check user hasn't been read in already
		Course course = CheckForCourse(courseID);
		if (course != null)
			return course;
		
		// If it doesn't exist start everything at null
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
		
		// Build temporary course object
		course = new Course(courseName, courseID, description, coordinator, prereqs, 
				topics);
		
		// Add to list of loaded users before loading secondary tables
		courses.add(course);			
		
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
	
	public CourseOffering LoadOffering (String offerID) throws InstanceNotFound {
		// Check user hasn't been read in already
		CourseOffering offer = CheckForOffer(offerID);
		if (offer != null)
			return offer;
		
		// If it doesn't exist start everything at null
		DateTime semester = null;
		Course course = null;
	    Staff lecturer = null;
	    String courseID = null;
	    String lecturerID = null;
		
		// Search through database for course 
		ResultSet rs = SearchDB("ASS1_OFFERINGS", "OFFERID", offerID);
		
		// Go through result set and build offering
		try {
			while (rs.next()) {
				Date semesterDate = rs.getDate("SEMESTER");
				courseID = rs.getString("COURSE");
				lecturerID = rs.getString("TEACHER");
				
				// Create semester date
				Calendar cal = Calendar.getInstance();
				cal.setTime(semesterDate);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				int month = cal.get(Calendar.MONTH);
				int year = cal.get(Calendar.YEAR);
				semester = new DateTime(day, month, year);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}
		
		// Throw error if offering was not found
		if (semester == null)
			throw new InstanceNotFound();
		
		// Build temporary offering object
		offer = new CourseOffering(offerID, semester, course, lecturer);
		
		// Add to list of loaded users before loading secondary tables
		offerings.add(offer);			
		
		// Load course
		course = LoadCourse(courseID);
		
		// Load lecturer as Staff
		lecturer = (Staff) LoadUser(lecturerID);
		
		// Recreate course offering
		offer.setAll(offerID, semester, course, lecturer);
		
		return offer;
	}
	
	public ArrayList<Mark> LoadMarks (String key, String value) throws InstanceNotFound {
		
		// Set an empty list up
		ArrayList<Mark> marks = new ArrayList<Mark>();
		
		// Search through database for internal marks 
		ResultSet rs = SearchDB("ASS1_INTLMARKS", key, value);
		
		// Go through result set and build internal marks
		try {
			while (rs.next()) {
				// Start with null variables
				Student student = null;
				CourseOffering offer = null;
				
				// Get gotten values
				String studentID = rs.getString("STUDENT");
				String offerID = rs.getString("OFFERING");
				String result = rs.getString("MARK");
				
				// Build mark
				Student tempStudent = new Student(studentID);
				CourseOffering tempOffer = new CourseOffering(offerID);
				InternalMark mark = new InternalMark(tempStudent, tempOffer, result);
				
				// Check that mark doesn't already exist
				if (CheckForMark(studentID, offerID, null) != null){
					continue;
				}
				
				// Otherwise need to assemble mark and add to lists
				allMarks.add(mark);
				
				// Load student and offer
				student = (Student) LoadUser(studentID);
				offer = LoadOffering(offerID);
				
				// Set up mark again
				mark.setAll(student, offer, result);
				System.out.println("Adding INTL Mark:" + mark);
				marks.add(mark);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}
		
		System.out.println("After INTL: " + marks.size());
		
		// Search through database for external marks 
		rs = SearchDB("ASS1_EXTLMARKS", key, value);
		
		// Go through result set and build external marks
		try {
			while (rs.next()) {
				// Start with null variables
				Student student = null;
				
				// Get gotten values
				String studentID = rs.getString("STUDENT");
				String course = rs.getString("COURSE");
				String institution = rs.getString("INSTITUTION");
				String description = rs.getString("DESCRIPTION");
				Date startDate = rs.getDate("STARTDATE");
				String result = rs.getString("MARK");
				
				// Build mark
				Student tempStudent = new Student(studentID);
				ExternalMark mark = new ExternalMark(tempStudent, course, institution, description,
						startDate, result);
				
				// Check that mark doesn't already exist
				if (CheckForMark(studentID, course, institution) != null){
					continue;
				}
				
				// Otherwise need to assemble mark and add to lists
				allMarks.add(mark);
				
				// Load student and offer
				student = (Student) LoadUser(studentID);
				
				// Set up mark again
				mark.setAll(student, course, institution, description, startDate, result);
				System.out.println("Adding EXTL Mark:" + mark);
				marks.add(mark);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}		
		
		System.out.println("After EXTL: " + marks.size());
		
		return marks;
	}
	
	// This function will eventually hold the SQL calls, for now it just has a local array
	public ResultSet SearchDB (String table, String key, String value) {
		
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
	 
	// This function will eventually hold the SQL calls, for now it just has a local array
	public ResultSet GetTable (String table) {
		
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
