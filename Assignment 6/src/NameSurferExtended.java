/*
 * File: NameSurferExtended.java
 * -----------------------------
 * This program implements the viewer for the baby-name database
 * that describes the 1000 most popular baby names in the United
 * States over a span of years and the attributes associated with
 * these names. This is an extended version of the program that
 * contains extra features to enhance the user's experience.
 */

//            List of Extensions in NameSurferExtended.java
//            ---------------------------------------------
//						 BY CHIRAG BHARADWAJ
//						chiragb2@stanford.edu
//
//1. I have added a "Delete" button to the canvas in the
//   NameSurferExtended.java file so that the user may delete an entry
//   he/she/it has previously entered lest the display screen become
//   too cluttered.  A method called deleteEntry(entry) has been added
//   to NameSurferGraph.java so that it may be called when the "Delete"
//   button is pressed and the entry is actually deleted from the graph.
//   
//2. In conjunction with #1 up there, I had to alter the color strategy
//   so that the color sequence BLACK-RED-BLUE-MAGENTA-BLACK-etc. is
//   retained even if a previously created entry is removed from the
//   display.  For this, I have altered the definition of the variable
//   "remainder" and the location of its definition (it is now defined
//   in update() so that the color scheme gets called each time an
//   entry is added or deleted).
//   
//3. Another extension I have added is to label each name-entry on the
//   line graph with a certain marking per decade.  For example, the
//   first, fifth, ninth, etc. name on the graph will have an open
//   black circle as a representative mark per decade at its rank.
//   The second, sixth, tenth, etc. name on the graph will have an open
//   red square.  The third, seventh, eleventh, etc. name on the graph
//   will have a closed blue circle.  And finally, the fourth, eighth,
//   twelfth, etc. name on the graph will have a closed magenta square.
//   This enables the user to identify which unit a certain lineage
//   belongs to in the case that he/she/it prints out a black and white
//   copy of the chart and cannot discern the various shades of
//   grayscale that differentiate "red" from "magenta".  These markings
//   allow the differentiation to occur independently of the presence
//   of color.

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurferExtended extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method is responsible for reading in the database and
	 * initializing the interactors at the top of the window.
	 */
	public void init() {
		nameField = new JTextField(TEXT_FIELD_WIDTH);
		add(new JLabel("Name"), NORTH);
		add(nameField, NORTH);
		nameField.addActionListener(this);

		add(new JButton("Graph"), NORTH);
		add(new JButton("Clear"), NORTH);
		add(new JButton("Delete"), NORTH);

		database = new NameSurferDataBase(NAMES_DATA_FILE);

		addActionListeners();
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so this method defines the responses to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String name = nameField.getText();
		NameSurferEntry rank = database.findEntry(name);

		if (e.getSource() == nameField ||				// If "enter" is pressed
				e.getActionCommand().equals("Graph")) {	// or if "Graph" is clicked
			if (rank != null) {				// Finds the rank of a
				graph.addEntry(rank);		// particular entry (name)
				graph.update();				// ...and then updates the graph
			}
		} else if (e.getActionCommand().equals("Clear")) {
			graph.clear();			// Clears and updates the graph
			graph.update();			// if "Clear" is pressed
		} else if (e.getActionCommand().equals("Delete")) {
			if (rank != null) {
				graph.deleteEntry(rank);		// Deletes the particular
				graph.update();					// entry and updates the graph.
			}
		}
	}

	/* Constructor: NameSurfer() */
	/**
	 * This constructor ties together the program by bringing the
	 * graph to the main program so that it can access the graph's
	 * attributes and use them to update the screen whenever
	 * necessary in other methods within the program.
	 */
	public NameSurferExtended() {
		graph = new NameSurferGraph();
		add(graph);
	}

	/* Private constants */
	private static final int TEXT_FIELD_WIDTH = 20;

	/* Private instance variables */
	private JTextField nameField;
	private NameSurferGraph graph;
	private NameSurferDataBase database;
}