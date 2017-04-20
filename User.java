package main;

import errors.PasswordIncorrect;
import errors.WrongFormat;

public class User {
	protected String id;
	protected String password;
	protected String fName;
	protected String lName;
	private static User user;


	public void setUser(User user1){
		user = user1;
	}
	User () {}

	User (String id, String password, String fName, String lName){
		this.id = id;
		this.password = password;
		this.fName = fName;
		this.lName = lName;
	}

	public static User login(String userID, String password) throws Exception {
		User user = null;

		// Check for format
		if (userID.contains(" ") || password.contains(" ")){
			throw new WrongFormat();
		}

		Reader reader = new Reader();
		user = reader.LoadUser(userID);

		// Check for correct password
		if (!user.checkPassword(password)) {
			throw new PasswordIncorrect();
		}

		return user;
	}

	// Gets
	public String getUserID () {
		return this.id;
	}

	public boolean checkPassword (String password) {

		if (this.password.equals(password))
			return true;
		else
			return false;
	}

	public String getFirstName () {
		return this.fName;
	}

	public String getLastName () {
		return this.lName;
	}
	public User getUser(){
		return user;
	}
}
