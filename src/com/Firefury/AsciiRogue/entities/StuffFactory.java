package com.Firefury.AsciiRogue.entities;

import java.awt.Color;
import java.util.ArrayList;

import com.Firefury.AsciiRogue.entities.ais.BatAi;
import com.Firefury.AsciiRogue.entities.ais.FungusAi;
import com.Firefury.AsciiRogue.entities.ais.GoblinAi;
import com.Firefury.AsciiRogue.entities.ais.PlayerAi;
import com.Firefury.AsciiRogue.entities.ais.ZombieAi;
import com.Firefury.AsciiRogue.items.Effect;
import com.Firefury.AsciiRogue.items.Item;
import com.Firefury.AsciiRogue.util.FieldOfView;
import com.Firefury.AsciiRogue.world.World;

import asciiPanel.AsciiPanel;

public class StuffFactory {
	private World world;
	private FieldOfView fov;
	
	public StuffFactory(World world, FieldOfView fov)
	{
		this.world = world;
		this.fov = fov;
	}
	
	public Creature newPlayer(ArrayList<String> messages)
	{
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5, "Player");
		world.addAtEmptyLocation(player, 0);
		new PlayerAi(player, messages, fov);
		return player;
	}
	
	public Creature newFungus(int z)
	{
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0, "Fungus");
		world.addAtEmptyLocation(fungus, z);
		new FungusAi(fungus, this);
		return fungus;
	}
	
	public Creature newBat(int depth)
	{
		Creature bat = new Creature(world, 'b', AsciiPanel.yellow, 15, 5, 0, "Bat");
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}
	
	public Creature newZombie(int depth, Creature player)
	{
		Creature zombie = new Creature(world, 'Z', AsciiPanel.brightGreen, 50, 10, 10, "Zombie");
		world.addAtEmptyLocation(zombie, depth);
		new ZombieAi(zombie, player);
		return zombie;
	}
	
	public Creature newGoblin(int depth, Creature player)
	{
		Creature goblin = new Creature(world, 'G', AsciiPanel.brightGreen, 66, 15, 5, "goblin", 6);
		goblin.equip(randomWeapon(depth));
		goblin.equip(randomArmor(depth));
		world.addAtEmptyLocation(goblin, depth);
		new GoblinAi(goblin, player);
		return goblin;
	}
	//ITEMS
	
	public Item newRock(int depth)
	{
		Item rock = new Item(',' , AsciiPanel.yellow, "rock");
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}
	
	public Item newVictoryItem(int depth)
	{
		Item item = new Item((char)232, AsciiPanel.brightBlue, "Sapphire Leaf");
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	//Food Items
	public Item newApple(int depth)
	{
		Item apple = new Item((char)15 , AsciiPanel.brightRed, "Apple");
		apple.modifyFoodValue(45);
		world.addAtEmptyLocation(apple, depth);
		return apple;
	}
	public Item newSteak(int depth)
	{
		Item steak = new Item((char)15 , new Color(88, 32, 0), "Steak");
		steak.modifyFoodValue(200);
		world.addAtEmptyLocation(steak, depth);
		return steak;
	}
	public Item newBread(int depth)
	{
		Item bread = new Item((char)15, new Color(255, 200, 68), "Bread");
		bread.modifyFoodValue(75);
		world.addAtEmptyLocation(bread, depth);
		return bread;
	}
	
	public Item newBaguette(int depth)
	{
		Item baguette = new Item('|', AsciiPanel.yellow, "Baguette");
		baguette.modifyFoodValue(75);
		baguette.modifyAttackValue(1);
		return baguette;
	}
	
	//Weapons and Armor
	public Item newDagger(int depth)
	{
		Item dagger = new Item(')', AsciiPanel.white, "Dagger");
		dagger.modifyAttackValue(5);
		world.addAtEmptyLocation(dagger, depth);
		return dagger;
	}
	
	public Item newSword(int depth)
	{
		Item sword = new Item(')', AsciiPanel.white, "Sword");
		sword.modifyAttackValue(10);
		world.addAtEmptyLocation(sword, depth);
		return sword;
	}
	
	public Item newStaff(int depth)
	{
		Item staff = new Item(')', AsciiPanel.white, "Staff");
		staff.modifyAttackValue(6);
		staff.modifyDefenseValue(3);
		world.addAtEmptyLocation(staff, depth);
		return staff;
	}
	
	public Item newBow(int depth)
	{
		Item bow = new Item(';', AsciiPanel.red, "Bow");
		bow.modifyAttackValue(1);
		bow.modifyRangedAttackValue(6);
		world.addAtEmptyLocation(bow, depth);
		return bow;
	}
	
	public Item newLightArmor(int depth){
	    Item item = new Item('[', AsciiPanel.green, "tunic");
	    item.modifyDefenseValue(2);
	    world.addAtEmptyLocation(item, depth);
	    return item;
	  }

	  public Item newMediumArmor(int depth){
	    Item item = new Item('[', AsciiPanel.white, "chainmail");
	    item.modifyDefenseValue(4);
	    world.addAtEmptyLocation(item, depth);
	    return item;
	  }

	  public Item newHeavyArmor(int depth){
	    Item item = new Item('[', AsciiPanel.brightWhite, "platemail");
	    item.modifyDefenseValue(6);
	    world.addAtEmptyLocation(item, depth);
	    return item;
	  }
	  
	  public Item randomWeapon(int depth){
		    switch ((int)(Math.random() * 5)){
		    case 0: return newDagger(depth);
		    case 1: return newSword(depth);
		    case 2: return newBaguette(depth);
		    case 3: return newBow(depth);
		    default: return newStaff(depth);
		    }
	  }

	  public Item randomArmor(int depth){
		    switch ((int)(Math.random() * 3)){
		    case 0: return newLightArmor(depth);
		    case 1: return newMediumArmor(depth);
		    default: return newHeavyArmor(depth);
		    }
	  }
	  
	  //Potions and Effects
	  public Item newHealthPotion(int depth)
	  {
		  Item hpPot = new Item('!', AsciiPanel.brightRed, "health potion");
		  hpPot.setQuaffEffect(new Effect(1) {
			  public void start(Creature creature)
			  {
				  if(creature.hp() == creature.maxHp())
				  {
					  return;
				  }
				  
				  creature.modifyHp(20);
				  creature.doAction("look healtier");
			  }
		  });
		  
		  world.addAtEmptyLocation(hpPot, depth);
		  return hpPot;
	  }
	  
	  public Item newPoisonPotion(int depth)
	  {
		  Item item = new Item('!', AsciiPanel.brightRed, "poison potion");
		  item.setQuaffEffect(new Effect(20) {
		  		public void start(Creature creature)
		  		{
		  			creature.doAction("look sick");
		  		}
		  		
		  		public void update(Creature creature)
		  		{
		  			super.update(creature);
		  			creature.modifyHp(-1);
		  		}
		  });
		  
		  world.addAtEmptyLocation(item, depth);
		  return item;
	  }
	  
	  public Item newStrengthPotion(int depth)
	  {
		  Item item = new Item('!', AsciiPanel.brightRed, "strength potion");
		  item.setQuaffEffect(new Effect(20){
			  public void start(Creature creature)
			  {
				  creature.modifyAttackValue(5);
				  creature.modifyDefenseValue(5);
				  creature.doAction("look stronger");
			  }
			  
			  public void end(Creature creature)
			  {
				  creature.modifyAttackValue(-5);
				  creature.modifyDefenseValue(-5);
				  creature.doAction("look less strong");
			  }
		  });
		  
		  world.addAtEmptyLocation(item, depth);
		  return item;
	  }
	  
	  public Item newSlowHealthPotion(int depth)
	  {
		  Item item = new Item('!', AsciiPanel.red, "slow health potion");
		  item.setQuaffEffect(new Effect(20){
			  public void start(Creature creature)
			  {
				  creature.doAction("slowly feels better");
			  }
			  public void update(Creature creature)
			  {
				  super.update(creature);
				  creature.modifyHp(2);
			  }
		  });
		  world.addAtEmptyLocation(item, depth);
		  return item;
	  }
	  
	  public Item newPotionOfArcher(int depth){
			Item item = new Item('!', AsciiPanel.white, "archer's potion");
			item.setQuaffEffect(new Effect(20){
				public void start(Creature creature){
					creature.modifyVisionRadius(3);
					creature.doAction("look more alert");
				}
				public void end(Creature creature){
					creature.modifyVisionRadius(-3);
					creature.doAction("look less alert");
				}
			});
			
			world.addAtEmptyLocation(item, depth);
			return item;
		}
	  
	  public Item newPotionOfExperience(int depth){
			Item item = new Item('!', AsciiPanel.white, "experience potion");
			item.setQuaffEffect(new Effect(20){
				public void start(Creature creature){
					creature.doAction("look more experienced");
					creature.modifyXp(creature.level() * 5);
				}
			});
			
			world.addAtEmptyLocation(item, depth);
			return item;
		}
	  
	  public Item newRandomPotion(int depth)
	  {
		  switch((int)(Math.random()*6))
		  {
			  case 0 : return newHealthPotion(depth);
			  case 1 : return newPoisonPotion(depth);
			  case 2 : return newSlowHealthPotion(depth);
			  case 3 : return newPotionOfArcher(depth);
			  case 4 : return newPotionOfExperience(depth);
			  default : return newStrengthPotion(depth);
		  }
	  }
}
