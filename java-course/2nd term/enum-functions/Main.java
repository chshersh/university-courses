//package ru.ifmo.ctddev.efimova.task10

import java.util.HashMap;
import java.util.Map;

enum Uno {
	VARIABLE {
		public String get(Map<String, String> val, String arg) {
			return val.get(arg);
		}
	},
	CONST {
		public String get(Map<String, String> val, String arg) {
			return arg;
		}
	};
	
	public abstract String get(Map<String, String> val, String arg);
}

class TypeWrapper implements Function {
	Uno uno;
	String v;
	
	public TypeWrapper(Uno uno, String v) {
		this.uno = uno;
		this.v = v;
	}
	
	public String evaluate(Map<String, String> val, Type t) {
		return uno.get(val, v);
	}
}

enum Bin {
	PLUS {
		public String doEvaluate(String a, String b, Function.Type t) {
			return t.add(a, b);
		}
	},
	MINUS {
		public String doEvaluate(String a, String b, Function.Type t) {
			return t.sub(a, b);
		}
	},
	TIMES {
		public String doEvaluate(String a, String b, Function.Type t) {
			return t.mul(a, b);
		}
	},
	DIVISION {
		public String doEvaluate(String a, String b, Function.Type t) {
			return t.div(a, b);
		}
	},
	MODUL {
		public String doEvaluate(String a, String b, Function.Type t) {
			return t.mod(a, b);
		}
	};
	
	public abstract String doEvaluate(String a, String b, Function.Type t);
}

class BinOp implements Function {
	Bin bin;
	Function left, right;
	
	public BinOp(Function left, Function right, Bin bin) {
		this.left = left;
		this.right = right;
		this.bin = bin;
	}
	
	public String evaluate(Map<String, String> val, Type t) {
		return bin.doEvaluate(left.evaluate(val, t), right.evaluate(val, t), t);
	}
}

public class Main {
	
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Not enough arguments!");
			System.exit(0);
		}
		
		Map<String, String> val = new HashMap<String, String>();
		Function.Type t = Function.Type.create(args[0]);
		val.put("x", args[2]);
		System.out.println(
				new Parser().parseExpression(args[1]).evaluate(val, t)
		);
		
		// 2 * x + 1 for example
		System.out.println(
				new BinOp(
						new BinOp(
								new TypeWrapper(Uno.CONST, "2"),
								new TypeWrapper(Uno.VARIABLE, "x"),
								Bin.TIMES
						),
						new TypeWrapper(Uno.CONST, "1"),
						Bin.PLUS
				).evaluate(val, t)
		);
	}

}