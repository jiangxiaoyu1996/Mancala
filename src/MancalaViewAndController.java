import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

/**
 * The view and controller of Mancala game MVC design.
 */
public class MancalaViewAndController extends JFrame implements ActionListener, ChangeListener, MouseListener {
  /**
   * The model of the Mancala game MVC design
   */
  private Game game;
  /**
   * The Mancala game board
   */
  private Board board;
  /**
   * The undo function button
   */
  private JButton undo;
  /**
   * The current player label
   */
  private JLabel currentPlayer;

  /**
   * Construct Manacala game frame.
   * @param layouts the array of layouts
   */
  public MancalaViewAndController(Layout[] layouts) {
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLayout(new BorderLayout());
	this.setSize(1000, 500);

	//Construct a dialog object
	Dialog dialog = new Dialog(this, layouts);
	dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	dialog.showDialog();

	//Construct a game model object
	game = new Game(dialog.getStoneNumber());
	board = new Board(dialog.getCurrentLayout());
	game.addChangeListener(this);

	//Construct a game board object
	board.setBoardSize(650, 300);
	board.addMouseListener(this);

	//Construct a current player label
	currentPlayer = new JLabel("Turn: Player " + Integer.toString(game.getCurrentUser()));
	currentPlayer.setPreferredSize(new Dimension(600, 20));

	//Construct a undo function button
	undo = new JButton("Undo: " + game.getUndoCount());
	undo.addActionListener(this);

	//Create a game panel
	JPanel gamePanel = new JPanel();
	gamePanel.setLayout(new BorderLayout());

	//North part of the game panel
	JPanel northPanel = new JPanel();
	northPanel.setLayout(new BorderLayout());

	JLabel playerB = new JLabel("<---------- PLAYER B", SwingConstants.CENTER);
	Font playerFont = new Font("Serif", Font.BOLD, 20);
	playerB.setFont(playerFont);
	northPanel.add(playerB, BorderLayout.NORTH);

	JLabel pitsB = new JLabel("B6            B5            B4            B3            B2            B1", SwingConstants.CENTER);
	Font pitFont = new Font("Serif", Font.BOLD, 18);
	pitsB.setFont(pitFont);
	northPanel.add(pitsB, BorderLayout.CENTER);

	//East part of the game panel
	JPanel eastPanel = new JPanel();
	eastPanel.setPreferredSize(new Dimension(50, 200));
	eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
	eastPanel.add(new JLabel("     A"), SwingConstants.CENTER);
	eastPanel.add(new JLabel("      "), SwingConstants.CENTER);
	eastPanel.add(new JLabel("     A"), SwingConstants.CENTER);
	eastPanel.add(new JLabel("     L"), SwingConstants.CENTER);
	eastPanel.add(new JLabel("     A"), SwingConstants.CENTER);
	eastPanel.add(new JLabel("     C"), SwingConstants.CENTER);
	eastPanel.add(new JLabel("     N"), SwingConstants.CENTER);
	eastPanel.add(new JLabel("     A"), SwingConstants.CENTER);
	eastPanel.add(new JLabel("     M"), SwingConstants.CENTER);

	//South part of the game panel
	JPanel southPanel = new JPanel();
	southPanel.setLayout(new BorderLayout());

	JLabel pitsA = new JLabel("A1            A2            A3            A4            A5            A6", SwingConstants.CENTER);
	pitsA.setFont(pitFont);
	southPanel.add(pitsA, BorderLayout.NORTH);

	JLabel playerA = new JLabel("PLAYER A ---------->", SwingConstants.CENTER);
	playerA.setFont(playerFont);
	southPanel.add(playerA, BorderLayout.CENTER);

	//West part of the game panel
	JPanel westPanel = new JPanel();
	westPanel.setPreferredSize(new Dimension(50, 200));
	westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
	westPanel.add(new JLabel("     B"), SwingConstants.CENTER);
	westPanel.add(new JLabel("      "), SwingConstants.CENTER);
	westPanel.add(new JLabel("     A"), SwingConstants.CENTER);
	westPanel.add(new JLabel("     L"), SwingConstants.CENTER);
	westPanel.add(new JLabel("     A"), SwingConstants.CENTER);
	westPanel.add(new JLabel("     C"), SwingConstants.CENTER);
	westPanel.add(new JLabel("     N"), SwingConstants.CENTER);
	westPanel.add(new JLabel("     A"), SwingConstants.CENTER);
	westPanel.add(new JLabel("     M"), SwingConstants.CENTER);

	//Add all components to game panel
	gamePanel.add(northPanel, BorderLayout.NORTH);
	gamePanel.add(eastPanel, BorderLayout.EAST);
	gamePanel.add(southPanel, BorderLayout.SOUTH);
	gamePanel.add(westPanel, BorderLayout.WEST);
	gamePanel.add(board, BorderLayout.CENTER);


	//Create a panel for current player and undo
	JPanel buttomPanel = new JPanel();
	buttomPanel.setLayout(new FlowLayout());
	buttomPanel.add(currentPlayer);
	buttomPanel.add(undo);


	add(gamePanel, BorderLayout.NORTH);
	add(buttomPanel, BorderLayout.CENTER);
	pack();
	setVisible(true);
  }

  /**
   * Undo the player's action
   * @param event the action event
   */
  public void actionPerformed(ActionEvent event) {
	game.undo();
	undo.setText("Undo: " + game.getUndoCount());
	currentPlayer.setText("Turn: Player " + Integer.toString(game.getCurrentUser()));
  }

  /**
   * Change the state
   * @param event the change event
   */
  public void stateChanged(ChangeEvent event) {
	board.initializeGame(game.getPits(), game.getMancalas());
	board.repaint();
	if (game.getGameStatus()) {
	  if (game.getCurrentUser() - 1 < 0) {
		JOptionPane.showMessageDialog(this, "Draw", "Game Over", JOptionPane.INFORMATION_MESSAGE);
	  } else {
		JOptionPane.showMessageDialog(this, "Player " + game.getCurrentUser() + " has won!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
	  }
	}
  }

  /**
   * Move the stone
   * @param event the event of the mouse
   */
  public void mousePressed(MouseEvent event) {
    //If the game is completed, then return
	if (game.getGameStatus()) {
	  return;
	}
	Rectangle2D.Double[][] containers = board.getPitContainers();
	for (int i = 0; i < containers.length; i++) {
	  for (int j = 0; j < containers[i].length; j++) {
	    //If pressed point is in the pit
		if (containers[i][j].contains(event.getPoint())) {
		  try {
			game.move(i, j);
			//Increase undo number
			undo.setText("Undo: " + game.getUndoCount());
			//Change turn information
			currentPlayer.setText("Turn: Player " + Integer.toString(game.getCurrentUser()));
		  } catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "You can't make that move!", JOptionPane.ERROR_MESSAGE);
		  }
		}
	  }
	}
  }

  /**
   * Click the stone
   * @param e the mouse event
   */
  public void mouseClicked(MouseEvent e) { }

  /**
   * Release the mouse
   * @param e the mouse event
   */
  public void mouseReleased(MouseEvent e) { }

  /**
   * Enter the mouse
   * @param e the mouse event
   */
  public void mouseEntered(MouseEvent e) { }

  /**
   * Exit the mouse
   * @param e the mouse event
   */
  public void mouseExited(MouseEvent e) { }
}
