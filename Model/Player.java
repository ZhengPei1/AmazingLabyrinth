package AmazingLabyrinth.Model;


import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

/*
Author: Zheng Pei - 50%, Fatimah Rana - 50%

Description: a class that represents a Player in the game, it is the superclass of AI

important fields:
1. Card[] possessedCards - array that stores the card the player owns
2. int column/ int row - the coordinate of the player on the board

Available Methods:
1. public Player(Card[], int, int) - constructor(Zheng Pei)
2. public Player(Player) - shallow copy constructor, only used by AI(Zheng Pei)
3. public boolean checkScore(Tile) - check if the current player can score a point(Zheng Pei)
4. public ArrayList<Tile> bfsAllPossibleMove(int, int, Tile[]) - returns all the tiles this player can move to
    within this turn, this method is only used by AI for simulation(Zheng Pei)
5. public boolean moveAble(int , int , Tile[] board) - returns true if the player can move to the given grid(Fatimah Rana)
6. mutators and accessors, notice some mutators are modified to work with the insert algorithm
7. toString method

Private Methods
N/A

 */

public class Player implements Serializable {
    private static final long serialVersionUID = 1234567L;
    // field that stores the card the player has
    private Card[] possessedCards;
    
    // the coordinate the player is standing on
    private int column;
    private int row;
    
    
    // constructor - Zheng Pei
    public Player(Card[] possessedCards, int column, int row){
        this.possessedCards = possessedCards;
        this.column = column;
        this.row = row;
    }
    
    // copy constructor - shallow copy - Zheng Pei
    public Player(Player player){
        this.possessedCards = player.getPossessedCards();
        this.column = player.getColumn();
        this.row = player.getRow();
    }
    
    // based on the ID of the tile and the card, determine if the player wins a score
    public boolean checkScore(Tile tile){
        for(Card card : possessedCards){
            if(Objects.equals(tile.getTileID(), card.getID())){
                card.setID(null);
                card.setImage(new ImageIcon("Images/Cards/CompletedCard.png"));
                return true;
            }
        }
        return false;
    }
    
    // use bfs algorithm to find where can the player move to if the player started at a given tile - Fatimah Rana, Zheng Pei
    public ArrayList<Tile> bfsAllPossibleMove(int fromRow, int fromCol, Tile[] board){
        
        // change the 1D array into an 2D array that simulate the maze
        Tile[][] gameBoard = new Tile[7][7];
        boolean[][] traveled = new boolean[7][7];
        ArrayList<Tile> result = new ArrayList<Tile>();
        
        
        // initialize arrays
        for(Tile tile : board)
            gameBoard[tile.getRow()][tile.getColumn()] = new Tile(tile);
        
        for(int i = 0; i < 7; ++i){
            for(int k = 0; k < 7; ++k){
                traveled[i][k] = false;
            }
        }
        
        // traverse through the array and find all connected tiles
        LinkedList<Tile> processingTiles = new LinkedList<>();
        processingTiles.add(gameBoard[fromRow][fromCol]);
        result.add(gameBoard[fromRow][fromCol]);
        
        while(!processingTiles.isEmpty()){
            Tile currentTile = processingTiles.removeFirst();
            
            // mark the processing tile as visited
            traveled[currentTile.getRow()][currentTile.getColumn()] = true;
            
            
            // connect to top side?
            if(currentTile.getRow() != 0 && currentTile.getOrientation()[0] &&
                    gameBoard[currentTile.getRow() - 1][currentTile.getColumn()].getOrientation()[1]){
    
                // check if the next tile has been visited
                if(!traveled[currentTile.getRow()-1][currentTile.getColumn()]){
                    processingTiles.add(gameBoard[currentTile.getRow() - 1][currentTile.getColumn()]);
                    result.add(gameBoard[currentTile.getRow() - 1][currentTile.getColumn()]);
                }
            }
            
            // connect to bottom side?
            if(currentTile.getRow() != 6 && currentTile.getOrientation()[1] &&
                    gameBoard[currentTile.getRow() + 1][currentTile.getColumn()].getOrientation()[0]){
    
                // check if the next tile has been visited
                if(!traveled[currentTile.getRow()+1][currentTile.getColumn()]){
                    processingTiles.add(gameBoard[currentTile.getRow() + 1][currentTile.getColumn()]);
                    result.add(gameBoard[currentTile.getRow() + 1][currentTile.getColumn()]);
                }

            }
            
            // connect to left side?
            if(currentTile.getColumn() != 0 && currentTile.getOrientation()[2] &&
                    gameBoard[currentTile.getRow()][currentTile.getColumn() - 1].getOrientation()[3]){
    
                // check if the next tile has been visited
                if(!traveled[currentTile.getRow()][currentTile.getColumn()-1]){
                    processingTiles.add(gameBoard[currentTile.getRow()][currentTile.getColumn() - 1]);
                    result.add(gameBoard[currentTile.getRow()][currentTile.getColumn() - 1]);
                }
            }
            
            // connect to the right side?
            if(currentTile.getColumn() != 6 && currentTile.getOrientation()[3] &&
                    gameBoard[currentTile.getRow()][currentTile.getColumn() + 1].getOrientation()[2]){
    
                // check if the next tile has been visited
                if(!traveled[currentTile.getRow()][currentTile.getColumn()+1]){
                    processingTiles.add(gameBoard[currentTile.getRow()][currentTile.getColumn() + 1]);
                    result.add(gameBoard[currentTile.getRow()][currentTile.getColumn() + 1]);
                }

            }
            
        }
        
        return result;
    }
    
    
    // the moving algorithm that detects whether a player can move to a place - Fatimah Rana
    public boolean moveAble(int toRow, int toCol, Tile[] board){
        // change the 1D array into an 2D array that simulate the maze
        Tile[][] gameBoard = new Tile[7][7];
        boolean[][] traveled = new boolean[7][7];
        
        // initialize arrays
        for(Tile tile : board)
            gameBoard[tile.getRow()][tile.getColumn()] = new Tile(tile);
        
        for(int i = 0; i < 7; ++i){
            for(int k = 0; k < 7; ++k){
                traveled[i][k] = false;
            }
        }
        
        // traverse through the array and find all connected tiles
        LinkedList<Tile> processingTiles = new LinkedList<>();
        processingTiles.add(gameBoard[getRow()][getColumn()]);
        
        while(!processingTiles.isEmpty()){
            Tile currentTile = processingTiles.removeFirst();
            
            // check if the current tile has been visited
            if(traveled[currentTile.getRow()][currentTile.getColumn()]){
                continue;
            }else{
                traveled[currentTile.getRow()][currentTile.getColumn()] = true;
            }
            
            // if we can travel to destination return true
            if(currentTile.getRow() == toRow && currentTile.getColumn() == toCol) return true;
            
            // connect to top side?
            if(currentTile.getRow() != 0 && currentTile.getOrientation()[0] &&
                    gameBoard[currentTile.getRow() - 1][currentTile.getColumn()].getOrientation()[1]){
                processingTiles.add(gameBoard[currentTile.getRow() - 1][currentTile.getColumn()]);
                
            }
            
            // connect to bottom side?
            if(currentTile.getRow() != 6 && currentTile.getOrientation()[1] &&
                    gameBoard[currentTile.getRow() + 1][currentTile.getColumn()].getOrientation()[0]){
                processingTiles.add(gameBoard[currentTile.getRow() + 1][currentTile.getColumn()]);
            }
            
            // connect to left side?
            if(currentTile.getColumn() != 0 && currentTile.getOrientation()[2] &&
                    gameBoard[currentTile.getRow()][currentTile.getColumn() - 1].getOrientation()[3]){
                processingTiles.add(gameBoard[currentTile.getRow()][currentTile.getColumn() - 1]);
                
            }
            
            // connect to the right side?
            if(currentTile.getColumn() != 6 && currentTile.getOrientation()[3] &&
                    gameBoard[currentTile.getRow()][currentTile.getColumn() + 1].getOrientation()[2]){
                processingTiles.add(gameBoard[currentTile.getRow()][currentTile.getColumn() + 1]);
            }
            
        }
        
        return false;
    }
    
    
    // mutators and accessors(some are adjusted to work with other algorithms) - Zheng Pei
    public Card[] getPossessedCards() {
        return possessedCards;
    }
    
    public void setPossessedCards(Card[] possessedCards) {
        this.possessedCards = possessedCards;
    }
    
    public int getColumn() {
        return column;
    }
    
    public void setColumn(int column) {
        // if a player gets pushed out of the board, it should reappear on the opposite side of the board
        if(column < 0){
            this.column = 6;
        } else if (column > 6) {
            this.column = 0;
        }else{
            this.column = column;
        }
    }
    
    public int getRow() {
        return row;
    }
    
    public void setRow(int row) {
        // if a player gets pushed out of the board, it should reappear on the opposite side of the board
        if(row < 0){
            this.row = 6;
        } else if (row > 6) {
            this.row = 0;
        }else{
            this.row = row;
        }
    }
    
    
    // to String method
    @Override
    public String toString() {
        return "Player{" +
                "possessedCards=" + Arrays.toString(possessedCards) +
                '}';
    }
}
