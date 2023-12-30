
package AmazingLabyrinth.View;

import AmazingLabyrinth.Model.Card;
import AmazingLabyrinth.Model.Player;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/*
Author: Hanyu Zhou 60%, Zheng Pei 40%

Description: the GUI aspect of the player panel, it shows the cards that a player has, the cards a player completed,
    and the who's turn it is

important fields:
1. JLabel[][] cards - label for cards and placeholders
2. JLabel yourLabel
3. JLabel turnLabel

Available Methods:
1. public PlayerPanel(Player[]) - constructor (Zheng Pei)
2. public void updateCards(Player[] ) - update cards GUI, it should be called whenever the user collected a treasure
    (Zheng Pei)
3. public void addTurnLabel(int) - add turn label (Hanyu Zhou)
4. public void updateTurnLabel(int) - update the turn label, it should be called whenever the turn changes(Hanyu Zhou)

Private Methods:
1. void addPlayers() - method to add player numbers and icons (Hanyu Zhou)
2. void addPlaceHolders() - method to add placeholders (Zheng Pei 60%, Hanyu Zhou 40%)
3. void addCards(Player[]) - method to add cards (Zheng Pei)

 */

public class PlayerPanel extends JPanel implements Serializable{
    private static final long serialVersionUID = 1234567L;
    
    // labels for player numbers
    JLabel[] playerNumLabels = new JLabel[4];
    
    // labels for playing pieces
    JLabel[] playerIcon = new JLabel[4];
    
    // label for cards and placeholders
    JLabel[][] cards = new JLabel[4][6];
    
    // your turn label - Hanyu Zhou
    JLabel yourLabel = new JLabel("Your");
    JLabel turnLabel = new JLabel("Turn!");
    
    // constructor method - Hanyu Zhou
    public PlayerPanel(Player[] allPlayers) {
        
        addPlayers();
        
        addPlaceHolders();
        
        addCards(allPlayers);
        
        setLayout(null);
        
    }
    
    // method to add player numbers and icons - Hanyu Zhou
    private void addPlayers() {
        
        // y-coordinate for the playerNumLabels
        int playerLabelY = 10;
        int playerIconY = 77;
        
        // add player numbers and icons
        for (int p = 0; p < 4/* or less than how many players there are */; p++) {
            
            playerNumLabels[p] = new JLabel("Player " + (p + 1), SwingConstants.CENTER);
            playerNumLabels[p].setFont(new Font("TimesRoman BOLD", Font.BOLD | Font.ITALIC, 30));
            playerNumLabels[p].setForeground(Color.white);
            playerNumLabels[p].setBounds(10, playerLabelY, 150, 50);
            playerLabelY += 152;
            add(playerNumLabels[p]);
            
            playerIcon[p] = new JLabel();
            playerIcon[p].setIcon(new ImageIcon("Images/PlayerIcons/Player" +  p  + ".png"));
            playerIcon[p].setBounds(53, playerIconY, 50, 55);
            playerIconY += 152;
            add(playerIcon[p]);
            
        }
        
    }
    
    // method to add placeholders - Zheng Pei 60%, Hanyu Zhou 40%
    private void addPlaceHolders() {
        
        int cardLabelX = 152;
        int cardLabelY = 10;
        
        // fill player panel with pace holders first
        for (int p = 0; p < 4; p++) {
            
            // add placeholders
            for (int c = 0; c < 6; c++) {
                
                cards[p][c] = new JLabel();
                cards[p][c].setIcon(new ImageIcon("Images/Cards/Placeholder.PNG"));
                cards[p][c].setBounds(cardLabelX, cardLabelY, 96, 132);
                add(cards[p][c]);
                
                // change coordinates for each card
                cardLabelX += 124;
                
            }
            
            // reset cardLabel X
            cardLabelX = 152;
            
            // change cardLabel Y
            cardLabelY += 152;
        }
        
    }
    
    // method to add cards - Zheng Pei
    private void addCards(Player[] allPlayers){
        
        for(int i = 0; i < allPlayers.length; ++i){
            Card[] allCards = allPlayers[i].getPossessedCards();
            for(int k = 0; k < allCards.length; ++k){
                cards[i][k].setIcon(allCards[k].getImage());
            }
        }
        
        repaint();
        
    }
    
    // update cards - Zheng Pei
    public void updateCards(Player[] allPlayers){
        for(int i = 0; i < allPlayers.length; ++i){
            for (int k = 0; k < allPlayers[i].getPossessedCards().length; k ++){
                cards[i][k].setIcon(allPlayers[i].getPossessedCards()[k].getImage());
            }
        }
        repaint();
    }
    
    // add the your turn label - Hanyu Zhou
    public void addTurnLabel(int turn) {
        
        int yourLabelY = 0;
        int turnLabelY = 0;
        
        // set y-coordinate based on the player number
        switch (turn) {
            case 0:
                yourLabelY = 10;
                turnLabelY = 60;
                break;
            
            case 1:
                yourLabelY = 162;
                turnLabelY = 212;
                break;
            
            case 2:
                yourLabelY = 314;
                turnLabelY = 364;
                break;
            
            case 3:
                yourLabelY = 466;
                turnLabelY = 516;
                break;
            
        }
        
        //set labels
        yourLabel.setFont(new Font("TimesRoman BOLD", Font.BOLD | Font.ITALIC, 35));
        yourLabel.setForeground(Color.white);
        yourLabel.setBounds(880, yourLabelY, 96, 50);
        
        turnLabel.setFont(new Font("TimesRoman BOLD", Font.BOLD | Font.ITALIC, 35));
        turnLabel.setForeground(Color.white);
        turnLabel.setBounds(880, turnLabelY, 96, 50);
        
        // add labels
        add(yourLabel);
        add(turnLabel);
        
    }
    
    // add the update turn label - Hanyu Zhou
    public void updateTurnLabel(int turn) {
        
        int yourLabelY = 0;
        int turnLabelY = 0;
        
        // reset y-coordinate based on the player number
        switch (turn) {
            case 0:
                yourLabelY = 10;
                turnLabelY = 60;
                break;
            
            case 1:
                yourLabelY = 162;
                turnLabelY = 212;
                break;
            
            case 2:
                yourLabelY = 314;
                turnLabelY = 364;
                break;
            
            case 3:
                yourLabelY = 466;
                turnLabelY = 516;
                break;
            
        }
        
        // reset bounds
        yourLabel.setBounds(880, yourLabelY, 96, 50);
        turnLabel.setBounds(880, turnLabelY, 96, 50);
        
        // repaint the labels
        repaint();
        
    }
    
    public JLabel[][] getCards() {
        return cards;
    }
    
    public void setCards(JLabel[][] cards) {
        this.cards = cards;
    }
    
    public JLabel getYourLabel() {
        return yourLabel;
    }
    
    public void setYourLabel(JLabel yourLabel) {
        this.yourLabel = yourLabel;
    }
    
    public JLabel getTurnLabel() {
        return turnLabel;
    }
    
    public void setTurnLabel(JLabel turnLabel) {
        this.turnLabel = turnLabel;
    }
    
}