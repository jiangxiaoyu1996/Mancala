import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * The regular layout one class.
 */
public class LayoutOne extends Layout {

  /**
   * The random object for generating random numbers
   */
  private static Random rand = new Random();
  /**
   * The title of layout one
   */
  private String title = "Regular";

  @Override
  /**
   * Set the dimension of the layout
   * @param width the width to set
   * @param height the height to set
   */
  public void setDimensions(int width, int height) {
	super.setDimensions(width, height);
	rectangles[0] = new Rectangle2D.Double((this.width * 7 / 8) - 6, 0, this.width / 8, this.height - 1);
	rectangles[1] = new Rectangle2D.Double(0, 0 , this.width / 8, this.height-1);

	for (int i = 0; i < length; i++) {
	  pitContainers[0][i] = new Rectangle2D.Double(
	  		this.width / 8 * (i + 1), this.height / 2, this.width / 8, this.height / 2);
	  pitContainers[1][i] = new Rectangle2D.Double(
	  		this.width / 8 * (length - i), 0, this.width / 8, this.height / 2);
	}
  }

  @Override
  /**
   * Redraw the layout
   * @param g the graphics object
   * @param pits the 2D array of pits
   * @param mancalas the array of mancala
   */
  public void redraw(Graphics g, int[][] pits, int[] mancalas) {
	Graphics2D g2 = (Graphics2D) g;
		
	for (Rectangle2D.Double rectangle : rectangles) {
	  g2.draw(rectangle);
	}

	for (int i = 0; i < pitContainers.length; i++) {
	  for (Rectangle2D.Double rectangle : pitContainers[i]) {
		g2.draw(rectangle);
	  }
	}

	for (int i = 0; i < pits.length; i++) {
	  for (int stone = 0; stone < mancalas[i]; stone++) {
		setRandomColor(rand.nextDouble() * 10, g2);
		g2.fill(Stone.newStone(
				(int) rectangles[i].getX(),
				(int) rectangles[i].getY(),
				(int) rectangles[i].getWidth(),
				(int) rectangles[i].getHeight()
				)
		);
	  }
	  for (int j = 0; j < pits[i].length; j++)
		for (int stone = pits[i][j]; stone > 0; stone--) {
		  setRandomColor(rand.nextDouble() * 10, g2);
		  g2.fill(Stone.newStone(
		  		  (int) pitContainers[i][j].getX(),
				  (int) pitContainers[i][j].getY(),
				  (int) pitContainers[i][j].getWidth(),
				  (int) pitContainers[i][j].getHeight()
				  )
		  );
		}
	}
  }

  /**
   * Set colors to stones
   * @param randomNumb the random number
   * @param g2 Graphics2D object
   */
  public void setRandomColor(double randomNumb, Graphics2D g2) {
	if (randomNumb >= 0 && randomNumb < 1) {
	  g2.setPaint(Color.cyan);
	} else if (randomNumb >= 1 && randomNumb < 2) {
	  g2.setPaint(Color.black);
	} else if (randomNumb >= 3 && randomNumb < 4) {
	  g2.setPaint(Color.yellow);
	} else if (randomNumb >= 5 && randomNumb < 6) {
	  g2.setPaint(Color.orange);
	} else if (randomNumb >= 3 && randomNumb < 4) {
	  g2.setPaint(Color.yellow);
	} else if (randomNumb >= 5 && randomNumb < 6) {
	  g2.setPaint(Color.green);
	} else if (randomNumb >= 6 && randomNumb < 7) {
	  g2.setPaint(Color.gray);
	} else {
	  g2.setPaint(Color.pink);
	}
  }

  @Override
  /**
   * Get the title of layout
   * @return the title of layout
   */
  public String getTitle() {
		return this.title;
	}

}


