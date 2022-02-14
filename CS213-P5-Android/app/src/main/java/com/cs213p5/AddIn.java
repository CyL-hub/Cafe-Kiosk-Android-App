package com.cs213p5;

/**
 * AddIn Object class. Condiment for Coffee Objects.
 * @author Brian Chang, Chris Lam
 */
public class AddIn {
	private String addInName;
	
	/**
	 * Constructs an AddIn object given a String name argument.
	 * @param addInName Name of condiment in String format.
	 */
	public AddIn(String addInName) {
		this.addInName = addInName;
	}
	
	/**
	 * Getter method to obtain an AddIn object's name attribute.
	 * @return addInName in String format.
	 */
	public String getAddInString() {
		return addInName;
	}
}
