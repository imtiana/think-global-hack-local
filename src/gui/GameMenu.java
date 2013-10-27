/* 
 * GameMenu.java
 * 
 * This file contains the category selection menu
 * The buttons are generated based on the directory names in the Games\ directory
 * 
 * Template taken from "mainGUI.java"
 * 
 * @author Wesley Tsai
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import util.DirectoryParser;

public class GameMenu extends mainGUI {
	private JLabel frame_title;
	private JPanel contentPane;
	private DirectoryParser directoryParser;

	/**
	 * Create the frame.
	 */
	public GameMenu() {
		directoryParser = new DirectoryParser(System.getProperty("user.dir") + "/Games");

		setup();
		setLayout(new GridLayout(directoryParser.getCategoryStrings().size(), 1));
		defineVariables();		
		// Pass the list of strings, and add a button to each
		createButtons(directoryParser.getCategoryStrings());

		// This function takes out the frame
		//this.setUndecorated(true);
		
		setVisible(true);
	}

	private class CategoryButtonAction extends AbstractAction {
		CategoryButtonAction(DirectoryParser directoryParser, int buttonIndex) {
			super();
			this.buttonIndex = buttonIndex;
			this.directoryParser = directoryParser;
		}
		
		private int buttonIndex;
		private DirectoryParser directoryParser;
		@Override
		public void actionPerformed(ActionEvent action) {
			CategoryMenu c = new CategoryMenu(directoryParser, buttonIndex);
		}
	}
	
	private void createButtons(List<String> categoryStrings) {
		for( int i = 0; i < categoryStrings.size(); i++ ) {
			JButton buttonToAdd = new JButton();
			buttonToAdd.setText(categoryStrings.get(i));
			buttonToAdd.setSize(20, 3);
			buttonToAdd.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(new Integer(i+1).toString()), "gameButtonPressed");
			buttonToAdd.getActionMap().put("gameButtonPressed", new CategoryButtonAction(directoryParser, i));
			this.add(buttonToAdd);
		}
		
		JButton buttonToAdd = new JButton("Exit");
		buttonToAdd.setSize(20, 5);
		buttonToAdd.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('e'), "exitButtonPressed");
		buttonToAdd.getActionMap().put("exitButtonPressed", new ExitAction(this));
		this.add(buttonToAdd);
	}
}
