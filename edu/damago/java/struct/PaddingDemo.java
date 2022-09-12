package edu.damago.java.struct;

/**
 * Demo for padding texts using two options for realization.
 */
public class PaddingDemo {
	static final int PADING_LENGTH = 50_000;

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		// warmup-phase
		String result = "";
		for (int loop = 0; loop < 20000; ++loop) {
			result = padByConcatenation("", ' ', true, 10);
			result = padByBuilder("", ' ', true, 10);
		}

		// benchmark phase
		final long startTimestamp = System.currentTimeMillis();
		for (int loop = 0; loop < 100; ++loop) {
			result = padByConcatenation("", ' ', true, PADING_LENGTH);
		}

		final long midTimestamp = System.currentTimeMillis();

		for (int loop = 0; loop < 100; ++loop) {
			result = padByBuilder("", ' ', true, PADING_LENGTH);
		}
		final long stopTimestamp = System.currentTimeMillis();
	
		// display the results
		System.out.format("padByConcatenation() required %.3fs.%n", (midTimestamp - startTimestamp) * 0.00001);
		System.out.format("padByBuilder() required %.3fs.%n", (stopTimestamp - midTimestamp) * 0.00001);
		System.out.println(result.length());
	}


	/**
	 * Pads the given text using String's + concatenation operator.
	 * @param text the text
	 * @param pad the padding character
	 * @param directionIsLeft true for left padding, false for right padding
	 * @param size the target size
	 * @return the padded text
	 */
	static private String padByConcatenation (final String text, final char pad, final boolean directionIsLeft, final int size) {
		String result = text;

		while (result.length() < size)
			result = directionIsLeft ? pad + result : result + pad;

		return result;
	}


	/**
	 * Pads the given text using a StringBuilder.
	 * @param text the text
	 * @param pad the padding character
	 * @param directionIsLeft true for left padding, false for right padding
	 * @param size the target size
	 * @return the padded text
	 */
	static private String padByBuilder (final String text, final char pad, final boolean directionIsLeft, final int size) {
		StringBuilder factory = new StringBuilder();

		while (factory.length() < size) {
			if (directionIsLeft)
				factory.insert(0, pad);
			else
				factory.append(pad);
		}

		return factory.toString();
	}
}