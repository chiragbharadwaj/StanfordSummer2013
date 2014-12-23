/*
 * File: Target.java
 * Name: Chirag Bharadwaj
 * Section Leader: Kristen Carnohan
 * -----------------
 * This program creates a target (with a bulls-eye) by using three
 * GOvals centered in the middle of a Graphics window. Also, just
 * for kicks, this program puts a nice box around it and makes it
 * a very special Target Gift Card that's worth $25!
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

// Runs the program. //

public class Target extends GraphicsProgram {	

	// Private constants. //

	private static final double OUTER_OVAL_WIDTH = 72;
	private static final double OUTER_OVAL_HEIGHT = 72;
	private static final double MIDDLE_OVAL_WIDTH = 46.8;
	private static final double MIDDLE_OVAL_HEIGHT = 46.8;
	private static final double INNER_OVAL_WIDTH = 21.6;
	private static final double INNER_OVAL_HEIGHT = 21.6;

	// Generates the gift card graphic. //

	public void run() {
		double u = (getWidth() - OUTER_OVAL_WIDTH) / 2;
		double v = (getHeight() - OUTER_OVAL_HEIGHT) / 2;
		GOval outerRedOval = new GOval(u, v, OUTER_OVAL_WIDTH, OUTER_OVAL_HEIGHT);
		outerRedOval.setFilled(true);
		outerRedOval.setFillColor(Color.RED);
		outerRedOval.setColor(Color.RED);
		add(outerRedOval);

		double w = (getWidth() - MIDDLE_OVAL_WIDTH) / 2;
		double x = (getHeight() - MIDDLE_OVAL_HEIGHT) / 2;
		GOval whiteOval = new GOval(w, x, MIDDLE_OVAL_WIDTH, MIDDLE_OVAL_HEIGHT);
		whiteOval.setFilled(true);
		whiteOval.setFillColor(Color.WHITE);
		whiteOval.setColor(Color.WHITE);
		add(whiteOval);

		double y = (getWidth() - INNER_OVAL_WIDTH) / 2;
		double z = (getHeight() - INNER_OVAL_HEIGHT) / 2;
		GOval innerRedOval = new GOval(y, z, INNER_OVAL_WIDTH, INNER_OVAL_HEIGHT);
		innerRedOval.setFilled(true);
		innerRedOval.setFillColor(Color.RED);
		innerRedOval.setColor(Color.RED);
		add(innerRedOval);

		double a = (getWidth()) / 2 - 51;
		double b = 2 * (getHeight()) / 3;
		GLabel logo = new GLabel("TARGET", a, b);
		logo.setFont("Garamond-Bold-24");
		add(logo);

		double c = (getWidth()) / 2 - 60;
		double d = 2 * (getHeight()) / 3 + 24;
		GLabel slogan = new GLabel("Expect more, pay less.", c, d);
		slogan.setFont("Garamond-Italic-16");
		add(slogan);

		double e = (getWidth()) / 2 + 60;
		double f = (getHeight()) / 2 - 102;
		GLabel amount = new GLabel("$25", e, f);
		amount.setFont("Lucida Grande-Bold-18");
		add(amount);

		double g = (getWidth()) / 2 - 92;
		double h = (getHeight()) / 2 - 102;
		GLabel giftCard = new GLabel("Gift Card", g, h);
		giftCard.setFont("Lucida Grande-18");
		add(giftCard);

		double i = (getWidth()) / 2 - 100;
		double j = (getHeight()) / 2 - 125;
		GRect rect = new GRect(i, j, 200, 250);
		add(rect);
	}
}
