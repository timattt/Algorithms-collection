/**
 * 
 */
package algs.dataStructures;

/**
 * @author timat
 *
 */
public class SmartQueue<T extends Comparable<T>> {
	private final SmartStack<T> s1 = new SmartStack<T>();
	private final SmartStack<T> s2 = new SmartStack<T>();

	public void push(T el) {
		s1.push(el);
	}

	public T pop() {
		while (!s1.getStack().isEmpty()) {
			s2.push(s1.pop());
		}

		return s2.pop();
	}

	public T min() {
		if (s1.getStack().isEmpty() || s2.getStack().isEmpty()) {
			return s1.getStack().isEmpty() ? s2.min() : s1.min();
		} else {
			T o1 = s1.min();
			T o2 = s2.min();
			if (o1.compareTo(o2) < 0) {
				return o1;
			} else {
				return o2;
			}
		}
	}

	@Override
	public String toString() {
		return "SmartQueue [s1=" + s1 + ", s2=" + s2 + "]";
	}
}