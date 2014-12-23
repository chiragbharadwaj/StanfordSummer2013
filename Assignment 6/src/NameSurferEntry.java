/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears
	 * in the data file. Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each
	 * decade.
	 */
	public NameSurferEntry(String line) {
		name = line.substring(0, line.indexOf(" "));

		StringTokenizer tokenizer =	// Tokenizes the database's output
			new StringTokenizer(line.substring(line.indexOf(" ") + 1));

		for (int i = 0; tokenizer.hasMoreTokens(); i++) {
			int rankOfDecade = Integer.parseInt(tokenizer.nextToken());
			rank[i] = rankOfDecade;	// Finds the ranks and stores
		}					// them in the rank array, one by one. The
	}						// index of the array for each decade
							// corresponds to the number of decades
							// that decade has been since START_DECADE.
	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() { 
		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular
	 * decade. The decade value is an integer indicating how many
	 * decades have passed since the first year in the database,
	 * which is given by the constant START_DECADE. If a name does
	 * not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		return rank[decade];
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry (overrides java.lang's toString() method).
	 */
	public String toString() {	  // Creates a string of the form
		String lineToString = getName() + " [";	// "Name [# # #]"
		for (int i = 0; i < NDECADES; i++) {
			lineToString = lineToString + getRank(i) + " ";
		}	// Adds the "# # #" part to "Name [# # #]"
		lineToString = lineToString + "]";
		return lineToString;
	}

	/* Private instance variables */
	private String name;
	
	/** Creates a new array that stores the ranks for each decade
	 *  for a particular player (a user-defined entry) */
	private int[] rank = new int[NDECADES];
}