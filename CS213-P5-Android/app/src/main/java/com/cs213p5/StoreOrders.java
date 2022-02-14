package com.cs213p5;
import java.util.ArrayList;
import java.util.List;

/**
 * StoreOrders object container class. 
 * Creates a List of Order objects.
 * @author Brian Chang, Chris Lam
 */
public class StoreOrders implements Customizable {
	private List<Order> orderList;
	
	/**
	 * Constructor to create StoreOrders object.
	 * Initializes a Order object List.
	 */
	public StoreOrders () {
		this.orderList = new ArrayList<Order>();
	}
	
	/**
	 * Implementation of customizable interface add method. 
	 * Adds an Order object into the StoreOrders object's List.
	 * @return True if successfully added, False otherwise.
	 */
	public boolean add (Object obj) {
		if(obj instanceof Order) {
			Order order = (Order) obj;
			if(orderList.add(order)) {
				return true;
			} 
			return false;
		}
		return false;
	}
	
	/**
	 * Implementation of customizable interface add method. 
	 * Removes an Order object from the StoreOrders object's List.
	 * @return True if successfully removed, False otherwise.
	 */
	public boolean remove (Object obj) {
		if(obj instanceof Order) {
			Order item = (Order) obj;
			if(orderList.remove(item)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * A getter method to obtain the List of Order objects.
	 * @return orderList List of Order objects.
	 */
	public List<Order> getOrderList() {
		return orderList;
	}
}