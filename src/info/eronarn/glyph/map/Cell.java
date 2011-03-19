package info.eronarn.glyph.map;

import info.eronarn.glyph.actor.Monster;
import info.eronarn.glyph.game.Event;
import info.eronarn.glyph.game.Game;
import info.eronarn.glyph.item.Item;

public class Cell {
	
	int Index; // The cell's unique ID.
	Pair Coord; // The cell's X,Y coordinate pair.
	public char FloorGlyph = ' ';
	public char ItemGlyph = ' ';
	public char MonsterGlyph = ' ';
	public char SpecialGlyph = ' ';
	public String Style; // The cell's display style.
	public Character Trigger;
	public boolean Redraw = true;
	
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
		FloorGlyph = Contents;
		
		switch (FloorGlyph) {
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':

			FloorGlyph = ' ';
			break;
			
		case 'Q':
		case 'T':
		case 'V':
			FloorGlyph = ' ';

			break;
		default:
			break;
		}
		
		if (FloorGlyph == '%') {
			char[] floor_types = {'.', ',', ' ', '`'};
			FloorGlyph = floor_types[Game.rng.nextInt(4)];
		}
		
		
	}
	public void CheckGlyphs() {
		char oldmons = MonsterGlyph;
		char olditem = ItemGlyph;
		
		Monster mons = Monster.GetMonsterAt(Coord);
		if (mons != null && mons.Glyph != ' ')
		    MonsterGlyph = mons.Glyph;
		else
			MonsterGlyph = ' ';
		
		Item item = Item.GetItemAt(Coord);
		if (item != null && item.Glyph != ' ')
			ItemGlyph = item.Glyph;
		else
			ItemGlyph = ' ';

		if (oldmons != MonsterGlyph || olditem != ItemGlyph)
			Redraw = true;
	}
	
	public boolean Passable() {
		return (FloorGlyph == '.' || FloorGlyph == ',' || FloorGlyph == ' ' || FloorGlyph == '`' || FloorGlyph == '+' || FloorGlyph == '=');
	}
	
	public void FireEvent(Monster monster) {
		if (monster.Index == Game.playerindex && Trigger != null) {
			 Event event = Game.map.EventMap.get(Trigger);
			 if (event != null)
				 event.Fire();
		}
	}
}
