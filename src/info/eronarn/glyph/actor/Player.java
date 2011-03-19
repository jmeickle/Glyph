package info.eronarn.glyph.actor;

import javax.swing.tree.DefaultMutableTreeNode;

import info.eronarn.glyph.Main;
import info.eronarn.glyph.game.EnumData;
import info.eronarn.glyph.game.Game;
import info.eronarn.glyph.game.EnumData.Command;
import info.eronarn.glyph.game.EnumData.Slot;
import info.eronarn.glyph.item.Item;
import info.eronarn.glyph.ui.Keys;
import info.eronarn.glyph.ui.UI;

// Contains functions where the player has to be treated differently than a monster.

public abstract class Player {
	
	public static Command extended;

	// Returns true because it says whether a command has been done.
	public static boolean Extended(char letter) {
		boolean success = false;
		switch (Player.extended) {
		case drop:
			success = Drop(LetterToIndex(letter));
			break;
		case equip:
			success = Equip(LetterToIndex(letter));
			break;
		case unequip:
			success = Unequip(LetterToIndex(letter));
			break;
		default:
			break;
		}
		return success;
	}

	public static void Extended(EnumData.Command Command) {
		Player.extended = Command;
		switch (Player.extended) {
		case drop:
			UI.Print("Drop which item? [a-z, Esc]");
			break;
		case equip:
			UI.Print("Put on which item? [a-z, Esc]");
			break;
		case unequip:
			UI.Print("Take off which item? [a-z, Esc]");
			break;
		case abort:
			UI.Print("Nevermind, then.");
			break;
		default:
			break;
		}
		
		if (!Command.equals(EnumData.Command.abort) && !Command.equals(EnumData.Command.done))
			Keys.GetLetter();
	}
	
    public static void Move(int dx, int dy) {
    	if (Game.player.Move(dx, dy))
    		Game.ready = true;
    }
    
    public static boolean Drop(int which) {
    	Item item = Game.player.Inventory.get(which);
    	if (Game.player.CanDrop(which)) {
    		UI.Print("You drop a " + item.Name + ".");
    		Game.player.Drop(which);
    		ChangedInventory();
    		Game.ready = true;
    		return true;
    	}
    	else if (item == null)
    		UI.Print("You don't have that item.");
    	else if (Game.player.HasInSlot(which, item.SlotUsed))
    		UI.Print("You can't drop equipped items.");
    	else
    		UI.Print("You can't drop anything here.");
    	
   		return false;
    }
    
    public static boolean Get() {
    	if (Game.player.CanGet()) {
    		Item item = Game.player.Get();
    		UI.Print("You pick up a " + item.Name + ".");
    		ChangedInventory();
    		Game.ready = true;
    		return true;
    	}
    	else if (!Game.player.InventoryRoom())
    		UI.Print("You can't carry anything.");
    	else
    		UI.Print("There's nothing here.");
    	
   		return false;
    }

    public static boolean Equip(int which) {
    	Item item = Game.player.Inventory.get(which);
    	if (Game.player.CanEquip(which)) {
    		Game.player.Equip(which);
    		UI.Print("You put on a " + item.Name + ".");
    		ChangedEquipment();
    		ChangedInventory();
    		Game.ready = true;
    		return true;
    	}
    	else if (item == null)
    		UI.Print("You don't have that item.");
    	else if (Game.player.HasInSlot(which, item.SlotUsed))
    		UI.Print("That item is already equipped.");
    	else
    		UI.Print("You're already using that slot.");
    	
   		return false;
    }
    
    public static boolean Unequip(int index) {    	
    	Item item = Game.player.Inventory.get(index);
    	if (Game.player.CanUnequip(index)) {
    		Game.player.Unequip(index);
    		UI.Print("You take off a " + item.Name + ".");
    		ChangedEquipment();
    		ChangedInventory();
    		Game.ready = true;
    		return true;
    	}
    	else if (item == null)
    		UI.Print("You don't have that item.");
    	else
    		UI.Print("That item isn't equipped.");
    	
   		return false;
    }

    // Stub in case more stuff needs to be checked here later.
	public static void ChangedEquipment() {
		RedrawEquipment();
	}
	
    // Stub in case more stuff needs to be checked here later.
	public static void ChangedInventory() {
		RedrawInventory();
	}
	
	public static void RedrawHealthbar() {
		Main.u.healthbar.setMaximum(Game.player.MaxHP);
		Main.u.healthbar.setValue(Game.player.HP);
	}
	
	public static void RedrawEquipment() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) Main.u.gear.getModel().getRoot();
		
		DefaultMutableTreeNode Equipment = (DefaultMutableTreeNode) root.getChildAt(0);
		Equipment.removeAllChildren();
		
		for (EnumData.Slot slot : EnumData.Slot.values()) {
			Integer index = Game.player.Equipment.get(slot);
			if (index != null) {
				Item item = Game.player.Inventory.get(index);
				Equipment.insert(new DefaultMutableTreeNode(slot.toString().replace('_', ' ') + ": " + Player.IndexToLetter(index) + " - " + item.Name), Equipment.getChildCount());
			}
			else
				Equipment.insert(new DefaultMutableTreeNode(slot.toString().replace('_', ' ') + ": nothing"), Equipment.getChildCount());
		}
		
		Main.u.gear.updateUI();
	}
		
	public static void RedrawInventory() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) Main.u.gear.getModel().getRoot();
		
		DefaultMutableTreeNode Inventory = (DefaultMutableTreeNode) root.getChildAt(1);
		Inventory.removeAllChildren();
		
		for (int i = 0; i < 26; i++) {
			Item item = Game.player.Inventory.get(i);
			if (item != null) {
				if (Game.player.HasInSlot(i, item.SlotUsed))
					Inventory.insert(new DefaultMutableTreeNode(Player.IndexToLetter(i) + " - " + item.Name + " (worn)"), Inventory.getChildCount());
				else
					Inventory.insert(new DefaultMutableTreeNode(Player.IndexToLetter(i) + " - " + item.Name), Inventory.getChildCount());
			}
		}
		
		Main.u.gear.updateUI();
	}

	public static char IndexToLetter(int i) {
		return (char) (i + 97);
	}
	
	private static int LetterToIndex(char letter) {
		return letter - 97;
	}
	
	public static int GetInventoryIndex(Item item) {
		for (int i = 0; i < 26; i++) {
			if (Game.player.Inventory.get(i).equals(item))
					return i;
		}
		return -1;
	}
	
	public static Slot GetInventorySlot(int index) {
		Item item = Game.player.Inventory.get(index);
		if (item != null)
			return item.SlotUsed;
		else
			return null;
	}
	
	public static void Die() {
		System.err.println("Error: it wasn't supposed to end like this! I was supposed to survive...");
		System.exit(0);
	}

}
