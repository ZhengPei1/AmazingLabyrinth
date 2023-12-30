package AmazingLabyrinth.View;

import javax.swing.*;

//Author - Fatimah Rana
//Frame that contains the gamerule.png, and allows the user to have brief instructions
//on the basic gameplay to avoid confusion.
public class GameRuleFrame extends JFrame{
	
	// allows the gamerulepanel to open
	// constructor - Fatimah
	public GameRuleFrame(){
		
		// Labels frame "Game Rule" and opens on button click
		setTitle("GAME RULE");
		setSize(700,850);
		setVisible(true);
		setLocationRelativeTo(null);
		
		// Adds image to frame and opens on click
		add(new JLabel(new ImageIcon("Images/GUIImages/gameRule.png")));
		
	}
	
	
}