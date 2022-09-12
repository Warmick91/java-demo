package edu.damago.java.struct;

import java.util.Arrays;

/**
 * Demo for literal values.
 */
public class LiteralDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		Object[] array = { "A", 16, true, args.length };
		System.out.println(Arrays.toString(array));
		
		array[1] = 42;		
		System.out.println(Arrays.toString(array));

		array = new Object[] { false, -42F, 1 - args.length };
		System.out.println(Arrays.toString(array));
	}
}