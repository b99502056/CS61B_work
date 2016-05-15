public class Planet {

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private double G = 6.67 * Math.pow(10, -11);

	public Planet (double xP, double yP, double xV,
		double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet (Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance (Planet p) {
		double distX = xxPos - p.xxPos;
		double distY = yyPos - p.yyPos;

		return Math.sqrt(distX * distX + distY * distY);
	}

	public double calcForceExertedBy (Planet p) {
		return (G * mass * p.mass) / (calcDistance(p) * calcDistance(p));
	}
	
	public double calcForceExertedByX (Planet p) {
		return calcForceExertedBy(p) * (p.xxPos - xxPos) / calcDistance(p);
	}

	public double calcForceExertedByY (Planet p) {
		return calcForceExertedBy(p) * (p.yyPos - yyPos) / calcDistance(p);
	}

	public double calcNetForceExertedByX (Planet[] pArray) {
		int counter = 0;
		double netForceX = 0;
		while (counter < pArray.length) {
			if (!(pArray[counter].equals(this))) {
				netForceX += calcForceExertedByX(pArray[counter]);
			}
			counter += 1;
		}
		return netForceX;
	}

	public double calcNetForceExertedByY (Planet[] pArray) {
		int counter = 0;
		double netForceY = 0;
		while (counter < pArray.length) {
			if (!(pArray[counter].equals(this))) {
				netForceY += calcForceExertedByY(pArray[counter]);
			}
			counter += 1;
		}
		return netForceY;
	}

	public void update (double dt, double fX, double fY) {
		double aX = fX / mass;
		double aY = fY / mass;

		xxVel += aX * dt;
		yyVel += aY * dt;
		xxPos += xxVel * dt;
		yyPos += yyVel * dt;

		return;
	}

	public void draw () {
		StdDraw.picture(xxPos, yyPos, "/images/"+imgFileName);
		return;
	}

}