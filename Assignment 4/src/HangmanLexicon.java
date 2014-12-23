/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains an implementation of HangmanLexicon that is used
 * inside the main Hangman.java file.
 */

import acm.util.*;

import java.io.*;
import java.util.*;

public class HangmanLexicon {

	/** This is the HangmanLexicon constructor. It uses a BufferedReader
	 *  that calls an ArrayList to read the predefined dictionary line by
	 *  line and store each line into a separate index inside the array. */
	public HangmanLexicon() {
		BufferedReader rd;
		try {
			rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while (true) {
				String word = rd.readLine();	// Reads one line of file.
				if (word == null) break;	// Breaks after last line read. 
				readLineArray.add(word);	// Adds the line to the array.
			}
			rd.close();
		} catch (IOException ex) {			// Just catches some exceptions
			throw new ErrorException(ex);	// in case of some errors.
		}
	}

	/* Creates the ArrayList that is called by the BufferedReader. */
	private ArrayList<String> readLineArray = new ArrayList<String>();

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return readLineArray.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return readLineArray.get(index);
	}
}