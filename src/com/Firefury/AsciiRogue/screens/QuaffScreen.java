package com.Firefury.AsciiRogue.screens;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.items.Item;
import com.Firefury.AsciiRogue.screens.inventory.InventoryBasedScreen;

public class QuaffScreen extends InventoryBasedScreen{
	
	public QuaffScreen(Creature player)
	{
		super(player);
	}

	@Override
	protected String getVerb() {
		return "quaff";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.quaffEffect() != null;
	}

	@Override
	protected Screen use(Item item) {
		player.quaff(item);
		return null;
	}

}
