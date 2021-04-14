/**
 * 
 */
package algs.dataStructures;

import java.util.LinkedList;
import java.util.List;

/**
 * @author timat
 *
 */
public class SqrtDecomposedLinkedList<T> {

	// Max entry capacity
	private final int s;

	// First entry
	private final SdEntry first = new SdEntry();

	// Size
	private int size;

	public SqrtDecomposedLinkedList(int s) {
		super();
		this.s = s;
	}

	public int size() {
		return size;
	}

	public void addLast(T o) {
		add(size, o);
	}

	public void add(int i, T o) {
		size++;
		for (SdEntry cur = first; true; cur = cur.getNext()) {
			List<T> objects = cur.objects;
			if (i <= objects.size()) {
				objects.add(i, o);
				if (objects.size() > s) {
					o = objects.remove(objects.size() - 1);
					i = 0;
				} else {
					return;
				}
			} else {
				i -= objects.size();
			}
		}
	}

	public T get(int i) {
		for (SdEntry cur = first; cur != null; cur = cur.next) {
			List<T> objects = cur.objects;
			if (i < objects.size()) {
				return objects.get(i);
			} else {
				i -= objects.size();
			}
		}
		return null;
	}

	public void remove(int i) {
		for (SdEntry previous = null, e = first; e != null; previous = e, e = e.next) {
			List<T> objects = e.objects;
			if (i < objects.size()) {
				objects.remove(i);
				size--;
				if (objects.isEmpty() && previous != null) {
					previous.next = e.next;
				}
				return;
			} else {
				i -= objects.size();
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();

		for (SdEntry e = first; e != null; e = e.next) {
			b.append(e.toString());
		}
		return "SqrtDecompositionList [s=" + s + ", size=" + size + ", entries=" + b.toString() + "]";
	}

	private class SdEntry {
		private SdEntry next;
		private final List<T> objects = new LinkedList<T>();

		private SdEntry getNext() {
			if (next == null) {
				return next = new SdEntry();
			}
			return next;
		}

		@Override
		public String toString() {
			return "SdEntry [objects=" + objects + "]";
		}
	}

}
