package test;
import main.*;
import static org.junit.Assert.*;
import main.PasswordIncorrect;
import main.User;
import main.UserNotFound;

import org.junit.*;

public class TestLogin {
	
	@Before
	public void setUp () {
		
	}

	@Test
	public void everythingRightTest () throws Exception {
		User user = User.login("s11111", "pass");
		assertEquals("John", user.getFirstName());
		user = User.login("s22222", "pass");
		assertEquals("Jane", user.getFirstName());
		user = User.login("s33333", "pass");
		assertEquals("Mohammed", user.getFirstName());
	}

	@Test (expected=UserNotFound.class)
	public void userNotFound () throws Exception {
		User user = User.login("s00000", "pass");
		user = User.login("s22222", "pass");
		user = User.login("s33333", "pass");
	}

	@Test (expected=PasswordIncorrect.class)
	public void wrongPasswordTest () throws Exception {
		User user = User.login("s11111", "wrong");
		user = User.login("s22222", "pass");
		user = User.login("s33333", "pass");
	}

	@After
	public void cleanUp () {
		
	}
}
