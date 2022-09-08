package edu.damago.java.tool;

import java.net.URI;
import java.net.URISyntaxException;

public class Ressources {

	/**
	 * Returns a new URI based on the given text.
	 * @param text the text
	 * @return the URI created
	 * @throws NullPointerException if the given text is {@code null}
	 * @throws IllegalArgumentException if the given text does not represent a valid URI
	 */
	static public URI newURI (final String text) throws NullPointerException, IllegalArgumentException {
		try {
			return new URI(text);
		} catch (final URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
