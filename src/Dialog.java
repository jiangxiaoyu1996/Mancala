import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Mancala game setup before starting.
 */
public class Dialog extends JDialog {
  /**
   * The width of dialog board.
   */
  public static final int WIDTH = 500;
  /**
   * The height of dialog board.
   */
  public static final int HEIGHT = 250;
  /**
   * The stone number in each pit
   */
  public static final int MIN_STONES = 3;
  /**
   * The stone number in each pit
   */
  public static final int MAX_STONES = 4;

  /**
   * The container object
   */
  private Container frame;
  /**
   * The stone number in each pit
   */
  private int stones = MIN_STONES;
  /**
   * The array list of layouts.
   */
  private Layout[] layoutList;
  /**
   * The current layout.
   */
  private Layout layout;

  /**
   * Construct game setup dialog board.
   * @param owner the frame to be set
   * @param layoutList the layout list to be set
   */
  public Dialog(Frame owner, Layout[] layoutList) {
	super(owner, true);
	this.setResizable(false);
	this.setSize(WIDTH, HEIGHT);

	this.layoutList = layoutList;
	//Set the layout to be the first layout from the layout list
	layout = layoutList[0];
	frame = getContentPane();

	//Create a panel to handle stone setup
	JPanel stonePanel = new JPanel();
    JLabel chooseStones = new JLabel("Number of stones: ");
	JRadioButton[] stoneButtons  = new JRadioButton[MAX_STONES - MIN_STONES + 1];
	ButtonGroup stoneBtnGroup = new ButtonGroup();

	//Create a panel to handle layout setup
	JPanel layoutPanel = new JPanel();
	JLabel chooseLayout = new JLabel("Which Layout: ");
	JRadioButton[] layoutButtons  = new JRadioButton[this.layoutList.length];
	ButtonGroup layoutBtnGroup = new ButtonGroup();

	//Create a button to start the Mancala game
	JButton startButton = new JButton("Start");

	//Add stone buttons and action listeners
	for (int i = 0; i <= MAX_STONES - MIN_STONES; i++) {
	  stoneButtons[i] = new JRadioButton(intToString(i + MIN_STONES), i + MIN_STONES == MIN_STONES);
	  stonePanel.add(stoneButtons[i]);
	  stoneBtnGroup.add(stoneButtons[i]);
	  final int index = i;
	  stoneButtons[i].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
					stones = index + MIN_STONES;
				}
	  });
	}

	//Add layout buttons and action listeners
	for (int i = 0; i < layoutList.length; i++) {
	  layoutButtons[i] = new JRadioButton(layoutList[i].getTitle(), i == 0);
	  layoutPanel.add(layoutButtons[i]);
	  layoutBtnGroup.add(layoutButtons[i]);
	  final Layout currentLayout = this.layoutList[i];
	  layoutButtons[i].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
					layout = currentLayout;
				}
	  });
	}

	//Add action listeners to start button
	startButton.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent event) {
		frame.setVisible(false);
		dispose();
	  }
	});

	//Create boxes to contain panels.
	Box stoneBox = Box.createHorizontalBox();
	stoneBox.add(Box.createHorizontalStrut(200));
	stoneBox.add(stonePanel);
	   
	Box layoutBox = Box.createHorizontalBox();
	layoutBox.add(Box.createHorizontalStrut(228));
	layoutBox.add(layoutPanel);
	   
	Box buttonBox = Box.createHorizontalBox();
	buttonBox.add(Box.createVerticalStrut(200));
	buttonBox.add(Box.createHorizontalStrut(400));
	buttonBox.add(startButton);
		
	frame.setLayout(new FlowLayout(FlowLayout.LEFT));
	frame.add(chooseStones);
	frame.add(stoneBox);
	frame.add(chooseLayout);
	frame.add(layoutBox);
	frame.add(buttonBox);
	}

  /**
   * Get current layout of the Mancala game.
   * @return the current layout of the Mancala game
   */
  public Layout getCurrentLayout() {
		return layout;
	}

  /**
   * Get the stone number of the Mancala game.
   * @return the stone number of the Mancala game
   */
  public int getStoneNumber() {
		return stones;
	}

  /**
   * Show dialog board.
   */
  public void showDialog() {
		this.setVisible(true);
	}

  //Change integer to string
  private static String intToString(int i) {
	switch (i) {
	  case 0:
		return "Zero";
	  case 1:
		return "One";
	  case 2:
		return "Two";
	  case 3:
		return "Three";
	  case 4:
		return "Four";
	  case 5:
		return "Five";
	  case 6:
		return "Six";
	  case 7:
		return "Seven";
	  case 8:
		return "Eight";
	  case 9:
		return "Nine";
	  default:
		return "";
	}
  }
}
