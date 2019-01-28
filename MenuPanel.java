/*
 CSCI213 Assignment 2
 ----------------------------------------------------------- 
 File name: MenuPanel.java
 Author: Tan Shi Terng Leon
 Registration Number: 4000602
 Description: Defines the MenuPanel class, used for displaying the menu and entering the drinks for order
 */

/**
 * @author Leon
 *
 */

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.event.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int NUM_OF_TYPES = 5;			//Number of coffee types
	private final int NUM_OF_SIZE = 3;			//Number of available sizes
	private final int QTYCHOICELIMIT = 10;		//Spinner's maximum value
	
	//Type of coffees
	private final String[] COFFEETYPE = {"Americano", "Cafe Latte", "Cappucino", "Arabian Coffee", "Hot Chocolate"};
	
	//The coffee sizes
	private final String[] COFFEESIZE = {"Small", "Medium", "Large"};
	
	//Price of each size of coffee
	private final double[] COFFEEPRICE = {2.50, 3.50, 4.50};
	
	//Used to display money in dollars and cents
	private DecimalFormat money;
	
	//Total number of drinks ordered and the total price
	private int totalDrinks;				
	private double totalPrice;
	
	//Stores how many of each type(row) and size(column) ordered
	private int typeQty[][];
	
	//Using a GridBagLayout
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	//Using Spinners for ordering coffee
	private JSpinner typeQtySpinner[][];
	private SpinnerNumberModel model[][];
	
	//To display to total number of drinks ordered and the total price
	private JLabel totalDrinksLabel, totalPriceLabel;
	private JTextField totalDrinksTextF, totalPriceTextF;
	
	private JButton resetButton, nextButton;
	
	//Image for each coffee type
	private ImageIcon[] coffeeImg;
	
	//Image for the next button
	private ImageIcon nextImg;
	
	private JLabel msgLabel;
	
	public MenuPanel() {
		
		//Initializing total drinks and total price
		totalDrinks = 0;
		totalPrice = 0;
		
		typeQty = new int[NUM_OF_TYPES][NUM_OF_SIZE];	//Allocating space for the order
		
		money = new DecimalFormat("0.00");		//Setting the format to display cost
		
		//Allocating space for the spinners (each representing a particular type and size of coffee)
		typeQtySpinner = new JSpinner[NUM_OF_TYPES][NUM_OF_SIZE];
		model = new SpinnerNumberModel[NUM_OF_TYPES][NUM_OF_SIZE];
		
		//Allocating layout and constraints
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		
		setLayout(layout);	//Setting the layout
		
		//Initializing the constraints
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		
		//Displaying top row
		addComponent(new JLabel("COFFEE EXP"), 0, 0);
		addComponent(new JLabel("Small"), 0, 1);
		addComponent(new JLabel("Quantity"), 0, 2);
		addComponent(new JLabel("Medium"), 0, 3);
		addComponent(new JLabel("Quantity"), 0, 4);
		addComponent(new JLabel("Large"), 0, 5);
		addComponent(new JLabel("Quantity"), 0, 6);
		
		//Defines and scales the images for each coffee
		prepareImages();
		
		//Setting up and adding the components
		for (int i = 0; i < NUM_OF_TYPES; i++) {
			
			addComponent(new JLabel(COFFEETYPE[i], coffeeImg[i], JLabel.LEFT), i + 1, 0);	//Adds the coffee type
			
			int col = 0;	//Keeps track of the column in this gridbag layout
			
			for (int j = 0; j < NUM_OF_SIZE; j++) {
				
				//Adds the price label
				addComponent(new JLabel(money.format(COFFEEPRICE[j])), i + 1, ++col);
				
				constraints.fill = GridBagConstraints.NONE;	//Sets the constraint for the spinners
				
				//Creating models for the spinners
				model[i][j] = new SpinnerNumberModel(0, 0, QTYCHOICELIMIT, 1);
				
				//Adding the spinners together with their models
				addComponent(typeQtySpinner[i][j] = new JSpinner(model[i][j]), i + 1, ++col);
				
				//Sets size of spinners
				typeQtySpinner[i][j].setPreferredSize(new Dimension(70, 20));
				
				//Adds a listener to each spinner
				typeQtySpinner[i][j].addChangeListener(new QuantityListener());
				
				constraints.fill = GridBagConstraints.BOTH;		//Resets the constraint
			}
		}
		
		//Setting the display for total drinks and price
		
		//Defining and initializing the labels and textfields
		totalDrinksLabel = new JLabel("Total No. of Drinks: ");
		totalPriceLabel = new JLabel("Total Price: ");
		totalDrinksTextF = new JTextField("0", 15);
		totalPriceTextF = new JTextField("$0", 15);
		
		totalDrinksTextF.setToolTipText("Shows the total number of drinks you ordered");
		totalPriceTextF.setToolTipText("Shows total price of your order");
		
		//Ensures user cannot edit the text fields (it is meant for display only)
		totalDrinksTextF.setEditable(false);
		totalPriceTextF.setEditable(false);
		
		//Sets background color of text fields to white
		totalDrinksTextF.setBackground(Color.white);
		totalPriceTextF.setBackground(Color.white);
		
		//Adds the labels and text fields
		addComponent(totalDrinksLabel, 7, 1, 2, 1);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		addComponent(totalDrinksTextF, 7, 3, 2, 1);
		addComponent(totalPriceLabel, 8, 1, 2, 1);
		addComponent(totalPriceTextF, 8, 3, 2, 1);
		
		//Defines and adds the reset button
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ResetButtonListener());
		resetButton.setToolTipText("Clears your order, resets all drinks to 0");
		addComponent(resetButton, 9, 4, 1, 1);
		
		//Defines and adds the next button
		nextImg = new ImageIcon("next.png");
		nextButton = new JButton("Next", nextImg);
		nextButton.setHorizontalTextPosition(SwingConstants.LEFT);
		nextButton.addActionListener(new NextButtonListener());
		nextButton.setEnabled(false);
		nextButton.setToolTipText("Please place an order first!");
		addComponent(nextButton, 9, 6, 1, 1);
		
		//The final message label
		msgLabel = new JLabel();
		addComponent(msgLabel, 9, 0, 3, 1);
		
		//Sets buffer of 5 pixels around the border
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	//Adds a component given its row and column
	private void addComponent(Component component, int row, int col) {
		
		constraints.gridx = col;
		constraints.gridy = row;
		
		layout.setConstraints(component, constraints);
		add(component);
	}
	
	//Adds a component specifying its row, column, width and height
	private void addComponent(Component component, int row, int col, int width, int height) {
		
		constraints.gridx = col;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		
		layout.setConstraints(component, constraints);
		add(component);
	}
	
	//Listener for the spinners
	private class QuantityListener implements ChangeListener {
		
		public void stateChanged(ChangeEvent arg0) {
			
			totalDrinks = 0;	//Initializes to 0;
			
			//Gets the spinners' values to the array
			for (int i = 0; i < NUM_OF_TYPES; i++) {
				for (int j = 0; j < NUM_OF_SIZE; j++) {
					typeQty[i][j] = (int)typeQtySpinner[i][j].getValue();
				}
			}
			
			calculateTotalDrinksAndPrice();		//Calculates total number of drinks and total price
			
			//Updates the text fields to display to user
			totalDrinksTextF.setText(Integer.toString(totalDrinks));
			totalPriceTextF.setText("$" + money.format(totalPrice));
			
			MainCoffee.orderTypeQty = typeQty;	//Assigns the array to a global variable to be used by other panels
			
			MainCoffee.totalDrinks = totalDrinks;	//Assigns the value to a global variable to be used by other panels
			
			if (totalDrinks == 0) {	//If no order placed
				MainCoffee.orderTextArea.setText("Please place an order first");	//Updates text area
				msgLabel.setText("");	//Sets message label to empty message
				
				MainCoffee.existingCustPanel.setVisible(false);
				MainCoffee.newCustPanel.setVisible(false);
				nextButton.setEnabled(false);
				nextButton.setToolTipText("Please place your order first!");
			}
			else {	//If order is placed
				
				String orderDisplay;	//Stores contents of the text area
				
				orderDisplay = "Step 1 - Confirm your order\n"
						+ "Your Order\n" 
						+ "================================================\n";
				
				//Displays the type, size and quantity of drinks
				boolean typeOrdered;						//Checks if the type has been ordered before
				for (int i = 0; i < NUM_OF_TYPES; i++) {	//For each coffee type
					typeOrdered = false;					//Beginning of the type, it has yet to be ordered
					for (int j = 0; j < NUM_OF_SIZE; j++) {	//For each size of a type
						if (typeQty[i][j] > 0) {			//If the quantity ordered is more than one
							if (typeOrdered == false)		//If type not yet ordered
								orderDisplay += COFFEETYPE[i] + ": ";	//Displays the type
							else							//If type has been ordered
								orderDisplay += ", ";		//Just display a comma and a space
							
							orderDisplay += typeQty[i][j] + " " + COFFEESIZE[j] + " Cup";	//Displays the quantity and size
							
							typeOrdered = true;				//Type has been ordered, so set it to true
						}
					}
					if (typeOrdered == true)				//If end of the type and it has been ordered (displayed)
						orderDisplay += "\n";				//Prints a new line
				}
				
				//Prints summary
				orderDisplay += "================================================\n"
						+ "No. of Drinks Ordered: " + totalDrinks + "\n"
						+ "Total Cost: " + "$" + money.format(totalPrice) + "\n"
						+ "================================================\n"
						+ "You have (must) opted for delivery.";
				
				//Sets the text area
				MainCoffee.orderTextArea.setText(orderDisplay);
				
				//Displays instruction on the message label
				msgLabel.setText("To Continue, go to <ORDER>...");
				
				//Makes panels visible so user can go to the next step
				if (MainCoffee.user.getUsername() == null) {
					MainCoffee.existingCustPanel.setVisible(true);
					MainCoffee.newCustPanel.setVisible(true);
				}
				
				nextButton.setEnabled(true);
				nextButton.setToolTipText("Confirm your order and login");
			}
		}
	}
	
	//Calculates total number of drinks and total price
	private void calculateTotalDrinksAndPrice() {
		
		totalDrinks = 0;
		totalPrice = 0;
		for (int i = 0; i < NUM_OF_TYPES; i++) {
			for (int j = 0; j < NUM_OF_SIZE; j++) {
				totalDrinks += typeQty[i][j];					//Adds up the quantity
				totalPrice += typeQty[i][j] * COFFEEPRICE[j];	//Adds up the price
			}
		}
	}
	
	//Listener for the reset button
	class ResetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			clearOrder();
			MainCoffee.existingCustPanel.setVisible(false);
			MainCoffee.newCustPanel.setVisible(false);
		}
	}
	
	//Resets all the spinner value to 0, thereby the order array values are set to zero also
	public void clearOrder() {
		for (int i = 0; i < NUM_OF_TYPES; i++) {
			for (int j = 0; j < NUM_OF_SIZE; j++) {
				typeQtySpinner[i][j].setValue(0);
			}
		}
	}
	
	//Listener for the next button
	class NextButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MainCoffee.tabbedPane.setSelectedIndex(2);	//Sets the current tab to the order tab
		}
	}
	
	//Sets the images for each coffee
	private void prepareImages() {
		
		coffeeImg = new ImageIcon[NUM_OF_TYPES];
		coffeeImg[0] = new ImageIcon("americano.jpg");
		coffeeImg[1] = new ImageIcon("cafeLatte.jpg");
		coffeeImg[2] = new ImageIcon("cappuccino.jpg");
		coffeeImg[3] = new ImageIcon("arabianCoffee.jpg");
		coffeeImg[4] = new ImageIcon("hotChoco.jpg");
		
		//Scales each image
		for (int i = 0; i < NUM_OF_TYPES; i++) {
			
			Image img = coffeeImg[i].getImage();
			img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			coffeeImg[i].setImage(img);
		}
		
	}

}
