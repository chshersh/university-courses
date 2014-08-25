import java.math.BigInteger;
import java.util.Map;

public interface Function {
	static enum Type {
		INTEGER {
			public String add(String a, String b) {
				Integer l = Integer.parseInt(a),
						r = Integer.parseInt(b);
				return Integer.toString(l + r);
			}
			public String sub(String a, String b) {
				Integer l = Integer.parseInt(a),
						r = Integer.parseInt(b);
				return Integer.toString(l - r);
			}
			public String mul(String a, String b) {
				Integer l = Integer.parseInt(a),
						r = Integer.parseInt(b);
				return Integer.toString(l * r);
			}
			public String mod(String a, String b) {
				Integer l = Integer.parseInt(a),
						r = Integer.parseInt(b);
				return Integer.toString(l % r);
			}
			public String div(String a, String b) {
				Integer l = Integer.parseInt(a),
						r = Integer.parseInt(b);
				return Integer.toString(l / r);
			}
		},
		DOUBLE {
			public String add(String a, String b) {
				Double l = Double.parseDouble(a),
						r = Double.parseDouble(b);
				return Double.toString(l + r);
			}
			public String sub(String a, String b) {
				Double l = Double.parseDouble(a),
						r = Double.parseDouble(b);
				return Double.toString(l - r);
			}
			public String mul(String a, String b) {
				Double l = Double.parseDouble(a),
						r = Double.parseDouble(b);
				return Double.toString(l * r);
			}
			public String mod(String a, String b) {
				Double l = Double.parseDouble(a),
						r = Double.parseDouble(b);
				return Double.toString(l % r);
			}
			public String div(String a, String b) {
				Double l = Double.parseDouble(a),
						r = Double.parseDouble(b);
				return Double.toString(l / r);
			}
		},
		BIGINTEGER {
			public String add(String a, String b) {
				BigInteger l = new BigInteger(a),
						r = new BigInteger(b);
				return l.add(r).toString();
			}
			public String sub(String a, String b) {
				BigInteger l = new BigInteger(a),
						r = new BigInteger(b);
				return l.subtract(r).toString();
			}
			public String mod(String a, String b) {
				BigInteger l = new BigInteger(a),
						r = new BigInteger(b);
				return l.mod(r).toString();
			}
			public String mul(String a, String b) {
				BigInteger l = new BigInteger(a),
						r = new BigInteger(b);
				return l.multiply(r).toString();
			}
			public String div(String a, String b) {
				BigInteger l = new BigInteger(a),
						r = new BigInteger(b);
				return l.divide(r).toString();
			}
		};
		
		public static Type create(String mode) {
			/*switch (mode) {
				case "-i": return INTEGER;
				case "-d": return DOUBLE;
				case "-b": return BIGINTEGER;
			}*/
			if (mode.equals("-i")) {
				return INTEGER;
			} else if (mode.equals("-d")) {
				return DOUBLE;
			} else if (mode.equals("-b")) {
				return BIGINTEGER;
			}
			throw new ClassModeFunctionException(mode);
		}
		
		public abstract String add(String a, String b);
		public abstract String sub(String a, String b);
		public abstract String div(String a, String b);
		public abstract String mul(String a, String b);
		public abstract String mod(String a, String b);
	}
	
	String  evaluate(Map<String, String> val, Type t);
}