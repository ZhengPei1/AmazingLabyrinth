
package AmazingLabyrinth.View;

import AmazingLabyrinth.Model.Board;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/*
Author: Zheng Pei 60%, Hanyu Zhou 40%

Description: the GUI part of the free tile, it contains an image of free tile and two rotation buttons

important fields:
1. JLabel freeTileLabel - the label that holds the image of the free tile
2. JButton rotateLeftButton/rotateRightButton - buttons that allows the user to rotate the freeTile

Available Methods:
1. public FreeTilePanel(Board) - constructor (Zheng Pei 50%, Hanyu Zhou 50%)
2. public void updateFreeTile(Board) - this function should be called whenever there's a new freeTile, it will update
    the GUI object in the freeTilePanel

Private Methods
N/A
 */

public class FreeTilePanel extends JPanel implements Serializable{
    private static final long serialVersionUID = 1234567L;
    
    // labels and buttons needed - Zheng Pei
    private JLabel title = new JLabel("Available Tile");
    private JLabel freeTileLabel = new JLabel();
    private JButton rotateLeftButton = new JButton();
    private JButton rotateRightButton = new JButton();
    ImageIcon curvedArrowLeftIcon = new ImageIcon("src/AmazingLabyrinth/Images/GUIImages/curvedArrowLeft.png");
    ImageIcon curvedArrowRightIcon = new ImageIcon("src/AmazingLabyrinth/Images/GUIImages/curvedArrowRight.png");
    
    // constructor - initialize all the fields as needed - Zheng Pei 50%, Hanyu Zhou 50%
    public FreeTilePanel(Board boardModel) {
        
        // using null layout
        setLayout(null);
        
        // set up the title label
        title.setFont(new Font("TimesRoman BOLD", Font.BOLD | Font.ITALIC, 40));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.white);
        title.setBounds(0, 20, 304, title.getPreferredSize().height);
        add(title);
        
        // add image for the right arrow
        Image leftArrowImage = curvedArrowLeftIcon.getImage().getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
        rotateLeftButton.setIcon(new ImageIcon(leftArrowImage));
        rotateLeftButton.setBounds(35, 165, 70, 70);
        rotateLeftButton.setContentAreaFilled(false);
        rotateLeftButton.setBorder(null);
        add(rotateLeftButton);
        
        // add image for the right arrow
        Image rightArrowImage = curvedArrowRightIcon.getImage().getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
        rotateRightButton.setIcon(new ImageIcon(rightArrowImage));
        rotateRightButton.setBounds(200, 165, 70, 70);
        rotateRightButton.setContentAreaFilled(false);
        rotateRightButton.setBorder(null);
        add(rotateRightButton);
        
        // set up the free tile label
        ImageIcon freeTile = boardModel.getFreeTile().getImage();
        Image freeTileImg = freeTile.getImage().getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(freeTileImg);
        freeTileLabel.setIcon(image);
        freeTileLabel.setBounds(92, 95, 120, 120);
        add(freeTileLabel);
        
    }
    
    // this function should be called whenever there's a new freeTile, it will update the GUI object in the
    // freeTilePanel - Zheng Pei
    public void updateFreeTile(Board boardModel) {
        
        // rescale image then repaint
        Image scaledImage = boardModel.getFreeTile().getImage().getImage().getScaledInstance(120, 120,
                java.awt.Image.SCALE_SMOOTH);
        freeTileLabel.setIcon(new ImageIcon(scaledImage));
        repaint();
    }
    
    // mutators and accessors - Zheng Pei
    public JButton getRotateLeftButton() {
        return rotateLeftButton;
    }
    
    public void setRotateLeftButton(JButton rotateLeftButton) {
        this.rotateLeftButton = rotateLeftButton;
    }
    
    public JButton getRotateRightButton() {
        return rotateRightButton;
    }
    
    public void setRotateRightButton(JButton rotateRightButton) {
        this.rotateRightButton = rotateRightButton;
    }
    
    public JLabel getFreeTileLabel() {
        return freeTileLabel;
    }
    
    public void setFreeTileLabel(JLabel freeTileLabel) {
        this.freeTileLabel = freeTileLabel;
    }
    
}