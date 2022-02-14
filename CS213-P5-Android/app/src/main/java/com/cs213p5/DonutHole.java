package com.cs213p5;

/**
 * Donut Hole object class, a child class of the MenuItem class.
 * Extends all attributes of the MenuItem class, including name, price, and quantity.
 * @author Brian Chang, Chris Lam
 */
public class DonutHole extends MenuItem {
    private static final double DONUTHOLEPRICE = 1.39;

    /**
     * Constructs a DonutHole object, given a flavor and quantity argument.
     * @param flavor Flavor attribute of the DonutHole object, as a String.
     * @param quantity Quantity attribute of the DonutHole object, as an Int.
     */
    public DonutHole (String flavor, int quantity) {
        super(String.format(flavor + " Donut Hole"), quantity);
        itemPrice();
    }

    /**
     * Method to set a DonutHole's unit price.
     */
    @Override
    public void itemPrice() {
        super.setPrice((float)DONUTHOLEPRICE);
    }
}