package test;

import static org.junit.Assert.*;
import main.Reader;

import org.junit.Test;

import errors.*;

public class TestReader {

	@Test
	public void testUserLoad() throws InstanceNotFound {
		Reader.LoadUser("s11111");
	}

}
