package com.Firefury.AsciiRogue.entities;

import java.awt.Color;
import java.util.ArrayList;

import com.Firefury.AsciiRogue.entities.ais.BatAi;
import com.Firefury.AsciiRogue.entities.ais.FungusAi;
import com.Firefury.AsciiRogue.entities.ais.PlayerAi;
import com.Firefury.AsciiRogue.items.Item;
import com.Firefury.AsciiRogue.util.FieldOfView;
import com.Firefury.AsciiRogue.world.World;

import asciiPanel.AsciiPanel;

public class StuffFactory {
	private World world;
	private FieldOfView fov;
	
	public StuffFactory(World world, FieldOfView fov)
	{
		this.world = world;
		this.fov = fov;
	}
	
	public Creature newPlayer(ArrayList<String> messages)
	{
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5, "Player");
		world.addAtEmptyLocation(player, 0);
		new PlayerAi(player, messages, fov);
		return player;
	}
	
	public Creature newFungus(int z)
	{
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0, "Fungus");
		world.addAtEmptyLocation(fungus, z);
		new FungusAi(fungus, this);
		return fungus;
	}
	
	public Creature newBat(int depth)
	{
		Creature bat = new Creature(world, 'b', AsciiPanel.yellow, 15, 5, 0, "Bat");
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}
	
	public Item newRock(int depth)
	{
		Item rock = new Item(',' , AsciiPanel.yellow, "rock");
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}
	
	public Item newVictoryItem(int depth)
	{
		Item item = new Item((char)232, AsciiPanel.brightBlue, "Sapphire Leaf");
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	//Food Items
	public Item newApple(int depth)
	{
		Item apple = new Item((char)15 , AsciiPanel.brightRed, "Apple");
		apple.modifyFoodValue(45);
		world.addAtEmptyLocation(apple, depth);
		return apple;
	}
	public Item newSteak(int depth)
	{
		Item steak = new Item((char)15 , new Color(88, 32, 0), "Steak");
		steak.modifyFoodValue(200);
		world.addAtEmptyLocation(steak, depth);
		return steak;
	}
	public Item newBread(int depth)
	{
		Item bread = new Item((char)15, new Color(255, 200, 68), "Bread");
		bread.modifyFoodValue(100);
		world.addAtEmptyLocation(bread, depth);
		return bread;
	}
}
