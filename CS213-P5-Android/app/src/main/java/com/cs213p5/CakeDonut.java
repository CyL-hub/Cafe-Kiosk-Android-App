package com.cs213p5;

/**
 * Cake Donut object class, a child class of the MenuItem class.
 * Extends all attributes of the MenuItem class, including name, price, and quantity.
 * @author Brian Chang, Chris Lam
 */
public class CakeDonut extends MenuItem {
    private static final double CAKEDONUTPRICE = 1.39;

    /**
     * Constructs a CakeDonut object, given a flavor and quantity argument.
     * @param flavor Flavor attribute of the CakeDonut object, as a String.
     * @param quantity Quantity attribute of the CakeDonut object, as an Int.
     */
    public CakeDonut (String flavor, int quantity) {
        super(String.format(flavor + " Cake Donut"), quantity);
        itemPrice();
    }

    /**
     * Method to set a CakeDonut's unit price.
     */
    @Override
    public void itemPrice() {
        super.setPrice((float)CAKEDONUTPRICE);
    }
}
