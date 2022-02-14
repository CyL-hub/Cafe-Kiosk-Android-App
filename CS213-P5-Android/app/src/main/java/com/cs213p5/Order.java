package com.cs213p5;
import java.util.List;
import java.util.ArrayList;

/**
 * Order object container class. 
 * Creates a List container for MenuItem objects, and maintains a orderNumber to each List. 
 * Includes methods for adding and removing MenuItems from the List, returning the orderNumber as a String, and printing the MenuItems in the List.
 * @author Brian Chang, Chris Lam
 */
public class Order implements Customizable {
	private List<MenuItem> itemList;
	private int orderNumber;
	private static int orderCounter = 0;
	
	/**
	 * Constructor to create a new Order object. 
	 * Initializes a MenuItem object List and a new order number. 
	 * Order numbers are uniquely incremented to each new Order object constructed.
	 */
	public Order() {
		this.itemList = new ArrayList<MenuItem>();
		this.orderNumber = ++orderCounter;
	}
	
	/**
	 * Implementation of customizable interface add method. 
	 * Adds a MenuItem object into the Order object's List.
	 * @return True if successfully added, False otherwise.
	 */
	public boolean add (Object obj) {
		if(obj instanceof MenuItem) {
			MenuItem item = (MenuItem) obj;
			if(itemList.add(item)) {
				return true;
			} 
			return false;
		}
		return false;
	}
	
	/**
	 * Implementation of customizable interface remove method.
	 * Removes a MenuItem object from the Order object's List.
	 * @return True if successfully removed, False otherwise.
	 */
	public boolean remove (Object obj) {
		if(obj instanceof MenuItem) {
			MenuItem item = (MenuItem) obj;
			if(itemList.remove(item)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Method to convert an Order object's order number attribute to string.
	 * @return orderNumber in String format.
	 */
    @Override
    public String toString() {
        return String.format( "Order (" + orderNumber + ")");
    }
    
    /**
     * Getter method to obtain an Order object's List of MenuItem objects.
     * @return itemList List object of MenuItem objects.
     */
	public List<MenuItem> getList() {
		return itemList;
	}
	
	/**
	 * Method to print all MenuItem object's inside an Order's List.
	 * @return printList String representation of all MenuItem objects inside the List.
	 */
	public String print() {
		String printList = "";
		for (int i = 0; i < itemList.size(); i++) {
			printList = printList + itemList.get(i).toString() + "\n";
		}
		return printList;
	}
}