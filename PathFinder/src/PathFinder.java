import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class PathFinder {
	
public static boolean [][] maze;
public static int playerOneScore = 0, playerTwoScore = 0, 
				  playerOneRoundScore = 0, playerTwoRoundScore = 0;

										  /*S*/  /*E*/  /*N*/  /*W*/
									   /*   0      1      2      3   */
public final static int[][] DIRECTIONS = {{1,0},{0,1},{-1,0},{0,-1}};

public static Maze mazeManager;

	public static void main(String[] args) {
		mazeManager = new Maze(true);
		maze = Maze.getMaze();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new View();	
			}
		});
	}
	
	public static void updateMaze() {
		maze = Maze.getMaze();
	}
	
	public static void runMaze() {
		int[][][] crossRoads = new int[10][10][4];
		
		//Player One
		findPath(0,0,3,0, crossRoads);
		
		View.erasePath();
		
		//Player Two
		String strNot = "";
		if (!findTheExit(Maze.getMazeCopy(), 0, 0)) {
			strNot = "NOT ";
		}
			JOptionPane.showMessageDialog(null, "Maze is " + strNot + "solvable." 
						+ "\n " + playerTwoRoundScore, "FindTheExit", 2);
		
				
		playerOneScore += playerOneRoundScore;
		playerTwoScore += playerTwoRoundScore;
		System.out.println("Playing at Maze = " + Maze.getMazeName() + "\nThe result was      PathFinder - " + playerOneRoundScore + "\n     		    FindTheExit - " + playerTwoRoundScore);
		System.out.println("\nThe overall score is PathFinder - " + playerOneScore + "\n     		    FindTheExit - " + playerTwoScore);
		playerOneRoundScore = 0;
		playerTwoRoundScore = 0;
		System.out.println();
	}
	
	//PathFinder ver0.95 alpha
	public static void findPath(int x, int y, int origin, int counter, int[][][] crossRoads) {
		//this header is for game purposes only
		playerOneRoundScore++;
		View.leaveMark(x,y);
		//JOptionPane.showMessageDialog(null, "[" + x + "," + y + "]", View.TITLE, 1);
		
		if (x == maze.length - 1 && y == maze.length - 1) {
			//System.out.println("EXIT PATH FOUND in " + playerOneRoundScore);
			if (counter > maze.length * 3) { 
				JOptionPane.showMessageDialog(null, "I FOUND IT!" 
										+ "\n " + playerOneRoundScore, View.TITLE, 1);
			} else {
				JOptionPane.showMessageDialog(null, "Ok, I'm out, that was easy!" 
										+ "\n " + playerOneRoundScore, View.TITLE, 1);
			}
			return;
		}
		
		//This is what kills the recursion when we reach a certain mark
		//System.out.println(counter);
		if (counter > 300) {
			JOptionPane.showMessageDialog(null, "Ok, I am lost, I give up... How can I get out of here?!" 
										+ "\n " + playerOneRoundScore, View.TITLE, 1);
			return;
		}
		
		//here we update the origin so that it is always between 0-3
		if (origin > 3) {
			origin -= 4;
		}
		
		boolean[] possibleDirections = new boolean[4];
		int intersectionCounter = 0;
		//first he checks all directions, except the origin, and sees how many of them are available
		for (int dir = 0; dir < DIRECTIONS.length; dir++) {
			if (dir != origin) {
				try {
					if (maze[x+DIRECTIONS[dir][0]][y+DIRECTIONS[dir][1]])	{
						possibleDirections[dir] = true;
						intersectionCounter++;
					}
					//here we check for the edges of the board and catch all the exceptions
				} catch (ArrayIndexOutOfBoundsException ex){
					continue;
				}
			}
		}
		//if he came to a dead end then he goes back from where he came from
		if (intersectionCounter == 0) {
			findPath(x+DIRECTIONS[origin][0],y+DIRECTIONS[origin][1],origin+2,++counter, crossRoads);
			return;
		}
		
		//if the result is one then he gets sent to that one possible place
		if (intersectionCounter == 1) {
			for (int dir = 0; dir < possibleDirections.length; dir++) {
	 			if(possibleDirections[dir]) {
	 				findPath(x+DIRECTIONS[dir][0],y+DIRECTIONS[dir][1],dir+2,++counter, crossRoads);
	 				return;
	 			}
			}
		}
		//if we made it here then we are standing at a cross roads, 
		//so first thing is to make a quick note that we arrived 
		crossRoads[x][y][origin]++;
		
		//Now he will make another check comparing with the previews times he was here
		int pathLeastTaken = 10;
		int futureDir = origin;
		for (int dir = 0; dir < possibleDirections.length; dir++) {
			if (possibleDirections[dir]) {
				if (crossRoads[x][y][dir] < pathLeastTaken) {
					pathLeastTaken = crossRoads[x][y][dir];
					futureDir = dir;
				}
			}
		}
		//here we make another annotation to the crossRoads map before we go to our new direction
		crossRoads[x][y][futureDir]++;
		findPath(x+DIRECTIONS[futureDir][0],y+DIRECTIONS[futureDir][1],futureDir+2,++counter, crossRoads);
		return;
	}


	//Dean's code (inverted values so it starts from 0,0 and chases the last cell)
	public static boolean findTheExit(boolean[][] matrix, int row, int col) {
		
		//this header is for game purposes only
		playerTwoRoundScore++;
		//JOptionPane.showMessageDialog(null, playerTwoRoundScore);
		View.leaveMark(row, col);
		
		if (row == matrix.length - 1 && col == matrix[0].length - 1) {// base condition
			//System.out.print(row + "," + col + " -> ");
			return true;
		}
		if (!matrix[row][col]) {// base condition
			return false;
		}
		matrix[row][col]=false;
		if (col < matrix[0].length - 1) { // check out of bounds
				if (findTheExit(matrix, row, col + 1)) {// check left step
					//System.out.print(row + "," + col + " -> ");
					return true;
				}
		}
		if (row < matrix.length - 1) { // check out of bounds
				if (findTheExit(matrix, row + 1, col)) {// check up step
					//System.out.print(row + "," + col + " -> ");
					return true;
				}
		}
		if (col > 0) { // check out of bounds
			
				if (findTheExit(matrix, row, col - 1)) {// check right step
					//System.out.print(row + "," + col + " -> ");
					return true;
				}
			
		}
		if (row > 0) { // check out of bounds
			
				if (findTheExit(matrix, row - 1, col)) {// check down step
					//System.out.print(row + "," + col + " -> ");
					return true;
				}
		}

		return false;// if all not work return false.
	}


}	


    

