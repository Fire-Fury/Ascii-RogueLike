package com.Firefury.AsciiRogue.entities.ais;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.exp.LevelUpController;
import com.Firefury.AsciiRogue.tiles.Tile;
import com.Firefury.AsciiRogue.util.Line;
import com.Firefury.AsciiRogue.util.Point;

public class CreatureAi {
	protected Creature creature;
	
	public CreatureAi(Creature creature){
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}
	
	public void onEnter(int x, int y, int z, Tile tile){
		if(tile.isGround())
		{
			creature.x = x;
			creature.y = y; 
			creature.z = z;
		}
		else
		{
			creature.doAction("bump into a wall");
		}
	}
	
	public void wander()
	{
		int mx = (int)(Math.random() * 3) - 1;
		int my = (int)(Math.random() * 3) - 1;
		
		Creature other = creature.creature(creature.x + mx, creature.y + my, creature.z);
		if(other != null && other.getGlyph() == creature.getGlyph())
		{
			return;
		}
		else
		{
			creature.moveBy(mx, my, 0);
		}
	}
	
	public void onUpdate(){
	}
	
	public void onNotify(String message){
	}
	
	public boolean canSee(int wx, int wy, int wz)
	{
		if(creature.z != wz)
		{
			return false;
		}
		
		if(Math.pow(creature.x - wx, 2) + Math.pow(creature.y - wy, 2) > Math.pow(creature.visionRadius(), 2))
		{
			return false;
		}
		
		for(Point p: new Line(creature.x, creature.y, wx, wy))
		{
			if(creature.realTile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy)
			{
				continue;
			}
			return false;
		}
		
		return true;
	}
	
	public void onGainLevel() { 
		new LevelUpController().autoLevelUp(creature);
	} 
	
	public Tile rememberedTile(int mx, int my, int mz)
	{
		return Tile.UNKNOWN;
	}
}
