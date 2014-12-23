/*
 * File: Hangman.java
 * ------------------
 * This program plays a typical game of Hangman by extending both ConsoleProgram and GraphicsProgram.
 * The program randomly chooses a word from a predefined dictionary and the user has 8 chances to guess
 * letters in the word correctly.
 */

//import acm.graphics.*;
import acm.program.*;
import acm.util.*;
//import java.awt.*;

public class Hangman extends ConsoleProgram {

	/* Declaration of an instance variable for the lexicon. */
	private HangmanLexicon lexicon;

	/* Declaration of an instance variable for the canvas. */
	private HangmanCanvas canvas;

	/* Creates the random generator for picking the secret word. */
	private RandomGenerator rgen = RandomGenerator.getInstance();

	/* Sets the number of available guesses per game to 8. */
	private int NUMBER_GUESSES = 8;
	
	/* Sets the amount of time to reset the canvas to 1 second. */
	private static final double PAUSE_TIME = 1000;


	/* Method: init() */
	/** Initializes the program and creates a canvas on which the scaffold appears, initially empty. */
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	/* Method: run() */
	/** Runs the program by physically chooses the random word from the dictionary and then starting
	 *  the game so that the user can input his/her/its guesses as to what letters are part of the
	 *  word. If the user guesses incorrectly, the program calls the HangmanCanvas() class to update
	 *  the person being hung on the scaffold and the labels under it. */
	public void run() {
		while (true) {		// Loops the game so the user can play over and over again.
			lexicon = new HangmanLexicon();
			int index = rgen.nextInt(0, lexicon.getWordCount());	// Chooses a random integer.
			String chosenWord = lexicon.getWord(index);	// Passes the above result into lexicon.
			playHangman(chosenWord);	// Passes the above result into the playHangman() method.
			pause(PAUSE_TIME);	// Allows the user to pause for a couple of seconds before next game.
			println();		// Adds extra space after each game
			println();		// to make things look pretty.
		}
	}

	/* Method: playHangman() */
	/** The only private method in the entire program. This method is what actually allows the user
	 *  to input his/her/its guess on each iteration. If he/she/it guesses correctly, this method
	 *  calls HangmanCanvas() and updates the label under the person on the right half of the program
	 *  and also lets the user know that he/she/it has guessed correctly on the left half of the
	 *  program. If the user guesses incorrectly, this method calls HangmanCanvas() and updates the
	 *  other label under the person on the right half of the program and also lets the user know that
	 *  he/she/it has guessed incorrectly on the left half of the program. Also, if the user guesses
	 *  incorrectly, this method will reduce the number of guesses remaining by 1. Once the number of
	 *  guesses remaining is 0, the user is prompted with a message that tells him/her/it that
	 *  he/she/it has lost. It then displays the word so the user knows what he/she/it was supposed to
	 *  be guessing. If the user guesses the entire word correctly before losing all of his/her/its
	 *  guesses, this method displays a message stating that the user has won. */
	private void playHangman(String word) {
		String result = "";					// Creates the empty string [result].
		int length = word.length();

		for (int i = 0; i < length; i++) {	// Updates [result] to a string with the length of
			result = result + "-";			// the word and made up of all dashes (e.g. ----).
		}

		println("Welcome to Hangman!");
		canvas.reset();		// Calls the reset() method from canvas.
		int numberGuesses = NUMBER_GUESSES;	// Allows for the number of guesses to be reset each game.

		while (!(result.equals(word)) && numberGuesses > 0) {
			println("The word now looks like this: " + result);	// Shows the user what the
			// updated string looks like.
			if (numberGuesses > 1) {
				println("You have " + numberGuesses + " guesses left.");
			} else {
				println("You have only one guess left.");	// Special grammatical case for
			}												// last guess left.

			String yourGuess = readLine("Your guess: ");	// Allows entering of user input.
			while (yourGuess.equals("") || yourGuess.length() > 1	// Exception handling.
					|| yourGuess.charAt(0) < 'A' || yourGuess.charAt(0) > 'z') {
				println("Error: Please enter a valid character (a single letter).");
				yourGuess = readLine("Your guess: ");	// Prompts re-entering of guess if
			}											// this kind of error occurs.
			String yourGuessUpperCase = yourGuess.toUpperCase();	// No distinction between uppercase
			char guess = yourGuessUpperCase.charAt(0);				// and lowercase letter entries.

			if (word.indexOf(guess) == -1) {			// The null/void case.
				println("There are no " + guess + "'s in the word.");
				numberGuesses = numberGuesses - 1;		// Subtracts from counter as a penalty.
				canvas.displayWord(result);			// Calls displayWord() and passes [result]. 
				canvas.noteIncorrectGuess(guess); // Calls noteIncorrectGuess(), passes [guess].
			} else {
				println("That guess is correct.");
				for (int i = 0; i < length; i++) {	// Checks over the entire range of indices.
					if (word.charAt(i) == guess) {	// Checks to see where the guessed letter is.
						if (i == 0) {	// The case where the guessed letter is at index (0).
							result = guess + result.substring(1); // Updates the [result] string.
							if (result.equals(word)) {	// The win case (user guesses whole word).
								println("You guessed the word: " + word);
								println("You win!");
							}
						} else if (i >= 0) {	// The case where the guess is not at index (0).
							result = result.substring(0, i) + guess + result.substring(i + 1);
							if (result.equals(word)) {	// The win case (user guesses whole word).
								println("You guessed the word: " + word);
								println("You win!");
							}
						}
					}
				}
				canvas.displayWord(result);	// Passes [result] into displayWord() from canvas.
			}
		}
		if (numberGuesses == 0) {		// Losing condition.
			println("You're completely hung.");
			println("The word was: " + word);	// Displays word.
			println("You lose.");
		}
	}
}