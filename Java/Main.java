public class Main {

	public static void main(String[] args) {
		int dim = 129;
		int sand = 1028;
		String path = "..\\fractal.png";
		for (int i = 0; i < args.length; ++i) {
			if (args[i].equals("--dimension") && i+1 <= args.length) dim = parseInt(args[i+1], "--dimension");
			if (args[i].equals("--sand") && i+1 <= args.length) sand = parseInt(args[i+1], "--sand");
			if (args[i].equals("--path") && i+1 <= args.length) path = args[i+1];
		}
		
		long start = System.currentTimeMillis();
		Fractal frac = new Fractal(dim);
		frac.setMid(sand);
		frac.generateImg();
		frac.saveImg(path);
		long stop = System.currentTimeMillis();
		long duration = (stop-start)/(1000);
		System.out.println("Task took " + duration + " seconds");
		duration /= 60;
		System.out.println("Task took " + duration + " minutes");
	}

	public static int parseInt(String arg, String purpose) {
		try {
			return Integer.parseInt(arg);
		} catch(NumberFormatException e) {
			System.err.println("Argument of " + purpose + " has to be an Integer. (provided \"" + arg + "\")");
			System.exit(1);
			return 0;
		}
	}
}