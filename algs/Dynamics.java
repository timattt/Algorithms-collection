/**
 * 
 */
package algs;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author timat
 *
 */
public class Dynamics {

	public static int findBiggestZeroSubMatrix(boolean[][] matrix) {
		int result = 0;

		int n = matrix.length;
		int m = matrix[0].length;

		int[] d = new int[m];
		Arrays.fill(d, -1);

		Stack<Integer> st = new Stack<Integer>();

		int[] d1 = new int[m];
		int[] d2 = new int[m];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (matrix[i][j]) {
					d[j] = i;
				}
			}

			st.clear();

			for (int j = 0; j < m; j++) {
				while (!st.isEmpty() && d[st.peek()] <= d[j]) {
					st.pop();
				}
				d1[j] = (st.isEmpty() ? -1 : st.peek());
				st.push(j);
			}

			st.clear();

			for (int j = m - 1; j >= 0; j--) {
				while (!st.isEmpty() && d[st.peek()] <= d[j]) {
					st.pop();
				}
				d2[j] = (st.isEmpty() ? m : st.peek());
				st.push(j);
			}

			for (int j = 0; j < m; j++) {
				int k1 = d1[j];
				int k2 = d2[j];
				result = Math.max(result, (k2 - k1 - 1) * (i - d[j]));
			}
		}

		return result;
	}

}
