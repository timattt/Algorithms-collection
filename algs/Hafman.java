/**
 * 
 */
package algs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author timat
 *
 */
public class Hafman {

	/**
	 * ��� ���� ��������� �������� �������.
	 * 
	 * @param text
	 *            �����, ������� ������������.
	 * @return ���������� �.�.�. ��� ������� ������� ������������� �������� ����.
	 */
	public static TreeMap<Character, boolean[]> createTable(String text) {
		// 1 ������� ������ ������� ��� �������� ������
		int[] freq = new int[256];

		// ��������� ������. ������ ������ ������������ ��� ������������� � ������.
		for (int i = 0; i < text.length(); i++) {
			char s = text.charAt(i);
			freq[s]++;
		}

		// ������� ���������� ��������� �������� � ������.
		int size = 0;
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] > 0) {
				size++;
			}
		}

		// ������� ��������� ������, � ������� ������������� ������ Vertex.
		Vertex[] raw_list = new Vertex[size];

		// ��������� ������ ���������.
		int index = 0;
		for (int i = 0; i < freq.length; i++) {
			// ������ ���������, ���� ������� ������� ������ ����.
			if (freq[i] > 0) {
				int q = freq[i];
				raw_list[index] = new Vertex((char) (i), q);
				index++;
			}
		}

		// 2
		// ��������� ������ �� �������.
		Arrays.sort(raw_list, new Comparator<Vertex>() {
			@Override
			public int compare(Vertex o1, Vertex o2) {
				return o1.q - o2.q;
			}
		});

		// ������� ������ �������� ������.
		Vertex head = raw_list[0];
		Vertex curr = head;

		// ��������� ��������� ������. ����������� ��� � ������� ������.
		for (int i = 1; i < raw_list.length; i++) {
			curr.next = raw_list[i];
			curr = curr.next;
		}

		// 3
		// �� ��� ���, ���� ������ ������ ������ 1, ����������� ��������� ���.
		while (size != 1) {
			// ����� ��� ���������
			Vertex small1 = head;
			Vertex small2 = head.next;

			// ������� ������, ������� �������� �� �����������.
			Vertex united = new Vertex('$', small1.q + small2.q);

			// � �������� ������� ����������� ��� ��� ���������� �������.
			united.child0 = small1;
			united.child1 = small2;

			// �������� �������� ������.
			head = head.next.next;

			// ���� ����� � ������ �������� ����� ����, �� ������ ������ ����� �����. (��.
			// ����. ������) ������� ������� ���������� �����������. ����� ���������� ������
			// �����������.
			if (size > 2) {
				head = insert(head, united);
			} else {
				head = united;
			}

			// ��������� ������.
			size--;
		}

		// 6
		// ������� ������, ���� ����� ������ 1|0.
		boolean[] line = new boolean[text.length()];
		// ������� �.�.�. ��� �������� ������� � �����.
		TreeMap<Character, boolean[]> table = new TreeMap<Character, boolean[]>();

		// ������� ������.
		head.dfs(0, line, table);

		return table;
	}

	/**
	 * ��� ��� ���� ������������ �����, ��������� �������, � ����� �������������
	 * �����.
	 * 
	 * @param table
	 *            ��� �������.
	 * @param text
	 *            ��� �����.
	 * @return ������������� �����.
	 */
	public static String convert(TreeMap<Character, boolean[]> table, String text) {
		// �������
		StringBuilder result = new StringBuilder();

		// ������� Writer. ��� ����� ����������, ����� ����� ����� ��������.
		BitWriter writer = new BitWriter() {
			@Override
			protected void newSymbol(char s) {
				result.append(s);
			}
		};

		// ��������� ��� ������.
		for (int i = 0; i < text.length(); i++) {
			// ����� ������ � ��� ���
			char s = text.charAt(i);
			boolean[] code = table.get(s);

			// ��������� ���� �������. ��������� �� � ������.
			for (boolean bit : code) {
				writer.insertBit(bit);
			}

		}

		if (!writer.isEmpty()) {
			writer.flush();
		}

		return result.toString();
	}

	/**
	 * ��� ���� ������ ������� ����� �������. ������ �����������.
	 * 
	 * @param table
	 */
	public static void printTable(TreeMap<Character, boolean[]> table) {
		for (char q : table.keySet()) {
			System.out.print(q + " ");
			boolean[] code = table.get(q);
			for (int i = 0; i < code.length; i++) {
				System.out.print(code[i] ? "1" : "0");
			}
			System.out.println();
		}
	}

	/**
	 * ��� ���� ��������� ����� ������ � ������, ��� ����� �� �������� ����������.
	 * 
	 * @param head
	 *            ������ ������.
	 * @param in
	 *            ��, ��� �� ���������
	 * @return ���������� ����� ������, �.�. ���� in ������ ������ �� ����� ������,
	 *         ����� ���������� ������ ������.
	 */
	private static Vertex insert(Vertex head, Vertex in) {
		// �������� ����� ���� ������. ���� �� ������ ����, ������� ������ ����, ��� ��
		// ���������.
		Vertex curr = head;
		while (curr.next != null && curr.next.q < in.q) {
			curr = curr.next;
		}

		// ���� �������, ����� �������� �� ��������� �������� �������, �� ��������
		// ������ �� ���� � ����������� ������ � ����.
		if (curr == head) {
			in.next = head;
			return in;
		}

		// ���� ��� ������, �� ����������� in � ������. ����.
		in.next = curr.next;
		curr.next = in;

		// ��������� ���������� ������ ������.
		return head;
	}

	/**
	 * ���� ������������� ����� ����������� ���� � char. ����� ����� ����� char,
	 * ���������� �������, ������� ���������� ������� ������.
	 * 
	 * @author timat
	 *
	 */
	private static abstract class BitWriter {
		private char buffer;
		private int count;

		private final int size = 8;

		/**
		 * ��� ���� ��������� ������ � ������.
		 * 
		 * @param bit
		 */
		public void insertBit(boolean bit) {
			// ��������
			if (bit) {
				buffer |= (1 << (size - count - 1));
			}
			// ��������� �������.
			count++;
			// ���� ������� �����, �� �������� ����������� �����.
			if (count == size) {
				newSymbol(buffer);
				buffer = 0;
				count = 0;
			}
			// ���������� ������.
		}

		/**
		 * ����� ����� ������, ����� ����� ��������.
		 * 
		 * @param s
		 *            ������.
		 */
		protected abstract void newSymbol(char s);

		/**
		 * ��������� ������������� ������.
		 * 
		 * @return true, ���� ������ �����.
		 */
		public boolean isEmpty() {
			return count == 0;
		}

		/**
		 * �������� ������. �������� ��� �����.
		 */
		public void flush() {
			newSymbol(buffer);
			count = 0;
			buffer = 0;
		}
	}

	/**
	 * �������. ����� ������. ����� ������.
	 */
	private static class Vertex {

		// List
		// ��������� �� ��������� ������� ������.
		private Vertex next;

		// Children
		// ������� ������ �������.
		private Vertex child0;
		private Vertex child1;

		// Parameters
		// ������
		private char symbol;
		// ���������� ����� ������� � ������
		private int q;

		private Vertex(char symbol, int q) {
			super();
			this.symbol = symbol;
			this.q = q;
		}

		/**
		 * ����� � ������� ������ ��� ���������� ������� ����������.
		 * 
		 * @param deep
		 *            ������� ������, �� ������� ��������� ������ �������.
		 * @param line
		 *            ������ �� ����� � ������.
		 * @param table
		 *            �������, ���� ������������ ���������� ��������.
		 */
		public void dfs(int deep, boolean[] line, TreeMap<Character, boolean[]> table) {
			// ���� �� ����� ����, �� ������� ���� �� ����.
			if (child0 == null || child1 == null) {
				boolean[] copy = new boolean[deep];
				for (int i = 0; i < deep; i++) {
					copy[i] = line[i];
				}
				table.put(symbol, copy);
			} else {
				// ����� ���������� ����� � �������.
				line[deep] = true;
				child0.dfs(deep + 1, line, table);
				line[deep] = false;
				child1.dfs(deep + 1, line, table);
			}
		}
	}

}
