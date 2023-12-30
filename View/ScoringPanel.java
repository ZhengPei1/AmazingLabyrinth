package AmazingLabyrinth.View;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

import javax.swing.*;

/*
Author: Hanyu Zhou

Description: the GUI aspect of the scoring panel, it displays all players' score

Important Fields:
1. JLabel titleLabel - used to display the title "Scoring"
2. JLabel[] playerIcon - an array with length 4 that holds image icons for each player
3. JLabel[] playerNumLabels - an array with length 4 that holds the player numbers
4. JLabel[] scoreLabel - an array with length 4 that holds each player's score, which will be updated as the game goes on
5. JLabel[] pointsLabel - an array with length 4 that holds the units (pts) for each player' score

Available Methods:
1. public ScoringPanel() - allows the game frame to create the scoring panel (Hanyu Zhou)
2. public void changeScore(int turn) - change score whenever a player gets their treasure
3. mutators and accessors

Private Methods
1. private void addText() - adds all the jlabels onto the panel

 */

public class ScoringPanel extends JPanel implements Serializable{
	private static final long serialVersionUID = 1234567L;
	
	// label for title
	JLabel titleLabel = new JLabel("Scoring");
	JLabel[] playerIcon = new JLabel[4];
	JLabel[] playerNumLabels = new JLabel[4];
	JLabel[] scoreLabel = new JLabel[4];
	JLabel[] pointsLabel = new JLabel[4];
	
	// constructor method
	public ScoringPanel() {
		addText();
		setLayout(null);
	}
	
	// add text and player icon for the scoring panel - Hanyu Zhou
	private void addText() {
		
		// set title font, size and colour, then add to panel
		titleLabel.setFont(new Font("TimesRoman BOLD", Font.BOLD | Font.ITALIC, 40));
		titleLabel.setBounds(20, 20, 150, 50);
		titleLabel.setForeground(Color.white);
		add(titleLabel);
		
		// add text for each player
		for (int p = 0; p < 4; p++) {
			
			// set text and font for player number labels
			playerNumLabels[p] = new JLabel("P" + (p + 1), SwingConstants.CENTER);
			playerNumLabels[p].setFont(new Font("TimesRoman BOLD", Font.BOLD | Font.ITALIC, 40));
			playerNumLabels[p].setForeground(Color.white);
			
			// set image for player icons
			playerIcon[p] = new JLabel();
			playerIcon[p].setIcon(new ImageIcon("src/AmazingLabyrinth/Images/PlayerIcons/Player" + p  + ".png"));
			playerIcon[p].setForeground(Color.white);
			
			// set text for scores
			scoreLabel[p] = new JLabel("0");
			scoreLabel[p].setFont(new Font("TimesRoman BOLD", Font.BOLD | Font.ITALIC, 40));
			scoreLabel[p].setForeground(Color.white);
			
			pointsLabel[p] = new JLabel("pts");
			pointsLabel[p].setFont(new Font("TimesRoman BOLD", Font.BOLD | Font.ITALIC, 30));
			pointsLabel[p].setForeground(Color.white);
			
			// set location for player 1
			if (p == 0) {
				playerNumLabels[p].setBounds(20, 105, 50, 30);
				playerIcon[p].setBounds(90, 90, 50, 55);
				scoreLabel[p].setBounds(180, 105, 50, 30);
				pointsLabel[p].setBounds(210, 90, 50, 70);
				
				// set location for player 2
			} else if (p == 1) {
				playerNumLabels[p].setBounds(20, 185, 50, 30);
				playerIcon[p].setBounds(90, 170, 50, 55);
				scoreLabel[p].setBounds(180, 185, 50, 30);
				pointsLabel[p].setBounds(210, 170, 50, 70);
				
				// set location for player 3
			} else if (p == 2) {
				playerNumLabels[p].setBounds(352, 105, 50, 30);
				playerIcon[p].setBounds(422, 90, 50, 55);
				scoreLabel[p].setBounds(512, 105, 50, 30);
				pointsLabel[p].setBounds(542, 90, 50, 70);
				
				// set location for player 4
			} else {
				playerNumLabels[p].setBounds(352, 185, 50, 30);
				playerIcon[p].setBounds(422, 170, 50, 55);
				scoreLabel[p].setBounds(512, 185, 50, 30);
				pointsLabel[p].setBounds(542, 170, 50, 70);
				
			}
			
			// add all the labels and icons
			add(playerNumLabels[p]);
			add(playerIcon[p]);
			add(scoreLabel[p]);
			add(pointsLabel[p]);
			
		}
		
	}
	
	// change score when a player finds a treasure - Hanyu
	public void changeScore(int turn) {
		scoreLabel[turn].setText(Integer.toString(Integer.parseInt(scoreLabel[turn].getText()) + 1));
		repaint();
	}
	
	// mutators and accessors
	public JLabel[] getScoreLabel() {
		return scoreLabel;
	}
	
	public void setScoreLabel(JLabel[] scoreLabel) {
		this.scoreLabel = scoreLabel;
	}
}