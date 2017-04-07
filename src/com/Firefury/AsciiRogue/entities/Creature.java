package com.Firefury.AsciiRogue.entities;

import java.awt.Color;

import com.Firefury.AsciiRogue.entities.ais.CreatureAi;
import com.Firefury.AsciiRogue.items.Inventory;
import com.Firefury.AsciiRogue.items.Item;
import com.Firefury.AsciiRogue.screens.PlayScreen;
import com.Firefury.AsciiRogue.tiles.Tile;
import com.Firefury.AsciiRogue.world.World;

public class Creature {
	private World world;
	
	public int x;
	public int y;
	public int z;
	
	private String name;
	public String getName() { return name; }
	
	private char glyph;
	public char getGlyph() { return glyph; }
	
	private Color color;
	public Color getColor() { return color; }

	private CreatureAi ai;
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
	
	private int maxHp;
	public int maxHp() { return maxHp; }
	
	private int hp;
	public int hp() { return hp; }
	
	private int maxFood;
	public int maxFood() { return maxFood; }
	
	private int food;
	public int food() { return food; }
	
	private int attackValue;
	public int attackValue() { 
		int atkValue = attackValue;
		if(weapon != null)
		{
			atkValue += weapon.attackValue();
		}
		if(armor != null)
		{
			atkValue += armor.attackValue();
		}
		return atkValue;
	}

	private int defenseValue;
	public int defenseValue() { 
		int defValue = defenseValue;
		if(weapon != null)
		{
			defValue += weapon.defenseValue();
		}
		if(armor != null)
		{
			defValue += armor.defenseValue();
		}
		return defValue;
	}
	
	private int visionRadius;
	public int visionRadius() { return visionRadius; }
	
	private Item weapon;
	public Item weapon() { return weapon; }
	
	private Item armor;
	public Item armor() { return armor; }
	
	private Inventory inventory;
	public Inventory inventory()
	{
		return inventory;
	}
	
	public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense, String name){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.visionRadius = 11;
		this.name = name;
		this.inventory = new Inventory(20);
		
		this.maxFood = 1000;
		this.food = maxFood/4 * 3;
	}
	
	public boolean canSee(int wx, int wy, int wz)
	{
		return ai.canSee(wx, wy, wz);
	}
	
	public Tile tile(int wx, int wy, int wz)
	{
		return world.tile(wx, wy, wz);
	}
	
	public void moveBy(int mx, int my, int mz){
		if(mx == 0 && my == 0 && mz == 0)
		{
			return;
		}
		
		Tile tile = world.tile(x+mx, y+my, z+mz);
		
		if (mz == -1){
			if (tile == Tile.STAIRS_DOWN) {
				doAction("walk up the stairs to level %d", z+mz+1);
			} else {
				doAction("try to go up but are stopped by the cave ceiling");
				return;
			}
		} else if (mz == 1){
			if (tile == Tile.STAIRS_UP) {
				doAction("walk down the stairs to level %d", z+mz+1);
			} else {
				doAction("try to go down but are stopped by the cave floor");
				return;
			}
		}
		
		Creature other = world.creature(x+mx, y+my, z+mz);
		
		if (other == null)
			ai.onEnter(x+mx, y+my, z+mz, tile);
		else
			attack(other);
	}
	
	public void pickup()
	{
		Item item = world.item(x, y, z);
		if(inventory.isFull() || item == null)
		{
			doAction("grab at the ground");
		}
		else
		{
			doAction("pickup a %s", item.name());
			world.remove(x,y,z);
			inventory.add(item);
		}
	}
	
	public void drop(Item item)
	{
		if(world.addAtEmptySpace(item, x, y, z))
		{
			doAction("drop a " + item.name());
			inventory.remove(item);
			unequip(item);
		}
		else
		{
			notify("There is nowhere to drop the %s", item.name());
		}
	}
	
	public void unequip(Item item)
	{
		if(item == null)
			return;
		
		if(item == armor)
		{
			notify("remove a " + item.name());
			armor = null;
		}
		else if(item == weapon)
		{
			notify("put away a " + item.name());
			weapon = null;
		}
	}
	
	public void equip(Item item)
	{
		if(item.attackValue() == 0 && item.defenseValue() == 0)
		{
			return;
		}
		
		if(item.attackValue() >= item.defenseValue())
		{
			unequip(weapon);
			notify("wield a " + item.name());
			weapon = item;
		}
		else
		{
			unequip(armor);
			notify("put on a " + item.name());
			armor = item;
		}
	}

	public void attack(Creature other){
		modifyFoodAmount(-5);
		int amount = Math.max(0, attackValue() - other.defenseValue());
		
		amount = (int)(Math.random() * amount) + 1;
		
		doAction("attack the '%s' for %d damage", other.getName(), amount);
		
		other.modifyHp(-amount);
		
		if(other.getName().equals("Player") && other.hp() < 1)
		{
			PlayScreen.setDeathCause("combat");
		}
	}

	public void modifyHp(int amount) { 
		hp += amount;
		
		if(hp > maxHp)
		{
			hp = maxHp;
		}
		else if (hp < 1) {
			doAction("die");
			leaveCorpse();
			world.remove(this);
		}
	}
	
	public void modifyFoodAmount(int amt)
	{
		food += amt;
		if(food > 1500)
		{
			food = maxFood;
			PlayScreen.setDeathCause("overeating");
			modifyHp(-1000);
		}
		else if(food > maxFood)
		{
			maxFood = maxFood + amt/3;
			food = maxFood;
			notify("You can't believe your stomach can hold that much!");
			modifyHp(-1);
		}
		else if(food < 1 && isPlayer())
		{
			PlayScreen.setDeathCause("starvation");
			modifyHp(-1000);
		}
	}
	
	public boolean isPlayer()
	{
		return getGlyph() == '@';
	}
	
	public void eat(Item item)
	{
		if(item.foodValue() < 0)
		{
			notify("Ugh. Gross!");
		}
		
		modifyFoodAmount(item.foodValue());
		inventory.remove(item);
		unequip(item);
	}
	
	private void leaveCorpse()
	{
		Item corpse = new Item('%', color, name + " corpse");
		corpse.modifyFoodValue(maxHp*3);
		world.addAtEmptySpace(corpse, x, y, z);
	}
	
	public void dig(int wx, int wy, int wz) {
		modifyFoodAmount(-10);
		world.dig(wx, wy, wz);
		doAction("dig");
	}
	
	public void update(){
		modifyFoodAmount(-1);
		ai.onUpdate();
	}

	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
	}

	public void notify(String message, Object ... params){
		ai.onNotify(String.format(message, params));
	}
	
	public void doAction(String message, Object ... params){
		int r = 9;
		for (int ox = -r; ox < r+1; ox++){
			for (int oy = -r; oy < r+1; oy++){
				if (ox*ox + oy*oy > r*r)
					continue;
				
				Creature other = world.creature(x+ox, y+oy, z);
				
				if (other == null)
					continue;
				
				if (other == this)
					other.notify("You " + message + ".", params);
				else if(other.canSee(x, y, z))
					other.notify(String.format("The '%s' %s.", getName(), makeSecondPerson(message)), params);
			}
		}
	}
	
	private String makeSecondPerson(String text){
		String[] words = text.split(" ");
		words[0] = words[0] + "s";
		
		StringBuilder builder = new StringBuilder();
		for (String word : words){
			builder.append(" ");
			builder.append(word);
		}
		
		return builder.toString().trim();
	}
	
	public Creature creature(int mx, int my, int mz)
	{
		return world.creature(mx, my, mz);
	}
}
