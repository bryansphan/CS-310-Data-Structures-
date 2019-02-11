/*
 * Program #4
 * Bryan Phan
 * cssc0931
 */
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinarySearchTree<K extends Comparable<K>,V> implements DictionaryADT<K,V> {
    private int currentSize, modCounter;
    private DictionaryNode<K,V> root;

    class DictionaryNode<K,V>{
        K key;
        V value;
        DictionaryNode<K,V> leftChild;
        DictionaryNode<K,V> rightChild;

        public DictionaryNode(K k, V v) {
            key = k;
            value = v;
            leftChild = rightChild = null;
        }
    }
    public BinarySearchTree() {
        currentSize = modCounter = 0;
        root = null;       
    }
   
    public boolean contains(K key) {
        return findValue(key,root) != null;
    }   
   
    public boolean add(K key, V value) {
        if(contains(key)) return false;
        if(root == null)
            root = new DictionaryNode<K,V>(key,value);
        else
            insert(key,value,root,null,false);
        currentSize++;
        modCounter++;
        return true;
    }

    private void insert(K k, V v, DictionaryNode<K,V> n, DictionaryNode<K,V> parent, boolean leftSide) {
        if(n == null) {
            if(leftSide)
                parent.leftChild = new DictionaryNode<K,V>(k,v);
            else
                parent.rightChild = new DictionaryNode<K,V>(k,v);
        }
        else if(((Comparable<K>)k).compareTo((K)n.key) < 0)
            insert(k,v,n.leftChild,n,true);
        else
            insert(k,v,n.rightChild,n,false);
    }

    public boolean delete(K key) {
        if(currentSize == 0) return false;
        if(!removeNode(key,root,null,false)) 
        	return false;
        currentSize--;
        modCounter++;
        return true;
    }
  
    private boolean removeNode(K key, DictionaryNode<K,V> n, DictionaryNode<K,V> parentNode, boolean wasLeft) {
        if(n == null) return false;
        if(((Comparable<K>)key).compareTo(n.key) < 0)
            removeNode(key,n.leftChild,n,true);
        else if(((Comparable<K>)key).compareTo(n.key) > 0)
            removeNode(key,n.rightChild,n,false);
        else 
        {
            if(n.leftChild == null && n.rightChild == null) 
            {
                if(((Comparable<K>)root.key).compareTo(n.key) == 0)
                    n = null;
                else if(wasLeft)
                    parentNode.leftChild = null;
                else
                    parentNode.rightChild = null;
            }
            else if(n.leftChild == null) 
            {
                if(((Comparable<K>)root.key).compareTo(n.key) == 0)
                    n = n.rightChild;
                else if(wasLeft)
                    parentNode.leftChild = n.rightChild;
                else
                    parentNode.rightChild = n.rightChild;
            }
            else if(n.rightChild == null) 
            {
                if(((Comparable<K>)root.key).compareTo(n.key) == 0)
                    n = n.leftChild;
                else if(wasLeft)
                    parentNode.leftChild = n.leftChild;
                else
                    parentNode.rightChild = n.leftChild;
            }
            
            else 
            {
                DictionaryNode<K,V> newNode = findChild(n.rightChild);
                removeNode(newNode.key,n.rightChild,n,false);
                n.key = newNode.key;
                n.value = newNode.value;
            }
        }
        root = n;
        return true;
    }

    private DictionaryNode<K,V> findChild(DictionaryNode<K,V> n) {
        if(n.leftChild == null)
            return n;
        return findChild(n.leftChild);
    }
   
    public V getValue(K key) {
        return findValue(key,root);
    }

    private V findValue(K key, DictionaryNode<K,V> n) {
        if(n == null) return null;
        if(((Comparable<K>)key).compareTo(n.key) < 0)
            return findValue(key, n.leftChild);
        if(((Comparable<K>)key).compareTo(n.key) > 0)
            return findValue(key, n.rightChild);
        return (V) n.value;
    }
    
    K Key;
    public K getKey(V value) {
        Key = null;
        findKey(root,value);
        return Key;
    }

    private void findKey(DictionaryNode<K,V> n, V value) {
        if(n == null) return;
        findKey(n.leftChild,value);
        if(((Comparable<V>)n.value).compareTo(value) == 0) 
        {
            Key = n.key;
        }
        findKey(n.rightChild,value);
    }
    
    public int size() {
        return currentSize;
    }

    public boolean isFull() {
        return false;
    }
   
    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void clear() {
        currentSize = modCounter = 0;
        root = null;
    }

    abstract class IteratorHelper<E> implements Iterator<E> {
        protected DictionaryNode<K,V> [] nodes;
        protected int idx, j;
        protected long modCheck;

        public IteratorHelper() {
            nodes = new DictionaryNode[currentSize];
            idx = j = 0;
            modCheck = modCounter;
            inOrder(root);
        }

        private void inOrder(DictionaryNode<K,V> n) {
            if(n == null) return;
            inOrder(n.leftChild);
            nodes[j++] = n;
            inOrder(n.rightChild);  
        }

        public boolean hasNext() {
            if(modCheck != modCounter)
                throw new ConcurrentModificationException();
            return idx < currentSize;
        }

        public abstract E next();

        public void remove()  {
            throw new UnsupportedOperationException();
        }
    }

    class KeyIteratorHelper<K> extends IteratorHelper<K> {
        public KeyIteratorHelper() 
        {
            super();
        }

        public K next() 
        {
            if(!hasNext())
                throw new NoSuchElementException();
            return (K) nodes[idx++].key;
        }
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V>  {
        public ValueIteratorHelper() {
            super();
        }

        public V next() {
            if(!hasNext())
                throw new NoSuchElementException();
            return (V) nodes[idx++].value;
        }
    }
    
    public Iterator<K> keys() {
        return (Iterator<K>) new KeyIteratorHelper<K>();
    }

    public Iterator<V> values()  {
        return (Iterator<V>) new ValueIteratorHelper<V>();
    }
}