
public class Board {
	
	private char[][] board;
	private final int size;
	private int round;
	
	public Board(int size) {
		this.size = size;
		this.board = new char[size][size];
		this.setRound(1);
	}
	
	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public void setTile(Move move) {
		this.board[move.getX()][move.getY()] = move.getTeam();
	}
	
	public char getTile(int x, int y) {
		return this.board[x][y];
	}
	
	public int getSize() {
		return size;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

}
