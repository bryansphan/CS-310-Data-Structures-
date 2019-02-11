/*
 * Program #2
 * Bryan Phan
 * cssc0931
 */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinearList<E extends Comparable<E>> implements LinearListADT<E> {
	int currentSize, modCounter;
	private Node<E> head;
	private Node<E> tail;
	
	public LinearList() {
		currentSize = 0;
		head = tail = null;
	}
	private class Node<E> {
		private E data;
		private Node<E> next;
		private Node<E> prev;
		
		private Node(E obj) {
			data = obj;
			next = null;
			prev = null;
		}
	}
	public boolean addFirst(E obj) {
		Node<E> newNode = new Node<>(obj);
		if (head == null)
			head = tail = newNode;
		else{
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		currentSize++;
		modCounter++;
		return true;
	}
	public boolean addLast(E obj) {
		Node<E> newNode = new Node<>(obj);
		if (head == null)
			head = tail = newNode;
		else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		currentSize++;
		modCounter++;
		return true;
	}
	public E removeFirst() {
		if (head == null) return null;
		E tmp = head.data;
		head = head.next;
		if (head == null)
			tail = null;
		else
			head.prev = null;
		currentSize--;
		modCounter++;
		return tmp;
	}
	public E removeLast() {
		if (head == null) return null;
		E tmp = tail.data;
		tail = tail.prev;
		if(tail == null)
			head = null;
		else 
			tail.next = null;
		currentSize--;
		modCounter++;
		return tmp;	
	}
	public E remove (E obj) {
		Node<E> current = head;
		while (current != null && obj.compareTo(current.data) != 0)
			current = current.next;
		if (current == null) return null;
		if (current == head)
			return removeFirst();
		if (current == tail) 
			return removeLast();
		current.prev.next = current.next;
		current.next.prev = current.prev;
		currentSize--;
		modCounter++;
		return current.data;
	}
	public E peekFirst() {
		if (currentSize == 0) return null;
		return head.data;
	}
	public E peekLast() {
		if (currentSize == 0) return null;
		return tail.data;
	}
	public boolean contains(E obj) {
		return find(obj) != null;
	}
	public E find(E obj) {
		for (E tmp: this)
			if(((Comparable<E>)obj).compareTo(tmp) == 0)
				return tmp;
		return null;
	}
	public void clear() {
		currentSize = 0;
		head = tail = null;
	}
	public boolean isEmpty() {
		return currentSize == 0;
	}
	public boolean isFull() {
		return false; 
	}
	public int size() {
		return this.currentSize;
	}
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
		class IteratorHelper implements Iterator<E>{
			private Node<E> itrPtr;
			private long modCheck; 
			
			public IteratorHelper() {
				modCheck = modCounter;
				itrPtr = head;
			}
			public boolean hasNext() {
				if(modCheck != modCounter)
					throw new ConcurrentModificationException();
				return itrPtr != null;
			}
			public E next() {
				if (!hasNext())
					throw new NoSuchElementException();
				E tmp = itrPtr.data;
				itrPtr = itrPtr.next;
				return tmp;
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}
}
