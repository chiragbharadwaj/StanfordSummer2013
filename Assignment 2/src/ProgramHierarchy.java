/*
 * File: ProgramHierarchy.java
 * Name: Chirag Bharadwaj
 * Section Leader: Kristen Carnohan
 * ---------------------------
 * This program graphically displays the complete diagram of the
 * acm.program class hierarchy.
 */

import acm.graphics.*;
import acm.program.*;

// Runs the program. //

public class ProgramHierarchy extends GraphicsProgram {	

	// Private constants. //

	/** Width of boxes in pixels. */
	private static final int BOX_WIDTH = 150;

	/** Height of boxes in pixels. */
	private static final int BOX_HEIGHT = 50;

// Physically creates the acm.program class hierarchy model. //
	
	public void run() {
		createBoxes();
		createLabels();
		addLines();
		addTitle();
	}

/**
 * This method adds boxes (in which the labels will be later placed)
 * centered around the screen depicting the acm.program class
 * hierarchy.
 */
	private void createBoxes() {
		int a = (getWidth() - 3*BOX_WIDTH - 50) / 2;
		int b = (getHeight() - 5*BOX_HEIGHT) / 2;
		
		GRect graphics = new GRect(a, b + 4*BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
		add(graphics);
		
		GRect console = new GRect(a + BOX_WIDTH + 25, b + 4*BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
		add(console);
		
		GRect dialog = new GRect(a + 2*BOX_WIDTH + 50, b + 4*BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
		add(dialog);
		
		GRect program = new GRect(a + BOX_WIDTH + 25, b + 2*BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
		add(program);
		
		GRect applet = new GRect(a + BOX_WIDTH + 25, b, BOX_WIDTH, BOX_HEIGHT);
		add(applet);
	}
	
/**
 * This method adds labels to each of the boxes created above.
 */
	private void createLabels() {
		int c = (getWidth() - 3*BOX_WIDTH - 50) / 2;
		int d = (getHeight() + 3*BOX_HEIGHT) / 2;
		
		GLabel graphics = new GLabel("GraphicsProgram");
		graphics.setFont("Courier-12");
		graphics.setLocation(c + (BOX_WIDTH - graphics.getWidth()) / 2, d + (BOX_HEIGHT + graphics.getAscent()) / 2);
		add(graphics);
		
		GLabel console = new GLabel("ConsoleProgram");
		console.setFont("Courier-12");
		console.setLocation(c + BOX_WIDTH + 25 + (BOX_WIDTH - console.getWidth()) / 2, d + (BOX_HEIGHT + console.getAscent()) / 2);
		add(console);
		
		GLabel dialog = new GLabel("DialogProgram");
		dialog.setFont("Courier-12");
		dialog.setLocation(c + 2*BOX_WIDTH + 50 + (BOX_WIDTH - dialog.getWidth()) / 2, d + (BOX_HEIGHT + dialog.getAscent()) / 2);
		add(dialog);
		
		GLabel program = new GLabel("Program");
		program.setFont("Courier-Bold-12");
		program.setLocation(c + BOX_WIDTH + 25 + (BOX_WIDTH - program.getWidth()) / 2, d - 2*BOX_HEIGHT + (BOX_HEIGHT + program.getAscent()) / 2);
		add(program);
		
		GLabel applet = new GLabel("Applet");
		applet.setFont("Courier-Bold-12");
		applet.setLocation(c + BOX_WIDTH + 25 + (BOX_WIDTH - applet.getWidth()) / 2, d - 4*BOX_HEIGHT + (BOX_HEIGHT + applet.getAscent()) / 2);
		add(applet);
	}

/**
 * This method adds lines to connect the boxes at their midpoints.
 */
	private void addLines() {
		int x = ((getWidth() - 2*BOX_WIDTH - 50) / 2);
		int y = (getHeight() + 3*BOX_HEIGHT) / 2;
		
		GLine line1 = new GLine(x, y, x + BOX_WIDTH + 25, y - BOX_HEIGHT);
		add(line1);
		
		GLine line2 = new GLine(x + BOX_WIDTH + 25, y, x + BOX_WIDTH + 25, y - BOX_HEIGHT);
		add(line2);
		
		GLine line3 = new GLine(x + 2*BOX_WIDTH + 50, y, x + BOX_WIDTH + 25, y - BOX_HEIGHT);
		add(line3);
		
		GLine topLine = new GLine(x + BOX_WIDTH + 25, y - 2*BOX_HEIGHT, x + BOX_WIDTH + 25, y - 3*BOX_HEIGHT);
		add(topLine);
	}
	
/**
 * This method adds a title to the acm.program class hierarchy.
 */
	private void addTitle() {
		GLabel title = new GLabel("acm.program Class Hierarchy");
		title.setFont("Courier-Bold-24");
		title.setLocation((getWidth() - title.getWidth()) / 2, BOX_HEIGHT);
		add(title);
	}
}