/**
 * 
 */
package algs.dataStructures.treap;

/**
 * @author timat
 *
 */
public class Treap {

	private int x;
	private int y;

	private int size;

	private Treap left;
	private Treap right;

	public Treap(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static int sizeOf(Treap tr) {
		return (tr == null ? 0 : tr.size);
	}

	public static void update(Treap tr) {
		if (tr != null) {
			tr.size = 1 + sizeOf(tr.left) + sizeOf(tr.right);
		}
	}

	/**
	 * [0, x] and (x, n)
	 * 
	 * @param tr
	 * @param lr
	 * @param x
	 */
	public static void split(Treap tr, Treap[] lr, int x) {
		if (tr == null) {
			lr[0] = lr[1] = null;
		} else if (x < tr.x) {
			split(tr.left, lr, x);
			tr.left = lr[1];
			lr[1] = tr;
		} else {
			split(tr.right, lr, x);
			tr.right = lr[0];
			lr[0] = tr;
		}

		update(lr[0]);
		update(lr[1]);
	}

	public static void insert(Treap[] tr, Treap ins) {
		if (tr[0] == null) {
			tr[0] = ins;
		} else {
			Treap root = tr[0];

			if (ins.y < root.y) {
				if (ins.x < tr[0].x) {
					tr[0] = root.left;
					insert(tr, ins);
					root.left = tr[0];
				} else {
					tr[0] = root.right;
					insert(tr, ins);
					root.right = tr[0];
				}
				tr[0] = root;
			} else {
				split(root, tr, ins.x);
				ins.left = tr[0];
				ins.right = tr[1];

				tr[0] = ins;
			}
		}

		update(tr[0]);
	}
	
	public static void merge(Treap l, Treap r, Treap[] tr) {
		if (l == null || r == null) {
			tr[0] = (l == null ? r : l);
		} else {
			if (l.y < r.y) {
				merge(l, r.left, tr);
				r.left = tr[0];
				tr[0] = r;
			} else {
				merge(l.right, r, tr);
				l.right = tr[0];
				tr[0] = l;
			}
		}

		update(tr[0]);
	}

	public static void erase(Treap[] tr, int x) {
		if (tr[0] != null) {
			Treap root = tr[0];
			if (root.x == x) {
				merge(root.left, root.right, tr);
			} else {
				if (x < root.x) {
					tr[0] = root.left;
					erase(tr, x);
					root.left = tr[0];
				}
				if (x > root.x) {
					tr[0] = root.right;
					erase(tr, x);
					root.right = tr[0];
				}

				tr[0] = root;
			}
		}

		update(tr[0]);
	}

	public static void elements(Treap tr) {
		System.out.print("[");
		for (int i = 0, size = sizeOf(tr); i < size; i++) {
			System.out.print(getCount(tr, i));
			if (i + 1 < size) {
				System.out.print(", ");
			}
		}
		System.out.println("]");
	}

	public static int getCount(Treap tr, int pos) {
		if (tr == null) {
			return -1;
		}
		int cur = sizeOf(tr.left);

		if (pos == cur) {
			return tr.x;
		}
		if (pos < cur) {
			return getCount(tr.left, pos);
		} else {
			return getCount(tr.right, pos - cur - 1);
		}
	}

}
