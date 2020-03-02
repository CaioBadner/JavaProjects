
public class PathFinder {
																		/*N*/
	public static boolean [] [] maze = {{true,true,false,true,true,true,true,true,true,true,true,false},
									    {false,true,true,true,true,false,false,true,true,false,true,false},
									    {true,true,false,false,true,false,false,true,true,false,true,false},  
									    {true,false,true,true,true,true,false,true,true,false,true,false},       /*E*/
					    /*W*/		    {true,true,true,false,false,true,false,true,true,true,true,false},     
									    {false,false,true,true,true,true,false,false,true,true,true,false},
									    {true,true,true,false,true,false,true,true,true,false,true,true}};
													                 	/*S*/
	
	
	/*   public static boolean [] [] maze = {{true,true,false,true,false},
											 {false,true,false,true,false},
											 {true,true,false,true,false},
											 {true,false,true,true,true},
											 {true,true,true,false,true}};
											  
	
	 */ 
	 
	 


	
	public static void main(String[] args) {
		
		String path = "";
		path = findPath(0,0,'W',0);
		
		System.out.println(path);
		
		
	}
	//the char origin will tell this method from which direction it just came from, so that it won't turn back around
	public static String findPath(int x, int y, char origin, int counter) {
		String path = "[" + x + "," + y + "]-";
		
		if (x == maze.length - 1 && y == maze[0].length - 1) {
			return path + " WINNER!";
		}
		
		if (counter > 100) {
			return "\nOk, I am lost, I give up... How can I get out of here?!";
		}
		
		
		if (y < maze[0].length - 1) {
			
			if (maze[x][y+1] && origin != 'E') {
				return path + findPath(x,y+1, 'W',++counter);
			}
		}
		if (x < maze.length - 1) {
			
			if (maze[x+1][y] && origin != 'S') {
				return path + findPath(x+1,y, 'N', ++counter);
			}
			
		}	
		if (x > 0) {
			
			if (maze[x-1][y] && origin != 'N') {
				return path + findPath(x-1,y, 'S',++counter);
			}
		}
		if (y > 0) {
			
			if (maze[x][y-1] && origin != 'W') {
				return path + findPath(x,y-1, 'E',++counter);
			}
			
		} 
		
		return path + "Dead End" + "\n" + findPath(x,y,'z', counter + 10);

	}
}
