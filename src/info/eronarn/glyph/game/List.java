package info.eronarn.glyph.game;

import info.eronarn.glyph.actor.Monster;

public class List {
	
	
	static int Counter; // Shared across all lists, and therefore unique.
	public ListItem list[]; // A list is an index of ListItems.
	
	// Default constructor.
	public List() {
		this(50);
	}

	public List(int size) {
		list = new ListItem[size];
		for (int i=0; i<list.length; i++) {  
		    list[i] = new ListItem();  
		}  
	}
	
	// An entry into a list. Can be any object.
	public class ListItem {
		Object listitem;
		
		public ListItem() {
			listitem = null;
			}
		
		public ListItem(Object object) {
		    listitem = object;
		}

		public ListItem(Monster monster) {
			listitem = monster;
        }
		
		// Return the object this entry contains.
		public Object Contains() {
			return listitem;
		}
	
	}
	
	// Increment Index by one and then return it.
	public int NextIndex() {
		Counter++;
		return Counter;
	}
	
	public int SaveToList(Object object) {
		int index = NextIndex();
		list[index] = new ListItem(object);
		return index;
	}
	
	public Object LoadFromList(int index) {
		return list[index].Contains();
	}
}
