package main;
import java.sql.*;

public class Database {
	
	public Connection Connect () throws SQLException {
		// Connection details
	    //String host = "emu.cs.rmit.edu.au:1521/general.cs.rmit.edu.au";
	    String host = "jdbc:oracle:thin:emu.cs.rmit.edu.au:1521:general";
	    String userName = "s3435088";
	    String password = "qoSpjXcw";
	    Connection con = null;
	    
	    try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		    // Connect using details
		    con = DriverManager.getConnection(host, userName, password);
		
			
		} catch (Exception e) {
			System.out.println(e);
		}  
	    
	    return con;
	}
	
	public String getWhatever () throws SQLException {
		// Connect to database
		Connection connection = Connect();
		
		return "Not yet functional";
	}
}
