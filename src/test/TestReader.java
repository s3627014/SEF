package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.Course;
import main.CourseOffering;
import main.Mark;
import main.Reader;
import main.User;

import org.junit.Test;

import errors.*;

public class TestReader {

	@Test (timeout=5000)
	public void testStudentLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		User user = reader.LoadUser("s12345");
		assertEquals(user.getFirstName(), "John");
	}

	@Test (timeout=5000)
	public void testStaffLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		User user = reader.LoadUser("e12345");
		assertEquals(user.getFirstName(), "Halil");
	}

	@Test (timeout=5000)
	public void testProgramCoordinatorLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		User user = reader.LoadUser("e23456");
		assertEquals(user.getFirstName(), "CompSciGuy");
	}

	@Test (timeout=5000)
	public void testAdminLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		User user = reader.LoadUser("a12345");
		assertEquals(user.getFirstName(), "Bland");
	}

	@Test (timeout=5000)
	public void testCourseLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		Course course = reader.LoadCourse("c12345");
		assertEquals(course.getCourseName(), "Software Engineering Fundamentals");
	}
	
	@Test (timeout=5000)
	public void testOfferLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		CourseOffering offer = reader.LoadOffering("o12345");
		assertEquals(offer.getCourse().getCourseName(), "Software Engineering Fundamentals");
	}

	@Test (timeout=5000)
	public void testMarksLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		ArrayList<Mark> marks = reader.LoadMarks("STUDENT", "s12345");
		assertEquals(marks.get(0).getResult(), "82");
	//	assertEquals(marks.get(1).getResult(), "71");
	}
}
