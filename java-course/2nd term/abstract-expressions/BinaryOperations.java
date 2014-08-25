import java.util.Map;

public abstract class BinaryOperations implements Functions {
	protected Functions left, right;
	protected Prior pr;
	
	public BinaryOperations(Functions left, Functions right) {
		this.left = left;
		this.right = right;
	}
	
	public int evaluate(Map<String, Integer> x) {
		return doEvaluate(left.evaluate(x), right.evaluate(x));
	}
	
	protected abstract int doEvaluate(int l, int r);
	
	public String toString() {
		/*String l = left.toString(), r = right.toString();
		if (this instanceof Times || this instanceof Division || this instanceof Modul) {
			if (left instanceof Plus || left instanceof Minus) {
				l = "(" + l + ")";
			}
			if (right instanceof Plus || right instanceof Minus) {
				r = "(" + r + ")";
			}
		} else if (this instanceof Minus && right instanceof Plus) {
			r = "(" + r + ")";
		}
		return catenate(l, r);*/
		return catenate(left, right);
	}
	
	public Prior getPrior() {
		return pr;
	}
	
	protected String wrap(Functions f) {
		String s = f.toString();
		if (f.getPrior() == Prior.MID) {
			s = "(" + s + ")";
		}
		return s;
	}
	
	protected abstract String catenate(Functions l, Functions r);
	
	public boolean isConst() {
		return false;
	}
	
	public Functions simplify() {
		left = left.simplify();
		right = right.simplify();
		if (left.isConst() && right.isConst()) {
			return new Const(doEvaluate(left.evaluate(null), right.evaluate(null)));
		}
		return mathReduction(left, right);
	}
	
	protected Functions checkRules(Functions verified, int[] indicate, Functions[] results) {
		if (verified.isConst()) {
			int val = verified.evaluate(null);
			for (int i = 0; i < indicate.length; i++) {
				if (indicate[i] == val) {
					return results[i];
				}
			}
		}
		return this;
	}
	protected abstract Functions mathReduction(Functions l, Functions r);
	
}
