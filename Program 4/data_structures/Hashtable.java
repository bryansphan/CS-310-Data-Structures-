/*
 * Program #4 
 * Bryan Phan
 * cssc0931
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class Hashtable <K extends Comparable<K>, V> implements DictionaryADT<K,V> {
	
	class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>>{
		K key;
		V value;
		
		DictionaryNode(K key, V value){
			this.key = key;
			this.value = value;
		}
		public int compareTo(DictionaryNode<K, V> node){
			return((Comparable<K>)key).compareTo((K)node.key);
		}
	}

	private long modCounter;
	private int currentSize, maxSize, tableSize;
	private LinearList<DictionaryNode<K,V>>[] list;

	public Hashtable(int size){
		maxSize = size;
		modCounter = 0;
		currentSize = 0;
		tableSize = (int) (maxSize*1.3f);
		list = new LinearList[tableSize];
		for(int i = 0; i < tableSize; i++)
			list[i] = new LinearList<DictionaryNode<K,V>>();
	}
	
	public boolean contains(K key){
		return list[getIndex(key)].contains(new DictionaryNode<K,V>(key,null));
	}

	private int getIndex(K key){
		return (key.hashCode() & 0x7FFFFFF) % tableSize;
	}
	
	public boolean add(K key,V value) {
		if(contains(key)) return false;
		DictionaryNode tmp = new DictionaryNode<K,V>(key,value);
		list[getIndex(key)].addLast(tmp);
		modCounter++;
		currentSize++;
		return true;
	}
	
	public boolean delete(K key) { 
		if(contains(key)){
			list[getIndex(key)].remove(new DictionaryNode<K,V>(key,null));
			currentSize--;
			modCounter++;
			return true;
		}
		return false; 			
	}

	public V getValue(K key) {
		DictionaryNode<K,V> tmp = list[getIndex(key)].find(new DictionaryNode<K,V>(key,null));
		if(tmp == null) return null;
		return tmp.value; 
	}

	public K getKey(V value) {
		for(int i = 0; i<tableSize; i++) {
			for(DictionaryNode<K,V> n: list[i]) {
				if(((Comparable<V>) value).compareTo(n.value) == 0)
					return n.key;
			}
		}
		return null;
	}
	
	public int size() {
		return currentSize;
	}
	
	public boolean isFull() {
		return currentSize == maxSize;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public void clear() {
		currentSize = 0;
		modCounter = 0;
		for(int i =0; i<tableSize; i++)
			list[i].clear();
	}

	public Iterator<K> keys() 
    {
        return (Iterator<K>) new KeyIteratorHelper<K>();
    }

    public Iterator<V> values() 
    {
        return (Iterator<V>) new ValueIteratorHelper<V>();
    }

    abstract class IteratorHelper<E> implements Iterator<E> 
    {
        protected DictionaryNode<K,V> [] nodes;
        protected int idx;
        protected long modCheck;

        public IteratorHelper() 
        {
            nodes = new DictionaryNode[currentSize];
            idx = 0;
            int j = 0;
            modCheck = modCounter;
            for(int i=0; i<tableSize; i++) 
            {
                for(DictionaryNode<K,V> n : list[i])
                    nodes[j++] = n;
            }
            shellSort();
        }

        private void shellSort() 
        {
            DictionaryNode<K,V>[] n = nodes;
            int in=0, out=0, h=1;
            DictionaryNode<K,V> tmp = null;
            int size = n.length;

            while(h <= size/3)
                h = h*3 + 1;
            while(h > 0) 
            {
                for(out=h; out<size; out++) 
                {
                    tmp = n[out];
                    in = out;
                    while(in > h-1 && ((Comparable<K>)n[in-h].key).compareTo(tmp.key) >= 0) 
                    {
                        n[in] = n[in-h];
                        in -= h;
                    }
                    n[in] = tmp;
                }
                h = (h-1)/3;
            }
        }

        public boolean hasNext() {
            if(modCheck != modCounter)
                throw new ConcurrentModificationException();
            return idx < currentSize;
        }

        public abstract E next();

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    class KeyIteratorHelper<K> extends IteratorHelper<K> {
        public KeyIteratorHelper() {
            super();
        }

        public K next() {
            if(!hasNext())
                throw new NoSuchElementException();
            return (K) nodes[idx++].key;
        }
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V> {
        public ValueIteratorHelper() {
            super();
        }

        public V next() {
            if(!hasNext())
                throw new NoSuchElementException();
            return (V) nodes[idx++].value;
        }
    }
}
