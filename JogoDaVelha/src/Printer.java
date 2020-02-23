import javax.swing.JOptionPane;

public class Printer {

	private static final String ROW = "-------------------";
	private static final String COLUMN = " | ";
	
	public static void printTitle(String title) {
		JOptionPane.showMessageDialog(null, title, title, 0);
		System.out.println(title + "\n");
	}
	
	public static void printNewGame(Player player1, Player player2) {
	
		System.out.println("New Game!");
		System.out.println(" " + player1.getPlayerName() + " (" + player1.getPlayerTeam() + ")  vs  " 
							+ player2.getPlayerName() + " (" + player2.getPlayerTeam() + ")");	
		System.out.println();
	}
	
	public static void printBoard(Board board) {
		
		System.out.print("    ");
		for (int y = 0; y < board.getSize(); y++) {
		System.out.print(COLUMN + y);
		}
		System.out.print(COLUMN + "\n" + ROW + "\n");
				
		for (int x = 0; x < board.getSize(); x++) {
			System.out.print(COLUMN + x);
			
			for (int y = 0; y < board.getSize(); y++) {
				System.out.print(COLUMN + board.getTile(x, y));
			}
			System.out.print(COLUMN + "\n" + ROW + "\n");
		}
		
		System.out.println();
	}

	public static void printResults(Player winner, Player player1, Player player2) {
		
		if (winner == null) {
			System.out.println("It was a tie...");
		} else { 
			System.out.println(" " + winner.getPlayerTeam() + " - " + winner.getPlayerName() + " wins!");
		}
		System.out.println();
		System.out.println("The score is " + player1.getPlayerName() + " - " + player1.getPlayerScore()
					+ "\n             " + player2.getPlayerName() + " - " + player2.getPlayerScore());

	}

	

}
