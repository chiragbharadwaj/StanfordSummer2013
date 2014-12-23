/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.util.*;

import java.io.*;
import java.util.*;

public class NameSurferDataBase implements NameSurferConstants {

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the
	 * data in the specified file. The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = rd.readLine();
				if (line == null) break;
				NameSurferEntry entry = new NameSurferEntry(line);	// Creates a new entry
				database.put(entry.getName(), entry);	// Puts the entry inside the
			}											// database (Hashmap)
			rd.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);	// Exception handling
		}
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one
	 * exists. If the name does not appear in the database, this
	 * method returns null.
	 */
	public NameSurferEntry findEntry(String name) {	// Converts the entry to the database's form 
		name = Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
		if (database.containsKey(name)) {	// Finds the key in the database...
			return database.get(name);		// ... then returns the value associated with it
		} else return null;		// Exception handling
	}

	/* Private instance variables */
	/** Uses a HashMap as a database (key-value pairs correspond to entries and ranks) */
	private HashMap<String, NameSurferEntry> database = new HashMap<String, NameSurferEntry>();
}