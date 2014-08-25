import java.util.Map;

public class Const implements Functions {
	private int c;
	private Prior pr;
	
	public Const(int c) {
		this.c = c;
		pr = Prior.LOW;
	}
	
	public Prior getPrior() {
		return pr;
	}
	
	public int evaluate(Map<String, Integer> x) {
		return c;
	}
	
	public String toString() {
		return Integer.toString(this.c);
	}
	
	public Functions simplify() {
		return this;
	}
	
	public boolean isConst() {
		return true;
	}
}
