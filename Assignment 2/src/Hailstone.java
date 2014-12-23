/*
 * File: Hailstone.java
 * Name: Chirag Bharadwaj
 * Section Leader: Kristen Carnohan
 * --------------------
 * This program uses the method mentioned in Gšdel, Escher, Bach by
 * Hofstadter to take any positive integer and eventually generate
 * the number 1 by conducting a process dependent on whether the
 * initial number chosen is odd or even. 
 */

import acm.program.*;

// Runs the program. //

public class Hailstone extends ConsoleProgram {
	public void run() {
		while (true) {
			int n = readInt("Pick any positive integer to start with: ");
			int iterationCounter = 0;
			println("");
			if (n == 0) {
				println("Entering 0 will cause the program to crash.");
				println("");
				println("");
			} else {
				while (n != 1) {
					if (n % 2 != 0) {
						println(+ n + " is odd, so I make 3n+1: " + (3 * n + 1));
						n = 3 * n + 1;
					} else {
						println(+ n + " is even, so I take half: " + (n / 2));
						n = n / 2;
					}
					iterationCounter += 1;
				}
				println("");
				println("This program took " + iterationCounter + " iteration" + (iterationCounter == 1 ? "" : "s") + " to reach 1.");
				println("");
				println("");
			}
		}
	}
}