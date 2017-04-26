package main;

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
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
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
					StudentFrame frame = new StudentFrame();
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
	public StudentFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 941, 635);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Jlist();

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

	private void Jlist() {
		//states an array
		DefaultListModel<String> listModel = new DefaultListModel<>();
		//change when database is up and running
		Student student = new Student();
		try {
			ResultSet courses = student.listCourses();
			while(courses.next()){
				listModel.addElement(courses.getString("COURSENAME"));

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
