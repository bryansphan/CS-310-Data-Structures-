/*
 * Program#1 
 * Bryan Phan
 * cssc0931
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E> {
	
	private int currentSize, maxSize, front, rear;
	private E[] storage;
	
	@SuppressWarnings("unchecked")
	public ArrayLinearList(int size) {
		maxSize = size;
		currentSize = front = rear = 0;
		storage = (E[]) new Object[maxSize];
	}
	
	public ArrayLinearList() {
		this(DEFAULT_MAX_CAPACITY);
	}
	
	public boolean addFirst (E obj) {
		// Checks all scenarios when addFirst is inserted in the front/middle/end
		if (currentSize == maxSize) return false;
		if (currentSize == 0) storage[front] =obj;
		else if(front == 0) front = maxSize - 1;
		else front--;
		storage[front]=obj;
		currentSize++;
		return true;
	}
	
	public boolean addLast(E obj){
		// Checks all scenarios when addLast is inserted in the front/middle/end
		if(currentSize == maxSize) return false;
		if(currentSize == 0) storage[rear] = obj;
		else if(rear == maxSize - 1) rear = 0;
		else rear++;
		storage[rear]=obj;
		currentSize++;
		return true;
	}
	
	public E removeFirst() {
		if (currentSize == 0) 
			return null;
		currentSize--;
		E temp = storage[front];
		if (front == rear) return temp;
		front++;
		if (front == maxSize) {
			front = 0;
			return storage[maxSize - 1];
		}
		return temp;
	}
	
	public E removeLast() {
		if (currentSize == 0) 
			return null;
		currentSize--;
		E temp = storage[rear];
		if (rear == front) return temp;
		rear--;
		if (rear == -1) {
			rear = maxSize - 1;
			return storage[0];
		}
		return temp;
	}
	
	@SuppressWarnings({ "unchecked"})
	public E remove(E obj) {
		// If list is empty
		if (currentSize == 0) {
			return null;
		}
		// If list is full or not empty 
		if (currentSize != 0) {
			// Finding index
			int index = front; 
			// Do-while loop, based off one given in class 
			for (int i = 0; i < currentSize; i++) {
				if (((Comparable<E>) obj).compareTo(storage[index]) == 0) {
					break;
				}
				index++;
				if (index == maxSize) index = 0;
			}
			// Shifting
			while (index != rear) {
				if (index == maxSize - 1) {
					storage[index] = storage[0];
					index = 0;
				}
				else {
					storage[index] = storage[index + 1];
					index++;
				}
			}
			currentSize--;
			if (rear == 0) rear = maxSize;
			rear--;
			return obj;
		}
		return null;
	}

	public E peekFirst() {
		if(currentSize == 0) return null;
		return storage[front];Size
	}
	
	public E peekLast() {
		if(currentSize == 0) return null;
		return storage[rear];
	}
	
	public boolean contains(E obj) {
		return find(obj) != null;
	}
	
	@SuppressWarnings("unchecked")
	public E find(E obj) {
		int index = front;
		int count = 0;
		while (count != currentSize) {
			if (((Comparable<E>) obj).compareTo(storage[index]) == 0)
				return storage[index];
			index++;
			if (index == maxSize)
				index = 0;
			count++;
		}
		return null;
	}
	
	public void clear() {
		currentSize = front = rear = 0;
	}
	
	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	public boolean isFull() {
		return currentSize == maxSize;
	}
	
	public int size() {
		return this.currentSize;
	}
	
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	class IteratorHelper implements Iterator<E> {
		//Given in class 
		private int index, count;
		
		public IteratorHelper() {
			index = front;
			count = 0;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public boolean hasNext() {
			return count != currentSize;
		}
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			E temp = storage[index++];
			if(index == maxSize) index = 0;
			count++;
			return temp;
		}
	}
}

