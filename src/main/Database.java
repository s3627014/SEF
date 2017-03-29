package main;
import java.sql.*;

import javax.naming.*;
import javax.sql.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;

public class Database {
	
	public Connection Connect () throws SQLException {
		// Connection details
	    String host = "jdbc:oracle://emu.cs.rmit.edu.au:1521/general"; //emailed Halil for these deets. More to come when he replies -Tim
	    String userName = "s3435088";
	    String password = "qoSpjXcw";
	    
	    // Make connection work
	    try
	    {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
//	    DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
	    }catch(ClassNotFoundException cnfe){cnfe.printStackTrace();}
	    // Connect using details
	    Connection con = DriverManager.getConnection(host, userName, password);
	    return con;
	}
	
	public String getWhatever () throws SQLException {
		// Connect to database
	//	Connection connection = Connect();
		
		return "Not yet functional";
	}
}
