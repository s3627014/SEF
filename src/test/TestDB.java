package test;
import main.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDB {

	Connection con;

	@Before
	public void beforeClass(){
		con=null;
	}

	@Test
	public void testConnection() throws Exception {
		con = Database.connect();
		assertFalse(con == null);
	}

	@Test
	public void testUserIDs() throws Exception {
		// Connect
		con = Database.connect();
		assertFalse(con == null);

		// Call sql statement to get result set from DB
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM ASS1_USERS");

		// Create expected userID list from known database values
		ArrayList<String> expectedUserIDs = new ArrayList<String>();
		expectedUserIDs.add("s34567");
		expectedUserIDs.add("s23456");
		expectedUserIDs.add("s12345");
		expectedUserIDs.add("e12345");
		expectedUserIDs.add("e23456");
		expectedUserIDs.add("a12345");

		// Create userID list from
		ArrayList<String> userIDs = new ArrayList<String>();
        while (rs.next()) {
            userIDs.add(rs.getString("userID"));
        }

        // Check that the got matches the expected
        boolean match = true;
        for (int i = 0; i < expectedUserIDs.size(); i++) {
        	if (!expectedUserIDs.get(i).equals(userIDs.get(i)))
        		match = false;
        }

        assertTrue(match);
	}

	@Test
	public void testWrongIDs() throws Exception {
		// Connect
		con = Database.connect();
		assertFalse(con == null);

		// Call sql statement to get result set from DB
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM ASS1_USERS");

		// Create expected userID list from known database values
		ArrayList<String> expectedUserIDs = new ArrayList<String>();
		expectedUserIDs.add("s33333");
		expectedUserIDs.add("incorrectID");
		expectedUserIDs.add("s11111");

		// Create userID list from
		ArrayList<String> userIDs = new ArrayList<String>();
        while (rs.next()) {
            userIDs.add(rs.getString("userID"));
        }

        // Check that the got matches the expected
        boolean match = true;
        for (int i = 0; i < expectedUserIDs.size(); i++) {
        	if (!expectedUserIDs.get(i).equals(userIDs.get(i)))
        		match = false;
        }

        assertFalse(match);
	}

	@After
	public void cleanUp () throws Exception {
		con.close();
	}

}
