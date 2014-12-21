/*
 * 
 */
package br.iesb.VIS2048.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

// TODO: Auto-generated Javadoc
/**
 * The Class AngledLinesWindowsCornerIcon.
 */
public class AngledLinesWindowsCornerIcon implements Icon {

	/** The Constant WHITE_LINE_COLOR. */
	private static final Color WHITE_LINE_COLOR = new Color(255, 255, 255);
	
	/** The Constant GRAY_LINE_COLOR. */
	private static final Color GRAY_LINE_COLOR = new Color(172, 168, 153);
	
	/** The Constant WIDTH. */
	private static final int WIDTH = 13;
	
	/** The Constant HEIGHT. */
	private static final int HEIGHT = 13;
	
	/* (non-Javadoc)
	 * @see javax.swing.Icon#getIconWidth()
	 */
	@Override
	public int getIconWidth() {
		return WIDTH;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Icon#getIconHeight()
	 */
	@Override
	public int getIconHeight() {
		return HEIGHT;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(WHITE_LINE_COLOR);
		g.drawLine(0, 12, 12, 0);
		g.drawLine(5, 12, 12, 5);
		g.drawLine(10, 12, 12, 10);
		
		g.setColor(GRAY_LINE_COLOR);
		g.drawLine(1, 12, 12, 1);
		g.drawLine(2, 12, 12, 2);
		g.drawLine(3, 12, 12, 3);
		
		g.drawLine(6, 12, 12, 6);
		g.drawLine(7, 12, 12, 7);
		g.drawLine(8, 12, 12, 8);
		
		g.drawLine(11, 12, 12, 11);
		g.drawLine(12, 12, 12, 12);
	}
	
}
