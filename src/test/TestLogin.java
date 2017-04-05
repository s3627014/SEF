package test;
import main.*;
import errors.*;
import static org.junit.Assert.*;

import org.junit.*;

import errors.PasswordIncorrect;

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

	@Test (expected=InstanceNotFound.class)
	public void userNotFound () throws Exception {
		User user = User.login("s00000", "pass");
	}

	@Test (expected=PasswordIncorrect.class)
	public void wrongPasswordTest () throws Exception {
		User user = User.login("s11111", "wrong");
	}

	@After
	public void cleanUp () {
		
	}
}
