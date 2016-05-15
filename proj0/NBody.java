public class NBody {

	/* Read the universe radius. */
	public static double readRadius(String path) {

		if (path == null) {
			System.out.println("Please supply a file path as a command argument.");
			System.exit(0);
		} 	
	
	
		In in = new In(path);
		int planetsNum = in.readInt();
		double universeRadius = in.readDouble();

		return universeRadius;
	}
	/* Read the planets, and return an array of planets. */
	public static Planet[] readPlanets(String path) {
		if (path == null) {
			System.out.println("Please supply a file path as a command line argument.");
			System.exit(0);
		}

		In in = new In(path);
		int planetsNum = in.readInt();
		double universeRadius = in.readDouble();
		Planet[] planets = new Planet[planetsNum];

		for (int i = 0; i < planetsNum; i++) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
		}

		return planets;
	}

	public static void main(String[] args) {
		/* Variable declaration section. */
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet[] planets = readPlanets(filename);
		double universityRadius = readRadius(filename);	
		double currentTime = 0.0;
		/* Reading planets.txt again to obtain the total 
		 * number of planets. */
		In in = new In(filename);
		int planetsNum = in.readInt();

		/* Drawing section. */
		StdDraw.setScale(-universityRadius, universityRadius);

		/* Initial state of the planets. */
		/* Draw the universe. */
		StdDraw.picture(0, 0, "./images/starfield.jpg");
		/* Draw the planets. */
		for (int k = 0; k < planetsNum; k++) {
			planets[k].draw();			
		}
		StdDraw.show(10);
		
		/* Create animation. */
		for (double i = 0.0; i <= T; i += dt) {
			/* Update the state of the planets due to force 
			 * interactions. */
			for (int j = 0; j < planetsNum; j++){
				double xNetForce = planets[j].calcNetForceExertedByX(planets);
				double yNetForce = planets[j].calcNetForceExertedByY(planets);
				planets[j].update(dt, xNetForce, yNetForce);
			}
			/* Draw the universe. */
			StdDraw.picture(0, 0, "./images/starfield.jpg");
			/* Draw the planets. */
			for (int k = 0; k < planetsNum; k++) {
				planets[k].draw();
			}
			/* Delay 10 ms. */
			StdDraw.show(10);
		}

		return;

	}
}