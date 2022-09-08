package edu.damago.java.struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Demo for type conversions.
 */
public class TypeConversionDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		int value = 0;
		Object object = null;

		// explicit cast & auto-cast
		object = "abc";			// auto-cast of declaration-type from String to Object
		value = ((CharSequence) object).length();
		System.out.println("length(abc) = " + value);
		System.out.println();

		// auto-boxing
		value = 16;
		object = null;

		object = value;		// auto-boxing into Integer with subsequent auto-cast to Object
		System.out.println("object = " + object.getClass().getName());

		value = (Integer) object;	// auto-unboxing from Integer to int
		System.out.println("value = " + value);
		System.out.println();

		// parsing
		String text = "42.300";
		float x = Float.parseFloat(text);
		System.out.println("text = " + text);
		System.out.println("x = " + x);
		System.out.println();

		// copying
		System.out.println("args = " + Arrays.toString(args));
		final List<String> argList = new ArrayList<>(Arrays.asList(args));
		System.out.println("argList = " + argList);
		final Set<String> argSet = new HashSet<>(Arrays.asList(args));
		System.out.println("argSet = " + argSet);
		System.out.println();

		// comparisons and selections
		final boolean test = "abc".equals(args[0]);
		System.out.println("test = " + test);
		final String outputText = test ? "yes" : "no";
		System.out.println("outputText = " + outputText);
	}
}