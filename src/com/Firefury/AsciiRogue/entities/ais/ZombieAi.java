package com.Firefury.AsciiRogue.entities.ais;

import java.util.List;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.util.Path;
import com.Firefury.AsciiRogue.util.Point;

public class ZombieAi extends CreatureAi{
	private Creature player;

	public ZombieAi(Creature creature, Creature player) {
		super(creature);
		this.player = player;
	}
	
	@Override
	public void onUpdate()
	{
		if(Math.random() < 0.2)
		{
			return;
		}
		
		if(creature.canSee(player.x, player.y, player.z))
		{
			hunt(player);
		}
		else
		{
			wander();
		}
	}
	
	public void hunt(Creature target)
	{
		List<Point> points = new Path(creature, target.x, target.y).points();
		
		if(points == null || points.size() == 0)
		{
			return;
		}
		
		int mx = points.get(0).x - creature.x;
		int my = points.get(0).y - creature.y;
		
		creature.moveBy(mx, my, 0);
	}
	
}
