/*
 * File: Pyramid.java
 * Name: Chirag Bharadwaj
 * Section Leader: Kristen Carnohan
 * ------------------
 * This program creates a pyramid centered at the bottom of the
 * Graphics window with a predetermined number of bricks comprising
 * the bottom row and one less brick on each subsequent row on top.
 * The last row is the row with only one brick.
 * 
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

// Runs the program. //

public class Pyramid extends GraphicsProgram {

	// Private constants. //

	/** Width of each brick in pixels. */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels. */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid. */
	private static final int BRICKS_IN_BASE = 14;

	// Generates the pyramid and adds a cute flag on top. //

	public void run() {
		generatePyramid();
		addFlag();
	}
/**
 * This method physically creates the pyramid by generating one
 * row of bricks and looping itself until it reaches the row that
 * contains only one brick. The method ensures that the rows are
 * staggered, meaning that the bricks do not line up but rather
 * form an alternating pattern between rows that looks like how
 * bricks are set in layers in a building's foundation.
 */
	private void generatePyramid() {
		int a = (getWidth() - (BRICK_WIDTH * BRICKS_IN_BASE)) / 2;
		int b = getHeight() - BRICK_HEIGHT;
		int n = BRICKS_IN_BASE;
		while (n > 0) {
			for (int i = 0; i < n; i++) {
				GRect brick = new GRect(a, b, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				brick.setFillColor(Color.LIGHT_GRAY);
				add(brick);
				a = a + BRICK_WIDTH;
			}
			a = a - ((n) * BRICK_WIDTH) + BRICK_WIDTH / 2;
			b = b - BRICK_HEIGHT;
			n = n - 1;
		}
	}

/**
 * This method physically creates a flag and flagpole on top of the
 * pyramid by using Graphics objects. The phrase "We surrender!" is
 * also included at the top through the use of a Label object.
 */
	private void addFlag() {
		int x = getWidth() / 2;
		int y = getHeight() - (BRICKS_IN_BASE * BRICK_HEIGHT);

		GLine flagpole = new GLine(x, y, x, y - 4*BRICK_HEIGHT);
		add(flagpole);

		GRect flag = new GRect(x, y - 4*BRICK_HEIGHT, BRICK_WIDTH, 3*BRICK_HEIGHT/2);
		add(flag);
		
		GLabel surrender = new GLabel("We surrender!");
		surrender.setFont("Euclid-Bold-24");
		surrender.setLocation(x - (surrender.getWidth() / 2), y - 5*BRICK_HEIGHT);
		add(surrender);
	}
}