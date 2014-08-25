
public class Times extends BinaryOperations implements Functions {
	public Times(Functions left, Functions right) {
		super(left, right);
		pr = Prior.HIGH;
	}
	
	protected int doEvaluate(int left, int right) {
		return left * right;
	}
	
	protected String catenate(Functions l, Functions r) {
		return wrap(l) + " * " + wrap(r);
	}
	
	protected Functions mathReduction(Functions l, Functions r) {
		if (l.isConst()) {
			return checkRules(l, new int[]{ 0, 1 }, new Functions[]{ new Const(0), r });
		}
		return checkRules(r, new int[]{ 0, 1 }, new Functions[]{ new Const(0), l });
	}
}
