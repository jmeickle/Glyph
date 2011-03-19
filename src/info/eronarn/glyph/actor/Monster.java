package info.eronarn.glyph.actor;

import java.util.HashMap;

import info.eronarn.glyph.game.EnumData;
import info.eronarn.glyph.game.Game;
import info.eronarn.glyph.game.EnumData.MonsterType;
import info.eronarn.glyph.game.EnumData.Slot;
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
    public HashMap <EnumData.Slot, Integer> Equipment;
	public HashMap <Integer, Item> Inventory;
	
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
		for (EnumData.Slot slot : EnumData.Slot.values()) {
			Item item = GetItem(slot);
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
	    Inventory = new HashMap <Integer, Item>();
	    HasInv = true;
    	if (equipment) {
    		HasEquip = true;
    	    Equipment = new HashMap <EnumData.Slot, Integer>();
    	}		
	}

	public boolean HasInSlot(int which, Slot slot) {
		Integer index = Equipment.get(slot);
		if (index != null && index == which)
			return true;
		else
			return false;
	}
	
	public Item GetItem(Slot slot) {
		Integer index = Equipment.get(slot);
		if (index != null)
			return Inventory.get(index);
		else
			return null;
	}
	
	public boolean CanDrop(int which) {
		Item item = Inventory.get(which);
		return (ChangeGear && Item.CanDrop(Coord) && !Inventory.isEmpty() && item != null && !HasInSlot(which, item.SlotUsed));
	}
	
	public void Drop(int which) {
		Item item = Inventory.remove(which);
		Item.Drop(item, Coord);
		UI.MonPrint(Index, Name + " drops " + item.Name + ".");
	}
	
	public boolean CanGet() {
		return (ChangeGear && Item.CanGet(Coord) && InventoryRoom());
	}
	
	public Item Get() {
		Item item = Item.Get(Coord);
		Inventory.put(NextInventoryNumber(), item);
		UI.MonPrint(Index, Name + " picks up " + item.Name + ".");
		return item;
	}
	
	public boolean CanEquip(int which) {
		Item item = Inventory.get(which);
		if (ChangeGear && item != null && Equipment.get(item.SlotUsed) == null && !HasInSlot(which, item.SlotUsed))
			return true;
		else
			return false;
	}
	
	public void Equip(int which) {
		Item item = Inventory.get(which);
		Equipment.put(item.SlotUsed, which);
		UI.MonPrint(Index, Name + " puts on " + item.Name + ".");
	}
	
	public boolean CanUnequip(int which) {
		Item item = Inventory.get(which);
		if (ChangeGear && item != null && HasInSlot(which, item.SlotUsed))
			return true;
		else
			return false;
	}
	
	public void Unequip(int which) {
		Item item = Inventory.get(which);
		int index = Equipment.remove(item.SlotUsed);
		UI.MonPrint(Index, Name + " takes off " + Inventory.get(index).Name + ".");
	}
	
	public int NextInventoryNumber() {
		for (int i = 0; i < 26; i++) {
			if (!Inventory.containsKey(i))
					return i;
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
