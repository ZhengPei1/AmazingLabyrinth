package AmazingLabyrinth.Model;

import javax.swing.*;

import java.io.Serializable;
import java.util.Arrays;

/*
Author: Zheng Pei

Description: a class that represents a tile in the game

important fields:
1. int column/row - the coordinates of the tile
2. ImageIcon image - the image of the tile
3. boolean[] orientation - represents the orientation of a tile, orientation[0][1][2][3] represents if the tile
    has a path to top side, bottom side, left side and right side
4. int tileNum - the number of the tile, ranged from 0-3, used to determine the orientation of the tile
5. String tileShape - tileShape is final and it can be "I" "L" or "T"
6. String tileID - if the ID of a card is the same as the ID of a tile, that means the tile has
    the treasure that matches this Card

Available Methods:
1. public Player(Card[], int, int) - constructor(Zheng Pei)
2. public Player(Player) - shallow copy constructor, only used by AI(Zheng Pei)
3. public boolean checkScore(Tile) - check if the current player can score a point(Zheng Pei)
4. public ArrayList<Tile> bfsAllPossibleMove(int, int, Tile[]) - returns all the tiles this player can move to
    within this turn, this method is only used by AI for simulation(Zheng Pei)
5. public boolean moveAble(int , int , Tile[] board) - returns true if the player can move to the given grid(Fatimah Rana)
6. mutators and accessors, notice some mutators are modified to work with the insert algorithm (Zheng Pei)
7. toString method

Private Methods
N/A

 */

public class Tile implements Serializable{
    private static final long serialVersionUID = 1234567L;
    
    // instance variables, when column and row == -1, that means this tile is the free tile - Zheng Pei
    private int column;
    private int row;
    private ImageIcon image;
    private boolean[] orientation;
    private int tileNum;
    private final String tileShape;
    private final String tileID;
    
    // constructor - initialize all variables - Zheng Pei
    public Tile(int column, int row, ImageIcon image, boolean[] orientation, String tileID, int tileNum, String tileShape) {
        this.column = column;
        this.row = row;
        this.image = image;
        this.orientation = orientation;
        this.tileID = tileID;
        this.tileNum = tileNum;
        this.tileShape = tileShape;
    }
    
    // copy constructor for tile - deep copy
    public Tile(Tile tile){
        this.column = tile.getColumn();
        this.row = tile.getRow();
        this.image = tile.getImage();
        this.orientation = tile.getOrientation();
        this.tileID = tile.getTileID();
        this.tileNum = tile.getTileNum();
        this.tileShape = new String(tile.getTileShape());
    }
    
    // mutators and accessors - some of them are modified to detect if a tile is removed from the board - Zheng Pei
    public int getColumn() {
        return column;
    }
    
    public boolean setColumn(int column) {
        // when setting columns, if column < 0 or > 6, that means the tile has been pushed out of the board
        if(column < 0 || column > 6){
            return false;
        }else{
            this.column = column;
            return true;
        }
    }
    
    public int getRow() {
        return row;
    }
    
    public boolean setRow(int row) {
        // when setting rows, if row < 0 or > 6, that means the tile has been pushed out of the board
        if(row < 0 || row > 6){
            return false;
        }else{
            this.row = row;
            return true;
        }
    }
    
    public ImageIcon getImage() {
        return image;
    }
    
    public void setImage(ImageIcon image) {
        this.image = image;
    }
    
    public boolean[] getOrientation() {
        return orientation;
    }
    
    public void setOrientation(boolean[] orientation) {
        this.orientation = orientation;
    }
    
    public String getTileID() {
        return tileID;
    }
    
    public int getTileNum() {
        return tileNum;
    }
    
    public void setTileNum(int tileNum) {
        this.tileNum = tileNum;
    }
    
    public String getTileShape() {
        return tileShape;
    }
    
    // to string method
    @Override
    public String toString() {
        return "Tile{" +
                "column=" + column +
                ", row=" + row +
                ", image=" + image +
                ", orientation=" + Arrays.toString(orientation) +
                ", tileNum=" + tileNum +
                ", tileID='" + tileID + '\'' +
                '}';
    }
}