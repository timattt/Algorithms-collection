/**
 * 
 */
package algs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * @author timat
 *
 */
public class Graph {

	// Utility
	protected static final int[] array = new int[3];

	// Graph
	protected final List<Edge>[] g;
	protected final int n;

	@SuppressWarnings("unchecked")
	public Graph(int n) {
		super();
		this.n = n;
		g = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			g[i] = new ArrayList<Edge>();
		}
	}

	public Graph(List<Edge>[] g) {
		this.g = g;
		n = g.length;
	}

	/**
	 * @param g2
	 */
	@SuppressWarnings("unchecked")
	public Graph(Graph g2) {
		this.n = g2.n;
		g = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			g[i] = new ArrayList<Edge>();
			g[i].addAll(g2.g[i]);
		}
	}

	public LinkedList<Integer> findEulerPath(int start) {
		LinkedList<Integer> path = new LinkedList<Integer>();
		int notmod = 0;
		for (int v = 0; v < n; v++) {
			if (g[v].size() % 2 != 0) {
				notmod++;
			}
		}
		if (notmod != 2 && notmod != 1) {
			return null;
		}

		Graph copy = new Graph(this);

		Stack<Integer> st = new Stack<Integer>();
		st.push(0);
		while (!st.isEmpty()) {
			int v = st.peek();
			if (copy.g[v].isEmpty()) {
				path.add(v);
				st.pop();
			} else {
				Edge to = copy.g[v].get(0);
				st.push(to.to);
				copy.removeNonOrientedEdge(v, to.to);
			}
		}

		return path;
	}

	public LinkedList<Integer> check_cycle(boolean accept_edge_cycles) {
		int[] colors = new int[n];

		final int color_grey = 1;
		final int color_black = 2;

		boolean[] has_cycle = new boolean[1];
		int[] cycle_start = { -1 };
		int[] cycle_end = { -1 };

		treeDfs(-1, 0, null, new Consumer<int[]>() {
			@Override
			public void accept(int[] t) {
				int p = t[0];
				int v = t[1];
				int to = t[2];
				if (colors[to] == color_grey && (to != p || accept_edge_cycles)) {
					has_cycle[0] = true;
					cycle_start[0] = to;
					cycle_end[0] = v;
				}
			}
		}, new Consumer<int[]>() {

			@Override
			public void accept(int[] t) {
				int v = t[1];
				colors[v] = color_grey;
			}
		}, new Consumer<int[]>() {

			@Override
			public void accept(int[] t) {
				int v = t[1];
				colors[v] = color_black;
			}
		});

		if (has_cycle[0]) {
			LinkedList<Integer> q = new LinkedList<Integer>();

			int[] parents = dfs_parents_array();

			for (int v = cycle_end[0]; v != cycle_start[0]; v = parents[v]) {
				q.addFirst(v);
			}

			q.addFirst(cycle_start[0]);

			return q;
		}
		return null;
	}

	public Graph transpose() {
		Graph t = new Graph(n);

		for (int p = 0; p < n; p++) {
			for (int c = 0; c < g[p].size(); c++) {
				t.insertOrientedEdge(g[p].get(c).to, p, g[p].get(c).w);
			}
		}

		return t;
	}

	public int[] dijkstra(int start) {
		int[] result = new int[n];
		boolean[] checked = new boolean[n];

		Arrays.fill(result, Integer.MAX_VALUE);
		result[start] = 0;

		int check = 0;

		while (check != n) {
			int cur = -1;
			int min = Integer.MAX_VALUE;
			for (int v = 0; v < n; v++) {
				if (!checked[v] && result[v] < min) {
					cur = v;
					min = result[v];
				}
			}

			for (int ci = 0; ci < g[cur].size(); ci++) {
				Edge c = g[cur].get(ci);
				result[c.to] = Math.min(result[c.to], result[cur] + c.w);
			}

			checked[cur] = true;
			check++;
		}

		return result;
	}

	public Graph scc() {
		int[] sc = sc();

		int totalGroups = sc[n];

		Graph scc = new Graph(totalGroups);

		for (int p = 0; p < n; p++) {
			for (int ci = 0; ci < g[p].size(); ci++) {
				int c = g[p].get(ci).to;
				if (sc[p] != sc[c]) {
					scc.insertOrientedEdge(sc[p], sc[c], 0);
				}
			}
		}

		return scc;
	}

	/**
	 * In result array in last slot there is an integer that represents total groups
	 * of condensation.
	 * 
	 * @return
	 */
	public int[] sc() {
		ArrayList<Integer> top = new ArrayList<Integer>();
		boolean[] checked = new boolean[n];

		for (int i = 0; i < n; i++) {
			if (!checked[i]) {
				dfs(i, checked, null, new Consumer<Integer>() {
					@Override
					public void accept(Integer t) {
						top.add(0, t);
					}
				});
			}
		}

		int[] sc = new int[n + 1];

		Graph gt = transpose();

		checked = new boolean[n];
		int[] currentGroup = new int[1];
		for (int i = 0; i < n; i++) {
			if (checked[top.get(i)]) {
				continue;
			}
			gt.dfs(top.get(i), checked, new Consumer<Integer>() {
				@Override
				public void accept(Integer t) {
					sc[t] = currentGroup[0];
				}
			}, null);
			currentGroup[0]++;
		}

		sc[n] = currentGroup[0];
		return sc;
	}

	public Stack<Integer> topologicalSort() {
		Stack<Integer> top = new Stack<Integer>();

		boolean[] checked = new boolean[n];
		for (int v = 0; v < n; v++) {
			if (checked[v]) {
				continue;
			}

			dfs(v, checked, null, new Consumer<Integer>() {
				@Override
				public void accept(Integer t) {
					top.push(t);
				}
			});
		}

		return top;
	}

	protected int[] calcDfsVerticeInTime() {
		int[] tin = new int[n];
		int[] currTime = new int[1];

		boolean[] checked = new boolean[n];

		for (int i = 0; i < n; i++) {
			if (!checked[i]) {
				dfs(i, checked, new Consumer<Integer>() {
					@Override
					public void accept(Integer t) {
						tin[t] = currTime[0]++;
					}
				}, null);
			}
		}

		return tin;
	}

	public Graph mst_prim() {
		Graph mst = new Graph(n);

		// mins[0] - weight
		// mins[1] - child
		int[][] mins = new int[n][2];

		for (int i = 0; i < n; i++) {
			mins[i][0] = Integer.MAX_VALUE;
		}

		boolean[] checked = new boolean[n];

		// Filling mins array for start vertex
		for (Edge c : g[0]) {
			mins[c.to][0] = c.w;
		}

		checked[0] = true;

		while (true) {
			int min_v = -1;
			int min = Integer.MAX_VALUE;
			for (int v = 0; v < n; v++) {
				if (checked[v]) {
					continue;
				}

				if (min > mins[v][0]) {
					min = mins[v][0];
					min_v = v;
				}
			}

			if (min_v == -1) {
				break;
			}

			checked[min_v] = true;
			mst.insertNonOrientedEdge(min_v, mins[min_v][1], mins[min_v][0]);

			// Filling mins array for new vertex
			for (Edge v : g[min_v]) {
				if (!checked[v.to]) {
					if (mins[v.to][0] > v.w) {
						mins[v.to][0] = v.w;
						mins[v.to][1] = min_v;
					}
				}
			}
		}

		return mst;
	}

	public void findCutPoints(Consumer<Integer> isbridge) {
		int[] tin = calcDfsVerticeInTime();
		int[] fup = Arrays.copyOf(tin, n);

		boolean[] checked = new boolean[n];

		for (int[] i = new int[1]; i[0] < n; i[0]++) {
			if (!checked[i[0]]) {
				treeDfs(-1, i[0], checked, new Consumer<int[]>() {
					@Override
					public void accept(int[] t) {
						int p = t[0];
						int v = t[1];
						int c = t[2];

						if (checked[c] && p != c) {
							fup[v] = Math.min(fup[v], tin[c]);
						}
					}
				}, null, new Consumer<int[]>() {
					@Override
					public void accept(int[] t) {
						int p = t[0];
						int v = t[1];
						if (p != -1 && p != i[0]) {
							fup[p] = Math.min(fup[p], fup[v]);

							if (fup[v] > tin[p]) {
								isbridge.accept(p);
							}
						} else if (g[i[0]].size() > 1) {
							isbridge.accept(i[0]);
						}
					}
				});
			}
		}
	}

	/**
	 * This method gives length in dfs tree from start vertex to all other.
	 * 
	 * @return
	 */
	protected int[] calcDfsHeights(int start) {
		int[] result = new int[n];

		int[] height = new int[] { -1 };

		dfs(start, null, new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {
				height[0]++;
				result[t] = height[0];
			}
		}, new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {
				height[0]--;
			}
		});

		return result;
	}

	/**
	 * p -> v -> to; fup[v] = min(fup[to[i]], tin[p[i]], tin[v]); Value to[i] must
	 * be the best, so algorithm checks it when all child vertices are checked. p[i]
	 * must not be equal to vertex v parent; (p[i] != p)
	 * 
	 * @param isbridge
	 *            - invokes when algorithm find a bridge.
	 */
	public void findBridges(Consumer<int[]> isbridge) {
		int[] tin = calcDfsVerticeInTime();
		int[] fup = Arrays.copyOf(tin, n);

		int[] bridge = new int[2];

		boolean[] checked = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (!checked[i]) {
				treeDfs(-1, i, checked, new Consumer<int[]>() {
					@Override
					public void accept(int[] t) {
						int p = t[0];
						int v = t[1];
						int c = t[2];
						if (p != -1 && checked[c] && p != c) {
							fup[v] = Math.min(tin[c], fup[v]);
						}
					}
				}, null, new Consumer<int[]>() {
					@Override
					public void accept(int[] t) {

						int p = t[0];
						int v = t[1];

						if (p == -1) {
							return;
						}

						fup[p] = Math.min(fup[p], fup[v]);

						if (fup[v] > tin[p]) {
							bridge[0] = p;
							bridge[1] = v;
							isbridge.accept(bridge);
						}
					}
				});
			}
		}
	}

	protected int[] findMinIncomingEdgeForAllVertices() {
		int[] result = new int[n];
		Arrays.fill(result, Integer.MAX_VALUE);
		for (int p = 0; p < n; p++) {
			for (Edge c : g[p]) {
				result[c.to] = Math.min(result[c.to], c.w);
			}
		}

		return result;
	}

	protected boolean canVisitAllVerticesFrom(int start) {
		int[] vis = new int[1];
		dfs(start, null, new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {
				vis[0]++;
			}
		}, null);
		return vis[0] == n;
	}

	public long mst(int start) {
		long result = 0;

		int[] mins = findMinIncomingEdgeForAllVertices();
		for (int i = 0; i < mins.length; i++) {
			if (i != start) {
				result += mins[i];
			}
		}

		Graph zero = new Graph(n);

		for (int p = 0; p < n; p++) {
			for (Edge c : g[p]) {
				if (mins[c.to] == c.w) {
					zero.insertOrientedEdge(p, c.to, 0);
				}
			}
		}

		if (zero.canVisitAllVerticesFrom(start)) {
			/*
			 * Запускаем обход в шлубину по нулевому графу и возвращаем ребра.
			 */
			return result;
		}

		int[] sc = zero.sc();

		int totalComponents = sc[n];

		Graph rec = new Graph(totalComponents);

		for (int p = 0; p < n; p++) {
			for (Edge c : g[p]) {
				if (sc[p] != sc[c.to]) {
					rec.insertOrientedEdge(sc[p], sc[c.to], c.w - mins[c.to]);
				}
			}
		}

		result += rec.mst(sc[start]);

		/*
		 * После рекурсивного вызова mst имеем ребра, которые соединяют определенные компоненты связности.
		 * Чтобы конвертировать их в ребра этого графа, можно каждому конденсированному ребру
		 * указывать сслыку на соответствующее ребро в графе.
		 * После чего добавляем новые ребра в нулевой граф запускаем обход в глубину и возвращаем ребра.
		 */
		return result;
	}

	public void removeOrientedEdge(int parent, int child) {
		for (int i = 0; i < g[parent].size(); i++) {
			if (g[parent].get(i).to == child) {
				g[parent].remove(i);
				return;
			}
		}
	}

	public void removeNonOrientedEdge(int v1, int v2) {
		removeOrientedEdge(v1, v2);
		removeOrientedEdge(v2, v1);
	}

	public void insertOrientedEdge(int parent, int child, int w) {
		g[parent].add(new Edge(child, w));
	}

	public void insertNonOrientedEdge(int v1, int v2, int w) {
		insertOrientedEdge(v1, v2, w);
		insertOrientedEdge(v2, v1, w);
	}

	protected int[] dfs_parents_array() {
		int[] parents = new int[n];

		treeDfs(-1, 0, null, null, new Consumer<int[]>() {

			@Override
			public void accept(int[] t) {
				int p = t[0];
				int v = t[1];
				parents[v] = p;
			}

		}, null);
		return parents;
	}

	/**
	 * 
	 * @param parent
	 * @param cur
	 * @param checked
	 * @param pack
	 *            - invokes with vertices: parent -> vertex -> vertex child. (vertex
	 *            child can be equal to parent)
	 * @param invertex
	 *            - invokes with vertices: parent -> vertex -> -1. (Invokes when dfs
	 *            goes into new vertex)
	 * @param outvertex
	 *            - invokes with vertices: parent -> vertex -> -1. (Invokes when dfs
	 *            goes out from vertex)
	 */
	public void treeDfs(int parent, int cur, boolean[] checked, Consumer<int[]> pack, Consumer<int[]> invertex,
			Consumer<int[]> outvertex) {
		if (checked == null) {
			checked = new boolean[n];
		}

		if (invertex != null) {
			array[0] = parent;
			array[1] = cur;
			array[2] = -1;
			invertex.accept(array);
		}

		checked[cur] = true;

		for (Edge v : g[cur]) {
			if (pack != null) {
				array[0] = parent;
				array[1] = cur;
				array[2] = v.to;
				pack.accept(array);
			}
			if (!checked[v.to]) {
				treeDfs(cur, v.to, checked, pack, invertex, outvertex);
			}
		}

		if (outvertex != null) {
			array[0] = parent;
			array[1] = cur;
			array[2] = -1;
			outvertex.accept(array);
		}

	}

	public void dfs(int cur, boolean[] checked, Consumer<Integer> invertex, Consumer<Integer> outvertex) {
		if (checked == null) {
			checked = new boolean[n];
		}

		if (invertex != null)
			invertex.accept(cur);

		checked[cur] = true;

		for (Edge v : g[cur]) {
			if (!checked[v.to]) {
				dfs(v.to, checked, invertex, outvertex);
			}
		}

		if (outvertex != null)
			outvertex.accept(cur);

	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int p = 0; p < n; p++) {
			for (Edge c : g[p]) {
				b.append("[").append(p).append(", ").append(c.to).append("->").append(c.w).append("] ");
			}
		}
		return b.toString();
	}

	private class Edge {
		private int to;
		private int w;

		private Edge(int to, int w) {
			super();
			this.to = to;
			this.w = w;
		}

		@Override
		public String toString() {
			return "GraphVertex [v=" + to + ", w=" + w + "]";
		}

	}
}
