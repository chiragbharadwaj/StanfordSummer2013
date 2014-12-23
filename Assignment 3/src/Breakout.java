/*
 * File: Breakout.java
 * -------------------
 * Name: Chirag Bharadwaj
 * Section Leader: Thomas Bridges-Lyman
 * 
 * This program implements the game of Breakout in a Java Applet.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram implements MouseMotionListener {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Total number of bricks */
	private static final int NBRICKS_TOTAL = NBRICKS_PER_ROW * NBRICK_ROWS;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
		(WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** Pause time between turns in milliseconds */
	private static final int PAUSE_TIME = 10;


	/** Creates the random generator */
	private RandomGenerator rgen = RandomGenerator.getInstance();

	private GOval ball;

	/** Declares the x- and y-components of the velocity as
	 *  instance variables. */
	private double vx, vy;

	private int numberLives = NTURNS;
	private int numberTotalBricks = NBRICKS_TOTAL;
	

	/* Method: init() */
	/** Initializes the Breakout program. */
	public void init() {
		addMouseListeners();
	}

	/* Method: run() */
	/** Runs the Breakout program by creating the title screen,
	 *  waiting for the user to click, and thereafter setting up
	 *  the bricks, paddle, and ball. Upon a click, the game begins
	 *  and the user must move the paddle in sync with the ball. */
	public void run() {
		setUpBreakout();
		playBreakout();
	}

	/** Sets up Breakout by creating the bricks and the paddle. */
	private void setUpBreakout() {
		createBricks();
		createPaddle();
	}

	/** Begins the game of Breakout on a user-entered click by
	 *  implementing giveLifeToBall. There are various cases but
	 *  the game essentially follows the laws of physics. */
	private void playBreakout() {
		createBall();
		giveLifeToBall();
	}

	/** Physically creates the bricks by displaying 2 rows of
	 *  red, 2 rows of orange, 2 rows of yellow, 2 rows of green,
	 *  and 2 rows of cyan bricks. The remaining rows below that
	 *  are all colored cyan. */
	private void createBricks() {
		double yOffset = BRICK_Y_OFFSET;
		for (int i = 0; i < 2; i++) {
			createRows(yOffset, Color.RED);
			yOffset = yOffset + BRICK_HEIGHT + BRICK_SEP;
		}
		for (int i = 0; i < 2; i++) {
			createRows(yOffset, Color.ORANGE);
			yOffset = yOffset + BRICK_HEIGHT + BRICK_SEP;
		}
		for (int i = 0; i < 2; i++) {
			createRows(yOffset, Color.YELLOW);
			yOffset = yOffset + BRICK_HEIGHT + BRICK_SEP;
		}
		for (int i = 0; i < 2; i++) {
			createRows(yOffset, Color.GREEN);
			yOffset = yOffset + BRICK_HEIGHT + BRICK_SEP;
		}
		for (int i = 0; i < 2; i++) {
			createRows(yOffset, Color.CYAN);
			yOffset = yOffset + BRICK_HEIGHT + BRICK_SEP;
		}

		int numberRowsRemaining = NBRICK_ROWS - 10;
		for (int i = 0; i < numberRowsRemaining; i++) {
			createRows(yOffset, Color.CYAN);
			yOffset = yOffset + BRICK_HEIGHT + BRICK_SEP;
		}
	}

	/** Creates the rows that the bricks are placed in. */
	private void createRows(double verPosition, Color color) {
		double horPosition = BRICK_SEP / 2;
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {			
			GRect brick = new GRect(horPosition, verPosition, BRICK_WIDTH, BRICK_HEIGHT);
			brick.setColor(color);
			brick.setFilled(true);
			brick.setFillColor(color);
			add(brick);
			horPosition = horPosition + BRICK_WIDTH + BRICK_SEP;
		}
	}

	/* Declares the instance variable paddle. */
	private GRect paddle;

	/** Physically creates the paddle but does not give it the
	 *  moving functionality that will later be added. */
	private void createPaddle() {
		double paddleHorLocation = (getWidth() - PADDLE_WIDTH) / 2;
		double paddleVerLocation = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle = new GRect(paddleHorLocation, paddleVerLocation, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setFillColor(Color.BLACK);
		add(paddle);
	}

	/* Declares the instance variable lastX. */
	private double lastX;

	/** Gives the paddle functionality based on user mouse
	 *  movements. Since the paddle is controlled by the mouse at
	 *  its center, the mouse is restricted from moving half of a
	 *  paddle width within both the left and right walls so as to
	 *  keep the entire paddle visible on screen at all times. */
	public void mouseMoved(MouseEvent e) {
		lastX = e.getX();
		if (lastX > (PADDLE_WIDTH / 2) && lastX < (getWidth() - PADDLE_WIDTH / 2)) {
			paddle.setLocation(e.getX(), getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			paddle.setBounds((lastX - PADDLE_WIDTH / 2), getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
		}
	}

	/** Physically creates the ball on the screen and centers it. */
	private void createBall() {
		ball = new GOval((getWidth() / 2) - BALL_RADIUS, (getHeight() / 2 - BALL_RADIUS), 2*BALL_RADIUS, 2*BALL_RADIUS);
		ball.setColor(Color.black);
		ball.setFilled(true);
		ball.setFillColor(Color.BLACK);
		add(ball);
	}

	/** Allows the ball to move with a certain velocity. Several
	 *  cases are listed, each of which contain direction as to
	 *  how to ball should react when colliding with another
	 *  object. These cases also discuss what to show when the
	 *  game ends (either by the user getting rid of all of the
	 *  bricks or losing all of his/her/its lives). */
	private void giveLifeToBall() {
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		vy = 3.0;
		while (numberTotalBricks != 0) {
			ball.move(vx, vy);
			if (ballHitRightWall()) {
				bounceOffRightWall();
			}
			if (ballHitLeftWall()) {
				bounceOffLeftWall();
			}
			if (ballHitCeiling()) {
				bounceOffCeiling();
			}
			if (ballHitFloor()) {
				if (numberLives > 0) {
					restart();
				} else {
					displayGameOver();
				}
			}
			checkCollider();
			pause(PAUSE_TIME);
		}
		if (numberTotalBricks == 0) {
			displayYouWin();
		}
	}

	/** Checks to see what the ball collided with. If it is the
	 *  paddle, the ball bounces. If it is a brick, it removes
	 *  the brick and bounces off. All movements are based on the
	 *  laws of physics. Also keeps track of the number of bricks
	 *  left in the grid (but doesn't limit it). */
	private void checkCollider() {
		GObject collider = getCollidingObject();
		if (collider == paddle) {
			vy = -1 * Math.abs(vy);
		} else if ((collider != null)) {
			remove(collider);
			numberTotalBricks = numberTotalBricks - 1;
			vy = -vy;
		}
	}

	/** Checks if the ball has hit the right wall. */
	private boolean ballHitRightWall() {
		return distanceFromRightWall() <= 0;
	}

	/** Calculates the distance between the ball and right wall. */
	private double distanceFromRightWall() {
		double ballRightSide = ball.getX() + 2 * BALL_RADIUS;
		return getWidth() - ballRightSide;
	}

	/** Allows the ball to bounce off of the right wall. */
	private void bounceOffRightWall() {
		vx = -vx;
		double distanceToRightOfRightWall = -1 * distanceFromRightWall();
		ball.move(-2 * distanceToRightOfRightWall, 0);
	}

	/** Checks if the ball has hit the left wall. */
	private boolean ballHitLeftWall() {
		return distanceFromLeftWall() <= 0;
	}

	/** Calculates the distance between the ball and left wall. */
	private double distanceFromLeftWall() {
		double ballLeftSide = ball.getX();
		return ballLeftSide;
	}

	/** Allows the ball to bounce off of the left wall. */
	private void bounceOffLeftWall() {
		vx = -vx;
		double distanceToLeftOfLeftWall = -1 * distanceFromLeftWall();
		ball.move(2 * distanceToLeftOfLeftWall, 0);
	}

	/** Checks if the ball has hit the ceiling. */
	private boolean ballHitCeiling() {
		return distanceBelowCeiling() <= 0;
	}

	/** Calculates the distance between the ball and ceiling. */
	private double distanceBelowCeiling() {
		double ballTop = ball.getY();
		return ballTop;
	}

	/** Allows the ball to bounce off of the ceiling. */
	private void bounceOffCeiling() {
		vy = -vy;
		double distanceAboveCeiling = -1 * distanceBelowCeiling();
		ball.move(0, 2 * distanceAboveCeiling);
	}

	/** Checks if the ball has hit the floor. */
	private boolean ballHitFloor() {
		return distanceAboveFloor() <= 0;
	}

	/** Calculates the distance between the ball and floor. */
	private double distanceAboveFloor() {
		double ballBottom = ball.getY() + 2 * BALL_RADIUS;
		return getHeight() - ballBottom;
	}

	/* Declares the instance variable restart. */
	private GLabel restart;
	
	/** Adds the label that tells the user to click to reset the
	 *  ball at the center of the screen. */
	private void addRestart() {
		restart = new GLabel ("Click anywhere to reset the ball.");
		restart.setFont("Helvetica-12");
		double restartHorPosition = (getWidth() - restart.getWidth()) / 2;
		double restartVerPosition = (getHeight() - restart.getAscent()) / 2;
		restart.setLocation(restartHorPosition, restartVerPosition);
		restart.setColor(Color.BLUE);
		add(restart);
	}
	
	/** If the ball hits the floor and there are one or more
	 *  lives left, this will reset the ball and subtract one
	 *  from the counter for the number of lives. */
	private void restart() {
		remove(ball);
		numberLives = numberLives - 1;
		addRestart();
		waitForClick();
		remove(restart);
		createBall();
	}

	/** This displays the Game Over screen upon losing. */
	private void displayGameOver() {
		GLabel gameOver = new GLabel ("GAME OVER");
		gameOver.setFont("Helvetica-Bold-24");
		double overHorPosition = (getWidth() - gameOver.getWidth()) / 2;
		double overVerPosition = (getHeight() - gameOver.getAscent()) / 2;
		gameOver.setLocation(overHorPosition, overVerPosition);
		add(gameOver);
	}

	/** This displays the Congratulations screen upon winning. */
	private void displayYouWin() {
		GLabel youWin = new GLabel ("Congratulations! You won!");
		youWin.setFont("Helvetica-Bold-18");
		double winHorPosition = (getWidth() - youWin.getWidth()) / 2;
		double winVerPosition = (getHeight() - youWin.getAscent()) / 2;
		youWin.setLocation(winHorPosition, winVerPosition);
		add(youWin);
	}

	/** This checks the four corners that define the ball (GOval)
	 *  to see if they overlap with another object. This will allow
	 *  the program to know if a collision has occurred, be it
	 *  with the paddle or the bricks. This is used later to allow
	 *  the bricks to be removed correctly and for the ball to
	 *  bounce off the paddle rather than remove the paddle. */
	private GObject getCollidingObject() {
		if (getElementAt(ball.getX(), ball.getY()) != null) {
			return getElementAt(ball.getX(), ball.getY());
		} else if (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS) != null) {
			return getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS) != null) {
			return getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()) != null) {
			return getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		} else
			return null;
	}
}