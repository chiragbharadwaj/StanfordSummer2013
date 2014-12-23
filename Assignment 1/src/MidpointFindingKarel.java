/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * A program that locates the midpoint of Street 1 in any grid/
 * world and places a beeper on it to mark the spot.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

/**
 * Executes the procedure that locates the midpoint(s) of Street 1
 * and drops a beeper on that point (or one of the midpoints).
 */
	public void run() {
		if (frontIsClear()) {
			beginRowEnds();
			while (noBeepersPresent()) {
				fillRowByAlternating();
			}
			removeRightSideBeepers();
			removeLeftSideBeepers();
			putBeeperOnMidpoint();
		} else {
			putBeeper();
		}
	}
/**
 * Marks the ends of the Street so that Karel is flanked by a
 * beeper on either end. This will initiate fillRowByAlternating's
 * conditions.
 */
	private void beginRowEnds() {
		putBeeper();
		moveToWall();
		putBeeper();
		turnAround();
		move();
	}
/**
 * Fills the entire Street 1 with beepers by oscillating between
 * the ends of the street. The last beeper is placed on the point
 * designated as the midpoint (the exact midpoint for an odd-sized
 * grid/world and the right midpoint for an even-sized grid/world).
 */
	private void fillRowByAlternating() {
		while (frontIsClear() && noBeepersPresent()) {
			move();
		}
		turnAround();
		move();
		putBeeper();
		move();
	}
/**
 * Removes all of the beepers to the right of the midpoint.
 */
	private void removeRightSideBeepers() {
		while (frontIsClear()) {
			pickBeeper();
			move();
		}
		pickBeeper();
		turnAround();
		moveToWall();
		turnAround();
	}
/**
 * Removes all of the beepers to the left of the midpoint.
 */
	private void removeLeftSideBeepers() {
		while (frontIsClear() && beepersPresent()) {
			pickBeeper();
			move();
		}
	}
/**
 * The above methods will end up removing the beeper on the
 * midpoint and move an extra space forward due to the way that
 * they are defined. To make up for this, putBeeperOnMidpoint
 * simply returns Karel to the point designated as the midpoint
 * and drops a beeper there.
 */
	private void putBeeperOnMidpoint() {
		turnAround();
		move();
		putBeeper();
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