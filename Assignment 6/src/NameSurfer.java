/*
 * File: NameSurfer.java
 * ---------------------
 * This program implements the viewer for the baby-name database
 * that describes the 1000 most popular baby names in the United
 * States over a span of years and the attributes associated with
 * these names.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

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

		database = new NameSurferDataBase(NAMES_DATA_FILE);

		addActionListeners();
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so this method defines the responses to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nameField ||				// Works if "enter"
				e.getActionCommand().equals("Graph")) {	// is pressed or if
			String name = nameField.getText();			// "Graph" is clicked
			NameSurferEntry rank = database.findEntry(name);// Finds the rank
			if (rank != null) {								// of a particular
				graph.addEntry(rank);						// entry (name)
				graph.update();		// ... and then updates the graph
			}
		} else if (e.getActionCommand().equals("Clear")) {
			graph.clear();			// Clears and updates the graph
			graph.update();			// if "Clear" is pressed
		}
	}

	/* Constructor: NameSurfer() */
	/**
	 * This constructor ties together the program by bringing the
	 * graph to the main program so that it can access the graph's
	 * attributes and use them to update the screen whenever
	 * necessary in other methods within the program.
	 */
	public NameSurfer() {
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