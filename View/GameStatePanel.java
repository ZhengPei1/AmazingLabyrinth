
package AmazingLabyrinth.View;

import java.io.Serializable;

import javax.swing.*;

/*
Author: Hanyu Zhou

Description: this method shows a starting and ending label at the beginning and end of the game

Important Fields: 
1. JLabel startedLabel - label for starting the game

Available Methods
1. public GameStatePanel() - set size for the game started label
2. public void update (ImageIcon image) - change the image into game ended at the end of the game
3. mutators and accessors

 */
public class GameStatePanel extends JPanel implements Serializable{
	private static final long serialVersionUID = 1234567L;
	
	// label for game started
	private JLabel startedLabel = new JLabel(new ImageIcon("Images/GUIImages/GameStarted.png"));
	
	// contructor methods
	public GameStatePanel() {
		
		// set size
		setSize(500,300);
		
		// add label
		startedLabel.setBounds(0,0,500,300);
		add(startedLabel);
		
		// not showing at first
		setOpaque(false);
		
	}
	
	// update the label when the game ended
	public void update(ImageIcon image){
		startedLabel.setIcon(image);
		repaint();
	}
	
	// mutators and accessors
	public JLabel getStartedLabel() {
		return startedLabel;
	}
	
	public void setStartedLabel(JLabel startedLabel) {
		this.startedLabel = startedLabel;
	}
	
}