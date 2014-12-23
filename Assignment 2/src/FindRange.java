/*
 * File: FindRange.java
 * Name: Chirag Bharadwaj
 * Section Leader: Kristen Carnohan
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

// Runs the program. //

public class FindRange extends ConsoleProgram {

	// Specifies the value of the sentinel. //
	private static final int SENTINEL = 0;

	public void run() {
		while (true) {
			welcomeStatement();
			int value = readInt("Enter an integer: ");
			if (value == SENTINEL) {
				failStatement();
			} else {
				int value2 = readInt("Enter an integer: ");
				if (value2 == SENTINEL) {
					earlyDataAnalysisStatement();
					println("The maximum and minimum values are both " + value + ".");
					println("");
					println("");
				} else {
					int max = value;
					int min = value;
					while (true) {
						int integer = readInt("Enter an integer: ");
						if (integer == SENTINEL) break;
						if (integer != SENTINEL && integer <= min) {
							min = integer;
						}
						if (integer >= max) {
							max = integer;
						}
					}
					dataAnalysisStatement();
					println("The minimum value you entered is: " + min + ".");
					println("The maximum value you entered is: " + max + ".");
					println("");
					println("");
				}
			}
		}
	}

/**
 * Welcomes the user to the program and states what the user needs
 * to do to run the program correctly.
 */
	private void welcomeStatement() {
		println("This program provides the maximum and minimum values for a list of integers.");
		println("");
		println("Enter values, one per line, using " + SENTINEL + " to signify the end of your list.");
		println("");
	}

/**
 * Tells the user that the data is being analyzed and will list the
 * largest and smallest numbers in the sample provided.
 */
	private void dataAnalysisStatement() {
		println("");
		println("Based on the list that you have created, the following facts have been compiled about the sample:");
		println("");
	}

/**
 * If the sentinel is entered as the second number, the program
 * generates the DataAnalysisStatement early and lists the first
 * number entered as both the largest and smallest number in the
 * sample provided.
 */
	private void earlyDataAnalysisStatement() {
		println("");
		println("Based on the list that you have created, the following fact has been compiled about the sample:");
		println("");
	}
	
/**
 * If the sentinel is entered as the first number, the program
 * generates a statement that tells the user that he/she/it has not
 * created a valid list (the set of entries in the list is empty).
 */
	private void failStatement() {
		println("");
		println("You have not created a valid list.");
		println("");
		println("");
	}
}