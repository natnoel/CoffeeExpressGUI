/*
 CSCI213 Assignment 2
 ----------------------------------------------------------- 
 File name: OrderPanel.java
 Author: Tan Shi Terng Leon
 Registration Number: 4000602
 Description: OrderPanel class: contains a text area, a DeliveryPanel, 
 				a NewCustomerPanel, and an ExistingCustomerPanel object
 */

/**
 * @author Leon
 *
 */

import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JScrollPane orderScrollPane;
	
	public OrderPanel() {
		
		setLayout(new GridLayout(2, 2));	//Sets a grid layout
		
		orderScrollPane = new JScrollPane(MainCoffee.orderTextArea);	//New scroll pane
		
		add(orderScrollPane);				//Adds the scroll pane
		add(MainCoffee.deliveryPanel);		//Adds the delivery panel
		add(MainCoffee.existingCustPanel);	//Adds the existing customer panel
		add(MainCoffee.newCustPanel);		//Adds the new customer panel
		
	}
	
}


