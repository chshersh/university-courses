package ru.ifmo.ctddev.kovanikov.task1;

public class InputFileMatrixException extends MatrixException {
	public InputFileMatrixException() {
		super();
	}
	
	public InputFileMatrixException(Throwable cause) {
		super(cause);
	}
	
	public String getMessage() {
		return "InputFileMatrixException";
	}
}
