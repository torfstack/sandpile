import java.lang.Math;

public class Main {

	public static void main(String[] args) {
		Sandpile sp = new Sandpile((int)Math.pow(2,10)+1);
		sp.setMid((int)Math.pow(2,17));
		Fractal frac = new Fractal(sp);
		frac.saveImg("..\\fractal.png");
	}
}