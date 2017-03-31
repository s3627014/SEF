package main;
import java.util.ArrayList;

public class Reader {
	ArrayList<User> users = new ArrayList<User>();
	
	Reader () {
		users.add(new Student("s11111", "pass", "John", "Smith", new OverloadPerms(), 
				new ArrayList<Mark>()));
		users.add(new Student("s22222", "pass", "Jane", "Smith", new OverloadPerms(), 
				new ArrayList<Mark>()));
		users.add(new Student("s33333", "pass", "Mohammed", "Ali", new OverloadPerms(), 
				new ArrayList<Mark>()));
		users.add(new Staff("st00001", "pass", "Mohammed", "Ali"));
		users.add(new Admin("a00001", "pass", "Mohammed", "Ali"));
	}
	
	public User LoadUser (String userID) throws UserNotFound {
		
		// Make user null to begin with
		User user = null;
		
		// Search through Database for user with this ID
		user = SearchDbForUser(userID);
		
		// Exception thrown if not found
		if (user == null){
			throw new UserNotFound();
		}
		
		// Return user
		return(user);
	}
	
	// This function will eventually hold the SQL calls, for now it just has a local array
	public User SearchDbForUser (String userID) {
		
		// Make user null to begin with
		User user = null;
		
		// Search through database
		for (User u : users) {
			if (u.getUserID().equals(userID)){
				user = u;
				break;
			}
		}
		
		// Return user if found in database
		return(user);
	}
}
