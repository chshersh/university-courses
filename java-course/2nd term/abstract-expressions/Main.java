import java.util.HashMap;
import java.util.Map;


public class Main {

	public static void main(String[] args) {
		// "((x - 1) - (x + 2)) * (x - 3) - 1" 5
		// "-x + 3 - x * -x * x + x % 5" 1
		// "x + 0 - 0 - x * 1 + 0 / x - (0 % x + 1 * x) + 2 - 0 * x" 1
		String inputExpression = args[0];
		int argument = Integer.parseInt(args[1]);
		
		Map<String, Integer> val = new HashMap<String, Integer>();
		val.put("x", argument);
		
		System.out.println(inputExpression);
		String parsedStr = new Parser().parseExpression(inputExpression).toString();
		System.out.println(parsedStr);
		System.out.println(new Parser().parseExpression(parsedStr).simplify().toString());
		System.out.println(new Parser().parseExpression(parsedStr).simplify().simplify().toString());
		
		/*
		String toStr = new Plus(
				new Times(
						new Times(
								new Variable("x"),
								new Minus(
										new Variable("x"),
										new Const(1)
								)
						),
						new Variable("x")
				),
				new Const(3)
		).toString();
		
		System.out.println(argument);
		System.out.println(toStr);
		System.out.println(new Parser().parseExpression(toStr).evaluate(val));
		*/
	}

}
