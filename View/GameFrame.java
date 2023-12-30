
package AmazingLabyrinth.View;

import AmazingLabyrinth.Model.Board;
import AmazingLabyrinth.Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/*
Author: Zheng Pei 50%, Hanyu Zhou 50%

Description: the GUI part of the free tile, it contains an image of free tile and two rotation buttons

important fields:
1. BoardPanel boardPanel - board panel
2. FreeTilePanel freeTilePanel - free tile panel
3. PlayerPanel playerPanel - player panel
4. ScoringPanel scoringPanel - scoring panel
5. GameStatePanel gameStatePanel - game state panel
6. MessagePanel messagePanel - message panel
7. JButton gameRuleButton; - game rule button
8. JButton newGameButton - new game button
9. JButton saveButton - save button

Available Methods:
1. public GameFrame(Board, Player[]) - constructor (Zheng Pei)
2. public void changeGameState(boolean) - animation that shows when the game starts and ends (Hanyu Zhou)
3. mutators and accessors

Private Methods
1. private void setUpComponents() - method being called to set up all the components (Zheng Pei 50%, Hanyu Zhou 50%)
 */

public class GameFrame extends JFrame implements Serializable{
    private static final long serialVersionUID = 1234567L;
    
    // all the panels - Zheng Pei
    private BoardPanel boardPanel;
    private FreeTilePanel freeTilePanel;
    private PlayerPanel playerPanel;
    private ScoringPanel scoringPanel = new ScoringPanel();
    private GameStatePanel gameStatePanel = new GameStatePanel();
    private MessagePanel messagePanel = new MessagePanel();
    private JLabel titleImage;
    
    // buttons on top right of the game frame
    JButton gameRuleButton = new JButton("Game Rule");
    JButton newGameButton = new JButton("New Game");
    JButton saveButton = new JButton("Save");
    
    // constructor that properly initialize and place all the GUI object on the game screen - Zheng Pei
    public GameFrame(Board boardModel, Player[] allPlayers) {
        // load background image
        try {
            setContentPane(new JLabel(new ImageIcon(
                    ImageIO.read(new File("Images/GUIImages/frameBackground.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // initialize board panel
        boardPanel = new BoardPanel(boardModel, allPlayers.length);
        
        // initialize freeTilePanel
        freeTilePanel = new FreeTilePanel(boardModel);
        
        // initialize the playerPanel
        playerPanel = new PlayerPanel(allPlayers);
        
        setLayout(null);
        setUpComponents();
        setPreferredSize(new Dimension(1920, 1080));
        setState(JFrame.MAXIMIZED_BOTH);
        pack();
        
        // close frame setting
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // make the frame visible
        setVisible(true);
    }
    
    // method being called to set up all the components - Zheng Pei 50%, Hanyu Zhou 50%
    private void setUpComponents() {
        
        // add game started label
        gameStatePanel.setBounds(710, 390, 500, 300);
        changeGameState(true);
        add(gameStatePanel);
        
        // add title image - resized version
        ImageIcon icon = new ImageIcon("Images/GUIImages/titleImage.png");
        Image newImg = icon.getImage().getScaledInstance(432, 75, java.awt.Image.SCALE_SMOOTH);
        titleImage = new JLabel(new ImageIcon(newImg));
        titleImage.setBounds(0, 50, 432, 75);
        add(titleImage);
        
        messagePanel.setBounds(430,20,500,130);
        add(messagePanel);
        
        // add border panel
        boardPanel.setBounds(24, 168, 864, 864);
        boardPanel.setOpaque(false);
        add(boardPanel);
        
        // add free tile panel
        freeTilePanel.setBounds(912, 776, 304, 304);
        freeTilePanel.setOpaque(false);
        freeTilePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.green));
        add(freeTilePanel);
        
        // add player panel
        playerPanel.setBounds(912, 168, 1008, 608);
        playerPanel.setOpaque(false);
        playerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.green));
        add(playerPanel);
        
        // add scoring panel
        scoringPanel.setBounds(1216, 776, 704, 304);
        scoringPanel.setOpaque(false);
        scoringPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.green));
        add(scoringPanel);
        
        // add game rule button
        gameRuleButton.setFont(new Font("TimesRoman BOLD", Font.BOLD, 30));
        gameRuleButton.setContentAreaFilled(false);
        gameRuleButton.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new ButtonBorder()));
        gameRuleButton.setFocusPainted(false);
        gameRuleButton.setForeground(Color.white);
        gameRuleButton.setBounds(976, 44, 250, 80);
        add(gameRuleButton);
        
        // add new game button
        newGameButton.setFont(new Font("TimesRoman BOLD", Font.BOLD, 30));
        newGameButton.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new ButtonBorder()));
        newGameButton.setFocusPainted(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setForeground(Color.white);
        newGameButton.setBounds(1290, 44, 250, 80);
        add(newGameButton);
        
        // add save button
        saveButton.setBounds(1604, 44, 250, 80);
        saveButton.setFont(new Font("TimesRoman BOLD", Font.BOLD, 30));
        saveButton.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new ButtonBorder()));
        saveButton.setFocusPainted(false);
        saveButton.setForeground(Color.white);
        saveButton.setContentAreaFilled(false);
        add(saveButton);
        
    }
    
    // game state panel that shows when the game starts and ends - Hanyu
    public void changeGameState(boolean gameStart){
        Timer timer;
        int delay = 3000; // 3 seconds delay
        
        gameStatePanel.setVisible(true);
        // change the label of the panel based on the current game state
        if(gameStart)
            gameStatePanel.update(new ImageIcon("Images/GUIImages/GameStarted.png"));
        else
            gameStatePanel.update(new ImageIcon("Images/GUIImages/GameEnded.png"));
        
        // remove the label
        ActionListener removeLabel = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                gameStatePanel.setVisible(false);
            }
        };
        
        // start the timer - remove the label after 3 sec
        timer = new Timer(delay, removeLabel);
        timer.setRepeats(false);
        timer.start();
    }
    
    
    
    // mutators and accessors - Zheng Pei
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }
    
    public void setBoardPanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }
    
    public FreeTilePanel getFreeTilePanel() {
        return freeTilePanel;
    }
    
    public void setFreeTilePanel(FreeTilePanel freeTilePanel) {
        this.freeTilePanel = freeTilePanel;
    }
    
    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }
    
    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }
    
    public JButton getGameRuleButton() {
        return gameRuleButton;
    }
    
    public void setGameRuleButton(JButton gameRuleButton) {
        this.gameRuleButton = gameRuleButton;
    }
    
    public JButton getNewGameButton() {
        return newGameButton;
    }
    
    public void setNewGameButton(JButton newGameButton) {
        this.newGameButton = newGameButton;
    }
    
    public JButton getSaveButton() {
        return saveButton;
    }
    
    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }
    
    public GameStatePanel getGameStatePanel() {
        return gameStatePanel;
    }
    
    public void setGameStatePanel(GameStatePanel gameStatePanel) {
        this.gameStatePanel = gameStatePanel;
    }
    
    public ScoringPanel getScoringPanel() {
        return scoringPanel;
    }
    
    public void setScoringPanel(ScoringPanel scoringPanel) {
        this.scoringPanel = scoringPanel;
    }
    
    public MessagePanel getMessagePanel() {
        return messagePanel;
    }
    
    public void setMessagePanel(MessagePanel messagePanel) {
        this.messagePanel = messagePanel;
    }
}