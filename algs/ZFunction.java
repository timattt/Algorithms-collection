/**
 * 
 */
package algs;

/**
 * @author timat
 *
 */
public class ZFunction {

	public static int[] z(String line) {
		int[] z = new int[line.length()];
		z[0] = line.length();

		char[] array = line.toCharArray();

		int l = 0;
		int r = 0;
		for (int i = 1; i < line.length(); i++) {
			//	���������
			if (i <= r) {
				z[i] = Math.min(z[i - l], r - i + 1);
			}
			//	���������
			
			for (; i + z[i] < line.length() && array[i + z[i]] == array[z[i]]; z[i]++);
			
			//	���������
			if (i + z[i] - 1 > r) {
				l = i;
				r = i + z[i] - 1;
			}
			//	���������
		}
		return z;
	}

}
