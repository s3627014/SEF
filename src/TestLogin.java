import static org.junit.Assert.*;
import org.junit.*;

public class TestLogin {
	
	@Before
	public void setUp () {
		
	}

	@Test
	public void everythingRightTest () throws Exception {
		User user = User.Login("s11111", "pass");
		assertEquals("John", user.fName);
		user = User.Login("s22222", "pass");
		assertEquals("Jane", user.fName);
		user = User.Login("s33333", "pass");
		assertEquals("Mohammed", user.fName);
	}

	@Test (expected=UserNotFound.class)
	public void wrongUsernameTest () throws Exception {
		User user = User.Login("s00000", "pass");
		user = User.Login("s22222", "pass");
		user = User.Login("s33333", "pass");
	}

	@Test (expected=PasswordIncorrect.class)
	public void wrongPasswordTest () throws Exception {
		User user = User.Login("s11111", "wrong");
		user = User.Login("s22222", "pass");
		user = User.Login("s33333", "pass");
	}

	@After
	public void cleanUp () {
		
	}
}
