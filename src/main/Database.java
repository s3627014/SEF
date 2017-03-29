package main;
import java.sql.*;

public class Database {
	
	public Connection Connect () throws SQLException {
		// Connection details
	    String host = "emu.cs.rmit.edu.au:1521/general.cs.rmit.edu.au";
	    String userName = "s3435088";
	    String password = "qoSpjXcw";
	    
	    // Connect using details
	    Connection con = DriverManager.getConnection(host, userName, password);
	    return con;
	}
	
	public String getWhatever () throws SQLException {
		// Connect to database
		Connection connection = Connect();
		
		return "Not yet functional";
	}
}
