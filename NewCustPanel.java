/*
 CSCI213 Assignment 2
 ----------------------------------------------------------- 
 File name: NewCustPanel.java
 Author: Tan Shi Terng Leon
 Registration Number: 4000602
 Description: NewCustPanel class: Allows a new user to create an account
 */

/**
 * @author Leon
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NewCustPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private GridBagLayout layout;
	private GridBagConstraints c;
	private JTextField nameTextF, usernameTextF, addressTextF, contactTextF;
	private JPasswordField  passwordField;
	private JButton registerButton;
	public JLabel msgLabel;
	private ImageIcon smileyImg;
	
	public NewCustPanel() {
		
		//Sets the image
		smileyImg = new ImageIcon("smiley.jpg");
		MainCoffee.scaleImg(smileyImg, 100, 100);
		
		//Defining layout to be used
		layout = new GridBagLayout();
		c = new GridBagConstraints();
		
		//Initializing constraints
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		
		//Sets the layout
		setLayout(layout);
		
		//Defining the text fields
		nameTextF = new JTextField(15);
		usernameTextF = new JTextField(15);
		passwordField = new JPasswordField(15);
		addressTextF = new JTextField(15);
		contactTextF = new JTextField(15);
		
		//Add listeners to text fields
		nameTextF.addActionListener(new RegisterButtonListener());
		usernameTextF.addActionListener(new RegisterButtonListener());
		passwordField.addActionListener(new RegisterButtonListener());
		addressTextF.addActionListener(new RegisterButtonListener());
		contactTextF.addActionListener(new RegisterButtonListener());
		
		//Sets mouse over text
		nameTextF.setToolTipText("Enter your name here");
		usernameTextF.setToolTipText("Enter your username here");
		passwordField.setToolTipText("Enter your password here");
		addressTextF.setToolTipText("Enter your address here");
		contactTextF.setToolTipText("Enter your contact no. here");
		
		//Defining the register button
		registerButton = new JButton("Register");
		registerButton.setBackground(Color.yellow);
		registerButton.addActionListener(new RegisterButtonListener());	//Adds listener
		registerButton.setToolTipText("Click to register");
		
		//Adds the textfields
		addComponent(new JLabel("Step 2b - New Customer"), 0, 0, 2, 1);
		addComponent(new JLabel("Name"), 1, 0, 1, 1);
		addComponent(nameTextF, 1, 1, 1, 1);
		addComponent(new JLabel("Username: "), 2, 0, 1, 1);
		addComponent(usernameTextF, 2, 1, 1, 1);
		addComponent(new JLabel("Password: "), 3, 0, 1, 1);
		addComponent(passwordField, 3, 1, 1, 1);
		addComponent(new JLabel("Address: "), 4, 0, 1, 1);
		addComponent(addressTextF, 4, 1, 1, 1);
		addComponent(new JLabel("Contact No.: "), 5, 0, 1, 1);
		addComponent(contactTextF, 5, 1, 1, 1);
		
		//Sets constraints for the register button
		c.weightx = 2;
		c.weighty = 2;
		c.fill = GridBagConstraints.BOTH;
		
		//Adds the register button
		addComponent(registerButton, 6, 1, 1, 1);
		
		//Adds the message label
		msgLabel = new JLabel();
		addComponent(msgLabel, 7, 0, 2, 1);
		
		//Adds a 5 pixel spacing around the border
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
	}
	
	//Adds a component specifying the row, column, width and height
	private void addComponent(Component comp, int row, int col, int width, int height) {
		
		c.gridy = row;
		c.gridx = col;
		c.gridwidth = width;
		c.gridheight = height;
		
		layout.setConstraints(comp, c);
		add(comp);
	}
	
	//Listener when user registers information
	class RegisterButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {

			try {
				
				hasEmptyFields();	//Throws an exception if there are fields left empty
				
				if (ExistingCustPanel.userExist(usernameTextF.getText()))	//If user already exist
					throw new Exception("This username already exist!");
				
				validContact(contactTextF.getText());	//Throws an exception if contact no. is invalid
				
				//Stores information of the user to be registered
				MainCoffee.user = new Customer(nameTextF.getText(), usernameTextF.getText(), 
						String.valueOf(passwordField.getPassword()), addressTextF.getText(), 
						contactTextF.getText());
				
				//Ask the user if he/she wants to confirms to register as a new customer
				int confirmNewCust = JOptionPane.showConfirmDialog(null, "Create new account?", "Confirm?", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if (confirmNewCust == 0) {	//If confirm
					
					//Resets the text fields
					nameTextF.setText(null);
					usernameTextF.setText(null);
					passwordField.setText(null);
					addressTextF.setText(null);
					contactTextF.setText(null);
					
					//Clears the message label
					msgLabel.setText(null);
					
					//Clears the existingCustPanel message label also
					MainCoffee.existingCustPanel.msgLabel.setText(null);
					
					//Stores new customer in the records
					saveNewCust();
					
					//String showing the new customer details
					String newCustInfo = "Name: " + MainCoffee.user.getName() + "\nUsername: " + MainCoffee.user.getUsername()
							+ "\nAddress: " + MainCoffee.user.getAddress() + "\nContact No.: " + MainCoffee.user.getContact();
					
					//Welcomes the user and shows that his/her account has been successfully created
					JOptionPane.showMessageDialog(null, "Welcome " + MainCoffee.user.getName() 
							+ "!\n\nSuccessfully created new customer account!\n\n" + newCustInfo, 
							"Account created!", JOptionPane.INFORMATION_MESSAGE, smileyImg);
					
					//Displays the current user in the label
					MainCoffee.loginUserLabel.setText("Logged in as user: " + MainCoffee.user.getUsername());
					
					MainCoffee.existingCustPanel.setVisible(false);	//Hides existing customer panel
					MainCoffee.newCustPanel.setVisible(false);		//Hides new customer panel
					MainCoffee.deliveryPanel.setVisible(true);		//Displays delivery panel (next step)
				}
				else{	//Create account not confirmed
					MainCoffee.user.reset();	//Resets the current user as he is not registered
				}
				
			}
			catch (Exception e) {
				
				msgLabel.setText(e.getMessage());
				JOptionPane.showMessageDialog(null, e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//Checks if there are empty fields (throws an Exception if there are)
	private void hasEmptyFields() throws Exception {
		
		String errorMsg = "Please enter your ";
		boolean empty = false;
			
		if (nameTextF.getText().isEmpty()) {	//If name field is empty
			errorMsg += "Name";
			empty = true;
		}
		else if (usernameTextF.getText().isEmpty()) {	//If username field is empty
			errorMsg += "Username";
			empty = true;
		}
		else if (String.valueOf(passwordField.getPassword()).isEmpty()) {	//If password field is empty
			errorMsg += "Password";
			empty = true;
		}
		else if (addressTextF.getText().isEmpty()) {	//If address field is empty
			errorMsg += "Address";
			empty = true;
		}
		else if (contactTextF.getText().isEmpty()) {	//If contact no. field is empty
			errorMsg += "Contact No.";
			empty = true;
		}
		
		if (empty == true) {		//Throws exception if a field is empty
			throw new Exception(errorMsg);
		}
		
	}
	
	//Checks if contact no. given is valid (throws an Exception if it is not)
	private void validContact(String contact) throws Exception {
		
		boolean valid = true;
		String errorMsg = null;
		
		try {
			if (contact.length() != 9) {			//If the length is not 9 digits long
				errorMsg = "Contact no. must be 9 digit";
				throw new Exception(errorMsg);
			}
			
			c.gridx = Integer.parseInt(contact);	//Throws exception if its not a number
		}
		catch (NumberFormatException e) {			//If not a number
			errorMsg = "Contact no. must be a numerical value";
			valid = false;
		}
		catch (Exception e) {
			valid = false;
		}
		
		if (valid == false) {						//Throws an exception if contact no. is not valid
			throw new Exception(errorMsg);
		}
	}
	
	//Stores new customer details in records
	private void saveNewCust() {
		try {
			//Opens file to append to
			FileWriter fileWriter = new FileWriter(new File(MainCoffee.CUSTOMERFILE), true);
			
			//Appends the new customer's details in the given format (name;username;password;address;contact)
			fileWriter.append(MainCoffee.user.getName() + ";" + MainCoffee.user.getUsername() + ";"
					+ MainCoffee.user.getPassword() + ";" + MainCoffee.user.getAddress() + ";" 
					+ MainCoffee.user.getContact() + "\n");
			
			//Closes the file writer
			fileWriter.close();
		} 
		catch (IOException e) {	//If file not found
			JOptionPane.showMessageDialog(null, e.getMessage(), "File not found!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
