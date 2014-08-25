package ru.ifmo.ctddev.kovanikov.task1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		if (args.length < 2) {
			System.out.println("Not enough arguments: " + args.length);
			System.exit(0);
		}
		
		File fin = new File(args[0]), fout = new File(args[1]);
		
		BufferedReader br = new BufferedReader(new FileReader(fin));
		try {
			
			Matrix a = new Matrix(br);
			Matrix b = new Matrix(br);
			Matrix c = new Matrix(br);
			a.multiply(a).add(b.multiply(c)).write(fout);
		} finally {
			br.close();
			
		}
	}

}
