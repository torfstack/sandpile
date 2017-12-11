class Tuple {

	private int x;
	private int y;

	public Tuple() {
		this.x = 0;
		this.y = 0;
	}

	public Tuple(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public String toString() {
		return "("+x+","+y+")";
	}
}