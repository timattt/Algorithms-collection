/**
 * 
 */
package algs.dataStructures;

import java.util.Iterator;
import java.util.TreeSet;

public class Trie {

	// Root
	private final TrieVertex root = new TrieVertex((char) 0);

	public void put(String key, int value) {
		root.put(key, 0, value);
	}

	public int get(String key) {
		return root.get(key, 0);
	}

	public void delete(String key) {
		root.delete(key, 0);
	}

	public void print() {
		root.print(0);
	}

	private static class TrieVertex implements Comparable<TrieVertex> {

		// Comparer
		private static final TrieVertex comparer = new TrieVertex((char) 0);

		// Symbol
		private char symbol;

		// Children
		private final TreeSet<TrieVertex> children = new TreeSet<TrieVertex>();

		// Value
		private int value = -1;

		private TrieVertex(char symbol) {
			super();
			this.symbol = symbol;
		}

		@Override
		public int compareTo(TrieVertex o) {
			return o.symbol - this.symbol;
		}

		public void put(String key, int deep, int val) {
			if (deep == key.length()) {
				this.value = val;
				return;
			}

			TrieVertex n;
			n = findChild(key.charAt(deep));

			if (n == null) {
				n = new TrieVertex(key.charAt(deep));
				children.add(n);
			}

			n.put(key, deep + 1, val);
		}

		public int get(String key, int deep) {
			if (deep == key.length()) {
				return this.value;
			}

			TrieVertex child = findChild(key.charAt(deep));

			if (child == null) {
				return -1;
			} else {
				return child.get(key, deep + 1);
			}
		}

		private TrieVertex findChild(char key) {
			comparer.symbol = key;
			TrieVertex result = children.floor(comparer);
			if (result != null && result.symbol == key) {
				return result;
			} else {
				return null;
			}
		}

		public void print(int deep) {
			Iterator<TrieVertex> i = children.iterator();
			while (i.hasNext()) {
				for (int j = 0; j < deep; j++) {
					System.out.print(" ");
				}
				TrieVertex v = i.next();
				System.out.println(v.symbol);
				v.print(deep + 1);
			}
		}

		public void delete(String key, int deep) {
			TrieVertex n = findChild(key.charAt(deep));
			if (n != null) {
				if (deep + 1 == key.length()) {
					n.value = -1;
					if (n.children.isEmpty()) {
						this.children.remove(n);
					}
				} else {
					n.delete(key, deep + 1);
					if (n.children.isEmpty()) {
						this.children.remove(n);
					}
				}
			}
		}

	}

}
