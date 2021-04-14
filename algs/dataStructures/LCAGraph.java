/**
 * 
 */
package algs.dataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import algs.Graph;

/**
 * @author timat
 *
 */
public class LCAGraph extends Graph {

	// Preprocessing arrays
	private final ArrayList<Integer> order = new ArrayList<Integer>(2 * n - 1);
	private final int[] first = new int[n];

	private SegmentsTree st;

	/**
	 * @param n
	 */
	public LCAGraph(int n) {
		super(n);
	}

	private LCAGraph(Graph g2) {
		super(g2);
	}

	public void init() {
		int[] heights = calcDfsHeights(0);

		treeDfs(-1, 0, null, null, new Consumer<int[]>() {
			@Override
			public void accept(int[] t) {
				order.add((int) t[1]);
			}
		}, new Consumer<int[]>() {
			@Override
			public void accept(int[] t) {
				if (t[0] != -1)
					order.add((int) t[0]);
			}
		});

		Arrays.fill(first, -1);

		for (int i = 0; i < order.size(); i++) {
			int v = order.get(i);
			if (first[v] == -1) {
				first[v] = i;
			}
		}

		int[] order_heights = new int[order.size()];
		for (int i = 0; i < order.size(); i++) {
			order_heights[i] = heights[order.get(i)];
		}
		
		System.out.println(order.toString());

		st = new SegmentsTree(order_heights);
	}

	public int lca(int v1, int v2) {
		int[] a = st.min(Math.min(first[v1], first[v2]), Math.max(first[v1], first[v2]));
		return order.get(a[1]);
	}

}
