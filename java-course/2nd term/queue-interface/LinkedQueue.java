public class LinkedQueue implements Queue, Copiable {
	private int size;
	private Node head, tail;

	private class Node {
		public Object value;
		public Node next;

		public Node(Object value, Node next) {
			this.value = value;
			this.next = next;
		}
	}
	
	//pre:
	public LinkedQueue() {
		tail = new Node(null, head);
		head = new Node(null, tail);
	}
	//post: tail.next = head

	public void push(Object e) {
		assert e != null;
		
		tail.value = e;
		tail.next = new Node(null, head);
		tail = tail.next;
		size++;
	}
	//post: tail'.value = e, tail'.next = tail

	public Object pop() {
		assert size > 0;
		
		size--;
		Object result = head.next.value;
		head.next = head.next.next;
		return result;
	}
	//post: result = head.next.value, head.next = head.next.next

	public Object peek() {
		assert size > 0;
		
		return head.next.value;
	}
	//post: result = head.next.value
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public LinkedQueue makeCopy() {
		LinkedQueue copy = new LinkedQueue();
		Node falseHead = head.next;
		for (int i = 0; i < size; ++i) {
			copy.push(falseHead.value);
			falseHead = falseHead.next;
		}
		return copy;
	}
}