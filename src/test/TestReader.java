package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.Admin;
import main.Course;
import main.CourseOffering;
import main.DateTime;
import main.Mark;
import main.ProgramCoordinator;
import main.Reader;
import main.Staff;
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
		assertEquals(user.getOverloadPerms().getSemesters().size(), 2);
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
		Staff user = (Staff) reader.LoadUser("e23456");
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
	public void testCourseSearch() throws InstanceNotFound {
		Reader reader = new Reader();
		ArrayList<Course> courses = reader.SearchForCourse("Database");
		assertEquals(courses.get(0).getCourseName(), "Database Concepts");
		assertEquals(courses.get(0).getDescription(), "A class about databases.");
		assertEquals(courses.get(0).getTopics().size(), 2);
		courses.get(0).setPrereqs(reader.LoadPrereqs("COURSE", courses.get(0).getCourseID()));
		assertEquals(courses.get(0).getPrereqs().size(), 1);
		
		courses = reader.SearchForCourse("c23456");
		assertEquals(courses.get(0).getCourseName(), "Database Concepts");
		assertEquals(courses.get(0).getDescription(), "A class about databases.");
		assertEquals(courses.get(0).getTopics().size(), 2);
		courses.get(0).setPrereqs(reader.LoadPrereqs("COURSE", courses.get(0).getCourseID()));
		assertEquals(courses.get(0).getPrereqs().size(), 1);
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
		
		// Enrol in two classes
		reader.SaveEnrolment("s12345", "o12345");
		reader.SaveEnrolment("s12345", "o23456");
		
		// Check classes
		ArrayList<CourseOffering> offers = reader.LoadClasses("s12345");
		assertEquals(offers.size(), 2);
		
		// Unenrol from one class
		reader = new Reader();
		reader.DeleteEnrolment("s12345", "o12345");
		
		// Check classes
		offers = reader.LoadClasses("s12345");
		assertEquals(offers.size(), 1);
		
		// Delete other made class
		reader.DeleteEnrolment("s12345", "o23456");
	}

	@Test (timeout=5000)
	public void testTeacherClassesLoad() throws InstanceNotFound {
		Reader reader = new Reader();
		ArrayList<CourseOffering> offers = reader.LoadClasses("e12345");
		assertEquals(offers.size(), 2);
	}
	
	@Test (timeout=5000)
	public void testUserSave() throws Exception {
		Reader reader = new Reader();
		User user = new Student("s45678", "pass", "Luke", "Lukington", null);
		reader.SaveUser(user);
		
		user = (Student) reader.LoadUser("s45678");
		assertEquals(user.getFirstName(), "Luke");
		assertEquals(user.getLastName(), "Lukington");
	}
	
	@Test (timeout=5000)
	public void testCourseSave() throws Exception {
		Reader reader = new Reader();
		Course course = new Course("Testing Course", "c34567", "A testing course.", 
				new Staff("e12345", "pass", "Halil", "Ali"), new ArrayList<String>());
		reader.SaveCourse(course);
		
		course = reader.LoadCourse("c34567");
		assertEquals(course.getCourseName(), "Testing Course");
		assertEquals(course.getDescription(), "A testing course.");
	}
	
	@Test (timeout=5000)
	public void testOverloadPermSave() throws Exception {
		Reader reader = new Reader();
		DateTime semester = new DateTime(1,1,2012);
		reader.SaveOverloadPerm("s12345", semester);
	}
}
