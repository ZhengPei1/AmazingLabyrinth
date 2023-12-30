package AmazingLabyrinth.Model;

import javax.swing.*;

import java.io.Serializable;
import java.util.*;

/*
Author: Zheng Pei

Description: a class that initializes the board with random tiles, the insertion method is included in this class,
all the info related to the game board are stored and can be accessed from this class

important fields:
1. Tile[] allTiles - this array contains all the tiles on the game board
2. Tile freeTile - this variable holds the free tile

Available Methods:
1. public Board() - the constructor (Zheng Pei)
2. public Board(Board board) - deep copy constructor, only used for AI (Zheng Pei)
3. public boolean shiftColumn/shiftRow(int, boolean, Player[]) - shifts a column/row of the game board include player,
    and returns true when the shift is valid (Zheng Pei)
4. mutators and accessors
5. toString method

Private Methods
1. private void makeTiles() - randomly select and initialize tiles (Zheng Pei)
2. private void randomizeTiles() - randomly place all the tiles onto the board (Zheng Pei)

 */

public class Board implements Serializable {
    private static final long serialVersionUID = 1234567L;
    
    // fields used to store all the tills and the free tile - Zheng Pei
    private Tile[] allTiles;
    private Tile freeTile;
    
    // fields used to store the info of last inserted tile - make sure that the player can't reverse a move - Zheng Pei
    private boolean lastInsertCol = false;
    private boolean goDownOrRight = false;
    private int rowOrColNum = 0;
    
    
    // constructor - Zheng Pei
    public Board() {
        // randomly select tiles and distribute tiles on the board
        makeTiles();
        randomizeTiles();
    }
    
    // copy constructor - deep copy - Zheng Pei
    public Board(Board board){
        this.allTiles = new Tile[49];
        for(int i = 0; i < 49; ++i){
            this.allTiles[i] = new Tile(board.getAllTiles()[i]);
        }
        this.freeTile = new Tile(board.getFreeTile());
        this.lastInsertCol = board.isLastInsertCol();
        this.goDownOrRight = board.isGoDownOrRight();
        this.rowOrColNum = board.getRowOrColNum();
    }
    
    // randomly select and initialize tiles - Zheng Pei
    private void makeTiles() {
        // initialize all tiles array
        allTiles = new Tile[34];
        
        // randomly select 12 treasure tiles using info read from csv file, exclude permanent tiles
        String[][] treasureState = Utility.readTreasure();
        for (int i = 0, insert = 0; i < 24; ++i) {
            
            if (Objects.equals(treasureState[i][3], "false")) {
                // this is a permanent tile, skip
                continue;
            }
            
            // read in new treasure tiles
            int randomNum = (int) (Math.random() * 4);
            allTiles[insert] = new Tile(0, 0, new ImageIcon("Images/Tiles/" + treasureState[i][0] + randomNum + ".png"),
                    Utility.tileOrientation(randomNum, treasureState[i][1]), treasureState[i][0], randomNum, treasureState[i][1]);
            insert++;
        }
        
        
        // select 12 I tiles and 8 L tiles by selecting I0-I4 for 3 times, L0-L4 for 2 times
        ArrayList<Tile> pathwayTile = new ArrayList<Tile>(34);
        int insertIndex = 12;
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k <= 3; ++k) {
                allTiles[insertIndex] = new Tile(0, 0, new ImageIcon("Images/Tiles/I" + k + ".png"), Utility.tileOrientation(k, "I"), "I", k, "I");
                insertIndex++;
                
                if (i != 2) {
                    allTiles[insertIndex] = new Tile(0, 0, new ImageIcon("Images/Tiles/L" + k + ".png"), Utility.tileOrientation(k, "L"), "L", k, "L");
                    insertIndex++;
                }
            }
        }
        
        // randomly select 1 I tile and 1 L tile
        int randomNum = (int) (Math.random() * 4);
        allTiles[insertIndex] = new Tile(0, 0, new ImageIcon("Images/Tiles/I" + randomNum + ".png"), Utility.tileOrientation(randomNum, "I"), "I", randomNum, "I");
        insertIndex++;
        
        randomNum = (int) (Math.random() * 4);
        allTiles[insertIndex] = new Tile(0, 0, new ImageIcon("Images/Tiles/L" + randomNum + ".png"), Utility.tileOrientation(randomNum, "L"), "L", randomNum, "L");
    }
    
    
    // function randomizes all the tiles on the board as well as randomly selecting one tile as the freeTile - Zheng Pei
    private void randomizeTiles() {
        // shuffle all the tiles
        List<Tile> shuffleList = Arrays.asList(allTiles);
        Collections.shuffle(shuffleList);
        shuffleList.toArray(allTiles);
        
        // board: row: 0-6, column 0-6, randomly assign row and column(if available) to all the moveable tiles
        int index = 0;
        for (int row = 0; row <= 6; ++row) {
            for (int col = 0; col <= 6; ++col) {
                if (row % 2 == 0 && col % 2 == 0) {
                    // these are all permanent tiles, no value assigned
                    continue;
                }
                
                // assign row and columns
                allTiles[index].setRow(row);
                allTiles[index].setColumn(col);
                index++;
            }
        }
        
        // make the last Tile the free tile, then change allTiles to an array without the freeTile
        allTiles[33].setRow(-1);
        allTiles[33].setColumn(-1);
        freeTile = allTiles[33];
        Tile[] tempAllTiles = Arrays.copyOf(allTiles, 33);
        
        // reload all tiles array
        allTiles = new Tile[49];
        for(int i = 0; i < tempAllTiles.length; ++i){
            allTiles[i] = tempAllTiles[i];
        }
        
        // read in permanent treasure tiles
        Tile[] permanentTreasure = new Tile[12];
        String[][] allTreasures = Utility.readTreasure();
        
        int addIndex = 0;
        for(int i = 0; i < 24; ++i){
            // if it's a permanent tile, then add to array
            if(Objects.equals(allTreasures[i][3], "false")){
                permanentTreasure[addIndex] = new Tile(
                        Integer.parseInt(allTreasures[i][5]),Integer.parseInt(allTreasures[i][4]), new ImageIcon(),
                        Utility.tileOrientation(Integer.parseInt(allTreasures[i][2]), allTreasures[i][1]), allTreasures[i][0],
                        Integer.parseInt(allTreasures[i][2]), allTreasures[i][1]
                );
                addIndex ++;
            }
        }
        
        // add permanent tiles into all tiles array
        for(int i = 33; i < 45 ; ++i){
            allTiles[i] = permanentTreasure[i-33];
        }
        
        // add permanent non-treasure tiles
        allTiles[45] = new Tile(0, 0, new ImageIcon(), Utility.tileOrientation(1, "L"), "L", 1, "L");
        allTiles[46] = new Tile(6, 0, new ImageIcon(), Utility.tileOrientation(2, "L"), "L", 2,"L");
        allTiles[47] = new Tile(6, 6, new ImageIcon(), Utility.tileOrientation(3, "L"), "L", 3,"L");
        allTiles[48] = new Tile(0, 6, new ImageIcon(), Utility.tileOrientation(0, "L"), "L", 0,"L");
        
    }
    
    
    // insert algorithm, based on the parameter passed into this method, shift all the tiles(and players) in that column by 1 - Zheng Pei
    public boolean shiftColumn(int columnNum, boolean goDown, Player[] allPlayers) {
        
        // make sure the user can't reverse a move
        if (lastInsertCol && goDownOrRight != goDown && rowOrColNum == columnNum) {
            return false;
        }
        
        // update the insert info
        lastInsertCol = true;
        goDownOrRight = goDown;
        rowOrColNum = columnNum;
        
        // insert the tile from the top side - move 1 grid down
        if (goDown) {
            
            // move player on the tile
            for (Player player : allPlayers) {
                if (player.getColumn() == columnNum) player.setRow(player.getRow() + 1);
            }
            
            // move tiles
            for (int i = 0; i < 33; ++i) {
                if (allTiles[i].getColumn() == columnNum) {
                    
                    // if a tile gets pushed out, it will become the free tile
                    if (!allTiles[i].setRow(allTiles[i].getRow() + 1)) {
                        
                        // insert the free tile into the board
                        freeTile.setColumn(columnNum);
                        freeTile.setRow(0);
                        
                        // then change the pushed out tile into the free tile
                        Tile tempTile = allTiles[i];
                        allTiles[i] = freeTile;
                        freeTile = tempTile;
                        
                    }
                }
            }
        } else {
            
            // insert the tile from the bottom side - move 1 grid up
            // move player on the tile
            for (Player player : allPlayers) {
                if (player.getColumn() == columnNum) player.setRow(player.getRow() - 1);
            }
            
            // move tiles
            for (int i = 0; i < 33; ++i) {
                if (allTiles[i].getColumn() == columnNum) {
                    
                    // if a tile gets pushed out, it will become the free tile
                    if (!allTiles[i].setRow(allTiles[i].getRow() - 1)) {
                        
                        // insert the free tile into the board
                        freeTile.setColumn(columnNum);
                        freeTile.setRow(6);
                        
                        // then change the pushed out tile into the free tile
                        Tile tempTile = allTiles[i];
                        allTiles[i] = freeTile;
                        freeTile = tempTile;
                        
                    }
                }
            }
        }
        return true;
    }
    
    // insert algorithm, based on the parameter passed into this method, shift all the tiles(and players) in that row by 1 - Zheng Pei
    public boolean shiftRow(int rowNum, boolean goRight, Player[] allPlayers) {
        
        // make sure the user can't reverse a move
        if (!lastInsertCol && goDownOrRight != goRight && rowOrColNum == rowNum) {
            return false;
        }
        
        // update the insert info
        lastInsertCol = false;
        goDownOrRight = goRight;
        rowOrColNum = rowNum;
        
        // insert the tile from the left side - move 1 grid to the right side
        if (goRight) {
            // move player on the tile
            for (Player player : allPlayers) {
                if (player.getRow() == rowNum) player.setColumn(player.getColumn() + 1);
            }
            
            // move tile
            for (int i = 0; i < 33; ++i) {
                if (allTiles[i].getRow() == rowNum) {
                    
                    // if a tile gets pushed out, it will become the free tile
                    if (!allTiles[i].setColumn(allTiles[i].getColumn() + 1)) {
                        
                        // insert the free tile into the board
                        freeTile.setRow(rowNum);
                        freeTile.setColumn(0);
                        
                        // then change the pushed out tile into the free tile
                        Tile tempTile = allTiles[i];
                        allTiles[i] = freeTile;
                        freeTile = tempTile;
                        
                    }
                }
            }
        } else {
            
            // insert the tile from the right side - move 1 grid to the left side
            // move player on the tile
            for (Player player : allPlayers) {
                if (player.getRow() == rowNum) player.setColumn(player.getColumn() - 1);
            }
            
            // move tile
            for (int i = 0; i < 33; ++i) {
                
                if (allTiles[i].getRow() == rowNum) {
                    
                    // if a tile gets pushed out, it will become the free tile
                    if (!allTiles[i].setColumn(allTiles[i].getColumn() - 1)) {
                        
                        // insert the free tile into the board
                        freeTile.setRow(rowNum);
                        freeTile.setColumn(6);
                        
                        // then change the pushed out tile into the free tile
                        Tile tempTile = allTiles[i];
                        allTiles[i] = freeTile;
                        freeTile = tempTile;
                        
                    }
                }
            }
        }
        return true;
    }
    
    // mutators and accessors - Zheng Pei
    public Tile[] getAllTiles() {
        return allTiles;
    }
    
    public void setAllTiles(Tile[] allTiles) {
        this.allTiles = allTiles;
    }
    
    public Tile getFreeTile() {
        return freeTile;
    }
    
    public void setFreeTile(Tile freeTile) {
        this.freeTile = freeTile;
    }
    
    public boolean isLastInsertCol() {
        return lastInsertCol;
    }
    
    public void setLastInsertCol(boolean lastInsertCol) {
        this.lastInsertCol = lastInsertCol;
    }
    
    public boolean isGoDownOrRight() {
        return goDownOrRight;
    }
    
    public void setGoDownOrRight(boolean goDownOrRight) {
        this.goDownOrRight = goDownOrRight;
    }
    
    public int getRowOrColNum() {
        return rowOrColNum;
    }
    
    public void setRowOrColNum(int rowOrColNum) {
        this.rowOrColNum = rowOrColNum;
    }
    
    // to string method - Zheng Pei
    @Override
    public String toString() {
        return "Board{" +
                "allTiles=" + Arrays.toString(allTiles) +
                "\n, freeTile=" + freeTile +
                '}';
    }
}