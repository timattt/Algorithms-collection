/**
 * 
 */
package algs.dataStructures;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author timat
 *
 */
public class DSU {

	// Tree
	private final ArrayList<Union> unions = new ArrayList<Union>();

	public DSU(int startcap) {
		unions.ensureCapacity(startcap);
		for (int i = 0; i < startcap; i++) {
			makeSet();
		}
	}

	public DSU() {
	}

	/**
	 * Creates new set and returns its index.
	 * 
	 * @return : Index of the new element.
	 */
	public int makeSet() {
		int index = unions.size();
		Union entry = new Union(index);
		unions.add(index, entry);
		return index;
	}

	/**
	 * This method find set in which given set is in.
	 * 
	 * @param setIndex
	 * @return
	 */
	public int findSet(int setIndex) {
		if (unions.get(setIndex).parent == setIndex) {
			return setIndex;
		} else {
			int parent = unions.get(setIndex).parent;
			int result = findSet(parent);
			unions.get(setIndex).parent = result;
			return result;
		}
	}

	/**
	 * This method unites two sets.
	 * 
	 * @param element1
	 *            : Index of the set 1.
	 * @param element2
	 *            : Index if the set 2.
	 */
	public void unionSets(int element1, int element2) {
		int set1 = findSet(element1);
		int set2 = findSet(element2);
		Union es1 = unions.get(set1);
		Union es2 = unions.get(set2);
		if (es1.rank == es2.rank) {
			es1.rank++;
			es2.parent = set1;
			return;
		}
		if (es1.rank > es2.rank) {
			es2.parent = set1;
		} else {
			es1.parent = set2;
		}
	}

	@Override
	public String toString() {
		TreeMap<Integer, StringBuilder> str = new TreeMap<Integer, StringBuilder>();

		for (int unionIndex = 0; unionIndex < unions.size(); unionIndex++) {
			int set = findSet(unionIndex);
			if (!str.containsKey(set)) {
				str.put(set, new StringBuilder());
			}

			StringBuilder b = str.get(set);
			if (b.length() > 0) {
				b.append(", ");
			} else {
				b.append("Set leader : ").append(set).append(" : [");
			}
			b.append(unionIndex);
		}

		StringBuilder result = new StringBuilder();
		Set<Entry<Integer, StringBuilder>> es = str.entrySet();
		for (Entry<Integer, StringBuilder> e : es) {
			result.append(e.getValue().append("]\n"));
		}

		return result.toString();
	}

	private class Union {
		private int parent;
		private int rank = 0;

		private Union(int parent) {
			super();
			this.parent = parent;
		}
	}

}
