import javax.swing.JOptionPane;

public class JogoDaVelhaMain {

	public static final String TITLE = "  JOGO DA VELHA  ";
	
	public static void main(String[] args) {
		
		Printer.printTitle(TITLE);
		
		Player winner = null;
		Player player1 = getNewPlayer();
		Player player2 = getPlayer2(player1.getPlayerTeam());
		
		if (player2.isRobot()) {
			JOptionPane.showMessageDialog(null, "Your opponent will be " + player2.getPlayerName() + 
					" with the " + player2.getPlayerTeam() + "s", "Jogo da Velha", 1);
			}
		
		int game = 0;
		
		boolean player1Moves = true;
		
		do {
			
			Printer.printNewGame(player1, player2);
			
			Board board = new Board(3);
			Printer.printBoard(board);
			
			int round = 1;
			int lastRound = board.getSize() * board.getSize() + 1;
			
			winner = null;
			boolean isGameOver = false;
			
			while(!isGameOver && round < lastRound) {
				
				if ((round + game) % 2 == 0) {
					player1Moves = false; 
				} else {
					player1Moves = true;
				}
				
				Move playerMove;
				
				if (player1Moves) {
					playerMove = player1.getPlayerMove(board);
				} else {
					playerMove = player2.getPlayerMove(board);
				}
				
				board.setTile(playerMove);
				Printer.printBoard(board);
				
				if (round > 4) {
					if (player1Moves) {
						if (player1.didPlayerWin(board)) {
							player1.setPlayerScore(player1.getPlayerScore() + 1);
							isGameOver = true;
							winner = player1;
						}
					} else {
						if (player2.didPlayerWin(board)) { 
							player2.setPlayerScore(player2.getPlayerScore() + 1);
							isGameOver = true;
							winner = player2;
						}
					}
				}
				round++;
				board.setRound(round);
			}
			Printer.printResults(winner, player1, player2);
			game++;
			
	} while (JOptionPane.showConfirmDialog(null, "Another round?", TITLE, JOptionPane.YES_NO_OPTION) == 0);

}

	


	//this is the method to get the first player
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
	
	//this is the version for when the second player is human
	private static Player getNewPlayer(char oppositeTeam) {
		Player player;
		char newTeam = 'O'; 
		if (oppositeTeam == 'O') {
			newTeam = 'X';
		}
		String playerName = JOptionPane.showInputDialog(null, "What is your friend's name?", TITLE, 3);
		player = new Player(newTeam, playerName);
		return player;
	}

	//and this is what finds out if the second player will be human or a computer, and if so it calls the constructor
	private static Player getPlayer2(char oppositeTeam) {
		if (JOptionPane.showConfirmDialog(null, "Do you want to play against a friend?", 
				TITLE, JOptionPane.YES_NO_OPTION) == 0) {
			return getNewPlayer(oppositeTeam);
		} else {
			if (oppositeTeam == 'X') {
				return new Player('O');
			} else {
				return new Player('X');
			}
		}
	}
}