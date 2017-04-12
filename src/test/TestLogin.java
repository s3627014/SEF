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

	@Test (timeout=1000)
	public void studentLogin () throws Exception {
		Student user = (Student) User.login("s12345", "pass");
		assertEquals("John", user.getFirstName());
	}
	
	@Test (timeout=1000)
	public void adminLogin () throws Exception {
		Admin user = (Admin) User.login("a123456", "pass");
		assertEquals("Bland", user.getFirstName());
	}
	
	@Test (timeout=1000)
	public void staffLogin () throws Exception {
		Staff user = (Staff) User.login("e12345", "pass");
		assertEquals("Halil", user.getFirstName());
	}
	
	@Test (timeout=1000)
	public void progCoordLogin () throws Exception {
		ProgramCoordinator user = (ProgramCoordinator) User.login("e23456", "pass");
		assertEquals("CompSciGuy", user.getFirstName());
	}

	@Test (expected=InstanceNotFound.class)
	public void userNotFound () throws Exception {
		User user = User.login("s00000", "pass");
	}

	@Test (expected=PasswordIncorrect.class)
	public void wrongPasswordTest () throws Exception {
		User user = User.login("s12345", "wrong");
	}
	@Test (expected=PasswordIncorrect.class)
	public void wrongPasswordFormatTest () throws Exception {
		User user = User.login("s11111", "p a s s");
		user = User.login("s22222", "pass");
		user = User.login("s33333", "pass");
	}
	@Test (expected=UserNotFound.class)
	public void userWrongFormat () throws Exception {
		User user = User.login("s000 00", "pass");
		user = User.login("s22222", "pass");
		user = User.login("s33333", "pass");
	}
	
	@After
	public void cleanUp () {
		
	}
}
