
public class Maze {

	private static boolean [] [] maze;
	private static int length;
	
	public Maze (int length) {
		Maze.length = length;
		Maze.maze = new boolean [length][length];
		Maze.maze[0][0] = true;
		Maze.maze[length-1][length-1] = true;
	}
	
	public Maze () {
		Maze.maze = maze01;
	}
	
	public int getLength() {
		return length;
	}

	public static boolean [] [] getMaze() {
		return maze;
	}

	public static final boolean [] [] maze01 =     {{true,true,true,false,true,false,true,true,true,true},
													{true,false,true,false,true,false,true,false,false,true},
													{true,false,true,true,true,true,true,false,true,true},
													{true,true,false,true,false,false,true,true,false,false},
									  				{false,true,false,false,true,false,false,true,true,true},     
													{false,true,false,true,true,true,false,true,false,true},
													{true,true,false,true,false,true,true,true,false,true},
													{true,false,true,true,true,true,false,true,true,true},
													{true,false,false,false,false,true,false,true,false,false},
													{true,true,true,true,true,true,false,true,true,true}};
																
									
/*
public static boolean [] [] maze = 	{{true,false,true,true,true},
										{true,false,true,false,true},
										{true,true,true,true,true},
										{true,false,true,false,true},
										{true,true,true,true,true}};

*/
}
