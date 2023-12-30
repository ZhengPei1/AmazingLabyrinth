package AmazingLabyrinth.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.JLabel;

import AmazingLabyrinth.Controller.Controller;
import AmazingLabyrinth.View.BoardPanel;
import AmazingLabyrinth.View.GameFrame;
import AmazingLabyrinth.View.PickFrame;
import AmazingLabyrinth.View.PlayerPanel;

/*
Author: Hanyu Zhou
 
Description: this class includes features to save and reload the Labyrinth game
 
Important Fields: 
1. File saveFile - create a file for saving the components
2. Controller controller - the saved version of the controller
3. GameFrame gameFrame - the saved version of the game frame
4. Board boardModel - the saved version of the game board
5. Player[] allPlayers - the saved array for the players
6. int turn - the saved integer for who's turn it is

Available Methods: 
1. public SaveProgress(Controller controller, GameFrame gameFrame, Board boardModel, Player[] allPlayers, int turn) - 
   save the components that was passed to this constructor method into the fields (Hanyu Zhou)
2. public void saveGame() - this method saves the game into the data storage file (Hanyu Zhou)
3. public static Controller loadGame() - this method loads the game that was saved previously (Hanyu Zhou)
 
Private Methods: 
N/A

 */

public class SaveProgress implements Serializable {
	private static final long serialVersionUID = 1234567L;

	// create a file for saving the components
	File saveFile = new File("saveProgress.dat");

	// fields to save the game
	static Controller controller;
	static GameFrame gameFrame;
	static Board boardModel;
	static Player[] allPlayers;
	static int turn;

	// constructor method
	public SaveProgress(Controller controller, GameFrame gameFrame, Board boardModel, Player[] allPlayers, int turn) {
		this.controller = controller;
		this.gameFrame = gameFrame;
		this.boardModel = boardModel;
		this.allPlayers = allPlayers;
		this.turn = turn;
	}

	// this method saves the game
	public void saveGame() {

		// create new file if the file does not exist
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			// save information to this file
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile));
			
			DataStorage data = new DataStorage();

			// reset the data file
			oos.reset();

			// controller
			data.controller = controller;

			// save fields in the controller
			data.gameFrame = gameFrame;
			data.boardModel = boardModel;
			data.allPlayers = allPlayers;
			data.turn = turn;
			data.playerState = controller.getPlayerState();
			data.cardNum = controller.getCardNum();

			// save fields in the game frame
			data.boardPanel = gameFrame.getBoardPanel();
			data.freeTilePanel = gameFrame.getFreeTilePanel();
			data.playerPanel = gameFrame.getPlayerPanel();
			data.scoringPanel = gameFrame.getScoringPanel();
			data.messagePanel = gameFrame.getMessagePanel();
			data.gameRuleButton = gameFrame.getGameRuleButton();
			data.newGameButton = gameFrame.getNewGameButton();
			data.saveButton = gameFrame.getSaveButton();

			// save fields in the board model
			data.allTiles = boardModel.getAllTiles();
			data.freeTile = boardModel.getFreeTile();

			// save fields in the player class
			data.possessedCards = new Card[allPlayers.length][data.cardNum];
			data.column = new int[allPlayers.length];
			data.row = new int[allPlayers.length];
			for (int p = 0; p < allPlayers.length; p++) {
				data.possessedCards[p] = allPlayers[p].getPossessedCards();
				data.column[p] = allPlayers[p].getColumn();
				data.row[p] = allPlayers[p].getRow();
			}

			// save fields in the board panel
			data.boardArray = gameFrame.getBoardPanel().getBoardArray();
			data.shiftButtonArray = gameFrame.getBoardPanel().getShiftButtonArray();
			data.playerLabels = gameFrame.getBoardPanel().getPlayerLabels();

			// save fields in the free tile panel
			data.freeTileLabel = gameFrame.getFreeTilePanel().getFreeTileLabel();
			data.rotateLeftButton = gameFrame.getFreeTilePanel().getRotateLeftButton();
			data.rotateRightButton = gameFrame.getFreeTilePanel().getRotateRightButton();

			// save fields in the player panel
			data.cards = gameFrame.getPlayerPanel().getCards();
			data.yourLabel = gameFrame.getPlayerPanel().getYourLabel();
			data.turnLabel = gameFrame.getPlayerPanel().getTurnLabel();

			// save fields in the scoring panel
			for (int p = 0; p < 4; p++)
				data.scoreLabel[p] = gameFrame.getScoringPanel().getScoreLabel()[p].getText();

			// save fields in the message panel
			data.msgTextArea = gameFrame.getMessagePanel().getMsgTextArea().getText();

			// save the data into the object output stream
			oos.writeObject(data);
			oos.flush();
			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// this method loads the game
	public static Controller loadGame() {

		try {
			// read information from this file
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saveProgress.dat"));
			DataStorage data = (DataStorage) ois.readObject();

			// load controller
			controller = data.controller;

			// load fields in the controller
			gameFrame = data.gameFrame;
			boardModel = data.boardModel;
			allPlayers = data.allPlayers;
			
			// load message text
			gameFrame.getMessagePanel().getMsgTextArea().setText(data.msgTextArea);

			// load score text
			for (int p = 0; p < 4; p++)
				gameFrame.getScoringPanel().getScoreLabel()[p].setText(data.scoreLabel[p]);

			// load player panel
			gameFrame.getPlayerPanel().setCards(data.cards);
			gameFrame.getPlayerPanel().setYourLabel(data.yourLabel);
			gameFrame.getPlayerPanel().setTurnLabel(data.turnLabel);

			// load board panel
			gameFrame.getBoardPanel().setBoardArray(data.boardArray);
			gameFrame.getBoardPanel().setShiftButtonArray(data.shiftButtonArray);
			gameFrame.getBoardPanel().setPlayerLabels(data.playerLabels);

			// load player settings
			for (int p = 0; p < allPlayers.length; p++) {
				allPlayers[p].setPossessedCards(data.possessedCards[p]);
				allPlayers[p].setColumn(data.column[p]);
				allPlayers[p].setRow(data.row[p]);
			}

			// load tiles
			boardModel.setAllTiles(data.allTiles);
			boardModel.setFreeTile(data.freeTile);

			// load all the panels inside the game frame
			gameFrame.setBoardPanel(data.boardPanel);
			gameFrame.setFreeTilePanel(data.freeTilePanel);
			gameFrame.setPlayerPanel(data.playerPanel);
			gameFrame.setScoringPanel(data.scoringPanel);
			gameFrame.setMessagePanel(data.messagePanel);
			gameFrame.setGameRuleButton(data.gameRuleButton);
			gameFrame.setNewGameButton(data.newGameButton);
			gameFrame.setSaveButton(data.saveButton);

			// load int values in the controller
			turn = data.turn;
			controller.setPlayerState(data.playerState);
			controller.setCardNum(data.cardNum);
			
			// add action listeners to the controller
			controller.turningButtonActionListener();
			controller.insertButtonActionListener();
			controller.gameTileActionListener();
			
			// set visibility so that the user can see the frame
			gameFrame.setVisible(true);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;

	}

}




