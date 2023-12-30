package AmazingLabyrinth.Model;

import java.io.Serializable;

import javax.swing.*;

import AmazingLabyrinth.Controller.Controller;
import AmazingLabyrinth.View.*;

/*
Author: Hanyu Zhou

Description: this class is being used to store data for save and reload

Important Fields: 
1. Controller controller - saving the controller
2. GameFrame gameFrame - saving the game frame
3. Board boardModel - saving the board
4. Player[] allPlayers - saving all players
5. int turn - saving the turn number
6. int[] playerState - saving player state from the pick frame
7. int cardNum - saving number of cards from the pick frame
8. BoardPanel boardPanel - saving board panel
9. FreeTilePanel freeTilePanel - saving free tile panel
10. PlayerPanel playerPanel - saving player panel
11. ScoringPanel scoringPanel - saving scoring panel
12. MessagePanel messagePanel - saving message panel
13. JButton gameRuleButton - saving the game rule button
14. JButton newGameButton - saving the new game button
15. JButton saveButton - saving the save button
16. Tile[] allTiles - saving all tiles on the board
17. Tile freeTile - saving the free tile
18. Card[][] possessedCards - saving cards for all players
19. int[] column - saving player column number
20. int[] row - saving player row number
21. JButton[][] boardArray - saving the buttons for the game board
22. JButton[] shiftButtonArray - saving the inserting arrows
23. JLabel[] playerLabels - saving player labels on the game board
24. JLabel freeTileLabel - saving free tile labels
25. JButton rotateLeftButton - saving rotate left arrow
26. JButton rotateRightButton - saving rotate right arrow
27. JLabel[][] cards - saving label for the cards
28. JLabel yourLabel - saving label for indicating turn
29. JLabel turnLabel - saving label for indicating turn
30. String[] scoreLabel - saving scores for all players
31. String msgTextArea - saving text in the message panel

Available Methods: 
N/A

Private Methods: 
N/A

 */

public class DataStorage implements Serializable {
	private static final long serialVersionUID = 1234567L;

	// controller
	Controller controller;

	// fields in the controller
	GameFrame gameFrame;
	Board boardModel;
	Player[] allPlayers; 
	int turn;
	int[] playerState = new int[4]; 
	int cardNum; 

	// fields in the game frame
	BoardPanel boardPanel;
	FreeTilePanel freeTilePanel;
	PlayerPanel playerPanel;
	ScoringPanel scoringPanel;
	MessagePanel messagePanel;
	JButton gameRuleButton;
	JButton newGameButton;
	JButton saveButton;

	// fields in the board model
	Tile[] allTiles;
	Tile freeTile;

	// fields in the player class
	Card[][] possessedCards;
	int[] column;
	int[] row;

	// fields in the board panel
	JButton[][] boardArray = new JButton[7][7];
	JButton[] shiftButtonArray = new JButton[12];
	JLabel[] playerLabels; 
	
	// fields in the free tile panel
	JLabel freeTileLabel;
	JButton rotateLeftButton;
	JButton rotateRightButton;
	
	// fields in the player panel
	JLabel[][] cards = new JLabel [4][6];
	JLabel yourLabel;
	JLabel turnLabel;
	
	// fields in the scoring panel
	String[] scoreLabel = new String[4];
	
	// fields in the message panel
	String msgTextArea;

}
