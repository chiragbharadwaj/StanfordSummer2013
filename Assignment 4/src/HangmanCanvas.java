/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display in the original Hangman.java file (e.g. the scaffold,
 * the person, and the labels on the right half of the program).
 */

import acm.graphics.*;

import java.awt.Color;

public class HangmanCanvas extends GCanvas {
	
	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	
	/* Declares an empty string that will be modified later. */
	private String incorrectGuessString = "";

	/* Declares the basic y-offset height for the labels on the canvas (modified later). */
	private double labelYOffset = ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + 1.75*LEG_LENGTH;

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();

		double corner = (getWidth() / 2) - BEAM_LENGTH;	// Specifies the corner of beam/scaffold.
		double scaffoldEnd = corner + SCAFFOLD_HEIGHT;	// Specifies the end of the scaffold.
		double ropeEnd = corner + ROPE_LENGTH;			// Specifies the end of the rope.

		GLine scaffold = new GLine(corner, corner, corner, scaffoldEnd);
		scaffold.setColor(Color.RED);
		add(scaffold);

		GLine beam = new GLine(corner, corner, getWidth() / 2, corner);
		beam.setColor(Color.RED);
		add(beam);

		GLine rope = new GLine(getWidth() / 2, corner, getWidth() / 2, ropeEnd);
		rope.setColor(Color.RED);
		add(rope);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the game.  The argument
	 * string shows what letters have been guessed so far; unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		GLabel displayWord = new GLabel(word);
		displayWord.setFont("Courier-Bold-16");
		displayWord.setColor(Color.blue);

		/* A bunch of constants stored as double variables that specify position/size. */
		double width = displayWord.getWidth();
		double height = displayWord.getAscent();
		double horPosition = (getWidth() - width) / 2;
		double verPosition = labelYOffset + height;
		double elementX = horPosition;
		double elementY = verPosition;

		displayWord.setLocation(horPosition, verPosition);	// Sets position of label.
		if (getElementAt(elementX, elementY) != null) {	// Removes the existing label there if any
			remove(getElementAt(elementX, elementY));	// such label exists ("updating the label"). 
		}
		add(displayWord);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.  Calling this method
	 * causes the next body part to appear on the scaffold and adds the letter to the list of
	 * incorrect guesses that appears at the bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
		incorrectGuessString = incorrectGuessString + letter;

		int stringLength = incorrectGuessString.length();

		GLabel incorrectGuess = new GLabel(incorrectGuessString);
		incorrectGuess.setFont("Courier-12");

		/* A bunch of constants stored as double variables that specify position/size. */
		double width = incorrectGuess.getWidth();
		double height = incorrectGuess.getAscent();
		double horPosition = (getWidth() - width) / 2;
		double verPosition = labelYOffset + 4*height;
		double stringOffset = (width / (2*stringLength));
		double elementX = horPosition + stringOffset;
		double elementY = verPosition;
		
		incorrectGuess.setLocation(horPosition, verPosition);	// Sets position of the label.
		if (getElementAt(elementX, elementY) != null) {	// Removes the existing label there if any
			remove(getElementAt(elementX, elementY));	// such label exists ("Updating the label").
		}
		add(incorrectGuess);

		if (incorrectGuessString.length() == 1) {
			createHead();			// Adds the head to canvas.
		}
		if (incorrectGuessString.length() == 2) {
			createBody();			// Adds the body to canvas.
		}
		if (incorrectGuessString.length() == 3) {
			createUpperLeftArm();	// Adds the left shoulder to canvas.
			createLowerLeftArm();	// Adds the left arm to canvas.
		}
		if (incorrectGuessString.length() == 4) {
			createUpperRightArm();	// Adds the right shoulder to canvas.
			createLowerRightArm();	// Adds the right arm to canvas.
		}
		if (incorrectGuessString.length() == 5) {
			createLeftHip();		// Adds left hip to canvas.
			createLeftLeg();		// Adds left leg to canvas.
		}
		if (incorrectGuessString.length() == 6) {
			createRightHip();		// Adds right hip to canvas.
			createRightLeg();		// Adds right leg to canvas.
		}
		if (incorrectGuessString.length() == 7) {
			createLeftFoot();		// Adds left foot to canvas.
		}
		if (incorrectGuessString.length() == 8) {
			createRightFoot();		// Adds right foot to canvas.
			incorrectGuessString = "";	// Resets the incorrectGuessString for the next round.
		}
	}

	/* Method: createHead() */
	/** This method physically adds the head to the canvas. */
	private void createHead() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headX = (getWidth() / 2) - HEAD_RADIUS;
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH;
		
		GOval head = new GOval(headX, headY, 2*HEAD_RADIUS, 2*HEAD_RADIUS);
		add(head);
	}
	
	/* Method: createHead() */
	/** This method physically adds the body to the canvas. */
	private void createBody() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double bodyYEnd = bodyYStart + BODY_LENGTH;
		
		GLine body = new GLine(getWidth() / 2, bodyYStart, getWidth() /2, bodyYEnd);
		add(body);
	}
	
	/* Method: createHead() */
	/** This method physically adds the left shoulder to the canvas. */
	private void createUpperLeftArm() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double leftArmXStart = (getWidth() / 2) - UPPER_ARM_LENGTH;
		double armY = bodyYStart + ARM_OFFSET_FROM_HEAD;
		
		GLine upperLeftArm = new GLine(leftArmXStart, armY, getWidth() / 2, armY);
		add(upperLeftArm);
	}
	
	/* Method: createHead() */
	/** This method physically adds the left arm to the canvas. */
	private void createLowerLeftArm() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double leftArmXStart = (getWidth() / 2) - UPPER_ARM_LENGTH;
		double armY = bodyYStart + ARM_OFFSET_FROM_HEAD;
		double armYEnd = armY + LOWER_ARM_LENGTH;
		
		GLine lowerLeftArm = new GLine(leftArmXStart, armY, leftArmXStart, armYEnd);
		add(lowerLeftArm);
	}
	
	/* Method: createHead() */
	/** This method physically adds the right shoulder to the canvas. */
	private void createUpperRightArm() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double armY = bodyYStart + ARM_OFFSET_FROM_HEAD;
		double rightArmXStart = (getWidth() / 2) + UPPER_ARM_LENGTH;
		
		GLine upperRightArm = new GLine(getWidth() / 2, armY, rightArmXStart, armY);
		add(upperRightArm);
	}
	
	/* Method: createHead() */
	/** This method physically adds the right arm to the canvas. */
	private void createLowerRightArm() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double armY = bodyYStart + ARM_OFFSET_FROM_HEAD;
		double rightArmXStart = (getWidth() / 2) + UPPER_ARM_LENGTH;
		double armYEnd = armY + LOWER_ARM_LENGTH;
		
		GLine lowerRightArm = new GLine(rightArmXStart, armY, rightArmXStart, armYEnd);
		add(lowerRightArm);
	}
	
	/* Method: createHead() */
	/** This method physically adds the left hip to the canvas. */
	private void createLeftHip() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double bodyYEnd = bodyYStart + BODY_LENGTH;
		double leftHipStart = (getWidth() / 2) - HIP_WIDTH;
		
		GLine leftHip = new GLine(leftHipStart, bodyYEnd, getWidth() / 2, bodyYEnd);
		add(leftHip);
	}
	
	/* Method: createHead() */
	/** This method physically adds the left leg to the canvas. */
	private void createLeftLeg() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double bodyYEnd = bodyYStart + BODY_LENGTH;
		double leftHipStart = (getWidth() / 2) - HIP_WIDTH;
		double legYEnd = bodyYEnd + LEG_LENGTH;
		
		GLine leftLeg = new GLine(leftHipStart, bodyYEnd, leftHipStart, legYEnd);
		add(leftLeg);
	}
	
	/* Method: createHead() */
	/** This method physically adds the right hip to the canvas. */
	private void createRightHip() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double bodyYEnd = bodyYStart + BODY_LENGTH;
		double rightHipStart = (getWidth() / 2) + HIP_WIDTH;
		
		GLine rightHip = new GLine(getWidth() / 2, bodyYEnd, rightHipStart, bodyYEnd);
		add(rightHip);
	}
	
	/* Method: createHead() */
	/** This method physically adds the right leg to the canvas. */
	private void createRightLeg() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double bodyYEnd = bodyYStart + BODY_LENGTH;
		double rightHipStart = (getWidth() / 2) + HIP_WIDTH;
		double legYEnd = bodyYEnd + LEG_LENGTH;
		
		GLine rightLeg = new GLine(rightHipStart, bodyYEnd, rightHipStart, legYEnd);
		add(rightLeg);
	}
	
	/* Method: createHead() */
	/** This method physically adds the left foot to the canvas. */
	private void createLeftFoot() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double bodyYEnd = bodyYStart + BODY_LENGTH;
		double leftHipStart = (getWidth() / 2) - HIP_WIDTH;
		double legYEnd = bodyYEnd + LEG_LENGTH;
		double leftFootStart = leftHipStart - FOOT_LENGTH;
		
		GLine leftFoot = new GLine(leftFootStart, legYEnd, leftHipStart, legYEnd);
		add(leftFoot);	
	}

	/* Method: createHead() */
	/** This method physically adds the right foot to the canvas. */
	private void createRightFoot() {
		/* A bunch of constants stored as double variables that specify position/size. */
		double headY = (getWidth() / 2) - BEAM_LENGTH + ROPE_LENGTH; 
		double bodyYStart = headY + 2*HEAD_RADIUS;
		double bodyYEnd = bodyYStart + BODY_LENGTH;
		double rightHipStart = (getWidth() / 2) + HIP_WIDTH;
		double legYEnd = bodyYEnd + LEG_LENGTH;
		double rightFootStart = rightHipStart + FOOT_LENGTH;
		
		GLine rightFoot = new GLine(rightHipStart, legYEnd, rightFootStart, legYEnd);
		add(rightFoot);
	}
}