package camuoi.tictactoe;

public class Point {
	public int x;
	public int y;

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "[" + x + "]" + "[" + y + "]";
	}
}
