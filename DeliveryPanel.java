/*
 CSCI213 Assignment 2
 ----------------------------------------------------------- 
 File name: DeliveryPanel.java
 Author: Tan Shi Terng Leon
 Registration Number: 4000602
 Description: DeliveryPanel class: Sets the date and time of delivery
 */

/**
 * @author Leon
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class DeliveryPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static final String[] DELIVERYTIME = {"9am-12pm", "1pm-6pm", "7pm-10pm"};
	
	//Latest order is set to 5 months from current date
	private static final int DAYLIMIT = 0, MONTHLIMIT = 5, YEARLIMIT = 0;
	
	//Components of the panel
	private JTextField deliveryDateTF;
	private JRadioButton timingRB1, timingRB2, timingRB3;
	private JButton confirmButton, logoutButton;
	private JLabel msgLabel;
	private ButtonGroup buttonGrp;
	public ImageIcon coffeeImg;
	
	//Information stored
	private int day, month, year;
	private int orderID;
	private String deliveryTime;
	
	//Formats to display (2 digit format and 9 digits order ID format)
	private DecimalFormat to2Digit, orderFmt;
	
	//Layout and constraints
	private GridBagLayout layout;
	private GridBagConstraints c;
	
	public DeliveryPanel() {
		
		to2Digit = new DecimalFormat("00");			//Used to print the day and month in 2 digits format
		orderFmt = new DecimalFormat("000000000");	//Used to print the order id
		
		//Sets up the image
		coffeeImg = new ImageIcon("coffeeLogo.jpg");
		MainCoffee.scaleImg(coffeeImg, 75, 75);
		
		//Sets lay out
		layout = new GridBagLayout();
		c = new GridBagConstraints();
		
		setLayout(layout);
		
		//Initializing constraints
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		
		//Adds header
		addComponent(new JLabel("Step 3 - Deliver Details"), 0, 0, 2, 1);
		
		//Adds label
		addComponent(new JLabel("Deliver Date (DD/MM/YY)"), 1, 0, 1, 1);
		
		//Adds the text field for delivery date
		deliveryDateTF = new JTextField();
		deliveryDateTF.addActionListener(new ConfirmListener());
		deliveryDateTF.setToolTipText("Enter the date for delivery");
		c.fill = GridBagConstraints.HORIZONTAL;
		addComponent(deliveryDateTF, 1, 1, 1, 1);
		
		//Adds the timing label
		c.fill = GridBagConstraints.BOTH;
		addComponent(new JLabel("Select your preferred times: "), 2, 0, 1, 1);
		
		//Creates a button group
		buttonGrp = new ButtonGroup();
		
		//Creates 3 radio buttons for the timings
		timingRB1 = new JRadioButton("9am-12pm");
		timingRB2= new JRadioButton("1-6pm");
		timingRB3 = new JRadioButton("7-10pm");
		
		//Adds listener
		TimingListener timingListener =  new TimingListener();
		timingRB1.addItemListener(timingListener);
		timingRB2.addItemListener(timingListener);
		timingRB3.addItemListener(timingListener);
		
		//Groups the buttons
		buttonGrp.add(timingRB1);
		buttonGrp.add(timingRB2);
		buttonGrp.add(timingRB3);
		
		//Sets mouse over text
		timingRB1.setToolTipText("Will deliver between " + DELIVERYTIME[0]);
		timingRB2.setToolTipText("Will deliver between " + DELIVERYTIME[1]);
		timingRB3.setToolTipText("Will deliver between " + DELIVERYTIME[2]);
		
		//Adds the radio buttons
		addComponent(timingRB1, 2, 1, 1, 1);
		addComponent(timingRB2, 3, 0, 1, 1);
		addComponent(timingRB3, 3, 1, 1, 1);
		
		//Confirm button
		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ConfirmListener());
		confirmButton.setToolTipText("Click here to confirm your delivery");
		c.fill = GridBagConstraints.NONE;
		addComponent(confirmButton, 4, 0, 1, 1);
		
		//Logout button
		logoutButton = new JButton("Cancel/Logout");
		logoutButton.setToolTipText("Cancel delivery and logout");
		logoutButton.addActionListener(new LogoutListener());
		addComponent(logoutButton, 4, 1, 1, 1);
		
		//Adds msgLabel to display errors or order id
		c.fill = GridBagConstraints.BOTH;
		msgLabel = new JLabel();
		msgLabel.setHorizontalAlignment(SwingConstants.LEFT);
		addComponent(msgLabel, 5, 0, 2, 1);
		
		//Adds loginUserLabel to display the current user
		addComponent(MainCoffee.loginUserLabel, 6, 0, 2, 1);
		
	}
	
	//Adds a component according to the row, column, width and height given
	private void addComponent(Component comp, int row, int col, int width, int height) {
		
		c.gridy = row;
		c.gridx = col;
		c.gridwidth = width;
		c.gridheight = height;
		
		layout.setConstraints(comp, c);
		add(comp);
	}
	
	//Listener for the logout button
	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			logout();						//The current user information is cleared
			
			deliveryDateTF.setText("");		//Resets the date text field
			buttonGrp.clearSelection();		//Resets the radio buttons
			msgLabel.setText("");			//Resets the msgLabel
			setVisible(false);				//Hides the panel
			MainCoffee.loginUserLabel.setText("");	//Clears the login user label (as no user is logged in)
			
			if (MainCoffee.totalDrinks > 0) {	//If order is not empty
				
				//Allows user to login or register
				MainCoffee.existingCustPanel.setVisible(true);
				MainCoffee.newCustPanel.setVisible(true);
			}
			
			//Displays a pop out frame
			JOptionPane.showMessageDialog(null, "Logout successful!", "Logged out", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//Listener for the radio buttons
	class TimingListener implements ItemListener {
		
		//Sets the delivery time
		public void itemStateChanged(ItemEvent event) {
			
			if (event.getSource() == timingRB1)
				deliveryTime = DELIVERYTIME[0];
			else if (event.getSource() ==  timingRB2)
				deliveryTime = DELIVERYTIME[1];
			else
				deliveryTime = DELIVERYTIME[2];
		}
	}
	
	//Listener for the confirm button
	class ConfirmListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			if (MainCoffee.totalDrinks == 0) {							//If no order placed
				JOptionPane.showMessageDialog(null, "Please place your order!", "Empty Order", JOptionPane.ERROR_MESSAGE);
				msgLabel.setText("Please place your order!");
			}
			else if (MainCoffee.user.getUsername() ==  null) {			//If user not logged in
				JOptionPane.showMessageDialog(null, "Please login first", "User not logged in!", JOptionPane.ERROR_MESSAGE);
				msgLabel.setText("Please login first");
			}
			else if (!correctFormat(deliveryDateTF.getText().trim())) {	//If date not in correct format
				JOptionPane.showMessageDialog(null, "Error! Delivery Date must be in the format DD/MM/YY", 
						"Invalid Date Format", JOptionPane.ERROR_MESSAGE);
				msgLabel.setText("Date must be in the format DD/MM/YY");
			}
			else {														//If date and timing are valid
			
				String dateInfo[] = deliveryDateTF.getText().trim().split("/");
				
				try {
					day = Integer.parseInt(dateInfo[0]);
				}
				catch (NumberFormatException e) {	//If day is not a number
					JOptionPane.showMessageDialog(null, "Day is not a number!", "Date Error", JOptionPane.ERROR_MESSAGE);
					msgLabel.setText("Day is not a number!");
					return;
				}
				try {
					month = Integer.parseInt(dateInfo[1]);
				}
				catch (NumberFormatException e) {	//If month is not a number
					JOptionPane.showMessageDialog(null, "Month is not a number!", "Date Error", JOptionPane.ERROR_MESSAGE);
					msgLabel.setText("Month is not a number!");
					return;
				}
				try {
					year = Integer.parseInt(dateInfo[2]);
					year = year + 2000;
				}
				catch (NumberFormatException e) {	//If year is not a number
					JOptionPane.showMessageDialog(null, "Year is not a number!", "Date Error", JOptionPane.ERROR_MESSAGE);
					msgLabel.setText("Year is not a number!");
					return;
				}
				
				//No timing selected
				if (timingRB1.isSelected() == timingRB2.isSelected() == timingRB3.isSelected() == false) {
					JOptionPane.showMessageDialog(null, "Please select a delivery timing!", "Deliver Time Error",
							JOptionPane.ERROR_MESSAGE);
					msgLabel.setText("Please select a delivery timing!");
					return;
				}
				
				//If date is not valid
				if (!validDate(day, month, year)) {
					msgLabel.setText("Invalid date!");
					return;
				}
				
				orderID = generateOrderID();	//Get order ID
				saveOrder();					//Save the order in "orderData.txt"
				saveOrderDrinks();				//Save the drinks ordered ins "orderDrinks.txt"
				
				//Updates the message label
				msgLabel.setText("<HTML>Thank you for your order.<BR>Your order number is " + orderFmt.format(orderID)
						+ "</HTML>");
				
				//Pop up frame to inform user that the order was successful
				JOptionPane.showMessageDialog(null, "Order successfully made!\n\n" +
						"Thank you for your order.\nYour order number is " + orderFmt.format(orderID), 
						"Order made!", JOptionPane.INFORMATION_MESSAGE, coffeeImg);
				
				
				//Resets the text field and the radio buttons and message label and the login user label
				deliveryDateTF.setText("");
				buttonGrp.clearSelection();
				msgLabel.setText("");
				MainCoffee.loginUserLabel.setText("");
				
				//Hides the panel
				setVisible(false);
				
				logout();										//Clears the current logged in user information
				resetOrder();									//Clears the order
				MainCoffee.tabbedPane.setSelectedIndex(0);		//Sets current tab to the intro tab
			}
		}
	}
	
	//Checks if dates if of the correct format
	private boolean correctFormat(String date) {
		
		boolean correct = true;
		
		if (date.length() != 8)										//Checks the length
			correct = false;
		else if (date.charAt(2) != '/' || date.charAt(5) != '/')	//Checks the positions for "/"
			correct = false;
		else if (date.equals("////////"))							//Checks for a string of 8 "/"s
			correct = false;
		
		return correct;
	}
	
	//Generates and returns a unique order ID
	private int generateOrderID () {
		
		int orderID;
		
		Random randomGenerator = new Random();
		
		do {
			orderID = randomGenerator.nextInt(MainCoffee.ORDERIDLIMIT);
		} while(orderIDExist(orderFmt.format(orderID)));	//If order ID already exists
		
		return orderID;
	}
	
	//Returns true if order ID already exists
	private boolean orderIDExist(String orderID) {
		
		try {
			Scanner orderScanner = new Scanner (new File(MainCoffee.ORDERDATAFILE));
			
			//Scans the file
			while (orderScanner.hasNextLine()) {
				
				String orderInfo[] = orderScanner.nextLine().split(";");
				
				if (orderInfo[0].equals(orderID)) {	//If order ID found
					orderScanner.close();
					return true;
				}
			}
			
			orderScanner.close();
			
		} catch (FileNotFoundException e) {	//If file not found
			return false;
		}
		
		return false;	//If order ID does not exist
	}
	
	//Saves the order
	private void saveOrder() {
		
		try {
			//Opens file for writing
			FileWriter orderWriter = new FileWriter(new File(MainCoffee.ORDERDATAFILE), true);
			
			//Writes the order to the end of the file
			orderWriter.write(orderFmt.format(orderID) + ";" + MainCoffee.user.getUsername() + ";"
					+ to2Digit.format(day) + "-" + to2Digit.format(month) + "-" + year + ";" + deliveryTime
					+ ";" + "pending for delivery\n");
			
			orderWriter.close();
			
		} catch (IOException e) {	//If file not found
			
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"File not found!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Saves the ordered drinks
	private void saveOrderDrinks() {
		try {
			//Opens file for writing
			FileWriter orderDrinksWriter = new FileWriter(new File(MainCoffee.ORDERDRINKSFILE), true);
			
			//Writes order to file
			for (int i = 0; i < MainCoffee.NUM_OF_TYPES; i++) {			//For each coffee type
				for (int j = 0; j < MainCoffee.NUM_OF_SIZES; j++) {		//For each size of each coffee type
					if (MainCoffee.orderTypeQty[i][j] > 0) {			//If there is an order
																		//Writes the order
						orderDrinksWriter.write(orderFmt.format(orderID) + ";" + MainCoffee.COFFEETYPE[i] + ";"
								+ MainCoffee.COFFEESIZE[j] + ";" + MainCoffee.orderTypeQty[i][j] + "\n");
					}
				}
			}
			orderDrinksWriter.close();
		} catch (IOException e) {	//If file not found
			
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"File not found!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Clears current logged in user information
	private void logout() {
		MainCoffee.user.reset();
	}
	
	//Clears all the orders
	private void resetOrder() {
		MainCoffee.menuPanel.clearOrder();
	}
	
	//Checks if date is valid
	private boolean validDate (int day, int month, int year) {
			
		int currYear = Calendar.getInstance().get(Calendar.YEAR);	//Gets current year in integer
		int currMonth = Calendar.getInstance().get(Calendar.MONTH);
		int currDay = Calendar.getInstance().get(Calendar.DATE);	//Gets current date in integer

		currMonth++;	//Current month in integer
		
		//Sets the latest date of delivery available
		int latestDay = currDay + DAYLIMIT;
		int latestMonth = currMonth + MONTHLIMIT;
		int latestYear = currYear + YEARLIMIT;
		
		if (latestMonth > 12) {
			latestYear += latestMonth / 12;
			latestMonth = latestMonth % 12;
		}
		
		if (latestDay > 31) {
			latestMonth += latestDay / 31;
			latestDay = latestDay % 31;
		}
		
		boolean isValid = true;
		String errorMsg = null;
			
		if (day < 1 || day > 31)	//Checks for out of range day
		{
			errorMsg = "Invalid day entered! Please re-enter";
			isValid = false;
		}
		else if (month < 1 || month > 12)	//Checks for out of range month
		{
			errorMsg = "Invalid month entered! Please re-enter";
			isValid = false;
		}
		else if ((month == 4 || month == 6|| month == 9|| month == 11)
				 && (day == 31))	//Checks for months that have more than 30 days
		{
			errorMsg = "This day do not exist in the month! Please re-enter";
			isValid = false;
		}
		else
		{
			int febMax, yrsFromALeapYr;
				
			yrsFromALeapYr = 2008 - year;
			
			if (yrsFromALeapYr % 4 != 0)
				febMax = 28;
			else
				febMax = 29;
			
			if (month == 2 && day > febMax)	//Checks for possible leap years
			{
				errorMsg = "This day do not exist! Please re-enter";
				isValid = false;
			}
			else
			{	
				if	((year < currYear) || 
					(year == currYear && month < currMonth) ||
					(year == currYear && month == currMonth && day < currDay))	//Checks if date is in the past
				{
					errorMsg = "Date is in the past! Please re-enter";
					isValid = false;
				}
				else if ((year > latestYear) ||		//Checks date is past the latest available delivery date
						(year == latestYear && month > latestMonth) ||
						(year == latestYear && month == latestMonth && day > latestDay)) {	
					
					errorMsg = "Please set date within 5 months from now";
					isValid = false;
				}
			}
		}
		
		if (isValid == false)
			JOptionPane.showMessageDialog(null, errorMsg, "Date Error!", JOptionPane.ERROR_MESSAGE);
		
		return isValid;
	}
}
