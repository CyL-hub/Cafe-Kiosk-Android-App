package com.cs213p5;

/**
 * Menu item object superclass.
 * Attributes a menu item's name and unit price. 
 * @author Brian Chang, Chris Lam
 */
public class MenuItem {
	String name;
    private float price;
    private int quantity;
    
    /**
     * Constructs a Menu Item object, given a name and quantity argument. 
     * @param name String name of the menu item object
     * @param quantity Int quantity of the menu item object
     */
    public MenuItem (String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    
    /**
     * Method to convert a menu item's attributes into a string.
     * @return The total quantity, name, and total price of a menu item in string format.
     */
    @Override
    public String toString() {
        return String.format( name + " (" + quantity + ")");
    }
    
    /**
     * Method to identify if two menuItem objects are equivalent, based on menu item names.
     * @param obj General object of Object superclass
     * @return Boolean true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof MenuItem) {
    		MenuItem menuItem = (MenuItem) obj;
    		return menuItem.name.equals(name) && menuItem.quantity == quantity;
    	}
    	return false;
    }
    
    /**
     * A method to calculate the item price of the menu item. 
     */
	public void itemPrice() {
		
	}
	
	/**
	 * A setter method to control changes to a menu item's total price attribute.
	 * @param price Menu item's price in float integer type.
	 */
	public void setPrice(float price) {
		this.price = price * quantity;
	}
	
	/**
	 * A getter method to obtain a menu item's total price attribute.
	 * @return price Float unit price of a menu item
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * A setter method to control changes to a menu item's total quantity.
	 * @param quantity Quantity of a menu item in integer type.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}   