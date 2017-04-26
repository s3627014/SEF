package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import errors.InstanceNotFound;
import main.CourseOffering;
import main.Reader;
import main.Student;
import main.User;

import java.awt.Font;

public class StudentFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInFrame frame = new LogInFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StudentFrame(String userID) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 941, 635);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Jlist(userID);

		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					LogInFrame regFace = new LogInFrame();
					regFace.setVisible(true);
					dispose();

				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


		btnLogOut.setBounds(363, 263, 185, 42);
		contentPane.add(btnLogOut);

		JButton btnTestButton = new JButton("My Courses");
		btnTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Student student = new Student();
				try {
					student.listCourses();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnTestButton.setBounds(363, 121, 185, 42);
		contentPane.add(btnTestButton);
		
		JLabel lblEnrolments = new JLabel("Welcome USER");
		lblEnrolments.setFont(new Font("Dialog", Font.BOLD, 39));
		lblEnrolments.setBounds(319, 12, 328, 32);
		contentPane.add(lblEnrolments);
		
		JButton btnCourseOfferings = new JButton("Course Offerings");
		btnCourseOfferings.setBounds(363, 191, 185, 42);
		contentPane.add(btnCourseOfferings);
	}

	private void Jlist(String userID) {
		//states an array
		DefaultListModel<String> listModel = new DefaultListModel<>();
		
		// Load user 
		Reader reader = new Reader();
		User user = null;
		try {
			user = reader.LoadUser(userID);
		} catch (InstanceNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (user instanceof Student) {
			Student student = (Student) user;
			
			try {
				ArrayList<CourseOffering> courses = student.listCourses();
				for (CourseOffering course : courses){
					listModel.addElement(course.getCourse().getCourseName());

				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
