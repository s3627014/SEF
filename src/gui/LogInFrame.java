package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

import errors.*;
import main.User;

public class LogInFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtPassWord;

	/**
	 * Launch the application.
	 */
	public static void initialise() {
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
	public LogInFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 941, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLogin = new JButton("Login");
		getRootPane().setDefaultButton(btnLogin);
		btnLogin.setBounds(644, 203, 171, 41);
		contentPane.add(btnLogin);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(220, 67, 595, 41);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		txtPassWord = new JTextField();
		txtPassWord.setBounds(220, 136, 595, 39);
		contentPane.add(txtPassWord);
		txtPassWord.setColumns(10);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(55, 71, 156, 33);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(55, 139, 156, 33);
		contentPane.add(lblPassword);
		
		JLabel lblCourseManager = new JLabel("Course Manager");
		lblCourseManager.setFont(new Font("Dialog", Font.BOLD, 31));
		lblCourseManager.setBounds(388, 12, 240, 33);
		contentPane.add(lblCourseManager);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = txtUserName.getText();
				String passWord = txtPassWord.getText();
				
				try {
					User.login(userName, passWord);
					newframe regFace = new newframe(userName);
					regFace.setVisible(true);
					dispose();
				}
				catch (InstanceNotFound err){
					JOptionPane.showMessageDialog(null, "User not found.");
					txtUserName.setText("");
					txtPassWord.setText("");
					txtUserName.requestFocus();
				}
				catch (PasswordIncorrect err){
					JOptionPane.showMessageDialog(null, "Password incorrect.");
					txtUserName.setText("");
					txtPassWord.setText("");
					txtUserName.requestFocus();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		
	}
}
