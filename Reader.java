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

	public User LoadUser (String userID) throws InstanceNotFound {
		System.out.println("Loading User");

		// Set everything to null
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
 				    DateTime semester;

 					// Create semester date
 					Calendar cal = Calendar.getInstance();
 					cal.setTime(dateSem);
 					int day = cal.get(Calendar.DAY_OF_MONTH);
 					int month = cal.get(Calendar.MONTH);
 					int year = cal.get(Calendar.YEAR);
 					semester = new DateTime(day, month, year);

 					// Set overloadPerm semester date
 					overloadPerms.addSemester(semester);
 				}
 			} catch (SQLException err) {
 				System.out.println(err);
  			}

			// Set up student
			Student student = new Student(userID, pass, fName, lName, overloadPerms);
			return student;
		}
		else if (userID.startsWith("e")) {

			// Check if is a program coordinator
 			rs = SearchDB("ASS1_COURSES", "COORDINATOR", userID);

 			// Go through result set and get courseIDs
 			ArrayList<String> courseIDs = new ArrayList<String>();
 			try {
 				while (rs.next()) {
 				    String courseID = rs.getString("COURSEID");
 				   courseIDs.add(courseID);
 				}
 			} catch (SQLException err) {
 				System.out.println(err);
 			}

			// Set up staff depending on whether is program coordinator
 			if (courseIDs.size() > 0){
 				ProgramCoordinator staff = new ProgramCoordinator(userID, pass, fName, lName);
 				return staff;
 			}
 			else {
 				Staff staff = new Staff(userID, pass, fName, lName);
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
		System.out.println("Loading Course");

		// Set everything to null
		Course course = null;
	    String courseName = null;
	    String description = null;
	    String coordID = null;
	    Staff coordinator = null;
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

		// Set up course object
		course = new Course(courseName, courseID, description, coordinator, topics);
		return course;
	}

	public ArrayList<Course> LoadPrereqs (String key, String value) throws InstanceNotFound {
		System.out.println("Loading Prereqs");

		// Set an empty list up
		ArrayList<Course> prereqs = new ArrayList<Course>();

		// Search through database for offers
		ResultSet rs = SearchDB("ASS1_PREREQS", key, value);

		// Go through result set and build internal marks
		try {
			while (rs.next()) {
				// Set all to null
				Course prereq = null;
				String prereqID = null;

				// Get gotten values
				prereqID = rs.getString("PREREQ");

				// Load course and add
				prereq = (Course) LoadCourse(prereqID);
				prereqs.add(prereq);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}

		return prereqs;
	}

	public CourseOffering LoadOffering (String offerID) throws InstanceNotFound {
		System.out.println("Loading Offer");

		// Set all to null
		CourseOffering offer = null;
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

 		// Load course
 		course = LoadCourse(courseID);

 		// Load lecturer as Staff
 		lecturer = (Staff) LoadUser(lecturerID);

		// Throw error if offering was not found
		if (semester == null)
			throw new InstanceNotFound();

		// Set up offer
		offer = new CourseOffering(offerID, semester, course, lecturer);
		return offer;
	}

	public ArrayList<CourseOffering> LoadOfferings (String key, String value) throws InstanceNotFound {
		System.out.println("Loading Offers");

		// Set an empty list up
		ArrayList<CourseOffering> offers = new ArrayList<CourseOffering>();

		// Search through database for offers
		ResultSet rs = SearchDB("ASS1_OFFERINGS", key, value);

		// Go through result set and build internal marks
		try {
			while (rs.next()) {
				// Set all to null
				String offerID = null;
				DateTime semester = null;
				Course course = null;
			    Staff lecturer = null;
			    String courseID = null;
			    String lecturerID = null;

				// Get gotten values
			    offerID = rs.getString("OFFERID");
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

				// Load student and offer
				course = (Course) LoadCourse(courseID);
				lecturer = (Staff) LoadUser(lecturerID);

				// Build offer
				CourseOffering offer = new CourseOffering (offerID, semester, course, lecturer);
				offers.add(offer);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}

		return offers;
	}

	public ArrayList<Mark> LoadMarks (String key, String value) throws InstanceNotFound {
		System.out.println("Loading Marks");

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

				// Load student and offer
				student = (Student) LoadUser(studentID);
				offer = LoadOffering(offerID);

				// Build mark
				InternalMark mark = new InternalMark(student, offer, result);
				marks.add(mark);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}

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

				// Load student
				student = (Student) LoadUser(studentID);

				// Build mark
				ExternalMark mark = new ExternalMark(student, course, institution, description,
						startDate, result);
				marks.add(mark);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}

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
	public void deleteRecord() throws SQLException{
		// Connect to database
				Connection con = null;
				Statement stmt = null;
				try {
					 con = Database.connect();
				}
				catch (ClassNotFoundException err) {
					System.out.println("Could not find Oracle package.");
				}
				catch (SQLException err) {
					System.out.println("Could not connect to database.");
				}
		 //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = con.createStatement();
	      String sql = "DELETE FROM ASS1_ENROLMENTS " +
	                   "WHERE STUDENT = s12345 ";
	      stmt.executeUpdate(sql);
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
