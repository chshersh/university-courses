
public class ClassModeFunctionException extends RuntimeException {
	private String msg;
	
	public ClassModeFunctionException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public String getMessage() {
		return "Wrong type identifier: " + msg;
	}
}
