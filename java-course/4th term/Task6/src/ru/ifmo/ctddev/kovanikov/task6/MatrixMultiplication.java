package ru.ifmo.ctddev.kovanikov.task6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The <tt>MatrixMultiplication</tt> class realizes
 * matrix multiplication in several threads.<p>
 * 
 * Gets matrix size <i>n</i> and number of threads <i>m</i> from
 * command-line arguments, creates two matrices with size <i>n * n</i>,
 * multiplies them using {@code Math.min(n * n, m)} threads, sets result 
 * into third matrix and writes elapsed time and sum of resulting matrix 
 * into standard output.
 * 
 * @author Dmitriy Kovanikov
 * @version 1.0
 */

public class MatrixMultiplication {
	
	/**
	 * Number of rows and also number of columns in matrices.
	 */
	private int size;
	
	/**
	 * Number of threads using to multiply matrices.
	 */
	private int threadsNumber;
	
	/**
	 * Left matrix of multiplication.
	 */
	private int[][] a;
	
	/**
	 * Right matrix of multiplication.
	 */
	private int[][] b;
	
	/**
	 * Constructs class containing two random matrices with {@link #randomMatrixGen()}
	 * which will be multiplied with number of columns and rows equal to {@link #size} and
	 * sets the number of using threads.
	 * 
	 * @param size {@link #size}
	 * @param threadsNumber {@link #threadsNumber}
	 */
	public MatrixMultiplication(int size, int threadsNumber) {
		this.size = size;
		this.threadsNumber = threadsNumber;
		this.a = randomMatrixGen();
		this.b = randomMatrixGen();
	}
	
	/**
	 * Generates random matrix with size {@link #size} * {@link #size}.
	 * 
	 * @return integer containing matrix with <tt>0-1</tt> values.
	 */
	public int[][] randomMatrixGen() {
		Random rnd = new Random();
		int[][] a = new int[size][size];
		
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				a[i][j] = rnd.nextInt(2);
			}
		}
		
		return a;
	}
	
	/**
	 * Auxiliary class that implements <tt>Runnable</tt> interface
	 * and calculates some matrix elements in the individual thread.
	 *
	 * @author Dmitriy Kovanikov
	 * @see Runnable
	 */
	public class MultiplyThread implements Runnable {
		/**
		 * We assume that matrix elements can be numbered in <tt>0..n * n - 1</tt> segment
		 * from left to right and from above to down.<p>
		 * 
		 * The start of segment of elements which current thread is calculating.
		 */
		private int start;
		
		/**
		 * The end of segment elements.
		 */
		private int end;
		
		/**
		 * Matrix in which we writes result.
		 */
		private int[][] c;
		
		/**
		 * Constructs separate <tt>Runnable</tt> object with segment of
		 * calculated elements and resulting matrix.
		 * 
		 * @param start {@link #start}
		 * @param end {@link #end}
		 * @param c {@link #c}
		 */
		public MultiplyThread(int start, int end, int[][] c) {
			this.start = start;
			this.end = end;
			this.c = c;
		}
		
		/**
		 * Implements <tt>Runnable</tt> method <tt>run</tt>.
		 * Actually calculates elements of matrix {@link #c} from
		 * {@link #start} to {@link #end} excluding.
		 */
		public void run() {
			for (int i = start; i < end; ++i) {
				int row = i / size, column = i % size;
				for (int j = 0; j < size; ++j) {
					c[row][column] += a[row][j] * b[j][column];
				}
			}
		}
	}
	
	/**
	 * Multiplies two randomly generated matrices <i>a</i> and <i>b</i>
	 * using <i>threadsNumber</i> threads.
	 * 
	 * @return matrix - result of multiplication {@link MatrixMultiplication#a} and {@link MatrixMultiplication#b}
	 * @throws InterruptedException if on of threads is interrupted
	 */
	public int[][] matrixMul() throws InterruptedException {
		int size = this.a.length, 
				numElements = size * size, // number of elements in thread
				blockSize = Math.max(1, numElements / threadsNumber); //number of elements, calculated by one thread
		int[][] c = new int[size][size]; // resulting matrix
		List<Thread> threads = new ArrayList<Thread>(threadsNumber);
		
		for (int i = 0; i < Math.min(numElements, threadsNumber - 1); ++i) {
			threads.add(new Thread(new MultiplyThread(i * blockSize, (i + 1) * blockSize, c)));
			threads.get(threads.size() - 1).start();
		}
		if (threadsNumber <= numElements) {
			// adding remaining elements to the last thread
			threads.add(new Thread(new MultiplyThread(blockSize * (threadsNumber - 1), numElements, c)));
			threads.get(threads.size() - 1).start();
		}
		
		// waiting for completing all threads
		for (Thread thread : threads) {
			thread.join();
		}
		
		return c;
	}
	
	/**
	 * Reads size of matrix and number of threads from <code>args</code>,
	 * creates an instance of <tt>MatrixMultiplication</tt> class.
	 * Multiply two matrices using {@link #threadsNumber} threads and writing
	 * the time of computation and sum of elements of resulting matrix.<p>
	 * 
	 * Writing error messages to standard output stream if arguments are invalid.
	 * 
	 * @param args as command-line arguments
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Not enough arguments.");
			System.exit(0);
		}
		
		try {
			int n = Integer.parseInt(args[0]), m = Integer.parseInt(args[1]);
			
			if (n <= 0 || m <= 0) {
				throw new NumberFormatException();
			}
			
			MatrixMultiplication multiplicator = new MatrixMultiplication(n, m);
			
			long startTime = System.nanoTime();
			
			int[][] matrix = multiplicator.matrixMul();
			long sum = 0;
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					sum += matrix[i][j];
				}
			}
			
			long endTime = System.nanoTime() - startTime;
			
			System.out.println("Calculating took " + endTime / 1000000000.0 + " seconds");
			System.out.println("Sum of elements is " + sum);
		} catch (NumberFormatException e) {
			System.err.println("Matrix size and number of threads must be positive integers: " + args[0] + " " + args[1]);
		} catch (InterruptedException e) {
			System.err.println("Thread is interrupted");
		}
	}

}
