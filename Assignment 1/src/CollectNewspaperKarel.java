/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * A program that makes Karel walk to the door of its house, pick
 * up the newspaper (represented by a beeper), and then return to
 * its initial position in the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

	public void run() {
		moveToNewspaper();
		pickUpNewspaper();
		returnHome();
	}
	/**
	 * Moves Karel to the newspaper by turning and moving as needed.
	 * When moveToNewspaper is called, Karel must be at the corner of
	 * Avenue 3 and Street 4 facing east. The postcondition for this
	 * method is that Karel is again facing east on the corner of
	 * Avenue 6 and Street 3.
	 */
	private void moveToNewspaper() {
		turnRight();
		move();
		turnLeft();
		move();
		move();
		move();
	}
	/**
	 * Picks up the newspaper. When pickUpNewspaper is called, Karel
	 * must be facing east on the corner of Avenue 6 and Street 3. The
	 * postcondition for this method is that Karel be facing west on
	 * the corner of Avenue 6 and Street 3.
	 */
	private void pickUpNewspaper() {
		pickBeeper();
		turnAround();
	}
	/**
	 * Returns Karel back to its initial position on the corner of
	 * Avenue 3 and Street 4, facing east. When returnHome is called,
	 * Karel must be facing west on the corner of Avenue 6 and Street
	 * 3.
	 */
	private void returnHome() {
		move();
		move();
		move();
		turnRight();
		move();
		turnRight();
	}
}