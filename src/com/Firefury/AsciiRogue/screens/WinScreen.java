package com.Firefury.AsciiRogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class WinScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("Congratulations! You Won.", 11);
		terminal.writeCenter("-- press [enter] to return to menu. --", 22);
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
		{
			return new PlayScreen();
		}
		else
		{
			return this;
		}
	}
	
}
