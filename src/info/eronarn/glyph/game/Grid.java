package info.eronarn.glyph.game;

import info.eronarn.glyph.map.Pair;

public class Grid {
	public GridEntry[][] grid;

	public Grid(int xmax, int ymax) {
		grid = new GridEntry[xmax][ymax];
		
    	for (int CurrY = 0; CurrY < ymax; CurrY++) {
    		
        	for (int CurrX = 0; CurrX < xmax; CurrX++) {
    		    grid[CurrX][CurrY] = new GridEntry();  
        	}
    	}

	}
	
	// Sets the grid contents.
	public void Contents(Pair coord, int index) {
		grid[coord.X()][coord.Y()] = new GridEntry(index);
	}
	
	// Gets the grid contents from a coord.
	public int Contents(Pair coord) {
		return grid[coord.X()][coord.Y()].index;
	}
	
	// GridEntry class.
	public class GridEntry {
		public int index = 0; // Index stored in it.

		// Constructor.
		public GridEntry() {

		}
		
		public GridEntry(int NewIndex) {
			index = NewIndex;
		}
	}

}
