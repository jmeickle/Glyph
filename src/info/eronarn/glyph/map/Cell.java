package info.eronarn.glyph.map;

import info.eronarn.glyph.actor.Monster;
import info.eronarn.glyph.game.Event;
import info.eronarn.glyph.game.Game;
import info.eronarn.glyph.item.Item;

public class Cell {
	
	int Index; // The cell's unique ID.
	Pair Coord; // The cell's X,Y coordinate pair.
	public char Glyph; // The cell's display glyph.
	public String Style; // The cell's display style.
	public Character Trigger;
	
	// Terrain type of the Cell.
	public enum cellType {
		WATER,
		GRASS,
		FLOOR,
		ROAD,
		SHRUB,
		TREE,
		WALL,
		DOOR_OPEN,
		DOOR_CLOSED,
		WINDOW
	}

	// Viewed status of the Cell.
	public enum viewType {
		UNSEEN,
		SEEN,
		IN_SIGHT
	}
	
	public Cell(char Contents, Pair Pos, Character Trigger) {
		this(Contents, Pos);
		this.Trigger = Trigger;
	}
	public Cell(char Contents, Pair Pos) {
		Coord = Pos;
		Glyph = Contents;
		
		switch (Glyph) {
		case 'Q':
		case 'A':
		case 'B':
		case 'C':

			Glyph = ' ';
			break;

		case 'T':
		case 'V':
			Glyph = ' ';

			break;
		default:
			break;
		}
		
		if (Glyph == '%') {
			char[] floor_types = {'.', ',', ' ', '`'};
			Glyph = floor_types[Game.rng.nextInt(4)];
		}
		
		
	}
	public char Glyph() {
		Monster mons = Monster.GetMonsterAt(Coord);
		if (mons != null && mons.Glyph != ' ')
		    return mons.Glyph;
		else {
			Item item = Item.GetItemAt(Coord);
			if (item != null && item.Glyph != ' ')		    
				return item.Glyph;
		}
		// Fallback:
			return this.Glyph;
	}
	
	public boolean Passable() {
		return (Glyph == '.' || Glyph == ',' || Glyph == ' ' || Glyph == '`' || Glyph == '+' || Glyph == '=');
	}
	
	public void FireEvent(Monster monster) {
		if (monster.Index == Game.playerindex && Trigger != null) {
			 Event event = Game.map.EventMap.get(Trigger);
			 if (event != null)
				 event.Fire();
		}
	}
}
