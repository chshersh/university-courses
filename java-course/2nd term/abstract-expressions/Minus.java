
public class Minus extends BinaryOperations {
	public Minus(Functions left, Functions right) {
		super(left, right);
		pr = Prior.MID;
	}
	
	protected int doEvaluate(int left, int right) {
		return left - right;
	}
	
	protected String catenate(Functions l, Functions r) {
		return left.toString() + " - " + wrap(right);
	}
	
	protected Functions mathReduction(Functions l, Functions r) {
		if (r.isConst()) {
			return checkRules(r, new int[]{ 0 }, new Functions[]{ l });
		}
		return checkRules(l, new int[]{ 0 }, new Functions[]{ new Times(new Const(-1), r) });
	}
}
