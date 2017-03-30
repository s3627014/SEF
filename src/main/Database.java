package main;
import java.sql.*;

public class Database {
	
	private static Connection con;
	
	public static void Connect () throws SQLException {
		// Connection details
	    //String host = "emu.cs.rmit.edu.au:1521/general.cs.rmit.edu.au";
	    String host = "jdbc:oracle:thin:emu.cs.rmit.edu.au:1521:general";
	    String userName = "s3435088";
	    String password = "qoSpjXcw";
	    
	    try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		    con = DriverManager.getConnection(host, userName, password);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	public static Connection getConnection(){
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
