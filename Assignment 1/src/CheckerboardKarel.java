/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * A program that uses Karel to draw a checkerboard pattern using
 * beepers on any predetermined grid/world size.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
/**
 * Executes the checkerboard pattern by dropping beepers on every
 * alternate square. When normalCheckerBoard or columnCheckerBoard
 * is called, Karel must be facing east somewhere on Street 1 on
 * any grid/world.
 */
	public void run() {
		if (frontIsClear()) {
			normalCheckerBoard();
		} else {
			columnCheckerBoard();
		}
	}
/**
 * Exectes the checkerboard pattern for any grid/world that is NOT
 * a single column. When normalCheckerBoard is called, Karel must
 * be facing east somewhere on Street 1 on any grid/world.
 */
	private void normalCheckerBoard() {
		while (frontIsClear()) {
			staggerRow();
			turnAround();
			moveToWall();
			turnRight();
			if (frontIsClear()) {
				move();
				turnRight();
				move();
				staggerRow();
				turnAround();
				moveToWall();
				turnRight();
				if (frontIsClear()) {
					move();
					turnRight();
				}
			}
		}
	}
/**
 * Executes the checkerboard pattern for any single column grid/
 * world. When columnCheckerBoard is called, Karel must be facing
 * east somewhere on Street 1 on any grid/world. 
 */
	private void columnCheckerBoard() {
		turnLeft();
		while (frontIsClear()) {
			putBeeper();
			move();
			if (frontIsClear()) {
				move();
			}
		}
		turnAround();
		move();
		if (noBeepersPresent()) {
			turnAround();
			move();
			putBeeper();
		}
	}
/**
 * Physically creates the checkerboard pattern by dropping beepers
 * on every alternate square along a row. When staggerRow is
 * called, Karel must be on the first square in any row (basically
 * anywhere along Avenue 1). 
 */
	private void staggerRow() {
		putBeeper();
		while (frontIsClear()) {
			if (frontIsClear()) {
				move();
			}
			if (frontIsClear()) {
				move();
				putBeeper();
			}
		}
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