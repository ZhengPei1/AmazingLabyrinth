package AmazingLabyrinth.View;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import AmazingLabyrinth.Model.SaveProgress;

/*
Author: Hanyu Zhou

Description: the GUI aspect for the starting frame where the user chooses whether they want to start a new
			 game or they want to continue the game that they saved
			 
Important Field:
1. ImageIcon titleImage - image for the title
2. ImageIcon buttonImage - image for the button background
3. JLabel titleLabel - label for title image
4. JLabel buttonLabel - label for button image
5. JButton startButton - start the game when the user clicks on this
6. JButton continueButton - reload game progress that was saved (this button will not appear in the starting
   frame until the user has saved something)

Available Methods:
1. public StartingFrame() - main frame that will appear in the beginning that allows the user to choose what they

Private Methods
1. private void addComponents() - add labels and buttons to the starting frame

 */

public class StartingFrame extends JFrame implements ActionListener, Serializable {
	private static final long serialVersionUID = 1234567L;
	
	// image icons
	ImageIcon titleImage = new ImageIcon("Images/GUIImages/titleImage.png");
	ImageIcon buttonImage = new ImageIcon("Images/GUIImages/Button.png");
	
	// labels
	JLabel titleLabel;
	JLabel buttonLabel;
	
	// button
	JButton startButton;
	JButton continueButton;
	
	// constructor method
	public StartingFrame() {
		
		// set background image
		try {
			setContentPane(new JLabel(new ImageIcon(
					ImageIO.read(new File("Images/GUIImages/frameBackground.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// add labels and buttons
		addComponents();
		
		// set up frame
		setLayout(null);
		setSize(1920, 1080);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	// add labels and buttons
	private void addComponents() {
		
		// scale the label image to make it bigger and add to the frame
		Image scaledTitleImg = titleImage.getImage().getScaledInstance(1200, 208, java.awt.Image.SCALE_SMOOTH);
		titleLabel = new JLabel(new ImageIcon(scaledTitleImg));
		titleLabel.setBounds(360, 70, 1200, 208);
		add(titleLabel);
		
		// scale button image
		Image scaledButtonImg = buttonImage.getImage().getScaledInstance(660, 219, java.awt.Image.SCALE_SMOOTH);
		
		// set start button image and text
		startButton = new JButton(new ImageIcon(scaledButtonImg));
		startButton.setFont(new Font("TimesRoman BOLD", Font.BOLD, 40));
		startButton.setText("Start Game");
		
		// set position
		startButton.setVerticalTextPosition(AbstractButton.CENTER);
		startButton.setHorizontalTextPosition(AbstractButton.CENTER);
		
		// add to frame
		startButton.setBounds(630, 410, 660, 219);
		startButton.addActionListener(this);
		add(startButton);
		
		// set continue button image and text
		continueButton = new JButton(new ImageIcon(scaledButtonImg));
		continueButton.setFont(new Font("TimesRoman BOLD", Font.BOLD, 40));
		continueButton.setText("Continue Game");
		
		// set position
		continueButton.setVerticalTextPosition(AbstractButton.CENTER);
		continueButton.setHorizontalTextPosition(AbstractButton.CENTER);
		
		// add to frame
		continueButton.setBounds(630, 700, 660, 219);
		continueButton.addActionListener(this);
		
		// only add the continue button if the user has saved progress before
		File saveFile = new File("saveProgress.dat");
		if (saveFile.exists())
			add(continueButton);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// if player clicks on start button
		if (e.getSource() == startButton) {
			
			// close starting and open pick frame
			dispose();
			new PickFrame();
			
			// if player clicks on the continue button
		} else if (e.getSource() == continueButton) {
			
			// close starting frame and load previous game
			dispose();
			SaveProgress.loadGame();
		}
	}
}