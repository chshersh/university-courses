package ru.ifmo.ctddev.kovanikov.task3;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class LinkedBag<E> extends AbstractCollection<E> {
	private long keys, size, version;
	private Map<E, Set<Long>> container;
	private Map<Long, E> elements;

	public LinkedBag() {
		container = new HashMap<E, Set<Long>>();
		elements = new LinkedHashMap<Long, E>();
	}

	@Override
	public boolean add(E e) {
		if (!container.containsKey(e)) {
			container.put(e, new HashSet<Long>());
		}

		container.get(e).add(keys);
		elements.put(keys++, e);

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
		return container.containsKey(obj);
	}

	@Override
	public boolean remove(Object obj) {
		if (!contains(obj)) {
			return false;
		}

		long curKey = container.get(obj).iterator().next();
		container.remove(curKey);
		elements.remove(curKey);
		if (container.get(obj).isEmpty()) {
			container.remove(obj);
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
		return elements.size();
	}

	private class LinkedBagIterator implements Iterator<E> {
		private long index, expectedVersion;
		private Iterator<Map.Entry<Long, E>> it;
		private E lastReturnedElement;
		private long lastReturnedKey;
		private boolean nextCalled;

		public LinkedBagIterator() {
			index = 0;
			expectedVersion = version;
			it = elements.entrySet().iterator();
		}

		private void checkConcurrent() {
			if (expectedVersion != version) {
				throw new ConcurrentModificationException();
			}
		}

		public E next() {
			checkConcurrent();
			index++;

			Map.Entry<Long, E> entry = it.next();
			lastReturnedElement = entry.getValue();
			lastReturnedKey = entry.getKey();

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

			it.remove();
			container.get(lastReturnedElement).remove(lastReturnedKey);
			if (container.get(lastReturnedElement).isEmpty()) {
				container.remove(lastReturnedElement);
			}

			size--;
			index--;
			nextCalled = false;
		}
	}

	public Iterator<E> iterator() {
		return new LinkedBagIterator();
	}
}
