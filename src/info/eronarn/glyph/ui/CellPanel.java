package info.eronarn.glyph.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

import javax.swing.JPanel;

public class CellPanel extends JPanel {

	int height = 16;
	int width = 16;
	Character Glyph;
	
	// Font used for the viewport.
    public static Font font = DemoFonts.getFont("Aesomatica_16x16.ttf").deriveFont((float) 16);
    
    private static final Color lightGreen = new Color( 0x30ffaf );
    /* transparent white */
    private static final Color transparent = new Color( 0x00ffffff, true );
    private static final Color transparent2 = new Color(255, 255, 255, 0);
    
	public CellPanel() {
	}

	public void setGlyph (Character glyph) {
		Glyph = glyph;
	}
	
    public void paintComponent( Graphics g )
    {
    super.paintComponent( g );
    Graphics2D g2d = ( Graphics2D ) g;
    // If you plan to change the clip region or AffineTransform,
    // use Graphics2D g2d = ( Graphics2D ) g.create(); instead
    // to avoid permanently modifying the caller's Graphics2D object.
    g2d.addRenderingHints( new RenderingHints( RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON ) );
    g2d.setBackground(Color.black);
    g2d.clearRect( 0, 0, width, height );
    g2d.setFont( font );
    String text = Character.toString(Glyph);
    g2d.setColor( Color.white );
    drawCenteredString( g2d, text, width / 2, height / 2 );
    }
    
    private static void drawCenteredString( final Graphics2D g2d, final String text, final int x, final int y )
    {
    final Font font = g2d.getFont();
    int width = g2d.getFontMetrics().stringWidth( text );
    final FontRenderContext fr = g2d.getFontRenderContext();
    final LineMetrics lm = font.getLineMetrics( text, fr );
    final int height = ( int ) ( lm.getAscent() * .76 + .5 )/* compensate for overstating */;
    // x,y is bottom left corner of text
    g2d.drawString( text, x - width / 2, y + height / 2 );
    }

}
