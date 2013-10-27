package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import module.GameLauncher;
import util.DirectoryParser;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MainMenu extends mainGUI {
	
	/** Variables **/
	private JButton helpButton;
	private JButton gamesButton;
	private JButton optionButton;
	private JButton quitButton;
	/** End of Variables **/
	
	public MainMenu() {
		
		setup();
		setLayout(new GridLayout(2,2));
		defineVariables();
		makeButtons();
		// setUndecorated(true); // hides top bar
		setVisible(true);
		
	}
	
	@Override
	public void makeButtons() {
		helpButton = new JButton("Help");
		gamesButton = new JButton("Games");
		optionButton = new JButton("Options");
		quitButton = new JButton("Quit");
		add(helpButton);
		add(gamesButton);
		add(optionButton);
		add(quitButton);
	}
	
	public static void main(String[] args){
//		UserMenu u = new UserMenu();
//		MainMenu s = new MainMenu();
//		GameMenu n = new GameMenu();
//		
		CategoryMenu c = new CategoryMenu(new DirectoryParser(System.getProperty("user.dir") + "/Games"), 1);
		
	}
	

	
}