
public class Move {

	private final int x, y;
	private final char team;
	
	public Move (int x, int y, char team) {
		this.x = x;
		this.y = y;
		this.team = team;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getTeam() {
		return team;
	}


}
