package info.eronarn.glyph.item;

import info.eronarn.glyph.game.EnumData;
import info.eronarn.glyph.game.Game;
import info.eronarn.glyph.game.EnumData.DamageType;
import info.eronarn.glyph.game.EnumData.ItemType;
import info.eronarn.glyph.game.EnumData.Material;
import info.eronarn.glyph.game.EnumData.Slot;
import info.eronarn.glyph.game.EnumData.Stat;
import info.eronarn.glyph.map.Pair;

public class Item {
	
	// The basics
    public String Name;
    public String Description;
    public ItemType Type;
	public char Glyph;

	// Misc. info
	public Pair Coord;
	public int Index;
    public int HP;
	public int MaxHP;
    public boolean Broken;
    public boolean Sharp;
	public int Quality;
	public Material Material;
	public Slot SlotUsed;
    
	// Combat stats
	public int Protection;
	public int Evasion;
	public int Accuracy;
    public int Damage;
    public DamageType DType;
    
    public Item() {
    }
    
    public Item(ItemType which) {
    }
    
	public static Item MakeItem(ItemType which) {

		Item item = new Item();

		// Each subclass of item gets to set some stats first.
		
		if (EnumData.IsWeapon(which)) {
			Item.Weapon weapon = item.new Weapon(which);
			item = weapon;
		}
		
		if (EnumData.IsArmour(which)) {
			Item.Armour armour = item.new Armour(which);
			item = armour;
		}
		
		// Only the truly generic properties get set here.
		item.Type = which;

		return item;
	}
	
	public static int PlaceItem(ItemType which, Pair where) {
		Item item = Item.MakeItem(which);
		item.Coord = where;
		item.Index = Game.item_list.SaveToList(item);			
	    Game.item_grid.Contents(where, item.Index);
		return item.Index;
	}
	
	public static Item GetItemAt(Pair coord) {
		return (Item) Game.item_list.LoadFromList(Game.item_grid.Contents(coord));
	}
	
	public static boolean CanDrop(Pair where) {
		if (Game.item_grid.Contents(where) != 0)
			return false;
		else
			return true;
	}
	
	public static void Drop(Item dropped, Pair where) {
		Game.item_grid.Contents(where, dropped.Index);
		dropped.Coord = where;
	}
	
	public static boolean CanGet(Pair where) {
		if (Game.item_grid.Contents(where) == 0)
			return false;
		else
			return true;
	}
	
	public static Item Get(Pair where) {
		Item item = GetItemAt(where);
		Game.item_grid.Contents(where, 0);
		return item;
	}
	
	public class Weapon extends Item {
		
		public Weapon(ItemType which) {
			// Defaults:
			
			SlotUsed = Slot.RightHand;
			Glyph = '/';
			Name = which.toString().replace('_', ' ');;
			
			switch (which) {
			// This is mostly here for perspective...
			case hand:
				Accuracy = +0;
				Damage = +0;
				DType = DamageType.bludgeoning;
			case claw:
				Accuracy = +1;
				Damage = +2;
				DType = DamageType.piercing;
				break;
			case teeth:
				Accuracy = +0;
				Damage = +3;
				DType = DamageType.piercing;
				break;
			case tentacle:
				Accuracy = -2;
				Damage = +3;
				DType = DamageType.bludgeoning;
				break;
			case axe:
				Accuracy = -2;
				Damage = +5;
				DType = DamageType.chopping;
				Glyph = 'W';
				break;
			case knife:
				Accuracy = +3;
				Damage = +1;
				DType = DamageType.stabbing;
				Glyph = '{';
				break;
			case spear:
				Accuracy = +2;
				Damage = +2;
				DType = DamageType.piercing;
				break;
			case staff:
				Accuracy = +2;
				Damage = +1;
				DType = DamageType.bludgeoning;
				Glyph = '|';
				break;
			case sword:
				Accuracy = +2;
				Damage = +3;
				DType = DamageType.slashing;
				break;
			default:
				break;
			}
		}
		
	}
	
	public class Armour extends Item {
		
		public Armour(ItemType which) {
			// Defaults:
			
			Glyph = '[';
			SlotUsed = Slot.Body;
			Name = which.toString().replace('_', ' ');
			
			switch (which) {

			case hat:
				break;
			case helmet:
				Accuracy = -1;
				Protection = +1;
				break;
			case cloak:
				break;
			case gloves:
				break;
			case gauntlets:
				Protection = +1;
				Damage = +1;
				break;
			case shoes:
				break;
			case boots:
				Damage = +1;
				break;
			case sollerets:
				Damage = +1;
				Protection = +2;
				Evasion = -1;
				break;
			case robe:
				break;
			case vest:
				break;
			case tunic:
				break;
			case leather_armour:
				Protection = +1;
				break;
			case mail_armour:
				Protection = +3;
				Evasion = -1;
				break;
			case plate_armour:
				Protection = +5;
				Evasion = -2;
				break;
			default:
				break;
			}
		}
		
	}

	public int value(Stat stat) {
		int value = 0;
		
		switch (stat) {
		case Accuracy:
			value += Accuracy;
			break;
		case Damage:
			value += Damage;
			break;
		case Evasion:
			value += Evasion;
			break;
		case Protection:
			value += Protection;
			break;
		default:
			break;
		}
		// TODO Auto-generated method stub
		return value;
	}
}
