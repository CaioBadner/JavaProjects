import java.util.Random;

import javax.swing.JOptionPane;

public class Player {

	private char playerTeam;
	private String playerName;
	private int playerScore;
	private boolean isRobot;
	
	private final String [] listOfNames = {"Alex","Bob", "Moshe", "Schmulik", "John", "Valentina", "Shoshana", "Mary", "Nancy"};
	
	//human players get identified by their construction with a name
	public Player (char team, String name) {
		this.setPlayerTeam(team);
		this.playerName = name;
		//this.playerMove = new int[2];
		this.isRobot = false;
	}
	
	//computer players get identified because they were constructed without a name
	public Player (char team) {
		String name = generateName();
		this.setPlayerTeam(team);
		this.playerName = name;
		this.isRobot = true;
	}

	private String generateName() {
		Random rand = new Random();
		int namePos = rand.nextInt(listOfNames.length);
		return listOfNames[namePos];
	}
	

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public int[] getPlayerMove(Board board) {
		
		if (!this.isRobot) {
		String input;
		int row = 0, column = 0;
		
		boolean isMoveValid = false;
		
		while (!isMoveValid) {
			do {
				input = JOptionPane.showInputDialog(null, "Enter row:", "" + this.playerTeam + " to move", 0);
				row = Integer.parseInt(input);
			} while (row < 0 || row > 2);
			
			do {
				input = JOptionPane.showInputDialog(null, "Enter column:", "" + this.playerTeam + " to move", 0);
				column = Integer.parseInt(input);
			} while (column < 0 || column > 2);
			
			int [] playerMove = {row,column};
			
			if (board[row][column] == null) {
				isMoveValid = true;
			}
		} 
			
			
			
			return playerMove;
		
		} else {
			
			return getComputerMove();
		}
	}

	private int[] getComputerMove() {
		return null;
	}

	public boolean isRobot() {
		return isRobot;
	}

	public char getPlayerTeam() {
		return playerTeam;
	}

	public void setPlayerTeam(char playerTeam) {
		this.playerTeam = playerTeam;
	}

	public String getPlayerName() {
		return playerName;
	}


	/*
	public class HumanPlayer extends Player {

		public HumanPlayer(char team, String name) {
			super(team, name);
		}

		@Override
		public int[] getPlayerMove() {
			return null;
		}

		@Override
		public String whatsMyName() {
			// TODO Auto-generated method stub
			return null;
		}

				
	}
	/*
	public class ComputerPlayer extends Player {

		
		public ComputerPlayer(char team, String name) {
			super(team, name);
		}

		@Override
		public int[] getPlayerMove() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected String whatsMyName() {
			String myName = null;
			return myName;
		}
		
	}
*/
	
	
}
