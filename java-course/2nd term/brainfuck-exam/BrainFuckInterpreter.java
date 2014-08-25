import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BrainFuckInterpreter {

	public static BufferedReader br;
	public static StringTokenizer st;

	public static final int modulo = 256, MAXCELL = 100000, MAXCOMMANDS = 10000;
	public static int p = 0, ip = 0, curMoves = 0;
	public static int[] cells = new int[MAXCELL];
	public static ArrayDeque<Integer> cycles = new ArrayDeque<Integer>();

	public static class NegativePointerException extends Exception {
		public String toString() {
			return "NegativePointerException " + p;
		}
	}
	
	public static class OutOfCommandsException extends Exception {
		public String toString() {
			return "OutOfCommandsException, MAXCOMMANDS == " + MAXCOMMANDS;
		}
	}

	public static void runProgram(String program) throws IOException,
			NegativePointerException, OutOfCommandsException {
		Scanner in = new Scanner(System.in);
		while (ip < program.length()) {
			char c = program.charAt(ip);
			if (c == '+') {
				cells[p] = (cells[p] + 1) % modulo;
			} else if (c == '-') {
				cells[p] = (modulo + cells[p] - 1) % modulo;
			} else if (c == '>') {
				++p;
				if (p >= MAXCELL) {
					throw new OutOfMemoryError();
				}
			} else if (c == '<') {
				--p;
				if (p < 0) {
					throw new NegativePointerException();
				}
			} else if (c == '.') {
				System.out.print((char) cells[p]);
			} else if (c == ',') {
				cells[p] = in.next().charAt(0);
			} else if (c == '[') {
				if (cells[p] == 0) {
					++ip;
					int balance = 1;
					while (ip < program.length()) {
						if (program.charAt(ip) == '[') {
							++balance;
						} else if (program.charAt(ip) == ']') {
							--balance;
							if (balance == 0) {
								break;
							}
						}
						++ip;
					}
					if (ip >= program.length()) {
						System.err
								.println("Syntax error, no appropriate \']\'");
						return;
					}
				} else {
					cycles.push(ip);
				}
			} else if (c == ']') {
				if (cells[p] == 0) {
					cycles.pop();
				} else {
					--ip;
					int balance = -1;
					while (ip >= 0) {
						if (program.charAt(ip) == '[') {
							++balance;
							if (balance == 0) {
								break;
							}
						} else if (program.charAt(ip) == ']') {
							--balance;
						}
						--ip;
					}
					if (ip < 0) {
						System.err
								.println("Syntax error, no appropriate \'[\'");
						return;
					}
				}
			} else {
				System.err.println("Unknown command");
				return;
			}
			++ip;
			++curMoves;
			if (curMoves >= MAXCOMMANDS) {
				throw new OutOfCommandsException();
			}
		}
	}

	public static void main(String[] args) throws IOException,
			NegativePointerException, OutOfCommandsException {

		if (args.length == 0) {
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Write program here: ");
		} else {
			try {
				br = new BufferedReader(new FileReader(args[0]));
			} catch (FileNotFoundException e) {
				System.err.println("No such file");
				System.exit(0);
			}
		}

		st = new StringTokenizer(br.readLine());
		runProgram(st.nextToken());
		br.close();
	}

}
