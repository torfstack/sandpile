import java.lang.Math;

public class Main {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Sandpile sp = new Sandpile((int)Math.pow(2,10)+1);
		sp.setMid((int)Math.pow(2,20));
		Fractal frac = new Fractal(sp);
		frac.generateImg();
		frac.saveImg("..\\fractal.png");
		long stop = System.currentTimeMillis();
		long duration = (stop-start)/(1000);
		System.out.println("Task took " + duration + " seconds");
		duration /= 60;
		System.out.println("Task took " + duration + " minutes");
	}
}