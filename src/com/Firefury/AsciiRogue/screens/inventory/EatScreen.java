package com.Firefury.AsciiRogue.screens.inventory;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.items.Item;
import com.Firefury.AsciiRogue.screens.Screen;

public class EatScreen extends InventoryBasedScreen{

	public EatScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "eat";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.foodValue() != 0;
	}

	@Override
	protected Screen use(Item item) {
		player.eat(item);
		return null;
	}

}
