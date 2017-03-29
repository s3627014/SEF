package main;

public class User {
	String id;
	String password;
	String fName;
	String lName;
	
	User () {}
	
	User (String id, String password, String fName, String lName){
		this.id = id;
		this.password = password;
		this.fName = fName;
		this.lName = lName;
	}
	
	public static User Login(String userID, String password) throws Exception {
		User user = null;
		
		Reader reader = new Reader();
		user = reader.LoadUser(userID);
		
		if (user.password != password) {
			throw new PasswordIncorrect();
		}
		
		return user;
	}
}
