import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

class Sandpile {

	private int dim;
	private int[][] pile;

	public Sandpile(int dim, int pile[][]) {
		this.dim = dim;
		this.pile = pile;
	}

	public Sandpile(int dim) {
		this.dim = dim;
		this.pile = new int[dim][dim];
	}

	public Sandpile() {
		this.dim = 3;
		this.pile = new int[3][3];
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < this.dim; ++i) {
			s += Arrays.toString(this.pile[i])+'\n';
		}
		return s.substring(0,s.length()-1);
	}

	public void max() {
		for (int i = 0; i < this.dim; ++i) {
			for (int j = 0; j < this.dim; ++j) {
				this.pile[i][j] = 3;
			}
		}
	}

	public void normalize() {
		LinkedList<Tuple> queue = new LinkedList<Tuple>();

		for (int i = 0; i < this.dim; ++i) {
			for (int j = 0; j < this.dim; ++j) {
				if (this.pile[i][j] > 3) queue.add(new Tuple(i,j));
			}
		}

		while (queue.size() != 0) {
			Tuple t = queue.poll();
			if (this.get(t) > 3) {
				int inc = this.get(t)/4;
				this.set(t, this.get(t)%4);

				int a = t.getX();
				int b = t.getY();

				if (a > 0) {
					this.set(a-1,b,this.get(a-1,b)+inc);
					if (this.get(a-1,b) > 3) queue.add(new Tuple(a-1,b));
				}
				if (b > 0) {
					this.set(a,b-1,this.get(a,b-1)+inc);
					if (this.get(a,b-1) > 3) queue.add(new Tuple(a,b-1));
				}
				if (a < this.dim-1) {
					this.set(a+1,b,this.get(a+1,b)+inc);
					if (this.get(a+1,b) > 3) queue.add(new Tuple(a+1,b));
				}
				if (b < this.dim-1) {
					this.set(a,b+1,this.get(a,b+1)+inc);
					if (this.get(a,b+1) > 3) queue.add(new Tuple(a,b+1));
				}
			}
		}
	}

	public int getDim() {
		return this.dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}

	public int[][] getPile() {
		return this.pile;
	}

	public void setPile(int[][] pile) {
		this.pile = pile;
	}

	public void set(int x, int y, int e) {
		this.pile[x][y] = e;
	}

	public void set(Tuple t, int e) {
		this.set(t.getX(), t.getY(), e);
	}

	public void setMid(int e) {
		if (this.dim%2 == 0) return;
		this.pile[(this.dim-1)/2][(this.dim-1)/2] = e;
	}

	public int get(Tuple t) {
		return this.pile[t.getX()][t.getY()];
	}

	public int get(int x, int y) {
		return this.pile[x][y];
	}
}