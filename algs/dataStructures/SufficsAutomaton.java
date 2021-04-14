/**
 * 
 */
package algs.dataStructures;

import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author timat
 *
 */
public class SufficsAutomaton {
	private final LinkedList<State> all = new LinkedList<State>();

	// index
	private int lastIndex = 0;

	// Root state
	private State root = new State(0);

	private State last = root;

	/**
	 * 
	 */
	public SufficsAutomaton() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (State s : all) {
			sb.append(s.toString());
		}

		return sb.toString();
	}

	public void add(char symbol) {
		State cur = new State(last.length + 1);

		State p = last;
		for (; !p.jumps.containsKey(symbol); p = p.link) {
			p.jumps.put(symbol, cur);
			if (p == root) {
				cur.link = root;
				last = cur;
				return;
			}
		}

		State q = p.jumps.get(symbol);

		if (p.length + 1 == q.length) {
			cur.link = q;
		} else {
			State clone = new State(q);
			clone.length = p.length + 1;
			cur.link = clone;
			q.link = clone;
			for (State st = p; st.link != null && st.jumps.get(symbol) == q; st = st.link) {
				st.jumps.replace(symbol, clone);
			}
		}

		last = cur;
	}

	private class State {
		private int length;
		private State link = null;
		private final int index = lastIndex++;
		private final TreeMap<Character, State> jumps = new TreeMap<Character, State>();

		private State(int length) {
			super();
			this.length = length;
			all.add(this);
		}

		private State(State s) {
			this.link = s.link;
			this.jumps.putAll(s.jumps);
			all.add(this);
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();

			b.append("state " + index + " link " + (link != null ? link.index : "?") + " jumps: ");
			for (Entry<Character, State> ent : jumps.entrySet()) {
				b.append(ent.getKey() + " -> " + ent.getValue().index).append(" ");
			}
			b.append("\n");

			return b.toString();
		}

	}

}
