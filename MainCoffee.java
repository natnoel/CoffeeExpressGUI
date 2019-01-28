/*
 CSCI213 Assignment 2
 ----------------------------------------------------------- 
 File name: MainCoffee.java
 Author: Tan Shi Terng Leon
 Registration Number: 4000602
 Description: Creates and displays the frame containing all the panels and panes
 */

/**
 * @author Leon
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;;

public class MainCoffee {
	
	//Constants
	public final static String COMPANYNAME = "COFFEE EXPRESS COMPANY";
	public static final String ORDERDATAFILE = "orderData.txt", CUSTOMERFILE = "customerInfo.txt";
	public static final String ORDERDRINKSFILE = "orderDrinks.txt";
	public static final int NUM_OF_TYPES = 5, NUM_OF_SIZES = 3;
	public static final String[] COFFEETYPE = {"Americano", "Cafe Latte", "Cappucino", "Arabian Coffee", "Hot Chocolate"};
	public static final String[] COFFEESIZE = {"Small", "Medium", "Large"};
	public static final double[] COFFEEPRICE = {2.50, 3.50, 4.50};
	public static final int ORDERIDLIMIT = 1000000000;
	
	private static MainFrame mainFrame;		//The frame that contains all the panels and panes
	public static JTabbedPane tabbedPane;	//To show tabs
	private static Dimension screensize;	//Stores the screen size of the current screen (used for positioning frame)
	
	public static int[][] orderTypeQty;		//Stores the quantity of drinks ordered of each type/size
	public static int totalDrinks;			//Total drinks ordered
	public static Customer user;			//Current user
	
	//Panels to be added
	public static MenuPanel menuPanel;
	public static JTextArea orderTextArea;
	public static DeliveryPanel deliveryPanel;
	public static ExistingCustPanel existingCustPanel;
	public static NewCustPanel newCustPanel;
	
	public static ImageIcon exitImg;		//Image appears on exit
	public static JLabel loginUserLabel;	//Shows the current logged in user
	
	public static void main(String[] args) {
		
		//Initialing the user (no user logged in at the moment)
		user = new Customer();
		
		//Allocating memory for the order array
		orderTypeQty = new int[NUM_OF_TYPES][NUM_OF_SIZES];
		
		//Defining the image to appear on exit
		exitImg = new ImageIcon("exitCoffee.jpg");
		
		//Allocating memory for the label
		loginUserLabel = new JLabel();
		
		//Initialing the frame
		mainFrame = new MainFrame(COMPANYNAME);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Allocating memory for the panels and the text area
		menuPanel = new MenuPanel();
		orderTextArea = new JTextArea("Please place your order first");
		deliveryPanel = new DeliveryPanel();
		existingCustPanel = new ExistingCustPanel();
		newCustPanel = new NewCustPanel();
		
		orderTextArea.setToolTipText("Your order");		//Sets mouse over text for the text area
		orderTextArea.setEditable(false);				//Makes text area uneditable
		
		//Makes the following panels invisible (set to visible only when needed)
		deliveryPanel.setVisible(false);
		existingCustPanel.setVisible(false);
		newCustPanel.setVisible(false);
		
		//Defining the tabbed panel
		tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("Intro", new IntroPanel());
		tabbedPane.addTab("Menu", menuPanel);
		tabbedPane.addTab("Order", new OrderPanel());
		tabbedPane.addTab("Track", new TrackPanel());
		
		mainFrame.add(tabbedPane);		//Adds tabbed panel to the frame
		
		mainFrame.pack();				//Packs the frame
		
		//Ensures frame appears in the middle of the screen
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation((screensize.width - mainFrame.getWidth()) / 2, 
				(screensize.height - mainFrame.getHeight()) / 2);
		
		//Sets the minimum frame size
		mainFrame.setMinimumSize(new Dimension(400, 520));
		
	}
	
	//Scales an image to a specific width and height
	public static void scaleImg(ImageIcon imgIcon, int width, int height) {
		
		imgIcon.setImage(imgIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

}


//Frame class
class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	MainFrame(String frameName) {
		super(frameName);
		
		this.addWindowListener(new ExitListener());		//Adds the listener
	}
	
	//Enables a pop out window upon frame closing by click the "X" button at the top right corner
	class ExitListener implements WindowListener {

		public void windowActivated(WindowEvent arg0) {
			
		}

		public void windowClosed(WindowEvent arg0) {
			
		}

		public void windowClosing(WindowEvent arg0) {
			JOptionPane.showMessageDialog(null, "Thank you for visiting Coffee Express Company", 
					"Exiting...", JOptionPane.INFORMATION_MESSAGE, MainCoffee.exitImg);
		}

		public void windowDeactivated(WindowEvent arg0) {
			
		}

		public void windowDeiconified(WindowEvent arg0) {
			
		}

		public void windowIconified(WindowEvent arg0) {
			
		}

		public void windowOpened(WindowEvent arg0) {
			
		}
		
	}

}
