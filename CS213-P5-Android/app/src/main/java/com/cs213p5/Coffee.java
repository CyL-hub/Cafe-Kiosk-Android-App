package com.cs213p5;

/**
 * Coffee object class, a child class of the MenuItem class. 
 * Extends all attributes of the MenuItem class, including name, price, and quantity.
 * Child class attributes includes:
 * String size to determine coffee cup size.
 * Boolean array addInList, to contain coffee add in condiment objects.
 * Integer addInCount, to track total number of condiments added. 
 * @author Brian Chang, Chris Lam
 */
public class Coffee extends MenuItem implements Customizable {
	private String size;
	boolean addInList[];
	int addInCount;
	private static final double SHORTPRICE = 1.99, TALLPRICE = 2.49, GRANDEPRICE = 2.99, VENTIPRICE = 3.49, ADDINCOST = 0.20;
	private static final int CREAM = 0, SYRUP = 1, MILK = 2, CARAMEL = 3, WHIPPED_CREAM = 4, ADD_IN_COUNT = 5, BOOL_TRUE = 1, BOOL_FALSE = 0, CUTOFF = 2;
	
	/**
	 * Constructs a Coffee object, given a size and quantity argument.
	 * @param size Size attribute of the Coffee object, as a String.
	 * @param quantity Quantity attribute of the Coffee object, as an Int.
	 */
	public Coffee (String size, int quantity) {
		super("Coffee", quantity);
		this.size = size;
		this.addInList = new boolean[ADD_IN_COUNT];
		this.addInCount = getCount(addInList);
	}
	
	/**
	 * Method to set a Coffee's unit price. 
	 * Unit price is adjust accordingly to coffee size, and total count of condiments added.
	 */
    @Override
    public void itemPrice() {
    	float price = 0;
    	switch (size) {
    	case "short":
    		price += SHORTPRICE;
    		break;
    	case "tall":
    		price += TALLPRICE;
    		break;
    	case "grande":
    		price += GRANDEPRICE;
    		break;
    	case "venti":
    		price += VENTIPRICE;
    		break;
    	default:
    	}
    	addInCount = getCount(addInList);
    	price += (addInCount * ADDINCOST);
    	super.setPrice(price);
    }
    
    /**
     * Implementation of customizable interface add method. 
     * Sets corresponding addInList array Index to true.
     * @return True when array indices are successfully set to True, False otherwise.
     */
	public boolean add (Object obj) {
		if(obj instanceof AddIn) {
			AddIn addIn = (AddIn) obj;
			switch (addIn.getAddInString()) {
			case "cream" :
				addInList[CREAM] = true;
				break;
			case "syrup" :
				addInList[SYRUP] = true;
				break;
			case "milk" :
				addInList[MILK] = true;
				break;
			case "caramel" :
				addInList[CARAMEL] = true;
				break;
			case "whipped cream" :
				addInList[WHIPPED_CREAM] = true;
				break;
			default:
			}
			return true;
		}
		return false;
	}
	
    /**
     * Implementation of customizable interface remove method. 
     * Sets corresponding addInList array Index to false.
     * @return True when when array indices are successfully set to False, False otherwise.
     */
	public boolean remove (Object obj) {
		if(obj instanceof AddIn) {
			AddIn addIn = (AddIn) obj;
			switch (addIn.getAddInString()) {
			case "cream" :
				addInList[CREAM] = false;
				break;
			case "syrup" :
				addInList[SYRUP] = false;
				break;
			case "milk" :
				addInList[MILK] = false;
				break;
			case "caramel" :
				addInList[CARAMEL] = false;
				break;
			case "whipped cream" :
				addInList[WHIPPED_CREAM] = false;
				break;
			default:		
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Helper method to count True values in addInList.
	 * @param list Boolean list array corresponds to addInList.
	 * @return Integer count of True values in addInList.
	 */
	private int getCount(boolean list[]) {
	    int count = 0;
	    for (boolean var : list) {
	        count += (var ? BOOL_TRUE : BOOL_FALSE);
	    }
	    return count;
	}
	
	/**
	 * Setter method to control changes to Coffee objects' size attribute.
	 * @param size Coffee's size in String format. 
	 */
	public void setSize(String size) {
		this.size = size;
	}
	
	/**
	 * Method to convert a Coffee Object's attribute to a String. 
	 * @return Coffee Object's name, quantity, size, and add in condiments in String format.
	 */
	@Override
    public String toString() {
		String addIn = "";
		if(addInList[CREAM]) addIn += "Cream, ";
		if(addInList[SYRUP]) addIn += "Syrup, ";
		if(addInList[MILK]) addIn += "Milk, ";
		if(addInList[CARAMEL]) addIn += "Caramel, ";
		if(addInList[WHIPPED_CREAM]) addIn += "Whipped Cream, ";
		if(addIn.length() > 0) addIn = addIn.substring(0, addIn.length() - CUTOFF);
        return super.toString() + " - " + size + "\n" + addIn;
    }
}