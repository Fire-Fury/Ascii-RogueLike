package com.Firefury.AsciiRogue.items;

import java.awt.Color;

public class Item {
	private char glyph;
	public char glyph() { return glyph; }
	
	private Color color;
	public Color color() { return color; }
	
	private String name;
	public String name() {return name; }
	
	private int foodValue;
	public int foodValue() { return foodValue; }
	public void modifyFoodValue(int amount) { foodValue += amount; }
	
	private int attackValue;
	public int attackValue() { return attackValue; }
	public void modifyAttackValue(int amt) { attackValue += amt; }
	
	private int defenseValue;
	public int defenseValue() { return defenseValue; }
	public void modifyDefenseValue(int amt) { defenseValue += amt; }
	
	private int thrownAttackValue;
	public int thrownAttackValue() { return thrownAttackValue; }
	public void modifyThrownAttackValue(int amt) { thrownAttackValue += amt; }
	
	private int rangedAttackValue;
	public int rangedAttackValue() { return rangedAttackValue; }
	public void modifyRangedAttackValue(int amt) { rangedAttackValue += amt; }
	
	private Effect quaffEffect;
	public Effect quaffEffect(){ return quaffEffect; }
	public void setQuaffEffect(Effect quaffEffect) { this.quaffEffect = quaffEffect; }
	
	public Item(char glyph, Color color, String name)
	{
		this.glyph = glyph;
		this.color = color;
		this.name = name;
	}
	
	public String details()
	{
		String details = "";
		if(attackValue() != 0)
		{
			details += "     attack:" + attackValue();
		}
		if(defenseValue() != 0)
		{
			details += "     defense:" + defenseValue();
		}
		if(foodValue() != 0)
		{
			details += "     food:" + foodValue();
		}
		
		return details;
	}
	
	public boolean equals(Item itemInQuestion)
	{
		if(this.name().equals(itemInQuestion.name()))
		{
			return true;
		}
		return false;
	}
}
