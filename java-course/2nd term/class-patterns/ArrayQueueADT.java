//inv: size, head, tail >= 0;
// elements.length = l
public class ArrayQueueADT {
	private int head, tail;
	private Object[] elements = new Object[0];
	
	//pre:
	public static void push(ArrayQueueADT queue, Object e) {
		ArrayQueueADT.ensureCapacity(queue, ArrayQueueADT.size(queue) + 1);
		queue.elements[queue.tail] = e;
		queue.tail = (queue.tail + 1) % queue.elements.length;
	}
	//post: size = size' + 1, tail = tail' + 1 (mod l), elements[tail'] = e
	
	//pre:
	private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
		if (queue.elements.length > capacity) {
			return;
		}
		int oldSize = ArrayQueueADT.size(queue);
		Object[] newElements = new Object[2 * capacity];
		
		if (queue.head <= queue.tail) {
			for (int i = queue.head; i < queue.tail; i++) {
				newElements[i - queue.head] = queue.elements[i];
			}
		} else {
			for (int i = queue.head; i < queue.elements.length; i++) {
				newElements[i - queue.head] = queue.elements[i];
			}
			for (int i = 0; i < queue.tail; ++i) {
				newElements[i + queue.elements.length - queue.head] = queue.elements[i];
			}
		}
		
		queue.head = 0;
		queue.tail = oldSize;
		queue.elements = newElements;
	}
	//post: if (elements.length > capacity) {l = l', head = head', tail = tail'}
	//		else {l = 2 * capacity, head = 0, tail = l'}
	
	//pre: size > 0
	public static Object pop(ArrayQueueADT queue) {
		assert ArrayQueueADT.size(queue) > 0;
		Object r = queue.elements[queue.head];
		queue.head = (queue.head + 1) % queue.elements.length;
		return r;
	}
	//post: size = size' - 1, head = head' + 1 (l), res = elements[head']
	
	//pre: size > 0
	public static Object peek(ArrayQueueADT queue) {
		assert ArrayQueueADT.size(queue) > 0;
		return queue.elements[queue.head];
	}
	//post: res = elements[head]
	
	//pre:
	public static boolean isEmpty(ArrayQueueADT queue) {
		return queue.tail == queue.head;
	}
	//post: res = (size == 0)
	
	//pre:
	public static int size(ArrayQueueADT queue) {
		if (queue.head <= queue.tail) {
			return (queue.tail - queue.head);
		} else {
			return (queue.elements.length - queue.head + queue.tail);
		}
	}
	//post: res = size
}