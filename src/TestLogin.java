import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLogin {
	Reader reader;
	
	@Before
	public void setUp () {
		reader = new Reader();
	}

	@Test
	public void test () throws UserNotFound {
		User user = reader.LoadUser("s11111");
		assertEquals("John", user.fName);
		user = reader.LoadUser("s22222");
		assertEquals("Jane", user.fName);
		user = reader.LoadUser("s33333");
		assertEquals("Mohammed", user.fName);
	}

	@After
	public void cleanUp () {
		
	}
}
