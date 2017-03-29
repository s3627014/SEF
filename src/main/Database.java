package main;
import java.sql.*;

public class Database {
	
	private static Connection con;
	
	public static void Connect () throws SQLException {
		// Connection details
	    //String host = "emu.cs.rmit.edu.au:1521/general.cs.rmit.edu.au";
	    String host = "jdbc:oracle:thin:@emu.cs.rmit.edu.au:1521:general";
	    String userName = "s3435088";
	    String password = "qoSpjXcw";
	    
		try {
			// step1 load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// step2 create the connection object
			Connection con = DriverManager.getConnection(host, userName, password);

			// step3 create the statement object
			Statement stmt = con.createStatement();

			// step4 execute query
			ResultSet rs = stmt.executeQuery("select * from ASS1_USERS");
			while (rs.next())
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

			// step5 close the connection object
			con.close();

		} catch (Exception e) {
			System.out.println(e);
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
