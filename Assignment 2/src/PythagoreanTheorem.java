/*
 * File: PythagoreanTheorem.java
 * Name: Chirag Bharadwaj
 * Section Leader: Kristen Carnohan
 * -----------------------------
 * This program evaluates the Pythagorean Theorem for the length of
 * the hypotenuse of a right triangle if the user inputs the
 * lengths of the two legs of the right triangle.
 */

import acm.program.*;

// Runs the program. //

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("If you enter the lengths of the legs of a right triangle, this program will use the Pythagorean Theorem to compute the the length of the hypotenuse.");
		println("");
		double leg1 = readDouble("Enter the length of leg 1: ");
		double leg2 = readDouble("Enter the length of leg 2: ");
		double hyp = Math.sqrt(leg1 * leg1 + leg2 * leg2);
		println("");
		println("The length of the hypotenuse is " + hyp + ".");
	}
}