package edu.damago.java.struct;

import java.util.Arrays;

/**
 * Demo for parameter strategies.
 */
public class ParameterStrategyDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		int x = 16;
		System.out.println("x = " + x);

		ParameterStrategyDemo.callByValue(x);
		System.out.println("x = " + x);

		x = ParameterStrategyDemo.callByValue(x);
		System.out.println("x = " + x);

		System.out.println();
			
		int[] xContainer = { 42 };
		System.out.println("xContainer = " + Arrays.toString(xContainer));

		ParameterStrategyDemo.callByReference(xContainer);
		System.out.println("xContainer = " + Arrays.toString(xContainer));

		xContainer = ParameterStrategyDemo.callByReference(xContainer);
		System.out.println("xContainer = " + xContainer);
	}


	static private int callByValue (int value) {
		value *= 2;
		return value;
	}


	static private int[] callByReference (int[] valueContainer) {
		valueContainer[0] *= 2;
		valueContainer = null;
		return valueContainer;
	}
}