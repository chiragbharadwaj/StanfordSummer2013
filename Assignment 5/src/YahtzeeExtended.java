/*
 * File: YahtzeeExtended.java
 * --------------------------
 * This program plays a game of Yahtzee! with up to 4 players and
 * random dice rolls (to simulate the user-created roll of 5 dice
 * in the real version of the game). The user is required to enter
 * his/her/its name (in multi-player, all users are required to
 * enter their names). This is the extended version of Yahtzee,
 * complete with the entire rule set as well as other enhancements.
 */

import java.applet.AudioClip;
import java.util.*;

import acm.graphics.GImage;
import acm.io.*;
import acm.program.*;
import acm.util.*;

public class YahtzeeExtended extends GraphicsProgram implements YahtzeeConstants {

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private int[][] scorecard;		// Only one extra instance variable!!
	private YahtzeeDisplay display;	// Parameter passing was deceptively difficult.
	private RandomGenerator rgen = new RandomGenerator();
	
/** Sets the amount (in ms) of time delay for the "pause" function */
	private static final int PAUSE_TIME = 2000; 

	/* Method: main() */
	/** The program begins executing at this method. */
	public static void main(String[] args) {
		new YahtzeeExtended().start(args);
	}

	/* Method: run() */
	/** This method sets up the game, prompts the user(s) to input
	 *  the number of players, the names of players, and then takes
	 *  the user(s) to the main screen (YahtzeeDisplay) to begin
	 *  the game.
	 */
	public void run() {
		GImage logo = new GImage("YahtzeeLogo.jpg");
		double logox = ((getWidth() - logo.getWidth()) / 2);
		double logoy = ((getHeight() - logo.getHeight()) / 2);
		add(logo, logox, logoy);
		pause(PAUSE_TIME);
		
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");

		if ((nPlayers > MAX_PLAYERS) || (nPlayers < 1)) {
			dialog.showErrorMessage("Error: Please enter a number between 1 and " +
					MAX_PLAYERS + ".");
			nPlayers = dialog.readInt("Enter number of players");
		}	// Exception handling.

		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name of Player " + i);
		}
		
		remove(logo);
		
		display = new YahtzeeDisplay(getGCanvas(), playerNames);

		playGame();

		dialog.println("Press \"OK\" to play again.");
	}

	/* Method: playGame() */
	/** This method actually executes the gameplay. The method will
	 *  tell a user that it is his/her/its turn currently and then
	 *  invokes the takeTurn() method. At the end, this method
	 *  displays the winner and his/her/its score.
	 */
	private void playGame() {
		for (int i = 1; i <= N_SCORING_CATEGORIES; i++) {
			for (int j = 1; j <= nPlayers; j++) {
				display.printMessage("It is " + playerNames[j - 1] +
				"'s turn.  Click the \"Roll Dice\" button.");
				takeTurn(i, j);
			}
		}
		declareCongratulations();
	}

	/* Method: takeTurn() */
	/** This method allows for gameplay to run on a turn-by-turn
	 *  basis. It essentially calls other methods that will allow
	 *  the user to roll the dice, select a category, and then
	 *  watch his/her/its scorecard be updated.
	 */
	private void takeTurn(int round, int player) {
		initializeScorecard(round, player);
		int[] dice = new int[N_DICE];
		int upperScore = scorecard[UPPER_SCORE - 1][player - 1];	// Initializes the running
		int upperBonus = scorecard[UPPER_BONUS - 1][player - 1];	// total scores that will
		int lowerScore = scorecard[LOWER_SCORE - 1][player - 1];	// be printed at the end.

		rollAndRerollDice(dice, player);

		display.printMessage("Select a category for this roll.");
		int category = display.waitForPlayerToSelectCategory();
		category = selectCategory(category, player);	// Assigns category to the return value.

		if (0 < category && category < UPPER_SCORE) {
			upperScore += updateScoresheet(dice, category, player);
		} else if (UPPER_BONUS < category && category < LOWER_SCORE) {
			lowerScore += updateScoresheet(dice, category, player);
		}	// Updates the running total scores that were created above. These are assigned
		// to the return value of updateScoresheet().

		if (upperScore >= UPPER_BONUS_CUTOFF) {
			upperBonus = UPPER_BONUS_AMOUNT;	// Allows Upper Bonus to be updated.
		}

		updateRunningTotals(round, player, upperScore, upperBonus, lowerScore);
	}

	/* Method: intializeScorecard() */
	/** This method sets up the scorecard and initializes it in a 
	 *  manner that will be beneficial later.
	 */
	private void initializeScorecard(int round, int player) {
		if (round == 1) {
			if (player == 1) {
				scorecard = new int[N_CATEGORIES][nPlayers];
				for (int j = 0; j < N_CATEGORIES; j++) {
					for (int k = 0; k < nPlayers; k++) {
						scorecard[j][k] = -1;	// Sets everything to -1 which will allow the
					}							// program to know if a category has already been
				}								// selected before (in a different method).
			}
			scorecard[UPPER_SCORE - 1][player - 1] = 0;	// Sets the values in these special
			scorecard[UPPER_BONUS - 1][player - 1] = 0;	// categories to 0 so that adding up
			scorecard[LOWER_SCORE - 1][player - 1] = 0;	// totals at the end (and keeping the
			scorecard[TOTAL - 1][player - 1] = 0;		// running totals) is easier to execute.
		}
	}

	/* Method: rollAndRerollDice() */
	/** This method allows the user to roll the dice once and then
	 *  choose to (or not to) re-roll the dice twice more. For each
	 *  "new" configuration of dice, the method displays the dice.
	 *  The user rolls the dice by clicking "Roll Dice" and rerolls
	 *  them by clicking "Roll Again". The values of the dice are
	 *  randomly determined to simulate the real-life scenario.
	 */
	private void rollAndRerollDice(int[] dice, int player) {
		AudioClip dieHitClip = MediaTools.loadAudioClip("DieHit.wav");
		
		for (int j = 1; j <= N_CONFIG; j++) {
			if (j == 1) {
				display.waitForPlayerToClickRoll(player);
				for (int k = 1; k <= N_DICE; k++) {
					dice[k - 1] = rgen.nextInt(1, N_SIDES);		// Randomly generates the values
					dieHitClip.play();							// on the five dice.
				}
			} else {
				for (int k = 1; k <= N_DICE; k++) {
					if (display.isDieSelected(k - 1)) {
						dice[k - 1] = rgen.nextInt(1, N_SIDES);	// Randomly generates the values
						dieHitClip.play();						// on the five dice.
					}
				}
			}
			display.displayDice(dice);
			if (j != N_CONFIG) {
				display.printMessage("Select the dice you wish to re-roll " +
				"and click \"Roll again\".");
				display.waitForPlayerToSelectDice();
			}
		}
		display.displayDice(dice);
	}

	/* Method: selectCategory() */
	/** After the user has chosen his/her/its final configuration
	 *  of dice, he/she/it is required to choose a category into
	 *  which these dice fit. However, if the user selects a
	 *  category which he/she/it has already chosen, this method
	 *  tells the user that it cannot honor his/her/its choice and
	 *  re-prompts him/her/it to select a category.
	 */
	private int selectCategory(int category, int player) {
		while (true) {										// Checks to see if the chosen
			if (scorecard[category - 1][player - 1] != -1) {// category has been chosen before.
				display.printMessage("You have already selected this category." +
				"  Please select a category that you have not yet chosen.");
				category = display.waitForPlayerToSelectCategory();
			} else break;
		}
		return category;
	}

	/* Method: updateScoresheet() */
	/** This method updates the scorecard in the category that the
	 *  user selected as well as in the total number of points the
	 *  user has accumulated thus far. This method will store the
	 *  appropriate number of points in the scoresheet cell if the
	 *  user's category selection is legitimate; otherwise, it will
	 *  store a value of 0.
	 */
	private int updateScoresheet(int[] dice, int category, int player) {

		if (checkCategory(dice, category) && !(bonusYahtzee(dice, player))) {
			display.updateScorecard(category, player, score(dice, category));
			scorecard[category - 1][player - 1] = score(dice, category);
			return score(dice, category);
		}

		if (bonusYahtzee(dice, player)) {
			display.updateScorecard(category, player, BONUS_YAHTZEE);
			scorecard[category - 1][player - 1] = BONUS_YAHTZEE;
			display.printMessage("Congratulations, " + playerNames[player - 1] +
			"!  You've gotten a Bonus Yahtzee!");
			return BONUS_YAHTZEE;
		} else {
			display.updateScorecard(category, player, 0);
			scorecard[category - 1][player - 1] = 0;
			return 0;
		}
	}

	/* Method: bonusYahtzee() */
	/** This method checks to see if a Bonus Yahtzee has occurred
	 *  (the unlikely situation in which the user has already rolled
	 *  a Yahtzee, selected the Yahtzee category, and then happens
	 *  to roll Yahtzee again in a subsequent roll. Such a scenario
	 *  leads to the acceptance of the dice configuration in ANY
	 *  category with a score of 100 points being stored.
	 */
	private boolean bonusYahtzee(int[] dice, int player) {
		if (checkCategory(dice, YAHTZEE) &&
				(scorecard[YAHTZEE - 1][player - 1] != -1) &&
				(scorecard[YAHTZEE - 1][player - 1] != 0) ) {
			return true;
		}
		return false;
	}

	/* Method: updateRunningTotals() */
	/** This method will update the scorecard behind-the-scenes so
	 *  that after the last round, the Upper Score, Upper Bonus,
	 *  and Lower Score categories show the right amount. These
	 *  updates are NOT printed until the final values of each of
	 *  the aforementioned categories are calculated in the last
	 *  round. However, the Total category is updated each round. 
	 */
	private void updateRunningTotals(int round, int player,
			int upperScore, int upperBonus, int lowerScore) {

		int total = upperScore + upperBonus + lowerScore;
		scorecard[UPPER_SCORE - 1][player - 1] = upperScore;	// Updates the running totals to
		scorecard[UPPER_BONUS - 1][player - 1] = upperBonus;	// the specific values from the
		scorecard[LOWER_SCORE - 1][player - 1] = lowerScore;	// scorecard array...
		scorecard[TOTAL - 1][player - 1] = total;				// ...and the final total too.

		if (round == N_SCORING_CATEGORIES) {
			display.updateScorecard(UPPER_SCORE, player, scorecard[UPPER_SCORE - 1][player - 1]);
			display.updateScorecard(UPPER_BONUS, player, scorecard[UPPER_BONUS - 1][player - 1]);
			display.updateScorecard(LOWER_SCORE, player, scorecard[LOWER_SCORE - 1][player - 1]);
		}
		display.updateScorecard(TOTAL, player, scorecard[TOTAL - 1][player - 1]);
	}

	/* Method: score() */
	/** This method simply assigns the correct point value to a
	 *  given dice configuration specified by the category. It also
	 *  returns this value.
	 */
	private int score(int[] dice, int category) {
		int score = 0;
		int sumOfAllDice = 0;
		for (int i = 0; i < N_DICE; i++) {
			sumOfAllDice += dice[i];
		}

		switch(category) {		// Assigns point values to the different dice configurations. 
		case 1: score = sumOfDice(dice, 1); break;
		case 2: score = sumOfDice(dice, 2); break;
		case 3: score = sumOfDice(dice, 3); break;
		case 4: score = sumOfDice(dice, 4); break;
		case 5: score = sumOfDice(dice, 5); break;
		case 6: score = sumOfDice(dice, 6); break;
		case 9: score = sumOfAllDice; break;
		case 10: score = sumOfAllDice; break;
		case 11: score = FULL_HOUSE_SCORE; break;
		case 12: score = SMALL_STRAIGHT_SCORE; break;
		case 13: score = LARGE_STRAIGHT_SCORE; break;
		case 14: score = YAHTZEE_SCORE; break;
		case 15: score = sumOfAllDice; break;
		}
		return score;
	}

	/* Method: sumOfDice() */
	/** This method checks to see how many dice have a particular
	 *  number (specified by the score() method) and then sums up
	 *  the total number of pips across those dice. For example, if
	 *  there are 4 dice that show the number "2", this method will
	 *  return 8 (4 * 2).
	 */
	private int sumOfDice(int[] dice, int i) {
		int sum = 0;
		for (int j = 0; j < N_DICE; j++) {
			if (dice[j] == i) {
				sum += i;
			}
		}
		return sum;
	}

	/* Method: highScore() */
	/** This method figures out the highest score amongst the
	 *  players after all the rounds have been completed and then
	 *  returns this value.
	 */
	private int highScore() {
		int highScore = scorecard[N_CATEGORIES - 1][0];
		for (int i = 1; i < nPlayers; i++) {
			if (scorecard[N_CATEGORIES - 1][i - 1] > highScore) {
				highScore = scorecard[N_CATEGORIES - 1][i - 1];
			}
		}
		return highScore;
	}

	/* Method: winnerName() */
	/** This method figures out which player has the highest score
	 *  that is determined by the highScore() method. It then
	 *  returns the name of that player.
	 */
	private String winnerName() {
		String winnerName = playerNames[0];
		int highScore = scorecard[N_CATEGORIES - 1][0];
		for (int i = 1; i < nPlayers; i++) {
			if (scorecard[N_CATEGORIES - 1][i - 1] > highScore) {
				winnerName = playerNames[i - 1];
			} 
		}
		return winnerName;
	}

	/* Method: checkCategory() */
	/** This method checks to see if the category that the user
	 *  specified matches the dice configuration displayed. It
	 *  returns "true" if they match and "false" otherwise.
	 */
	private boolean checkCategory(int[] dice, int category) {
		if ((ONES <= category && category <= SIXES) || category == CHANCE) {
			return true;
		} else if (category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND
				|| category == YAHTZEE) {
			return threesFoursAndYahtzees(dice, category);
		} else if (category == FULL_HOUSE) {
			return fullHouse(dice);
		} else if (category == SMALL_STRAIGHT || category == LARGE_STRAIGHT) {
			return straights(dice, category);
		}
		return false;
	}

	/* Method: threesFoursAndYahtzees */
	/** This method specifically checks to see if the dice
	 *  configuration is a Three of a Kind, Four of a Kind, or a
	 *  Yahtzee. It returns "true" if it matches one of these and
	 *  the user specified the same category. Otherwise, it returns
	 *  "false".
	 */
	private boolean threesFoursAndYahtzees(int[] dice, int category) {
		for (int i = 0; i < N_DICE; i++) {
			int numberInstances = 0;
			for (int j = 0; j < N_DICE; j++) {
				if (dice[i] == dice[j]) numberInstances++;
			}
			if (numberInstances >= 3 && category == THREE_OF_A_KIND) return true;
			if (numberInstances >= 4 && category == FOUR_OF_A_KIND) return true;
			if (numberInstances == 5 && category == YAHTZEE) return true;
		}
		return false;
	}

	/* Method: fullHouse() */
	/** This method specifically checks to see if the dice
	 *  configuration is a Full House. It returns "true" if it is a
	 *  Full House and the user specified so. Otherwise, it returns
	 *  "false".
	 */
	private boolean fullHouse(int[] dice) {
		boolean threeOfAKind = false;
		boolean pair = false;
		for (int i = 0; i < N_DICE; i++) {
			int numberInstances = 0;
			for (int j = 0; j < N_DICE; j++) {
				if (dice[i] == dice[j]) numberInstances++;
			}
			if (numberInstances == 3) {
				threeOfAKind = true;
			} else if (numberInstances == 2) {
				pair = true;
			}
		}
		if (threeOfAKind && pair) return true;	// Returns true only if the dice
		return false;							// configuration contains both a
	}											// Three of a Kind and a Pair.

	/* Method: straights() */
	/** This method specifically checks to see if the dice
	 *  configuration is a Small Straight or a Large Straight. It
	 *  returns "true" if it matches one of these and the user
	 *  specified the same category. Otherwise, it returns "false".
	 */
	private boolean straights(int[] dice, int category) {
		int numberConsecutives = 1;
		Arrays.sort(dice);	// Sorts the dice in increasing order by calling the sort function.
		for (int i = 1; i < N_DICE; i++) {
			if (dice[i] == dice[i - 1] + 1) numberConsecutives++;
		}
		if (numberConsecutives >= 4 && category == SMALL_STRAIGHT) {
			return true;
		} else if (numberConsecutives == 5 && category == LARGE_STRAIGHT) {
			return true;
		}
		return false;
	}

	/* Method: declareCongratulations() */
	/** This method congratulates the winning user of the current
	 *  game of Yahtzee and displays his/her/its score.
	 */
	private void declareCongratulations() {
		if (nPlayers == 1) {
			display.printMessage("Game over.  You... beat yourself!  Your score was: "
					+ highScore() + ".");
		} else {
			display.printMessage(winnerName() + " won this game of \"Yahtzee!\" with a score of "
					+ highScore() + "!");
		}	// The above conditionals display the winning congratulations statement.
	}
}