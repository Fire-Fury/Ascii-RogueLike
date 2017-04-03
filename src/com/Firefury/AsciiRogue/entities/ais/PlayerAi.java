package com.Firefury.AsciiRogue.entities.ais;

import java.util.ArrayList;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.tiles.Tile;
import com.Firefury.AsciiRogue.util.FieldOfView;

public class PlayerAi extends CreatureAi{
	private ArrayList<String> messages;
	private FieldOfView fov;

	public PlayerAi(Creature creature, ArrayList<String> messages, FieldOfView fov) {
		super(creature);
		this.messages = messages;
		this.fov = fov;
	}
	
	@Override
	public void onEnter(int x, int y, int z, Tile tile)
	{
		if(tile.isGround())
		{
			creature.x = x;
			creature.y = y;
			creature.z = z;
		}
		else if(tile.isDiggable())
		{
			creature.dig(x, y, z);
		}
		
	}
	
	public void onNotify(String msg)
	{
		messages.add(msg);
	}
	
	public boolean canSee(int wx, int wy, int wz) {
	    return fov.isVisible(wx, wy, wz);
	}

}
