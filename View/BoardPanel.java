package AmazingLabyrinth.View;

import AmazingLabyrinth.Model.Board;
import AmazingLabyrinth.Model.Player;
import AmazingLabyrinth.Model.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/*
Author: Zheng Pei

Description: the GUI aspect of the Board Model, it contains all the tiles(in the form of JButton), all the players
(JLabel), and all the insert button(JButton)

Important Fields:
1. JButton[][] boardArray - the array that stores JButtons based on their corresponding location on the board, boardArray[row][col]
2. JButton[] shiftButtonArray - buttons used by player to insert free tiles
3. JLabel[] playerLabels - an array that contains all JLabel that represents different players on the board

Available Methods:
1. public BoardPanel(Board, int) - constructor(Zheng Pei)
2. public void updateBoard(Board, Player[]) -  this function should be called whenever a tile/ or player on the board
    is changed/moved, it will update the GUI (Zheng Pei)
3. mutators and accessors

Private Methods
1. private void initializeBoard(Board) - this function will initialize the GUI component of the board, it should only
    be called once when an object of this class is created (Zheng Pei)
2. private void initializePlayerLabel(int) - initialize player labels based on the number of players in the game(Zheng Pei)
3. @Override
    protected void paintComponent(Graphics) - this method is override to paint an image as the background of the panel(Zheng Pei)
    
 */

public class BoardPanel extends JPanel implements Serializable {
    private static final long serialVersionUID = 1234567L;
    
    // the array that stores JButtons based on their corresponding location on the board - boardArray[row][col]
    private static JButton[][] boardArray = new JButton[7][7];
    
    // the buttons used by player to insert free tiles
    private JButton[] shiftButtonArray = new JButton[12];
    
    // contains all JLabel that represents different players on the board
    private JLabel[] playerLabels;
    
    // constructor
    public BoardPanel(Board boardModel, int numPlayer){
        setLayout(null);
        
        // initialize gameBoard and player labels
        initializeBoard(boardModel);
        initializePlayerLabel(numPlayer);
    }
    
    
    // this function will initialize the board, it should only be called once when an object of this class is created - Zheng Pei
    private void initializeBoard(Board boardModel){
        Tile[] allTiles = boardModel.getAllTiles();
        
        // fill the game board with JButtons and specify the location of each JButton
        for(int row = 0; row < 7; ++row){
            for (int col = 0; col < 7; col++) {
                boardArray[row][col] = new JButton();
                boardArray[row][col].setContentAreaFilled(false);
                boardArray[row][col].setBounds( col*96+96, row*96+96, 96, 96);
                boardArray[row][col].setLayout(null);
                add(boardArray[row][col]);
            }
        }
        
        // load the image of the non-permanent tiles
        for(Tile t: allTiles){
            boardArray[t.getRow()][t.getColumn()].setIcon(t.getImage());
            repaint();
        }
        
        // make buttons that are used for tile insertion
        for(int i = 0; i < 12; ++i){
            ImageIcon icon = new ImageIcon("Images/GUIImages/arrow" + (i/3) + ".png");
            Image newImg = icon.getImage().getScaledInstance( 80, 80,  java.awt.Image.SCALE_SMOOTH ) ;
            icon = new ImageIcon( newImg );
            shiftButtonArray[i] = new JButton();
            
            // set up the background of the button
            shiftButtonArray[i].setContentAreaFilled(false);
            shiftButtonArray[i].setBorderPainted(false);
            shiftButtonArray[i].setFocusPainted(false);
            shiftButtonArray[i].setIcon(icon);
        }
        
        
        // specify the location of each insert button
        for(int i = 0; i < 3; ++i){
            shiftButtonArray[i].setBounds(192 + i*96*2, 0, 96, 96);
            shiftButtonArray[i+3].setBounds(192 + i*96*2, 768, 96, 96);
            shiftButtonArray[i+6].setBounds(0, 192 + i*96*2, 96, 96);
            shiftButtonArray[i+9].setBounds(768, 192 + i*96*2, 96, 96);
        }
        
        // add JButtons onto the screen
        for(JButton jButton : shiftButtonArray){
            add(jButton);
        }
    }
    
    // initialize player labels based on the number of players in the game - Zheng Pei
    private void initializePlayerLabel(int numPlayer){
        playerLabels = new JLabel[numPlayer];
        
        // specify the location of player labels and placed them onto the board
        for(int player = 0; player < numPlayer; ++player){
            
            playerLabels[player] = new JLabel();
            playerLabels[player].setIcon(new ImageIcon("Images/PlayerIcons/Player" + player + ".png"));
            
            // specify coordinates, then add onto the screen
            if(player == 0){
                playerLabels[player].setBounds(0, 0, 50, 55);
                boardArray[0][0].add(playerLabels[player]);
            }else if (player == 1){
                playerLabels[player].setBounds(40, 40, 50, 55);
                boardArray[6][6].add(playerLabels[player]);
            }else if(player == 2) {
                playerLabels[player].setBounds(0, 40, 50, 55);
                boardArray[6][0].add(playerLabels[player]);
            }else{
                playerLabels[player].setBounds(40, 0, 50, 55);
                boardArray[0][6].add(playerLabels[player]);
            }
        }
    }
    
    // this function should be called whenever a tile/ or player on the board is changed/moved, it will update the GUI - Zheng Pei
    public void updateBoard(Board boardModel, Player[] allPlayers){
        
        // clear the player image on all the tiles
        for(JButton[] buttons : boardArray)
            for(JButton button : buttons) button.removeAll();
        
        // update the imageIcon of tiles based on the new tiles order
        Tile[] allTiles = boardModel.getAllTiles();
        for(Tile t: allTiles){
            boardArray[t.getRow()][t.getColumn()].setIcon(t.getImage());
        }
        
        // update Player by adding the player on tile
        for(int player = 0; player < allPlayers.length; ++player){
            boardArray[allPlayers[player].getRow()][allPlayers[player].getColumn()].add(playerLabels[player]);
        }
        
        repaint();
    }
    
    // set an image as the background of the gameBoard by overriding the paintComponent method - Zheng Pei
    @Override
    protected void paintComponent(Graphics g) {
        // set the image bingoCard As the background of the bingo panel
        Image image;
        try {
            image = ImageIO.read(new File("Images/GUIImages/GameBoard.png"));
            super.paintComponent(g);
            // auto resize before setting as background
            g.drawImage(image, 96, 96, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // mutators and accessors - Zheng Pei
    public static JButton[][] getBoardArray() {
        return boardArray;
    }
    
    public void setBoardArray(JButton[][] boardArray) {
        this.boardArray = boardArray;
    }
    
    public JButton[] getShiftButtonArray() {
        return shiftButtonArray;
    }
    
    public void setShiftButtonArray(JButton[] shiftButtonArray) {
        this.shiftButtonArray = shiftButtonArray;
    }
    
    public JLabel[] getPlayerLabels() {
        return playerLabels;
    }
    
    public void setPlayerLabels(JLabel[] playerLabels) {
        this.playerLabels = playerLabels;
    }
    
}