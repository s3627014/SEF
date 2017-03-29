package main;
import java.sql.*;

public class DBConnect {
	
	public Connection Connect () throws SQLException {
		try {
		    String host = "emu.cs.rmit.edu.au"; //emailed Halil for these deets. More to come when he replies -Tim
		    String userName = "s3435088";
		    String password = "qoSpjXcw";
		    Connection con = DriverManager.getConnection(host, userName, password);
		    return con;
	    }

		catch ( SQLException err ) {
			System.out.println( err.getMessage( ) );
		}
	}
}
