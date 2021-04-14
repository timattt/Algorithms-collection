/**
 * 
 */
package algs;

/**
 * @author timat
 *
 */
public class GCD {

	public static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	/**
	 * result[0] = x; result[1] = y;
	 * 
	 * @param a
	 * @param b
	 * @param result
	 */
	public static int advanced_gcd(int a, int b, int[] result) {
		int g;
		if (b == 0) {
			g = a;
			result[0] = 1;
			result[1] = 0;
		} else {
			g = advanced_gcd(b, a % b, result);
			int x1 = result[0];
			int y1 = result[1];
			result[0] = y1;
			result[1] = x1 - (a / b) * y1;
		}
		return g;
	}

	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}
}
