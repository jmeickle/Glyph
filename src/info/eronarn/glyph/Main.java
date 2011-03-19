/**
 * Got code for this from, at least these, but definitely more:
 * http://www.rgagnon.com/javadetails/java-0305.html
 * http://download.oracle.com/javase/tutorial/uiswing/examples/components/index.html#TextSamplerDemo
 * http://kmkeen.com/df2ttf/
 */
package info.eronarn.glyph;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import info.eronarn.glyph.game.*;
import info.eronarn.glyph.ui.UI;
import javax.swing.*;

import org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel;

/**
 * @author eronarn
 *
 */
public class Main {

	public static JFrame w; // The actual game window.
	public static UI u; // Everything inside the game window.
	public static Game g; // The game - no UI elements here, folks!
	
	// Line separator - it's system dependent.
	public final static String ls = System.getProperty("line.separator");

	public static void main(String[] args) {
		
		// First, let's get our GUI stuff together.
	    try {
		    // Set L&F to Substance Dust. This should be available with
	    	// the libraries that are included...
	        UIManager.setLookAndFeel(new SubstanceDustLookAndFeel());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	       // ^^ LOL YEAH RIGHT. ^^
	    }

	    // Boot up the frame and change some settings.
		w = new JFrame();		
		w.setTitle("Glyph");
		w.setResizable(false);
		w.setFocusable(true);
		w.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
				};
				});
		
		w.setVisible(true); // Display our fancy window!
		
		// Boot up the game. This is where the magic happens.
		g = new Game();
		
		// Boot up the UI - a JPanel stuffed full of goodies.
		u = new UI();

		// Final touches:
		w.add(u); // Add the UI to the window.
		u.initMap(); // Set up the tiles.
		w.setSize(800,620); // add 20 for the window title.
		w.pack(); // Make sure everything fits nicely.
		
		// Launch the game!
		g.InitGame(); 
		}
}