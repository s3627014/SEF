package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.Course;
import main.Reader;

import org.junit.Test;

import errors.InstanceNotFound;
import errors.PasswordIncorrect;

public class TestCourse {

	@Test (timeout=3000)
	public void testCourseLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		Course course = reader.LoadCourse("c12345");
		assertEquals(course.getCourseName(), "Software Engineering Fundamentals");
	}

	@Test (timeout=3000)
	public void testPrereqs() throws InstanceNotFound {
		Reader reader = new Reader();
		Course course = reader.LoadCourse("c23456");
		ArrayList<Course> prereqs = new ArrayList<Course>();
		prereqs.add(reader.LoadCourse("c12345"));
		
		assertTrue(course.checkPrerequisites(prereqs));
	}

	@Test (timeout=3000)
	public void testWrongPrereqs() throws InstanceNotFound {
		Reader reader = new Reader();
		Course course = reader.LoadCourse("c23456");
		ArrayList<Course> prereqs = new ArrayList<Course>();
		
		assertFalse(course.checkPrerequisites(prereqs));
	}

}
