package ru.ifmo.ctddev.kovanikov.task6;

/**
 * Simple class to test 
 * {@link ru.ifmo.ctddev.kovanikov.task6.MatrixMultiplication <tt>MatrixMultiplication class</tt>}.
 * 
 * @author Dmitriy Kovanikov
 * @version 1.0
 */
public class MatrixTest {
	/**
	 * Tests {@link ru.ifmo.ctddev.kovanikov.task6.MatrixMultiplication <tt>MatrixMultiplication class</tt>} on
	 * number of threads from 1 to 16 inclusive and matrix size 1000.<br>
	 * 
	 * @param  args
	 *         Command line arguments. Could be used to define range
	 *         of testing threads' number.
	 */
	public static void main(String[] args) {
		for (int i = 1; i <= 16; ++i) {
			System.out.println("Threads number is " + Integer.toString(i));
			MatrixMultiplication.main(new String[]{"1000", Integer.toString(i)});
		}
	}
}
