/**
 * 
 */
package algs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author timat
 *
 */
public class FastIO {

	private static StringTokenizer tokenizer;
	private static BufferedReader in;
	private static PrintWriter writer;

	public static void init() {
		in = new BufferedReader(new InputStreamReader(System.in));
		writer = new PrintWriter(System.out);
	}

	public static void initf() throws FileNotFoundException {
		initf("in.txt", "out.txt");
	}

	public static void initf(String in_name, String out_name) throws FileNotFoundException {
		File in_file = new File(in_name);
		in = new BufferedReader(in_file.exists() ? new FileReader(in_file) : new InputStreamReader(System.in));
		File out_file = new File(out_name);
		writer = new PrintWriter(out_file);
	}

	public static void cleanup() throws IOException {
		in.close();
		writer.close();
	}

	public static String readToken() throws IOException {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			tokenizer = new StringTokenizer(in.readLine());
		}
		return tokenizer.nextToken();
	}

	public static int readInt() throws NumberFormatException, IOException {
		return Integer.parseInt(readToken());
	}

}
