import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * The GUI class to present board component of Mancala game.
 */
public class Board extends JComponent {

  /**
   * The layout type of the game board
   */
  private Layout layout;
  /**
   * The mancala array
   */
  private int[] mancalas;
  /**
   * The 2D array pits
   */
  private int[][] pits;

  /**
   * Construct a board object with given layout
   * @param layout the layout to set
   */
  public Board(Layout layout) {
		this.layout = layout;
	}

  /**
   * Get pit containers
   * @return pit containers
   */
  public Rectangle2D.Double[][] getPitContainers() {
		return layout.getPitContainers();
	}

  /**
   * Set board size
   * @param width the width to set
   * @param height the height to set
   */
  public void setBoardSize(int width, int height) {
	setPreferredSize(new Dimension(width, height));
	layout.setDimensions(width, height);
  }

  /**
   * Paint the component
   * @param g the graphics object
   */
  public void paintComponent(Graphics g) {
		layout.redraw(g, pits, mancalas);
	}

  /**
   * Initialize the Mancala game
   * @param pits the 2D array pits to set
   * @param mancalas the array mancala to set
   */
  public void initializeGame(int[][] pits, int[] mancalas) {
	this.pits = pits;
	this.mancalas = mancalas;
  }
}
