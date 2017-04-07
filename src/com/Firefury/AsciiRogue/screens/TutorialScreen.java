package com.Firefury.AsciiRogue.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class TutorialScreen implements Screen{

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("Created By: Alexander Wang", 1, 1);
		terminal.write("V1.0.1", 73, 1);
		terminal.writeCenter("Tutorial", 1);
		terminal.write("You are the @.", 4, 4);
		terminal.write("[" + (char)24 + "] or [k] to move up.", 4, 5);
		terminal.write("[" + (char)25 + "] or [j] to move down.", 34, 5);
		terminal.write("[" + (char)26 + "] or [l] to move right.", 4, 6);
		terminal.write("[" + (char)27 + "] or [h] to move left.", 34, 6);
		terminal.write("Press [e] to enter the eat menu.(Also shows edible items)", 4, 7);
		terminal.write("Press [d] to enter the drop menu.(Also shows your inventory)", 4, 8);
		terminal.write("Press [w] to enter the equip/unequip menu.(Also shows weapons/armor)", 4, 9);
		
		terminal.write((char)15, 4, 11, AsciiPanel.brightRed);
		terminal.write((char)15, 5, 11, new Color(88, 32, 0));
		terminal.write((char)15, 6, 11, new Color(255, 200, 68));
		terminal.write(" are food items.", 8, 11);
		
		terminal.write("[ and ) are armor and weapons.", 34, 11);
		
		terminal.write((char)232, 4, 12, AsciiPanel.brightBlue);
		terminal.write(" is the Sapphire Leaf", 6, 12);
		
		terminal.write("Press [Shift]+[>] on '>' tiles to decend one level.", 4, 14);
		terminal.write("Press [Shift]+[<] on '<' tiles to ascend one level.", 4, 15);
		terminal.write("Press [g] to pick up an item directly beneath you.", 4, 16);
		
		terminal.write("The Sapphire leaf is found on the deepest level.(L5)", 4, 18);
		terminal.write("Return to the surface with the leaf in your inventory and you win!", 4, 19);
		
		terminal.writeCenter("-+= Press [Enter] to begin your journey! =+-", 21);
		terminal.writeCenter("-+= Press [ESC] to return to menu! =+-", 22);

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			return new StartScreen();
		}
		else if(key.getKeyCode() == KeyEvent.VK_ENTER)
		{
			return new PlayScreen();
		}
		else
		{
			return this;
		}
	}

}
