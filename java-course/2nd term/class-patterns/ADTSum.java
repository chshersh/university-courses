public class ADTSum {
	public static void main(String[] args) {
		int sum = 0;
		ArrayQueueADT queue = new ArrayQueueADT();
		
		for (int i = 0; i < args.length; i++) {
			String[] spl = args[i].split(" ");
			
			for (int j = 0; j < spl.length; j++) {
				if (!spl[j].isEmpty()) {
					sum += Integer.parseInt(spl[j]);
					ArrayQueueADT.push(queue, spl[j]);
				}
			}
		}
		
		System.out.println(ArrayQueueADT.size(queue));
		
		while (!ArrayQueueADT.isEmpty(queue)) {
			System.out.println(ArrayQueueADT.peek(queue) + " " + 
				ArrayQueueADT.pop(queue));
		}
		
		System.out.println(sum);
	}
}