import java.util.List;
import java.util.ArrayList;
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
		mazeManager = new Maze();
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
		//Maze.printMaze(maze);
		List<int[]> crossRoads = new ArrayList<int[]>();
		
		/*
		System.out.println();
		System.out.print("Dean thinks that '" + Maze.getMazeName() + " can ");
		if (!findTheExit(Maze.getMazeCopy(), 9, 9)) {
			System.out.print(" NOT ");
		} else {
			System.out.println();
		}
		System.out.print(" be solved!");
		*/
		
		//Player One
		findPath(0,0,3,0, crossRoads);
		
		View.erasePath();
		
		//Player Two
		
		if (findTheExit(Maze.getMazeCopy(), 0, 0)) {
			JOptionPane.showMessageDialog(null, "Maze is solvable." 
						+ "\n " + playerTwoRoundScore, "FindTheExit", 1);
		} else {
			JOptionPane.showMessageDialog(null, "Maze is not solvable." 
						+ "\n " + playerTwoRoundScore, "FindTheExit", 2);
		}
				
		playerOneScore += playerOneRoundScore;
		playerTwoScore += playerTwoRoundScore;
		System.out.println("Playing at Maze = " + Maze.getMazeName() + "\nThe result was      PathFinder - " + playerOneRoundScore + "\n     		    FindTheExit - " + playerTwoRoundScore);
		System.out.println("\nThe overall score is PathFinder - " + playerOneScore + "\n     		    FindTheExit - " + playerTwoScore);
		playerOneRoundScore = 0;
		playerTwoRoundScore = 0;
		System.out.println();
	}
	
	//My Code
	//PathFinder ver0.85 alpha
	public static void findPath(int x, int y, int origin, int counter, List<int[]> crossRoads) {
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
		
		//then he checks his cross roads map and he see if he should cancel any of the directions
		if (intersectionCounter > 1 && crossRoads.size() > 0) {
			int[] pathsAlreadyTaken = new int [4];
			int pathLeastTaken = 4;
			int futureDir = -1;
			boolean shouldIBother = false;
			
			//here he adds the number of times he was at this same intersection
			//saving this number in an array according to the direction he took on previous times
			for (int [] oldPathChoice : crossRoads) {
				if (oldPathChoice[0] == x && oldPathChoice[1] == y) {
					pathsAlreadyTaken[oldPathChoice[2]]++;
					shouldIBother = true;
				}
			}
			
			if (shouldIBother) {
				//and here he checks to see what was the path that he took the least number of times
				for (int dir = possibleDirections.length - 1; dir >= 0; dir--) {
					if (possibleDirections[dir]) {
						if (pathsAlreadyTaken[dir] < pathLeastTaken) {
							pathLeastTaken = pathsAlreadyTaken[dir];
							futureDir = dir;
							
						}
					}	
				}
				
				//and now we eliminate every option that isn't FutureDirection
				for (int dir = 0; dir < possibleDirections.length; dir++) {
					if (dir != futureDir) {
						possibleDirections[dir] = false;
					}
				}
				
			}	
				
		}
		
		//and here he is just sent to the one possibleDirection left;
		for (int dir = 0; dir < possibleDirections.length; dir++) {
 			if(possibleDirections[dir]) {
 				//here we add the information about this crossRoad to the crossRoads book
 				if (intersectionCounter > 1) {
 					//JOptionPane.showMessageDialog(null, "[" + x + "," + y + "] \n TSOMET FARADIZ", View.TITLE, 1);
 					
 					//we remember where we came from and where we went
 					crossRoads.add(new int[]{x,y,origin});
 					crossRoads.add(new int[]{x,y,dir});
 				}
 				findPath(x+DIRECTIONS[dir][0],y+DIRECTIONS[dir][1],dir+2,++counter, crossRoads);
 				return;
 			}
		}
		findPath(x,y,origin+2,++counter, crossRoads);
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


    

