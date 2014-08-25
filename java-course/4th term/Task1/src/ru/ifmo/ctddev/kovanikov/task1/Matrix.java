package ru.ifmo.ctddev.kovanikov.task1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Matrix {

	private double[][] box;
	private int sizeRow, sizeColumn;

	public Matrix(int sizeRow, int sizeColumn) {
		checkThrowSize(sizeRow, sizeColumn);

		this.sizeRow = sizeRow;
		this.sizeColumn = sizeColumn;
		this.box = new double[sizeRow][sizeColumn];
	}

	public Matrix(double[][] mas) {
		// 0 rows or columns
		checkThrowSize(mas.length, (mas.length > 0 && mas[0] != null) ? mas[0].length : 0);
		checkMatrixSize(mas);
		
		this.sizeRow = mas.length;
		this.sizeColumn = mas[0].length;
		this.box = new double[sizeRow][sizeColumn];
		for (int i = 0; i < sizeRow; ++i) {
			for (int j = 0; j < sizeColumn; ++j) {
				this.box[i][j] = mas[i][j];
			}
		}
	}

	private void doInput(BufferedReader br) {
		try {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int sr = Integer.parseInt(st.nextToken()), sc = Integer.parseInt(st.nextToken());
			checkThrowSize(sr, sc);
			if (st.hasMoreElements()) {
				throw new InputFileMatrixException();
			}
			
			this.sizeRow = sr;
			this.sizeColumn = sc;
			this.box = new double[sizeRow][sizeColumn];

			for (int i = 0; i < sr; ++i) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < sc; ++j) {
					this.box[i][j] = Double.parseDouble(st.nextToken());
				}
				if (st.hasMoreElements()) {
					throw new InputFileMatrixException();
				}
			}

		} catch (IOException e) {
			throw new InputFileMatrixException(e);
		} catch (NumberFormatException e) {
			throw new InputFileMatrixException(e);
		} catch (NoSuchElementException e) {
			throw new InputFileMatrixException(e);
		} catch (NullPointerException e) {
			throw new InputFileMatrixException(e);
		}
	}
	
	public Matrix(BufferedReader br) throws IOException {
		doInput(br);
	}
	
	public Matrix(File input) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(input));
		try {
			doInput(br);
		} finally {
			br.close();
		}
	}
	
	private void checkThrowSize(int sr, int sc) {
		if (sr <= 0 || sc <= 0) {
			throw new WrongSizeMatrixException(sr, sc);
		}
	}

	private void checkThrowBound(int i, int j) {
		if (i < 0 || sizeRow <= i || j < 0 || sizeColumn <= j) {
			throw new OutOfBoundsMatrixException(i, j);
		}
	}
	
	private void checkMatrixSize(double[][] m) {
		int rows = m.length, columns = m[0].length;
		for (int i = 0; i < rows; i++) {
			if (m[i] == null || m[i].length != columns) {
				throw new WrongSizeMatrixException(rows, columns);
			}
		}
	}

	public double get(int i, int j) {
		checkThrowBound(i, j);
		return box[i][j];
	}

	public void set(int i, int j, double x) {
		checkThrowBound(i, j);
		box[i][j] = x;
	}

	// wrong operands (size != size')
	public Matrix add(Matrix other) {
		if (sizeRow != other.sizeRow || sizeColumn != other.sizeColumn) {
			throw new WrongOperandsMatrixException(other);
		}

		Matrix C = new Matrix(sizeRow, sizeColumn);

		for (int i = 0; i < sizeRow; ++i) {
			for (int j = 0; j < sizeColumn; ++j) {
				C.box[i][j] = box[i][j] + other.box[i][j];
			}
		}

		return C;
	}

	public Matrix subtract(Matrix other) {
		return this.add(other.scale(-1));
	}

	public Matrix multiply(Matrix other) {
		if (sizeColumn != other.sizeRow) {
			throw new WrongOperandsMatrixException(other);
		}

		double[][] container = new double[sizeRow][other.sizeColumn];
		for (int i = 0; i < sizeRow; ++i) {
			for (int j = 0; j < other.sizeColumn; ++j) {
				for (int k = 0; k < sizeColumn; ++k) {
					container[i][j] += box[i][k] * other.box[k][j];
				}
			}
		}

		return new Matrix(container);
	}

	public Matrix scale(double x) {
		Matrix c = new Matrix(sizeRow, sizeColumn);

		for (int i = 0; i < sizeRow; ++i) {
			for (int j = 0; j < sizeColumn; ++j) {
				c.box[i][j] = box[i][j] * x;
			}
		}

		return c;
	}

	public Matrix transpose() {
		double[][] container = new double[sizeColumn][sizeRow];

		for (int i = 0; i < sizeColumn; ++i) {
			for (int j = 0; j < sizeRow; ++j) {
				container[i][j] = box[j][i];
			}
		}

		return new Matrix(container);
	}

	public void out() {
		for (int i = 0; i < sizeRow; ++i) {
			for (int j = 0; j < sizeColumn; ++j) {
				System.out.print(box[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void write(File out) throws IOException {
		FileWriter fw = new FileWriter(out);
		try {
			
			fw.write(sizeRow + " " + sizeColumn + "\n");
			for (int i = 0; i < sizeRow; ++i) {
				for (int j = 0; j < sizeColumn; ++j) {
					fw.write(box[i][j] + " ");
				}
				fw.write("\n");
			}

		} finally {
			fw.close();
		}
	}

	public int getSizeRow() {
		return sizeRow;
	}

	public int getSizeColumn() {
		return sizeColumn;
	}

}
