package info.eronarn.glyph.game;

import info.eronarn.glyph.Main;
import info.eronarn.glyph.actor.Monster;
import info.eronarn.glyph.actor.Player;
import info.eronarn.glyph.game.EnumData.Level;
import info.eronarn.glyph.game.EnumData.MonsterType;
import info.eronarn.glyph.map.*;
import info.eronarn.glyph.ui.CellPanel;
import info.eronarn.glyph.ui.Keys;
import info.eronarn.glyph.ui.UI;

import java.util.Random;

public class Game {

    public static Random rng = new Random(); // Oh, beloved RNG!
    
    public static Map map; // We use this to make the map and store some stuff in it.
    
	public static List mons_list = new List(500); // Where monsters live.
	public static List item_list = new List(500); // Where objects live.
	
    public static Grid mons_grid; // Indices into mons_list. One monster per tile.
    public static Grid item_grid; // Indices into item_list. One item per tile.
    
    public static Queue queue = new Queue(100); // Queue for monster (incl. player) actions.
    public static boolean ready = false; // Whether we're waiting on player input.
    
	public static Monster player; // Player as a monster.
    public static int playerindex; // Player's monster index.
    
    public Game() {
  
    	// Create the basic map data structures...
    	map = new Map(); 
    	// Create grids that depend on map size...
    	mons_grid = new Grid(map.xmax, map.ymax);
    	item_grid = new Grid(map.xmax, map.ymax);
    	// ... and then fill them with the desired level's info.
        map.InitMap(Level.hotel); 

        // Now, create the player - he's a monster just like everyone else!
        player = new Monster(MonsterType.player);
        playerindex = player.PlaceMonster(new Pair(map.startx, map.starty));
        queue.PlayerFirst(); // However, the player is a cheater; he gets to go first.
            
        // Let's go!
        RunQueue();
    }

    public static void repaintMap() {
    	
    	for (int CurrY = 0; CurrY < UI.ymax; CurrY++) {
    	
        	for (int CurrX = 0; CurrX < UI.xmax; CurrX++) {    	        
    			CellPanel charcell = (CellPanel) Main.u.mainpanel.getComponent(CurrX + CurrY * UI.xmax);
    			charcell.setGlyph(map.cell[CurrX][CurrY].Glyph());
        	}
    	}

    	Main.u.mainpanel.repaint();
    }

    // Runs through the queue until the player comes up. Starts again when the player acts.
    public static void RunQueue(){
      int acting = queue.Next();
      if (acting != playerindex) {
      	Monster mons = (Monster) mons_list.LoadFromList(acting);
    	if (mons != null) {
    		mons.Act();
    		RunQueue();
    	}
      }
      else
      {
          Game.repaintMap();
          Player.RedrawHealthbar();
          Keys.GetCommand();
      }
    }
} 