package com.Firefury.AsciiRogue.screens.inventory;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.items.Item;
import com.Firefury.AsciiRogue.screens.Screen;

public class ExamineScreen extends InventoryBasedScreen {

	public ExamineScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "examine";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		String article = "aeiou".contains(item.name().subSequence(0, 1)) ? "an " : "a ";
		player.notify("It's " + article + item.name() + "." + item.details());
		return null;
	}
	
}
