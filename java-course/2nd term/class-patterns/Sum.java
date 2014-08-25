public class Sum {
	public static void main(String[] args) {
		int sum = 0;
		Queue queue = new ArrayQueue();
		
		for (int i = 0; i < args.length; i++) {
			String[] spl = args[i].split(" ");
			
			for (int j = 0; j < spl.length; j++) {
				if (!spl[j].isEmpty()) {
					sum += Integer.parseInt(spl[j]);
					queue.push(spl[j]);
				}
			}
		}
		
		System.out.println(queue.size());
		
		while (!queue.isEmpty()) {
			System.out.println(queue.peek() + " " + queue.pop());
		}
		
		System.out.println(sum);
	}
}