package AmazingLabyrinth.Model;

import java.io.Serializable;
import javax.swing.*;

/*
Author - Zheng Pei

Description: a class that represents the card assigned to players in the game

important fields:
1. ImageIcon image - the image of the card
2. String ID - the ID of the card, if the ID of a card is the same as the ID of a tile, that means the tile has
    the treasure that matches this Card. the ID of a card should be set to null once it's been collected.

Available Methods:
1. public Card(ImageIcon, String) - constructor(Zheng Pei)
2. mutators and accessors
3. toString method

Private Methods
N/A

 */

public class Card implements Serializable{
    private static final long serialVersionUID = 1234567L;
    
    // fields, the ID of a card should be set to null once it's been collected
    private ImageIcon image;
    private String ID;
    
    // constructor that initialize the card - Zheng Pei
    public Card(ImageIcon image, String ID) {
        this.image = image;
        this.ID = ID;
    }
    
    // mutators and accessors
    public ImageIcon getImage() {
        return image;
    }
    
    public void setImage(ImageIcon image) {
        this.image = image;
    }
    
    public String getID() {
        return ID;
    }
    
    public void setID(String ID) {
        this.ID = ID;
    }
    
    // to string method
    @Override
    public String toString() {
        return "Card{" +
                "Card ID='" + ID + '\'' +
                '}';
    }
}