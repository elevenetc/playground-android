package su.levenetc.androidplayground.utils;

/**
 * Created by eugene.levenetc on 10/07/2017.
 * Used https://github.com/locationtech/jts
 */
public class Vector {

	private final Point start;
	private final Point end;
	private float length = -1;

	public Vector(Point start, Point end) {

		this.start = start;
		this.end = end;
	}

	public float length() {
		if (length == -1)
			length = (float) Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.x - start.x, 2));

		return length;
	}

	/**
	 * @param t (0 end 1) - on vector, (<0) - out from start (>1) - out from end
	 * @return
	 */
	public Point getPointAt(float t) {
		final float x = (1 - t) * start.x + t * end.x;
		final float y = (1 - t) * start.y + t * end.y;
		return new Point(x, y);
	}

	public static boolean isSameSignAndNonZero(double a, double b) {
		if (a == 0 || b == 0) {
			return false;
		}
		return (a < 0 && b < 0) || (a > 0 && b > 0);
	}

	public Point computeIntersect(
			Point p1,
			Point p2,
			Point p3,
			Point p4) {
		double a1;
		double b1;
		double c1;

		Point pa = new Point(0, 0);
	/*
	 *
     */
		//Coefficients of line eqns.
		double a2;
	/*
	 *
     */
		//Coefficients of line eqns.
		double b2;
	/*
	 *
     */
		//Coefficients of line eqns.
		double c2;
		double r1;
		double r2;
		double r3;
		double r4;
		//'Sign' values
		//double denom, offset, num; Intermediate values

		boolean isProper = false;

    /*
     *  Compute a1, b1, c1, where line joining points 1 and 2
     *  is "a1 x  +  b1 y  +  c1  =  0".
     */
		a1 = p2.y - p1.y;
		b1 = p1.x - p2.x;
		c1 = p2.x * p1.y - p1.x * p2.y;

    /*
     *  Compute r3 and r4.
     */
		r3 = a1 * p3.x + b1 * p3.y + c1;
		r4 = a1 * p4.x + b1 * p4.y + c1;

    /*
     *  Check signs of r3 and r4.  If both point 3 and point 4 lie on
     *  same side of line 1, the line segments do not intersect.
     */
		if (r3 != 0 &&
				r4 != 0 &&
				isSameSignAndNonZero(r3, r4)) {
			return null;
		}

    /*
     *  Compute a2, b2, c2
     */
		a2 = p4.y - p3.y;
		b2 = p3.x - p4.x;
		c2 = p4.x * p3.y - p3.x * p4.y;

    /*
     *  Compute r1 and r2
     */
		r1 = a2 * p1.x + b2 * p1.y + c2;
		r2 = a2 * p2.x + b2 * p2.y + c2;

    /*
     *  Check signs of r1 and r2.  If both point 1 and point 2 lie
     *  on same side of second line segment, the line segments do
     *  not intersect.
     */
		if (r1 != 0 &&
				r2 != 0 &&
				isSameSignAndNonZero(r1, r2)) {
			return null;
		}

		/**
		 *  Line segments intersect: compute intersection point.
		 */
		double denom = a1 * b2 - a2 * b1;
		if (denom == 0) {
			return computeCollinearIntersection(p1, p2, p3, p4);
		}
		double numX = b1 * c2 - b2 * c1;
		pa.x = (float) (numX / denom);
	/*
	 *  TESTING ONLY
     *  double valX = (( num < 0 ? num - offset : num + offset ) / denom);
     *  double valXInt = (int) (( num < 0 ? num - offset : num + offset ) / denom);
     *  if (valXInt != pa.x)     // TESTING ONLY
     *  System.out.println(val + " - int: " + valInt + ", floor: " + pa.x);
     */
		double numY = a2 * c1 - a1 * c2;
		pa.y = (float) (numY / denom);

		// check if this is a proper intersection BEFORE truncating values,
		// to avoid spurious equality comparisons with endpoints
        isProper = !pa.equals(p1) && !pa.equals(p2) && !pa.equals(p3) && !pa.equals(p4);

		pa.fixZeroes();

		return pa;
	}

	private Point computeCollinearIntersection(
			Point p1,
			Point p2,
			Point p3,
			Point p4) {

		double r1;
		double r2;
		double r3;
		double r4;
		Point q3;
		Point q4;
		double t3;
		double t4;
		r1 = 0;
		r2 = 1;
		r3 = rParameter(p1, p2, p3);
		r4 = rParameter(p1, p2, p4);
		// make sure p3-p4 is in same direction as p1-p2
		if (r3 < r4) {
			q3 = p3;
			t3 = r3;
			q4 = p4;
			t4 = r4;
		} else {
			q3 = p4;
			t3 = r4;
			q4 = p3;
			t4 = r3;
		}
		// check for no intersection
		if (t3 > r2 || t4 < r1) {
			return null;
		}

		Point pa = new Point(0, 0);

		// check for single point intersection
		if (q4 == p1) {
			pa.set(p1);
			return pa;
		}
		if (q3 == p2) {
			pa.set(p2);
			return pa;
		}

		return null;
	}

	/**
	 * RParameter computes the parameter for the point p
	 * in the parameterized equation
	 * of the line from p1 to p2.
	 * This is equal to the 'distance' of p along p1-p2
	 */
	private double rParameter(Point p1, Point p2, Point p) {
		double r;
		// compute maximum delta, for numerical stability
		// also handle case of p1-p2 being vertical or horizontal
		double dx = Math.abs(p2.x - p1.x);
		double dy = Math.abs(p2.y - p1.y);
		if (dx > dy) {
			r = (p.x - p1.x) / (p2.x - p1.x);
		} else {
			r = (p.y - p1.y) / (p2.y - p1.y);
		}
		return r;
	}
}
