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
			
			// Make staff specific loads
		}
		else if (userID.startsWith("a")) {
			
			// Make admin specific loads
		}
		
		return user;
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
