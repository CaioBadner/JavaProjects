
public class Board {
	
	private char[][] board;
	
	private static final String ROW = "-------------------";
	private static final String COLUMN = " | ";
	
	public Board(int size) {
		this.board = new char[size][size];
	}
	
	public void setTile(int[] move, char playerSide) {
		this.board[move[0]][move[1]] = playerSide;
	}
	
	public void printBoard() {
		
		System.out.print("    ");
		for (int x = 0; x < this.board[0].length; x++) {
		System.out.print(COLUMN + x);
		}
		System.out.print(COLUMN + "\n" + ROW + "\n");
				
		for (int y = 0; y < this.board.length; y++) {
			System.out.print(COLUMN + y);
			
			for (int x = 0; x < board[0].length; x++) {
				System.out.print(COLUMN + board[x][y]);
			}
			System.out.print(COLUMN + "\n" + ROW + "\n");
		}
		
		System.out.println();
	}
	
}
