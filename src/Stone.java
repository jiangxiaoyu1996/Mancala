import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * The stone object class.
 */
public class Stone {

  /**
   * A random ojbect for generating random numbers.
   */
  private static Random rand = new Random();

  /**
   * Create a new stone in the given pit.
   * @param x the left-upper corner x coordinate of parent pit
   * @param y the left-upper corner y coordinate of parent pit
   * @param width the width of parent pit
   * @param height the height of parent pit
   * @return a stone in the parent pit
   */
  public static Ellipse2D.Double newStone(int x, int y, int width, int height) {
    	//Create two random numbers
		double random1 = rand.nextDouble() * 2 * Math.PI;
		double random2 = rand.nextDouble() * 2 * Math.PI;

		//Define the stone radius to be 1/8 of the pit's width
		int circleRadius = width / 8;

		//The center point of the parent pit
		int midX = x + width / 2;
		int midY = y + height / 2;

		double offsetX = Math.cos(random1) * Math.sqrt(7/2);
		double offsetY = Math.sin(random2) * Math.sqrt(7/2);

		//The center of the stone
		int centerX = (int) (midX + circleRadius * offsetX);
		int centerY = (int) (midY + circleRadius * offsetY);

		return new Ellipse2D.Double(
			centerX - circleRadius,
			centerY - circleRadius,
			circleRadius * 2,
			circleRadius * 2
		);
	}
}