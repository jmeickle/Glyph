package info.eronarn.glyph.map;

public class Pair {
	private int X;
	private int Y;
	
	// Constructors
	public Pair (int x, int y) {
		this.X = x;
		this.Y = y;
	}

	public Pair (Pair pair) {
		this.X = pair.X();
		this.Y = pair.Y();
	}
	
	// Get X/Y
	public int X() {
		return X;
	}

	public int Y() {
		return Y;
	}
	
	// Set X/Y
	public void X(int x) {
		X = x;
	}

	public void Y(int x) {
		X = x;
	}
	public boolean equals(Pair other) {
		if (this.X() == other.X() && this.Y() == other.Y())
			return true;
		else
			return false;
	}

}
