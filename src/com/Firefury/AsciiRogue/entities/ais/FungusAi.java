package com.Firefury.AsciiRogue.entities.ais;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.entities.StuffFactory;

public class FungusAi extends CreatureAi{
	private StuffFactory factory;
	private int spreadCount;

	public FungusAi(Creature creature, StuffFactory factory) {
		super(creature);
		this.factory = factory;
	}
	
	@Override
	public void onUpdate()
	{
		if(spreadCount < 4 && Math.random() < 0.01)
		{
			spread();
		}
	}
	
	private void spread()
	{
		int x = creature.x + (int)(Math.random() * 3) - 1;
		int y = creature.y + (int)(Math.random() * 3) - 1;
		
		if(!creature.canEnter(x, y, creature.z))
		{
			return;
		}
		
		Creature child = factory.newFungus(creature.z);
		child.x = x;
		child.y = y;
		child.z = creature.z;
		spreadCount++;
		creature.doAction("spawn a child");
	}
	
}
