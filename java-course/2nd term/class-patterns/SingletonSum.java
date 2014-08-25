public class SingletonSum {
	public static void main(String[] args) {
		int sum = 0;
		
		for (int i = 0; i < args.length; i++) {
			String[] spl = args[i].split(" ");
			
			for (int j = 0; j < spl.length; j++) {
				if (!spl[j].isEmpty()) {
					sum += Integer.parseInt(spl[j]);
					ArrayQueueSingleton.push(spl[j]);
				}
			}
		}
		
		System.out.println(ArrayQueueSingleton.size());
		
		while (!ArrayQueueSingleton.isEmpty()) {
			System.out.println(ArrayQueueSingleton.peek() + " " + ArrayQueueSingleton.pop());
		}
		
		System.out.println(sum);
	}
}