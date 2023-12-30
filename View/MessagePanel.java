
package AmazingLabyrinth.View;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;

/*
Author: Hanyu Zhou

Description: GUI aspect for the panel displaying player's action

Important Fields
1. JTextArea msgTextArea - text area to display message
2. JScrollPane msgScrollPane - scroll pane that allows the user to see actions before

Available Methods
1. public MessagePanel() - set text area and scroll pane for the panel
2. mutators and accessors

Private Methods
N/A

 */

public class MessagePanel extends JPanel implements Serializable{
	private static final long serialVersionUID = 1234567L;
	
	// text area and scroll pane to display messages
	JTextArea msgTextArea = new JTextArea("Game Started\r\n");
	JScrollPane msgScrollPane = new JScrollPane(msgTextArea);
	
	// constructor method
	public MessagePanel() {
		
		// set text area
		msgTextArea.setFont(new Font("TimesRoman BOLD", Font.PLAIN, 20));
		msgTextArea.setEditable(false);
		msgTextArea.setForeground(Color.white);
		msgTextArea.setBackground(new Color(199, 158, 101));
		msgTextArea.setCaretPosition(msgTextArea.getDocument().getLength()); // always set the scroll pane to the bottom
		
		// set scroll pane
		msgScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		msgScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		msgScrollPane.setPreferredSize(new Dimension(500, 130));
		add(msgScrollPane);
		
		// remove background for both
		msgScrollPane.getViewport().setOpaque(false);
		msgScrollPane.setOpaque(false);
		msgTextArea.setOpaque(false);
		
		// set size and opacity
		setSize(500, 130);
		setOpaque(false);
		
	}
	
	// mutators and accessors
	public JTextArea getMsgTextArea() {
		return msgTextArea;
	}
	
	public void setMsgTextArea(JTextArea msgTextArea) {
		this.msgTextArea = msgTextArea;
	}
	
}