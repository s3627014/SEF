package main;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import errors.*;


public class Reader {
	
	// LOAD ALL FUNCTIONS
	
	public ArrayList<User> LoadAllUsers () throws InstanceNotFound {
		ArrayList<User> users = new ArrayList<User>();
		
		// Go through result set and build user
		try {
			
			// Search through database for user 
			ResultSet rs = GetTable("ASS1_USERS");
			
			while (rs.next()) {		
				
				// Set everything to null
				User user = null;
				String userID = null;
			    String fName = null;
			    String lName = null;
			    String pass = null;
			    
			    // Get information
				userID = rs.getString("USERID");
			    fName = rs.getString("FNAME");
			    lName = rs.getString("LNAME");
			    pass = rs.getString("PASS");
			    
			    user = new User(userID, pass, fName, lName);
			    users.add(user);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}
		
		return users;
	}
	
	public ArrayList<Course> LoadAllCourses () throws InstanceNotFound {
		ArrayList<Course> courses = new ArrayList<Course>();
		
		// Go through result set and build course
		try {
			
			// Search through database for course 
			ResultSet rs = GetTable("ASS1_COURSES");
			
			while (rs.next()) {
				// Set everything to null
				Course course = null;
			    String courseID = null;
			    String courseName = null;
			    String description = null;
			    String coordID = null;
			    Staff coordinator = null;
		 	    ArrayList<String> topics = new ArrayList<String>();
				
		 	    // Get information
			    courseID = rs.getString("COURSEID");
			    courseName = rs.getString("COURSENAME");
			    description = rs.getString("DESCRIPTION");
			    coordID = rs.getString("COORDINATOR");
				
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
				courses.add(course);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}	
		
		return courses;
	}
	
	public ArrayList<CourseOffering> LoadAllOfferings () throws InstanceNotFound {
		ArrayList<CourseOffering> offers = new ArrayList<CourseOffering>();
		
		// Go through result set and build course
		try {
			
			// Search through database for course 
			ResultSet rs = GetTable("ASS1_OFFERINGS");
			
			while (rs.next()) {
				// Set all to null
				CourseOffering offer = null;
			    String offerID = null;
				DateTime semester = null;
				Course course = null;
			    Staff lecturer = null;
			    String courseID = null;
			    String lecturerID = null;

			    // Get information
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
		 		
		 		// Load course
		 		course = LoadCourse(courseID);
		 		
		 		// Load lecturer as Staff
		 		lecturer = (Staff) LoadUser(lecturerID);
				
				// Set up offer 
				offer = new CourseOffering(offerID, semester, course, lecturer);	
				offers.add(offer);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}	
		
		return offers;
	}

	
	// SPECIFIC LOAD FUNCTIONS
	
	public User LoadUser (String userID) throws InstanceNotFound {
		System.out.println("Loading User");
		
		// Set everything to null
		User user = null;
	    String fName = null;
	    String lName = null;
	    String pass = null;
		
		// Go through result set and build user
		try {
			
			// Search through database for user 
			ResultSet rs = SearchDB("ASS1_USERS", "USERID", userID);
			
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
	 			
			// Student based variables
			OverloadPerms overloadPerms = new OverloadPerms();
			
 			try {
 				// Load overloads
 				ResultSet rs = SearchDB("ASS1_OVERLOADS", "STUDENT", userID);
 	 			
 	 			// Go through result set and build overloads
 	 			
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
 			
 			// Go through result set and get courseIDs
 			ArrayList<String> courseIDs = new ArrayList<String>();
 			try {
 				
 				// Check if is a program coordinator
 				ResultSet rs = SearchDB("ASS1_COURSES", "COORDINATOR", userID);
 				
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
		
		// Go through result set and build course
		try {
			
			// Search through database for course 
			ResultSet rs = SearchDB("ASS1_COURSES", "COURSEID", courseID);
			
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
 		
 		// Go through result set and build course
 		try {
 			
 			// Search through database for topics 
 	 		ResultSet rs = SearchDB("ASS1_TOPICS", "COURSEID", courseID);
 	 		
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
	
	public ArrayList<Course> SearchForCourse (String searchTerm) throws InstanceNotFound {
		System.out.println("Searching for Course");
		
		// Set everything to null
		ArrayList<Course> courses = new ArrayList<Course>();
		
		// Go through result set and build course
		try {
			
			// Search through database for courses with a matching name 
			ResultSet rs = PartialSearchDB("ASS1_COURSES", "COURSENAME", searchTerm);
			
			while (rs.next()) {
				
				// Start at null
				Course course = null;
			    String courseID = null;
			    String courseName = null;
			    String description = null;
			    String coordID = null;
			    Staff coordinator = null;
		 	    ArrayList<String> topics = new ArrayList<String>();
		 	    
		 	    // Get info
			    courseID = rs.getString("COURSEID");
			    courseName = rs.getString("COURSENAME");
			    description = rs.getString("DESCRIPTION");
			    coordID = rs.getString("COORDINATOR");
				
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
				courses.add(course);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}
		
		// Go through result set and build course
		try {

			// Search through database for courses with a matching name 
			ResultSet rs = PartialSearchDB("ASS1_COURSES", "COURSEID", searchTerm);
			
			while (rs.next()) {
				
				// Start at null
				Course course = null;
			    String courseID = null;
			    String courseName = null;
			    String description = null;
			    String coordID = null;
			    Staff coordinator = null;
		 	    ArrayList<String> topics = new ArrayList<String>();
		 	    
		 	    // Get info
			    courseID = rs.getString("COURSEID");
			    courseName = rs.getString("COURSENAME");
			    description = rs.getString("DESCRIPTION");
			    coordID = rs.getString("COORDINATOR");
				
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
				courses.add(course);
			}
		} catch (SQLException err) {
			System.out.println(err);
		}
		
		return courses;
	}
	
	public ArrayList<Course> LoadPrereqs (String key, String value) throws InstanceNotFound {
		System.out.println("Loading Prereqs");
		
		// Set an empty list up
		ArrayList<Course> prereqs = new ArrayList<Course>();
		
		// Go through result set and build internal marks
		try {
			
			// Search through database for offers 
			ResultSet rs = SearchDB("ASS1_PREREQS", key, value);
			
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
		
		// Go through result set and build offering
		try {
			
			// Search through database for course 
			ResultSet rs = SearchDB("ASS1_OFFERINGS", "OFFERID", offerID);
			
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
	
	public ArrayList<Mark> LoadMarks (String key, String value) throws InstanceNotFound {
		System.out.println("Loading Marks");
		
		// Set an empty list up
		ArrayList<Mark> marks = new ArrayList<Mark>();
		
		// Go through result set and build internal marks
		try {
			
			// Search through database for internal marks 
			ResultSet rs = SearchDB("ASS1_INTLMARKS", key, value);
			
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
		
		// Go through result set and build external marks
		try {
			
			// Search through database for external marks 
			ResultSet rs = SearchDB("ASS1_EXTLMARKS", key, value);
			
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
	
	public ArrayList<CourseOffering> LoadOfferings (String key, String value) throws InstanceNotFound {
		System.out.println("Loading Offers");
		
		// Set an empty list up
		ArrayList<CourseOffering> offers = new ArrayList<CourseOffering>();
		
		// Go through result set and build internal marks
		try {
			
			// Search through database for offers 
			ResultSet rs = SearchDB("ASS1_OFFERINGS", key, value);
			
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
	
	public ArrayList<CourseOffering> LoadClasses (String userID) throws InstanceNotFound {
		System.out.println("Loading Enrolments");
		
		// Set an empty list up
		ArrayList<CourseOffering> classes = new ArrayList<CourseOffering>();
		
		// If student get enrolments
		if (userID.startsWith("s")){
			
			// Go through result set and build internal marks
			try {
				
				// Search through database for enrolment 
				ResultSet rs = SearchDB("ASS1_ENROLMENTS", "STUDENT", userID);
				
				while (rs.next()) {
					// Start with null variables
					CourseOffering offer = null;
					
					// Get gotten values
					String offerID = rs.getString("OFFERING");
					
					// Load student and offer
					offer = LoadOffering(offerID);
					classes.add(offer);
				}
			} catch (SQLException err) {
				System.out.println(err);
			}			
		}
		// If staff get offerings
		else if (userID.startsWith("e")){
			
			// Go through result set and build internal marks
			try {
				
				// Search through database for internal marks 
				ResultSet rs = SearchDB("ASS1_OFFERINGS", "TEACHER", userID);
				
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
					
					// Build offer
					CourseOffering offer = new CourseOffering (offerID, semester, course, lecturer);					
					classes.add(offer);
				}
			} catch (SQLException err) {
				System.out.println(err);
			}			
		}
		else {
			throw new InstanceNotFound();
		}
		
		return classes;
	}

	
	// SAVE FUNCTIONS
	
	public boolean SaveUser (User user) {

		ArrayList<Keypair> setPairs = new ArrayList<Keypair>();
		setPairs.add(new Keypair("PASS", user.getPassword()));		
		setPairs.add(new Keypair("FNAME", user.getFirstName()));	
		setPairs.add(new Keypair("LNAME", user.getLastName()));	

		ArrayList<Keypair> wherePairs = new ArrayList<Keypair>();
		wherePairs.add(new Keypair("USERID", user.getUserID()));	
		
		// Try to update, and if no instance exists try to add
		try {
			UpdateRecord("ASS1_USERS", setPairs, wherePairs);
		} catch (SQLException e) {
			try {
				AddRecord("ASS1_USERS", setPairs);
			} catch (SQLException err) {
				// TODO Auto-generated catch block
				err.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean SaveCourse (Course course) {

		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();	
		keypairs.add(new Keypair("COURSENAME", course.getCourseName()));		
		keypairs.add(new Keypair("DESCRIPTION", course.getDescription()));	
		keypairs.add(new Keypair("COORDINATOR", course.getCoordinator().getUserID()));

		ArrayList<Keypair> wherePairs = new ArrayList<Keypair>();
		wherePairs.add(new Keypair("COURSEID", course.getCourseID()));
		
		// Try to update, and if no instance exists try to add
		try {
			UpdateRecord("ASS1_COURSES", keypairs, wherePairs);
		} catch (SQLException e) {
			try {
				AddRecord("ASS1_COURSES", keypairs);
			} catch (SQLException err) {
				// TODO Auto-generated catch block
				err.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean SaveOffering (CourseOffering offer) {

		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();	
		keypairs.add(new Keypair("SEMESTER", offer.getSemester().getDate()));	
		keypairs.add(new Keypair("COURSE", offer.getCourse().getCourseID()));		
		keypairs.add(new Keypair("TEACHER", offer.getLecturer().getUserID()));	

		ArrayList<Keypair> wherePairs = new ArrayList<Keypair>();
		wherePairs.add(new Keypair("OFFERID", offer.getOfferID()));	
		
		// Try to update, and if no instance exists try to add
		try {
			UpdateRecord("ASS1_OFFERINGS", keypairs, wherePairs);
		} catch (SQLException e) {
			try {
				AddRecord("ASS1_OFFERINGS", keypairs);
			} catch (SQLException err) {
				// TODO Auto-generated catch block
				err.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean SaveEnrolment (String studentID, String offerID) {

		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();
		keypairs.add(new Keypair("STUDENT", studentID));	
		keypairs.add(new Keypair("OFFERING", offerID));		
		
		// Try to add
		try {
			AddRecord("ASS1_ENROLMENTS", keypairs);
		} catch (SQLException err) {
			// TODO Auto-generated catch block
			err.printStackTrace();
		}
		
		return true;
	}
	
	public boolean SaveMark (InternalMark mark) {
			
		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();	
		keypairs.add(new Keypair("MARK", mark.getResult()));
		
		ArrayList<Keypair> wherePairs = new ArrayList<Keypair>();
		wherePairs.add(new Keypair("STUDENT", mark.getStudent().getUserID()));	
		wherePairs.add(new Keypair("OFFERING", mark.getOffer().getOfferID()));	
		
		// Try to update, and if no instance exists try to add
		try {
			UpdateRecord("ASS1_INTLMARKS", keypairs, wherePairs);
		} catch (SQLException e) {
			try {
				AddRecord("ASS1_INTLMARKS", keypairs);
			} catch (SQLException err) {
				// TODO Auto-generated catch block
				err.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean SaveOverloadPerm (String studentID, DateTime semester) {
			
		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();
		keypairs.add(new Keypair("STUDENT", studentID));	
		keypairs.add(new Keypair("SEMESTER", semester.getDate()));	
		
		// Try to add
		try {
			AddRecord("ASS1_OVERLOADS", keypairs);
		} catch (SQLException err) {
			// TODO Auto-generated catch block
			err.printStackTrace();
		}
		
		return true;
	}
	
	public boolean CreateExemptions (String studentID, String courseID) {
			
		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();
		keypairs.add(new Keypair("STUDENT", studentID));	
		keypairs.add(new Keypair("COURSE", courseID));	
		
		// Try to add
		try {
			AddRecord("ASS1_EXEMPTIONS", keypairs);
		} catch (SQLException err) {
			// TODO Auto-generated catch block
			err.printStackTrace();
		}
		
		return true;
	}

	public boolean SavePrereq (String courseID, String prereqID) {
			
		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();
		keypairs.add(new Keypair("COURSE", courseID));	
		keypairs.add(new Keypair("PREREQ", prereqID));		
		
		// Try to add
		try {
			AddRecord("ASS1_PREREQS", keypairs);
		} catch (SQLException err) {
			// TODO Auto-generated catch block
			err.printStackTrace();
		}
		
		return true;
	}
	
	
	// DELETION FUNCTIONS
	
	public boolean DeleteOffering (String offerID) {
		
		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();
		keypairs.add(new Keypair("OFFERID", offerID));
		
		try {
			DeleteRecord("ASS1_OFFERINGS", keypairs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean DeleteEnrolment (String studentID, String offerID) {
		
		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();
		keypairs.add(new Keypair("STUDENT", studentID));
		keypairs.add(new Keypair("OFFERING", offerID));
		
		try {
			DeleteRecord("ASS1_ENROLMENTS", keypairs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	public boolean DeleteMark (String studentID, String offerID) {
		
		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();
		keypairs.add(new Keypair("STUDENT", studentID));
		keypairs.add(new Keypair("OFFERING", offerID));
		
		try {
			DeleteRecord("ASS1_MARKS", keypairs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	public boolean DeleteOverloadPerm (String studentID, DateTime semester) {
		
		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();
		keypairs.add(new Keypair("STUDENT", studentID));
		keypairs.add(new Keypair("SEMESTER", semester.getDate()));
		
		try {
			DeleteRecord("ASS1_PREREQS", keypairs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean DeleteExemption (String studentID, String courseID) {
		
		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();
		keypairs.add(new Keypair("STUDENT", studentID));
		keypairs.add(new Keypair("COURSE", courseID));
		
		try {
			DeleteRecord("ASS1_EXEMPTIONS", keypairs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean DeletePrereq (String courseID, String prereqID) {
		
		ArrayList<Keypair> keypairs = new ArrayList<Keypair>();
		keypairs.add(new Keypair("COURSE", courseID));
		keypairs.add(new Keypair("PREREQ", prereqID));
		
		try {
			DeleteRecord("ASS1_PREREQS", keypairs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	
	// PRIVATE HELPER FUNCTIONS
	
	private ResultSet SearchDB (String table, String key, String value) 
			throws SQLException {
		
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
		
	private ResultSet PartialSearchDB (String table, String key, String value) 
			throws SQLException {
		
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
		query += " WHERE " + key + " LIKE '%" + value + "%'";
		
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

	private boolean AddRecord (String table, ArrayList<Keypair> keypairs) 
			throws SQLException {
		
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
		
		// Build sql query to select the key values from the table
		String query = "INSERT INTO " + table + "(";

		if (keypairs.size() > 0){
			query += keypairs.get(0).key;
		}
		// Handle secondary keys
		for (int i = 1; i < keypairs.size(); i++) {
			query += ", " + keypairs.get(i).key;
		}
		
		query += ") VALUES(";
		
		// Handle first key
		if (keypairs.size() > 0){
			query += keypairs.get(0).getValue();
		}
		// Handle secondary keys
		for (int i = 1; i < keypairs.size(); i++) {
			query += ", " + keypairs.get(i).getValue();
		}
		
		// Finish query
		query += ")";
		
		System.out.println(query);
		
		// Attempt to run statement on oracle server
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery(query);
		}
		catch (SQLException err) {
			System.out.println(err);
		}		
		
		return true;
	}

	private boolean UpdateRecord (String table, ArrayList<Keypair> setPairs, 
			ArrayList<Keypair> wherePairs) throws SQLException {
		
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
		
		// Build sql query to select the key values from the table
		String query = "UPDATE " + table + " SET ";

		// Handle first key
		if (setPairs.size() > 0){
			query += setPairs.get(0).key + " =" + setPairs.get(0).getValue();
		}
		// Handle secondary keys
		for (int i = 1; i < setPairs.size(); i++) {
			query += ", " + setPairs.get(i).key + " =" + setPairs.get(i).getValue();
		}

		// Continue building WHERE section of query
		if (wherePairs.size() > 0){
			query += " WHERE " + wherePairs.get(0).key + " =" + wherePairs.get(0).getValue();
		}
		// Handle secondary keys
		for (int i = 1; i < wherePairs.size(); i++) {
			query += " AND " + wherePairs.get(i).key + " =" + wherePairs.get(i).getValue();
		}
		
		System.out.println(query);
		
		// Attempt to run statement on oracle server
		Statement stmt = con.createStatement();
		stmt.executeQuery(query);
		
		return true;
	}
	
	private boolean DeleteRecord (String table, ArrayList<Keypair> keypairs) 
			throws SQLException {
		
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
		
		// Build sql query to select the key values from the table
		String query = "DELETE FROM " + table;
		
		// Handle first key
		if (keypairs.size() > 0){
			query += " WHERE " + keypairs.get(0).key + " ='" + keypairs.get(0).value + "'";
		}
		// Handle secondary keys
		for (int i = 1; i < keypairs.size(); i++) {
			query += " AND " + keypairs.get(i).key + " ='" + keypairs.get(i).value + "'";
		}
		
		System.out.println(query);
		
		// Attempt to run statement on oracle server
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery(query);
		}
		catch (SQLException err) {
			System.out.println(err);
		}		
		
		return true;
	}
	
	private ResultSet GetTable (String table) 
			throws SQLException {
		
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
