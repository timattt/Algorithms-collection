/**
 * 
 */
package algs.dataStructures;

/**
 * @author timat
 *
 */
public class SegmentsTree {

	private static final int[] array = new int[2];

	// Root
	private final Vertex root;

	public SegmentsTree(int[] a) {
		root = new Vertex();
		root.init(a, 0, a.length - 1);
	}

	public int sum(int l, int r) {
		return root.sum(l, r);
	}

	public void update(int i, int v) {
		root.update(i, v);
	}

	public int[] min(int l, int r) {
		root.min(l, r);
		return array;
	}

	private class Vertex {

		private Vertex left;
		private Vertex right;

		private int value;

		private int l;
		private int r;
		private int mid;

		private int min;
		private int min_index;

		/**
		 * Init vertex with given array in segment [l, r].
		 * 
		 * @param a
		 * @param l
		 * @param r
		 */
		private void init(int[] a, int l, int r) {
			this.l = l;
			this.r = r;
			if (l == r) {
				value = a[l];
				mid = l;
				min = a[l];
				min_index = l;
				return;
			} else {
				left = new Vertex();
				right = new Vertex();

				mid = (l + r) / 2;
				left.init(a, l, mid);
				right.init(a, mid + 1, r);

				value = left.value + right.value;

				if (left.min < right.min) {
					this.min = left.min;
					this.min_index = left.min_index;
				} else {
					this.min = right.min;
					this.min_index = right.min_index;
				}
			}
		}

		public void min(int l, int r) {
			if (this.l == l && this.r == r) {
				array[0] = min;
				array[1] = min_index;
				return;
			}

			if (r <= mid) {
				left.min(l, r);
				return;
			}
			if (l > mid) {
				right.min(l, r);
				return;
			}

			left.min(l, mid);
			int min1 = array[0];
			int min1i = array[1];

			right.min(mid + 1, r);
			int min2 = array[0];
			int min2i = array[1];

			array[0] = Math.min(min1, min2);
			array[1] = (min1 < min2 ? min1i : min2i);
		}

		public int sum(int l, int r) {
			if (this.l == l && this.r == r) {
				return value;
			}
			if (r <= mid) {
				return left.sum(l, r);
			}
			if (l > mid) {
				return right.sum(l, r);
			}
			return left.sum(l, mid) + right.sum(mid + 1, r);
		}

		public void update(int i, int v) {
			if (l == i && r == i) {
				value = v;
				min = v;
				return;
			}
			if (i > mid) {
				right.update(i, v);
			} else {
				left.update(i, v);
			}
			value = right.value + left.value;
			min = Math.min(right.min, left.min);
			min_index = (right.min < left.min ? right.min_index : left.min_index);
		}
	}

}
