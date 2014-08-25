//inv: size, head, tail >= 0;
// elements.length = l
public class ArrayQueue {
	private int head, tail;
	private Object[] elements;
	
	//pre:
	public ArrayQueue(int n) {
		elements = new Object[n];
	}
	//post: l = n
	
	//pre:
	public ArrayQueue() {
		this(0);
	}
	//post: l = 0
	
	//pre:
	public void push(Object e) {
		ensureCapacity(size() + 1);
		elements[tail] = e;
		tail = (tail + 1) % elements.length;
	}
	//post: size = size' + 1, tail = tail' + 1 (mod l), elements[tail'] = e
	
	//pre:
	private void ensureCapacity(int capacity) {
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
	public Object pop() {
		assert size() > 0;
		Object r = elements[head];
		head = (head + 1) % elements.length;
		return r;
	}
	//post: size = size' - 1, head = head' + 1 (mod l), res = elements[head']
	
	//pre: size > 0
	public Object peek() {
		assert size() > 0;
		return elements[head];
	}
	//post: res = elements[head]
	
	//pre:
	public boolean isEmpty() {
		return tail == head;
	}
	//post: res = (size == 0)
	
	//pre:
	public int size() {
		if (head <= tail) {
			return (tail - head);
		} else {
			return (elements.length - head + tail);
		}
	}
	//post: res = size
}