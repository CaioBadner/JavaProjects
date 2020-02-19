import javax.swing.JOptionPane;

public class JogoDaVelhaMain {

	public static final String TITLE = "  JOGO DA VELHA  ";
	
	public static void main(String[] args) {
		
		JOptionPane.showMessageDialog(null, TITLE, TITLE, 0);
		System.out.println(TITLE + "\n");
		
		Board board = new Board(3);
		Player player1 = getNewPlayer();
		Player player2;
		
		if (JOptionPane.showConfirmDialog(null, "Do you want to play against a friend?", 
				TITLE, JOptionPane.YES_NO_OPTION) == 0) {
			player2 = getNewPlayer(player1.getPlayerTeam());
		} else {
			if (player1.getPlayerTeam() == 'X') {
				player2 = new Player('O');
			} else {
				player2 = new Player('X');
			}
		}
		
		System.out.println(" " + player1.getPlayerName() + " x " + player2.getPlayerName() + "\n");		
		
		board.printBoard();
		
		int round = 1;
		
		while(!isGameOver() && round < 10) {
			
			int[] playerMove;
			
			if (round  % 2 != 0) {
				playerMove = player1.getPlayerMove(board);
			} else {
				playerMove = player2.getPlayerMove(board);
			}
			
			
			board.printBoard();
			
			
			round++;
		}
		
	}



	private static boolean isGameOver() {

		return false;
	}



	private static Player getNewPlayer() {
		Player player;
		
		String firstPlayerName = JOptionPane.showInputDialog(null, "What is your name?", TITLE, 3);
		
		if (JOptionPane.showConfirmDialog(null, "Do you want to be the Xs?", TITLE, JOptionPane.YES_NO_OPTION) == 0) {
			player = new Player('X', firstPlayerName);
		} else {
			player = new Player('O', firstPlayerName);
		}
		return player;
	}
	
	private static Player getNewPlayer(char playerTeam) {
		Player player;
		char newTeam = 'O'; 
		if (playerTeam == 'O') {
			newTeam = 'X';
		}
		
		String playerName = JOptionPane.showInputDialog(null, "What is your name?", TITLE, 3);
		
		player = new Player(newTeam, playerName);

		return player;
	}
}