package info.eronarn.glyph.map;

import info.eronarn.glyph.actor.Monster;
import info.eronarn.glyph.game.Event;

import info.eronarn.glyph.game.EnumData.ItemType;
import info.eronarn.glyph.game.EnumData.Level;
import info.eronarn.glyph.game.EnumData.MonsterType;
import info.eronarn.glyph.item.Item;

import java.util.Arrays;
import java.util.HashMap;

public class Map {
	
	// Start position of the player.
	
	public int startx = 5;
	public int starty = 5;
	
	public final int xmax = 36; // Max X of the map.
	public final int ymax = 36; // Max Y of the map.
	
	public Cell cell[][]; // 2D array of Cell objects.

	// Mappings of monsters, items, events, etc.
	public HashMap<Character, MonsterType> MonMap;
	public HashMap<Character, ItemType> ItemMap;
	public HashMap<Character, Event> EventMap;
	
    // Constructor. Map is not ready, though! Must call InitMap on it,
	// which tells it which level it should load.
	public Map() {
		cell = new Cell[xmax][ymax];
		MonMap = new HashMap<Character, MonsterType>();
		ItemMap = new HashMap<Character, ItemType>();
		EventMap = new HashMap<Character, Event>();
	}
	
	// Parses the returned text map, then goes through each glyph in order (Y, X)
    // and generates a cell (the only information passed in is current glyph).
	public void InitMap (Level which) {
		String[] map = GameData.load(which).split("\n");

		char[][] MapTemplate = new char[xmax][ymax];
		
		for (int i = 0; i < ymax; i++) {
			Arrays.fill(MapTemplate[i], ' ');
		}
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length(); j++) {
				MapTemplate[j][i] = map[i].charAt(j);
			}
		}
		
    	for (int CurrY = 0; CurrY < ymax; CurrY++) {
    		
        	for (int CurrX = 0; CurrX < xmax; CurrX++) {
        		
        		Character Glyph = MapTemplate[CurrX][CurrY];
    			Character Trigger = null;
    			boolean replace = false;
    			
    			MonsterType mons = MonMap.get(Glyph);
    			ItemType item = ItemMap.get(Glyph);
    			Event event = EventMap.get(Glyph);

    			if (mons != null) {
    				Monster monster = new Monster(mons);
    				monster.PlaceMonster(new Pair(CurrX, CurrY));
    				replace = true;
    			}
    			
    			if (item != null) {
    				Item.PlaceItem(item, new Pair(CurrX, CurrY));
    				replace = true;
    			}
    			
    			if (event != null) {
    				Trigger = Glyph;
    				replace = true;
    			}
    			
    			if (replace)
    					Glyph = ' ';
    			
   				cell[CurrX][CurrY] = new Cell(Glyph, new Pair(CurrX, CurrY), Trigger);
        	}
    	}
	
	}
}
