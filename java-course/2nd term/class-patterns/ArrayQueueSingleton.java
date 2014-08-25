//inv: size, head, tail >= 0;
// elements.length = l
public class ArrayQueueSingleton {
	private static int head, tail;
	private static Object[] elements = new Object[0];
	
	//pre:
	public static void push(Object e) {
		ensureCapacity(size() + 1);
		elements[tail] = e;
		tail = (tail + 1) % elements.length;
	}
	//post: size = size' + 1, tail = tail' + 1 (mod l), elements[tail'] = e
	
	//pre:
	private static void ensureCapacity(int capacity) {
		if (elements.length > capacity) {
			return;
		}
		int oldSize = size();
		Object[] newElements = new Object[2 * capacity];
		
		if (head <= tail) {
			for (int i = head; i < tail; i++) {
				newElements[i - head] = elements[i];
			}
		} else {
			for (int i = head; i < elements.length; i++) {
				newElements[i - head] = elements[i];
			}
			for (int i = 0; i < tail; ++i) {
				newElements[i + elements.length - head] = elements[i];
			}
		}
		
		head = 0;
		tail = oldSize;
		elements = newElements;
	}
	//post: if (elements.length > capacity) {l = l', head = head', tail = tail'}
	//		else {l = 2 * capacity, head = 0, tail = l'}
	
	//pre: size > 0
	public static Object pop() {
		assert size() > 0;
		Object r = elements[head];
		head = (head + 1) % elements.length;
		return r;
	}
	//post: size = size' - 1, head = head' + 1 (l), res = elements[head']
	
	//pre: size > 0
	public static Object peek() {
		assert size() > 0;
		return elements[head];
	}
	//post: res = elements[head]
	
	//pre:
	public static boolean isEmpty() {
		return tail == head;
	}
	//post: res = (size == 0)
	
	//pre:
	public static int size() {
		if (head <= tail) {
			return (tail - head);
		} else {
			return (elements.length - head + tail);
		}
	}
	//post: res = size
}