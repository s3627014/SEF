package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.Admin;
import main.Course;
import main.CourseOffering;
import main.Mark;
import main.ProgramCoordinator;
import main.Reader;
import main.Student;
import main.User;

import org.junit.Test;

import errors.*;

public class TestReader {

	@Test (timeout=5000)
	public void testStudentLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		Student user = (Student) reader.LoadUser("s12345");
		assertEquals(user.getFirstName(), "John");
		assertEquals(user.getLastName(), "Smith");
		assertEquals(user.getOverloadPerms().getSemesters().size(), 1);
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
		ProgramCoordinator user = (ProgramCoordinator) reader.LoadUser("e23456");
		assertEquals(user.getFirstName(), "CompSciGuy");
		assertEquals(user.getLastName(), "Master");
	}

	@Test (timeout=5000)
	public void testAdminLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		Admin user = (Admin) reader.LoadUser("a12345");
		assertEquals(user.getFirstName(), "Bland");
		assertEquals(user.getLastName(), "Blanderson");
	}

	@Test (timeout=5000)
	public void testCourseLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		Course course = reader.LoadCourse("c23456");
		assertEquals(course.getCourseName(), "Database Concepts");
		assertEquals(course.getDescription(), "A class about databases.");
		assertEquals(course.getTopics().size(), 2);
		course.setPrereqs(reader.LoadPrereqs("COURSE", course.getCourseID()));
		assertEquals(course.getPrereqs().size(), 1);
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
		assertEquals(marks.get(1).getResult(), "71");
	}

	@Test (timeout=5000)
	public void testOffersLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		ArrayList<CourseOffering> offers = reader.LoadOfferings("TEACHER", "e12345");
		assertEquals(offers.size(), 2);
	}

	@Test (timeout=5000)
	public void testStudentClassesLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		ArrayList<CourseOffering> offers = reader.LoadClasses("s12345");
		assertEquals(offers.size(), 2);
		offers = reader.LoadClasses("s23456");
		assertEquals(offers.size(), 0);
	}

	@Test (timeout=5000)
	public void testTeacherClassesLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		ArrayList<CourseOffering> offers = reader.LoadClasses("e12345");
		assertEquals(offers.size(), 2);
	}
}
