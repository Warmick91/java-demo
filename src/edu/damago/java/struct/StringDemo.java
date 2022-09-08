package edu.damago.java.struct;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Demo for string values.
 */
public class StringDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		String leftValue, rightValue;
		
		leftValue = "abc";
		System.out.println("length(abc) = " + leftValue.length());
		System.out.println("abc[1] = " + leftValue.charAt(1));
		System.out.println();

		System.out.println("abc.indexOf(bc) = " + leftValue.indexOf("bc"));
		System.out.println("abc.indexOf(c) = " + leftValue.indexOf("c"));
		System.out.println("abc.indexOf(c) = " + leftValue.indexOf("e"));

		leftValue = "abcabdabe";
		for (int position, startPosition = 0; ; startPosition = position + 1) {
			position = leftValue.indexOf("ab", startPosition);
			if (position == -1) break;

			System.out.println("position = " + position);
		}

		final String fileContent = "blablabla ....\n" +
			"blablabla1234||2345||3456||4567blablabla\n" +
			"blablabla\n" +
			"blablablaPW:hugo blablabla\n" +
			"... blablabla";

		final boolean containsCreditCardNumber = fileContent.matches("[\\s\\S]*\\d\\d\\d\\d\\D*\\d\\d\\d\\d\\D*\\d\\d\\d\\d\\D*\\d\\d\\d\\d[\\s\\S]*");
		System.out.println("containsCreditCardNumber = " + containsCreditCardNumber);
		final boolean containsPassword = fileContent.matches("[\\s\\S]*(pw|PW|pwd|PWD|password|passwort|passe)[\\s\\S]*");
		System.out.println("containsPassword = " + containsPassword);
		System.out.println();

		rightValue = "Wir \t\t haben      gleich\t\tFeierabend!";
		System.out.println(rightValue.substring(4, 9));
		System.out.println(rightValue.substring(17));

		final byte[] bytes = rightValue.getBytes(StandardCharsets.UTF_8);
		System.out.println("bytes = " + Arrays.toString(bytes));
		leftValue = new String(bytes, StandardCharsets.UTF_8);
		System.out.println("unbytes = " + leftValue);

		final String[] words = leftValue.split("\\s+");
		System.out.println("words = " + Arrays.toString(words));
		final String joinText = String.join("-\t-", words);
		System.out.println(joinText);
		System.out.println();

		System.out.format("%s %s %s!%n", "Jetzt", "ist", "Feieabend!");
	}
}