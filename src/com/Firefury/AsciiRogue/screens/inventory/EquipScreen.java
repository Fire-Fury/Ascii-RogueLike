package com.Firefury.AsciiRogue.screens.inventory;

import com.Firefury.AsciiRogue.entities.Creature;
import com.Firefury.AsciiRogue.items.Item;
import com.Firefury.AsciiRogue.screens.Screen;

public class EquipScreen extends InventoryBasedScreen{
	
	public EquipScreen(Creature player){
		super(player);
	}

	@Override
	protected String getVerb() {
		return "wear or wield";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.attackValue() > 0 || item.defenseValue() > 0;
	}

	@Override
	protected Screen use(Item item) {
		player.equip(item);
		return null;
	}
	
	

}
