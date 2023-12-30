package AmazingLabyrinth.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/*
Author: Zheng Pei

General strategy of the AI:
    1. When there are two players:
        1.1 Defense would be the highest priority of the AI since the only way the AI can lose is when the other player
        collected all cards
    2. When there are more than two players:
        2.1 The AI would only focus on the Player who moves after AI moves, since AI can not control how the board
        looks like after two turns, so it's pointless to mess with other players
        2.2 Attack would be the highest priority of the AI, and AI would only start defend in the following two scenarios
            2.2.1 AI would defend when the next Player only has one cards left, this would always be assigned with the
                highest priority
            2.2.2 AI would defend when AI can not make a "valuable" move in this turn, and the next Player is about to
                collect his treasure
            2.2.3 If AI can collect a treasure in this turn, then except 2.2.1, this would be the highest priority task
                for AI

Calculate the value of each tile with the follow rules
    1 a tile's absolute distance to all the treasures (when the distance is 0, i.e. the treasure tile AI wants to
        go to, the value of the tile would be infinity)
    2 a permanent tile is more valuable than a movable tile because of its stability
    3 treasure located on the permanent tile is less valuable than treasure on a movable tile(they are harder to
        collect)
    4 a tile that can connect with three sides is more valuable than a tile that connects two sides (T shape is more
        valuable than L shape, and I shape)

How AI insert tile and move (assume the next player can also make the best move)
    1. simulate every possible board arrangement after different insertions and rotations, find the most valuable tile
    AI can move to, record the value as value1
    2. then simulate every possible insertion the player can make after AI's turn, find the most valuable tile the
    player can move to, record the value as value2
    3. use the insertion that creates the maximum value1-value2, then perform the most valuable move
    
 */
public class AI extends Player implements Serializable{
    private static final long serialVersionUID = 1234567L;
    
    // a set of constant that defines the priority of the AI, the higher a value is, the more AI cares about it
    private final int PERMANENT_TILE_VALUE = 1;
    private final int THREE_SIDED_SHAPE_VALUE = 2;
    private final int TREASURER_PERMANENT_TILE_VALUE = 900;
    private final int TREASURER_MOVEABLE_TILE_VALUE = 999;
    private final int ENEMY_TREASURE_VALUE;
    private final int ENEMY_LAST_TREASURE_VALUE = 9999;
    private final int AI_LAST_TREASURE_VALUE = 99999;
    
    // Constructor - initialize variables and defines priority
    public AI(Card[] possessedCards, int column, int row, int playerNum) {
        super(possessedCards, column, row);
        
        // we give different priority to the scenario where enemy collect a treasure card based on player number
        // refer to - general strategy of AI
        if(playerNum == 2){
            ENEMY_TREASURE_VALUE = 9999;
        }else{
            ENEMY_TREASURE_VALUE = 8;
        }
    }
    
    // Copy Constructor
    public AI(AI oldAI) {
        super(oldAI.getPossessedCards(), oldAI.getColumn(), oldAI.getRow());
        ENEMY_TREASURE_VALUE = oldAI.ENEMY_TREASURE_VALUE;
    }
    
    /*
    find and return the move AI should do, return an array with values that represents
    [0] - the number of the column/row the AI should insert a new tile
    [1] - if AI should shift a column (return 0 = false, 1 = true)
    [2] - if AI should use the goDown/goRight button for the shift (return 0 = false, 1 = true)
    [3] - the row that AI should move to after the insertion
    [4] - the column that AI should move to after the insertion
    [5] - how many times the AI have used rotate right button
     */
    public int[] findMove(final AI AIPlayer, final Player human, final Board gameBoard) {
        
        // find the AI move that will give AI the biggest advantage(by calculating score difference)
        // assuming that the human player is smart enough to make the most valuable move
        int maximumScoreDiff = -99999999;
        int shiftNum = 0;
        int shouldShiftColumn = 0;
        int shouldGoDownOrRight = 0;
        int toRow = 0;
        int toCol = 0;
        int rotateTime = 0;
        
        // the outer for loop is to simulate rotate
        for(int rotate = 0; rotate <= 3; ++rotate){
            if(rotate != 0){
                gameBoard.setFreeTile(Utility.rotateTileRight(gameBoard.getFreeTile()));
            }
            
            // find the best move, the three inner for loops are used to switch between different insertion buttons
            for(int colSwitch = 0; colSwitch <= 1; ++colSwitch){
                for(int insertButtonSwitch = 0; insertButtonSwitch <= 1; ++insertButtonSwitch) {
                    for (int shift = 1; shift <= 5; shift += 2) {
                        
                        // conversion between int and boolean
                        boolean moveThisColumn = colSwitch != 0;
                        boolean goDownOrRight = insertButtonSwitch != 0;
                
                        // simulate a possible AI move
                        Object[] AIPossibleMove = simulateAITurn(AIPlayer, human, gameBoard, shift, moveThisColumn, goDownOrRight);
                        
                        // skip when AI can not insert a tile using the current button
                        if(AIPossibleMove == null) continue;
                        Board newGameBoard = (Board) AIPossibleMove[0];
                        Player newPlayer = (Player) AIPossibleMove[1];
                
                        // for each AI move, simulate all possible human move, then find the maximum score human can gain
                        int humanMaxScore = -99999999;
                        
                        // simulate all the rotations -and shifts a human player can do
                        for(int humanRotate = 0; humanRotate < 4; ++humanRotate){
                            newGameBoard.setFreeTile(Utility.rotateTileRight(newGameBoard.getFreeTile()));
                            for (int humanShift = 1; humanShift <= 5; humanShift += 2) {
                                humanMaxScore = Math.max(humanMaxScore, simulateHumanTurn(newPlayer, newGameBoard, humanShift, true, true));
                                humanMaxScore = Math.max(humanMaxScore, simulateHumanTurn(newPlayer, newGameBoard, humanShift, true, false));
                                humanMaxScore = Math.max(humanMaxScore, simulateHumanTurn(newPlayer, newGameBoard, humanShift, false, true));
                                humanMaxScore = Math.max(humanMaxScore, simulateHumanTurn(newPlayer, newGameBoard, humanShift, false, true));
                            }
                        }
                
                        // use the maximum score AI can gain to subtract the maximum score human can gain, find the maximumScoreDiff
                        int scoreDiff = (int) AIPossibleMove[2] - humanMaxScore;
                        if (scoreDiff > maximumScoreDiff){
                            maximumScoreDiff = scoreDiff;
                    
                            // record the states
                            shiftNum = shift;
                            shouldShiftColumn = colSwitch;
                            shouldGoDownOrRight = insertButtonSwitch;
                            toRow = (int)AIPossibleMove[3];
                            toCol = (int)AIPossibleMove[4];
                            rotateTime = rotate;
                        }
                    }
                }
            }
        }
        
        // return all the info needed for AI to make move
        return new int[]{shiftNum, shouldShiftColumn, shouldGoDownOrRight, toRow, toCol, rotateTime};
    }
    
    
    /*
    simulate insertion the player(AI or human) can make, then calculate the maximum value
    AI can gain after this insertion, return an array with values that represents
    [0] - the gameBoard after the insertion (Board)
    [1] - the new human Player object (Player)
    [2] - the maximum value AI can gain in this turn (int)
    [3] - the row AI should move to (int)
    [4] - the column AI should move to (int)
     */
    private Object[] simulateAITurn(final AI AIPlayer, final Player human, final Board gameBoard, int rowOrColNum, boolean moveThisCol,
                                   boolean goDownOrRight) {
        // make object copies
        Board afterInsertion = new Board(gameBoard);
        AI copyAI = new AI(AIPlayer);
        Player copyHuman = new Player(human);
        
        // perform insertion, if the insertion can't be performed, skip by returning null
        if(moveThisCol){
            if(!afterInsertion.shiftColumn(rowOrColNum, goDownOrRight, new Player[]{copyAI, copyHuman})) {
                return null;
            }
        }else{
            if(!afterInsertion.shiftRow(rowOrColNum, goDownOrRight, new Player[]{copyAI, copyHuman})){
                return null;
            }
        }
        
        // get the info about the best move AI can make
        int[] moveInfo = calculateMaxValue(copyAI, afterInsertion, false);
        
        // return an array of values for further processing
        return new Object[]{afterInsertion, copyHuman, moveInfo[0], moveInfo[1], moveInfo[2]};
    }
    
    /*
    simulate the insertion the human player can make, then calculate the maximum value
    the player can get in this turn
     */
    public int simulateHumanTurn(final Player human, final Board gameBoard, int rowOrColNum, boolean moveThisCol,
                                 boolean goDownOrRight) {
        // make object copies
        Board afterInsertion = new Board(gameBoard);
        Player copyHuman = new Player(human);
        
        // perform insertion, if the insertion can't be performed, then we assume the player decided to skip turn
        if(moveThisCol){
            afterInsertion.shiftColumn(rowOrColNum, goDownOrRight, new Player[]{copyHuman});
        }else{
            afterInsertion.shiftRow(rowOrColNum, goDownOrRight, new Player[]{copyHuman});
        }
    
        // get the info about the best move Human can make
        int[] moveInfo = calculateMaxValue(copyHuman, afterInsertion, true);
        
        // return the maximum value human player can gain within his turn, we don't care about other info
        return moveInfo[0];
    }
    
    /*
    calculate the most valuable tile the Player can move to, return an array with values that represents
    [0] - the maximum value that can be gained from this move
    [1] - the row that the player should move to
    [2] - the column that the player should move to
     */
    private int[] calculateMaxValue(Player perspective, Board gameBoard, boolean isEnemy) {
        
        // create and initialize a 2D array that holds the value of all the tiles
        int[][] tileValue = new int[7][7];
        for (int[] tiles : tileValue)
            for (int tile : tiles) tile = 0;
        
        // find all the Tiles that contains the treasure player/AI needs to find
        ArrayList<Tile> neededTiles = findNeededTreasure(perspective.getPossessedCards(), gameBoard);
        
        // first assign a value to every tile
        for (int row = 0; row < 7; ++row) {
            for (int col = 0; col < 7; ++col) {
                
                // if it's a permanent tile, then give the tile more value because of it's stability
                if (isPermanentTile(row, col))
                    tileValue[row][col] += PERMANENT_TILE_VALUE;
                
                // if it's a T shaped tile, then give the tile more value because of it's connectivity
                if(isTShaped(gameBoard, row, col))
                    tileValue[row][col] += THREE_SIDED_SHAPE_VALUE;
                
                // if it's a needed tile - tile with objective
                if(isNeededTile(neededTiles, row, col)){
                    
                    // assign different values based on the state of the tile and player - refer to Calculation of Value
                    if(isPermanentTile(row, col)){
                        if(isEnemy){
                            if(isLastCard(perspective.getPossessedCards())) tileValue[row][col] += ENEMY_LAST_TREASURE_VALUE;
                            else tileValue[row][col] += ENEMY_TREASURE_VALUE;
                        }else{
                            if(isLastCard(perspective.getPossessedCards())) tileValue[row][col] += AI_LAST_TREASURE_VALUE;
                            else tileValue[row][col] += TREASURER_PERMANENT_TILE_VALUE;
                        }
                    }
                    else{
                        if(isEnemy){
                            if(isLastCard(perspective.getPossessedCards())) tileValue[row][col] += ENEMY_LAST_TREASURE_VALUE;
                            else tileValue[row][col] += ENEMY_TREASURE_VALUE;
                        }else{
                            if(isLastCard(perspective.getPossessedCards())) tileValue[row][col] += AI_LAST_TREASURE_VALUE;
                            else tileValue[row][col] += TREASURER_MOVEABLE_TILE_VALUE;
                        }
                    }
                    
                }else{
                    // if it's not a tile with objective, find the total distance between this tile and all the objectives
                    // the shorter this distance is, the better the move is
                    
                    int totalDistance = 0;
                    for(Tile tile: neededTiles){
                        totalDistance += findDistance(row, col, tile.getRow(), tile.getColumn());
                    }
                    
                    // subtract this distance from the tile value
                    tileValue[row][col] -= totalDistance;
                }
            }
        }
        
        // generate all the possible moves
        ArrayList<Tile> possibleMoves = perspective.bfsAllPossibleMove(perspective.getRow(), perspective.getColumn(), gameBoard.getAllTiles());
        boolean[][] moveAble = new boolean[7][7];
        
        // map the possible moves with a 2D array, so it's easier for us to search
        for(Tile tile: possibleMoves){
            moveAble[tile.getRow()][tile.getColumn()] = true;
        }
        
        // then find the tile with the maximum value that the Player/AI can move to
        int maximumValue = -999999999;
        int toRow = -1;
        int toCol = -1;
        for (int row = 0; row < 7; ++row) {
            for (int col = 0; col < 7; ++col) {
                if (moveAble[row][col]) {
                    if (tileValue[row][col] > maximumValue) {
                        toRow = row;
                        toCol = col;
                        maximumValue = tileValue[row][col];
                    }
                }
            }
        }
        
        // return an array of value
        return new int[]{maximumValue, toRow, toCol};
    }
    
    // method that checks if a tiles is a T tile
    private boolean isTShaped(Board gameBoard, int row, int col){
        Tile[] allTiles = gameBoard.getAllTiles();
        for(Tile tile : allTiles){
            if(tile.getRow() == row && tile.getColumn() == col){
                return Objects.equals(tile.getTileShape(), "T");
            }
        }
        return false;
    }
    
    
    // method that checks if this player only has one card left to collect
    private boolean isLastCard(Card[] possessedCards){
        int count = 0;
        for(Card card : possessedCards){
            if(card.getID() != null) count += 1;
        }
        return count == 1;
    }
    
    // method that checks if a needed Tile is at a specific location
    private boolean isNeededTile(ArrayList<Tile> neededTile, int row, int col){
        for(Tile tile : neededTile){
            if(row == tile.getRow() && col == tile.getColumn()) return true;
        }
        return false;
    }
    
    // method that returns an arrayList of Tiles that contains the treasure AI needs to find
    private ArrayList<Tile> findNeededTreasure(Card[] neededCards, Board gameBoard){
        Tile[] allTiles = gameBoard.getAllTiles();
        
        ArrayList<Tile> neededTiles = new ArrayList<>();
        for(Card card : neededCards){
            for(Tile tile : allTiles){
                if(Objects.equals(tile.getTileID(), card.getID())){
                    neededTiles.add(tile);
                    break;
                }
            }
        }
        
        return neededTiles;
    }
    
    // method that returns an integer representing the absolute distance between two tiles
    private int findDistance(int x1, int y1, int x2, int y2){
        return (int)Math.round(Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)));
    }
    
    // method that returns true if a tile is a permanent tile, false otherwise
    private boolean isPermanentTile(int row, int col) {
        // if both row and col are even number, then it's a permanent tile
        if (row % 2 == 0 && col % 2 == 0) return true;
        return false;
    }
}
