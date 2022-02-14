package com.cs213p5;

/**
 * Yeast Donut object class, a child class of the MenuItem class.
 * Extends all attributes of the MenuItem class, including name, price, and quantity.
 * @author Brian Chang, Chris Lam
 */
public class YeastDonut extends MenuItem {
    private static final double YEASTDONUTPRICE = 1.39;

    /**
     * Constructs a YeastDonut object, given a flavor and quantity argument.
     * @param flavor Flavor attribute of the YeastDonut object, as a String.
     * @param quantity Quantity attribute of the YeastDonut object, as an Int.
     */
    public YeastDonut (String flavor, int quantity) {
        super(String.format(flavor + " Yeast Donut"), quantity);
        itemPrice();
    }

    /**
     * Method to set a YeastDonut's unit price.
     */
    @Override
    public void itemPrice() {
        super.setPrice((float)YEASTDONUTPRICE);
    }
}