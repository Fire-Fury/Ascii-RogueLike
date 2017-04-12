package com.Firefury.AsciiRogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("Created By: Alexander Wang", 1, 1);
		terminal.write("V1.1.2", 73, 1);
		terminal.writeCenter("A Generic Rogue-Like", 11);
		terminal.writeCenter("You hear the Sapphire Leaf lies in this cavern...", 13);
		terminal.writeCenter("Resurface with it alive and you shall be rewarded greatly!", 14);
		terminal.writeCenter("-+= Press [Enter] to begin your journey! =+-", 21);
		terminal.writeCenter("-+= Press [T] to learn how to play! =+-", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
		{
			return new PlayScreen();
		}
		else if(key.getKeyCode() == KeyEvent.VK_T)
		{
			return new TutorialScreen();
		}
		else
		{
			return this;
		}
	}
	
}
