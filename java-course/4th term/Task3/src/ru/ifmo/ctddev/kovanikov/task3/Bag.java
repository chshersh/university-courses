package ru.ifmo.ctddev.kovanikov.task3;

import java.util.List;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Bag<E> extends AbstractCollection<E> {
	private long size, version;
	private Map<E, List<E>> elements;

	public Bag() {
		elements = new HashMap<E, List<E>>();
	}

	@Override
	public boolean add(E e) {
		if (!elements.containsKey(e)) {
			elements.put(e, new ArrayList<E>());
		}
		elements.get(e).add(e);
		
		size++;
		version++;
		return true;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c == this) {
			return super.addAll(new LinkedList<E>(c));
		}
		return super.addAll(c);
	}

	@Override
	public boolean contains(Object obj) {
		return elements.containsKey(obj);
	}
	
	@Override
	public boolean remove(Object obj) {
		if (!elements.containsKey(obj)) {
			return false;
		}

		int lastIndex = elements.get(obj).size() - 1;
		elements.get(obj).remove(lastIndex);

		if (elements.get(obj).isEmpty()) {
			elements.remove(obj);
		}

		size--;
		version++;
		return true;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		Object[] a = c.toArray();
		boolean modified = false;
		for (Object obj : a) {
			modified |= this.remove(obj);
		}
		return modified;
	}

	public int size() {
		return (int) Math.min((long) Integer.MAX_VALUE, size);
	}

	private class BagIterator implements Iterator<E> {
		private long index, expectedVersion;
		private Iterator<List<E>> it;
		private int arrListIndex; // index of element in current ArrayList
		private E lastReturnedElement; // need to remove from HashMap
		private List<E> lastReturnedList;
		private boolean nextCalled;

		public BagIterator() {
			index = 0;
			expectedVersion = version;
			it = elements.values().iterator();
		}

		private void checkConcurrent() {
			if (expectedVersion != version) {
				throw new ConcurrentModificationException();
			}
		}

		public E next() {
			checkConcurrent();
			index++;

			if (lastReturnedList == null
					|| arrListIndex >= lastReturnedList.size()) {
				lastReturnedList = it.next();
				arrListIndex = 0;
			}

			lastReturnedElement = lastReturnedList.get(arrListIndex++);
			nextCalled = true;
			return lastReturnedElement;
		}

		public boolean hasNext() {
			checkConcurrent();
			return index < size;
		}

		public void remove() {
			if (!nextCalled) {
				throw new IllegalStateException();
			}
			checkConcurrent();

			// swap
			int lastIndex = lastReturnedList.size() - 1;
			lastReturnedList.set(--arrListIndex,
					lastReturnedList.get(lastIndex));
			lastReturnedList.remove(lastIndex);

			if (lastReturnedList.isEmpty()) {
				it.remove();
			}

			size--;
			index--;
			nextCalled = false;
		}
	}

	public Iterator<E> iterator() {
		return new BagIterator();
	}
}
