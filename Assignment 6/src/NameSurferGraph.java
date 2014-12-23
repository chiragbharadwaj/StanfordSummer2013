/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/* Constructor: NameSurferGraph() */
	/**
	 * Adds the ComponentListener to initialize the graph.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
	}

	/* Method: clear() */
	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		entries.clear();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update().
	 */
	public void addEntry(NameSurferEntry entry) {
		entries.add(entry);
	}

	/* Method: deleteEntry(NameSurferEntry entry) */
	/**
	 * Deletes an existing NameSurferEntry in the list of entries on
	 * the display. Not that this method does not actually draw the
	 * graph, but simply deletes the stored entry; the graph is drawn
	 * by calling update();
	 */
	public void deleteEntry(NameSurferEntry entry) {
		entries.remove(entries.indexOf(entry));
	}

	/* Method: update() */
	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. The application calls update() after it
	 * calls either clear() or addEntry(); update() is also called whenever
	 * the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		createVerticalBars();
		createHorizontalBars();
		addLabels();

		int currentEntry = 0;

		// Loops through the ArrayList "entries" and displays the information
		// associated with each name in the ArrayList on the graph.
		for(currentEntry = 0; currentEntry < entries.size(); currentEntry++) {
			int remainder = currentEntry % 4;
			// The above allows for the initialization of the 4-count repeat cycle.
			// Also, defining it here in the update() method allows for the color 
			// sequence to remain unbroken if the deleteEntry() method is later
			// called. This provides an aesthetic advantage.

			NameSurferEntry entry = entries.get(currentEntry);
			displayEntriesOnGraph(currentEntry, entry, remainder);
		}
	}

	/* Method: createVerticalBars() */
	/**
	 * Creates the vertical bars on the graph that represent each
	 * decade from 1900 to 2000.
	 */
	private void createVerticalBars() {
		double barHorPosition = 0;
		for (int i = 0; i < NDECADES + 1; i++) {
			GLine verticalBar = new GLine(barHorPosition, 0,
					barHorPosition, getHeight());
			add(verticalBar);	// barHorPosition gets incremented for next decade
			barHorPosition = barHorPosition + ((double)getWidth() / NDECADES);
		}	// This is type-casted because for some reason getWidth() was an int otherwise
	}

	/* Method: createHorizontalBars() */
	/**
	 * Creates the horizontal bars on the top and bottom of the
	 * graph to represent boundaries that the lines cannot extend
	 * beyond.
	 */
	private void createHorizontalBars() {
		GLine topBar = new GLine(0, GRAPH_MARGIN_SIZE,
				getWidth(), GRAPH_MARGIN_SIZE);
		add(topBar);
		GLine bottomBar = new GLine(0, (double)getHeight() - GRAPH_MARGIN_SIZE,
				getWidth(), (double)getHeight() - GRAPH_MARGIN_SIZE);
		add(bottomBar);	// The bars are slightly offset from the top and bottom
	}					// to allow for readability and inclusion of decade labels

	/* Method: addLabels() */
	/**
	 * Adds the labels showing the decade next to each of the
	 * vertical bars created previously on the graph. The labels
	 * range from 1900 to 2000.
	 */
	private void addLabels() {
		double labelHorPosition = LABEL_HOR_OFFSET;
		double labelVerPosition = (double)getHeight() - GRAPH_MARGIN_SIZE;
		for (int i = 0; i < NDECADES + 1; i++) {
			GLabel decade = new GLabel("" + (START_DECADE + 10*i));	// 10*i increments
			decade.setLocation(labelHorPosition,					// by decade
					labelVerPosition + decade.getAscent());
			add(decade);
			labelHorPosition = labelHorPosition + ((double)getWidth() / NDECADES);
		}	// This is type-casted because for some reason getWidth() was an int otherwise
	}

	/* Method: displayEntriesOnGraph(currentEntry, entry) */
	/**
	 * Calls methods that add the lines and labels for each rank
	 * entry for each decade for a particular name entered by the
	 * user.
	 */
	private void displayEntriesOnGraph(int currentEntry,
			NameSurferEntry entry, int remainder) {

		createConnectorLines(currentEntry, entry, remainder);
		createNameLabels(currentEntry, entry, remainder);
	}

	/* Method: createConnectorLines(currentEntry, entry, remainder) */
	/**
	 * Actually adds the connecting lines between rank entries for
	 * each decade for a particular name entered by the user. The
	 * result is a line graph of sorts.
	 */
	private void createConnectorLines(int currentEntry,
			NameSurferEntry entry, int remainder) {
		for (int i = 0; i < NDECADES - 1; i++) {
			double xStart = i * ((double)getWidth() / NDECADES);	// Still type-casted
			double yStart = 0;
			double xEnd = (i + 1) * ((double)getWidth() / NDECADES);	// ...and again
			double yEnd = 0;
			double yNewGeneral = getHeight() - GRAPH_MARGIN_SIZE;	// "bottom" of window
			double yStartNew = GRAPH_MARGIN_SIZE + 	// Sets boundaries and spacing per 1 rank
				(entry.getRank(i) * (getHeight() - 2*GRAPH_MARGIN_SIZE) / MAX_RANK);
			double yEndNew = GRAPH_MARGIN_SIZE + 	// Sets boundaries and spacing per 1 rank
				(entry.getRank(i + 1) * (getHeight() - 2*GRAPH_MARGIN_SIZE) / MAX_RANK);

			// Checks the rank between consecutive decades in 4 separate cases
			if (entry.getRank(i) == 0 && entry.getRank(i + 1) == 0) {
				yStart = yNewGeneral;					// This allows for the consideration
				yEnd = yNewGeneral;						// of the four separate cases that
			} else if (entry.getRank(i + 1) == 0) {		// could possibly occur. The
				yStart = yStartNew;						// assignment of yStart and yEnd 
				yEnd = yNewGeneral;						// depending on the various case at
			} else if (entry.getRank(i) == 0) {			// hand is actually systematic in
				yStart = yNewGeneral;					// nature.
				yEnd = yEndNew;
			} else if (entry.getRank(i) != 0 && entry.getRank(i + 1) != 0) {
				yStart = yStartNew;
				yEnd = yEndNew;
			}

			GLine connector = new GLine(xStart, yStart, xEnd, yEnd);

			Color color = setColor(remainder);	// Sets color on a 4-count cycle
			connector.setColor(color);

			add(connector);

			if (i < NDECADES - 2) {		// Adds connecting shapes (circle, square, etc.)
				addConnectingShape(xStart, yStart, color);
			} else if (i == NDECADES - 2) {		// Makes sure the last decade gets a shape too
				addConnectingShape(xStart, yStart, color);
				addConnectingShape(xEnd, yEnd, color);
			}
		}
	}

	/* Method: createNameLabels(currentEntry, entry, remainder) */
	/**
	 * Actually adds the labels denoting the name and rank for each
	 * decade for a particular name entered by the user. The labels
	 * are right next to the "point" that denotes the rank entry
	 * for that decade.
	 */
	private void createNameLabels(int currentEntry,
			NameSurferEntry entry, int remainder) {
		for (int i = 0; i < NDECADES; i++) {
			String nameLabel = entry.getName() + " " + Integer.toString(entry.getRank(i));
			double nameLabelX = (i * ((double)getWidth() / NDECADES)) + SPACING; // Offset
			double nameLabelY = 0;	// More type-casting above. Isn't getWidth() a double?

			if (entry.getRank(i) == 0) {	// Case where the name does not appear in top 1000
				nameLabel = entry.getName() + "*";	// Adds the asterisk next to name as desired
				nameLabelY = getHeight() - GRAPH_MARGIN_SIZE - SPACING;	// Offsets the spacing
			} else {		// Case where the name DOES appear in the top 1000 list
				nameLabelY = GRAPH_MARGIN_SIZE +	// Sets boundaries and spacing per 1 rank
					((entry.getRank(i)) * (getHeight() - 2*GRAPH_MARGIN_SIZE) / MAX_RANK)
					- SPACING;			// Also assigns the correct location based on the rank
			}

			GLabel nameGLabel = new GLabel(nameLabel, nameLabelX, nameLabelY);

			Color color = setColor(remainder);
			nameGLabel.setColor(color);

			add(nameGLabel);
		}
	}

	/* Method: setColor(remainder) */
	/**
	 * This method selects the color out of a cycle of four possible
	 * colors by using a switch statement. The way it is set up allows
	 * for it to be repeated every 4 entries. Thus, Entry 9 has the
	 * same color as Entry 5 which has the same color as Entry 1, etc.  
	 */
	private Color setColor(int remainder) {
		switch(remainder) {
		case 0: return Color.BLACK;
		case 1: return Color.RED;
		case 2: return Color.BLUE;
		}
		return Color.MAGENTA;
	}

	/* Method: addConnectingShape(xStart, yStart, color) */
	/**
	 * This method adds the representative mark to each entry on
	 * the graph per decade. It also makes sure to match the color
	 * that is appropriately selected by setColor().
	 */
	private void addConnectingShape(double xStart, double yStart, Color color) {
		if (color == Color.BLACK) {
			addOpenCircles(xStart, yStart, color);
		} else if (color == Color.RED) {
			addOpenSquares(xStart, yStart, color);
		} else if (color == Color.BLUE) {
			addClosedCircles(xStart, yStart, color);
		} else if (color == Color.MAGENTA) {
			addClosedSquares(xStart, yStart, color);
		}
	}

	// The following four methods do literally what their names imply.
	
	private void addOpenCircles(double xStart, double yStart, Color color) {
		addCircle(xStart, yStart, color, Color.WHITE);
	}

	private void addOpenSquares(double xStart, double yStart, Color color) {
		addSquare(xStart, yStart, color, Color.WHITE);
	}

	private void addClosedCircles(double xStart, double yStart, Color color) {
		addCircle(xStart, yStart, color, color);
	}

	private void addClosedSquares(double xStart, double yStart, Color color) {
		addSquare(xStart, yStart, color, color);
	}

	/* Method: addCircle(xStart, yStart, color, fillColor) */
	/**
	 * This method creates circles (filled or not) that get added to
	 * the graph as representative marks for the odd entries (1, 3,
	 * 5, 7, etc.).
	 */
	private void addCircle(double xStart, double yStart, Color color, Color fillColor) {
		double xStartNew = xStart - (DIAMETER / 2);	// Location offset (for GOval definition)
		double yStartNew = yStart - (DIAMETER / 2);	// ...for this one too.

		GOval circle = new GOval(xStartNew, yStartNew, DIAMETER, DIAMETER);
		circle.setColor(color);
		circle.setFilled(true);
		circle.setFillColor(fillColor);
		add(circle);
	}

	/* Method: addSquare(xStart, yStart, color, fillColor) */
	/**
	 * This method creates squares (filled or not) that get added to
	 * the graph as representative marks for the even entries (2, 4,
	 * 6, 8, etc.).
	 */
	private void addSquare(double xStart, double yStart, Color color, Color fillColor) {
		double xStartNew = xStart - (DIAMETER / 2);	// Location offset (for GRect definition)
		double yStartNew = yStart - (DIAMETER / 2);	// ...for this one too.

		GRect square = new GRect(xStartNew, yStartNew, DIAMETER, DIAMETER);
		square.setColor(color);
		square.setFilled(true);
		square.setFillColor(fillColor);
		add(square);
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }

	/* Private constants */
	/** The number of pixels to offset the decade labels horizontally
	 *  from the vertical bars that represent decade markings */
	private static final int LABEL_HOR_OFFSET = 3;

	/** The diameter of the circles and squares that represent dots
	 *  on the line graph for each entry */
	private static final double DIAMETER = 7.5;

	/** The number of pixels to space the labels from the vertical bars */
	private static final int SPACING = 5;

	/* Private instance variables */
	private ArrayList<NameSurferEntry> entries = new ArrayList<NameSurferEntry>();
}