package AmazingLabyrinth.View;

import java.awt.*;
import java.io.Serializable;

import javax.swing.*;
import javax.swing.border.*;

/*
Author: Hanyu Zhou

Description: GUI aspect for the boarder for buttons in the game frame

Important Fields
1. Color topColor - white colour for top and left of the border
2. Color bottomColor - gray colour for bottom and right of the border

Available Methods
1. public ButtonBorder() - used for calling up this class (Hanyu Zhou)
2. @Override
   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) - method to draw the border (Hanyu Zhou)
3. @Override
   public Insets getBorderInsets(Component c) - set gap for four sides of the button (Hanyu Zhou)
4. @Override
   public boolean isBorderOpaque() - set opacity for border (Hanyu Zhou)

Private Methods
N/A

 */

public class ButtonBorder implements Border, Serializable {
	private static final long serialVersionUID = 1234567L;
	
	// colours for the border
	Color topColor = Color.white; // top and left
	Color bottomColor = Color.gray; // bottom and right
	
	// constructor method
	public ButtonBorder() {
	}
	
	// draw the border
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		
		// minus the pixel of the starting coordinate of a line
		width--;
		height--;
		
		// draw the lines
		g.setColor(topColor); // set colours to top colour
		g.drawLine(x, y + height, x, y); // draw the line from top right to top left
		g.drawLine(x, y, x + width, y); // draw the line from top left to bottom left
		
		g.setColor(bottomColor); // set colours to bottom colour
		g.drawLine(x + width, y, x + width, y + height); // draw the line from top right to bottom right
		g.drawLine(x, y + height, x + width, y + height); // draw the line from bottom left to bottom right
		
	}
	
	// set the gap for the four sides
	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}
	
	// set opacity
	@Override
	public boolean isBorderOpaque() {
		return true;
	}
	
}