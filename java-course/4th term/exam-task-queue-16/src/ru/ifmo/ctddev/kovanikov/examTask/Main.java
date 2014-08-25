package ru.ifmo.ctddev.kovanikov.examTask;

import java.util.Random;

/**
 * In <tt>Main</tt> class creates two threads -
 * <tt>Producer</tt> and <tt>Consumer</tt> which add and
 * extract values from {@link ru.ifmo.ctddev.kovanikov.examTask.QueueTask}. 
 * 
 * @author  Dmitriy Kovanikov
 * @version 1.0
 */

public class Main {
	/**
	 * Queue of values.
	 * 
	 * @see QueueTask
	 */
	public static QueueTask qt = new QueueTask();
	
	/**
	 * Class that implements <tt>Runnable</tt> interface and 
	 * adds values to queue of tasks.
	 * 
	 * @author Dmitriy Kovanikov
	 * @see    Runnable
	 */
	public static class Producer implements Runnable {
		/**
		 * Implements <tt>Runnable</tt> method <tt>run</t>.
		 * Adds consequent integer values to {@link Main#qt} starting from 1.<p>
		 * 
		 * Sleeps for a random interval of time from 0 to 2000 milliseconds 
		 * after adding.<p>
		 * 
		 * Writing information to console of what element is added and size of <i>qt</t>.
		 * 
		 * @see Random
		 */
		@Override
		public void run() {
			final Random sleepTime = new Random();
			int i = 0;
			try {
				while (true) {
					synchronized (this) {
						qt.add(++i);
						System.out.println("Added value " + i + ". Queue size is " + qt.size());
						wait(sleepTime.nextInt(2000));
					}
				}
			} catch (InterruptedException e) {
				System.err.println("Thread Producer is interrupted!");
			}
		}
	}
	
	/**
	 * Class that implements <tt>Runnable</tt> interface and 
	 * extracts values from queue of tasks.
	 * 
	 * @author Dmitriy Kovanikov
	 * @see    Runnable
	 */
	public static class Consumer implements Runnable {
		/**
		 * What time should <tt>Consumer</tt> wait.
		 */
		private final long waitTime; 
		
		/**
		 * Constructs new Consumer with fixed waiting time.
		 * 
		 * @param waitTime as itself.
		 */
		public Consumer(long waitTime) {
			if (waitTime < 0) {
				throw new IllegalArgumentException();
			}
			
			this.waitTime = waitTime;
		}
		
		/**
		 * Implements <tt>Runnable</tt> method <tt>run</t>.
		 * Extracts elements from {@link Main#qt}.<p>
		 * 
		 * Sleeps for a fixed interval of {@link #waitTime} milliseconds.
		 * after adding.<p>
		 * 
		 * Writing information to console of what element is extracted.
		 */
		@Override
		public void run() {
			try {
				while (true) {
					synchronized (this) {
						final Object value = qt.get();
						System.out.println("Taken value " + value);
						wait(waitTime);
					}
				}
			} catch (InterruptedException e) {
				System.err.println("Thread Consumer is interrupted!");
			}
		}
	}
	
	/**
	 * Creates two threads of <tt>Producer</tt> and <tt>Consumer</tt>
	 * which adds and extracts values from queue continuously.<p>
	 * 
	 * Writes to console about incorrect input if <i>waitingTime</i> is
	 * not a non-negative integer of {@link Long} value. 
	 * 
	 * @param args as command-line arguments. Wait time of <tt>Consumer</tt> class
	 * 		  should be passed here.
	 */
	public static void main(String[] args) {
		try {
			long waitTime = Long.parseLong(args[0]);
			
			Thread producer = new Thread(new Producer()), 
					consumer = new Thread(new Consumer(waitTime));
			
			producer.start();
			consumer.start();
		} catch(NumberFormatException e) {
			System.err.println("Wait time must be non-negative integer: " + args[0]);
		} catch(IllegalArgumentException e) {
			// should be | of exceptions. But only in java 1.7
			System.err.println("Wait time must be non-negative integer: " + args[0]);
		}
	}
}
