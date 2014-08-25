package ru.ifmo.ctddev.kovanikov.task3;

import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		Bag<Integer> b = new Bag<Integer>();
		LinkedBag<Integer> lb = new LinkedBag<Integer>();

		for (int i = 0; i < 5; i++) {
			b.add(i);
			lb.add(i);
		}
		b.addAll(b);
		lb.addAll(lb);
		System.out.println(b.containsAll(lb));
		System.out.println(lb.containsAll(b));
		b.removeAll(lb);
		b.retainAll(b);
		b.addAll(lb);
		b.retainAll(b);

		for (Iterator<Integer> it = b.iterator(); it.hasNext();) {
			int x = it.next();
			System.out.print(x + " ");
			if (x % 2 == 1) {
				it.remove();
			}
		}
		System.out.println();
		for (Iterator<Integer> it = lb.iterator(); it.hasNext();) {
			int x = it.next();
			System.out.print(x + " ");
			if (x % 2 == 1) {
				it.remove();
			}
		}
		System.out.println();
		System.out.println(b.size());
		System.out.println(lb.size());
		for (Integer e : b) {
			System.out.print(e + " ");
		}
		System.out.println();
		for (Integer e : lb) {
			System.out.print(e + " ");
		}
		System.out.println();
		
		int lbSize = lb.size();
		for (int i = 0; i < lbSize; i++) {
			if (i % 2 == 0) {
				lb.remove(i % 5);
			}
		}
		for (Integer e : lb) {
			System.out.print(e + " ");
		}
		System.out.println();
		b.removeAll(b);
		System.out.println(b.size());
		b.addAll(lb);
		b.addAll(lb);
		b.retainAll(lb);
		System.out.println(b.size());
		for (Integer e : b) {
			System.out.print(e + " ");
		}
		System.out.println();
	}

}
