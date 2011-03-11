package info.eronarn.glyph.actor;

import javax.swing.tree.DefaultMutableTreeNode;

import info.eronarn.glyph.Main;
import info.eronarn.glyph.game.Game;
import info.eronarn.glyph.game.EnumData.Slot;
import info.eronarn.glyph.item.Item;
import info.eronarn.glyph.ui.UI;

// Contains functions where the player has to be treated differently than a monster.

public abstract class Player {
	
    public static void Move(int dx, int dy) {
    	if (Game.player.Move(dx, dy))
    		Game.ready = true;
    }
    
    public static void Drop() {
    	if (Game.player.CanDrop()) {
        	Item item = Game.player.Inventory.get('a');
    		Player.RedrawInventory(item, true);
    		Game.player.Drop('a');
    		UI.Print("You now have: " + Game.player.Inventory.toString());
    		Game.ready = true;
    	}
    }
    
    public static void Get() {
    	if (Game.player.CanGet()) {
    		Item item = Game.player.Get();
    		Player.RedrawInventory(item, false);
    		UI.Print("You now have: " + Game.player.Inventory.toString());
    		Game.ready = true;
    	}
    }

    public static void Equip() {
    	Item item = Game.player.Inventory.get('a');
    	if (Game.player.CanEquip('a')) {
    		Player.RedrawInventory(item, true);
    		Game.player.Equip('a');
    		Player.RedrawEquipment(item, false);
    		UI.Print("You put on: " + item.Name);
    		UI.Print("You are now wearing: " + Game.player.Equipment.toString());
    		Game.ready = true;
    	}
    }
    
    public static void Unequip() {
    	Item item = Game.player.Equipment.get(Slot.RightHand);
    	if (Game.player.CanUnequip(Slot.RightHand)) {
    		Player.RedrawEquipment(item, true);
    		Game.player.Unequip(Slot.RightHand);
    		Player.RedrawInventory(item, false);
    		UI.Print("You take off: " + item.Name);
    		UI.Print("You are now wearing: " + Game.player.Equipment.toString());
    		Game.ready = true;
    	}
    }

	public static void RedrawHealthbar() {
		Main.u.healthbar.setMaximum(Game.player.MaxHP);
		Main.u.healthbar.setValue(Game.player.HP);
	}
	
	public static void RedrawEquipment(Item item, boolean remove) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) Main.u.gear.getModel().getRoot();
		DefaultMutableTreeNode Equipment = (DefaultMutableTreeNode) root.getChildAt(0);
		if (remove)
			Equipment.remove(item.SlotUsed.ordinal());
		else
			Equipment.insert(new DefaultMutableTreeNode(item.SlotUsed.toString() + " - " + item.Name), item.SlotUsed.ordinal());
	}
	
	public static void RedrawInventory(Item item, boolean remove) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) Main.u.gear.getModel().getRoot();
		DefaultMutableTreeNode Inventory = (DefaultMutableTreeNode) root.getChildAt(1);
		if (remove)
			Inventory.remove(Player.GetInventoryNumber(item));
		else
			Inventory.insert(new DefaultMutableTreeNode(Player.GetInventoryLetter(item) + " - " + item.Name), Player.GetInventoryNumber(item));
	}

	public static int GetInventoryNumber(Item item) {
		for (int i = 0; i < 26; i++) {
			if (Game.player.Inventory.get((char) ((i + 97))).equals(item))
					return i;
		}
		return 0;
	}
	
	public static char GetInventoryLetter(Item item) {
		for (int i = 0; i < 26; i++) {
			if (Game.player.Inventory.get((char) ((i + 97))).equals(item))
					return (char) (i + 97);
		}
		return '0';
	}
	
	public static void Die() {
		System.err.println("Error: it wasn't supposed to end like this! I was supposed to survive...");
	}

}
