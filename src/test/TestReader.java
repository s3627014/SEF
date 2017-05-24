package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import main.Admin;
import main.Course;
import main.CourseOffering;
import main.DateTime;
import main.InternalMark;
import main.Mark;
import main.ProgramCoordinator;
import main.Reader;
import main.Staff;
import main.Student;
import main.User;

import org.junit.Test;

import errors.*;

public class TestReader {

	@Test (timeout=15000)
	public void testStudentLoad() throws Exception {
		Reader reader = new Reader();
		Student user = (Student) reader.LoadUser("s12345");
		assertEquals(user.getFirstName(), "John");
		assertEquals(user.getLastName(), "Smith");
		assertEquals(user.getOverloadPerms().getSemesters().size(), 2);
	}

	@Test (timeout=15000)
	public void testStaffLoad() throws Exception {
		Reader reader = new Reader();
		User user = reader.LoadUser("e12345");
		assertEquals(user.getFirstName(), "Halil");
	}

	@Test (timeout=15000)
	public void testProgramCoordinatorLoad() throws Exception {
		Reader reader = new Reader();
		Staff user = (Staff) reader.LoadUser("e23456");
		assertEquals(user.getFirstName(), "CompSciGuy");
		assertEquals(user.getLastName(), "Master");
	}

	@Test (timeout=15000)
	public void testAdminLoad() throws Exception {
		Reader reader = new Reader();
		Admin user = (Admin) reader.LoadUser("a12345");
		assertEquals(user.getFirstName(), "Bland");
		assertEquals(user.getLastName(), "Blanderson");
	}

	@Test (timeout=15000)
	public void testCourseLoad() throws Exception {
		Reader reader = new Reader();
		Course course = reader.LoadCourse("c23456");
		assertEquals(course.getCourseName(), "Database Concepts");
		assertEquals(course.getDescription(), "A class about databases.");
		assertEquals(course.getTopics().size(), 2);
		course.setPrereqs(reader.LoadPrereqs("COURSE", course.getCourseID()));
		assertEquals(course.getPrereqs().size(), 1);
	}

	@Test (timeout=15000)
	public void testCourseSearch() throws Exception {
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

	@Test (timeout=15000)
	public void testLoadAllCourses() throws Exception {
		Reader reader = new Reader();
		ArrayList<Course> courses = reader.LoadAllCourses();

		assertEquals(courses.size(), 3);
	}

	@Test (timeout=15000)
	public void testOfferLoad() throws Exception {
		Reader reader = new Reader();
		CourseOffering offer = reader.LoadOffering("o12345");
		assertEquals(offer.getCourse().getCourseName(), "Software Engineering Fundamentals");
	}

	@Test (timeout=15000)
	public void testMarksLoad() throws Exception {
		Reader reader = new Reader();
		ArrayList<Mark> marks = reader.LoadMarks("STUDENT", "s12345");
		InternalMark intlMark = (InternalMark) marks.get(0);
		assertEquals(intlMark.getFinalised(), true);
		assertEquals(marks.get(1).getResult(), "71");
	}

	@Test (timeout=15000)
	public void testMarksSave() throws Exception {
		Reader reader = new Reader();
		Student student = (Student) reader.LoadUser("s12345");
		CourseOffering offer = reader.LoadOffering("o12345");
		InternalMark mark = new InternalMark(student, offer, "61", true);
		reader.SaveMark(mark);
	}

	@Test (timeout=15000)
	public void testOffersLoad() throws Exception {
		Reader reader = new Reader();
		ArrayList<CourseOffering> offers = reader.LoadOfferings("TEACHER", "e12345");
		assertEquals(offers.size(), 2);
	}

	@Test (timeout=15000)
	public void testOfferSave() throws Exception {
		Reader reader = new Reader();
		DateTime time = new DateTime(0,1,2017);
		Staff teacher = (Staff) reader.LoadUser("e12345");
		Course course = reader.LoadCourse("c12345");
		CourseOffering offer = new CourseOffering("o12345", time, course, teacher, 100, new ArrayList<Student>());
		offer.checkIfFull();

		reader.SaveOffering(offer);
	}

	@Test (timeout=15000)
	public void testStudentClassesLoad() throws InstanceNotFound, SQLException {

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

	@Test (timeout=15000)
	public void testTeacherClassesLoad() throws Exception {
		Reader reader = new Reader();
		ArrayList<CourseOffering> offers = reader.LoadClasses("e12345");
		assertEquals(offers.size(), 2);
	}

	@Test (timeout=15000)
	public void testUserSave() throws Exception {
		Reader reader = new Reader();
		User user = new Student("s45678", "pass", "Luke", "Lukington", null);
		reader.SaveUser(user);

		user = (Student) reader.LoadUser("s45678");
		assertEquals(user.getFirstName(), "Luke");
		assertEquals(user.getLastName(), "Lukington");
	}

	@Test (timeout=15000)
	public void testCourseSave() throws Exception {
		Reader reader = new Reader();
		ArrayList<Course> prereqs = new ArrayList<Course>();
		prereqs.add(reader.LoadCourse("c12345"));
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("A testing topic.");
		topics.add("Another testing topic.");
		Course course = new Course("Testing Course", "c34567", "A testing course.",
				new Staff("e12345", "pass", "Halil", "Ali"), prereqs, topics);
		reader.SaveCourse(course);


		course = reader.LoadCourse("c34567");
		assertEquals(course.getCourseName(), "Testing Course");
		assertEquals(course.getDescription(), "A testing course.");
	}

	@Test (timeout=15000)
	public void testOverloadPermSave() throws Exception {
		Reader reader = new Reader();
		DateTime semester = new DateTime(1,1,2012);
		reader.SaveOverloadPerm("s12345", semester);
	}
}
