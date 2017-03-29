package main;
import java.sql.*;

class Initialise {
	public static void main(String [ ] args)
	{
		Database db = new Database();
		
		try {
			db.Connect();			
		}
		catch (SQLException err) {
			System.out.println(err);
		}
		
		createMenu();
	}

	public static void createMenu() {
		System.out.println("*************** Course Management System ***************\n");
		System.out.println("1.   Login");
		System.out.println("3.   Exit\n");
		System.out.println("**********************************************************");
	}
}
