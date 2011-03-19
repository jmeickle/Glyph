package info.eronarn.glyph.ui;

import info.eronarn.glyph.Main;
import info.eronarn.glyph.game.Game;

import java.awt.Adjustable;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class UI extends JPanel {
	
	public final static int xmax = 36; // Max X of the viewport.
	public final static int ymax = 36; // Max Y of the viewport.
	    
	// These areas are exposed to make it easier to do stuff with them.
	// They're indented according to their tree structure.
	
	public JPanel fakepanel; // Main panel, where the tiles are placed.
	public JPanel mainpanel; // Main panel, where the tiles are placed.
    	public Canvas canvas; // Canvas, for drawing on.
	public JPanel sidepanel; // The entire sidebar, split into three sections.
		public JPanel toparea; // Top part of the sidebar.
			public JLabel namelabel; // Character's name.
			public JProgressBar healthbar; // Health bar.
		public JTabbedPane tabs; // The middle, tabbed section.
			public JScrollPane scrollPane; // Container for the event log.
				public JTextPane textPane; // Event log.
			public JTree glyphs; // Glyphs.
			public JTree gear; // Equipment and inventory.
		public JPanel bottomarea; // Bottom section, where buttons will be placed as needed.
	
	// Font used for the viewport.
	public static Font font = DemoFonts.getFont("Aesomatica_16x16.ttf").deriveFont((float) 16);
	private static final Color lightGreen = new Color( 0x30ffaf );
	private static final Color transparent = new Color( 0x00ffffff, true ); 	/* transparent white */
	public static final Color transparent2 = new Color(255, 255, 255, 0);
	    
	    
	/**
	 * Constructor - this'll create everything in the layout.
	 */
	public UI() {
		setPreferredSize(new Dimension(800, 600));
		setSize(new Dimension(800, 600));
		setBorder(null);
		setFocusable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{580, 186};
		gridBagLayout.rowHeights = new int[]{580};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0};
		setLayout(gridBagLayout);
		
		mainpanel = new JPanel();
		mainpanel.setPreferredSize(new Dimension(580, 580));
		mainpanel.setFocusable(false);
		mainpanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		GridBagConstraints gbc_mainpanel = new GridBagConstraints();
		gbc_mainpanel.anchor = GridBagConstraints.WEST;
		gbc_mainpanel.gridx = 0;
		gbc_mainpanel.gridy = 1;
//	    add(mainpanel, gbc_mainpanel);
		GridBagLayout gbl_mainpanel = new GridBagLayout();
		gbl_mainpanel.columnWidths = new int[xmax];
		gbl_mainpanel.rowHeights = new int[ymax];
		Arrays.fill(gbl_mainpanel.columnWidths, 16);
		Arrays.fill(gbl_mainpanel.rowHeights, 16);
		mainpanel.setLayout(gbl_mainpanel);
		
		fakepanel = new JPanel();
		fakepanel.setPreferredSize(new Dimension(580, 580));
		fakepanel.setFocusable(false);
		fakepanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		GridBagConstraints gbc_fakepanel = new GridBagConstraints();
		gbc_fakepanel.anchor = GridBagConstraints.WEST;
		gbc_fakepanel.gridx = 0;
		gbc_fakepanel.gridy = 0;
	    add(fakepanel, gbc_fakepanel);
		GridBagLayout gbl_fakepanel = new GridBagLayout();
		gbl_fakepanel.columnWidths = new int[]{576};
		gbl_fakepanel.rowHeights = new int[]{576};
		fakepanel.setLayout(gbl_fakepanel);
		
		canvas = new Canvas();
		canvas.setBackground(Color.black);
		canvas.setPreferredSize(new Dimension(576, 576));
		canvas.setFocusable(false);
		GridBagConstraints gbc_canvas = new GridBagConstraints();
		gbc_canvas.anchor = GridBagConstraints.WEST;
		gbc_canvas.gridx = 0;
		gbc_canvas.gridy = 0;
		fakepanel.add(canvas, gbc_canvas);
		
		sidepanel = new JPanel();
		sidepanel.setBorder(null);
		sidepanel.setFocusable(false);
		GridBagConstraints gbc_sidepanel = new GridBagConstraints();
		gbc_sidepanel.gridx = 1;
		gbc_sidepanel.gridy = 0;
		add(sidepanel, gbc_sidepanel);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{186};
		gbl_panel_2.rowHeights = new int[]{137, 274, 137, 137};
		gbl_panel_2.columnWeights = new double[]{0.0};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		sidepanel.setLayout(gbl_panel_2);
		
		toparea = new JPanel();
		toparea.setFocusable(false);
		toparea.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		GridBagConstraints gbc_toparea = new GridBagConstraints();
		gbc_toparea.fill = GridBagConstraints.VERTICAL;
		gbc_toparea.insets = new Insets(10, 10, 10, 0);
		gbc_toparea.anchor = GridBagConstraints.CENTER;
		gbc_toparea.gridx = 0;
		gbc_toparea.gridy = 0;
		sidepanel.add(toparea, gbc_toparea);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{186};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0};
		toparea.setLayout(gbl_panel_1);
		
		namelabel = new JLabel("Glyph");
		GridBagConstraints gbc_namelabel = new GridBagConstraints();
		gbc_namelabel.anchor = GridBagConstraints.NORTH;
		gbc_namelabel.insets = new Insets(0, 0, 5, 0);
		gbc_namelabel.gridx = 0;
		gbc_namelabel.gridy = 0;
		toparea.add(namelabel, gbc_namelabel);
		
		healthbar = new JProgressBar();
		healthbar.setFocusable(false);
		GridBagConstraints gbc_healthbar = new GridBagConstraints();
		gbc_healthbar.insets = new Insets(5, 5, 5, 5);
		gbc_healthbar.fill = GridBagConstraints.HORIZONTAL;
		gbc_healthbar.anchor = GridBagConstraints.NORTH;
		gbc_healthbar.gridx = 0;
		gbc_healthbar.gridy = 1;
		toparea.add(healthbar, gbc_healthbar);
		
		tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.setFocusable(false);
		tabs.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		GridBagConstraints gbc_tabs = new GridBagConstraints();
		gbc_tabs.insets = new Insets(0, 10, 10, 0);
		gbc_tabs.anchor = GridBagConstraints.CENTER;
		gbc_tabs.fill = GridBagConstraints.BOTH;
		gbc_tabs.gridx = 0;
		gbc_tabs.gridy = 1;
		sidepanel.add(tabs, gbc_tabs);
		tabs.setName("");
		
		scrollPane = new JScrollPane();
		scrollPane.setFocusable(false);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setAutoscrolls(true);
		tabs.addTab("Log", null, scrollPane, null);
		
		textPane = new JTextPane();
		textPane.setFocusable(false);
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		glyphs = new JTree();
		glyphs.setFocusable(false);
		glyphs.setRootVisible(false);
		tabs.addTab("Glyphs", null, glyphs, null);
		glyphs.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Glyphs") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("colors");
						node_1.add(new DefaultMutableTreeNode("blue"));
						node_1.add(new DefaultMutableTreeNode("violet"));
						node_1.add(new DefaultMutableTreeNode("red"));
						node_1.add(new DefaultMutableTreeNode("yellow"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("sports");
						node_1.add(new DefaultMutableTreeNode("basketball"));
						node_1.add(new DefaultMutableTreeNode("soccer"));
						node_1.add(new DefaultMutableTreeNode("football"));
						node_1.add(new DefaultMutableTreeNode("hockey"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("food");
						node_1.add(new DefaultMutableTreeNode("hot dogs"));
						node_1.add(new DefaultMutableTreeNode("pizza"));
						node_1.add(new DefaultMutableTreeNode("ravioli"));
						node_1.add(new DefaultMutableTreeNode("bananas"));
					add(node_1);
				}
			}
		));
		
		gear = new JTree();
		gear.setFocusable(false);
		gear.setRootVisible(false);
		gear.setShowsRootHandles(true);
		tabs.addTab("Gear", null, gear, null);
		gear.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Gear") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("Equipment");
					insert(node_1, 0);
					node_1 = new DefaultMutableTreeNode("Inventory");
					insert(node_1, 1);
				}
			}
		));
		
		bottomarea = new JPanel();
		bottomarea.setFocusable(false);
		bottomarea.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		GridBagConstraints gbc_bottomarea = new GridBagConstraints();
		gbc_bottomarea.insets = new Insets(0, 10, 10, 0);
		gbc_bottomarea.fill = GridBagConstraints.BOTH;
		gbc_bottomarea.gridx = 0;
		gbc_bottomarea.gridy = 2;
		sidepanel.add(bottomarea, gbc_bottomarea);
		
		JButton btnFillerLol = new JButton("FILLER LOL");
		btnFillerLol.setFocusable(false);
		bottomarea.add(btnFillerLol);
	}
	
	// This is broken out mostly for legibility.
    public void initMap() {
    	
		canvas.createBufferStrategy(2);
		
    	// We make one cell for each x,y of the viewport:
    	for (int CurrY = 0; CurrY < ymax; CurrY++) {
        	
        	for (int CurrX = 0; CurrX < xmax; CurrX++) {
        		
        		CellPanel charcell = new CellPanel(CurrX, CurrY);
    
        		// Change some settings.
//        		charcell.setDoubleBuffered(true);
//        		charcell.setVisible(false);
        		charcell.setOpaque(true);
        		charcell.setFocusable(false);
    	        charcell.setBorder(BorderFactory.createEmptyBorder());
    	        
    	        // Create the layout constraints.
    			GridBagConstraints gbc_charcell = new GridBagConstraints();
    			gbc_charcell.insets = new Insets(0, 0, 0, 0);
    			gbc_charcell.gridx = CurrX;
    			gbc_charcell.gridy = CurrY;
    			gbc_charcell.fill = GridBagConstraints.BOTH;
    			gbc_charcell.anchor = GridBagConstraints.WEST;
    			
    			// Add it to the main panel.
    			mainpanel.add(charcell, gbc_charcell);
        		}
        	}   	
    }
    
	// If no second argument, add a newline.
	public static void Print(String Text) {
		Print(Text, true);
	}
	
	// Suppresses new lines if the second parameter is false.
	public static void Print(String Text, boolean newline) {
		
		if (newline)
			Text = Text + Main.ls;
		
    	StyledDocument textdoc = Main.u.textPane.getStyledDocument();
        try {
        	textdoc.insertString(textdoc.getLength(), Text, textdoc.getStyle(StyleContext.DEFAULT_STYLE));
            }
        catch (BadLocationException ble) {
	        	System.err.println("Error inserting into text pane!");
	        	}

        SwingUtilities.invokeLater( new Runnable()
        {
        public void run()
           {
           // Make sure the first line of the scroll region is visible.
            Adjustable sb = Main.u.scrollPane.getVerticalScrollBar();
    		sb.setValue(sb.getMaximum());
           }
        } );
	}

	// Prints a message, but only if the caller isn't the player.
	public static void MonPrint(int index, String Text) {
		if (index != Game.playerindex)
			UI.Print(Text);
	}
}
