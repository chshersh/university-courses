package ru.ifmo.ctddev.kovanikov.task1;

public class WrongSizeMatrixException extends MatrixException {
	private final int sizeRow, sizeColumn;

	public WrongSizeMatrixException(int sizeRow, int sizeColumn) {
		this.sizeRow = sizeRow;
		this.sizeColumn = sizeColumn;
	}

	public String getMessage() {
		return "WrongSizeMatrixException: " + Integer.toString(sizeRow) + " "
				+ Integer.toString(sizeColumn);
	}
}
