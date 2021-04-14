/**
 * 
 */
package algs.dataStructures;

import java.util.LinkedList;

/**
 * @author timat
 *
 */
public class SmartStack<T extends Comparable<T>> {
	private final LinkedList<E<T>> stack = new LinkedList<E<T>>();

	public void push(T e) {
		E<T> el = new E<T>();
		el.element = e;
		if (getStack().isEmpty() || e.compareTo(getStack().getFirst().min) < 0) {
			el.min = el.element;
		} else {
			el.min = getStack().getFirst().min;
		}
		getStack().addFirst(el);
	}

	public T pop() {
		return getStack().removeFirst().element;
	}

	public T min() {
		return getStack().getFirst().min;
	}

	private static class E<T> {
		private T element;
		private T min;

		@Override
		public String toString() {
			return "E [element=" + element + ", min=" + min + "]";
		}

	}

	@Override
	public String toString() {
		return "SmartStack [stack=" + getStack() + "]";
	}

	public LinkedList<E<T>> getStack() {
		return stack;
	}

}