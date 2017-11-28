import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 * The Game model class in the Mancala MVC design
 */
public class Game {

  /**
   * The maximum undo time.
   */
  public static final int MAX_UNDOS = 1;

  /**
   * The 2D array for 12 pits.
   */
  private int[][] pits = new int[2][6];
  /**
   * The 2D array for undos.
   */
  private int[][] undos = new int[2][];
  /**
   * The array for storing player's latest two actions
   */
  private int[] undoTrack = new int[2];
  /**
   * The array for 2 mancala
   */
  private int[] mancalas = new int[2];
  /**
   * The array for 2 blank mancala
   */
  private int[] blankMancalas;
  /**
   * The array list for change listeners
   */
  private ArrayList<ChangeListener> listenerList = new ArrayList<ChangeListener>();
  /**
   * The current player
   */
  private int current = 0;
  /**
   * The current undo player
   */
  private int currentUndo = 0;
  /**
   * The boolean variable for undo decision
   */
  private boolean undo = false;
  /**
   * The boolean variable for free turn decision
   */
  private boolean isFree = false;
  /**
   * The boolean variable for game completion decision
   */
  private boolean isGameComplete = false;

  /**
   * Construct a game model object
   * @param stones the stone number in each pit
   */
  public Game(int stones) {
	for (int i = 0; i < 2; i++) {
	  //Initialize mancala A and B with 0
	  mancalas[i] = 0;
	  //Initialize undo track with 0
	  undoTrack[i] = 0;
	  //Initialize all pits with stone numbers
	  for (int j = 0; j < 6; j++) {
		pits[i][j] = stones;
	  }
	}
	//Set manacala A and B back to 0
	resetAll();
  }

  /**
   * Move the stones in the pits which player clicks on.
   * @param side the player side
   * @param pit the pit number on the side
   */
  public void move(int side, int pit) {
    //Check if it is the player's turn
	if (side != current) {
	  throw new IllegalArgumentException("It's not your turn!");
	}
	//If a pit is already empty, then return
	if (pits[side][pit] == 0) {
	  return;
	}

	resetAll();

	//A player gets a free turn
	if (isFree) {
	  undoTrack[side] = 0;
	  isFree = false;
	} else if (undoTrack[side] == 0) {
	  undoTrack[switchSide(side)] = 0;
	  currentUndo = side;
	}

	undo = true;
	int contain = pits[side][pit];
	pits[side][pit] = 0;
	while (0 < contain) {
	  pit = ++pit >= 6 ? 0 : pit;
	  if (pit == 0) {
		if (side == current) {
		  ++mancalas[side];
		  --contain;
		  if (contain <= 0) {
			arePitsEmpty();
			isFree = true;
			updateAll();
			return;
		  }
		}
		side = switchSide(side);
	  }
	  ++pits[side][pit];
	  --contain;
	}
	endTurn(side, pit);
  }

  /**
   * Undo player's latest action.
   */
  public void undo() {
	if (getGameStatus() || !undo || (current == currentUndo && !undo) || (undoTrack[currentUndo] == MAX_UNDOS)) {
	  return;
	}
		
	for (int i = 0; i < 2; i++) {
	  pits[i] = undos[i].clone();
	}

	undo = false;
	isFree = false;
	mancalas = blankMancalas.clone();
	undoTrack[currentUndo]++;
	current = currentUndo;
	updateAll();
  }

  /**
   * Update all change listener
   */
  public void updateAll() {
	for (ChangeListener listener : listenerList) {
      listener.stateChanged(new ChangeEvent(this));
    }
  }

  /**
   * Adds a change listener to the system
   * @param listener the change listener to be added
   */
  public void addChangeListener(ChangeListener listener) {
    listenerList.add(listener);
	updateAll();
  }

  /**
   * Get pits 2D array
   * @return the 2D array of pits
   */
  public int[][] getPits() {
		return pits.clone();
	}

  /**
   * Get undo count
   * @return the undo count
   */
  public int getUndoCount() {
		return MAX_UNDOS - undoTrack[currentUndo];
	}

  /**
   * Get array of mancala
   * @return the array of mancala
   */
  public int[] getMancalas() {
		return mancalas.clone();
	}

  /**
   * Get the current player
   * @return the current player
   */
  public int getCurrentUser() {
		return current + 1;
	}

  /**
   * Get the game status
   * @return the status of the game
   */
  public boolean getGameStatus() {
	return isGameComplete;
  }

  private void endTurn(int side, int pit) {
	System.out.println(side);
	System.out.println(current);
	System.out.println(pit);
	System.out.println(pits[side][pit]);
	if (pits[side][pit] == 1 && side == current) {
	  mancalas[side] += 1 + pits[switchSide(side)][6 - pit - 1];
	  System.out.println(mancalas[side]);
	  pits[side][pit] = 0;
	  pits[switchSide(side)][6 - pit - 1] = 0;
	  isFree = true;
	} else {
	  current = switchSide(current);
	}
    arePitsEmpty();
	updateAll();
  }
	
  private boolean arePitsEmpty() {
	int[][] tempPits = pits.clone();
	int empty;
	for (int i = 0; i < 2; i++) {
	  empty = 0;
	  for (int j = 0; j < 6; j++) {
		if (tempPits[i][j] == 0) {
		  empty++;
		}
	  }
	  if (empty == 6) {
		terminateGame(switchSide(i));
		return true;
	  }
	}
	return false;
  }
	
  private void terminateGame(int side) {
	isGameComplete = true;
	for (int i = 0; i < 6; i++) {
	  mancalas[side] += pits[side][i];
	  pits[side][i] = 0;
	}
	if (mancalas[current] == mancalas[switchSide(current)]) {
	  current = -1;
	  return;
	}
	if (mancalas[current] < mancalas[switchSide(current)]) {
	  current = switchSide(current);
	  return;
	}
  }

  private int switchSide(int side) {
		return ++side >= 2 ? 0 : side;
	}

  private void resetAll() {
	blankMancalas = mancalas.clone();
	for (int i = 0; i < 2; i++) {
	  undos[i] = pits[i].clone();
	}
  }
}
