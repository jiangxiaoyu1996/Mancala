/**
 * The Tester of the Mancala game program.
 */
public class MancalaTester {
	public static void main(String[] args) {
		Layout[] layouts = { new LayoutOne(), new LayoutTwo() };
		MancalaViewAndController controller = new MancalaViewAndController(layouts);
	}
}
