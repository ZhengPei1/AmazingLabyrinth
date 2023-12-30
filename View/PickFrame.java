package AmazingLabyrinth.View;
import AmazingLabyrinth.Controller.Controller;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;

import javax.imageio.*;
import javax.swing.*;

/*
 Author: Hussain Murtaza 80%, Hanyu Zhou 20%

Description: this frame allows the user to choose how many players/AI they want to have, as well as
			 the number of cards
			 
Important Fields
1. ImageIcon titleImage - image for title
2. ImageIcon buttonImage - image for button background
3. JLabel titleLabel - label for title image
4. JLabel[] playerNumLabels - label array for player numbers
5. JLabel[] playerIcon - label array for player icons
6. JButton startButton - button to start the game
7. JPanel[] playerRadioPanel - panel array for each player's radio buttons
8. ButtonGroup[] settingsButtonGroups - button group array for each player's radio buttons
9. JRadioButton[] playerRadioButton - radio button array for all human selection
10. JRadioButton[] aiRadioButton - radio button array for all AI selection
11. JRadioButton[] noneRadioButton - radio button array for all none selection
12.	JComboBox CardChoiceSelect - comboBox with an array for card selection
13. int allPlayer[] - integer array of players to pass to the controller
14. int cardNum - integer of card numbers to pass to the controller

Available Methods:
1. public PickFrame() - the method which contains all components, will be called when user clicks
   start in the starting frame (Hussain)
2. @Override
   public void actionPerformed(ActionEvent e) - override method which contains action listeners
   for the start button (Hanyu, Hussain)
3. mutators and accessors (Hanyu)

Private Methods
1. private void setupComponents() - add label for title and start button to the frame (Hussain)
2. private void addPlayerLabel() - add player number and icons to the frame (Hussain)
3. private void addPlayerRadioButtons() - add player radio buttons to the frame (Hanyu)
4. private void addComboBox() - add card drop down  to the frame (Hussain)

 */

public class PickFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1234567L;
	
	// fields - Hanyu
	
	// image icons
	private ImageIcon titleImage = new ImageIcon("Images/GUIImages/titleImage.png");
	private ImageIcon buttonImage = new ImageIcon("Images/GUIImages/Button.png");
	private ImageIcon[] playerImages = new ImageIcon[4];
	private Image[] scaledImages = new Image[4];
	
	// labels
	private JLabel titleLabel;
	private JLabel[] playerNumLabels = new JLabel[4];
	private JLabel[] playerIcons = new JLabel[4];
	
	// button
	private JButton startButton = new JButton(buttonImage);
	
	// setting up for player radio buttons
	private JPanel[] playerRadioPanel = new JPanel[4];
	private ButtonGroup[] settingsButtonGroups = new ButtonGroup[4];
	private JRadioButton[] playerRadioButton = new JRadioButton[4];
	private JRadioButton[] aiRadioButton = new JRadioButton[4];
	private JRadioButton[] noneRadioButton = new JRadioButton[4];
	
	// setting up for card dropdown
	private JComboBox CardChoiceSelect = new JComboBox();
	
	// for counting player/AI/card numbers
	private int allPlayer[] = new int[4];
	
	private int cardNum;
	
	// constructor method - Hussain
	public PickFrame() {
		
		try {
			setContentPane(new JLabel(new ImageIcon(
					ImageIO.read(new File("Images/GUIImages/frameBackground.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setupComponents();
		addPlayerIcons();
		addPlayerRadioButtons();
		addCardComboBox();
		
		setLayout(null);
		setPreferredSize(new Dimension(1920, 1080));
		setState(JFrame.MAXIMIZED_BOTH);
		pack();
		
		// close frame setting
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// make the frame visible
		setVisible(true);
		
	}
	
	// set up components - Hussain
	public void setupComponents() {
		
		// setting up title image - Hussain
		Image scaledTitleImg = titleImage.getImage().getScaledInstance(900, 150, java.awt.Image.SCALE_SMOOTH);
		titleLabel = new JLabel(new ImageIcon(scaledTitleImg));
		titleLabel.setBounds(510, 50, 900, 150);
		add(titleLabel);
		
		// adding start button - Hussain
		startButton.setFont(new Font("TimesRoman BOLD", Font.BOLD, 40));
		startButton.setText("Start");
		startButton.setVerticalTextPosition(AbstractButton.CENTER);
		startButton.setHorizontalTextPosition(AbstractButton.CENTER);
		startButton.setBounds(1600, 900, 220, 73);
		startButton.addActionListener(this);
		add(startButton);
		
	}
	
	// add player icons - Hussain
	private void addPlayerIcons() {
		
		// y-coordinate for the playerNumLabels
		int playerIconX = 340;
		
		for (int p = 0; p < 4; p++) {
			
			playerImages[p] = new ImageIcon("Images/PlayerIcons/Player" + p + ".png");
			scaledImages[p] = playerImages[p].getImage().getScaledInstance(310, 310, java.awt.Image.SCALE_SMOOTH);
			playerIcons[p] = new JLabel (new ImageIcon (scaledImages[p]));
			playerIcons[p].setBounds(playerIconX, 220, 310, 310);
			playerIconX += 310;
			add(playerIcons[p]);
			
		}
		
	}
	
	// add radio buttons for players - Hanyu
	private void addPlayerRadioButtons() {
		
		// setting up radio button groups for all the players - Hanyu
		int panelX = 340; // set x-coordinate
		
		// loop through all players
		for (int p = 0; p < 4; p++) {
			
			// set radio buttons
			playerNumLabels[p] = new JLabel("Player" + (p + 1), SwingConstants.CENTER);
			playerRadioButton[p] = new JRadioButton("Human", true); // set default to 4 human player
			aiRadioButton[p] = new JRadioButton("AI");
			noneRadioButton[p] = new JRadioButton("None");
			
			// set fonts for them
			playerNumLabels[p].setFont(new Font("Algerian", Font.PLAIN, 65));
			playerRadioButton[p].setFont(new Font("Algerian", Font.PLAIN, 50));
			aiRadioButton[p].setFont(new Font("Algerian", Font.PLAIN, 50));
			noneRadioButton[p].setFont(new Font("Algerian", Font.PLAIN, 50));
			
			// set text colour
			if (p == 0) {
				
				playerNumLabels[p].setForeground(Color.RED);
				playerRadioButton[p].setForeground(Color.RED);
				aiRadioButton[p].setForeground(Color.RED);
				noneRadioButton[p].setForeground(Color.RED);
				
			} else if (p == 1) {
				
				
				playerNumLabels[p].setForeground(Color.green);
				playerRadioButton[p].setForeground(Color.green);
				aiRadioButton[p].setForeground(Color.green);
				noneRadioButton[p].setForeground(Color.green);
				
				
			} else if (p == 2) {
				
				playerNumLabels[p].setForeground(Color.YELLOW);
				playerRadioButton[p].setForeground(Color.YELLOW);
				aiRadioButton[p].setForeground(Color.YELLOW);
				noneRadioButton[p].setForeground(Color.YELLOW);
				
			} else if (p == 3) {
				
				playerNumLabels[p].setForeground(Color.cyan);
				playerRadioButton[p].setForeground(Color.cyan);
				aiRadioButton[p].setForeground(Color.cyan);
				noneRadioButton[p].setForeground(Color.cyan);
				
			}
			
			// remove background
			playerNumLabels[p].setOpaque(false);
			playerRadioButton[p].setOpaque(false);
			aiRadioButton[p].setOpaque(false);
			noneRadioButton[p].setOpaque(false);
			
			// add radio buttons to the group
			settingsButtonGroups[p] = new ButtonGroup();
			settingsButtonGroups[p].add(playerRadioButton[p]);
			settingsButtonGroups[p].add(aiRadioButton[p]);
			settingsButtonGroups[p].add(noneRadioButton[p]);
			
			// set panel for each player
			playerRadioPanel[p] = new JPanel();
			playerRadioPanel[p].setLayout(new GridLayout(4, 1)); // 4 rows 1 column
			
			// add radio buttons to the panel
			playerRadioPanel[p].add(playerNumLabels[p]);
			playerRadioPanel[p].add(playerRadioButton[p]);
			playerRadioPanel[p].add(aiRadioButton[p]);
			
			// only add "None" radio button if it is player 3/4
			if (p > 1)
				playerRadioPanel[p].add(noneRadioButton[p]);
			
			
			// add radio button panels
			playerRadioPanel[p].setBounds(panelX, 540, 300, 250);
			playerRadioPanel[p].setOpaque(false);
			add(playerRadioPanel[p]);
			
			panelX += 310;
			
		}
		
	}
	
	// add combobox for card choice - Hussain
	private void addCardComboBox() {
		
		String[] CardChoice = { "Select the Card Amount", "1 Card", "2 Cards", "3 Cards", "4 Cards", "5 Cards", "6 Cards" };
		
		//Create the combo box, select item at index 0.
		CardChoiceSelect = new JComboBox(CardChoice);
		CardChoiceSelect.setFont(new Font("Algerian", Font.PLAIN, 55));
		CardChoiceSelect.setBounds(585, 810, 750, 75);
		CardChoiceSelect.setSelectedIndex(0);
		CardChoiceSelect.addActionListener(this);
		add(CardChoiceSelect);
		
	}
	
	// action listener - Hanyu
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// loop 4 player radio button groups
		for (int p = 0; p < 4; p++) {
			
			// set command for each radio button
			playerRadioButton[p].setActionCommand(playerRadioButton[p].getText());
			aiRadioButton[p].setActionCommand(aiRadioButton[p].getText());
			noneRadioButton[p].setActionCommand(noneRadioButton[p].getText());
			
			// check to see which selection the user choose
			switch (settingsButtonGroups[p].getSelection().getActionCommand()) {
				
				case "Human":
					allPlayer[p] = 0;
					break;
				
				case "AI":
					allPlayer[p] = 1;
					break;
				
				case "None":
					allPlayer[p] = 2;
					break;
				
			}
			
		}
		
		// check to see how many cards the user selected
		int selection = CardChoiceSelect.getSelectedIndex();
		if(e.getSource() == CardChoiceSelect){
			switch (selection) {
				
				case 0:
					break;
				case 1:
					JOptionPane.showMessageDialog(null, "1 Card Selected!");
					cardNum = 1;
					break;
				case 2:
					JOptionPane.showMessageDialog(null, "2 Cards Selected!");
					cardNum = 2;
					break;
				case 3:
					JOptionPane.showMessageDialog(null, "3 Cards Selected!");
					cardNum = 3;
					break;
				case 4:
					JOptionPane.showMessageDialog(null, "4 Cards Selected!");
					cardNum = 4;
					break;
				case 5:
					JOptionPane.showMessageDialog(null, "5 Cards Selected!");
					cardNum = 5;
					break;
				case 6:
					JOptionPane.showMessageDialog(null, "6 Cards Selected!");
					cardNum = 6;
					break;
				
			}
		}
		
		
		// check to see whether there is player 3 before adding player 4
		if (allPlayer[2] == 2 && allPlayer[3] == 0 || allPlayer[2] == 2 && allPlayer[3] == 1) {
			// pop up message to show error
			JOptionPane.showMessageDialog(null, "You must add player 3 before adding player 4!", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if(selection == 0) {
			
			JOptionPane.showMessageDialog(null, "Select a Card Choice Please", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(e.getSource() == startButton){
			dispose(); // dispose frame
			// start game
			new Controller(new int[] { allPlayer[0], allPlayer[1], allPlayer[2], allPlayer[3] }, cardNum);
		}
	}
	
	public int getCardNum() {
		return cardNum;
	}
	
	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}
	
	public int[] getAllPlayer() {
		return allPlayer;
	}
	
	public void setAllPlayer(int[] allPlayer) {
		this.allPlayer = allPlayer;
	}
	
}