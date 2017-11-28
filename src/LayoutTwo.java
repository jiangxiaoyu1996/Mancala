import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * The dark layout two class.
 */
public class LayoutTwo extends Layout {
  /**
   * The title for layout
   */
  private String title = "Dark";

  @Override
  /**
   * Set the dimension of the layout
   * @param width the width to set
   * @param height the height to set
   */
  public void setDimensions(int width, int height) {
	super.setDimensions(width, height);
	rectangles[0] = new Rectangle2D.Double((this.width * 7 / 8) - 6, 0, this.width / 8, this.height - 1);
	rectangles[1] = new Rectangle2D.Double(0, 0 , this.width / 8, this.height - 1);

	for (int i = 0; i < length; i++) {
	  pitContainers[0][i] = new Rectangle2D.Double(
			  this.width / 8 * (i + 1),
			  this.height / 2,
			  this.width / 8,
			  this.height / 2
	  );
	  pitContainers[1][i] = new Rectangle2D.Double(
			  this.width / 8 * (length - i),
			  0,
			  this.width / 8,
			  this.height / 2
	  );
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
	  g2.setPaint(Color.black);
	  g2.fill(rectangle);
	  g2.setPaint(Color.white);
	  g2.draw(rectangle);
	}
		
	for (int i = 0; i < pitContainers.length; i++) {
	  for (Rectangle2D.Double rectangle : pitContainers[i]) {
		g2.setPaint(Color.black);
		g2.fill(rectangle);
		g2.setPaint(Color.white);
		g2.draw(rectangle);

	  }
	}

	for (int i = 0; i < pits.length; i++) {
	  g2.setPaint(Color.white);
	  for (int stone = 0; stone < mancalas[i]; stone++) {
		g2.draw(Stone.newStone(
				(int) rectangles[i].getX(),
				(int) rectangles[i].getY(),
				(int) rectangles[i].getWidth(),
				(int) rectangles[i].getHeight()
				)
		);
	  }
	  for (int j = 0; j < pits[i].length; j++)
		for (int stone = pits[i][j]; stone > 0; stone--) {
		  g2.draw(Stone.newStone(
				  (int) pitContainers[i][j].getX(),
				  (int) pitContainers[i][j].getY(),
				  (int) pitContainers[i][j].getWidth(),
				  (int) pitContainers[i][j].getHeight()
				  )
		  );
		}
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


