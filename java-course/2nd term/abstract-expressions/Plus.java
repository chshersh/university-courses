
public class Plus extends BinaryOperations implements Functions {
	public Plus(Functions left, Functions right) {
		super(left, right);
		pr = Prior.MID;
	}
	
	protected int doEvaluate(int left, int right) {
		return left + right;
	}
	
	protected String catenate(Functions l, Functions r) {
		return l.toString() + " + " + r.toString();
	}
	
	protected Functions mathReduction(Functions l, Functions r) {
		if (l.isConst()) {
			return checkRules(l, new int[]{ 0 }, new Functions[]{ r });
		}
		return checkRules(l, new int[]{ 0 }, new Functions[]{ r });
	}
}
