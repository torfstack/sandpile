import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.lang.Math;

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
		boolean sym_hor = true;
		boolean sym_vert = true;
		System.out.println("Sandpile dimension is " + this.dim);

		// determine is sandpile is symmetric
		for (int i = 0; i < this.dim; ++i) {
			for (int j = 0; j < this.dim; ++j) {
				if (i < this.dim/2) sym_hor &= this.pile[i][j] == this.pile[this.dim-1-i][j];
				if (j < this.dim/2) sym_vert &= this.pile[i][j] == this.pile[i][this.dim-1-j];
			}
		}
		System.out.println("Sandpile is " + (sym_hor?"":"not ") + "horizontally symmetric");
		System.out.println("Sandpile is " + (sym_vert?"":"not ") + "vertically symmetric");


		// populate queue; Queue only contains entries > 3 after this
		int axis_hor = sym_hor?(int)Math.ceil((float)this.dim/2):this.dim-1;
		int axis_vert = sym_vert?(int)Math.ceil((float)this.dim/2):this.dim-1;
		for (int i = 0; i <= axis_hor; ++i) {
			for (int j = 0; j <= axis_vert; ++j) {
				if (this.pile[i][j] > 3) queue.add(new Tuple(i,j));
			}
		}
		System.out.println("Populated the queue with initial indexes, considering a " + axis_hor + " by " + axis_vert + " grid");

		// topple the entries; don't bother with cells across the axis if symmetric
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
				if (a < this.dim-1 && (!sym_hor || a < Math.ceil((float)this.dim/2))) {
					if (a+1==Math.ceil((float)this.dim/2)) this.set(a+1,b,this.get(a+1,b)+inc);
					this.set(a+1,b,this.get(a+1,b)+inc);
					if (this.get(a+1,b) > 3) queue.add(new Tuple(a+1,b));
				}
				if (b < this.dim-1 && (!sym_vert || b < Math.ceil((float)this.dim/2))) {
					if (b+1==Math.ceil((float)this.dim/2)) this.set(a,b+1,this.get(a,b+1)+inc);
					this.set(a,b+1,this.get(a,b+1)+inc);
					if (this.get(a,b+1) > 3) queue.add(new Tuple(a,b+1));
				}
			}
		}
		System.out.println("Toppled all entries, no more values > 3");

		// copy entries due to symmetry
		if (sym_hor) {
			for (int i = 0; i <= axis_hor; ++i) {
				for (int j = 0; j < this.dim; ++j) {
					this.set(this.dim-1-i,j,this.get(i,j));
				}
			}
			System.out.println("Copied values due to horizontal symmetry");
		}
		if (sym_vert) {
			for (int i = 0; i < this.dim; ++i) {
				for (int j = 0; j <= axis_vert; ++j) {
					this.set(i,this.dim-1-j,this.get(i,j));
				}
			}
			System.out.println("Copied values due to vertical symmetry");
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
		if (this.dim < 1) return;
		if (this.dim%2 == 1) this.pile[(this.dim-1)/2][(this.dim-1)/2] = e;
		else {
			this.pile[this.dim/2][this.dim/2] = e;
			this.pile[(this.dim-1)/2][this.dim/2] = e;
			this.pile[this.dim/2][(this.dim-1)/2] = e;
			this.pile[(this.dim-1)/2][(this.dim-1)/2] = e;
		}
	}

	public void setCorner(int e) {
		if (this.dim < 1) return;
		this.pile[this.dim-1][this.dim-1] = e;
	}

	public int get(Tuple t) {
		return this.pile[t.getX()][t.getY()];
	}

	public int get(int x, int y) {
		return this.pile[x][y];
	}
}