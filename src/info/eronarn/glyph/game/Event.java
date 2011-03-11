package info.eronarn.glyph.game;

import info.eronarn.glyph.ui.UI;

public class Event {

	public Character TriggerGlyph; // What glyph was this defined for?
	public String EventText; // What text should be displayed?
	public boolean FireOnce = true; // Should this event only fire once globally?
	
	public Event(String string) {
		EventText = string;	
	}
	
	public Event(String string, boolean bool) {
		EventText = string;	
		FireOnce = bool;
	}

	public void Fire() {
		UI.Print(EventText);
		if (FireOnce)
			Game.map.EventMap.remove(TriggerGlyph);
	}
}
