public class QueueTest {
	public static void fill(Queue q) {
		for (int i = 0; i < 5; ++i) {
			q.push(i);
		}
	}
	
	public static void dump(Queue q) {
		while (!q.isEmpty()) {
			System.out.println(q.size() + " " + 
				q.peek() + " " + q.pop());
		}
	}
	
	public static void test(Queue q) {
		fill(q);
		Queue copy = q.makeCopy();
		
		dump(q);
		System.out.println();
		
		dump(copy);
		System.out.println("=====");
	}
	
	public static void main(String[] args) {
		test(new ArrayQueue());
		test(new LinkedQueue());
	}
}