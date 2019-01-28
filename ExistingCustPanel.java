/*
 CSCI213 Assignment 2
 ----------------------------------------------------------- 
 File name: ExistingCustPanel.java
 Author: Tan Shi Terng Leon
 Registration Number: 4000602
 Description: ExistingCustPanel class: Allows existing user to login with username and password
 */

/**
 * @author Leon
 *
 */

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.util.Scanner;
import java.io.*;

class ExistingCustPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	//For the layout
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	//Components
	private JTextField usernameTF;
	private JPasswordField passwordField;
	private JButton loginButton;
	public JLabel msgLabel;
	
	private static String[] custInfo;	//Stores customer information retrieved from file
	private String username, password;	//Stores user's input
	
	ExistingCustPanel() {
		
		//Setting the layout
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		
		setLayout(layout);
		
		//Initializing constraints
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		//First component fills the entire first row
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		
		addComponent(new JLabel("Step 2a - Existing Customer"));
		
		//Fills the entire second row
		addComponent(new JLabel("Please Login."));
		
		//Start from the 1st column
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		addComponent(new JLabel("Username: "));
		
		//Fills the remaining column
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		usernameTF = new JTextField();
		usernameTF.addActionListener(new LoginButtonListener());	//Add listener
		usernameTF.setToolTipText("Enter your username here");
		addComponent(usernameTF);
		
		//Starts from the 1st column
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		addComponent(new JLabel("Password: "));
		
		//Fills the remaining column
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		passwordField = new JPasswordField();
		passwordField.addActionListener(new LoginButtonListener());	//Add listener
		passwordField.setToolTipText("Enter you password here");
		addComponent(passwordField);
		
		//Starts from the 1st column
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		addComponent(new JPanel());								//Fills empty space
		
		//Fills the remaining columns
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		loginButton = new JButton("Login");							//Creating login button
		loginButton.setToolTipText("Click to login");				//Set mouse over text
		loginButton.addActionListener(new LoginButtonListener());	//Add listener
		addComponent(loginButton);
		
		//Adds a label to display message
		msgLabel = new JLabel();
		addComponent(msgLabel);
		
		//Creates a buffer spacing around the border
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	//Adds a component according to the constraints given
	private void addComponent(Component component) {
		
		layout.setConstraints(component, constraints);
		add(component);
	}
	
	//When user logs in
	class LoginButtonListener implements ActionListener {
		
		public void actionPerformed (ActionEvent e) {
			
			username = usernameTF.getText();						//Stores username input
			password = new String(passwordField.getPassword());		//Stores password input
			
			//Resets the text and password field
			usernameTF.setText(null);
			passwordField.setText(null);
			
			if (userExist(username)) {	//If user exist in records
				
				if (password.equals(custInfo[2])) {	//If password entered correctly
					
					//Stores user information
					MainCoffee.user = new Customer(custInfo[0].trim(), custInfo[1].trim(), custInfo[2].trim(), 
							custInfo[3].trim(), custInfo[4].trim());
					
					MainCoffee.deliveryPanel.setVisible(true);			//Displays delivery panel
					MainCoffee.existingCustPanel.setVisible(false);		//Hides the existing customer panel
					MainCoffee.newCustPanel.setVisible(false);			//Hides the new customer panel
					
					//Display welcome/login successful message
					JOptionPane.showMessageDialog(null, "Welcome " + MainCoffee.user.getName(), "Login Successful", 
							JOptionPane.INFORMATION_MESSAGE, MainCoffee.deliveryPanel.coffeeImg);
					
					//Message label displays nothing
					msgLabel.setText(null);
					
					//Clears the newCustPanel's message label also
					MainCoffee.newCustPanel.msgLabel.setText(null);
					
					//Displays the current username in the label
					MainCoffee.loginUserLabel.setText("Logged in as user: " + MainCoffee.user.getUsername());
				}
				else {	//If incorrect password entered
					msgLabel.setText("Username/Password Invalid. Please try again.");
					
					JOptionPane.showMessageDialog(null, "Invalid password!", "Login Unsuccessful", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else {	//If user does not exist
				msgLabel.setText("No such user exists!");
				JOptionPane.showMessageDialog(null, "No such user!", "Login Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//Checks if user exist in the records
	public static boolean userExist(String username) {
		try {
			Scanner custFile = new Scanner(new File(MainCoffee.CUSTOMERFILE));	//Opens file for scanning
			
			while (custFile.hasNextLine()) {				//Scans file line by line
				
				custInfo = custFile.nextLine().split(";");	//Getting relevant info from each line
				if (custInfo[1].equals(username)) {			//If username found
					return true;
				}
			}
			
		}
		catch (FileNotFoundException exception) {			//If file not found
			JOptionPane.showMessageDialog(null, "File "+ MainCoffee.CUSTOMERFILE + "not found!", 
					"Login Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return false;										//If username not found
	}
	
}
