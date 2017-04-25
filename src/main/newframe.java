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

public class newframe extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					newframe frame = new newframe();
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
	public newframe() {
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

	private void Jlist() {

		JList<String> list;
		//states an array
		DefaultListModel<String> listModel = new DefaultListModel<>();
		//change when database is up and running
		Student student = new Student();

		User user = new User();


		Reader reader = new Reader();
		try {
			ResultSet offeringList = student.listCourses();

			while(offeringList.next()){
				System.out.println("1");
				String offeringID = offeringList.getString("OFFERING");
				ResultSet course = reader.SearchDB("ASS1_OFFERINGS","OFFERID",offeringID);
				course.next();
				System.out.println("2");
				String courseID = course.getString("COURSE");
				 course = reader.SearchDB("ASS1_COURSES","COURSEID",courseID);
				 course.next();
				listModel.addElement(course.getString("COURSENAME"));


			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		list = new JList<>(listModel);
		list.setBounds(26, 28, 857, 234);
		contentPane.add(list);
	    list.addMouseListener(new MouseAdapter(){
	          @Override
	          public void mouseClicked(MouseEvent e) {
	              System.out.println("Mouse click.");
	              int index = list.getSelectedIndex();
	              System.out.println("Index Selected: " + index);
	              String s = (String) list.getSelectedValue();
	              System.out.println("Value Selected: " + s.toString());
	              int response = JOptionPane.showConfirmDialog(null, "Do you want to withdraw from " + s.toString() + "?", "Confirm",
	            	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	            	    if (response == JOptionPane.NO_OPTION) {
	            	      System.out.println("No button clicked");
	            	    } else if (response == JOptionPane.YES_OPTION) {
	            	      System.out.println("Yes button clicked");
	            	      Reader reader = new Reader();
	            	      try {
							reader.deleteRecord();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	    } else if (response == JOptionPane.CLOSED_OPTION) {
	            	      System.out.println("JOptionPane closed");
	            	    }
	          }
	    });
		String t = list.getSelectedValue();
		System.out.println(t);
	}

}
