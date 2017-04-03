package com.Firefury.AsciiRogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen{
	
	private String causeOfDeath;
	
	public LoseScreen(String cause)
	{
		this.causeOfDeath = cause;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter(String.format("You died from %s. Game Over.", causeOfDeath), 11);
        terminal.writeCenter("-- press [enter] to return to menu. --", 22);
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
		{
			return new StartScreen();
		}
		else
		{
			return this;
		}
	}

}
