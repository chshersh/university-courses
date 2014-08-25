package ru.ifmo.ctddev.kovanikov.task1;

public class MatrixException extends RuntimeException {
	
	public MatrixException() {
		super();
	}
	
	public MatrixException(String message) {
		super(message);
	}
	
	public MatrixException(Throwable cause) {
		super(cause);
	}
	
	public MatrixException(
			String message,
			Throwable cause
	){
		super(message, cause);
	}
}
