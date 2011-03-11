package info.eronarn.glyph.game;

abstract public class EnumData {
	
	public enum Level {
		tutorial,
		hotel,
		endgame, 
	}
	
	public enum DataType {
		monster,
		feature,
		item,
	}
	
	public enum Slot {
	    LeftHand,
	    RightHand,
	    Head,
	    Neck,
	    Body,
	    Cloak,
	    Hands,
	    LeftRing,
	    RightRing,
	    Feet,
	}
	
	public enum Stat {
		Protection,
		Evasion,
		Accuracy,
	    Damage,
	}
	public enum Material {
		// Organic
		min_organic,
		bone,
		leather,
		wood,
		max_organic,
		stone,
		// Metal
		min_metal,
		iron,
		steel,
		bronze,
		brass,
		silver,
		gold,
		platinum,
		max_metal,	
	}
	
	public static boolean IsOrganic(Material type) {
		return (type.ordinal() > Material.min_organic.ordinal() && type.ordinal() < Material.max_organic.ordinal());
	}
	
	public static boolean IsMetal(Material type) {
		return (type.ordinal() > Material.min_metal.ordinal() && type.ordinal() < Material.max_metal.ordinal());
	}
	
	public enum ItemType {
		min_weapon,
		axe,
		knife,
		spear,
		staff,
		sword,
		min_fake,
		hand,
		claw,
		teeth,
		tentacle,
		max_fake,
		max_weapon,
		min_armour,
		min_clothes,
		hat,
		cloak,
		gloves,
		shoes,
		boots,
		robe,
		vest,
		tunic,
		max_clothes,
		helmet,
    	gauntlets,
    	sollerets,
		leather_armour,
		mail_armour,
		plate_armour,
		max_armour,
	}
	
	public static boolean IsArmour(ItemType type) {
		return (type.ordinal() > ItemType.min_armour.ordinal() && type.ordinal() < ItemType.max_armour.ordinal());
	}
	
	public static boolean IsClothing(ItemType type) {
		return (type.ordinal() > ItemType.min_clothes.ordinal() && type.ordinal() < ItemType.max_clothes.ordinal());
	}
	
	public static boolean IsWeapon(ItemType type) {
		return (type.ordinal() > ItemType.min_weapon.ordinal() && type.ordinal() < ItemType.max_weapon.ordinal());
	}
	
	public static boolean IsFakeWeapon(ItemType type) {
		return (type.ordinal() > ItemType.min_fake.ordinal() && type.ordinal() < ItemType.max_fake.ordinal());
	}
	
	public enum DamageType {
		bludgeoning,
		chopping,
		piercing,
		slashing,
		stabbing,
		bleeding,
		poison,
		hot,
		cold,
		shock,
	}
	
	public enum MonsterType {
		min_human,
		player,
		beggar,
		citizen,
		knave,
		thug,
		scoundrel,
		blackguard,
		slumlord,
		police,
		guard,
		max_human,
		zombie
	}
	
	public static boolean IsHuman(MonsterType type) {
		return (type.ordinal() > MonsterType.min_human.ordinal() && type.ordinal() < MonsterType.max_human.ordinal());
	}
	

}
