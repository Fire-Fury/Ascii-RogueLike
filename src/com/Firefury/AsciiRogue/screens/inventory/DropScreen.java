package com.Firefury.AsciiRogue.screens.inventory;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.items.Item;
import com.Firefury.AsciiRogue.screens.Screen;

public class DropScreen extends InventoryBasedScreen{
	public DropScreen(Creature player)
	{
		super(player);
	}

	@Override
	protected String getVerb() {
		return "drop";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		player.drop(item);
		return null;
	}
}
