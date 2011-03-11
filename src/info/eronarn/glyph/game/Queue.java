package info.eronarn.glyph.game;

public class Queue {
	int current;
	int size;
	int[] queue;

	// Constructor. Makes an int array of length.
	public Queue(int length) {
		current = 0;
		size = length;
		queue = new int[size];
	}
	
	// Make sure the player gets to go first!
	public void PlayerFirst() {
		for (int i = 0; i < size; i++) {
			if (queue[i] == Game.playerindex)
				current = i;
		}
	}
	
	// Gets the next queue index.
	public int Next() {
		for (int i = current; i < size; i++) {
			current++;
			if (queue[i] != 0)
				return queue[i];
		}
		current = 0;
		return Next();
	}
	
	// Adds something to the queue.
	public boolean Insert(int Index) {
		for (int i = 0; i < size; i++) {
			if (queue[i] == 0) {
				queue[i] = Index;
			    return true;
			}
		}
		return false;
	}
	
	// Removes something from the queue.
	public boolean Remove(int Index) {
		for (int i = 0; i < size; i++) {
			if (queue[i] == Index) {
				queue[i] = 0;
			    return true;
			}
		}
		return false;
	}
}
