import java.util.Map;

public interface Functions {
	int evaluate(Map<String, Integer> x);
	
	enum Prior { LOW, MID, HIGH };
	Prior getPrior();
	
	Functions simplify();
	boolean isConst();
}
