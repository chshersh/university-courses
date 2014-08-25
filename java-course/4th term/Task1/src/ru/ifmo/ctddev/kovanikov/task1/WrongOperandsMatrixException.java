package ru.ifmo.ctddev.kovanikov.task1;

public class WrongOperandsMatrixException extends MatrixException {
	private final Matrix arg;

	public WrongOperandsMatrixException(Matrix arg) {
		this.arg = arg;
	}

	public String getMessage() {
		return "WrongOperandsMatrixException: Incorrect matrix size "
				+ Integer.toString(arg.getSizeRow()) + " " + Integer.toString(arg.getSizeColumn());
	}
}
