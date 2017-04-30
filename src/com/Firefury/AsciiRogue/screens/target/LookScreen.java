package com.Firefury.AsciiRogue.screens.target;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.items.Item;
import com.Firefury.AsciiRogue.tiles.Tile;

public class LookScreen extends TargetBasedScreen{

	public LookScreen(Creature player, String caption, int sx, int sy) {
		super(player, caption, sx, sy);
	}
	
	public void enterWorldCoordinates(int x, int y, int screenX,int screenY)
	{
		Creature creature = player.creature(x, y, player.z);
		if(creature != null)
		{
			caption = creature.glyph() + " "  + creature.name() + creature.details();
			return;
		}
		
		Item item = player.item(x, y, player.z);
		if(item != null)
		{
			caption = item.glyph() + " " + item.name() + item.details();
			return;
		}
		
		Tile tile = player.tile(x, y, player.z);
		
		caption = tile.glyph() + " " + tile.details();
	}

}
