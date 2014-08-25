package ru.ifmo.ctddev.kovanikov.examTask;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * The <tt>QueueTask</tt> class realizes Concurrent queue
 * which can add values and pop them.
 * 
 * @author  Dmitriy Kovanikov
 * @version 1.0
 */

public class QueueTask {
	/**
	 * Data container of {@link #QueueTask()}.
	 * 
	 * @see Queue
	 */
	private Queue<Object> queue;
	
	/**
	 * Constructor creates new {@link QueueTask#queue} of <tt>Object</tt> and capacity <i>10</i>.
	 */
	public QueueTask() {
		this.queue = new ArrayDeque<Object>(10);
	}
	
	/**
	 * Add a non-<i>null</i> <tt>Object</tt> in the {@link QueueTask#queue}.<p>
	 * 
	 * Sleeps if size of {@link QueueTask#queue} becomes equal to 10.
	 * 
	 * @param  value as added value
	 * @throws InterruptedException
	 * @see    java.lang.Object#notifyAll()
     * @see    java.lang.Object#wait()
	 */
	public synchronized void add(Object value) throws InterruptedException {
		while (queue.size() >= 10) {
			wait();
		}
		
		if (value != null) {
			queue.add(value);
		}
		notify();
	}
	
	/**
	 * Pops and return value from <i>queue</i>.
	 * 
	 * @return <tt>Object</tt> - value in the head of queue.
	 * @throws InterruptedException
	 * @see    java.lang.Object#notifyAll()
     * @see    java.lang.Object#wait()
	 */
	public synchronized Object get() throws InterruptedException {
		while (queue.isEmpty()) {
			wait();
		}
		
		Object result = queue.poll();
		notify();
		return result;
	}
	
	/**
	 * Size of <i>queue</i>.
	 * 
	 * @return number of elements in {@link #queue}.
	 */
	public int size() {
		return queue.size();
	}
}
