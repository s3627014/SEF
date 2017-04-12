package test;

import static org.junit.Assert.*;
import main.Course;
import main.Reader;
import main.User;

import org.junit.Test;

import errors.*;

public class TestReader {

	@Test (timeout=3000)
	public void testStudentLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		User user = reader.LoadUser("s12345");
		assertEquals(user.getFirstName(), "John");
	}

	@Test (timeout=3000)
	public void testStaffLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		User user = reader.LoadUser("e12345");
		assertEquals(user.getFirstName(), "Halil");
	}

	@Test (timeout=3000)
	public void testProgramCoordinatorLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		User user = reader.LoadUser("e23456");
		assertEquals(user.getFirstName(), "CompSciGuy");
	}

	@Test (timeout=3000)
	public void testAdminLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		User user = reader.LoadUser("a12345");
		assertEquals(user.getFirstName(), "Bland");
	}

	@Test (timeout=3000)
	public void testCourseLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		Course course = reader.LoadCourse("c12345");
		assertEquals(course.getCourseName(), "Software Engineering Fundamentals");
	}

}
