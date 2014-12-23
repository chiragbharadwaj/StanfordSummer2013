/*
 * File: StoneMasonKarel.java
 * --------------------------
 * A program that "repairs the quad" at Stanford University after
 * the 1989 earthquake. It restores all the towers supporting the
 * arches in the quad by filling in stones (beepers) wherever
 * there aren't any.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		while (frontIsClear()) {
			fixOneTower();
			moveToNextTower();
		}
		while (frontIsBlocked()) {
			fixOneTowerOnly();
		}
	}
/**
 * Fixes one tower by replacing all the missing stones that are
 * needed to hold up the arches. When fixOneTower is called, Karel
 * needs to be facing east somewhere on Street 1. The postcondition
 * for this method is that Karel needs to again be facing east
 * somewhere on Street 1.
 */
	private void fixOneTower() {
		turnLeft();
		while (frontIsClear()) {
			if (noBeepersPresent()) {
				putBeeper();
				move();
			} else {
				move();
			}
		}
		while (frontIsBlocked()) {
			if (noBeepersPresent()) {
				putBeeper();
			}
			turnRight();
			turnRight();
			moveToWall();
			turnLeft();
		}
	}
/**
 * Fixes one tower by replacing all the missing stones that are
 * needed to hold up the arches. However, unlike fixOneTower,
 * fixOneTowerOnly does not return Karel to Street 1. When
 * fixOneTowerOnly is called, Karel needs to be facing east on the
 * corner of the last Avenue and Street 1. 
 */
	private void fixOneTowerOnly() {
		turnLeft();
		while (frontIsClear()) {
			if (noBeepersPresent()) {
				putBeeper();
				move();
			} else {
				move();
			}
		}
		while (frontIsBlocked()) {
			if (noBeepersPresent()) {
				putBeeper();
			}
		}
	}
/**
 * Moves Karel to the next tower by moving four times. When
 * moveToNextTower is called, Karel needs to be facing east
 * somewhere on Street 1. The postcondition for this method is that
 * Karel again be facing east somewhere on Street 1.
 */
	private void moveToNextTower() {
		move();
		move();
		move();
		move();
	}
/**
 * Moves Karel forward until it is blocked by a wall.
 */
	private void moveToWall() {
		while (frontIsClear()) {
			move();
		}
	}
}
