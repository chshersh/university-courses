import java.util.Map;

public class Variable implements Functions {
	public String name;
	private Prior pr;
	
	public Variable(String name) {
		this.name = name;
		pr = Prior.LOW;
	}
	
	public Prior getPrior() {
		return pr;
	}
	
	public int evaluate(Map<String, Integer> val) {
		return val.get(this.name);
	}
	
	public String toString() {
		return this.name;
	}
	
	public Functions simplify() {
		return this;
	}
	
	public boolean isConst() {
		return false;
	}
}
