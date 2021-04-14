/**
 * 
 */
package algs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author timat
 *
 */
public class Search {

	/**
	 * This method performs a binary search in the specified range. The F field
	 * denotes a monotonically increasing or decreasing function in the range to be
	 * searched.
	 * 
	 * It will be found such a minimum value of the argument at which the value of
	 * the function will be equal to Y.
	 * 
	 * Searches range from border-left, inclusive, to border-right, inclusive.
	 */
	public static int binarySearch_int(Method F, int Y, int border_left, int border_right) {
		int left = border_left - 1;
		int right = border_right + 1;
		int cur;
		while (right - left > 1) {
			cur = (right + left) / 2;
			try {
				if ((int) (F.invoke(null, cur)) < Y) {
					left = cur;
				} else {
					right = cur;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return right;
	}

	/**
	 * This method performs a binary search on a continuous specified interval.
	 */
	public static double binarySearch_double(Method F, double Y, double border_left, double border_right,
			double epsilon) {
		double left = border_left;
		double right = border_right;
		double cur = 0;
		while (right - left > epsilon) {
			cur = (left + right) / 2;
			try {
				if ((double) (F.invoke(null, cur)) < Y) {
					left = cur;
				} else {
					right = cur;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return right;
	}

	/**
	 * F is a continuous function with one extremum. The function searches for a
	 * maximum at a given interval. And with a given infelicity.
	 */
	public static double ternary_search_maximum_double(Method F, double border_left, double border_right,
			double epsilon) {
		double left = border_left;
		double right = border_right;
		while (right - left > epsilon) {
			double m1 = left + (right - left) / 3;
			double m2 = right - (right - left) / 3;
			try {
				if ((double) (F.invoke(null, m1)) < (double) (F.invoke(null, m2))) {
					left = m1;
				} else {
					right = m2;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return right;
	}

	public static int ternary_search_maximum_int(Method F, int border_left, int border_right) {
		int left = border_left;
		int right = border_right;
		while (right - left > 2) {
			int m1 = left + (right - left) / 3;
			int m2 = right - (right - left) / 3;
			try {
				if ((int) (F.invoke(null, m1)) < (int) (F.invoke(null, m2))) {
					left = m1;
				} else {
					right = m2;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return -1;
			}
		}

		int val = -1;
		int max = Integer.MIN_VALUE;

		for (; left <= right; left++) {
			try {
				if (max < (int) (F.invoke(null, left))) {
					max = (int) (F.invoke(null, left));
					val = left;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return val;
	}
}
