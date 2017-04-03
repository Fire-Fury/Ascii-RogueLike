package com.Firefury.AsciiRogue.entities.ais;

import com.Firefury.AsciiRogue.entities.Creature;

public class BatAi extends CreatureAi{
	public BatAi(Creature creature)
	{
		super(creature);
	}
	
	public void onUpdate()
	{
		wander();
		wander();
	}
}
