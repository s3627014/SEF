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

import errors.InstanceNotFound;
import main.CourseOffering;
import main.Reader;
import main.Student;
import main.User;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class newframe extends JFrame {

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
	public newframe(String userID) {
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


		btnLogOut.setBounds(712, 478, 171, 41);
		contentPane.add(btnLogOut);

		JButton btnTestButton = new JButton("TEST BUTTON");
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
		btnTestButton.setBounds(220, 467, 185, 27);
		contentPane.add(btnTestButton);
	}

	private void Jlist(String userID) {

		JList<String> list;
		//states an array
		DefaultListModel<String> listModel = new DefaultListModel<>();
		
		// Get the user
		Reader reader = new Reader();
		User user = null;
		try {
			user = reader.LoadUser(userID);
		} catch (InstanceNotFound e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Different display for different user types
		if (user instanceof Student){

			// Existing student stuff (changed to use new student .listCourses)
			Student student = (Student) user;
			ArrayList<CourseOffering> offers;
			try {
				offers = student.listCourses();
				for (CourseOffering offer : offers) {
					listModel.addElement(offer.getCourse().getCourseName());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list = new JList<>(listModel);
			list.setBounds(26, 28, 857, 234);
			contentPane.add(list);
		}
		
	}
}
