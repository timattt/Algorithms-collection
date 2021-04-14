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
	 * Эта вещь применяет алгоритм Хафмана.
	 * 
	 * @param text
	 *            Текст, который используется.
	 * @return Возвращает К.Ч.Д. Где каждому символу соответствует бинарный шифр.
	 */
	public static TreeMap<Character, boolean[]> createTable(String text) {
		// 1 Создаем массив частоты для символов строки
		int[] freq = new int[256];

		// Прогоняем строку. Кажому сиволу соответсвует его встречаемость в тексте.
		for (int i = 0; i < text.length(); i++) {
			char s = text.charAt(i);
			freq[s]++;
		}

		// Считаем количество различных символов в строке.
		int size = 0;
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] > 0) {
				size++;
			}
		}

		// Создаем первичный массив, в который упаковываются классы Vertex.
		Vertex[] raw_list = new Vertex[size];

		// Заполняем массив объектами.
		int index = 0;
		for (int i = 0; i < freq.length; i++) {
			// Объект создается, если частота символа больше нуля.
			if (freq[i] > 0) {
				int q = freq[i];
				raw_list[index] = new Vertex((char) (i), q);
				index++;
			}
		}

		// 2
		// Сортируем массив по частоте.
		Arrays.sort(raw_list, new Comparator<Vertex>() {
			@Override
			public int compare(Vertex o1, Vertex o2) {
				return o1.q - o2.q;
			}
		});

		// Создаем Голову связного списка.
		Vertex head = raw_list[0];
		Vertex curr = head;

		// Прогоняем первичный массив. Упаковываем все в связный список.
		for (int i = 1; i < raw_list.length; i++) {
			curr.next = raw_list[i];
			curr = curr.next;
		}

		// 3
		// До тех пор, пока размер списка больше 1, сворачиваем последние два.
		while (size != 1) {
			// Берем два последних
			Vertex small1 = head;
			Vertex small2 = head.next;

			// Создаем объект, который является их объедиением.
			Vertex united = new Vertex('$', small1.q + small2.q);

			// В качестве сыновей привязываем ему два предыдущих объекта.
			united.child0 = small1;
			united.child1 = small2;

			// Изменяем значение головы.
			head = head.next.next;

			// Если длина в начале итерации равна двум, то теперь голова будет нулем. (см.
			// пред. строку) Поэтому головой становится объединение. Иначе используем функию
			// объединения.
			if (size > 2) {
				head = insert(head, united);
			} else {
				head = united;
			}

			// Уменьшаем размер.
			size--;
		}

		// 6
		// Создаем массив, куда будем класть 1|0.
		boolean[] line = new boolean[text.length()];
		// Создаем К.Ч.Д. для удобного доступа к шифру.
		TreeMap<Character, boolean[]> table = new TreeMap<Character, boolean[]>();

		// Обходим дерево.
		head.dfs(0, line, table);

		return table;
	}

	/**
	 * Вот эта вещь Конвертирует текст, использую таблицу, в новым зашифрованный
	 * текст.
	 * 
	 * @param table
	 *            Это таблица.
	 * @param text
	 *            Это текст.
	 * @return Зашифрованный текст.
	 */
	public static String convert(TreeMap<Character, boolean[]> table, String text) {
		// Создаем
		StringBuilder result = new StringBuilder();

		// Создаем Writer. Его метод стработает, когда буфер будет заполнен.
		BitWriter writer = new BitWriter() {
			@Override
			protected void newSymbol(char s) {
				result.append(s);
			}
		};

		// Прогоняем всю строку.
		for (int i = 0; i < text.length(); i++) {
			// Берем символ и его код
			char s = text.charAt(i);
			boolean[] code = table.get(s);

			// Прогоняем биты символа. Вставляем их в буффер.
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
	 * Эта вещь просто выводит новую таблицу. Ничего интересного.
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
	 * Эта вещь вставляет новый объект в список, так чтобы не нарушить сортировку.
	 * 
	 * @param head
	 *            Голова списка.
	 * @param in
	 *            То, что мы вставляем
	 * @return Возвращает новую голову, т.к. если in должен встать на место головы,
	 *         нужно произвести замену головы.
	 */
	private static Vertex insert(Vertex head, Vertex in) {
		// Проходим через весь список. Пока не находи вещь, которая меньше того, что мы
		// вставляем.
		Vertex curr = head;
		while (curr.next != null && curr.next.q < in.q) {
			curr = curr.next;
		}

		// Если элемент, после которого мы вставляем является головой, то заменяем
		// голову на него и привязываем голову к нему.
		if (curr == head) {
			in.next = head;
			return in;
		}

		// Если все хорошо, то Привязываем in к найден. элем.
		in.next = curr.next;
		curr.next = in;

		// Формально возвращаем старую голову.
		return head;
	}

	/**
	 * Этот замечательный класс Упаковывает биты в char. Когда будет готов char,
	 * вывывается функция, которая отправляет готовый символ.
	 * 
	 * @author timat
	 *
	 */
	private static abstract class BitWriter {
		private char buffer;
		private int count;

		private final int size = 8;

		/**
		 * Эта вещь вставляет символ в буффер.
		 * 
		 * @param bit
		 */
		public void insertBit(boolean bit) {
			// Вставлем
			if (bit) {
				buffer |= (1 << (size - count - 1));
			}
			// Прибавлем счетчик.
			count++;
			// Если счетчик готов, то вывываем абстрактный метод.
			if (count == size) {
				newSymbol(buffer);
				buffer = 0;
				count = 0;
			}
			// Сбрасываем буффер.
		}

		/**
		 * Метод будет вызван, когда буфер заполнен.
		 * 
		 * @param s
		 *            Буффер.
		 */
		protected abstract void newSymbol(char s);

		/**
		 * Проверяет заполненность буфера.
		 * 
		 * @return true, если буффер полон.
		 */
		public boolean isEmpty() {
			return count == 0;
		}

		/**
		 * Спускаем буффер. Обнуляем его после.
		 */
		public void flush() {
			newSymbol(buffer);
			count = 0;
			buffer = 0;
		}
	}

	/**
	 * Вершина. Часть списка. Часть дерева.
	 */
	private static class Vertex {

		// List
		// Указатель на следующий элемент списка.
		private Vertex next;

		// Children
		// Сыновья данной вершины.
		private Vertex child0;
		private Vertex child1;

		// Parameters
		// Символ
		private char symbol;
		// Количество этого символа в тексте
		private int q;

		private Vertex(char symbol, int q) {
			super();
			this.symbol = symbol;
			this.q = q;
		}

		/**
		 * Обход в глубину дерева для построения таблицы шифрования.
		 * 
		 * @param deep
		 *            Уровень дерева, на котором находится данная вершина.
		 * @param line
		 *            Массив из нулей и единиц.
		 * @param table
		 *            Таблица, куда складываются полученные значения.
		 */
		public void dfs(int deep, boolean[] line, TreeMap<Character, boolean[]> table) {
			// Если мы нашли лист, то выводим шифр из него.
			if (child0 == null || child1 == null) {
				boolean[] copy = new boolean[deep];
				for (int i = 0; i < deep; i++) {
					copy[i] = line[i];
				}
				table.put(symbol, copy);
			} else {
				// Иначе продолжаем обход в глубину.
				line[deep] = true;
				child0.dfs(deep + 1, line, table);
				line[deep] = false;
				child1.dfs(deep + 1, line, table);
			}
		}
	}

}
