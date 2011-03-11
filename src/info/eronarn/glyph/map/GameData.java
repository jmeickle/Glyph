package info.eronarn.glyph.map;

import info.eronarn.glyph.game.Event;
import info.eronarn.glyph.game.Game;
import info.eronarn.glyph.game.EnumData.ItemType;
import info.eronarn.glyph.game.EnumData.Level;
import info.eronarn.glyph.game.EnumData.MonsterType;

public abstract class GameData {

	/**
	 * Box drawing characters! Just for convenient reference.
	 *  	0	1	2	3	4	5	6	7	8	9	A	B	C	D	E	F
U+250x	─	━	│	┃	┄	┅	┆	┇	┈	┉	┊	┋	┌	┍	┎	┏
U+251x	┐	┑	┒	┓	└	┕	┖	┗	┘	┙	┚	┛	├	┝	┞	┟
U+252x	┠	┡	┢	┣	┤	┥	┦	┧	┨	┩	┪	┫	┬	┭	┮	┯
U+253x	┰	┱	┲	┳	┴	┵	┶	┷	┸	┹	┺	┻	┼	┽	┾	┿
U+254x	╀	╁	╂	╃	╄	╅	╆	╇	╈	╉	╊	╋	╌	╍	╎	╏
U+255x	═	║	╒	╓	╔	╕	╖	╗	╘	╙	╚	╛	╜	╝	╞	╟
U+256x	╠	╡	╢	╣	╤	╥	╦	╧	╨	╩	╪	╫	╬	╭	╮	╯
U+257x	╰	╱	╲	╳	╴	╵	╶	╷	╸	╹	╺	╻	╼	╽	╾	
	 */
	
	public static String load(Level which) {
		String map = "";
		
		switch (which) {
		case tutorial:
			
			SetStart(5, 5);
				    	
			add('A', MonsterType.zombie);
			add('B', MonsterType.zombie);
			add('C', MonsterType.zombie);
			add('T', new Event("Some text here! Only happens once!"));
			add('V', new Event("Some text here! Only happens once!"));
	    	map = ""
  		      + "╔══╗  ╔══╗" + "\n"
              + "║  ╚══╝  ║" + "\n"
              + "║  A V 0╔╣" + "\n"
              + "╚╗      ╚╝" + "\n"
              + " ║B       " + "\n"
              + " ║     T  " + "\n"
              + "╔╝      ╔╗" + "\n"
              + "║ C    0╚╣" + "\n"
              + "║  ╔══╗  ║" + "\n"
              + "╚══╝  ╚══╝" + "\n";
			
			break;
		case hotel:
			SetStart(35, 3);
	    	
			add('A', MonsterType.zombie);
			add('B', MonsterType.zombie);
			add('C', MonsterType.zombie);
			
			add('C', ItemType.helmet);
			add('D', ItemType.mail_armour);
			add('E', ItemType.sword);
			add('Q', ItemType.knife);
			
			add('T', new Event("Some text here! Only happens once!"));
			add('V', new Event("Some text here! Only happens once!"));
	    	map = ""
	    		
	   		   // This represents the maximum width of a map!	    		
	   		   // "...................................."
	    	
	        	+ "++++++++++++++++++++++++++++++++++++" + "\n"
	    		+ "++++++++++++++++++++++++++++++++++++" + "\n"
	    		+ "     %    %%     ==      %%         " + "\n"
	    		+ " ╔═#═┼═══╗ % ╔═══╦┼═╦═══╗T%V    C   " + "\n"
	    		+ " ║≈≈≈≈≈≈≈║ % ║Θ=π║==║Θ=π║ T%V  Q  D " + "\n"
	    		+ " #≈≈≈≈≈≈≈║ % #A==┼==┼=A=#  T%V   E  " + "\n"
	    		+ " ║≈≈≈≈≈≈≈║ % ║===║==║===║  T%V      " + "\n"
	    		+ " #≈≈≈≈≈≈≈║ % ╠═══╣==╠═══╣═══┼═══╗" + "\n"
	    		+ " ║≈≈≈≈≈≈≈║ % ║Θ=π║==║Θ=π║≈≈≈≈≈≈≈║" + "\n"
	    		+ " #≈≈≈≈≈≈≈║ % #===┼==┼===#≈≈≈≈≈≈≈║" + "\n"
	    		+ " ║≈≈≈≈≈≈≈║ % ║===║==║=A=║≈≈≈≈≈≈≈║" + "\n"
	    		+ " ╚═══════╝ % ╠═══╣==╠═══╣≈≈≈≈≈≈≈║" + "\n"
	    		+ "  C       %  ║Θ=π║==║Θ=π║≈≈≈≈≈≈≈║" + "\n"
	    		+ "     %%%%%   #A==┼==┼===#≈≈≈≈≈≈≈║" + "\n"
	    		+ "   %%        ║===║==║===║≈≈≈≈≈≈≈║" + "\n"
	    		+ "  %     C    ╠═══╣==╠═══╣≈≈≈≈≈≈≈║" + "\n"
	    		+ " ╔#══┼═══╗   ║Θ=π║==║Θ=π║≈≈≈≈≈≈≈║" + "\n"
	    		+ " ║≈≈≈≈≈≈≈║   #===┼==┼===#≈≈≈≈≈≈≈║" + "\n"
	    		+ " ║≈≈≈≈≈≈≈║   ║===║==║=A=║≈≈≈≈≈≈≈║" + "\n"
	    		+ " ║≈≈≈≈≈≈≈║   ╠═══╝==╚═══╣≈≈≈≈≈≈≈║" + "\n"
	    		+ " ║≈≈≈≈≈≈≈║   ║○==┼=====>║≈≈≈≈≈≈≈║" + "\n"
	    		+ " ╚═══════╝   ╚════##════╝═══════╝" + "\n";

		default:
			break;

		}
		
		return(map);
	}
	
	// Sets the player's starting position!
	private static void SetStart(int x, int y) {
		Game.map.startx = x;
		Game.map.starty = y;
	}


	public static void add(Character glyph, MonsterType monster) {
		Game.map.MonMap.put(glyph, monster);
	}
	
	public static void add(Character glyph, ItemType item) {
		Game.map.ItemMap.put(glyph, item);
	}
	
	public static void add(Character glyph, Event event) {
		event.TriggerGlyph = glyph;
		Game.map.EventMap.put(glyph, event);
	}
	
	public static void add(Character glyph, Event event, boolean onlyonce) {
		event.FireOnce = onlyonce;
		event.TriggerGlyph = glyph;
		Game.map.EventMap.put(glyph, event);
	}
}
