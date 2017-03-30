package main;
import java.sql.*;

public class Database {
	
	private static Connection con;
	
	public static Connection connect () throws SQLException, ClassNotFoundException {
		// Connection details
	    String host = "jdbc:oracle:thin:@emu.cs.rmit.edu.au:1521:general";
	    String username = "s3435088";
	    String password = "qoSpjXcw";
	    
		// Load the driver class and start connection
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(host, username, password);

		return con;
	}
	
	public static void closeConnection(){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                 e.printStackTrace();
            }
        }

    }
	
//	public String getWhatever () throws SQLException {
//		// Connect to database
//		Connection connection = Connect(null, null, null);
//		
//		return "Not yet functional";
//	}
}
