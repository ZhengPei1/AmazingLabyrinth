/*
Author: Fatimah Rana 40%, Zheng Pei 60%

Description: utility class that holds a lot of static methods which are commonly used throughout the program

important fields:
N/A

Available Methods:
1. static String[][] readTreasure() - returns a two-dimensional array that holds all the info extracted from Treasures.csv
    (Fatimah Rana)
2. static Card[] readCards() - returns a series of Cards objects that hold all the available tiles in the game (Fatimah Rana)
3. static boolean[] tileOrientation(int , String) - with the correct info given, it will return an array of boolean
    that represents the connectivity of the tile, refers to Player.java (Zheng Pei)
4. public static Tile rotateTileLeft/rotateTileRight (Tile) - rotate the given tile 90 degrees to the left side or
    the right side

Private Methods
N/A

 */
package AmazingLabyrinth.Model;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public final class Utility {
    
    // method used to read in the state of Treasure files from the csv- Fatimah Rana
    public static String[][] readTreasure() {
        String line = "";
        String splitBy = ",";
        
        String[][] treasure = new String[24][6];    // use comma as separator - Fatimah
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("Text/Treasures.csv"));
            
            // count the row where we need to insert the tile
            int rowCount = 0;
            while ((line = br.readLine()) != null)   //returns a Boolean value - Fatimah
            {
                treasure[rowCount] = line.split(splitBy);
                rowCount++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return treasure;
    }
    
    // method used to read in the card csv file - Fatimah Rana 100%
    public static Card[] readCards() {
        String line = "";
        String splitBy = ",";
        
        String[][] info = new String[24][4];    // use comma as separator
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("Text/Cards.csv"));
            
            // count the row where we need to insert the tile
            int rowCount = 0;
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                info[rowCount] = line.split(splitBy);
                rowCount++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // convert the info in csv file into cards - Fatimah Rana
        Card[] cards = new Card[24];
        for (int i = 0; i < info.length; ++i) {
            String id = info[i][0];
            cards[i] = new Card(new ImageIcon("Images/Cards/Card" + id + ".png"), id);
        }
        return cards;
    }
    
    // method that is used to find the orientation of the tile, it returns a boolean array with length 4, the four values
    // indicates if it's connected to the top, bot, left, right side of the tile - Zheng Pei
    public static boolean[] tileOrientation(int tileNum, String shape) {
        
        // four variables use to indicate the connectivity of the tile
        boolean top = true;
        boolean bot = true;
        boolean left = true;
        boolean right = true;
        
        // If it's a T shaped tile
        if (Objects.equals(shape, "T")) {
            // based on its tile number, decide connectivity
            switch (tileNum) {
                case 0: {
                    top = false;
                    break;
                }
                case 1: {
                    right = false;
                    break;
                }
                case 2: {
                    bot = false;
                    break;
                }
                case 3: {
                    left = false;
                    break;
                }
            }
        }
        // if it's a I shaped tile
        else if (Objects.equals(shape, "I")) {
            // based on its tile number, decide connectivity
            switch (tileNum) {
                case 0:
                case 2: {
                    left = false;
                    right = false;
                    break;
                }
                case 1:
                case 3: {
                    top = false;
                    bot = false;
                    break;
                }
            }
            
        }
        // if it's a L shaped tile
        else if(Objects.equals(shape, "L")) {
            // based on its tile number, decide connectivity
            switch (tileNum) {
                case 0: {
                    left = false;
                    bot = false;
                    break;
                }
                case 1: {
                    top = false;
                    left = false;
                    break;
                }
                case 2: {
                    top = false;
                    right = false;
                    break;
                }
                case 3: {
                    right = false;
                    bot = false;
                    break;
                }
            }
        }
        
        // return the orientation
        return new boolean[]{top, bot, left, right};
    }
    
    // method that can be called whenever a tile needs to be rotated left 90 degrees - Zheng Pei
    public static Tile rotateTileLeft (Tile tile){
        // find the new tile number and update the number
        int newTileNum = tile.getTileNum() + 1;
        if (newTileNum == 4) newTileNum = 0;
        tile.setTileNum(newTileNum);
        
        // change image and orientation
        tile.setImage(new ImageIcon("Images/Tiles/" + tile.getTileID() + newTileNum + ".png"));
        
        tile.setOrientation(Utility.tileOrientation(newTileNum, tile.getTileShape()));
        return tile;
    }
    
    // method that can be called whenever a tile needs to be rotated right 90 degrees - Zheng Pei
    public static Tile rotateTileRight (Tile tile){
        // find the new tile number and update the number
        int newTileNum = tile.getTileNum() - 1;
        if (newTileNum == -1) newTileNum = 3;
        tile.setTileNum(newTileNum);
        
        // change image and orientation
        tile.setImage(new ImageIcon("Images/Tiles/" + tile.getTileID() + newTileNum + ".png"));
        
        tile.setOrientation(Utility.tileOrientation(newTileNum, tile.getTileShape()));
        return tile;
    }
    
}