package info.eronarn.glyph.actor;

import java.util.HashMap;

import info.eronarn.glyph.game.EnumData;
import info.eronarn.glyph.game.Game;
import info.eronarn.glyph.game.EnumData.MonsterType;
import info.eronarn.glyph.game.EnumData.Stat;
import info.eronarn.glyph.item.Item;
import info.eronarn.glyph.map.Pair;
import info.eronarn.glyph.ui.UI;

public class Monster extends Actor {

	// The basics
    public String Name = "Buggy monster";
    public String Description = "It's a monster, but buggy!";
    public MonsterType Type;
	public char Glyph = '!';

	// Misc. info
	public Pair Coord;
	public int Index;
    public int TargetIndex;
	public boolean Asleep = false;
	public boolean Alive = true;
//    public Faction Faction;
        
	// Combat stats
    public int HP = 20;
	public int MaxHP = 20;
	public int Protection = 0;
	public int Evasion = 5;
	public int Accuracy = 5;
    public int Damage = 5;
    
    // Equipment
    public boolean ChangeGear = true;
    public boolean HasInv = false;
    public boolean HasEquip = false;
    public HashMap <EnumData.Slot, Item> Equipment;
	public HashMap <Character, Item> Inventory;
	
	public Monster(MonsterType type) {
                // Define monster stuff in another function...?
		// TODO Auto-generated constructor stub
		
	    Name = type.toString().replace('_', ' ');
	    
		switch (type) {
		case player:
			Name = "you";
			Glyph = 'A';		    
			break;
		case beggar:
			break;
		case knave:
			break;
		case scoundrel:
			break;
		case slumlord:
			break;
		case thug:
			break;
		case guard:
			break;
		case blackguard:
			break;
		case citizen:
			break;
		case zombie:
			Glyph = 'Z';
			InitInventory(true);
			ChangeGear = false;
			break;

		default:
			break;
		}
		
		if (EnumData.IsHuman(type))
			InitInventory(true);
	}
	
	public int PlaceMonster(Pair pos) {
		Coord = pos;
		Index = Game.mons_list.SaveToList(this);			
	    Game.mons_grid.Contents(pos, Index);
		Game.queue.Insert(Index);
		return Index;
	}

/**
 * Combat functions.	
 */
	public boolean Attack(Monster defender) {
        int to_hit = 2 + Mod(Stat.Accuracy) - defender.Mod(Stat.Evasion);
        if (to_hit < 1 || Game.rng.nextInt(to_hit) < 1) {
    		UI.Print(Name + " missed monster " + defender.Name + ".");
        	return false;
        }
        else {
    		UI.Print(Name + " hit monster " + defender.Name, false);
            return defender.Hit(Mod(Stat.Damage));
        }
    }

    public boolean Hit(int damage) {
    	int amount = damage - Mod(Stat.Protection);
    	if (damage < 1)
    		return false;
    	else {
    		amount = Game.rng.nextInt(amount) + 1;
    		UI.Print(" for " + amount + "!");
    		return Hurt(amount);
    	}
    }

    public boolean Hurt(int amount) {
    	this.HP -= amount;
    	if (this.HP <= 0)
    		return Die();
    	else
    		return false;
    }
    
    public boolean Die() {
    	if (Index == Game.playerindex) {
    		Player.Die();
    		return false;
    	}
		UI.Print(Name + " has been slain!!!");
    	Alive = false;
    	Game.mons_grid.Contents(Coord, 0);
    	Game.queue.Remove(Index);
    	return true;
    }
    
/**
 * Retrieval/utility functions.
 */

	public static boolean IsMonsterAt(Pair coord) {
		if (GetMonsterAt(coord) != null)
			return true;
		else
			return false;
	}
	
    public static int GetIndexAt(Pair coord) {
        return Game.mons_grid.Contents(coord);
    }
    
	public static Monster GetMonsterAt(int index) {
		Monster mons = (Monster) Game.mons_list.LoadFromList(index);
		return mons;
	}
	
	public static Monster GetMonsterAt(Pair coord) {
		return GetMonsterAt(GetIndexAt(coord));
	}
	
/**
 * Movement functions.
 */
	
	public void Act() {
		Move(RandomMove(), RandomMove());		
//		Main.TextPane("Monster " + Glyph + " moved!");
	}
	
	// Can return -1, 0, or 1.
	public static int RandomMove() {
		return Game.rng.nextInt(3) - 1;
	}

	public void GetTargetIndex(Pair loc) {
		TargetIndex = GetIndexAt(loc);
	}
	
	public void MoveToTarget() {
		Pair target = GetMonsterAt(TargetIndex).Coord;
	}

	public boolean Move(int dx, int dy) {
		
	    Pair move = new Pair(Coord.X() + dx, Coord.Y() + dy);
	    if (ValidMove(move)) {
	    	if (GetMonsterAt(move) != null && !Coord.equals(move))
	    		Attack(GetMonsterAt(move));
	    	else
	    	{
	    		Move(move);
		    	Game.map.cell[move.X()][move.Y()].FireEvent(this);
	    	}
	    	return true;
	    }
	    else
	    	return false;
	}
	
	public boolean ValidMove(Pair pos) {
		if (pos.X() < 0 || pos.X() >= Game.map.xmax || pos.Y() < 0 || pos.Y() >= Game.map.ymax)
			return false;
		else if (Game.map.cell[pos.X()][pos.Y()].Passable())
    		return true;
    	else
    		return false;
	}
	
	public void Move(Pair pos) {
		Game.mons_grid.Contents(Coord, 0);
		Game.mons_grid.Contents(pos, Index);
		Coord = pos;
	}

/**
 * Item effect functions.	
 */
	public int Mod(Stat stat) {
		if (!HasEquip)
			return 0;
		
		int mod = 0;
		for (EnumData.Slot slots : EnumData.Slot.values()) {
			Item item = Equipment.get(slots);
			if (item != null)
				mod += item.value(stat);
		}
		return (value(stat) + mod);
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

		return value;
	}
	
/**
 * Inventory and item management functions.	
 */
	
	// Initializes inventory variables, for carrying things.
	// The argument determines whether to initialize its
	// equipment capabilities, too.
	
    private void InitInventory(boolean equipment) {
	    Inventory = new HashMap <Character, Item>();
	    HasInv = true;
    	if (equipment) {
    		HasEquip = true;
    	    Equipment = new HashMap <EnumData.Slot, Item>();
    	}		
	}

	public boolean CanDrop() {
		return (ChangeGear && !Inventory.isEmpty() && Item.CanDrop(Coord));
	}
	
	public void Drop(char letter) {
		Item item = Inventory.remove(letter);
		Item.Drop(item, Coord);
		UI.Print(Name + " drops " + item.Name + ".");
	}
	
	public boolean CanGet() {
		return (ChangeGear && InventoryRoom() && Item.CanGet(Coord));
	}
	
	public Item Get() {
		Item item = Item.Get(Coord);
		Inventory.put(NextInventoryLetter(), item);
		UI.Print(Name + " picks up " + item.Name + ".");
		return item;
	}
	
	public boolean CanEquip(char letter) {
		Item item = Inventory.get(letter);
		if (ChangeGear && item != null && Equipment.get(item.SlotUsed) == null)
			return true;
		else
			return false;
	}
	
	public void Equip(char letter) {
		Item item = Inventory.get(letter);
		Equipment.put(item.SlotUsed, item);
		UI.Print(Name + " puts on " + item.Name + ".");
	}
	
	public boolean CanUnequip(EnumData.Slot slot) {
		if (ChangeGear && Equipment.get(slot) != null)
			return true;
		else
			return false;
	}
	
	public void Unequip(EnumData.Slot slot) {
		Item unequipped = null;
		if (InventoryRoom()) {
			unequipped = Equipment.remove(slot);
			Inventory.put(NextInventoryLetter(), unequipped);
			UI.Print(Name + " takes off " + unequipped.Name + ".");
		}
		else if (Item.CanDrop(Coord)) {
			unequipped = Equipment.remove(slot);
			Item.Drop(unequipped, Coord);
			UI.Print(Name + " takes off " + unequipped.Name + " and drops it on the ground.");
		}
		else
			UI.Print(Name + " shouldn't be trying to unequip anything! Bug!");
	}
	
	// 97 = 'a'. So, 26 letters, starting from there.
	public char NextInventoryLetter() {
		for (int i = 0; i < 26; i++) {
			if (!Inventory.containsKey((char) (i + 97)))
					return (char) (i + 97);
		}
		return '0';
	}
	
	// Only 26 lowercase letters...
	public boolean InventoryRoom() {
		if (Inventory.size() < 26)
			return true;
		else
			return false;
	}
}
