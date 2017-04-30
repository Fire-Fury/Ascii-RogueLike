package com.Firefury.AsciiRogue.exp;

import java.util.ArrayList;
import java.util.List;

import com.Firefury.AsciiRogue.entities.Creature;

public class LevelUpController {
	
	private static LevelUpOption[] options = new LevelUpOption[]{
		new LevelUpOption("Increased HP"){
			public void invoke(Creature creature) { creature.gainMaxHp(); }
		},
		new LevelUpOption("Increased Attack"){
			public void invoke(Creature creature) { creature.gainAttackValue(); }
		},
		new LevelUpOption("Increased Defense"){
			public void invoke(Creature creature) { creature.gainDefenseValue(); }
		},
		new LevelUpOption("Increased Vision"){
			public void invoke(Creature creature) { creature.gainVision(); }
		}
	};
	
	public void autoLevelUp(Creature creature)
	{
		options[(int)(Math.random() * options.length)].invoke(creature);
	}
	
	public List<String> getLevelUpOptions(){
		List<String> names = new ArrayList<String>();
		for (LevelUpOption option : options){
			names.add(option.name());
		}
		return names;
	}
	
	public LevelUpOption getLevelUpOption(String name){
		for (LevelUpOption option : options){
			if (option.name().equals(name))
				return option;
		}
		return null;
	}
}
