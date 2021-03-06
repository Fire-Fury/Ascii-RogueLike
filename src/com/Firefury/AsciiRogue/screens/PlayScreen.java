package com.Firefury.AsciiRogue.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.entities.StuffFactory;
import com.Firefury.AsciiRogue.items.Item;
import com.Firefury.AsciiRogue.screens.inventory.DropScreen;
import com.Firefury.AsciiRogue.screens.inventory.EatScreen;
import com.Firefury.AsciiRogue.screens.inventory.EquipScreen;
import com.Firefury.AsciiRogue.screens.inventory.LevelUpScreen;
import com.Firefury.AsciiRogue.screens.inventory.ThrowScreen;
import com.Firefury.AsciiRogue.screens.target.FireWeaponScreen;
import com.Firefury.AsciiRogue.screens.target.LookScreen;
import com.Firefury.AsciiRogue.tiles.Tile;
import com.Firefury.AsciiRogue.util.FieldOfView;
import com.Firefury.AsciiRogue.world.World;
import com.Firefury.AsciiRogue.world.WorldBuilder;

import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {
	private World world;
	private Creature player;
	private int screenWidth;
	private int screenHeight;
	private ArrayList<String> messages;
	private FieldOfView fov;
	private Screen subscreen;
	
	public PlayScreen(){
		screenWidth = 80;
		screenHeight = 23;
		messages = new ArrayList<String>();
		createWorld();
		
		fov = new FieldOfView(world);
		
		StuffFactory stuffFactory = new StuffFactory(world, fov);
		createCreatures(stuffFactory);
		createItems(stuffFactory);
	}
	
	private static String deathCause = "an unknown encounter";
	public static void setDeathCause(String c)
	{
		deathCause = c;
	}
	
	private void createCreatures(StuffFactory stuffFactory){
		player = stuffFactory.newPlayer(messages);
		
		for (int z = 0; z < world.depth(); z++){
			for (int i = 0; i < 4; i++){
				stuffFactory.newFungus(z);
			}
			for (int i = 0; i < 20; i++){
				stuffFactory.newBat(z);
			}
			for(int i = 0; i < 3; i++)
			{
				stuffFactory.newZombie(z, player);
				stuffFactory.newGoblin(z, player);
			}
		}		
	}
	
	private void createItems(StuffFactory stuffFactory)
	{
		for(int z = 0; z < world.depth(); z++)
		{
			for(int i = 0; i < (world.height() * world.width())/30; i++)
			{
				stuffFactory.newRock(z);
			}
			for(int i = 0; i < 8; i++)
			{
				stuffFactory.newApple(z);
			}
			for(int i = 0; i < 5; i++)
			{
				stuffFactory.newSteak(z);
				stuffFactory.newBread(z);
			}
			for(int i = 0; i < 2; i++)
			{
				stuffFactory.randomWeapon(z);
				stuffFactory.randomArmor(z);
			}
			for(int i = 0; i < 3; i++)
			{
				stuffFactory.newRandomPotion(z);
			}
		}
		stuffFactory.newVictoryItem(world.depth()-1);
	}
	
	private void createWorld(){
		world = new WorldBuilder(90, 32, 5)
					.makeCaves()
					.build();
	}
	
	public int getScrollX() { return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth)); }
	
	public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight)); }
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY(); 
		
		displayTiles(terminal, left, top);
		displayMessages(terminal, messages);
		
		//terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 23);

		String healthStats = "HP: " + String.format("%3d/%3d", player.hp(), player.maxHp());
		String foodStats = "Food: " + String.format("%3d/%3d", player.food(), player.maxFood());
		terminal.write(healthStats, 0, 0);
		terminal.write(foodStats, 0, 1);
	 	
		if(subscreen != null)
		{
			subscreen.displayOutput(terminal);
		}
	}

	private void displayMessages(AsciiPanel terminal, ArrayList<String> messages) {
		int top = screenHeight - messages.size();
		for (int i = 0; i < messages.size(); i++){
			terminal.writeCenter(messages.get(i), top + i + 1);
		}
		messages.clear();
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		
		fov.update(player.x, player.y, player.z, player.visionRadius());
		
		for (int x = 0; x < screenWidth; x++){
			for (int y = 0; y < screenHeight; y++){
				int wx = x + left;
				int wy = y + top;

				if(player.canSee(wx, wy, player.z))
				{
					terminal.write(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
				}
				else
				{
					terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.DARK_GRAY);
				}
			}
		}
	}
	
	private boolean userIsTryingToExit()
	{
		return (player.z == 0) && (world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP); 
	}
	
	private Screen userExits()
	{
		for(Item item : player.inventory().getItems())
		{
			if(item != null && item.name().equals("Sapphire Leaf"))
			{
				return new WinScreen();
			}
		}
		return new LoseScreen("trying to leave without the Sapphire Leaf");
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		int level = player.level();
		
		if(subscreen != null)
		{
			subscreen = subscreen.respondToUserInput(key);
		}
		else
		{
			switch (key.getKeyCode()){
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_H: player.moveBy(-1, 0, 0); break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_L: player.moveBy( 1, 0, 0); break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_K: player.moveBy( 0,-1, 0); break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_J: player.moveBy( 0, 1, 0); break;
			case KeyEvent.VK_Y: player.moveBy(-1,-1, 0); break;
			case KeyEvent.VK_U: player.moveBy( 1,-1, 0); break;
			case KeyEvent.VK_B: player.moveBy(-1, 1, 0); break;
			case KeyEvent.VK_N: player.moveBy( 1, 1, 0); break;
			case KeyEvent.VK_D: subscreen = new DropScreen(player); break;
			case KeyEvent.VK_E: subscreen = new EatScreen(player); break;
			case KeyEvent.VK_W: subscreen = new EquipScreen(player); break;
			case KeyEvent.VK_SEMICOLON: subscreen = new LookScreen(player, "Looking", player.x - getScrollX(), player.y - getScrollY()); break;
			case KeyEvent.VK_T: subscreen = new ThrowScreen(player, player.x - getScrollX(), player.y - getScrollY()); break;
			case KeyEvent.VK_Q: subscreen = new QuaffScreen(player); break;
			case KeyEvent.VK_F: 
				if(player.weapon() == null || player.weapon().rangedAttackValue() == 0)
				{
					player.notify("You do not have a ranged weapon equipped.");
				}
				else
				{
					subscreen = new FireWeaponScreen(player, player.x - getScrollX(), player.y - getScrollY()); break;
				}
			}
		
			switch (key.getKeyChar()){
			case ',':
			case 'g': 
					player.pickup(); 
					break;
			case '<': 
					if(userIsTryingToExit())
						return userExits();
					else
						player.moveBy( 0, 0, -1); 
					break;
			case '>': player.moveBy( 0, 0, 1); break;
			}
		}
		if(player.level() > level)
		{
			subscreen = new LevelUpScreen(player, player.level() - level);
		}	
		if(subscreen == null)
		{
			world.update();
		}
		if(player.hp() < 1)
		{
			return new LoseScreen(deathCause);
		}
		
		
		return this;
	}
}
