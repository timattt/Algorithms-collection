/**
 * 
 */
package algs.dataStructures;

/**
 * @author timat
 *
 */
public class SqrtDecomposition {

	// Arrays
	private final int[] a;
	private final int[] b;

	// Maximum block length
	private final int n;

	public SqrtDecomposition(int[] a) {
		super();
		this.a = a;
		n = (int) Math.sqrt(a.length);

		b = new int[up(a.length, n)];
		for (int i = 0; i < a.length; i++) {
			b[i / n] += a[i];
		}
	}

	private int up(int a, int b) {
		return a / b + (a % b == 0 ? 0 : 1);
	}

	public int sum(int i, int j) {
		int sum = 0;
		for (int k = i; k <= j;) {
			if (k % n == 0 && k + n - 1 <= j) {
				sum += b[k / n];
				k += n;
			} else {
				sum += a[k];
				k++;
			}
		}
		return sum;
	}

}
