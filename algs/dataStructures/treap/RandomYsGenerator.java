/**
 * 
 */
package algs.dataStructures.treap;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author timat
 *
 */
public class RandomYsGenerator {

	private static void swap(int[] arr, int a, int b) {
		int tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;
	}

	public static void randomShufle(int[] arr) {
		Random r = new Random();

		for (int i = 0; i < arr.length; i++) {
			int b = r.nextInt(arr.length - 1) + 1;
			int a = r.nextInt(b);

			swap(arr, a, b);
		}
	}

	public static LinkedList<Integer> generate(int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = i;
		}
		randomShufle(arr);

		LinkedList<Integer> result = new LinkedList<Integer>();
		for (int i = 0; i < size; i++) {
			result.add(arr[i]);
		}
		return result;
	}

}
