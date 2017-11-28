import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * The abstract layout class.
 */
public abstract class Layout {

  /**
   * The width of layout
   */
  protected int width;
  /**
   * The height of layout
   */
  protected int height;
  /**
   * The 2D rectangle array of pit containers
   */
  protected Rectangle2D.Double[][] pitContainers = new Rectangle2D.Double[2][6];
  /**
   * The rectangle array of mancala
   */
  protected Rectangle2D.Double[] rectangles = new Rectangle2D.Double[2];
  /**
   * The length of pits
   */
  protected int length = 6;

  /**
   * Set the dimension of the layout
   * @param width the width to set
   * @param height the height to set
   */
  public void setDimensions(int width, int height) {
	this.width = width;
	this.height = height;
  }

  /**
   * Get pit containers
   * @return pit containers
   */
  public Rectangle2D.Double[][] getPitContainers() {
	return pitContainers;
  }

  /**
   * Redraw the layout
   * @param g the graphics object
   * @param pits the 2D array of pits
   * @param mancalas the array of mancala
   */
  public abstract void redraw(Graphics g, int[][] pits, int[] mancalas);

  /**
   * Get the title of layout
   * @return the title of layout
   */
  public abstract String getTitle();

}

