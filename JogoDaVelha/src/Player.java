import java.util.Random;

import javax.swing.JOptionPane;

public class Player {

	private final char playerTeam;
	private final char oppositeTeam;
	private final String playerName;
	private int playerScore;
	private final boolean isRobot;
	
	//these are the constants that the AI will use 
	private static final String [] LIST_OF_NAMES = {"Alex","Bob", "Moshe", "Schmulik", "John", "Valentina", "Shoshana", "Mary", "Nancy"};
	private static final int[][] CORNERS = {{0,0},{0,2},{2,0},{2,2}};
	private static final int[][] MIDDLES = {{1,0},{0,1},{1,2},{2,1}};
	private static final int[] CENTER = {1,1};
	private static final int[][] DIAGONALONE = {{0,0},{1,1},{2,2}};
	private static final int[][] DIAGONALTWO = {{2,0},{1,1},{0,2}};
	
	//human players get identified by their construction with a name
	public Player (char team, String name) {
	
		this.playerTeam = team;
		this.playerName = name;
		this.isRobot = false;
		this.oppositeTeam = findOppositeTeam(team);
	}
	
	//computer players get identified because they were constructed without a name
	public Player (char team) {
		this.oppositeTeam = findOppositeTeam(team);
		String name = generateName();
		this.playerTeam = team;
		this.playerName = name;
		this.isRobot = true;
	}
	
	private char findOppositeTeam(char team) {
		if (team == 'X') {
			return 'O';
		} else {
			return 'X';
		}	
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public boolean isRobot() {
		return isRobot;
	}

	public char getPlayerTeam() {
		return playerTeam;
	}

	public Move getPlayerMove(Board board) {
		
		Move playerMove; 
		
		//this is the human part
		if (!this.isRobot) {
		
			do {
				int row = getHumanCoordinate(true);
				int column = getHumanCoordinate(false);
				playerMove = new Move(row,column,this.playerTeam);
			} while (!isMoveValid(playerMove, board));
			
			return playerMove;
		
		//and this calls the AI guesses
		} else {
			
			do {
				 playerMove = getComputerMove(board);
			} while (!isMoveValid(playerMove, board));
				
			JOptionPane.showMessageDialog(null, "I will place a " + this.playerTeam + " at (" + (playerMove.getX()+1) 
										+ "," + (playerMove.getY()+1) + ")", this.playerName, 1 );	
			return playerMove;
		}
	}
	
	private int getHumanCoordinate(boolean isRow) {
		
		String strCoord, input;
		
		int coord = 0;
		
		if (isRow) {
			strCoord = "row";
		} else {
			strCoord = "column";
		}
		
		do { 
			input = JOptionPane.showInputDialog(null, this.playerName + ", enter a " + strCoord 
					+ " number from 1 to 3", this.playerTeam + " to move", 3);
			try {
				coord = Integer.parseInt(input);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Please enter a valid " + strCoord 
						+ " number! 1-2-3", "Error", 0);
				continue;
			}
		} while (coord < 1 || coord > 3);
		return coord - 1;
	}

	private boolean isMoveValid(Move move, Board board) {
		
		if (board.getTile(move.getX(), move.getY()) != 'X' && 
				board.getTile(move.getX(), move.getY()) != 'O') {
			return true;
		}
		
		if (!this.isRobot) {
			JOptionPane.showMessageDialog(null, "The square (" + (move.getX()+1) + "," + (move.getY()+1) 
										+ ") is occupied!" + "\nPlease enter a valid a move!", "Error", 0);
		}	
		return false;
	}

	public String getPlayerName() {
		return playerName;
	}

	private String generateName() {
		Random rand = new Random();
		int namePos = rand.nextInt(LIST_OF_NAMES.length);
		return LIST_OF_NAMES[namePos];
	}
	
	private Move getComputerMove(Board board) {
		if (board.getRound() == 1) {
			return makeFirstMoveAsPlayer1();
		}
		
		if (board.getRound() == 2) {
			return makeFirstMoveAsPlayer2(board);
		}
		
		Move move;
		
		move = findNearWin(board, this.playerTeam);
		if (move != null) {
			return move;
		} 
		
		move = findNearWin(board, this.oppositeTeam);
		if (move != null) {
			return move;
		} 
		
		if (board.getRound() < 4 ) {
			return makeFirstMoveAsPlayer1();
		}
		
		return  findAnEmptySquare(board);
			
	}
	

	private Move findAnEmptySquare(Board board) {
		
		for (int x = 0; x < board.getSize(); x++) {
			for (int y = 0; y < board.getSize(); y++) {
				if (board.getTile(x, y) == 0) {
					return new Move(x,y,this.playerTeam);
				}
			}
		}
		return null;
	}

	//the first move overall is mostly random, with 40% of going for the middle, 
	//and 35% chance of going for the corners
	private Move makeFirstMoveAsPlayer1() {

		Random rand = new Random();
		int option = rand.nextInt(100);
		
		if (option < 40) {
			return new Move(CENTER[0],CENTER[1],this.playerTeam);
		} 
		
		int choice = rand.nextInt(CORNERS.length);
		
		if (option < 75) {
			return new Move(CORNERS[choice][0],CORNERS[choice][1],this.playerTeam);
		} 

		return new Move(MIDDLES[choice][0],MIDDLES[choice][1],this.playerTeam);
		
	}
	
	private Move makeFirstMoveAsPlayer2(Board board) {
		
		Random rand = new Random();
		int option = rand.nextInt(CORNERS.length+1);
		
		if (board.getTile(1, 1) == this.oppositeTeam) {
			//if the opponent placed his mark in the center, then there is a 80% chance the computer
			//will go for one of the corners, and 20% he will choose the middle
			if (option != 4) {
				return new Move(CORNERS[option][0],CORNERS[option][1],this.playerTeam);
			} else {
				int choice = rand.nextInt(CORNERS.length);
				return new Move(MIDDLES[choice][0],MIDDLES[choice][1],this.playerTeam);
			}
		} else {
			//here there is a 40% chance it will go for a corner if the opponent didnt chose the middle
			if (option > 2) {
				for (int [] i  : CORNERS) {
					if (board.getTile(i[0], i[1]) == 0) {
						return new Move(i[0],i[1],this.playerTeam);
					}
				}
			}
		}
		return new Move(CENTER[0],CENTER[1],this.playerTeam);
	}
		
	private Move findNearWin(Board board, char team) {

		//check all rows first
		for (int x = 0; x < board.getSize(); x++) {
			int moveX = 0;
			int moveY = 0;
			int counter = 0;
			for (int y = 0; y < board.getSize(); y++) {
				if (board.getTile(x, y) == team) {
					counter++;
				} else if (board.getTile(x, y) == 0) {
					moveX = x;
					moveY = y;
				} else { 
					counter--;
				}
			}
			if (counter == 2) {
				return new Move(moveX, moveY, this.playerTeam);
			}
		}
		
		//then check the columns
		for (int y = 0; y < board.getSize(); y++) {
			int moveX = 0;
			int moveY = 0;
			int counter = 0;
			for (int x = 0; x < board.getSize(); x++) {
				if (board.getTile(x, y) == team) {
					counter++;
				} else if (board.getTile(x, y) == 0) {
					moveX = x;
					moveY = y;
				} else { 
					counter--;
				}
			}
			if (counter == 2) {
				return new Move(moveX, moveY, this.playerTeam);
			}
		}	
		
		//then the diagonals
		int moveX = 0;
		int moveY = 0;
		int counter = 0;
		for (int [] i : DIAGONALONE) {
				if (board.getTile(i[0],i[1]) == team) {
					counter++;
				} else if (board.getTile(i[0],i[1]) == 0) {
					moveX = i[0];
					moveY = i[1];
				} else {
					counter--;
				}
			}
			if (counter == 2) {
				return new Move(moveX, moveY, this.playerTeam);
			}
		counter = 0;
		for (int [] i : DIAGONALTWO) {
			
			if (board.getTile(i[0],i[1]) == team) {
				counter++;
			} else if (board.getTile(i[0],i[1]) == 0) {
				moveX = i[0];
				moveY = i[1];
			} else {
				counter--;
			}
		}
		if (counter == 2) {
			return new Move(moveX, moveY, this.playerTeam);
		}
	
		
		//if nothing useful has been found he will return an empty move and use 
		//that information to make the next decision
		return null;	
	}
	
	
	
	public boolean didPlayerWin(Board board) {
		
		
		
		//first we check the rows
		for (int x = 0; x < board.getSize(); x++) {
			int counter = 0;
			for (int y = 0; y < board.getSize(); y++) {
				if (board.getTile(x, y) == this.playerTeam) {
					counter++;
				}
			}
			if (counter == board.getSize()) {
				return true;
			}
		}
		
		//then we check the columns
		for (int y = 0; y < board.getSize(); y++) {
			int counter = 0;
			for (int x = 0; x < board.getSize(); x++) {
				if (board.getTile(x, y) == this.playerTeam) {
					counter++;
				}
			}
			if (counter == board.getSize()) {
				return true;
			}
		}
		
		//and this checks both of the diagonals
		if (board.getTile(0, 0) == this.playerTeam 
			&& board.getTile(1, 1) == this.playerTeam 
				&& board.getTile(2, 2) == this.playerTeam
					|| board.getTile(2, 0) == this.playerTeam 
						&& board.getTile(1, 1) == this.playerTeam 
							&& board.getTile(0, 2) == this.playerTeam) {
			return true;
		}
		
		return false;
	}

	
}
