/*
 * Program #4
 * Bryan Phan
 * cssc0931
 */

import data_structures.*;
import java.util.Iterator;
import java.io.*;

public class PhoneBook {
	private int maxSize;
	private DictionaryADT<PhoneNumber, String> list;
   
    // Constructor.  There is no argument-less constructor, or default size
    public PhoneBook(int maxSize) {
    	this.maxSize = maxSize; 
    	list = new Hashtable<PhoneNumber, String>(maxSize);
    	// new BinarySearchTree<PhoneNumber, String>();
    	// new BalancedTree<PhoneNumber, String>();
    }
       
    // Reads PhoneBook data from a text file and loads the data into
    // the PhoneBook.  Data is in the form "key=value" where a phoneNumber
    // is the key and a name in the format "Last, First" is the value.
    public void load(String filename) {
    	try {
    		FileReader file = new FileReader(filename);
    		BufferedReader in = new BufferedReader(file);
    		String line;
    		while((line = in.readLine()) != null) {
    			PhoneNumber K = new PhoneNumber(line.substring(0, 12));
    			String V = line.substring(13);
    			addEntry(K,V);
    		}
    		in.close();
    	}
    	catch (IOException e) {
    		System.out.println(e.toString());
    	}
    }
           
    // Returns the name associated with the given PhoneNumber, if it is
    // in the PhoneBook, null if it is not.
    public String numberLookup(PhoneNumber number) {
    	return list.getValue(number);
    }
       
    // Returns the PhoneNumber associated with the given name value.
    // There may be duplicate values, return the first one found.
    // Return null if the name is not in the PhoneBook.
    public PhoneNumber nameLookup(String name) {
    	return list.getKey(name);
    }
       
    // Adds a new PhoneNumber = name pair to the PhoneBook.  All
    // names should be in the form "Last, First".
    // Duplicate entries are *not* allowed.  Return true if the
    // insertion succeeds otherwise false (PhoneBook is full or
    // the new record is a duplicate).  Does not change the datafile on disk.
    public boolean addEntry(PhoneNumber number, String name) {
    	return list.add(number, name);
    }
       
    // Deletes the record associated with the PhoneNumber if it is
    // in the PhoneBook.  Returns true if the number was found and
    // its record deleted, otherwise false.  Does not change the datafile on disk.
    public boolean deleteEntry(PhoneNumber number) {
    	return list.delete(number);
    }
       
    // Prints a directory of all PhoneNumbers with their associated
    // names, in sorted order (ordered by PhoneNumber).
    public void printAll() {
    	Iterator<PhoneNumber> keys = list.keys();
    	Iterator<String> values = list.values();
    	while (keys.hasNext()) {
    		System.out.print(keys.next());
    		System.out.print(" " + values.next());
    		System.out.println();
    	}
    }
       
    // Prints all records with the given Area Code in ordered
    // sorted by PhoneNumber.
    public void printByAreaCode(String code) {
    	PhoneNumber tmp;
    	Iterator<PhoneNumber> keys = list.keys();
    	while (keys.hasNext()) {
    		tmp = keys.next();
    		if(((Comparable<String>)tmp.getAreaCode()).compareTo(code) == 0)
    			System.out.println(tmp);
    	}
    }
   
    // Prints all of the names in the directory, in sorted order (by name,
    // not by number).  There may be duplicates as these are the values.       
    public void printNames() {
    	int i = 0;
    	String [] nodes = new String[list.size()];
    	Iterator<String> values = list.values();
		while(values.hasNext()) {
			nodes[i++] = values.next();
		}
		nodes = (String[]) shellShort(nodes);
		for(i = 0; i < list.size(); i++) {
			System.out.println(nodes[i] + " ");
		}
    }
    public String[] shellShort(String[] array) {
    	String[] n = array;
    		int in, out, h = 1;
    		String temp;
    		int size = n.length;
    		while(h <= size/3)
    			h = h*3+1;
    		while (h > 0) {
    			for (out = h; out < size; out++) {
    				temp = n[out];
    				in = out;
    				while (in > h-1 && n[in-h].compareTo((String) temp) >= 0) {
    					n[in] = n[in-h];
    					in -= h;
    				}
    				n[in] = temp;
    			}
    			h = (h-1)/3;
    		}
    		return n;
    }
}