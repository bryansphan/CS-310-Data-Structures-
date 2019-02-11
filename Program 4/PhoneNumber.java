/*
 * Program #4
 * Bryan Phan
 * cssc0931
 */

import data_structures.*;
import java.util.Iterator;

public class PhoneNumber implements Comparable<PhoneNumber> {
    String areaCode, prefix, number;
    String phoneNumber;
    DictionaryADT<PhoneNumber,String> phonebook;
   
    // Constructor.  Creates a new PhoneNumber instance.  The parameter
    // is a phone number in the form xxx-xxx-xxxx, which is area code -
    // prefix - number.  The phone number must be validated, and an
    // IllegalArgumentException thrown if it is invalid.
    public PhoneNumber(String n) {
    	verify(n);
    	phoneNumber = n;
    }
    
    // Follows the specifications of the Comparable Interface.  
    public int compareTo(PhoneNumber n) {
    	return ((Comparable<String>)phoneNumber).compareTo(n.phoneNumber);
    }
    // Returns an int representing the hashCode of the PhoneNumber.
    public int hashCode() {
    	return phoneNumber.hashCode();
    }
   
    // Private method to validate the Phone Number.  Should be called
    // from the constructor.
    //Ex. *** - *** - **** 
    private void verify(String n) {
    	if (!n.matches("\\d{3}-\\d{3}-\\d{4}"))
    		throw new IllegalArgumentException();
    }
       
    // Returns the area code of the Phone Number.
    public String getAreaCode() {
    	return phoneNumber.substring(0,3);
    }
       
    // Returns the prefix of the Phone Number.
    public String getPrefix() {
    	return phoneNumber.substring(4,7);
    }
       
    // Returns the the last four digits of the number.
    public String getNumber() {
    	return phoneNumber.substring(8,12);
    }

    // Returns the Phone Number.       
    public String toString() {
    	return phoneNumber;
    }
} 