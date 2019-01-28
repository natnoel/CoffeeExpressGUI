/*
 CSCI213 Assignment 2
 ----------------------------------------------------------- 
 File name: IntroPanel.java
 Author: Tan Shi Terng Leon
 Registration Number: 4000602
 Description: Defines the panel for the Intro
 */

/**
 * @author Leon
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IntroPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private final String COMPANYNAME = new String("COFFEE EXPRESS COMPANY");
	private final int DELAY = 5000;
	private final int NUM_OF_IMAGES = 7;
	
	private JPanel coffeeLabelPanel, bottomPanel;
	private JLabel company, coffeeLabel, selectMenu, selectOrder, selectTrack;
	private JButton exitButton;
	private Timer timer;
	private ImageIcon coffeeImg[];
	private int imgChangeCount;
	
	public IntroPanel() {
		
		//Setting a box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Company Label
		company = new JLabel(COMPANYNAME);
		
		//Array of coffee images
		coffeeImg = new ImageIcon[NUM_OF_IMAGES];
		coffeeImg[0] = new ImageIcon("introcoffee.jpg");
		coffeeImg[1] = new ImageIcon("americano.jpg");
		coffeeImg[2] = new ImageIcon("arabianCoffee.jpg");
		coffeeImg[3] = new ImageIcon("cafeLatte.jpg");
		coffeeImg[4] = new ImageIcon("cappuccino.jpg");
		coffeeImg[5] = new ImageIcon("hotChoco.jpg");
		coffeeImg[6] = new ImageIcon("exitCoffee.jpg");
		
		//Initializing coffee picture Label
		coffeeLabel = new JLabel(coffeeImg[0]);
		coffeeLabelPanel = new JPanel();
		coffeeLabelPanel.add(coffeeLabel);
		coffeeLabelPanel.setAlignmentX(getWidth() / 2 - coffeeLabelPanel.getWidth()/2);
		
		//Timer for image to change
		timer = new Timer(DELAY, new ImageListener());
		timer.start();
		imgChangeCount = 0;
		
		//Instruction 1 Label
		selectMenu = new JLabel("Select <Menu> to place your order.");
		
		//Instruction 2 Label
		selectOrder = new JLabel("Select <Order to check out and select your delivery.");
		
		//Instruction 3 Label
		selectTrack = new JLabel("Select <Track> to track your order status.");
		
		//Exit button
		exitButton = new JButton("Click to Exit");
		exitButton.addActionListener(new ExitButtonListener());
		exitButton.setPreferredSize(new Dimension(500, 50));
		
		//Adds the components to the panel
		add(company);
		add(Box.createRigidArea(new Dimension(0, 25)));
		add(Box.createVerticalGlue());
		add(coffeeLabelPanel);
		add(Box.createRigidArea(new Dimension(0, 25)));
		add(Box.createVerticalGlue());
		add(selectMenu);
		add(Box.createRigidArea(new Dimension(0, 25)));
		add(Box.createVerticalGlue());
		add(selectOrder);
		add(Box.createRigidArea(new Dimension(0, 25)));
		add(Box.createVerticalGlue());
		add(selectTrack);
		add(Box.createRigidArea(new Dimension(0, 25)));
		add(Box.createVerticalGlue());
		
		//Adds the exit button
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 1));
		bottomPanel.add(exitButton);
		bottomPanel.setAlignmentX(LEFT_ALIGNMENT);
		add(bottomPanel);
	}
	
	//Change the image every 5 seconds
	class ImageListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			coffeeLabel.setIcon(coffeeImg[++imgChangeCount % NUM_OF_IMAGES]);
		}
	}
	
	//Upon exit by the exit button, displays a pop up frame
	class ExitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			JOptionPane.showMessageDialog(null, "Thank you for visiting Coffee Express Company", 
					"Exiting...", JOptionPane.INFORMATION_MESSAGE, MainCoffee.exitImg);
			
			System.exit(0);
		}
	}
}
