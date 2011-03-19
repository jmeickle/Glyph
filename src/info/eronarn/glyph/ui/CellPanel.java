package info.eronarn.glyph.ui;

import info.eronarn.glyph.game.Game;
import info.eronarn.glyph.map.Cell;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.RenderingHints;
import java.awt.Shape;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;

import javax.swing.JPanel;

public class CellPanel extends JPanel {

	public int x;
	public int y;
	
	public int xpix;
	public int ypix;
	int height = 16;
	int width = 16;
	public Cell Cell;
	public boolean Redraw = true;
	
    public CellPanel(int x, int y) {
    	this.x = x;
    	this.y = y;
    	
		xpix = this.x*width;
		ypix = this.y*height;
		
		
		Cell = Game.map.cell[x][y];
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
    g2d.setFont( UI.font );
    String text = Character.toString(Cell.FloorGlyph);
    g2d.setColor( Color.white );
    drawCenteredString( g2d, text, width / 2, height / 2 );
    Redraw = false;
    }
    
    private static void drawCenteredString( final Graphics2D g2d, final String text, final int xpix, final int ypix )
    {
    		
    final Font font = g2d.getFont();
    int width = g2d.getFontMetrics().stringWidth( text );
    final FontRenderContext fr = g2d.getFontRenderContext();

    GlyphVector vector = UI.font.createGlyphVector(fr, text);
   
    Shape shape = vector.getGlyphOutline(0, xpix-8, ypix+4);
    g2d.setStroke(new BasicStroke((float) 2.5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    g2d.setColor(new Color(0, 0, 0, 200));

    g2d.draw(shape);
    
    g2d.setColor(Color.white);
    g2d.setStroke(new BasicStroke());
    
    final LineMetrics lm = font.getLineMetrics( text, fr );
    final int height = ( int ) ( lm.getAscent() * .76 + .5 )/* compensate for overstating */;
    // x,y is bottom left corner of text
    g2d.drawString( text, xpix - width / 2, ypix + height / 2 );
    }

    public void Redraw(Graphics g) {
	    Graphics2D g2d = ( Graphics2D ) g;
		
	    g2d.addRenderingHints( new RenderingHints( RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON ) );
	    g2d.clearRect( xpix, ypix, width, height );

		DrawFloor(g2d, Cell.FloorGlyph, Color.white, Color.black);

		if (Cell.ItemGlyph != ' ')
			DrawItem(g2d, Cell.ItemGlyph, Color.yellow, UI.transparent2);
		if (Cell.MonsterGlyph != ' ')
			DrawMonster(g2d, Cell.MonsterGlyph, Color.white, UI.transparent2);
    }
    
	public void DrawFloor(Graphics2D g, char glyph, Color color, Color bg) {
		DrawOn(g, Cell.FloorGlyph, color, bg);
	}
	
	public void DrawItem(Graphics2D g, char glyph, Color color, Color bg) {
		DrawOn(g, Cell.ItemGlyph, color, bg);
	}
	
	public void DrawMonster(Graphics2D g, char glyph, Color color, Color bg) {
		DrawOn(g, Cell.MonsterGlyph, color, bg);
	}
	
	public void DrawSpecial(Graphics2D g, char glyph, Color color, Color bg) {
		DrawOn(g, glyph, color, bg);
	}
	
	public void DrawOn(Graphics2D g, char glyph, Color fg, Color bg) {
		
	    Graphics2D g2d = g;
	    
	    g2d.setClip(null);
	    g2d.clipRect(xpix, ypix, width, height);
	    
	    g2d.setColor(bg);
	    g2d.fillRect(xpix, ypix, width, height);
	    
	    g2d.setColor(fg);
	    
	    String text = Character.toString(glyph);
	    g2d.setFont( UI.font );
	    
	    drawCenteredString( g2d, text, xpix+ (width / 2), ypix+(height / 2) );
	}
}
