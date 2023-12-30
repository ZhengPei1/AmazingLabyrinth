package AmazingLabyrinth.Controller;

import AmazingLabyrinth.Model.*;
import AmazingLabyrinth.View.BoardPanel;
import AmazingLabyrinth.View.GameFrame;
import AmazingLabyrinth.View.GameRuleFrame;
import AmazingLabyrinth.View.PickFrame;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.*;

/*
Author: Zheng Pei 80%, Hanyu Zhou 20%

Description: the logical units for the whole program, all the components, methods or classes can be accessed and
			 modified in this file directly or indirectly.

Important Fields:
1. GameFrame gameFrame - the JFrame of the game, all the GUI components on the JFrame can be accessed through this
2. Board BoardModel - an instance of the abstract(non-GUI) game board
3. Player[] allPlayers - an array that holds all the players
4. int turn - indicates who's turn is it now
5. Timer timer - the timer used to slow down AI's move
6. boolean inserted - used to record whether the current player already inserted a tile in this turn
7. boolean gameEnd - used to indicate whether a game ended or not

Available Methods:
1. public Controller(int[], int) - initialize the game with given info (Zheng Pei 50%, Hanyu Zhou 50%)
2. private void insertButtonActionListener() - sets up the actionListener for all the insert buttons around the game board (Zheng Pei)
3. private void turningButtonActionListener() - set up the action listener for turning buttons (Zheng Pei)
4. private void gameTileActionListener() - set up ActionListener for all the game board tiles -> which are JButtons,
    so that there's a way to detect the tile the player wants to move to - (Zheng Pei)
5. public void endGameCheck() - check if the game ended or not (Zheng Pei)
6. mutators and accessors

Private Methods:
1. private Player[] initializePlayer(int[] , int)  - initializes all player objects  (Zheng Pei)
2. private void humanTurn(final int, final int ) - simulates a turn of human player (Hanyu Zhou)
3. private void AITurn() - simulates a turn of AI (Zheng Pei)
4. @Override
    public void actionPerformed(ActionEvent event)  - set up action listener for the three game frame buttons(Hanyu Zhou)
    
 */

public class Controller implements ActionListener, Serializable {
    private static final long serialVersionUID = 1234567L;
    
    private Controller controller = this;
    
    // create an object of gameFrame and Board, and a list of players - Zheng Pei
    private GameFrame gameFrame;
    private Board boardModel = new Board();
    private Player[] allPlayers;
    private SaveProgress save;
    
    // this indicates the player that can move this turn
    private int turn = 0;
    
    // save user's choice from the previous frame
    private int[] playerState = new int[4];
    private int cardNum;
    
    // the timer used to control the AI's speed
    private Timer timer ;
    
    private boolean inserted = false;
    
    private boolean gameEnd = false;
    
    // constructor that initialize the gameFrame and set up the actionListeners as needed - Zheng Pei 50%, Hanyu Zhou 50%
    public Controller(int[] playerState, int cardNum) {
        this.playerState = playerState;
        this.cardNum = cardNum;
        this.allPlayers = initializePlayer(playerState, cardNum);
        
        // set up all the action listeners and GUI components
        gameFrame = new GameFrame(boardModel, allPlayers);
        turningButtonActionListener();
        insertButtonActionListener();
        gameTileActionListener();
        gameFrame.getPlayerPanel().addTurnLabel(0);
        gameFrame.getGameRuleButton().addActionListener(this);
        gameFrame.getNewGameButton().addActionListener(this);
        gameFrame.getSaveButton().addActionListener(this);
        
        // pass components for saving
        save = new SaveProgress(controller,gameFrame,boardModel,allPlayers,turn);
        
        // if the first player is AI, automatically trigger the move
        if (playerState[0] == 1) {
            timer = new Timer(4000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AITurn();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
        
    }
    
    // method that initializes players and dealing out cards - Zheng Pei
    private Player[] initializePlayer(int[] playerState, int cardNum) {
        
        // an array that stores all the human players
        int playerNum = 4;
        for (int i : playerState)
            if (i == 2) playerNum--;
        
        Player[] allPlayers = new Player[playerNum];
        
        // read in all the cards, then randomize it
        Card[] cards = Utility.readCards();
        List<Card> shuffleList = Arrays.asList(cards);
        Collections.shuffle(shuffleList);
        shuffleList.toArray(cards);
        
        // load player
        for (int player = 0; player < 4; ++player) {
            
            // assign enough cards to each player
            Card[] playerCards = new Card[cardNum];
            
            for (int i = 0; i < cardNum; ++i) {
                playerCards[i] = cards[i + cardNum * player];
            }
            
            // specify player location
            int row;
            int column;
            if (player == 0) {
                row = 0;
                column = 0;
            } else if (player == 1) {
                row = 6;
                column = 6;
            } else if (player == 3) {
                row = 6;
                column = 0;
            } else {
                row = 0;
                column = 6;
            }
            
            // human player
            if (playerState[player] == 0) allPlayers[player] = new Player(playerCards, row, column);
                
                // AI player
            else if (playerState[player] == 1) allPlayers[player] = new AI(playerCards, row, column, playerNum);
            
        }
        
        // return the list of players
        return allPlayers;
    }
    
    // method that sets up the actionListener for all the insert buttons around the game board - Zheng Pei
    public void insertButtonActionListener() {
        
        // set up the actionListener for the insert buttons in boardPanel
        JButton[] shiftButtonArray = gameFrame.getBoardPanel().getShiftButtonArray();
        
        for (int shift = 1, index = 0; shift <= 5; shift += 2, index++) {
            
            // the row/column that will be shifted when this button is clicked
            final int shiftPos = shift;
            
            // for the three buttons on the top
            shiftButtonArray[index].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // if the player inserted a tile already
                    if (inserted) {
                        gameFrame.getMessagePanel().getMsgTextArea().append("You already inserted a tile this turn.\r\n");
                        return;
                    }
                    
                    // call the shift function
                    else if (!boardModel.shiftColumn(shiftPos, true, allPlayers)) {
                        gameFrame.getMessagePanel().getMsgTextArea().append("You cannot insert a tile here.\r\n");
                        return;
                    }
                    
                    // set inserted to true
                    inserted = true;
                    
                    // update the board and the freeTile
                    gameFrame.getBoardPanel().updateBoard(boardModel, allPlayers);
                    gameFrame.getFreeTilePanel().updateFreeTile(boardModel);
                }
            });
            
            // for the three buttons on the bottom
            shiftButtonArray[index + 3].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    // if the player inserted a tile already
                    if (inserted) {
                        gameFrame.getMessagePanel().getMsgTextArea().append("You already inserted a tile this turn.\r\n");
                        return;
                    }
                    
                    // call the shift function
                    else if (!boardModel.shiftColumn(shiftPos, false, allPlayers)) {
                        gameFrame.getMessagePanel().getMsgTextArea().append("You cannot insert a tile here.\r\n");
                        return;
                    }
                    
                    // set inserted to true
                    inserted = true;
                    
                    // update the board and the freeTile
                    gameFrame.getBoardPanel().updateBoard(boardModel, allPlayers);
                    gameFrame.getFreeTilePanel().updateFreeTile(boardModel);
                }
            });
            
            // for the three buttons on the left
            shiftButtonArray[index + 6].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    // if the player inserted a tile already
                    if (inserted) {
                        gameFrame.getMessagePanel().getMsgTextArea().append("You already inserted a tile this turn.\r\n");
                        return;
                    }
                    
                    // call the shift function
                    else if (!boardModel.shiftRow(shiftPos, true, allPlayers)) {
                        gameFrame.getMessagePanel().getMsgTextArea().append("You cannot insert a tile here.\r\n");
                        return;
                    }
                    
                    // set inserted to true
                    inserted = true;
                    
                    // update the board and the freeTile
                    gameFrame.getBoardPanel().updateBoard(boardModel, allPlayers);
                    gameFrame.getFreeTilePanel().updateFreeTile(boardModel);
                }
            });
            
            // for the three buttons on the right
            shiftButtonArray[index + 9].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    // if the player inserted a tile already
                    if (inserted) {
                        gameFrame.getMessagePanel().getMsgTextArea().append("You already inserted a tile this turn.\r\n");
                        return;
                    }
                    
                    // call the shift function
                    else if (!boardModel.shiftRow(shiftPos, false, allPlayers)) {
                        gameFrame.getMessagePanel().getMsgTextArea().append("You cannot insert a tile here.\r\n");
                        return;
                    }
                    
                    // set inserted to true
                    inserted = true;
                    
                    // update the board and the freeTile
                    gameFrame.getBoardPanel().updateBoard(boardModel, allPlayers);
                    gameFrame.getFreeTilePanel().updateFreeTile(boardModel);
                }
            });
        }
    }
    
    // set up the action listener for turning buttons - Zheng Pei
    public void turningButtonActionListener() {
        
        // set up the left and right rotation buttons by calling rotation methods in the Utility class
        gameFrame.getFreeTilePanel().getRotateLeftButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tile freeTile = Utility.rotateTileLeft(boardModel.getFreeTile());
                
                // update GUI
                boardModel.setFreeTile(freeTile);
                gameFrame.getFreeTilePanel().updateFreeTile(boardModel);
            }
        });
        
        gameFrame.getFreeTilePanel().getRotateRightButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tile freeTile = Utility.rotateTileRight(boardModel.getFreeTile());
                
                // update GUI
                boardModel.setFreeTile(freeTile);
                gameFrame.getFreeTilePanel().updateFreeTile(boardModel);
            }
        });
    }
    
    // set up ActionListener for all the game board tiles -> which are JButtons, so that there's a way to detect
    // the tile the player wants to move to - Zheng Pei
    public void gameTileActionListener() {
        
        // when the user clicked on one of the JButton(tile), the JButton will call the HumanTurn or AI turn method
        JButton[][] gameBoard = gameFrame.getBoardPanel().getBoardArray();
        
        // add actionListener for every JButton on the board
        for (int row = 0; row < 7; ++row) {
            for (int col = 0; col < 7; ++col) {
                final int colNum = col;
                final int rowNum = row;
                
                gameBoard[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // stop the user from moving if the timer is still running
                        if (timer != null && timer.isRunning()) {
                            return;
                        }
                        
                        // the user must insert first before they move
                        if (!inserted) {
                            // tell the user that they cannot insert
                            gameFrame.getMessagePanel().getMsgTextArea().append("You must insert a tile before you move.\r\n");
                            return;
                        }
                        
                        // perform human turn first
                        humanTurn(rowNum, colNum);
                        
                        // check if next turn is AI's turn, if so, help AI to complete AI's turn
                        if (allPlayers[turn] instanceof AI) {
                            AITurn();
                        }
                        
                    }
                });
            }
        }
    }
    
    // simulates a turn of human player Hanyu Zhou
    private void humanTurn(final int rowNum, final int colNum) {
        // check if game ended
        if (gameEnd) {
            gameFrame.changeGameState(false);
            return;
        }
        
        // check if the user can move to the tile, display message in the msg panel
        if (!allPlayers[turn].moveAble(rowNum, colNum, boardModel.getAllTiles())) {
            gameFrame.getMessagePanel().getMsgTextArea().append("Unavailable tile! Please select another one!\r\n");
            return;
        } else {
            // if they can, show them which coord they are on
            gameFrame.getMessagePanel().getMsgTextArea().append(
                    "Player " + (turn + 1) + ", you moved to (" + (rowNum + 1) + ", " + (colNum + 1) + ").\r\n");
        }
        
        // update the player on the board
        allPlayers[turn].setRow(rowNum);
        allPlayers[turn].setColumn(colNum);
        
        // check if the user scored or not
        Tile[] allTiles = boardModel.getAllTiles();
        for (Tile tile : allTiles) {
            if (tile.getColumn() == colNum && tile.getRow() == rowNum) {
                if (allPlayers[turn].checkScore(tile)) {
                    gameFrame.getPlayerPanel().updateCards(allPlayers);
                    gameFrame.getScoringPanel().changeScore(turn);
                    // show congrat msg if the user scored
                    gameFrame.getMessagePanel().getMsgTextArea().append("Congratulations Player " + (turn + 1) + "! You have just scored a point!\r\n");
                }
                break;
            }
        }
        
        // update the board
        gameFrame.getBoardPanel().updateBoard(boardModel, allPlayers);
        
        // check if game ended
        endGameCheck();
        
        // switch turn
        turn++;
        if (turn == allPlayers.length)
            turn = 0;
        // set inserted back to false
        inserted = false;
        // set turn label to the next player
        gameFrame.getPlayerPanel().updateTurnLabel(turn);
        
        highLightTile(BoardPanel.getBoardArray()[rowNum][colNum]);
        
    }
    
    private void highLightTile(JButton currentTile) {
        
        currentTile.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        
    }
    
    private void setBorder(MatteBorder createMatteBorder) {
        // TODO Auto-generated method stub
        
    }
    
    // simulates a turn of AI - Zheng Pei
    private void AITurn() {
        
        // change the tile state to inserted to stop human player to insert a tile in AI's turn
        inserted = true;
        
        // check if game ended
        if (gameEnd) {
            gameFrame.changeGameState(false);
            return;
        }
        
        // make a deep copy of every parameter before passing them in - avoid change!
        Board copyBoard = new Board(boardModel);
        AI copyAI = new AI((AI) allPlayers[turn]);
        Player copyPlayer;
        
        // find the player that moves after AI moves
        if (turn + 1 == allPlayers.length) {
            copyPlayer = new Player(allPlayers[0]);
        } else {
            copyPlayer = new Player(allPlayers[turn + 1]);
        }
        
        // find move for AI
        int[] AIMoveInfo = copyAI.findMove(copyAI, copyPlayer, copyBoard);
        
        // click on the rotation button based on the AIMove algorithm result
        for (int rotateTime = 0; rotateTime < AIMoveInfo[5]; ++rotateTime) {
            gameFrame.getFreeTilePanel().getRotateRightButton().doClick();
        }
        String test = boardModel.toString();
        //  complete the shift that needs to be done
        if (AIMoveInfo[1] == 1) {
            boardModel.shiftColumn(AIMoveInfo[0], AIMoveInfo[2] == 1, allPlayers);
        } else {
            boardModel.shiftRow(AIMoveInfo[0], AIMoveInfo[2] == 1, allPlayers);
        }
        System.out.println(Objects.equals(test, boardModel.toString()));
        // pause the program for 2sec to let the user see AI's insert
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // update the board and the freeTile
                gameFrame.getBoardPanel().updateBoard(boardModel, allPlayers);
                gameFrame.getFreeTilePanel().updateFreeTile(boardModel);
            }
        });
        
        timer.setRepeats(false);
        timer.start();
        
        // pause the program for 2sec to let the user see AI's move
        timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // make sure the move is legal
                if(!allPlayers[turn].moveAble(AIMoveInfo[3], AIMoveInfo[4], boardModel.getAllTiles())){
                    System.out.println(AIMoveInfo[3] + AIMoveInfo[4]);
                    AIMoveInfo[3] = allPlayers[turn].getRow();
                    AIMoveInfo[4] = allPlayers[turn].getColumn();
                    
                }
                // perform the move
                allPlayers[turn].setRow(AIMoveInfo[3]);
                allPlayers[turn].setColumn(AIMoveInfo[4]);
                
                // update the message panel and the game board
                gameFrame.getMessagePanel().getMsgTextArea().append(
                        "Player " + (turn + 1) + ", you moved to (" + (AIMoveInfo[3] + 1) + ", " + (AIMoveInfo[4] + 1) + ").\r\n");
                gameFrame.getBoardPanel().updateBoard(boardModel, allPlayers);
                
                // check if the AI scored or not
                Tile[] allTiles = boardModel.getAllTiles();
                for (Tile tile : allTiles) {
                    if (tile.getColumn() == AIMoveInfo[4] && tile.getRow() == AIMoveInfo[3]) {
                        if (allPlayers[turn].checkScore(tile)) {
                            
                            // if AI scored, print congrats msg
                            gameFrame.getPlayerPanel().updateCards(allPlayers);
                            gameFrame.getScoringPanel().changeScore(turn);
                            gameFrame.getMessagePanel().getMsgTextArea().append("Congratulations Player " + (turn + 1) + "! You have just scored a point!\r\n");
                        }
                        break;
                    }
                }
                
                // check if game ended
                endGameCheck();
                
                // switch turn
                turn++;
                if (turn == allPlayers.length)
                    turn = 0;
                
                // set inserted back to false and turn label to the next player
                inserted = false;
                gameFrame.getPlayerPanel().updateTurnLabel(turn);
                
                // if next turn is still AI's turn, run the AI
                if (allPlayers[turn] instanceof AI) {
                    AITurn();
                }
                
            }
        });
        
        timer.setRepeats(false);
        timer.start();
    }
    
    // end game check - Zheng Pei
    public void endGameCheck() {
        
        int nullCard = 0;
        for (Player player : allPlayers) {
            // check how many cards has been completed
            Card[] possessedCards = player.getPossessedCards();
            for (Card card : possessedCards)
                if (card.getID() == null) {
                    nullCard++;
                }
            
            // if the completed cards # == the cards that a player owns
            if (nullCard == possessedCards.length) {
                
                // then the game should end because a player collected all the cards
                gameEnd = true;
                gameFrame.getMessagePanel().getMsgTextArea().append("Game Ended\r\n");
                gameFrame.changeGameState(false);
                return;
            }
            
            // reset the number
            nullCard = 0;
        }
    }
    
    // mutators and accessors - Hanyu Zhou
    public GameFrame getGameFrame() {
        return gameFrame;
    }
    
    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }
    
    public Board getBoardModel() {
        return boardModel;
    }
    
    public void setBoardModel(Board boardModel) {
        this.boardModel = boardModel;
    }
    
    public Player[] getAllPlayers() {
        return allPlayers;
    }
    
    public void setAllPlayers(Player[] allPlayers) {
        this.allPlayers = allPlayers;
    }
    
    public int getTurn() {
        return turn;
    }
    
    public void setTurn(int turn) {
        this.turn = turn;
    }
    
    public int[] getPlayerState() {
        return playerState;
    }
    
    public void setPlayerState(int[] playerState) {
        this.playerState = playerState;
    }
    
    public int getCardNum() {
        return cardNum;
    }
    
    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }
    
    // Hanyu Zhou
    // action listener for game frame buttons
    @Override
    public void actionPerformed(ActionEvent event) {
        
        // open different frames based on the button the player selected
        if (event.getSource() == gameFrame.getGameRuleButton()) {
            
            // open the game rule frame
            new GameRuleFrame();
            
        } else if (event.getSource() == gameFrame.getNewGameButton()) {
            // if the AI is running, disable the player from reopening the game
            if(timer != null && timer.isRunning()){
                JOptionPane.showMessageDialog(null, "You are not allowed to reopen a game when the AI is running", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // close the current frame and open pick frame
            gameFrame.dispose();
            new PickFrame();
            
        } else if (event.getSource() == gameFrame.getSaveButton()) {
    
            // if the AI is running, disable the player from saving the game
            if(timer != null && timer.isRunning()){
                JOptionPane.showMessageDialog(null, "You are not allowed to save the game when the AI is running", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // save the game
            save.saveGame();
            // show a message saying the game is saved
            JOptionPane.showMessageDialog(null, "Game has been saved!", "GAME SAVED",
                    JOptionPane.INFORMATION_MESSAGE);
            
        }
        
    }
    
    
}