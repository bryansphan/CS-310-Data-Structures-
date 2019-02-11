/*
 * Program #4
 * Bryan Phan 
 * cssc0931
 */
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.TreeMap;
import java.util.Map;

public class BalancedTree<K extends Comparable<K>,V> implements DictionaryADT<K,V>  {
	private TreeMap<K,V> list;
	
	//RedBlackTree
	public BalancedTree() {
		list = new TreeMap<K,V>();
	}
	public boolean contains(K key) {
		return list.containsKey(key);
	}
	public boolean add(K key,V value) {
		if(list.containsKey(key)) return false;
		list.put(key, value);
		return true;
	}
	public boolean delete(K key) {
		if(list.isEmpty()) return false;
		if(!list.containsKey(key)) return false;
		list.remove(key);
		return true;
	}
	public V getValue(K key) {
		return list.get(key);
	}
	public K getKey(V value) {
		for (Map.Entry<K, V> entry: list.entrySet()) {
			if(((Comparable<V>)value).compareTo(entry.getValue()) == 0)
				return (K) entry.getKey();
		}
		return null;
	}
	public int size() {
		return list.size();
	}
	public boolean isFull() {
		return false;
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
	public void clear() {
		list.clear();
	}
	public Iterator<K> keys() {
		return (Iterator<K>) list.keySet().iterator();
	}
	public Iterator<V> values() {
		return (Iterator<V>) list.values().iterator();
	}
}
