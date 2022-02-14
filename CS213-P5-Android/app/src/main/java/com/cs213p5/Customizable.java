package com.cs213p5;
/**
 * Customizable interface class.
 * Specifies an add and remove method that the Coffee, Order, and StoreOrders classes must implement.
 * @author Brian Chang, Chris Lam
 */
public interface Customizable {
	boolean add(Object obj);
	boolean remove(Object obj);
}