package info.eronarn.glyph.ui;

import info.eronarn.glyph.Main;
import info.eronarn.glyph.actor.Player;
import info.eronarn.glyph.game.Game;
import info.eronarn.glyph.game.EnumData.Command;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class Keys {
	public final static KeyAdapter action = new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_NUMPAD1:  Player.Move (-1,1); break;
                case KeyEvent.VK_NUMPAD2:  Player.Move (0,1); break;
                case KeyEvent.VK_NUMPAD3:  Player.Move (1,1); break;
                case KeyEvent.VK_NUMPAD4:  Player.Move (-1,0); break;
                case KeyEvent.VK_NUMPAD5:  Player.Move (0,0); break;
                case KeyEvent.VK_NUMPAD6:  Player.Move (1, 0); break;
                case KeyEvent.VK_NUMPAD7:  Player.Move (-1,-1); break;
                case KeyEvent.VK_NUMPAD8:  Player.Move (0,-1); break;
                case KeyEvent.VK_NUMPAD9:  Player.Move (1,-1); break;
                // These are duplicates of the above:
                case KeyEvent.VK_LEFT:  Player.Move (-1,0); break;
                case KeyEvent.VK_RIGHT: Player.Move (1,0);  break;
                case KeyEvent.VK_UP:    Player.Move (0,-1); break;
                case KeyEvent.VK_DOWN:  Player.Move (0,1);  break;
                case KeyEvent.VK_PERIOD:  Player.Move (0,0); break;
                // Item-y commands.
                case KeyEvent.VK_D: Player.Extended(Command.drop); break;
                case KeyEvent.VK_G: // fallthrough
                case KeyEvent.VK_COMMA: Player.Get(); break;
                case KeyEvent.VK_E: Player.Extended(Command.equip); break;
                case KeyEvent.VK_U: Player.Extended(Command.unequip); break;
            }
            if (Game.ready) {
            	Game.ready = false;
                Main.w.removeKeyListener(action);
                Game.RunQueue();
            }
        }

		};
	
	public final static KeyAdapter letter = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				Main.w.removeKeyListener(letter);
				Player.Extended(Command.abort);
				Main.w.addKeyListener(action);
			} else if (Player.Extended(e.getKeyChar())) {
				Main.w.removeKeyListener(letter);
				Player.Extended(Command.done);
				Main.w.addKeyListener(action);
			}

			if (Game.ready) {
				Game.ready = false;
				Main.w.removeKeyListener(action);
				Game.RunQueue();
			}
		}

	};

	public static void GetCommand() {
		Main.w.addKeyListener(action);
	}

	public static void GetLetter() {
		Main.w.removeKeyListener(action);
		Main.w.addKeyListener(letter);
	}
}
