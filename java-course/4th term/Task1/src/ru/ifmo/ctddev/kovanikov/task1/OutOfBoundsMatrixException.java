package ru.ifmo.ctddev.kovanikov.task1;

public class OutOfBoundsMatrixException extends MatrixException {
	private final int indexRow, indexColumn;

	public OutOfBoundsMatrixException(int indexRow, int indexColumn) {
		this.indexRow = indexRow;
		this.indexColumn = indexColumn;
	}

	public String getMessage() {
		return "OutOfBoundsMatrixException: " + Integer.toString(indexRow) + " "
				+ Integer.toString(indexColumn);
	}
}
