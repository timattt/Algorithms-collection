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
public class SimpsonRule {

	public static double integrate(int n, double a, double b, Method f) {

		double s = 0;
		double h = (b - a) / n;
		for (int i = 0; i <= n; ++i) {
			double x = a + h * i;
			try {
				s += (double) (f.invoke(null, x)) * ((i == 0 || i == n) ? 1 : ((i & 1) == 0) ? 2 : 4);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		s *= h / 3;

		return s;
	}

}
