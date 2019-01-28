/*
 CSCI213 Assignment 2
 ----------------------------------------------------------- 
 File name: TrackPanel.java
 Author: Tan Shi Terng Leon
 Registration Number: 4000602
 Description: TrackPanel class: Allows a new user to track an order
 */

/**
 * @author Leon
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

public class TrackPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//Components
	private JTextField orderIDTF;
	private JButton trackButton;
	private JLabel orderLabel;
	private JPanel subPanel;
	private JTextArea orderTA;
	private JScrollPane orderScrollPane;
	
	//Layout and constraints
	private GridBagLayout layout;
	private GridBagConstraints c;
	
	private String[] orderInfo;
	private Font errorFont, normalFont;
	private String orderID;
	private DecimalFormat priceFmt;
	
	public TrackPanel() {
		
		//Font used to show error message and font used to show order details
		errorFont = new Font("Showcard Gothic", Font.PLAIN, 12);
		normalFont = new Font("Times New Romans", Font.PLAIN, 12);
		
		//Format to show price
		priceFmt = new DecimalFormat("$0.00");
		
		//Initializing constraints
		c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		
		//Setting the layout
		setLayout(layout = new GridBagLayout());
		
		//Setting the order ID text field
		orderIDTF = new JTextField(15);
		orderIDTF.addActionListener(new TrackListener());
		orderIDTF.setToolTipText("Enter your order ID here");
		
		//Setting the track button
		trackButton = new JButton("Track");
		trackButton.addActionListener(new TrackListener());
		trackButton.setToolTipText("Track your order!");
		
		//Setting order label
		orderLabel = new JLabel();
		
		//Setting order text area
		orderTA = new JTextArea();
		orderTA.setEditable(false);
		orderScrollPane = new JScrollPane(orderTA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderScrollPane.setVisible(false);		//Only show when order is found
		
		//Creating sub panel and adding components
		subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));
		subPanel.add(new JLabel("Order ID: "));
		subPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		subPanel.add(orderIDTF);
		subPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		subPanel.add(trackButton);
		
		//Adding the rest of the components
		c.anchor = GridBagConstraints.WEST;
		addComponent(new JLabel("Please enter order ID"), 0, 0, 4, 1);
		addComponent(Box.createVerticalGlue(), 1, 0, 4, 1);
		addComponent(subPanel, 3, 0, 4, 1);
		addComponent(Box.createVerticalGlue(), 4, 0, 4, 1);
		addComponent(orderLabel, 5, 0, 2, 1);
		c.fill = GridBagConstraints.BOTH;
		addComponent(orderScrollPane, 5, 2, 2, 1);
		
		//Sets a spacing of 5 pixels around the border
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	private void addComponent(Component component, int row, int col, int width, int height) {
		c.gridy = row;
		c.gridx = col;
		c.gridwidth = width;
		c.gridheight = height;
		
		layout.setConstraints(component, c);
		add(component);
	}
	
	//Listener for the track button
	class TrackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			orderID = orderIDTF.getText();
			
			if (!validOrderID(orderID)) {		//If order is invalid
				orderLabel.setFont(errorFont);
				orderScrollPane.setVisible(false);
			}
			else if (!orderIDExist(orderID)) {	//If order ID does not exist
				orderLabel.setFont(errorFont);
				orderLabel.setText("Order does not exist in records!");
				orderScrollPane.setVisible(false);
			}
			else {								//If order ID found
				orderLabel.setFont(normalFont);
				orderLabel.setText("<HTML>Your Order Information is as follows.<br><br>" +
						"Username: " + orderInfo[1] + "<br><br>" +
						"Deliver Date: " + orderInfo[2] + "<br><br>" +
						"Delivery Time: " + orderInfo[3] + "<br><br>" +
						"Status: " + orderInfo[4] + "</HTML>");
				
				orderTA.setText(orderItemsToString());
				
				orderScrollPane.setVisible(true);
			}
		}
	}
	
	//Imports the ordered items information from the file to a string
	private String orderItemsToString() {
		
		String[] drinkInfo;
		String previousType = "none";
		int totalDrinks = 0;
		double totalPrice = 0;
		
		String orderStr = "Your Order\n" +
				"================================================";
		
		try {
			Scanner fileReader = new Scanner(new File(MainCoffee.ORDERDRINKSFILE));
			
			while (fileReader.hasNextLine()) {
				drinkInfo = fileReader.nextLine().split(";");
				
				if (orderID.equals(drinkInfo[0])) {
					
					if (drinkInfo[1].equals(previousType)) {
						
						orderStr += ", " + drinkInfo[2] + " " + drinkInfo[3] + " cup(s)";
					}
					else {
						orderStr += "\n";
						orderStr += drinkInfo[1] + ": " + drinkInfo[2] + " " + drinkInfo[3] + " cup(s)";
						
						previousType = drinkInfo[1];
					}
					
					totalDrinks += Integer.parseInt(drinkInfo[3]);
					
					for (int i = 0; i < MainCoffee.NUM_OF_SIZES; i++) {
						if (MainCoffee.COFFEESIZE[i].equals(drinkInfo[2])) {
							totalPrice += MainCoffee.COFFEEPRICE[i] * Integer.parseInt(drinkInfo[3]);
						}
					}
				}
			}
			
			orderStr += "\n================================================\n" +
					"Total Drinks: " + totalDrinks + "\n" +
					"Total Cost: " + priceFmt.format(totalPrice) + 
					"\n================================================\n";
			
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "File not found!", JOptionPane.ERROR_MESSAGE);
			orderStr = "";
		}
		
		return orderStr;
	}
	
	//Checks if it is a valid order ID
	private boolean validOrderID(String orderID) {
		boolean valid = true;
		String errorMsg = null;
		
		try {
			
			if (orderID.length() != 9) {
				errorMsg = "Order ID must be 9 digits";
				throw new Exception(errorMsg);
			}
			
			c.gridx = Integer.parseInt(orderID);
		}
		catch (NumberFormatException e) {
			errorMsg = "Order ID must be a numerical value!";
			valid = false;
		}
		catch (Exception e) {
			valid = false;
		}
		
		if (valid == false) {
			orderLabel.setText(errorMsg);
		}
		
		return valid;
	}
	
	//Checks if order ID exists in the file
	private boolean orderIDExist(String orderID) {
		
		try {
			Scanner fileReader = new Scanner(new File(MainCoffee.ORDERDATAFILE));
			
			while (fileReader.hasNextLine()) {
				
				orderInfo = fileReader.nextLine().split(";");
				
				if (orderInfo[0].trim().equals(orderID)) {
					return true;
				}
			}
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "File not found!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return false;
	}

}
